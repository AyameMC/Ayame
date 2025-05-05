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

import org.ayamemc.ayame.model.sync.data.InMemoryModelData;
import org.ayamemc.ayame.util.HashUtils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class ModelCacheDatabase {
    private final Path parentFolder;

    public ModelCacheDatabase(Path parentFolder) throws IOException {
        this.parentFolder = parentFolder;
        Files.createDirectories(this.parentFolder);
    }

    public byte @Nullable [] getCache(String cacheHash) throws IOException {
        final Path target = this.parentFolder.resolve(cacheHash);
        final File cacheFile = target.toFile();

        if (!cacheFile.exists()) {
            return null;
        }

        return Files.readAllBytes(target);
    }

    public boolean hasCache(String cacheHash) {
        final Path target = this.parentFolder.resolve(cacheHash);
        final File cacheFile = target.toFile();

        return cacheFile.exists();
    }

    public boolean removeCache(String cacheHash) {
        final Path target = this.parentFolder.resolve(cacheHash);
        final File cacheFile = target.toFile();

        return cacheFile.delete();
    }

    public void addCache(@NotNull InMemoryModelData cache) throws IOException {
        final ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        final DataOutputStream bufferHelper = new DataOutputStream(buffer);

        cache.serialize(bufferHelper);
        bufferHelper.flush();

        final byte[] data = buffer.toByteArray();
        final String hash = HashUtils.lowercaseHashOf(data);

        final Path target = this.parentFolder.resolve(hash);
        final File cacheFile = target.toFile();

        if (cacheFile.exists()) {
            return;
        }

        Files.write(target, data);
    }
}
