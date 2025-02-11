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

import org.ayamemc.ayame.Ayame;
import org.ayamemc.ayame.model.sync.AbstractModelLoader;
import org.ayamemc.ayame.model.sync.ModelCacheDatabase;
import org.ayamemc.ayame.model.sync.data.InMemoryModelData;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;

public class ClientModelLoader extends AbstractModelLoader {
    private final ClientModelManager modelManager;

    public ClientModelLoader(ExecutorService worker, ModelCacheDatabase modelCacheDatabase, ClientModelManager modelManager) {
        super(worker, modelCacheDatabase);
        this.modelManager = modelManager;
    }

    public CompletableFuture<Boolean> loadModelIfCached(String hash) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                final byte[] data = this.modelCacheDatabase.getCache(hash);

                if (data == null) {
                    return false;
                }

                final ByteArrayInputStream streamIn = new ByteArrayInputStream(data);
                final DataInputStream helperStreamIn = new DataInputStream(streamIn);

                final InMemoryModelData modelData = new InMemoryModelData(true, false); // We will initialize these params soon

                modelData.deserialize(helperStreamIn);

                this.onModelLoaded(modelData);

                return true;
            }catch (Exception e){
                Ayame.LOGGER.error("Failed to load ayame model", e);
                return false;
            }
        }, this.worker);
    }

    @Override
    protected void onModelLoaded(InMemoryModelData modelData) {
        this.modelManager.addLoadedModelAndRegister(modelData);
    }
}
