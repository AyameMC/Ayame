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

package org.ayamemc.ayame.client.util;

import com.google.gson.JsonObject;
import com.mojang.blaze3d.platform.NativeImage;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import org.ayamemc.ayame.client.api.PlayerModelAPIHooks;
import org.ayamemc.ayame.model.sync.data.DefaultModelSelection;
import org.ayamemc.ayame.model.resource.IModelResource;
import org.ayamemc.ayame.util.TaskManager;
import org.jetbrains.annotations.NotNull;
import software.bernie.geckolib.cache.GeckoLibCache;
import software.bernie.geckolib.cache.object.BakedGeoModel;
import software.bernie.geckolib.loading.json.raw.Model;
import software.bernie.geckolib.loading.json.typeadapter.KeyFramesAdapter;
import software.bernie.geckolib.loading.object.BakedAnimations;
import software.bernie.geckolib.loading.object.BakedModelFactory;
import software.bernie.geckolib.loading.object.GeometryTree;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

import static org.ayamemc.ayame.Ayame.MINECRAFT;
import static org.ayamemc.ayame.Ayame.MOD_ID;

/**
 * 用于向GeckoLib缓存和贴图写入新模型的工具类
 *
 * @see GeckoLibCache
 */

public class ModelResourceLoadUtil {
    /**
     * @param modelRes 模型资源
     * @return 未完成的模型构建器
     */




    /**
     * 向模型缓存中添加新条目
     *
     * @param resourceLocation 传入{@link ResourceLocation}类型的文件路径
     * @param modelRes         模型资源
     */
    public static void addBakedModel(ResourceLocation resourceLocation, @NotNull IModelResource modelRes) {
        Map<ResourceLocation, BakedGeoModel> models = GeckoLibCache.getBakedModels();
        // 如果已经存在了
        if (models.containsKey(resourceLocation)) return;

        models.put(resourceLocation, instanceBakedModel(modelRes));
    }

    public static BakedGeoModel instanceBakedModel(@NotNull IModelResource resource) {
        Model m = KeyFramesAdapter.GEO_GSON
                .fromJson(GsonHelper.fromJson(KeyFramesAdapter.GEO_GSON, resource.getModelJson(resource.getDefault()).toString(), JsonObject.class), Model.class);

        return BakedModelFactory.getForNamespace(MOD_ID).constructGeoModel(GeometryTree.fromModel(m));
    }

    public static void addBakedModelDirectly(ResourceLocation resourceLocation, @NotNull BakedGeoModel model) {
        Map<ResourceLocation, BakedGeoModel> models = GeckoLibCache.getBakedModels();
        if (models.containsKey(resourceLocation)) return;
        models.put(resourceLocation, model);
    }

    public static BakedAnimations instanceBakedAnimation(@NotNull IModelResource resource) {
        return KeyFramesAdapter.GEO_GSON
                .fromJson(GsonHelper.getAsJsonObject(resource.getAnimationJson(resource.getDefault()).toGson(), "animations"), BakedAnimations.class);
    }

    /**
     * 向动画缓存中添加新条目
     *
     * @param resourceLocation 传入{@link ResourceLocation}类型的文件路径
     * @param modelRes         模型资源
     * @see ResourceLocation
     */
    public static void addBakedAnimationFromModelResource(ResourceLocation resourceLocation, @NotNull IModelResource modelRes) {
        Map<ResourceLocation, BakedAnimations> animations = GeckoLibCache.getBakedAnimations();
        if (animations.containsKey(resourceLocation)) return;
        addBakedAnimationDirectly(resourceLocation, instanceBakedAnimation(modelRes));
    }

    public static void addBakedAnimationDirectly(ResourceLocation resourceLocation, @NotNull BakedAnimations ani) {
        Map<ResourceLocation, BakedAnimations> animations = GeckoLibCache.getBakedAnimations();
        if (animations.containsKey(resourceLocation)) return;
        animations.put(resourceLocation, ani);
    }

    /**
     * 注册贴图
     *
     * @param resourceLocation 传入{@link ResourceLocation}类型的文件路径
     * @param modelRes         模型资源
     */
    public static void registerTextureDynamically(ResourceLocation resourceLocation, @NotNull IModelResource modelRes) {
        try {
            MINECRAFT.getTextureManager().register(resourceLocation, new DynamicTexture(NativeImage.read(modelRes.getTexture(modelRes.getDefault()))));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void registerTextureDynamically(ResourceLocation resourceLocation, InputStream inputStream) {
        try {
            MINECRAFT.getTextureManager().register(resourceLocation, new DynamicTexture(NativeImage.read(inputStream)));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static BakedGeoModel readModel(ResourceLocation resourceLocation) {
        Map<ResourceLocation, BakedGeoModel> models = GeckoLibCache.getBakedModels();
        if (!models.containsKey(resourceLocation)) {
            throw new RuntimeException("Model not found: " + resourceLocation);
        }
        return models.get(resourceLocation);
    }

    public static BakedAnimations readAnimation(ResourceLocation resourceLocation) {
        Map<ResourceLocation, BakedAnimations> animations = GeckoLibCache.getBakedAnimations();
        if (!animations.containsKey(resourceLocation)) {
            throw new RuntimeException("Animation not found: " + resourceLocation);
        }
        return animations.get(resourceLocation);
    }

    public static InputStream readTexture(ResourceLocation resourceLocation) {
        TextureManager textureManager = MINECRAFT.getTextureManager();
        if (!textureManager.getTexture(resourceLocation, null).getClass().equals(DynamicTexture.class)) {
            throw new RuntimeException("Texture not found: " + resourceLocation);
        }
        try {
            return new ByteArrayInputStream(((DynamicTexture) textureManager.getTexture(resourceLocation, null)).getPixels().asByteArray());
        } catch (IOException e) {
            throw new RuntimeException("Failed to trad Textures", e);
        }
    }
}
