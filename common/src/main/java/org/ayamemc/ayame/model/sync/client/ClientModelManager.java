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
import org.ayamemc.ayame.model.sync.data.DefaultInMemoryModelResource;
import org.jetbrains.annotations.Nullable;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Stream;

public class ClientModelManager {
    private final Map<UUID, ModelSelection> playerModelSelections = Maps.newHashMap();
    private final Set<DefaultInMemoryModelResource> loadedModels = ConcurrentHashMap.newKeySet();


    public void loadModel(DefaultInMemoryModelResource model) {
        this.loadedModels.add(model);
    }

    public void unloadAll() {
        for (DefaultInMemoryModelResource modelData : this.loadedModels) {
            if (!modelData.canDeregister()) {
                continue;
            }

            modelData.deregister();
        }
    }

    public void reloadAll() {
        for (DefaultInMemoryModelResource modelData : this.loadedModels) {
            if (!modelData.canDeregister()) {
                continue;
            }

            modelData.deregister();
        }

        for (DefaultInMemoryModelResource modelData : this.loadedModels) {
            modelData.register();
        }
    }

    public void updateModelOfPlayer(UUID playerUUID, ModelSelection modelSelection){
        this.playerModelSelections.put(playerUUID, modelSelection);
    }

    public Stream<DefaultInMemoryModelResource> filterOutDefaultModel() {
        return this.loadedModels.stream().filter(DefaultInMemoryModelResource::isDefaultModel);
    }

    public ModelSelection getModelOfPlayer(UUID playerUUID){
        final Optional<DefaultInMemoryModelResource> defaultModelResource = this.filterOutDefaultModel().findFirst();

        if (defaultModelResource.isEmpty()) {
            throw new IllegalStateException("No default models has been found!");
        }

        return this.playerModelSelections.computeIfAbsent(playerUUID, unused -> defaultModelResource.get().getFallbackModelSelection());
    }

    public boolean hasModel(String id) {
        for (DefaultInMemoryModelResource loaded : this.loadedModels) {
            if (loaded.getId().equals(id)) {
                return true;
            }
        }

        return false;
    }

    @Nullable
    public DefaultInMemoryModelResource getModel(String id) {
        for (DefaultInMemoryModelResource loaded : this.loadedModels) {
            if (loaded.getId().equals(id)) {
                return loaded;
            }
        }

        return null;
    }

    public Collection<DefaultInMemoryModelResource> getAllModels() {
        return new ArrayList<>(this.loadedModels); // Copy to ensure safe
    }
}
