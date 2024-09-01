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

package org.ayamemc.ayame.mixin;

import com.google.common.collect.ImmutableMap;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraft.client.resources.PlayerSkin;
import net.minecraft.world.entity.player.Player;
import org.ayamemc.ayame.client.model.DefaultModels;
import org.ayamemc.ayame.client.renderer.GeoPlayerRender;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.HashMap;
import java.util.Map;


/**
 * 使用自定义模组取代默认的史蒂夫
 */
@Environment(EnvType.CLIENT)
@Mixin(EntityRenderers.class)
public class ClientEntityRenderersMixin {
    @Inject(method = "createPlayerRenderers", at = @At("RETURN"), cancellable = true)
    private static void createPlayerRenderers(EntityRendererProvider.Context context, CallbackInfoReturnable<Map<PlayerSkin.Model, EntityRenderer<? extends Player>>> cir) {
        Map<PlayerSkin.Model, EntityRenderer<? extends Player>> m = new HashMap<>(Map.of(PlayerSkin.Model.WIDE, new GeoPlayerRender(context, new GeoPlayerRender.GeoPlayerModel(DefaultModels.TEST_MODEL))));
        m.put(PlayerSkin.Model.SLIM, new GeoPlayerRender(context, new GeoPlayerRender.GeoPlayerModel(DefaultModels.TEST_MODEL)));
        cir.setReturnValue(ImmutableMap.copyOf(m));
    }
}