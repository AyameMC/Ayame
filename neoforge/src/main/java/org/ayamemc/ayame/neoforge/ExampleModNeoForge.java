package org.ayamemc.ayame.neoforge;

import net.neoforged.fml.common.Mod;

import org.ayamemc.ayame.ExampleMod;

@Mod(ExampleMod.MOD_ID)
public final class ExampleModNeoForge {
    public ExampleModNeoForge() {
        // Run our common setup.
        ExampleMod.init();
    }
}
