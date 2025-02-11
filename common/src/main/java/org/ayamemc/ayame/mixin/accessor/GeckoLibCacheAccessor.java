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

package org.ayamemc.ayame.mixin.accessor;

import net.minecraft.resources.ResourceLocation;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.gen.Accessor;
import software.bernie.geckolib.cache.GeckoLibCache;
import software.bernie.geckolib.cache.object.BakedGeoModel;
import software.bernie.geckolib.loading.object.BakedAnimations;

import java.util.Map;

@Mixin(value = GeckoLibCache.class, remap = false)
public interface GeckoLibCacheAccessor {
    @Accessor(value = "MODELS")
    static Map<ResourceLocation, BakedGeoModel> getModels() {
        throw new AssertionError();
    }

    @Mutable
    @Accessor(value = "MODELS")
    static void setModels(Map<ResourceLocation, BakedGeoModel> models) {
        throw new AssertionError();
    }

    @Accessor(value = "ANIMATIONS")
    static Map<ResourceLocation, BakedAnimations> getAnimations() {
        throw new AssertionError();
    }

    @Mutable
    @Accessor(value = "ANIMATIONS")
    static void setAnimations(Map<ResourceLocation, BakedAnimations> animations) {
        throw new AssertionError();
    }
}
