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

package org.ayamemc.ayame.fabric.client;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.resource.IdentifiableResourceReloadListener;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.util.profiling.ProfilerFiller;
import org.ayamemc.ayame.Ayame;
import org.ayamemc.ayame.client.AyameClient;
import org.ayamemc.ayame.fabric.client.util.AyameKeyRegister;
import org.ayamemc.ayame.model.sync.ModelSelectionManager;
import org.jetbrains.annotations.NotNull;
import software.bernie.geckolib.GeckoLibConstants;

import java.util.Collection;
import java.util.Collections;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;

/**
 * Fabric客户端初始化所使用的类
 *
 * @see ClientModInitializer
 */

public final class AyameFabricClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        /*ResourceManagerHelper.get(PackType.CLIENT_RESOURCES)
                .registerReloadListener(new IdentifiableResourceReloadListener() {
                    @Override
                    public ResourceLocation getFabricId() {
                        return Ayame.withAyamePath("model_reload");
                    }

                    @Override
                    public @NotNull CompletableFuture<Void> reload(PreparationBarrier preparationBarrier,
                                                                   ResourceManager resourceManager,
                                                                   ProfilerFiller preparationsProfiler,
                                                                   ProfilerFiller reloadProfiler,
                                                                   Executor backgroundExecutor,
                                                                   Executor gameExecutor) {
                        return ModelSelectionManager.reload(preparationBarrier,
                                backgroundExecutor
                        );
                    }
                });*/
        AyameClient.init();
        // 不要动AyameKeyMappingEventHandler的init方法
        AyameKeyRegister.init();
        FabricClientEventHandler.init();
    }
}
