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

package org.ayamemc.ayame.neoforge.client;


import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModLoadingContext;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.client.event.RegisterKeyMappingsEvent;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;
import net.neoforged.neoforge.common.NeoForge;
import org.ayamemc.ayame.Ayame;
import org.ayamemc.ayame.client.AyameClient;
import org.ayamemc.ayame.client.AyameKeyRegister;
import org.ayamemc.ayame.client.IAyameClientEvents;
import org.ayamemc.ayame.client.gui.screen.SettingsScreen;
import org.ayamemc.ayame.neoforge.AyameNeoForgeSounds;
import org.ayamemc.ayame.neoforge.client.event.NeoForgeClientEventHandler;
import org.ayamemc.ayame.neoforge.client.impl.AyameClientEventsNeoForgeImpl;
import org.ayamemc.ayame.neoforge.client.impl.NeoForgeKeyMappingRegistryImpl;
import org.ayamemc.ayame.util.ModLoader;

@Mod(value = Ayame.MOD_ID, dist = Dist.CLIENT)
public class AyameNeoForgeClient {
    public AyameNeoForgeClient(IEventBus modBus) {
        AyameNeoForgeSounds.SOUND_EVENTS.register(modBus);

        AyameClient.init(new NeoForgeKeyMappingRegistryImpl(), ModLoader.NEOFORGE, null);

        NeoForge.EVENT_BUS.register(NeoForgeClientEventHandler.class);

        modBus.addListener(RegisterKeyMappingsEvent.class, event -> {
            event.register(AyameKeyRegister.MODEL_SELECT_MENU);
            event.register(AyameKeyRegister.CAMERA_Y_OFFSET_UP);
            event.register(AyameKeyRegister.CAMERA_Y_OFFSET_DOWN);
            event.register(AyameKeyRegister.CAMERA_Y_OFFSET_RESET);
            event.register(AyameKeyRegister.CUSTOM_KEY_0);
            event.register(AyameKeyRegister.CUSTOM_KEY_1);
            event.register(AyameKeyRegister.CUSTOM_KEY_2);
            event.register(AyameKeyRegister.CUSTOM_KEY_3);
            event.register(AyameKeyRegister.OPEN_ROULETTE);
            event.register(AyameKeyRegister.ROULETTE_KEY);
        });

        IAyameClientEvents.Instance.INSTANCE = new AyameClientEventsNeoForgeImpl();

        ModLoadingContext.get().registerExtensionPoint(
                IConfigScreenFactory.class,
                () -> (modContainer, lastScreen) -> new SettingsScreen(lastScreen)
        );

    }


}
