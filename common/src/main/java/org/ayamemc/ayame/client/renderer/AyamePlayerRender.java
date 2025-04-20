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
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.ItemInHandRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import org.ayamemc.ayame.client.AyameClient;
import org.ayamemc.ayame.client.yttribume.Yttribumes;
import org.ayamemc.ayame.model.AyameMolangVars;
import org.ayamemc.ayame.model.sync.ModelSelection;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.joml.Matrix4f;
import software.bernie.geckolib.animation.AnimationState;
import software.bernie.geckolib.cache.object.BakedGeoModel;
import software.bernie.geckolib.cache.object.GeoBone;
import software.bernie.geckolib.loading.math.MathParser;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

import java.util.Optional;
import java.util.UUID;

import static org.ayamemc.ayame.Ayame.LOGGER;


public class AyamePlayerRender extends GeoEntityRenderer<Player> {
    private static final int BOOT_SLOT = 0;
    private static final int LEGGINGS_SLOT = 1;
    private static final int CHEST_PLATE_SLOT = 2;
    private static final int HELMET_SLOT = 3;
    private final AyamePlayerHandRenderer handRenderer = new AyamePlayerHandRenderer();
    private final ItemInHandRenderer itemInHandRenderer;

    // TODO : 完善代码 & 添加API
    public AyamePlayerRender(EntityRendererProvider.Context context) {
        super(context, new GeoPlayerModel());
        this.itemInHandRenderer = context.getItemInHandRenderer();
    }

    @Override
    public void preRender(PoseStack poseStack, Player animatable, BakedGeoModel model, @Nullable MultiBufferSource bufferSource, @Nullable VertexConsumer buffer, boolean isReRender, float partialTick, int packedLight, int packedOverlay, int colour) {
//        poseStack.pushPose();
        super.preRender(poseStack, animatable, model, bufferSource, buffer, isReRender, partialTick, packedLight, packedOverlay, colour);
        // 实体缩放
        float scale = animatable.ayame$getYttribume(Yttribumes.MODEL_SCALE);

        poseStack.scale(scale, scale, scale);
//        poseStack.popPose();
    }

    @Override
    public void actuallyRender(PoseStack poseStack, Player player, BakedGeoModel model, @Nullable RenderType renderType,
                               @NotNull MultiBufferSource bufferSource, @Nullable VertexConsumer buffer, boolean isReRender,
                               float partialTick, int packedLight, int packedOverlay, int colour) {
        // 先渲染玩家模型
        RenderType translucentRenderType = RenderType.entityTranslucent(getTextureLocation(player));
        VertexConsumer translucentBuffer = bufferSource.getBuffer(translucentRenderType);

        int a = (int) (player.ayame$getYttribume(Yttribumes.MODEL_ALPHA) * 255);
        int modifiedColour = (a << 24) | (colour & 0x00FFFFFF);

        super.actuallyRender(poseStack, player, model, translucentRenderType, bufferSource, translucentBuffer,
                isReRender, partialTick, packedLight, packedOverlay, modifiedColour);

        // 渲染手持物品
        renderHeldItems(poseStack, bufferSource, packedLight, player, partialTick);
    }


    private void renderHeldItems(PoseStack poseStack, MultiBufferSource bufferSource, int packedLight,
                                 Player player, float partialTick) {

        renderArmWithItem(
                player,
                player.getMainHandItem(),
                ItemDisplayContext.THIRD_PERSON_RIGHT_HAND,
                HumanoidArm.RIGHT,
                poseStack,
                bufferSource,
                packedLight
        );


        renderArmWithItem(
                player,
                player.getOffhandItem(),
                ItemDisplayContext.THIRD_PERSON_LEFT_HAND,
                HumanoidArm.LEFT,
                poseStack,
                bufferSource,
                packedLight
        );
    }

    protected void renderArmWithItem(
            LivingEntity entity,
            ItemStack itemStack,
            ItemDisplayContext displayContext,
            HumanoidArm arm,
            PoseStack poseStack,
            MultiBufferSource buffer,
            int packedLight
    ) {
        if (!itemStack.isEmpty()) {
            poseStack.pushPose();

            // 获取手部骨骼并应用变换
            Optional<GeoBone> handBone = getGeoModel().getBone(
                    arm == HumanoidArm.RIGHT ? "RightHand" : "LeftHand"
            );

            if (handBone.isPresent()) {
                // 应用骨骼的模型空间变换
                Matrix4f boneMatrix = handBone.get().getModelSpaceMatrix();
                poseStack.last().pose().mul(boneMatrix);
            }

            // 调整物品方向
//            poseStack.mulPose(Axis.XP.rotationDegrees(-90.0F));
//            poseStack.mulPose(Axis.YP.rotationDegrees(180.0F));

            // 调整位置偏移
            boolean isLeft = arm == HumanoidArm.LEFT;
//            poseStack.translate((isLeft ? -1 : 1) / 16.0F, 0.125F, -0.625F);

            // 渲染物品
            itemInHandRenderer.renderItem(
                    entity,
                    itemStack,
                    displayContext,
                    isLeft,
                    poseStack,
                    buffer,
                    packedLight
            );

            poseStack.popPose();
        }
    }

    public void renderRightHand(PoseStack poseStack, MultiBufferSource buffer, int packedLight, AbstractClientPlayer player) {
        handRenderer.render(poseStack, new AyameHand(), buffer, null, null, packedLight, 0);
    }

    public void renderLeftHand(PoseStack poseStack, MultiBufferSource buffer, int packedLight, Player player) {
        // X 轴镜像
        poseStack.pushPose();
        poseStack.scale(-1.0F, 1.0F, 1.0F);
        handRenderer.render(poseStack, new AyameHand(), buffer, null, null, packedLight, 0);
        poseStack.popPose();
    }

    protected static class GeoPlayerModel extends GeoModel<Player> {
        private ModelSelection getPlayerModelSelectionOrFallback(@NotNull Player player) {
            final UUID playerUUID = player.getUUID();

            ModelSelection ret = AyameClient.modelManagerClient.getModelOfPlayer(playerUUID);

            final String selectedModelId = ret.getId();

            // 如果没有这个模型, 或者没有加载完成
            if (!AyameClient.modelManagerClient.hasModel(selectedModelId)) {
                ret = AyameClient.modelManagerClient.getDefaultModelFallback(); // 落回默认模型

                return ret;
            }

            return ret;
        }

        @Override
        public void applyMolangQueries(@NotNull AnimationState<Player> animationState, double animTime) {
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

            for (ResourceLocation id : Yttribumes.getIds()) {
                MathParser.setVariable(id.getNamespace() + ".yttribume." + id.getPath(), () -> player.ayame$getYttribume(Yttribumes.get(id)));
            }
        }

        @Override
        public ResourceLocation getModelResource(Player animatable) {
            return this.getPlayerModelSelectionOrFallback(animatable).getGeoModel();
        }

        @Override
        public ResourceLocation getTextureResource(Player animatable) {
            return this.getPlayerModelSelectionOrFallback(animatable).getTexture();
        }

        @Override
        public ResourceLocation getAnimationResource(Player animatable) {
            return this.getPlayerModelSelectionOrFallback(animatable).getAnimation();
        }
    }

}