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

package org.ayamemc.ayame.client.renderer;

import net.minecraft.resources.ResourceLocation;
import org.ayamemc.ayame.Ayame;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.renderer.GeoObjectRenderer;

public class AyamePlayerHandRenderer extends GeoObjectRenderer<AyameHand> {

    public AyamePlayerHandRenderer() {
        super(new GeoHandModel());
    }

    @Override
    public long getInstanceId(AyameHand abstractClientPlayer) {

        return this.animatable.hashCode();
    }

    public static class GeoHandModel extends GeoModel<AyameHand> {

        @Override
        public ResourceLocation getModelResource(AyameHand animatable) {
            return Ayame.withAyamePath("geo/ayame/ayame_chan_hand.json");
        }

        @Override
        public ResourceLocation getTextureResource(AyameHand animatable) {
            return Ayame.withAyamePath("textures/ayame/ayame_chan.png");
        }

        @Override
        public ResourceLocation getAnimationResource(AyameHand animatable) {
            return Ayame.withAyamePath("animations/ayame/ayame_chan.json");
        }
    }
}
