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

package org.ayamemc.ayame.model.sync;

import org.ayamemc.ayame.model.sync.client.ClientModelManager;
import org.ayamemc.ayame.model.sync.data.InMemoryModelData;
import org.ayamemc.ayame.model.sync.data.ModelDataComponent;
import org.jetbrains.annotations.NotNull;

import java.io.*;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.function.Consumer;

public abstract class AbstractModelLoader {
    protected final ExecutorService worker;

    protected final ModelCacheDatabase modelCacheDatabase;
    private final Set<IModelLoader> modelLoaders = new HashSet<>();

    public AbstractModelLoader(ExecutorService worker, ModelCacheDatabase modelCacheDatabase) {
        this.worker = worker;
        this.modelCacheDatabase = modelCacheDatabase;
    }

    public void loadModel0(@NotNull ModelDataComponent dataComponent, Consumer<ModelDataComponent> componentModifier) throws IOException {
        if (componentModifier != null) componentModifier.accept(dataComponent);

        final InMemoryModelData built = new InMemoryModelData(dataComponent.canUnload(), dataComponent.loadAsDefaultModel());

        built.restoreFrom(dataComponent.modelMeta());
        built.restoreFrom(dataComponent.byteStorage());

        this.modelCacheDatabase.addCache(built);

        this.onModelLoaded(built);
    }

    protected abstract void onModelLoaded(InMemoryModelData modelData);

    public void registerModelLoader(IModelLoader modelLoader) {
        this.modelLoaders.add(modelLoader);
    }

    public void deregisterModelLoader(IModelLoader modelLoader) {
        this.modelLoaders.remove(modelLoader);
    }

    protected IModelLoader selectModelLoaderFor(File target) {
        for (IModelLoader modelLoader : this.modelLoaders) {
            if (modelLoader.wantLoad(target)) {
                return modelLoader;
            }
        }

        throw new IllegalStateException("No model loaders have been found!");
    }

    public void loadModelSync(File file, Consumer<ModelDataComponent> modelDataComponentModifier) {
        final IModelLoader selectedLoader = this.selectModelLoaderFor(file);
        final ModelDataComponent dataComponent = selectedLoader.loadModel(file);

        if (dataComponent == null) {
            throw new IllegalStateException();
        }

        try {
            this.loadModel0(dataComponent, modelDataComponentModifier);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public CompletableFuture<Void> loadModelAsync(File file, Consumer<ModelDataComponent> modelDataComponentModifier) {
        return CompletableFuture.runAsync(() -> this.loadModelSync(file, modelDataComponentModifier), this.worker);
    }
}
