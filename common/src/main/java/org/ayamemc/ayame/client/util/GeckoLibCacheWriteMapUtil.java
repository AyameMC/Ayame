/*
 *      Custom player model mod. Based on GeckoLib.
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

package org.ayamemc.ayame.client.util;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import org.ayamemc.ayame.client.resource.ModelResource;
import software.bernie.geckolib.cache.GeckoLibCache;
import software.bernie.geckolib.cache.object.BakedGeoModel;
import software.bernie.geckolib.loading.json.raw.Model;
import software.bernie.geckolib.loading.json.typeadapter.KeyFramesAdapter;
import software.bernie.geckolib.loading.object.BakedAnimations;
import software.bernie.geckolib.loading.object.BakedModelFactory;
import software.bernie.geckolib.loading.object.GeometryTree;

import static org.ayamemc.ayame.Ayame.MOD_ID;

public class GeckoLibCacheWriteMapUtil {

    public static void addModelResource(String namespace, ModelResource modelRes){
        addBakedModel(ResourceLocation.fromNamespaceAndPath(namespace, "geo/ayame/"+modelRes.getMetaData().name()+".json"), modelRes);
        addBakedAnimation(ResourceLocation.fromNamespaceAndPath(namespace, "animations/ayame/"+modelRes.getMetaData().name()+".json"), modelRes);
    }

    // 向模型缓存中添加新条目
    public static void addBakedModel(ResourceLocation resourceLocation, ModelResource modelRes) {
        Model m = KeyFramesAdapter.GEO_GSON.fromJson(modelRes.getModelJson().toGson(), Model.class);
        BakedGeoModel bakedGeoModel = BakedModelFactory.getForNamespace(MOD_ID).constructGeoModel(GeometryTree.fromModel(m));

        GeckoLibCache.getBakedModels().put(resourceLocation, bakedGeoModel);
    }

    // 动画缓存
    public static void addBakedAnimation(ResourceLocation resourceLocation, ModelResource modelRes) {
        BakedAnimations ani = KeyFramesAdapter.GEO_GSON.fromJson(GsonHelper.getAsJsonObject(modelRes.getAnimationJson().toGson(), "animations"), BakedAnimations.class);
        GeckoLibCache.getBakedAnimations().put(resourceLocation, ani);
    }
}

