package org.ayamemc.ayame.neoforge;

import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import org.ayamemc.ayame.Ayame  ;

@Mod(Ayame.MOD_ID)
public final class AyameNeoForge {
    public AyameNeoForge(IEventBus modBus) {
        // Run our common setup.
        Ayame.init();

    }
}
