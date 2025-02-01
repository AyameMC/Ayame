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

package org.ayamemc.ayame.model;

import net.minecraft.server.packs.resources.PreparableReloadListener;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.util.profiling.ProfilerFiller;
import net.minecraft.world.entity.player.Player;
import org.ayamemc.ayame.Ayame;
import org.ayamemc.ayame.client.api.PlayerModelAPI;
import org.ayamemc.ayame.client.util.ModelResourceWriterUtil;
import org.ayamemc.ayame.util.FileUtil;
import org.ayamemc.ayame.util.JsonInterpreter;
import org.jetbrains.annotations.NotNull;

import java.io.InputStream;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executor;

import static org.ayamemc.ayame.Ayame.LOGGER;
/**
 * 正在渲染中的模型缓存，它同时运行在服务端和客户端
 */
public class AyameModelCache {
    // 考虑到未来可能涩及多线程操作，所以使用 ConcurrentHashMap
    public static Map<Player, ModelType> playerModelCache = new ConcurrentHashMap<>();

    public static void setPlayerModel(Player player, ModelType model) {
        playerModelCache.put(player, model);
    }

    public static void removePlayerModel(Player player) {
        playerModelCache.remove(player);
    }

    /**
     * 获取玩家模型，如果没有就返回默认的
     *
     * @param player 玩家
     * @return 玩家模型
     */
    @NotNull
    public static ModelType getPlayerModel(Player player) {
        return playerModelCache.getOrDefault(player, DefaultModels.BUILTIN_MODEL_TYPE);
    }

    public static boolean hasPlayerModel(Player player) {
        return playerModelCache.containsKey(player);
    }

    public static @NotNull CompletableFuture<Void> reload(PreparableReloadListener.PreparationBarrier preparationBarrier,
                                                          ResourceManager resourceManager,
                                                          ProfilerFiller preparationsProfiler,
                                                          ProfilerFiller reloadProfiler,
                                                          Executor backgroundExecutor,
                                                          Executor gameExecutor) {
        Map<Player, PlayerModelAPI.CacheEntry> newCache = new HashMap<>();
        // TODO: 完成 reload
        return CompletableFuture.runAsync(() -> {
            PlayerModelAPI.getCache().forEach((player, cacheEntry) -> {
                final ModelType model = cacheEntry.model();
                JsonInterpreter modelJson = JsonInterpreter.of(FileUtil.getFileAsStream(Path.of(model.getGeoModel().getPath())));
                JsonInterpreter animJson = JsonInterpreter.of(FileUtil.getFileAsStream(Path.of(model.getAnimation().getPath())));
                JsonInterpreter armJson = JsonInterpreter.of(FileUtil.getFileAsStream(Path.of(model.getArm().getPath())));
                InputStream texture = FileUtil.getFileAsStream(Path.of(model.getTexture().getPath()));

                PlayerModelAPI.CacheEntry newEntry = new PlayerModelAPI.CacheEntry(model, modelJson, animJson, armJson, texture);
                newCache.put(player, newEntry);
            });
            LOGGER.info("Reloaded Ayame Model Cache");
        }, backgroundExecutor).thenCompose(preparationBarrier::wait).thenAcceptAsync(empty -> {
            newCache.forEach((player, cacheEntry) -> {
                ModelResourceWriterUtil.addModelResource(cacheEntry);
            });
        }, gameExecutor);
    }
}
