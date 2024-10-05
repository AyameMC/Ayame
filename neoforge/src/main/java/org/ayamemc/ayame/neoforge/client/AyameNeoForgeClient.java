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

package org.ayamemc.ayame.neoforge.client;


import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.fml.ModLoadingContext;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;
import org.ayamemc.ayame.Ayame;
import org.ayamemc.ayame.client.AyameClient;
import org.ayamemc.ayame.client.IAyameClientEvents;
import org.ayamemc.ayame.client.gui.screen.SettingsScreen;

@OnlyIn(Dist.CLIENT)
@Mod(value = Ayame.MOD_ID, dist = Dist.CLIENT)
public class AyameNeoForgeClient {
    public AyameNeoForgeClient() {
        AyameClient.init();
        IAyameClientEvents.Instance.INSTANCE = new AyameClientEventsNeoForgeImpl();
        ModLoadingContext.get().registerExtensionPoint(
                IConfigScreenFactory.class,
                () -> (modContainer, parentScreen) -> new SettingsScreen(parentScreen));
    }


}
