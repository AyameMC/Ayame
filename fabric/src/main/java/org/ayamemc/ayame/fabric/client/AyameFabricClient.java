/*
 *      This file is part of Ayame.
 *
 *     Ayame is free software: you can redistribute it and/or modify it under the terms of the GNU Lesser General Public License as published by the Free Software Foundation, either version 3 of the License, or any later version.
 *
 *     Ayame is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more details.
 *
 *     You should have received a copy of the GNU Lesser General Public License along with Ayame. If not, see <https://www.gnu.org/licenses/>.
 */

package org.ayamemc.ayame.fabric.client;

import net.fabricmc.api.ClientModInitializer;
import net.minecraft.client.KeyMapping;
import org.ayamemc.ayame.fabric.client.msic.AyameKeyMappings;

public final class AyameFabricClient implements ClientModInitializer {
    private static KeyMapping keyMapping;

    @Override
    public void onInitializeClient() {
        AyameKeyMappings.init();
        AyameFabricClientEvents.init();
    }
}
