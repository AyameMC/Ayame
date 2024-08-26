package org.ayamemc.ayame.fabric.client;

import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.client.Minecraft;
import org.ayamemc.ayame.fabric.AyameFabric;
import org.ayamemc.ayame.fabric.client.msic.AyameKeyMappings;

public class AyameFabricClientEvents {
    public static void init(){
        ClientTickEvents.END_CLIENT_TICK.register(AyameFabricClientEvents::endClientTickEvent);
    }

    public static void endClientTickEvent(Minecraft minecraft) {
        AyameKeyMappings.processKeyPressed();
    }
}
