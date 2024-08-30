/*
 *      Custom player model mod. Based on GeckoLib.
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

package org.ayamemc.ayame.neoforge.client.events;

import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.ClientTickEvent;
import org.ayamemc.ayame.Ayame;
import org.ayamemc.ayame.client.screen.ModelSelectMenuScreen;

@EventBusSubscriber(modid = Ayame.MOD_ID, value = Dist.CLIENT)
public class OpenModelSelectMenuEvent {
    @SubscribeEvent
    public static void onClientClick(ClientTickEvent.Post event) {
        while (RegisterKeyMapping.MODEL_SELECT_MENU.get().consumeClick()) {
            Minecraft.getInstance().setScreen(new ModelSelectMenuScreen(Component.empty(), Minecraft.getInstance().screen));
        }
    }
}
