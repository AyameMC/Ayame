/*
 *      This file is part of Ayame.
 *
 *     Ayame is free software: you can redistribute it and/or modify it under the terms of the GNU Lesser General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.
 *
 *     Ayame is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more details.
 *
 *     You should have received a copy of the GNU Lesser General Public License along with Ayame. If not, see <https://www.gnu.org/licenses/>.
 */

package org.ayamemc.ayame.fabric.client;

import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.client.Minecraft;
import org.ayamemc.ayame.fabric.client.msic.AyameKeyMappings;

public class AyameFabricClientEvents {
    public static void init() {
        ClientTickEvents.END_CLIENT_TICK.register(AyameFabricClientEvents::endClientTickEvent);
    }

    public static void endClientTickEvent(Minecraft minecraft) {
        AyameKeyMappings.processKeyPressed();
    }
}
