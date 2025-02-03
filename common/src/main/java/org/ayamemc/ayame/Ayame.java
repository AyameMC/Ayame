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

package org.ayamemc.ayame;

import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import org.ayamemc.ayame.client.yttribume.Yttribumes;
import org.ayamemc.ayame.model.AyameMolangVars;
import org.ayamemc.ayame.model.DefaultModels;
import org.ayamemc.ayame.model.resource.ModelResourceRegistry;
import org.ayamemc.ayame.util.ConfigUtil;
import org.ayamemc.ayame.util.ModLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class Ayame {
    public static final String MOD_ID = "ayame";
    public static final String VERSION = "0.1.0";
    public static final String MOD_NAME = "Ayame";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_NAME);
    public static final Minecraft MINECRAFT = Minecraft.getInstance();
    public static ModLoader modLoader;

    /**
     * @see org.ayamemc.ayame.client.AyameClient
     */
    public static void init(ModLoader modLoader) {
        Ayame.modLoader = modLoader;
        ConfigUtil.init();
        ModelResourceRegistry.init();
        DefaultModels.init();
        Yttribumes.init();

        AyameMolangVars.registerMolangVars();
    }

    public static ResourceLocation withAyamePath(String location) {
        return ResourceLocation.fromNamespaceAndPath(MOD_ID, location);
    }
}
