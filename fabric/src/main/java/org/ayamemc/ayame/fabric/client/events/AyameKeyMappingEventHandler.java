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

package org.ayamemc.ayame.fabric.client.events;

import com.mojang.blaze3d.platform.InputConstants;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.KeyMapping;
import org.ayamemc.ayame.client.AyameClient;
import org.ayamemc.ayame.client.gui.screen.ModelSelectMenuScreen;
import org.ayamemc.ayame.fabric.client.util.AyameTMSKeyMappings;
import org.ayamemc.ayame.util.JavaUtil;
import org.ayamemc.ayame.util.TranslatableName;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.lwjgl.glfw.GLFW;

import static org.ayamemc.ayame.Ayame.MOD_ID;

/**
 * 注册Ayame所使用的按键，若安装了 <a href="https://github.com/wyatt-herkamp/too-many-shortcuts">too-many-shortcuts</a>组则会使用其提供的组合按键绑定。
 * @see AyameTMSKeyMappings
 * @see JavaUtil
 */
@Environment(EnvType.CLIENT)
public class AyameKeyMappingEventHandler {
    public static final boolean IS_TMS_INSTALLED = JavaUtil.isJavaClassExist("dev.kingtux.tms.api.TMSKeyBinding");
    public static final KeyMapping MODEL_SELECT_MENU = registerKeyMapping(
            TranslatableName.SELECT_MODEL_MENU,
            InputConstants.Type.KEYSYM,
            GLFW.GLFW_KEY_Y,
            TranslatableName.MOD_KEY_MENU_NAME,
            "alt"
    );

    /**
     * 注册按键
     * @param name 按键绑定页面显示的键位名称
     * @param type 输入类型，见{@link InputConstants}
     * @param keyCode 键位，见{@link GLFW}
     * @param category 按键绑定页面的种类
     * @param modifier 组合键位，见{@link AyameTMSKeyMappings}
     * @return 调用 {@link AyameTMSKeyMappings}
     * @see KeyMapping
     */
    public static KeyMapping registerKeyMapping(String name, @NotNull InputConstants.Type type, int keyCode, String category, @Nullable String modifier) {
        KeyMapping keyMapping;
        if (IS_TMS_INSTALLED && modifier != null) {
            keyMapping = AyameTMSKeyMappings.registerTMSKeyMapping(name, type, keyCode, category, modifier);
        } else {
            keyMapping = new KeyMapping("key." + MOD_ID + "." + name, type, keyCode, category);
        }
        return KeyBindingHelper.registerKeyBinding(keyMapping);
    }

    public static void init() {
    }

    /**
     * 按下按键后打开{@link ModelSelectMenuScreen}屏幕
     * @see KeyMapping
     */
    public static void processKeyPressed() {
        while (AyameKeyMappingEventHandler.MODEL_SELECT_MENU.consumeClick()) {
            AyameClient.openSelectMenuKeyPressed();
        }
    }
}
