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

package org.ayamemc.ayame.fabric.mixin.client.event;


//@Mixin(ItemInHandRenderer.class)
public abstract class RenderHandCustomEventMixin {
//    @Shadow
//    private float oMainHandHeight;
//    @Shadow
//    private float mainHandHeight;
//    @Shadow
//    private ItemStack mainHandItem;
//    @Shadow
//    private float oOffHandHeight;
//    @Shadow
//    private float offHandHeight;
//    @Shadow
//    private ItemStack offHandItem;
//
//    @Shadow
//    static ItemInHandRenderer.HandRenderSelection evaluateWhichHandsToRender(LocalPlayer player) {
//        return null;
//        // 这里return null实际上应该没什么问题，毕竟它只是个影子，真正被调用的不是它
//    }
//
//    @Inject(method = "renderHandsWithItems", at = @At("HEAD"), cancellable = true)
//    private void renderHandsWithItems(float partialTicks, PoseStack poseStack, MultiBufferSource.BufferSource buffer, LocalPlayer playerEntity, int combinedLight, CallbackInfo ci) {
//        float f = playerEntity.getAttackAnim(partialTicks);
//        InteractionHand interactionHand = playerEntity.swingingArm;
//        float pitch = Mth.lerp(partialTicks, playerEntity.xRotO, playerEntity.getXRot());
//        ItemInHandRenderer.HandRenderSelection handsToRender = evaluateWhichHandsToRender(playerEntity);
//        if (handsToRender == null) {
//            return;
//        }
//        if (handsToRender.renderMainHand) {
//            InteractionResult result = RenderArmCallback.ON_RENDER_ARM.invoker().onRenderArm(InteractionHand.MAIN_HAND, poseStack, buffer, combinedLight, partialTicks, pitch, interactionHand == InteractionHand.MAIN_HAND ? f : 0.0F, 1.0F - Mth.lerp(partialTicks, this.oMainHandHeight, this.mainHandHeight), this.mainHandItem, playerEntity);
//            if (result != InteractionResult.PASS) {
//                ci.cancel();
//                return;
//            }
//        }
//
//        if (handsToRender.renderOffHand) {
//            InteractionResult result = RenderArmCallback.ON_RENDER_ARM.invoker().onRenderArm(InteractionHand.OFF_HAND, poseStack, buffer, combinedLight, partialTicks, pitch, interactionHand == InteractionHand.OFF_HAND ? f : 0.0F, 1.0F - Mth.lerp(partialTicks, this.oOffHandHeight, this.offHandHeight), this.offHandItem, playerEntity);
//            if (result != InteractionResult.PASS) {
//                ci.cancel();
//            }
//            ci.cancel();
//        }
//    }
}
