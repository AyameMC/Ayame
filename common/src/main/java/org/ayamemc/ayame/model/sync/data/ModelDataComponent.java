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

package org.ayamemc.ayame.model.sync.data;

import org.ayamemc.ayame.model.AyameModelData;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class ModelDataComponent{
    private static final String SPECTOR = "/";

    public final AyameModelData modelMeta;
    public final Map<String, byte[]> byteStorage;

    private boolean canUnload = true;
    private boolean loadAsDefaultModel = false;

    public ModelDataComponent(AyameModelData modelMeta, Map<String, byte[]> byteStorage) {
        this.modelMeta = modelMeta;
        this.byteStorage = byteStorage;
    }

    public AyameModelData modelMeta() {
        return this.modelMeta;
    }

    public Map<String, byte[]> byteStorage() {
        return this.byteStorage;
    }

    public boolean canUnload() {
        return this.canUnload;
    }

    public boolean loadAsDefaultModel() {
        return this.loadAsDefaultModel;
    }

    public void setCanUnload(boolean value) {
        this.canUnload = value;
    }

    public void setLoadAsDefaultModel(boolean value) {
        this.loadAsDefaultModel = value;
    }

    @Contract("_ -> new")
    public static @NotNull ModelDataComponent fromMapBuffer(@NotNull Map<String, byte[]> buffer) {
        String metaJsonContent;
        try {
            metaJsonContent = new String(buffer.get("ayame.json"));
        } catch (NullPointerException e) {
            throw new RuntimeException("ayame.json not found!", e.getCause());
        }
        final AyameModelData parsed = AyameModelData.parse(metaJsonContent);

        final Map<String, byte[]> remaining = new HashMap<>();

        for (Map.Entry<String, byte[]> fileEntry : buffer.entrySet()) {
            String fileNameWithPath = fileEntry.getKey();
            final byte[] fileData = fileEntry.getValue();

            fileNameWithPath = fileNameWithPath.replace(File.separator, SPECTOR);

            remaining.put(fileNameWithPath, fileData);
        }

        return new ModelDataComponent(parsed, remaining);
    }
}
