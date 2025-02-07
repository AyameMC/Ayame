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

package org.ayamemc.ayame.model.sync.client;

import org.ayamemc.ayame.model.sync.IModelLoader;
import org.ayamemc.ayame.model.sync.ModelCacheDatabase;
import org.ayamemc.ayame.model.sync.data.InMemoryModelData;
import org.ayamemc.ayame.model.sync.data.ModelDataComponent;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;

public class ClientModelLoader {
    private final ExecutorService worker;

    private final ModelCacheDatabase modelCacheDatabase;
    private final ClientModelManager modelManager;

    private final Set<IModelLoader> modelLoaders= new HashSet<>();

    public ClientModelLoader(ExecutorService worker, ModelCacheDatabase modelCacheDatabase, ClientModelManager modelManager) {
        this.worker = worker;
        this.modelCacheDatabase = modelCacheDatabase;
        this.modelManager = modelManager;
    }

    public void loadModel0(@NotNull ModelDataComponent dataComponent, boolean canUnload, boolean isDefaultModel) throws IOException {
        final InMemoryModelData built = new InMemoryModelData(canUnload, isDefaultModel);

        built.restoreFrom(dataComponent.modelMeta());
        built.restoreFrom(dataComponent.byteStorage());

        this.modelCacheDatabase.addCache(built);

        this.modelManager.addLoadedModelAndRegister(built);
    }

    public void registerModelLoader(IModelLoader modelLoader) {
        this.modelLoaders.add(modelLoader);
    }

    public void deregisterModelLoader(IModelLoader modelLoader) {
        this.modelLoaders.remove(modelLoader);
    }

    public IModelLoader selectModelLoaderFor(File target) {
        for (IModelLoader modelLoader : this.modelLoaders) {
            if (modelLoader.wantLoad(target)) {
                return modelLoader;
            }
        }

        throw new IllegalStateException("No model loaders have been found!");
    }

    public void loadModelSync(File file) {
        final IModelLoader selectedLoader = this.selectModelLoaderFor(file);
        final ModelDataComponent dataComponent = selectedLoader.loadModel(file);

        if (dataComponent == null) {
            throw new IllegalStateException();
        }

        try {
            this.loadModel0(dataComponent, true, false);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public CompletableFuture<Void> loadModelAsync(File file) {
        return CompletableFuture.runAsync(() -> this.loadModelSync(file), this.worker);
    }
}
