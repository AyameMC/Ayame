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

package org.ayamemc.ayame.client;

import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.KeyMapping;
import net.minecraft.network.chat.Component;
import org.ayamemc.ayame.client.handler.ClientEventHandler;
import org.ayamemc.ayame.client.script.JsPlayer;
import org.ayamemc.ayame.client.script.event.JsKeyPressEvent;
import org.ayamemc.ayame.util.TranslatableName;
import org.lwjgl.glfw.GLFW;

import static net.minecraft.client.Minecraft.getInstance;
import static org.ayamemc.ayame.client.AyameClient.keyMappingRegistry;

public class AyameKeyRegister {
    public static final KeyMapping MODEL_SELECT_MENU = keyMappingRegistry.registerKey(
            TranslatableName.SELECT_MODEL_MENU,
            ModifierKey.ALT,
            InputConstants.Type.KEYSYM,
            GLFW.GLFW_KEY_Y,
            TranslatableName.MOD_KEY_MENU_NAME
    );
    public static final KeyMapping CAMERA_Y_OFFSET_UP = keyMappingRegistry.registerKey(
            TranslatableName.CAMERA_Y_OFFSET_UP,
            ModifierKey.CONTROL,
            InputConstants.Type.KEYSYM,
            GLFW.GLFW_KEY_UP,
            TranslatableName.MOD_KEY_MENU_NAME
    );
    public static final KeyMapping CAMERA_Y_OFFSET_DOWN = keyMappingRegistry.registerKey(
            TranslatableName.CAMERA_Y_OFFSET_DOWN,
            ModifierKey.CONTROL,
            InputConstants.Type.KEYSYM,
            GLFW.GLFW_KEY_DOWN,
            TranslatableName.MOD_KEY_MENU_NAME
    );
    public static final KeyMapping CAMERA_Y_OFFSET_RESET = keyMappingRegistry.registerKey(
            TranslatableName.CAMERA_Y_OFFSET_RESET,
            ModifierKey.CONTROL,
            InputConstants.Type.KEYSYM,
            GLFW.GLFW_KEY_R,
            TranslatableName.MOD_KEY_MENU_NAME
    );

    public static final KeyMapping CUSTOM_KEY_0 = keyMappingRegistry.registerKey(
            TranslatableName.CUSTOM_KEY_0,
            ModifierKey.NONE,
            InputConstants.Type.KEYSYM,
            GLFW.GLFW_KEY_UNKNOWN,
            TranslatableName.MOD_KEY_MENU_NAME
    );

    public static final KeyMapping CUSTOM_KEY_1 = keyMappingRegistry.registerKey(
            TranslatableName.CUSTOM_KEY_1,
            ModifierKey.NONE,
            InputConstants.Type.KEYSYM,
            GLFW.GLFW_KEY_UNKNOWN,
            TranslatableName.MOD_KEY_MENU_NAME
    );
    public static final KeyMapping CUSTOM_KEY_2 = keyMappingRegistry.registerKey(
            TranslatableName.CUSTOM_KEY_2,
            ModifierKey.NONE,
            InputConstants.Type.KEYSYM,
            GLFW.GLFW_KEY_UNKNOWN,
            TranslatableName.MOD_KEY_MENU_NAME
    );
    public static final KeyMapping CUSTOM_KEY_3 = keyMappingRegistry.registerKey(
            TranslatableName.CUSTOM_KEY_3,
            ModifierKey.NONE,
            InputConstants.Type.KEYSYM,
            GLFW.GLFW_KEY_UNKNOWN,
            TranslatableName.MOD_KEY_MENU_NAME
    );
    public static final KeyMapping OPEN_ROULETTE = keyMappingRegistry.registerKey(
            TranslatableName.OPEN_ROULETTE,
            ModifierKey.NONE,
            InputConstants.Type.KEYSYM,
            GLFW.GLFW_KEY_X,
            TranslatableName.MOD_KEY_MENU_NAME
    );

    public static final KeyMapping ROULETTE_KEY = keyMappingRegistry.registerKey(
            TranslatableName.ANIMATION_ROULETTE,
            ModifierKey.NONE,
            InputConstants.Type.KEYSYM,
            GLFW.GLFW_KEY_C,
            TranslatableName.MOD_KEY_MENU_NAME
    );

    public static void processKeyPressed() {
        while (AyameKeyRegister.MODEL_SELECT_MENU.consumeClick()) {
            ClientEventHandler.openSelectMenuKeyPressed();
        }
        while (AyameKeyRegister.ROULETTE_KEY.consumeClick()) {
            ClientEventHandler.openRouletteScreen();
        }
        while (AyameKeyRegister.CAMERA_Y_OFFSET_UP.consumeClick()) {
            ClientEventHandler.plusCameraYOffset(0.01f, false);
        }
        while (AyameKeyRegister.CAMERA_Y_OFFSET_DOWN.consumeClick()) {
            ClientEventHandler.plusCameraYOffset(-0.01f, false);
        }
        while (AyameKeyRegister.CAMERA_Y_OFFSET_RESET.consumeClick()) {
            ClientEventHandler.plusCameraYOffset(0.0f, true);
        }
        while (CUSTOM_KEY_0.consumeClick()) {
            JsKeyPressEvent.triggerEvent(0, new JsPlayer(getInstance().player));
        }
        while (CUSTOM_KEY_1.consumeClick()) {
            JsKeyPressEvent.triggerEvent(1, new JsPlayer(getInstance().player));
        }
        while (CUSTOM_KEY_2.consumeClick()) {
            JsKeyPressEvent.triggerEvent(2, new JsPlayer(getInstance().player));
        }
        while (CUSTOM_KEY_3.consumeClick()) {
            JsKeyPressEvent.triggerEvent(3, new JsPlayer(getInstance().player));
        }
        while (OPEN_ROULETTE.consumeClick()) {
            ClientEventHandler.openRouletteScreen();
        }

    }
}
