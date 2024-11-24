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

package org.ayamemc.ayame.fabric.client.event;

import com.mojang.math.Axis;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.animal.Pig;

public class ClientTickEvent {
    // TODO 这里是示例方法，请完善
    public static void init(){
        HudRenderCallback.EVENT.register(ClientTickEvent::renderEntityOnHud);
    }


    private static void renderEntityOnHud(GuiGraphics guiGraphics, DeltaTracker tickDelta) {
        Minecraft client = Minecraft.getInstance();

        if (client.level == null || client.player == null) return;

        LivingEntity entity = new Pig(EntityType.PIG, client.level);

        // 设置实体的起始位置
        entity.setPos(client.player.getX(), client.player.getY(), client.player.getZ());

        // 渲染逻辑
        int x = client.getWindow().getGuiScaledWidth() - 50; // 右下角的X坐标
        int y = client.getWindow().getGuiScaledHeight() - 50; // 右下角的Y坐标
        renderEntityInGui(entity, x, y, 30); // 实体大小为30
    }

    private static void renderEntityInGui(LivingEntity entity, int x, int y, int size) {
        Minecraft client = Minecraft.getInstance();
        EntityRenderDispatcher dispatcher = client.getEntityRenderDispatcher();


        com.mojang.blaze3d.vertex.PoseStack poseStack = new com.mojang.blaze3d.vertex.PoseStack();
        poseStack.pushPose();
        poseStack.translate(x, y, 1050.0);
        poseStack.scale(size, size, size);
        poseStack.mulPose(Axis.ZP.rotationDegrees(180.0F));

        dispatcher.render(entity, 0.0, 0.0, 0.0, 0.0F, 1.0F, poseStack, client.renderBuffers().bufferSource(), 15728880);
        poseStack.popPose();
    }
}
