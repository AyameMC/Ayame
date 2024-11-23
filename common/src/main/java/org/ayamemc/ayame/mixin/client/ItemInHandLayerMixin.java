/*
 *     Custom player model mod. Powered by GeckoLib.
 *     Copyright (C) 2024  CrystalNeko, HappyRespawnanchor, pertaz(Icon Designer)
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

package org.ayamemc.ayame.mixin.client;

import net.minecraft.client.renderer.entity.layers.ItemInHandLayer;
import org.ayamemc.ayame.client.renderer.AyameGeoAnimatable;
import org.spongepowered.asm.mixin.Mixin;
import software.bernie.geckolib.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.animation.AnimatableManager;

@Mixin(ItemInHandLayer.class)
public class ItemInHandLayerMixin implements AyameGeoAnimatable {
    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {

        AyameGeoAnimatable.super.registerControllers(controllers);
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return AyameGeoAnimatable.super.getAnimatableInstanceCache();
    }

    @Override
    public double getTick(Object object) {
        return AyameGeoAnimatable.super.getTick(object);
    }
}
