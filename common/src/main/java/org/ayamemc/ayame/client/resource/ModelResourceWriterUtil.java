/*
 *      Custom player model mod. Powered by GeckoLib.
 *      Copyright (C) 2024  CrystalNeko, HappyRespawnanchor, pertaz(Icon Desiger)
 *
 *      This file is part of Ayame.
 *
 *     Ayame is free software: you can redistribute it and/or modify it under the terms of the GNU Lesser General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.
 *
 *     Ayame is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more details.
 *
 *     You should have received a copy of the GNU Lesser General Public License along with Ayame. If not, see <https://www.gnu.org/licenses/>.
 */

/*
 *      Custom player model mod. Powered by GeckoLib.
 *      Copyright (C) 2024  CrystalNeko, HappyRespawnanchor, pertaz(Icon Desiger)
 *
 *      This file is part of Ayame.
 *
 *     Ayame is free software: you can redistribute it and/or modify it under the terms of the GNU Lesser General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.
 *
 *     Ayame is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more details.
 *
 *     You should have received a copy of the GNU Lesser General Public License along with Ayame. If not, see <https://www.gnu.org/licenses/>.
 */

package org.ayamemc.ayame.client.resource;

import com.mojang.blaze3d.platform.NativeImage;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import software.bernie.geckolib.cache.GeckoLibCache;
import software.bernie.geckolib.cache.object.BakedGeoModel;
import software.bernie.geckolib.loading.json.raw.Model;
import software.bernie.geckolib.loading.json.typeadapter.KeyFramesAdapter;
import software.bernie.geckolib.loading.object.BakedAnimations;
import software.bernie.geckolib.loading.object.BakedModelFactory;
import software.bernie.geckolib.loading.object.GeometryTree;

import java.io.IOException;

import static org.ayamemc.ayame.Ayame.LOGGER;
import static org.ayamemc.ayame.Ayame.MOD_ID;

/**
 * 用于向GeckoLib缓存和贴图写入新模型的工具类
 *
 * @see GeckoLibCache
 */
public class ModelResourceWriterUtil {
    /**
     * @param namespace {@link String}类型的命名空间
     * @param modelRes  传入{@link ModelResource}类型，模型资源（如json）的路径
     *
     * @return {@link ModelResourceLocationRecord} 模型资源路径和动画路径的记录
     */
    public static ModelResourceLocationRecord addModelResource(String namespace, ModelResource modelRes) {
        ResourceLocation modelLocation = ResourceLocation.fromNamespaceAndPath(namespace, "geo/ayame/" + modelRes.getMetaData().name() + ".json");
        ResourceLocation animationLocation = ResourceLocation.fromNamespaceAndPath(namespace, "animations/ayame/" + modelRes.getMetaData().name() + ".json");
        addBakedModel(modelLocation, modelRes);
        addBakedAnimation(animationLocation, modelRes);

        int record = 0;

        return new ModelResourceLocationRecord(modelLocation, animationLocation, addTexture(modelRes));
    }

    /**
     * 向模型缓存中添加新条目
     *
     * @param resourceLocation 传入{@link ResourceLocation}类型的文件路径
     * @param modelRes         传入{@link ModelResource}类型，模型资源（如json）的路径
     *
     */
    public static void addBakedModel(ResourceLocation resourceLocation, ModelResource modelRes) {
        Model m = KeyFramesAdapter.GEO_GSON.fromJson(modelRes.getModelJson().toGson(), Model.class);
        BakedGeoModel bakedGeoModel = BakedModelFactory.getForNamespace(MOD_ID).constructGeoModel(GeometryTree.fromModel(m));

        GeckoLibCache.getBakedModels().put(resourceLocation, bakedGeoModel);
    }

    /**
     * 向动画缓存中添加新条目
     *
     * @param resourceLocation 传入{@link ResourceLocation}类型的文件路径
     * @param modelRes         传入{@link ModelResource}类型，模型资源（如json）的路径
     * @see ResourceLocation
     */
    public static void addBakedAnimation(ResourceLocation resourceLocation, ModelResource modelRes) {
        BakedAnimations ani = KeyFramesAdapter.GEO_GSON.fromJson(GsonHelper.getAsJsonObject(modelRes.getAnimationJson().toGson(), "animations"), BakedAnimations.class);
        GeckoLibCache.getBakedAnimations().put(resourceLocation, ani);
    }

    /**
     * 注册贴图
     * @param res 模型资源
     * @return {@link ResourceLocation} 资源的路径
     */
    public static ResourceLocation addTexture(ModelResource res) {
        try {
            return Minecraft.getInstance().getTextureManager().register(res.getMetaData().name(),new DynamicTexture(NativeImage.read(res.getTextureContent())));
        } catch (IOException e) {
            LOGGER.error("Failed to load texture for model :{}", res.getMetaData().name(),e);
        }
        return ResourceLocation.fromNamespaceAndPath(MOD_ID,"missing");
    }

    /**
     * 模型资源路径和动画路径的记录，只是为了方便
     * @param modelLocation 模型资源路径
     * @param animationLocation 模型动画路径
     * @param textureLocation 贴图路径
     */
    public record ModelResourceLocationRecord(ResourceLocation modelLocation, ResourceLocation animationLocation,ResourceLocation textureLocation) {}
}

