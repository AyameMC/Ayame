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

package org.ayamemc.ayame.client;

import com.mojang.logging.LogUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.sounds.SoundEvent;
import org.ayamemc.ayame.Constants;
import org.ayamemc.ayame.client.api.KeyMappingRegistry;
import org.ayamemc.ayame.client.yttribume.Yttribumes;
import org.ayamemc.ayame.model.molang.AyameMolangs;
import org.ayamemc.ayame.model.sync.IModelLoader;
import org.ayamemc.ayame.model.sync.ModelCacheDatabase;
import org.ayamemc.ayame.model.sync.client.ClientModelLoader;
import org.ayamemc.ayame.model.sync.client.ClientModelManager;
import org.ayamemc.ayame.util.ConfigUtil;
import org.ayamemc.ayame.util.FileUtil;
import org.jetbrains.annotations.NotNull;
import team.unnamed.mocha.MochaEngine;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

import static org.ayamemc.ayame.Ayame.LOGGER;


public class AyameClient {
    public static final ExecutorService modWorker = Executors.newCachedThreadPool();
    public static final ModelCacheDatabase cacheDatabase;
    public static final ClientModelManager modelManagerClient = new ClientModelManager();
    public static final Minecraft MINECRAFT = Minecraft.getInstance();
    public static KeyMappingRegistry keyMappingRegistry;
    public static String modLoader;
    public static SoundEvent ayameSound;




    static {
        try {
            cacheDatabase = new ModelCacheDatabase(Constants.CACHE_DIR_CLIENT);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    private static final ClientModelLoader modelLoaderClient = new ClientModelLoader(modWorker, cacheDatabase, modelManagerClient);

    public static void init(@NotNull KeyMappingRegistry keyMappingRegistry,
                            @NotNull String modLoader,
                            @NotNull SoundEvent ayameSound) {
        AyameClient.keyMappingRegistry = keyMappingRegistry;
        AyameClient.modLoader = modLoader;
        AyameClient.ayameSound = ayameSound;

        ConfigUtil.init();
        AyameMolangs.registerMolangs();

        Yttribumes.init();

        registerDefaultModeLoaders();
        exportDefaultModels();
        loadAllDefaultModels();
    }

    public static void registerDefaultModeLoaders() {
        for (IModelLoader modelLoader : Constants.DEFAULT_MODEL_LOADERS) {
            modelLoaderClient.registerModelLoader(modelLoader);
        }
    }

    private static void exportDefaultModels() {
        for (Map.Entry<String, Constants.DefaultModelInfo> entry : Constants.DEFAULT_MODELS.entrySet()) {
            String modelId = entry.getKey();
            List<String> filePaths = entry.getValue().files();
            final Path modelDir = Constants.MODELS_DIR.resolve(modelId);
            FileUtil.copyAyameBuiltinFilesToDirectory(
                    entry.getValue().prefix(),
                    filePaths.toArray(new String[0]),
                    modelDir
            );
        }
    }

    public static void loadAllDefaultModels() {
        LogUtils.getLogger().info("Register default models");

        Constants.DEFAULT_MODELS.keySet().forEach(modelId -> {
            final Path targetPath = Constants.MODELS_DIR.resolve(modelId);
            modelLoaderClient.loadModelSync(
                    targetPath.toFile(),
                    Constants.DEFAULT_MODEL_DATA_MODIFIER
            );
        });
    }

    public static CompletableFuture<Boolean> tryLoadModelCacheFromServer(String hash) {
        return modelLoaderClient.loadModelIfCached(hash);
    }

    public static void unloadAllModels() {
        modelManagerClient.unloadAll();
    }

    public static void requestServerSync() {

    }

    public static @NotNull CompletableFuture<Void> loadAllModelLocal() {
        LogUtils.getLogger().info("Loading models in local");

        final File targetDir = Constants.MODELS_DIR.toFile();
        final File[] files = targetDir.listFiles();

        if (files == null) {
            return CompletableFuture.completedFuture(null);
        }

        final AtomicInteger taskCountDown = new AtomicInteger(files.length);
        final CompletableFuture<Void> callback = new CompletableFuture<>();

        for (File targetFile : files) {
            modelLoaderClient.loadModelAsync(targetFile, null).whenComplete((unused, ex) -> {
                if (ex != null) {
                    LOGGER.error(ex.getMessage(), ex);
                }

                final int curr = taskCountDown.decrementAndGet();

                if (curr == 0) {
                    callback.complete(null);
                }
            });
        }

        return callback;
    }
}
