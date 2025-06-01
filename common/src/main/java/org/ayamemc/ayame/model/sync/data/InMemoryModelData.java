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
import org.ayamemc.ayame.mixin.accessor.GeckoLibCacheAccessor;
import org.ayamemc.ayame.model.AyameModelData;
import org.ayamemc.ayame.model.IRegistrableModel;
import org.ayamemc.ayame.model.resource.IModelResource;
import org.ayamemc.ayame.model.sync.ModelSelection;
import org.ayamemc.ayame.util.JsonInterpreter;
import org.ayamemc.ayame.util.MainThreadUtil;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import software.bernie.geckolib.cache.object.BakedGeoModel;
import software.bernie.geckolib.loading.json.raw.Model;
import software.bernie.geckolib.loading.json.typeadapter.KeyFramesAdapter;
import software.bernie.geckolib.loading.object.BakedAnimations;
import software.bernie.geckolib.loading.object.BakedModelFactory;
import software.bernie.geckolib.loading.object.GeometryTree;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

import static org.ayamemc.ayame.Ayame.MOD_ID;
import static org.ayamemc.ayame.client.AyameClient.MINECRAFT;

public class InMemoryModelData implements ISerializableModelResource, IRegistrableModel {
    private static final int DATA_HEADER = 0x4D524D44;
    private static final int VERSION = 0x0001;

    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private final Map<String, byte[]> internalDataStorage = new HashMap<>();
    private final Registrar registrar = new Registrar(this); // Only used on client side
    private AyameModelData modelMetaData;
    private boolean canUnload = true;
    private boolean isDefaultModel = false;

    public InMemoryModelData(boolean canUnload, boolean isDefaultModel) {
        this.canUnload = canUnload;
        this.isDefaultModel = isDefaultModel;
    }

    public InMemoryModelData(Map<String, byte[]> dataStorage, boolean canUnload, boolean isDefaultModel) {
        this.internalDataStorage.putAll(dataStorage);
        byte[] jsonData = this.internalDataStorage.get("ayame.json");
        if (jsonData == null) throw new IllegalStateException("ayame.json not found!");
        String jsonString = new String(jsonData, StandardCharsets.UTF_8);
        this.modelMetaData = AyameModelData.parse(jsonString);
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
        byte[] jsonData = this.internalDataStorage.get("ayame.json");
        if (jsonData != null) {
            String jsonString = new String(jsonData, StandardCharsets.UTF_8);
            this.modelMetaData = AyameModelData.parse(jsonString);
        }
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
    public AyameModelData.ModelData getModelData(String modelName) {
        // Find the model data by name
        return this.modelMetaData.model.subModels.stream()
                .filter(modelData -> modelData.name.equals(modelName))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Model data not found for name: " + modelName));
    }

    @Override
    public AyameModelData.ScriptData getScriptData() {
        return this.modelMetaData.script;
    }

    @Override
    public String getId() {
        return this.modelMetaData.metadata.id;
    }

    @Override
    public AyameModelData.ModelData getDefaultModel() {
        return this.modelMetaData.model;
    }

    @Contract("_ -> new")
    private @NotNull InputStream getDataAsStream(String key) {
        final byte[] target = this.internalDataStorage.get(key);

        if (target == null) {
            throw new IllegalArgumentException("No data found for key: " + key);
        }

        return new ByteArrayInputStream(target);
    }

    // TODO: 修复model null问题
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
                    .fromJson(GsonHelper.fromJson(KeyFramesAdapter.GEO_GSON, resource.getModelJson(resource.getDefaultModel()).toString(), JsonObject.class), Model.class);

            return BakedModelFactory.getForNamespace(MOD_ID).constructGeoModel(GeometryTree.fromModel(m));
        }

        public static BakedAnimations instanceBakedAnimation(@NotNull IModelResource resource) {
            return KeyFramesAdapter.GEO_GSON
                    .fromJson(GsonHelper.getAsJsonObject(resource.getAnimationJson(resource.getDefaultModel()).toGson(), "animations"), BakedAnimations.class);
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
                MINECRAFT.getTextureManager().register(resourceLocation, new DynamicTexture(NativeImage.read(modelRes.getTexture(modelRes.getDefaultModel()))));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        // Our black magic of GeckoLib
        public static Map<ResourceLocation, BakedGeoModel> getInjectedModelsMap() {
            final Map<ResourceLocation, BakedGeoModel> GECKO_MODELS = GeckoLibCacheAccessor.getModels();

            // Already replaced
            if (GECKO_MODELS instanceof Object2ObjectOpenHashMap) {
                return GECKO_MODELS;
            }

            Map<ResourceLocation, BakedGeoModel> newValue = new Object2ObjectOpenHashMap<>(GECKO_MODELS);

            GeckoLibCacheAccessor.setModels(newValue);
            return newValue;
        }

        public static Map<ResourceLocation, BakedAnimations> getInjectedAnimationsMap() {
            final Map<ResourceLocation, BakedAnimations> GECKO_ANIMATIONS = GeckoLibCacheAccessor.getAnimations();
            // Already replaced
            if (GECKO_ANIMATIONS instanceof Object2ObjectOpenHashMap) {
                return GECKO_ANIMATIONS;
            }

            Map<ResourceLocation, BakedAnimations> newValue = new Object2ObjectOpenHashMap<>(GECKO_ANIMATIONS);

            GeckoLibCacheAccessor.setAnimations(newValue);
            return newValue;
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
    }
}
