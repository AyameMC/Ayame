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

package org.ayamemc.ayame.fabric.client;

import com.mojang.blaze3d.vertex.PoseStack;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandRegistrationCallback;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayConnectionEvents;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderEvents;
import net.fabricmc.fabric.api.event.player.AttackEntityCallback;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.ItemStack;
import org.ayamemc.ayame.client.AyameKeyRegister;
import org.ayamemc.ayame.client.AyameClient;
import org.ayamemc.ayame.client.handler.ClientEventHandler;
import org.ayamemc.ayame.util.TaskManager;


/**
 * Fabric客户端初始化时用于注册事件的类
 *
 * @see AyameFabricClient
 */

public class FabricClientEventHandler {
    /**
     * 注册Fabric事件
     */
    public static void init() {
        ClientTickEvents.START_CLIENT_TICK.register(ClientEventHandler::tick);
        ClientTickEvents.END_CLIENT_TICK.register(FabricClientEventHandler::endClientTickEvent);
        ClientPlayConnectionEvents.JOIN.register(FabricClientEventHandler::joinServer);
        ClientPlayConnectionEvents.DISCONNECT.register(FabricClientEventHandler::quitServer);
        ClientCommandRegistrationCallback.EVENT.register(ClientEventHandler::registerClientCommands);
        HudRenderCallback.EVENT.register(ClientEventHandler::renderHud);
        WorldRenderEvents.START.register((context) -> ClientEventHandler.renderCamera());
        AttackEntityCallback.EVENT.register((player, world, hand, entity, hitResult) -> {
            if (world.isClientSide()) {
                ClientEventHandler.attackEntity(player, world, hand, entity);
            }
            return InteractionResult.PASS;
        });
    }


    private static void quitServer(ClientPacketListener clientPacketListener, Minecraft minecraft) {
        // 停止执行玩家进入世界的任务
        TaskManager.TaskManagerImpls.CLIENT_IN_WORLD_TASKS.setCanExecute(false);

        AyameClient.unloadAllModels();
    }

    private static void joinServer(ClientPacketListener clientPacketListener, PacketSender packetSender, Minecraft minecraft) {
        // 执行玩家进入世界的任务
        TaskManager.TaskManagerImpls.CLIENT_IN_WORLD_TASKS.setCanExecute(true);
        TaskManager.TaskManagerImpls.CLIENT_IN_WORLD_TASKS.executeAll();

        if (minecraft.isLocalServer()) {
            AyameClient.loadAllModelLocal().join();
            return;
        }

        AyameClient.requestServerSync();
    }


    private static void endClientTickEvent(Minecraft minecraft) {
        AyameKeyRegister.processKeyPressed();
    }

}
