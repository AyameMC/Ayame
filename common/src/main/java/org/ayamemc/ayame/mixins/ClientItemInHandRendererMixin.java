package org.ayamemc.ayame.mixins;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.ItemInHandRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.world.entity.HumanoidArm;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ItemInHandRenderer.class)
public class ClientItemInHandRendererMixin {
    @Inject(method = "renderPlayerArm", at = @At("HEAD"), cancellable = true)
    private void renderPlayerArm(PoseStack poseStack, MultiBufferSource multiBufferSource, int i, float f, float g, HumanoidArm humanoidArm, CallbackInfo ci) {
        // TODO 重写物品在手中的逻辑
        ci.cancel();
    }
}