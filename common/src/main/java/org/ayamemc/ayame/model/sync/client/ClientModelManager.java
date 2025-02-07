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

import com.google.common.collect.Maps;
import org.ayamemc.ayame.model.sync.ModelSelection;
import org.ayamemc.ayame.model.sync.data.InMemoryModelData;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Stream;

public class ClientModelManager {
    private final Map<UUID, ModelSelection> playerModelSelections = Maps.newHashMap();
    // 已经加载的模型
    private final Set<InMemoryModelData> loadedModels = ConcurrentHashMap.newKeySet();

    // 添加新的模型
    public void addLoadedModelAndRegister(@NotNull InMemoryModelData model) {
        model.register();

        this.loadedModels.add(model);
    }

    public void unloadAll() {
        final Iterator<InMemoryModelData> modelResourceIterator = this.loadedModels.iterator();
        while (modelResourceIterator.hasNext()) {
            final InMemoryModelData resource = modelResourceIterator.next();

            if (!resource.canDeregister()) {
                continue;
            }

            resource.deregister();
            modelResourceIterator.remove();
        }
    }

    public void reloadAll() {
        for (InMemoryModelData modelData : this.loadedModels) {
            if (!modelData.canDeregister()) {
                continue;
            }

            modelData.deregister();
        }

        for (InMemoryModelData modelData : this.loadedModels) {
            modelData.register();
        }
    }

    public void updateModelOfPlayer(UUID playerUUID, ModelSelection modelSelection){
        this.playerModelSelections.put(playerUUID, modelSelection);
    }

    public Stream<InMemoryModelData> filterOutDefaultModel() {
        return this.loadedModels.stream().filter(InMemoryModelData::isDefaultModel);
    }

    public ModelSelection getDefaultModelFallback() {
        final Optional<InMemoryModelData> got = this.filterOutDefaultModel().findFirst();

        if (got.isEmpty()) {
            throw new IllegalStateException("No default model found!");
        }

        final InMemoryModelData actual = got.get();

        return actual.getFallbackModelSelection();
    }

    public ModelSelection getModelOfPlayer(UUID playerUUID){
        final Optional<InMemoryModelData> defaultModelResource = this.filterOutDefaultModel().findFirst();

        if (defaultModelResource.isEmpty()) {
            throw new IllegalStateException("No default models has been found!");
        }

        return this.playerModelSelections.computeIfAbsent(playerUUID, unused -> defaultModelResource.get().getFallbackModelSelection());
    }

    public boolean hasModel(String id) {
        for (InMemoryModelData loaded : this.loadedModels) {
            if (loaded.getId().equals(id)) {
                return true;
            }
        }

        return false;
    }

    @Nullable
    public InMemoryModelData getModel(String id) {
        for (InMemoryModelData loaded : this.loadedModels) {
            if (loaded.getId().equals(id)) {
                return loaded;
            }
        }

        return null;
    }

    public Collection<InMemoryModelData> getAllModels() {
        return new ArrayList<>(this.loadedModels); // Copy to ensure safe
    }
}
