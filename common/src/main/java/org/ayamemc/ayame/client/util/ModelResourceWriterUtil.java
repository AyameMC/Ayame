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
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import org.ayamemc.ayame.client.api.PlayerModelAPI;
import org.ayamemc.ayame.model.DefaultModelType;
import org.ayamemc.ayame.model.resource.IModelResource;
import org.ayamemc.ayame.util.JsonInterpreter;
import org.jetbrains.annotations.NotNull;
import software.bernie.geckolib.cache.GeckoLibCache;
import software.bernie.geckolib.cache.object.BakedGeoModel;
import software.bernie.geckolib.loading.json.raw.Model;
import software.bernie.geckolib.loading.json.typeadapter.KeyFramesAdapter;
import software.bernie.geckolib.loading.object.BakedAnimations;
import software.bernie.geckolib.loading.object.BakedModelFactory;
import software.bernie.geckolib.loading.object.GeometryTree;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

import static org.ayamemc.ayame.Ayame.MOD_ID;

/**
 * 用于向GeckoLib缓存和贴图写入新模型的工具类
 *
 * @see GeckoLibCache
 */
@Environment(EnvType.CLIENT)
public class ModelResourceWriterUtil {
    /**
     * @param modelRes 模型资源
     * @return 未完成的模型构建器
     */
    public static DefaultModelType.Builder addModelResource(@NotNull IModelResource modelRes) {
        addBakedModel(modelRes.createModelResourceLocation(), modelRes);
        addBakedModel(modelRes.createArmResourceLocation(), modelRes);
        addBakedAnimation(modelRes.createAnimationResourceLocation(), modelRes);
        addTexture(modelRes.createTextureResourceLocation(), modelRes);
        return DefaultModelType.Builder.create()
                .setGeoModel(modelRes.createModelResourceLocation())
                .setAnimation(modelRes.createAnimationResourceLocation())
                .setTexture(modelRes.createTextureResourceLocation());
    }

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
        Model m = KeyFramesAdapter.GEO_GSON.fromJson(GsonHelper.fromJson(KeyFramesAdapter.GEO_GSON, modelRes.getModelJson(modelRes.getDefault()).toString(), JsonObject.class), Model.class);
        BakedGeoModel bakedGeoModel = BakedModelFactory.getForNamespace(MOD_ID).constructGeoModel(GeometryTree.fromModel(m));
        models.put(resourceLocation, bakedGeoModel);
    }

    /**
     * 向动画缓存中添加新条目
     *
     * @param resourceLocation 传入{@link ResourceLocation}类型的文件路径
     * @param modelRes         模型资源
     * @see ResourceLocation
     */
    public static void addBakedAnimation(ResourceLocation resourceLocation, @NotNull IModelResource modelRes) {
        Map<ResourceLocation, BakedAnimations> animations = GeckoLibCache.getBakedAnimations();
        if (animations.containsKey(resourceLocation)) return;
        BakedAnimations ani = KeyFramesAdapter.GEO_GSON.fromJson(GsonHelper.getAsJsonObject(modelRes.getAnimationJson(modelRes.getDefault()).toGson(), "animations"), BakedAnimations.class);
        animations.put(resourceLocation, ani);
    }

    /**
     * 注册贴图
     *
     * @param resourceLocation 传入{@link ResourceLocation}类型的文件路径
     * @param modelRes         模型资源
     */
    public static void addTexture(ResourceLocation resourceLocation, @NotNull IModelResource modelRes) {
        try {
            Minecraft.getInstance().getTextureManager().register(resourceLocation, new DynamicTexture(NativeImage.read(modelRes.getTexture(modelRes.getDefault()))));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void addBakedModel(ResourceLocation resourceLocation, JsonInterpreter json) {
        Map<ResourceLocation, BakedGeoModel> models = GeckoLibCache.getBakedModels();
        if (models.containsKey(resourceLocation)) return;
        Model m = KeyFramesAdapter.GEO_GSON.fromJson(json.toString(), Model.class);
        BakedGeoModel bakedGeoModel = BakedModelFactory.getForNamespace(MOD_ID).constructGeoModel(GeometryTree.fromModel(m));
        models.put(resourceLocation, bakedGeoModel);
    }

    public static void addBakedAnimation(ResourceLocation resourceLocation, JsonInterpreter json) {
        Map<ResourceLocation, BakedAnimations> animations = GeckoLibCache.getBakedAnimations();
        if (animations.containsKey(resourceLocation)) return;
        BakedAnimations ani = KeyFramesAdapter.GEO_GSON.fromJson(json.toString(), BakedAnimations.class);
        animations.put(resourceLocation, ani);
    }

    public static void addTexture(ResourceLocation resourceLocation, InputStream inputStream) {
        try {
            Minecraft.getInstance().getTextureManager().register(resourceLocation, new DynamicTexture(NativeImage.read(inputStream)));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static DefaultModelType.Builder addModelResource(PlayerModelAPI.CacheEntry entry) {
        addBakedModel(entry.model().getGeoModel(), entry.modelJson());
        addBakedModel(entry.model().getArm(), entry.armJson());
        addBakedAnimation(entry.model().getAnimation(), entry.animJson());
        addTexture(entry.model().getTexture(), entry.texture());
        return DefaultModelType.Builder.create()
                .setGeoModel(entry.model().getGeoModel())
                .setAnimation(entry.model().getAnimation())
                .setTexture(entry.model().getTexture());
    }
}
