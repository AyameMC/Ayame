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

package org.ayamemc.ayame.fabric.client.test;

import net.fabricmc.fabric.api.event.player.AttackEntityCallback;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.InteractionResult;
import org.ayamemc.ayame.client.script.JsEntity;
import org.ayamemc.ayame.client.script.JsPlayer;
import org.ayamemc.ayame.client.script.JsWorld;
import org.ayamemc.ayame.client.script.event.JsAttackEntityEvent;

/**
 * 一个便于测试的类,在发布前这个类应该是空的或者没有被调用
 */
public class AyameTest {
    public static void init(){
        AttackEntityCallback.EVENT.register((player, world, hand, entity, hitResult) -> {
            if (world.isClientSide()) {
                JsAttackEntityEvent.trigger(new JsPlayer(player), new JsWorld(world), new JsEntity(entity));
            }
            return InteractionResult.PASS;
        });
    }
}
