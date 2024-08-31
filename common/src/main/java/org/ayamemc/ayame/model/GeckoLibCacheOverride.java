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

package org.ayamemc.ayame.model;

import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.cache.GeckoLibCache;
import software.bernie.geckolib.cache.object.BakedGeoModel;
import software.bernie.geckolib.loading.object.BakedAnimations;

import java.util.Map;

public class GeckoLibCacheOverride {

    // 向模型缓存中添加新条目
    public static void addBakedModel(ResourceLocation resourceLocation, BakedGeoModel model) {
        Map<ResourceLocation, BakedGeoModel> models = GeckoLibCache.getBakedModels();
        models.put(resourceLocation, model);
    }

    // 动画缓存
    public static void addBakedAnimation(ResourceLocation resourceLocation, BakedAnimations animation) {
        Map<ResourceLocation, BakedAnimations> animations = GeckoLibCache.getBakedAnimations();
        animations.put(resourceLocation, animation);
    }
}

