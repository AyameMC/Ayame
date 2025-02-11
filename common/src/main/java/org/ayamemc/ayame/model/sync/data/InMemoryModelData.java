/*
 *     Custom player model mod. Powered by GeckoLib.
 *     Copyright (C) 2024-2025  CrystalNeko, HappyRespawnanchor, pertaz(Icon Designer)
 *
 *     This file is part of Ayame.
 *
 *     Ayame is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU Lesser General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     Ayame is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU Lesser General Public License for more details.
 *
 *     You should have received a copy of the GNU Lesser General Public License
 *     along with Ayame.  If not, see <https://www.gnu.org/licenses/>.
 */

package org.ayamemc.ayame.model.sync.data;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.mojang.blaze3d.platform.NativeImage;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import org.ayamemc.ayame.model.AyameModelData;
import org.ayamemc.ayame.model.IRegistrableModel;
import org.ayamemc.ayame.model.resource.IModelResource;
import org.ayamemc.ayame.model.sync.ModelSelection;
import org.ayamemc.ayame.util.JsonInterpreter;
import org.ayamemc.ayame.util.MainThreadUtil;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import software.bernie.geckolib.cache.GeckoLibCache;
import software.bernie.geckolib.cache.object.BakedGeoModel;
import software.bernie.geckolib.loading.json.raw.Model;
import software.bernie.geckolib.loading.json.typeadapter.KeyFramesAdapter;
import software.bernie.geckolib.loading.object.BakedAnimations;
import software.bernie.geckolib.loading.object.BakedModelFactory;
import software.bernie.geckolib.loading.object.GeometryTree;

import java.io.*;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.ayamemc.ayame.Ayame.MINECRAFT;
import static org.ayamemc.ayame.Ayame.MOD_ID;

public class InMemoryModelData implements ISerializableModelResource, IRegistrableModel {
    private static final int DATA_HEADER = 0x4D524D44;
    private static final int VERSION = 0x0001;

    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    private AyameModelData modelMetaData;
    private boolean canUnload = true;
    private boolean isDefaultModel = false;

    private final Map<String, byte[]> internalDataStorage = new HashMap<>();
    private final Registrar registrar = new Registrar(this); // Only used on client side

    public InMemoryModelData(boolean canUnload, boolean isDefaultModel) {
        this.canUnload = canUnload;
        this.isDefaultModel = isDefaultModel;
    }

    public InMemoryModelData(Map<String, byte[]> dataStorage, boolean canUnload, boolean isDefaultModel) {
        this.internalDataStorage.putAll(dataStorage);
        this.modelMetaData = AyameModelData.parse(this.getIndexJson().toString());
        this.canUnload = canUnload;
        this.isDefaultModel = isDefaultModel;
    }

    public JsonInterpreter getIndexJson() {
        final byte[] data = this.internalDataStorage.get("ayame.json");

        if (data == null) {
            throw new IllegalStateException("ayame.json not found!");
        }

        return JsonInterpreter.of(new ByteArrayInputStream(data));
    }

    public void restoreFrom(AyameModelData modelData) {
        this.modelMetaData = modelData;
    }

    public void restoreFrom(Map<String, byte[]> data) {
        this.internalDataStorage.putAll(data);
    }

    @Override
    public void register() {
        this.registrar.register();
    }

    @Override
    public void deregister() {
        this.registrar.deregister();
    }

    @Override
    public boolean canDeregister() {
        return this.canUnload;
    }

    @Override
    public AyameModelData.MetaData getMetaData() {
        return this.modelMetaData.metadata;
    }

    @Override
    public String getId() {
        return this.modelMetaData.metadata.id;
    }

    @Override
    public List<AyameModelData.ModelData> getModels() {
        return this.modelMetaData.models;
    }

    @Contract("_ -> new")
    private @NotNull InputStream getDataAsStream(String key) {
        final byte[] target = this.internalDataStorage.get(key);

        if (target == null) {
            throw new IllegalArgumentException("No data found for key: " + key);
        }

        return new ByteArrayInputStream(target);
    }

