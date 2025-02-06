/*
 *     Custom player model mod. Powered by GeckoLib.
 *     Copyright (C) 2024-2025  CrystalNeko, HappyRespawnanchor, pertaz(Icon Designer)
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


import net.minecraft.client.Minecraft;
import net.minecraft.server.packs.resources.ReloadableResourceManager;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.util.profiling.ProfilerFiller;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModLoadingContext;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.resource.ContextAwareReloadListener;
import org.ayamemc.ayame.Ayame;
import org.ayamemc.ayame.client.AyameClient;
import org.ayamemc.ayame.client.IAyameClientEvents;
import org.ayamemc.ayame.client.gui.screen.SettingsScreen;
import org.ayamemc.ayame.model.sync.ModelSelectionManager;
import org.ayamemc.ayame.neoforge.client.event.NeoForgeClientEventHandler;
import org.ayamemc.ayame.neoforge.client.event.RegisterKeyEventHandler;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;

@Mod(value = Ayame.MOD_ID, dist = Dist.CLIENT)
public class AyameNeoForgeClient {
    public AyameNeoForgeClient(IEventBus modBus) {
        registerReloadListener();
        AyameClient.init();

        NeoForge.EVENT_BUS.register(NeoForgeClientEventHandler.class);

        modBus.register(RegisterKeyEventHandler.class);

        IAyameClientEvents.Instance.INSTANCE = new AyameClientEventsNeoForgeImpl();

        ModLoadingContext.get().registerExtensionPoint(
                IConfigScreenFactory.class,
                () -> (modContainer, lastScreen) -> new SettingsScreen(lastScreen)
        );

    }

    public static void registerReloadListener() {
        Minecraft mc = Minecraft.getInstance();

        if (mc.getResourceManager() instanceof ReloadableResourceManager resourceManager)
            resourceManager.registerReloadListener(new ContextAwareReloadListener() {
                @Override
                public @NotNull CompletableFuture<Void> reload(@NotNull PreparationBarrier preparationBarrier,
                                                               @NotNull ResourceManager resourceManager,
                                                               @NotNull ProfilerFiller preparationsProfiler,
                                                               @NotNull ProfilerFiller reloadProfiler,
                                                               @NotNull Executor backgroundExecutor,
                                                               @NotNull Executor gameExecutor) {
                    return ModelSelectionManager.reload(preparationBarrier, backgroundExecutor);
                }
            });
    }

}
