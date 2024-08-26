package org.ayamemc.ayame.fabric;

import net.fabricmc.api.ModInitializer;

import org.ayamemc.ayame.Ayame;

public final class AyameFabric implements ModInitializer {
    @Override
    public void onInitialize() {
        Ayame.init();
    }
}
