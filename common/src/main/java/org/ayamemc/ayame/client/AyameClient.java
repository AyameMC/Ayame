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

package org.ayamemc.ayame.client;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.Minecraft;
import org.ayamemc.ayame.client.resource.ModelScanner;
import org.ayamemc.ayame.client.gui.screen.ModelSelectMenuScreen;
import org.ayamemc.ayame.util.ConfigUtil;

@Environment(EnvType.CLIENT)
public class AyameClient {
    public static void init() {
        DefaultAyameModels.init();
        ConfigUtil.init();
        // 扫描模型
        ModelScanner.scanModel();
    }

    public static void openSelectMenuKeyPressed() {
        ModelSelectMenuScreen.openDefaultModelSelectMenu(Minecraft.getInstance().screen);
    }
}
