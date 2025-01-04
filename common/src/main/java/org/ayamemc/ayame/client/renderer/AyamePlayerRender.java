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

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import org.ayamemc.ayame.client.yttribume.Yttribumes;
import org.ayamemc.ayame.model.AyameModelCache;
import org.ayamemc.ayame.model.AyameMolangVars;
import org.ayamemc.ayame.model.ModelType;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.animation.AnimationState;
import software.bernie.geckolib.cache.object.BakedGeoModel;
import software.bernie.geckolib.loading.math.MathParser;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.renderer.GeoEntityRenderer;


public class AyamePlayerRender extends GeoEntityRenderer<Player> {
    private static final int BOOT_SLOT = 0;
    private static final int LEGGINGS_SLOT = 1;
    private static final int CHEST_PLATE_SLOT = 2;
    private static final int HELMET_SLOT = 3;


    // TODO : 完善代码 & 添加API
    public AyamePlayerRender(EntityRendererProvider.Context context) {
        super(context, new GeoPlayerModel());
    }

    @Override
    public void preRender(PoseStack poseStack, Player animatable, BakedGeoModel model, @Nullable MultiBufferSource bufferSource, @Nullable VertexConsumer buffer, boolean isReRender, float partialTick, int packedLight, int packedOverlay, int colour) {
        super.preRender(poseStack, animatable, model, bufferSource, buffer, isReRender, partialTick, packedLight, packedOverlay, colour);
        // 实体缩放
        float scale = animatable.ayame$getYttribume(Yttribumes.MODEL_RENDER_SCALE);
        poseStack.scale(scale, scale, scale);

        // 坐下时向下移动
        if (animatable.ayame$isSitting()) {
            poseStack.translate(0, -0.7, 0);
        }
        // 游泳/爬行时向下移动
        if (animatable.getPose() == Pose.SWIMMING) {
            poseStack.translate(0, -0.5, 0);
        }
    }

    @Override
    public void actuallyRender(PoseStack poseStack, Player player, BakedGeoModel model, @Nullable RenderType renderType,
                               MultiBufferSource bufferSource, @Nullable VertexConsumer buffer, boolean isReRender, float partialTick,
                               int packedLight, int packedOverlay, int colour) {
        RenderType translucentRenderType = RenderType.entityTranslucent(getTextureLocation(player));
        VertexConsumer translucentBuffer = bufferSource.getBuffer(translucentRenderType);
        super.actuallyRender(poseStack, player, model, translucentRenderType, bufferSource, translucentBuffer, isReRender, partialTick, packedLight, packedOverlay, colour);
    }


    // TODO : 添加API
    public static class GeoPlayerModel extends GeoModel<Player> {

        public GeoPlayerModel() {
        }

        /**
         * 将玩家模型切换为对应外观，TODO: 同时告诉服务器
         *
         * @param model 传入{@link ModelType}类型的模型资源
         */
        public static void switchModel(Player player, ModelType model) {
            AyameModelCache.setPlayerModel(player, model);
        }

        @Override
        public void applyMolangQueries(AnimationState<Player> animationState, double animTime) {
            final Player player = animationState.getAnimatable();
            MathParser.setVariable(
                    AyameMolangVars.HAS_MAINHAND, () -> player.getMainHandItem() != ItemStack.EMPTY ? 0 : 1
            );
            MathParser.setVariable(
                    AyameMolangVars.HAS_OFFHAND, () -> player.getOffhandItem() != ItemStack.EMPTY ? 0 : 1
            );
            MathParser.setVariable(
                    AyameMolangVars.IS_RIPTIDE, () -> player.getOffhandItem() != ItemStack.EMPTY ? 0 : 1
            );
            MathParser.setVariable(AyameMolangVars.HAS_BOOTS, () ->
                    // 玩家是否穿鞋
                    player.getInventory().getArmor(BOOT_SLOT).isEmpty() ? 0 : 1
            );

            MathParser.setVariable(AyameMolangVars.HAS_LEGGINGS, () ->
                    // 玩家是否穿裤子
                    player.getInventory().getArmor(LEGGINGS_SLOT).isEmpty() ? 0 : 1
            );

            MathParser.setVariable(AyameMolangVars.HAS_CHEST_PLATE, () ->
                    // 玩家是否穿胸甲
                    player.getInventory().getArmor(CHEST_PLATE_SLOT).isEmpty() ? 0 : 1
            );

            MathParser.setVariable(AyameMolangVars.HAS_HELMET, () ->
                    // 玩家是否穿头盔
                    player.getInventory().getArmor(HELMET_SLOT).isEmpty() ? 0 : 1
            );
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