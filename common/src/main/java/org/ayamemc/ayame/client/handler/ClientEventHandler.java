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

package org.ayamemc.ayame.client.handler;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.animal.Pig;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import org.ayamemc.ayame.client.gui.screen.AyameScreen;
import org.ayamemc.ayame.client.gui.screen.ModelSelectMenuScreen;
import org.ayamemc.ayame.model.AyameModelCache;
import org.ayamemc.ayame.model.ModelType;

public class ClientEventHandler {
    public static final int TOOLTIP_BACKGROUND_COLOR = 0xCC_5f5f5f;
    public static final int TOOLTIP_BORDER_TOP_COLOR = 0xCC_fdc7f5;
    public static final int TOOLTIP_BORDER_BOTTOM_COLOR = 0xCC_fde8f5;
    private final static Minecraft minecraft = Minecraft.getInstance();
    private final static Player player = minecraft.player;

    public static boolean shouldUseAyameTooltipColor() {
        return minecraft.screen instanceof AyameScreen;
    }

    public static void renderCustomHandEventHandler(
            InteractionHand hand,
            PoseStack poseStack,
            MultiBufferSource multiBufferSource,
            int packedLight,
            float partialTick,
            float interpolatedPitch,
            float swingProgress,
            float equipProgress,
            ItemStack stack
    ) {
        //AyamePlayerHandRenderer renderer = new AyamePlayerHandRenderer(null);
        //renderer.render(poseStack, null, multiBufferSource, null, (VertexConsumer) multiBufferSource, packedLight, partialTick);
    }

    public static void openSelectMenuKeyPressed() {
        minecraft.setScreen(new ModelSelectMenuScreen(null));
    }

    public static void renderCustomHandInHud(GuiGraphics guiGraphics, DeltaTracker tickDelta) {

        if (minecraft.level == null || player == null) return;

        ModelType model = AyameModelCache.getPlayerModel(player)

        LivingEntity entity = new Pig(EntityType.PIG, minecraft.level);

        // 设置实体的起始位置
        entity.setPos(player.getX(), player.getY(), player.getZ());

        // 渲染逻辑
        int x = minecraft.getWindow().getGuiScaledWidth() - 50; // 右下角的X坐标
        int y = minecraft.getWindow().getGuiScaledHeight() - 50; // 右下角的Y坐标
        renderEntityInGui(entity, x, y, 30); // 实体大小为30
    }

    private static void renderEntityInGui(LivingEntity entity, int x, int y, int size) {
        EntityRenderDispatcher dispatcher = minecraft.getEntityRenderDispatcher();


        PoseStack poseStack = new PoseStack();
        poseStack.pushPose();
        poseStack.translate(x, y, 1050.0);
        poseStack.scale(size, size, size);
        poseStack.mulPose(Axis.ZP.rotationDegrees(180.0F));

        dispatcher.render(entity, 0.0, 0.0, 0.0, 0.0F, 1.0F, poseStack, minecraft.renderBuffers().bufferSource(), 15728880);
        poseStack.popPose();
    }
}
