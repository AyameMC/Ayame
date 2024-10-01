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

package org.ayamemc.ayame.fabric.client;

import com.mojang.blaze3d.vertex.PoseStack;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayConnectionEvents;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.ItemStack;
import org.ayamemc.ayame.client.event.RenderCustomArmEventHandler;
import org.ayamemc.ayame.fabric.client.event.AyameKeyMappingEventHandler;
import org.ayamemc.ayame.fabric.client.api.event.RenderArmCallback;
import org.ayamemc.ayame.util.TaskManager;


/**
 * Fabric客户端初始化时用于注册事件的类
 *
 * @see AyameFabricClient
 */
@Environment(EnvType.CLIENT)
public class AyameFabricClientEvents {
    /**
     * 注册Fabric事件
     */
    public static void init() {
        ClientTickEvents.END_CLIENT_TICK.register(AyameFabricClientEvents::endClientTickEvent);
        ClientPlayConnectionEvents.JOIN.register(AyameFabricClientEvents::joinServer);
        ClientPlayConnectionEvents.DISCONNECT.register(AyameFabricClientEvents::quitServer);
        RenderArmCallback.ON_RENDER_ARM.register(AyameFabricClientEvents::renderCustomArm);
    }

    private static void quitServer(ClientPacketListener clientPacketListener, Minecraft minecraft) {
        // 停止执行玩家进入世界的任务
        TaskManager.TaskManagerImpls.CLIENT_IN_WORLD_TASKS.setCanExecute(false);
    }

    private static void joinServer(ClientPacketListener clientPacketListener, PacketSender packetSender, Minecraft minecraft) {
        // 执行玩家进入世界的任务
        TaskManager.TaskManagerImpls.CLIENT_IN_WORLD_TASKS.setCanExecute(true);
        TaskManager.TaskManagerImpls.CLIENT_IN_WORLD_TASKS.executeAll();
//        // 开新线程扫描模型
//        new Thread(ModelScanner::scanModel).start();
    }

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
     */
    private static InteractionResult renderCustomArm(
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
    ) {
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
        return InteractionResult.SUCCESS;
    }

    private static void endClientTickEvent(Minecraft minecraft) {
        AyameKeyMappingEventHandler.processKeyPressed();
    }

}
