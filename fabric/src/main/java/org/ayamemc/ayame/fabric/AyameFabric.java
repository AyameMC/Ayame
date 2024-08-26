package org.ayamemc.ayame.fabric;

import net.fabricmc.api.ModInitializer;

import org.ayamemc.ayame.Ayame;

public final class AyameFabric implements ModInitializer {
    @Override
    public void onInitialize() {
        // This code runs as soon as Minecraft is in a mod-load-ready state.
        // However, some things (like resources) may still be uninitialized.
        // Proceed with mild caution.

        // Run our common setup.
        Ayame.init();
    }
}
