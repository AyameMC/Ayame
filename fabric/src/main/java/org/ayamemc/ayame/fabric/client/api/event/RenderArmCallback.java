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

package org.ayamemc.ayame.fabric.client.api.event;

import com.mojang.blaze3d.vertex.PoseStack;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

/**
 * 在以第一人称渲染玩家手臂之前触发。为代替NeoForge提供的 {@code RenderArmEvent} 而生（因为Fabric Api没给对应的事件），可用于替换玩家手臂的渲染
 * <p>例如在手臂上渲染盔甲或直接将手臂替换为盔甲。
 */
@Environment(EnvType.CLIENT)
public class RenderArmCallback {
    public static final Event<OnRenderArm> ON_RENDER_ARM = EventFactory.createArrayBacked(OnRenderArm.class,
            (listeners) -> (hand, poseStack, multiBufferSource, packedLight, partialTick, interpolatedPitch, swingProgress, equipProgress, stack,player) -> {
                for (OnRenderArm listener : listeners) {
                    InteractionResult result = listener.onRenderArm(
                            hand,
                            poseStack,
                            multiBufferSource,
                            packedLight,
                            partialTick,
                            interpolatedPitch,
                            swingProgress,
                            equipProgress,
                            stack,
                            player
                    );
                    if (result != InteractionResult.PASS) return result;
                }
                return InteractionResult.PASS;
            });


    public interface OnRenderArm {

        /**
         * 渲染玩家手之前调用
         *
         * @param hand              正在渲染的手
         * @param poseStack         用于渲染的姿势堆栈
         * @param multiBufferSource 渲染缓冲区的来源
         * @param packedLight       用于渲染的压缩（天空和方块）光量
         * @param partialTick       Partial Tick
         * @param interpolatedPitch 玩家实体的插值音高
         * @param swingProgress     正在渲染的手牌的挥动进度
         * @param equipProgress     装备动画的进度，从 { 0.0} 到 { 1.0}
         * @param stack             要渲染的物品组
         * @param player             玩家实体
         */
        InteractionResult onRenderArm(
                InteractionHand hand,
                PoseStack poseStack,
                MultiBufferSource multiBufferSource,
                int packedLight,
                float partialTick,
                float interpolatedPitch,
                float swingProgress,
                float equipProgress,
                ItemStack stack,
                LocalPlayer player
        );
    }
}
