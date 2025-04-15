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

package org.ayamemc.ayame.fabric.client.impl;

import com.mojang.blaze3d.platform.InputConstants;
import dev.kingtux.tms.api.TMSKeyBinding;
import dev.kingtux.tms.api.modifiers.BindingModifiers;
import dev.kingtux.tms.api.modifiers.KeyModifier;
import net.minecraft.client.KeyMapping;
import org.ayamemc.ayame.client.ModifierKey;
import org.ayamemc.ayame.client.api.KeyMappingRegistry;

public class TMSKeyMappingRegistryImpl implements KeyMappingRegistry {
    private static BindingModifiers toTmsModifiers(ModifierKey modifierKey) {
        BindingModifiers modifiers = new BindingModifiers();
        switch (modifierKey) {
            case SHIFT -> modifiers.set(KeyModifier.SHIFT, true);
            case ALT -> modifiers.set(KeyModifier.ALT, true);
            case CONTROL -> modifiers.set(KeyModifier.CONTROL, true);
        }
        return modifiers;
    }
    @Override
    public KeyMapping registerKey(String name, ModifierKey modifierKey, InputConstants.Type inputType, int keyCode, String category) {
        return new TMSKeyBinding(
                handleNameTranslateKey(name),
                inputType,
                keyCode,
                category,
                toTmsModifiers(modifierKey)
        );
    }
}
