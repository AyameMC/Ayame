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
import org.ayamemc.ayame.util.TranslatableName;
import org.lwjgl.glfw.GLFW;

import static org.ayamemc.ayame.Ayame.MOD_ID;

public class KeyList {
    public static final KeyMapping ROULETTE_KEY = new KeyMapping(
            "key.ayame." + TranslatableName.ANIMATION_ROULETTE,
            InputConstants.Type.KEYSYM,
            GLFW.GLFW_KEY_C,
            TranslatableName.MOD_KEY_MENU_NAME
    );

//    public static final KeyMapping CAMERA_Y_OFFSET_DOWN = registerKeyMapping(
//            TranslatableName.CAMERA_Y_OFFSET_DOWN,
//            InputConstants.Type.KEYSYM,
//            GLFW.GLFW_KEY_DOWN,
//            TranslatableName.MOD_KEY_MENU_NAME,
//            Modifier.CTRL
//    );
}
