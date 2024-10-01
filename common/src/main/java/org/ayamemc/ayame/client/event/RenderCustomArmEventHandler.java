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

package org.ayamemc.ayame.client.event;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.item.ItemStack;

public class RenderCustomArmEventHandler {
    public static void renderCustomArmEventHandler(
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
        // 渲染自定义手臂逻辑在这里添加
        //Ayame.LOGGER.warn("render arm!\n hand={} poseStack={} multiBufferSource={} packedLight={} partialTick={} interpolatedPitch={} swingProgress={} equipProgress={} stack={}", hand, poseStack, multiBufferSource, packedLight, partialTick, interpolatedPitch, swingProgress, equipProgress, stack);
    }
}
