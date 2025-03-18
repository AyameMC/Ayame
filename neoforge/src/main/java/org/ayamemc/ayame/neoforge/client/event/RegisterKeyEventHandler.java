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

package org.ayamemc.ayame.neoforge.client.event;

import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.KeyMapping;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.client.event.ClientTickEvent;
import net.neoforged.neoforge.client.event.RegisterKeyMappingsEvent;
import net.neoforged.neoforge.client.settings.KeyConflictContext;
import net.neoforged.neoforge.client.settings.KeyModifier;
import net.neoforged.neoforge.common.util.Lazy;
import org.ayamemc.ayame.Ayame;
import org.ayamemc.ayame.util.TranslatableName;
import org.lwjgl.glfw.GLFW;

import java.util.function.Supplier;

/**
 * 注册Ayame所使用的按键，由于NeoForge提供了组合按键绑定的支持，因此不依赖<a href="https://github.com/wyatt-herkamp/too-many-shortcuts">too-many-shortcuts</a>。
 *
 * @see KeyModifier
 */
public class RegisterKeyEventHandler {
    public static final Lazy<KeyMapping> MODEL_SELECT_MENU = Lazy.of(() -> new KeyMapping(
            "key." + Ayame.MOD_ID + "." + TranslatableName.SELECT_MODEL_MENU,
            KeyConflictContext.IN_GAME,
            KeyModifier.ALT,
            InputConstants.Type.KEYSYM,
            GLFW.GLFW_KEY_Y,
            TranslatableName.MOD_KEY_MENU_NAME
    ));
    public static final Lazy<KeyMapping> CAMERA_Y_OFFSET_UP = Lazy.of(() -> new KeyMapping(
            "key." + Ayame.MOD_ID + "." + TranslatableName.CAMERA_Y_OFFSET_UP,
            KeyConflictContext.IN_GAME,
            KeyModifier.CONTROL,
            InputConstants.Type.KEYSYM,
            GLFW.GLFW_KEY_UP,
            TranslatableName.MOD_KEY_MENU_NAME
    ));
    public static final Lazy<KeyMapping> CAMERA_Y_OFFSET_DOWN = Lazy.of(() -> new KeyMapping(
            "key." + Ayame.MOD_ID + "." + TranslatableName.CAMERA_Y_OFFSET_DOWN,
            KeyConflictContext.IN_GAME,
            KeyModifier.CONTROL,
            InputConstants.Type.KEYSYM,
            GLFW.GLFW_KEY_DOWN,
            TranslatableName.MOD_KEY_MENU_NAME
    ));
    public static final Lazy<KeyMapping> CAMERA_Y_OFFSET_RESET = Lazy.of(() -> new KeyMapping(
            "key." + Ayame.MOD_ID + "." + TranslatableName.CAMERA_Y_OFFSET_RESET,
            KeyConflictContext.IN_GAME,
            KeyModifier.CONTROL,
            InputConstants.Type.KEYSYM,
            GLFW.GLFW_KEY_R,
            TranslatableName.MOD_KEY_MENU_NAME
    ));
    public static final Lazy<KeyMapping> CUSTOM_KEY_0 = Lazy.of(() -> new KeyMapping(
            "key." + Ayame.MOD_ID + "." + TranslatableName.CUSTOM_KEY_0,
            KeyConflictContext.IN_GAME,
            KeyModifier.NONE,
            InputConstants.Type.KEYSYM,
            GLFW.GLFW_KEY_0,
            TranslatableName.MOD_KEY_MENU_NAME
    ));
    public static final Lazy<KeyMapping> CUSTOM_KEY_1 = Lazy.of(() -> new KeyMapping(
            "key." + Ayame.MOD_ID + "." + TranslatableName.CUSTOM_KEY_1,
            KeyConflictContext.IN_GAME,
            KeyModifier.NONE,
            InputConstants.Type.KEYSYM,
            GLFW.GLFW_KEY_1,
            TranslatableName.MOD_KEY_MENU_NAME
    ));
    public static final Lazy<KeyMapping> CUSTOM_KEY_2 = Lazy.of(() -> new KeyMapping(
            "key." + Ayame.MOD_ID + "." + TranslatableName.CUSTOM_KEY_2,
            KeyConflictContext.IN_GAME,
            KeyModifier.NONE,
            InputConstants.Type.KEYSYM,
            GLFW.GLFW_KEY_2,
            TranslatableName.MOD_KEY_MENU_NAME
    ));
    public static final Lazy<KeyMapping> CUSTOM_KEY_3 = Lazy.of(() -> new KeyMapping(
            "key." + Ayame.MOD_ID + "." + TranslatableName.CUSTOM_KEY_3,
            KeyConflictContext.IN_GAME,
            KeyModifier.NONE,
            InputConstants.Type.KEYSYM,
            GLFW.GLFW_KEY_3,
            TranslatableName.MOD_KEY_MENU_NAME
    ));
    public static final Lazy<KeyMapping> ANIMATION_ROULETTE = Lazy.of(() -> new KeyMapping(
            "key." + Ayame.MOD_ID + "." + TranslatableName.OPEN_ROULETTE,
            KeyConflictContext.IN_GAME,
            KeyModifier.ALT,
            InputConstants.Type.KEYSYM,
            GLFW.GLFW_KEY_X,
            TranslatableName.MOD_KEY_MENU_NAME
    ));

    /**
     * 在NeoForge中注册该按键按下的行为
     *
     * @see NeoForgeClientEventHandler#onClientClick(ClientTickEvent.Post)
     * @see RegisterKeyMappingsEvent
     */
    @SubscribeEvent
    public static void onKeyPressed(RegisterKeyMappingsEvent event) {
        event.register(MODEL_SELECT_MENU.get());
        event.register(CAMERA_Y_OFFSET_UP.get());
        event.register(CAMERA_Y_OFFSET_DOWN.get());
        event.register(CAMERA_Y_OFFSET_RESET.get());
        event.register(CUSTOM_KEY_0.get());
        event.register(CUSTOM_KEY_1.get());
        event.register(CUSTOM_KEY_2.get());
        event.register(CUSTOM_KEY_3.get());
        event.register(ANIMATION_ROULETTE.get());
    }
}
