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

package org.ayamemc.ayame.neoforge.client.event;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.item.ItemStack;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RenderHandEvent;
import org.ayamemc.ayame.Ayame;
import org.ayamemc.ayame.client.event.RenderCustomArmEventHandler;


@OnlyIn(Dist.CLIENT)
@EventBusSubscriber(modid = Ayame.MOD_ID, value = Dist.CLIENT)
public class RenderCustomModelArmEventHandler {
    @SubscribeEvent
    public static void renderCustomModelArm(RenderHandEvent event) {
        event.setCanceled(true); // 取消渲染默认手臂
        InteractionHand hand = event.getHand();
        PoseStack poseStack = event.getPoseStack();
        MultiBufferSource multiBufferSource = event.getMultiBufferSource();
        int packedLight = event.getPackedLight();
        float partialTick = event.getPartialTick();
        float interpolatedPitch = event.getInterpolatedPitch();
        float swingProgress = event.getSwingProgress();
        float equipProgress = event.getEquipProgress();
        ItemStack stack = event.getItemStack();
        RenderCustomArmEventHandler.renderCustomArmEventHandler(
                hand,
                poseStack,
                multiBufferSource,
                packedLight,
                partialTick,
                interpolatedPitch,
                swingProgress,
                equipProgress,
                stack
        );
    }
}
