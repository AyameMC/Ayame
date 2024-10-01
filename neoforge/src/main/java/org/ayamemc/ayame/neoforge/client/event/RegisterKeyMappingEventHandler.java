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

package org.ayamemc.ayame.neoforge.client.event;

import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.KeyMapping;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RegisterKeyMappingsEvent;
import net.neoforged.neoforge.client.settings.KeyConflictContext;
import net.neoforged.neoforge.client.settings.KeyModifier;
import net.neoforged.neoforge.common.util.Lazy;
import org.ayamemc.ayame.Ayame;
import org.ayamemc.ayame.util.TranslatableName;
import org.lwjgl.glfw.GLFW;

/**
 * 注册Ayame所使用的按键，由于NeoForge提供了组合按键绑定的支持，因此不依赖<a href="https://github.com/wyatt-herkamp/too-many-shortcuts">too-many-shortcuts</a>。
 *
 * @see KeyModifier
 */
@OnlyIn(Dist.CLIENT)
@EventBusSubscriber(modid = Ayame.MOD_ID, bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class RegisterKeyMappingEventHandler {
    public static final Lazy<KeyMapping> MODEL_SELECT_MENU = Lazy.of(() -> new KeyMapping(
            "key." + Ayame.MOD_ID + "." + TranslatableName.SELECT_MODEL_MENU,
            KeyConflictContext.IN_GAME,
            KeyModifier.ALT,
            InputConstants.Type.KEYSYM,
            GLFW.GLFW_KEY_Y,
            TranslatableName.MOD_KEY_MENU_NAME
    ));

    /**
     * 在NeoForge中注册该按键按下的行为，按下后行为位于{@link OpenModelSelectMenuEventHandler}
     *
     * @see RegisterKeyMappingsEvent
     */
    @SubscribeEvent
    public static void onKeyPressed(RegisterKeyMappingsEvent event) {
        event.register(MODEL_SELECT_MENU.get());
    }
}
