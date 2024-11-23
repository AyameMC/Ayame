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
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ItemInHandRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.item.ItemStack;
import org.ayamemc.ayame.client.gui.screen.AyameScreen;
import org.ayamemc.ayame.client.gui.screen.ModelSelectMenuScreen;
import org.ayamemc.ayame.client.renderer.AyamePlayerHandRenderer;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.renderer.GeoItemRenderer;

public class ClientEventHandler {
    public static final int TOOLTIP_BACKGROUND_COLOR = 0xCC_5f5f5f;
    public static final int TOOLTIP_BORDER_TOP_COLOR = 0xCC_fdc7f5;
    public static final int TOOLTIP_BORDER_BOTTOM_COLOR = 0xCC_fde8f5;
    private final static Minecraft minecraft = Minecraft.getInstance();

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
        AyamePlayerHandRenderer renderer = new AyamePlayerHandRenderer(null);
        renderer.render(poseStack, null, multiBufferSource, null, (VertexConsumer) multiBufferSource, packedLight, partialTick);
    }

    public static void openSelectMenuKeyPressed() {
        minecraft.setScreen(new ModelSelectMenuScreen(null));
    }
}
