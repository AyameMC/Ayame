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

package org.ayamemc.ayame.client.api;

import org.ayamemc.ayame.model.resource.AyameModelResource;
import org.ayamemc.ayame.model.sync.ModelCacheDatabase;
import org.ayamemc.ayame.model.sync.client.ClientModelLoader;
import org.ayamemc.ayame.model.sync.client.ClientModelManager;
import org.ayamemc.ayame.util.FileUtil;

import java.io.IOException;
import java.nio.file.Path;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ClientAPIHooks {
    public static final ExecutorService modWorker = Executors.newCachedThreadPool();

    public static final ModelCacheDatabase cacheDatabase;

    static {
        try {
            cacheDatabase = new ModelCacheDatabase(Path.of(AyameModelResource.MODEL_PATH));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static final ClientModelManager modelManagerClient = new ClientModelManager();
    public static final ClientModelLoader modelLoaderClient = new ClientModelLoader(modWorker, cacheDatabase, modelManagerClient);

    static {

    }

    private static void injectDefaultModels() {
        FileUtil.copyAyameBuiltinDirectoryToDirectory("models/ayame_chan/", AyameModelResource.MODEL_PATH + "ayame_chan");
    }

    public static void loadModelsLocal() {
        
    }

}