    @Override
    public JsonInterpreter getModelJson(AyameModelData.@NotNull ModelData model) {
        return JsonInterpreter.of(this.getDataAsStream(model.model));
    }

    @Override
    public JsonInterpreter getAnimationJson(AyameModelData.@NotNull ModelData model) {
        return JsonInterpreter.of(this.getDataAsStream(model.animation));
    }

    @Override
    public InputStream getTexture(AyameModelData.@NotNull ModelData model) {
        return this.getDataAsStream(model.texture);
    }

    @Override
    public JsonInterpreter getArmJson(AyameModelData.@NotNull ModelData model) {
        return JsonInterpreter.of(this.getDataAsStream(model.arm));
    }

    @Override
    public ModelSelection getFallbackModelSelection() {
        return DefaultModelSelection.Builder.create()
                .setGeoModel(this.createModelResourceLocation())
                .setArm(this.createArmResourceLocation())
                .setAnimation(this.createAnimationResourceLocation())
                .setTexture(this.createTextureResourceLocation())
                .setId(this.getId())
                .build();
    }

    @Override
    public boolean isDefaultModel() {
        return this.isDefaultModel;
    }

    @Override
    public void serialize(@NotNull DataOutputStream outBuffer) throws IOException {
        outBuffer.writeInt(DATA_HEADER);
        outBuffer.writeInt(VERSION);
        outBuffer.writeUTF(GSON.toJson(this.modelMetaData));

        outBuffer.writeBoolean(this.canUnload);
        outBuffer.writeBoolean(this.isDefaultModel);

        outBuffer.writeInt(this.internalDataStorage.size());
        for (Map.Entry<String, byte[]> entry : this.internalDataStorage.entrySet()) {
            final String fileName = entry.getKey();
            final byte[] data = entry.getValue();

            outBuffer.writeUTF(fileName);
            outBuffer.writeInt(data.length);
        }
    }

    @Override
    public void deserialize(@NotNull DataInputStream inBuffer) throws IOException {
        final int header = inBuffer.readInt();
        final int version = inBuffer.readInt();

        if (header != DATA_HEADER) {
            throw new IOException("Invalid data header");
        }

        if (version != VERSION) {
            throw new IOException("Invalid data version");
        }

        this.modelMetaData = GSON.fromJson(inBuffer.readUTF(), AyameModelData.class);

        this.canUnload = inBuffer.readBoolean();
        this.isDefaultModel = inBuffer.readBoolean();

        final int dataCount = inBuffer.readInt();
        for (int i = 0; i < dataCount; i++) {
            final String fileName = inBuffer.readUTF();
            final int dataLength = inBuffer.readInt();

            final byte[] data = new byte[dataLength];
            inBuffer.readFully(data);

            this.internalDataStorage.put(fileName, data);
        }
    }

    public static class Registrar {
        private final InMemoryModelData modelResource;

        public Registrar(InMemoryModelData modelResource) {
            this.modelResource = modelResource;
        }

        public void register() {
            final Runnable scheduledRegister = () -> {
                addBakedModel(this.modelResource.createModelResourceLocation(), this.modelResource);
                addBakedModel(this.modelResource.createArmResourceLocation(), this.modelResource);
                addBakedAnimationFromModelResource(this.modelResource.createAnimationResourceLocation(), this.modelResource);
                registerTextureDynamically(this.modelResource.createTextureResourceLocation(), this.modelResource);
            };

            if (!MainThreadUtil.runningOnClientMain()) {
                MINECRAFT.execute(scheduledRegister);
                return;
            }

            scheduledRegister.run();
        }

        public void deregister() {
            final Runnable scheduledDeregister = () -> {
                removeBakedModel(this.modelResource.createModelResourceLocation());
                removeBakedModel(this.modelResource.createArmResourceLocation());
                removeBakedAnimation(this.modelResource.createAnimationResourceLocation());
                deregisterTextureDynamically(this.modelResource.createTextureResourceLocation());
            };

            if (!MainThreadUtil.runningOnClientMain()) {
                MINECRAFT.execute(scheduledDeregister);
                return;
            }

            scheduledDeregister.run();
        }

