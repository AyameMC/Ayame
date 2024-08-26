package org.ayamemc.ayame.neoforge;

import net.neoforged.fml.common.Mod;

import org.ayamemc.ayame.Ayame;

@Mod(Ayame.MOD_ID)
public final class AyameNeoForge {
    public AyameNeoForge() {
        // Run our common setup.
        Ayame.init();
    }
}
