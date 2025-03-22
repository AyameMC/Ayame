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

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import org.ayamemc.ayame.client.AyameClient;
import org.ayamemc.ayame.client.AyameKeyRegister;
import org.ayamemc.ayame.fabric.client.impl.FabricKeyMappingRegistryImpl;

/**
 * Fabric客户端初始化所使用的类
 *
 * @see ClientModInitializer
 */

public final class AyameFabricClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        AyameClient.init(new FabricKeyMappingRegistryImpl());
        FabricClientEventHandler.init();

        initKeyBinding();
    }

    private void initKeyBinding() {
        KeyBindingHelper.registerKeyBinding(AyameKeyRegister.MODEL_SELECT_MENU);
        KeyBindingHelper.registerKeyBinding(AyameKeyRegister.CAMERA_Y_OFFSET_UP);
        KeyBindingHelper.registerKeyBinding(AyameKeyRegister.CAMERA_Y_OFFSET_DOWN);
        KeyBindingHelper.registerKeyBinding(AyameKeyRegister.CAMERA_Y_OFFSET_RESET);
        KeyBindingHelper.registerKeyBinding(AyameKeyRegister.CUSTOM_KEY_0);
        KeyBindingHelper.registerKeyBinding(AyameKeyRegister.CUSTOM_KEY_1);
        KeyBindingHelper.registerKeyBinding(AyameKeyRegister.CUSTOM_KEY_2);
        KeyBindingHelper.registerKeyBinding(AyameKeyRegister.CUSTOM_KEY_3);
        KeyBindingHelper.registerKeyBinding(AyameKeyRegister.OPEN_ROULETTE);
        KeyBindingHelper.registerKeyBinding(AyameKeyRegister.ROULETTE_KEY);

    }
}
