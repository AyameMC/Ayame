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

package org.ayamemc.ayame.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import org.ayamemc.ayame.model.AyameModelCache;
import org.ayamemc.ayame.model.AyameModelType;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.cache.object.BakedGeoModel;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

@Environment(EnvType.CLIENT)
public class GeoPlayerRender extends GeoEntityRenderer<Player> {

    // TODO : 完善代码 & 添加API
    public GeoPlayerRender(EntityRendererProvider.Context context) {
        super(context, new GeoPlayerModel());
    }

    @Override
    public void preRender(PoseStack poseStack, Player animatable, BakedGeoModel model, @Nullable MultiBufferSource bufferSource, @Nullable VertexConsumer buffer, boolean isReRender, float partialTick, int packedLight, int packedOverlay, int colour) {
        super.preRender(poseStack, animatable, model, bufferSource, buffer, isReRender, partialTick, packedLight, packedOverlay, colour);
    }


    // TODO : 添加API
    public static class GeoPlayerModel extends GeoModel<Player> {

        public GeoPlayerModel() {}


        /**
         * 将玩家模型切换为对应外观，TODO: 同时告诉服务器
         * @param model 传入{@link AyameModelType}类型的模型资源
         */
        public static void switchModel(Player player,AyameModelType model){
            AyameModelCache.addPlayerModel(player,model);
        }

        @Override
        public ResourceLocation getModelResource(Player animatable) {
            return AyameModelCache.getPlayerModel(animatable).getGeoModel();
        }

        @Override
        public ResourceLocation getTextureResource(Player animatable) {
            return AyameModelCache.getPlayerModel(animatable).getTexture();
        }

        @Override
        public ResourceLocation getAnimationResource(Player animatable) {
            return AyameModelCache.getPlayerModel(animatable).getAnimation();
        }

    }
}