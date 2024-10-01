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

package org.ayamemc.ayame.fabric.client.util;

import com.mojang.blaze3d.platform.InputConstants;
import dev.kingtux.tms.api.TMSKeyBinding;
import dev.kingtux.tms.api.modifiers.BindingModifiers;
import dev.kingtux.tms.api.modifiers.KeyModifier;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.KeyMapping;
import net.minecraft.resources.ResourceLocation;
import org.ayamemc.ayame.fabric.client.event.AyameKeyMappingEventHandler;
import org.jetbrains.annotations.NotNull;
import org.lwjgl.glfw.GLFW;

import static org.ayamemc.ayame.Ayame.MOD_ID;


/**
 * 注册{@code too-many-shortcuts}的组合按键，检测是否安装其的逻辑位于{@link AyameKeyMappingEventHandler}。
 */
@Environment(EnvType.CLIENT)
public class AyameTMSKeyMappings {
    /**
     * 使用TMS注册按键
     *
     * @param name     按键绑定页面显示的键位名称
     * @param type     输入类型，见{@link InputConstants}
     * @param keyCode  键位，见{@link GLFW}
     * @param category 按键绑定页面的种类
     * @param modifier TMS提供的组合键位，见{@link AyameTMSKeyMappings}
     * @return AyameKeyMappings
     */
    public static KeyMapping registerTMSKeyMapping(String name, @NotNull InputConstants.Type type, int keyCode, String category, @NotNull String modifier) {
        BindingModifiers bindingModifiers = null;
        if (modifier.equalsIgnoreCase("alt")) {
            bindingModifiers = new BindingModifiers();
            bindingModifiers.set(KeyModifier.ALT, true);
        } else if (modifier.equalsIgnoreCase("shift")) {
            bindingModifiers = new BindingModifiers();
            bindingModifiers.set(KeyModifier.SHIFT, true);
        } else if (modifier.equalsIgnoreCase("ctrl")) {
            bindingModifiers = new BindingModifiers();
            bindingModifiers.set(KeyModifier.CONTROL, true);
        }
        if (bindingModifiers != null) {
            return new TMSKeyBinding(ResourceLocation.fromNamespaceAndPath(MOD_ID, name), type, keyCode, category, bindingModifiers);
        } else {
            throw new RuntimeException("Modifier only can be alt, shift or ctrl");
        }
    }
}
