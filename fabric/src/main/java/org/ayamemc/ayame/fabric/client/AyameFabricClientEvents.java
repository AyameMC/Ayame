/*
 *      Custom player model mod. Powered by GeckoLib.
 *      Copyright (C) 2024  CrystalNeko, HappyRespawnanchor, pertaz(Icon Desiger)
 *
 *      This file is part of Ayame.
 *
 *     Ayame is free software: you can redistribute it and/or modify it under the terms of the GNU Lesser General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.
 *
 *     Ayame is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more details.
 *
 *     You should have received a copy of the GNU Lesser General Public License along with Ayame. If not, see <https://www.gnu.org/licenses/>.
 */

package org.ayamemc.ayame.fabric.client;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayConnectionEvents;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientPacketListener;
import org.ayamemc.ayame.fabric.client.msic.AyameKeyMappings;
import org.ayamemc.ayame.util.TaskManager;


/**
 * Fabric客户端初始化时用于注册事件的类
 * @see AyameFabricClient
 */
@Environment(EnvType.CLIENT)
public class AyameFabricClientEvents {
    /**
     * 注册Fabric事件
     */
    public static void init() {
        ClientTickEvents.END_CLIENT_TICK.register(AyameFabricClientEvents::endClientTickEvent);
        ClientPlayConnectionEvents.JOIN.register((AyameFabricClientEvents::joinServer));
        ClientPlayConnectionEvents.DISCONNECT.register((AyameFabricClientEvents::quitServer));
    }

    private static void quitServer(ClientPacketListener clientPacketListener, Minecraft minecraft) {
        // 停止执行玩家进入世界的任务
        TaskManager.TaskManagerImpls.CLIENT_IN_WORLD_TASKS.setCanExecute(false);
    }

    private static void joinServer(ClientPacketListener clientPacketListener, PacketSender packetSender, Minecraft minecraft) {
        // 执行玩家进入世界的任务
        TaskManager.TaskManagerImpls.CLIENT_IN_WORLD_TASKS.setCanExecute(true);
        TaskManager.TaskManagerImpls.CLIENT_IN_WORLD_TASKS.executeAll();
    }

    private static void endClientTickEvent(Minecraft minecraft) {
        AyameKeyMappings.processKeyPressed();
    }
}