        public static void removeBakedAnimation(ResourceLocation location) {
            final Map<ResourceLocation, BakedAnimations> animations = getInjectedAnimationsMap();

            animations.remove(location);
        }

        public static void removeBakedModel(ResourceLocation location) {
            final Map<ResourceLocation, BakedGeoModel> models = getInjectedModelsMap();

            models.remove(location);
        }

        public static void deregisterTextureDynamically(ResourceLocation target) {
            final TextureManager textureManager = MINECRAFT.getTextureManager();

            textureManager.release(target);
        }

        public static void addBakedModel(ResourceLocation resourceLocation, @NotNull IModelResource modelRes) {
            Map<ResourceLocation, BakedGeoModel> models = getInjectedModelsMap();

            models.put(resourceLocation, instanceBakedModel(modelRes));
        }

        public static BakedGeoModel instanceBakedModel(@NotNull IModelResource resource) {
            Model m = KeyFramesAdapter.GEO_GSON
                    .fromJson(GsonHelper.fromJson(KeyFramesAdapter.GEO_GSON, resource.getModelJson(resource.getDefault()).toString(), JsonObject.class), Model.class);

            return BakedModelFactory.getForNamespace(MOD_ID).constructGeoModel(GeometryTree.fromModel(m));
        }

        public static BakedAnimations instanceBakedAnimation(@NotNull IModelResource resource) {
            return KeyFramesAdapter.GEO_GSON
                    .fromJson(GsonHelper.getAsJsonObject(resource.getAnimationJson(resource.getDefault()).toGson(), "animations"), BakedAnimations.class);
        }

        public static void addBakedAnimationFromModelResource(ResourceLocation resourceLocation, @NotNull IModelResource modelRes) {
            addBakedAnimationDirectly(resourceLocation, instanceBakedAnimation(modelRes));
        }

        public static void addBakedAnimationDirectly(ResourceLocation resourceLocation, @NotNull BakedAnimations ani) {
            Map<ResourceLocation, BakedAnimations> animations = getInjectedAnimationsMap();
            animations.put(resourceLocation, ani);
        }

        public static void registerTextureDynamically(ResourceLocation resourceLocation, @NotNull IModelResource modelRes) {
            try {
                MINECRAFT.getTextureManager().register(resourceLocation, new DynamicTexture(NativeImage.read(modelRes.getTexture(modelRes.getDefault()))));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        // Our black magic of GeckoLib
        public static Map<ResourceLocation, BakedGeoModel> getInjectedModelsMap() {
            try {
                final Class<GeckoLibCache> targetClazz = GeckoLibCache.class;

                final Field modelsField = targetClazz.getDeclaredField("MODELS");
                modelsField.setAccessible(true);

                final Map<ResourceLocation, BakedGeoModel> originalValue = (Map<ResourceLocation, BakedGeoModel>) modelsField.get(null);

                // Already replaced
                if (Object2ObjectOpenHashMap.class.isAssignableFrom(originalValue.getClass())) {
                    return originalValue;
                }

                final Map<ResourceLocation, BakedGeoModel> newValue = new Object2ObjectOpenHashMap<>(originalValue);

                modelsField.set(null, newValue);
                return newValue;
            }catch (Exception e) {
                throw new RuntimeException(e);
            }
        }

        public static Map<ResourceLocation, BakedAnimations> getInjectedAnimationsMap() {
            try {
                final Class<GeckoLibCache> targetClazz = GeckoLibCache.class;

                final Field modelsField = targetClazz.getDeclaredField("ANIMATIONS");
                modelsField.setAccessible(true);

                final Map<ResourceLocation, BakedAnimations> originalValue = (Map<ResourceLocation, BakedAnimations>) modelsField.get(null);

                // Already replaced
                if (Object2ObjectOpenHashMap.class.isAssignableFrom(originalValue.getClass())) {
                    return originalValue;
                }

                final Map<ResourceLocation, BakedAnimations> newValue = new Object2ObjectOpenHashMap<>(originalValue);

                modelsField.set(null, newValue);
                return newValue;
            }catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }
}
