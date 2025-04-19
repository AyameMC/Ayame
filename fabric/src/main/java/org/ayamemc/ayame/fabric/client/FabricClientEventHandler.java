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

import net.fabricmc.fabric.api.client.command.v2.ClientCommandRegistrationCallback;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientEntityEvents;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderEvents;
import net.fabricmc.fabric.api.event.player.AttackEntityCallback;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import org.ayamemc.ayame.client.AyameKeyRegister;
import org.ayamemc.ayame.client.handler.ClientEventHandler;


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
        ClientEntityEvents.ENTITY_LOAD.register(FabricClientEventHandler::joinWorld);
        ClientEntityEvents.ENTITY_UNLOAD.register(FabricClientEventHandler::quitWorld);
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

    private static void quitWorld(Entity entity, ClientLevel clientLevel) {
        if (entity instanceof Player && clientLevel.isClientSide()) {
            ClientEventHandler.quiltWorld();
        }

    }

    private static void joinWorld(Entity entity, ClientLevel clientLevel) {
        if (entity instanceof Player && clientLevel.isClientSide()) {
            ClientEventHandler.johnWorld();
        }
    }


    private static void endClientTickEvent(Minecraft minecraft) {
        AyameKeyRegister.processKeyPressed();
    }

}
