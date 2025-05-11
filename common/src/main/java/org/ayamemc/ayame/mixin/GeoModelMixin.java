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

package org.ayamemc.ayame.mixin;

import net.minecraft.resources.ResourceLocation;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import software.bernie.geckolib.GeckoLibConstants;
import software.bernie.geckolib.animation.AnimationProcessor;
import software.bernie.geckolib.cache.GeckoLibCache;
import software.bernie.geckolib.cache.object.BakedGeoModel;
import software.bernie.geckolib.model.GeoModel;

@Mixin(GeoModel.class)
public abstract class GeoModelMixin {
    @Shadow private BakedGeoModel currentModel;

    @Shadow @Final private AnimationProcessor<T> processor;


    /**
     * @author a
     * @reason a
     */
    @Overwrite
    public BakedGeoModel getBakedModel(ResourceLocation location) {
        BakedGeoModel model = GeckoLibCache.getBakedModels().get(location);

        if (model == null) {
            if (!location.getPath().contains("geo/")){

            }
                //throw GeckoLibConstants.exception(location, "Invalid model resource path provided - GeckoLib models must be placed in assets/<modid>/geo/");

           // throw GeckoLibConstants.exception(location, "Unable to find model");
        }

        if (model != this.currentModel) {
            this.processor.setActiveModel(model);
            this.currentModel = model;
        }

        return this.currentModel;
    }
}
