/*
 *      This file is part of Ayame.
 *
 *     Ayame is free software: you can redistribute it and/or modify it under the terms of the GNU Lesser General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.
 *
 *     Ayame is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more details.
 *
 *     You should have received a copy of the GNU Lesser General Public License along with Ayame. If not, see <https://www.gnu.org/licenses/>.
 */

package org.ayamemc.ayame.fabric.client.msic;

import com.mojang.blaze3d.platform.InputConstants;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import org.ayamemc.ayame.screen.ModelSelectMenuScreen;
import org.ayamemc.ayame.util.JavaUtil;
import org.ayamemc.ayame.util.TranslatableName;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.lwjgl.glfw.GLFW;

import static org.ayamemc.ayame.Ayame.MOD_ID;


public class AyameKeyMappings {
    public static final boolean IS_TMS_INSTALLED = JavaUtil.tryClass("dev.kingtux.tms.api.TMSKeyBinding");
    public static final KeyMapping MODEL_SELECT_MENU = registerKeyMapping(
            TranslatableName.SELECT_MODEL_MENU,
            InputConstants.Type.KEYSYM,
            GLFW.GLFW_KEY_Y,
            TranslatableName.MOD_KEY_MENU_NAME,
            "alt"
    );

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

    public static void processKeyPressed() {
        while (AyameKeyMappings.MODEL_SELECT_MENU.consumeClick()) {
            // TODO open model select menu
            Minecraft.getInstance().setScreen(new ModelSelectMenuScreen(Component.empty(), Minecraft.getInstance().screen));
        }
    }
}
