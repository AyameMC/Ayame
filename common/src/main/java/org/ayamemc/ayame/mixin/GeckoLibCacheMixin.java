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

package org.ayamemc.ayame.mixin;

import org.ayamemc.ayame.client.AyameClient;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import software.bernie.geckolib.cache.GeckoLibCache;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.function.Consumer;

@Mixin(GeckoLibCache.class)
public abstract class GeckoLibCacheMixin {
    @Redirect(method = "reload", at = @At(value = "INVOKE", target = "Ljava/util/concurrent/CompletableFuture;thenAcceptAsync(Ljava/util/function/Consumer;Ljava/util/concurrent/Executor;)Ljava/util/concurrent/CompletableFuture;"))
    private static CompletableFuture<Void> onGeckoLibReload(CompletableFuture<Void> previous, Consumer<Void> originalAction, Executor gameExecutor) {
        return previous.thenAcceptAsync(originalAction, gameExecutor).thenAccept(unused -> {
            AyameClient.modelManagerClient.reloadAll();
        });
    }
}
