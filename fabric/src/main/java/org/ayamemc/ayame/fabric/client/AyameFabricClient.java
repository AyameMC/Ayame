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
