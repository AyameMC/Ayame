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
import org.ayamemc.ayame.Constants;
import org.ayamemc.ayame.model.resource.AyameModelResource;
import org.ayamemc.ayame.model.sync.IModelLoader;
import org.ayamemc.ayame.model.sync.ModelCacheDatabase;
import org.ayamemc.ayame.model.sync.client.ClientModelLoader;
import org.ayamemc.ayame.model.sync.client.ClientModelManager;
import org.ayamemc.ayame.util.ConfigUtil;
import org.ayamemc.ayame.util.FileUtil;

import java.io.IOException;
import java.nio.file.Path;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class AyameClient {
    public static final ExecutorService modWorker = Executors.newCachedThreadPool();
    public static final ModelCacheDatabase cacheDatabase;
    public static final ClientModelManager modelManagerClient = new ClientModelManager();

    static {
        try {
            cacheDatabase = new ModelCacheDatabase(Constants.CACHE_DIR_CLIENT);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static final ClientModelLoader modelLoaderClient = new ClientModelLoader(modWorker, cacheDatabase, modelManagerClient);


    public static void init() {
        ConfigUtil.init();
        // 扫描模型
       //  ModelScanner.scanModel();
        registerDefaultModelLoaders();
        injectDefaultModels();
        registerDefaultModels();
    }

    public static void registerDefaultModelLoaders(){
        for (IModelLoader modelLoader : Constants.DEFAULT_MODEL_LOADERS) {
            modelLoaderClient.registerModelLoader(modelLoader);
        }
    }

    private static void injectDefaultModels() {
        FileUtil.copyAyameBuiltinDirectoryToDirectory("models/ayame_chan/", AyameModelResource.MODEL_PATH + "ayame_chan");
    }

    public static void registerDefaultModels() {
        LogUtils.getLogger().info("Register default models");

        for (String defaultModelName : Constants.DEFAULT_MODELS) {
            final Path targetPath = Constants.MODELS_DIR.resolve(defaultModelName);

            modelLoaderClient.loadModelSync(targetPath.toFile(), Constants.DEFAULT_MODEL_DATA_MODIFIER);
        }
    }
}
