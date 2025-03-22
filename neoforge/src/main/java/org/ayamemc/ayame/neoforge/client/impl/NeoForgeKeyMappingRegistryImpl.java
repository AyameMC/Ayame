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

package org.ayamemc.ayame.neoforge.client.impl;

import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.KeyMapping;
import net.neoforged.neoforge.client.settings.KeyConflictContext;
import net.neoforged.neoforge.client.settings.KeyModifier;
import org.ayamemc.ayame.client.ModifierKey;
import org.ayamemc.ayame.client.api.KeyMappingRegistry;

public class NeoForgeKeyMappingRegistryImpl implements KeyMappingRegistry {

    @Override
    public KeyMapping registerKey(String name, ModifierKey modifierKey, InputConstants.Type inputType, int keyCode, String category) {
        return new KeyMapping(
                handleNameTranslateKey(name),
                KeyConflictContext.IN_GAME,
                toNeoForgeKeyModifier(modifierKey),
                inputType,
                keyCode,
                category
        );
    }

    private static KeyModifier toNeoForgeKeyModifier(ModifierKey modifierKey) {
        return switch (modifierKey) {
            case ALT -> KeyModifier.ALT;
            case SHIFT -> KeyModifier.SHIFT;
            case CONTROL -> KeyModifier.CONTROL;
            case NONE -> KeyModifier.NONE;
        };
    }
}
