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
import org.ayamemc.ayame.client.AyameClient;
import org.ayamemc.ayame.client.script.JavaScriptLoader;
import org.ayamemc.ayame.model.sync.ModelSelection;
import org.ayamemc.ayame.model.sync.data.InMemoryModelData;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Stream;

/**
 * 用于管理已经加载的模型和服务端发给客户端的实体数据
 *
 * @see ModelSelection
 * @see InMemoryModelData
 */
public class ClientModelManager {
    private final Map<UUID, ModelSelection> playerModelSelections = Maps.newHashMap();


    // 已经加载的模型
    private final Set<InMemoryModelData> loadedModels = ConcurrentHashMap.newKeySet();

    // 添加新的模型
    public void addLoadedModelAndRegister(@NotNull InMemoryModelData model) {
        if (this.modelDuplicated(model)) {
            return;
        }

        model.register();

        this.loadedModels.add(model);
    }

    public boolean modelDuplicated(@NotNull InMemoryModelData data) {
        final String id = data.getId();

        for (InMemoryModelData dataCurr : this.loadedModels) {
            if (dataCurr.getId().equals(id)) {
                return true;
            }
        }

        return false;
    }

//    public void reloadModel(@NotNull InMemoryModelData model) {
//        model.deregister();
//        this.loadedModels.remove(model);
//    }

    /**
     * 卸载并删除掉可以卸载的模型
     * 一般只有默认模型被标记为不可卸载
     */
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

    /**
     * 重载模型
     */
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


    public void updateAndReloadModelOfPlayer(UUID playerUUID, ModelSelection modelSelection) {
        AyameClient.unloadAllModels();
        AyameClient.loadAllModelLocal();
        updateModelOfPlayer(playerUUID, modelSelection);
    }

    /**
     * 更新玩家的客户端侧的实体数据
     *
     * @param playerUUID     玩家uuid
     * @param modelSelection 实体数据
     */
    public void updateModelOfPlayer(UUID playerUUID, ModelSelection modelSelection) {
        this.playerModelSelections.put(playerUUID, modelSelection);
        // 同时加载脚本,调用脚本的onStart()
        JavaScriptLoader.runJs();
    }

    public Stream<InMemoryModelData> filterOutDefaultModel() {
        return this.loadedModels.stream().filter(InMemoryModelData::isDefaultModel);
    }

    /**
     * 获取默认模型的实体数据
     *
     * @return 在第一位的默认模型的实体数据
     */
    public ModelSelection getDefaultModelFallback() {
        final Optional<InMemoryModelData> got = this.filterOutDefaultModel().findFirst();

        if (got.isEmpty()) {
            throw new IllegalStateException("No default model found!");
        }

        final InMemoryModelData actual = got.get();

        return actual.getFallbackModelSelection();
    }

    /**
     * 获取或临时创建玩家的实体数据
     *
     * @param playerUUID 玩家uuid
     * @return 新的或者已经发给客户端的实体数据
     */
    public ModelSelection getModelOfPlayer(UUID playerUUID) {
        final Optional<InMemoryModelData> defaultModelResource = this.filterOutDefaultModel().findFirst();

        if (defaultModelResource.isEmpty()) {
            throw new IllegalStateException("No default models has been found!");
        }

        return this.playerModelSelections.computeIfAbsent(playerUUID, unused -> defaultModelResource.get().getFallbackModelSelection());
    }

    /**
     * 检查是否有某个模型
     *
     * @param id 模型id
     * @return 有为true, 无为false
     */
    public boolean hasModel(String id) {
        for (InMemoryModelData loaded : this.loadedModels) {
            if (loaded.getId().equals(id)) {
                return true;
            }
        }

        return false;
    }

    /**
     * 获取某个模型
     *
     * @param id 模型id
     * @return 模型的内存中数据, 如果没有这个模型则返回null
     */
    @Nullable
    public InMemoryModelData getModel(String id) {
        for (InMemoryModelData loaded : this.loadedModels) {
            if (loaded.getId().equals(id)) {
                return loaded;
            }
        }

        return null;
    }

    /**
     * 获取全部模型
     *
     * @return 全部模型的复制
     */
    public Collection<InMemoryModelData> getAllModels() {
        return new ArrayList<>(this.loadedModels); // Copy to ensure safe
    }
}
