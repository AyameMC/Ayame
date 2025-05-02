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

import org.ayamemc.ayame.model.sync.data.ModelDataComponent;
import org.ayamemc.ayame.util.FileUtil;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.HashMap;
import java.util.Map;

public class FolderBasedModelLoader implements IModelLoader{
    @Override
    public ModelDataComponent loadModel(File modelFile) {
        final Path folder = modelFile.toPath();
        final Map<String, byte[]> fileBuffer = new HashMap<>();

        try {
            Files.walkFileTree(folder, new SimpleFileVisitor<>() {
                @Override
                public @NotNull FileVisitResult visitFile(Path file, @NotNull BasicFileAttributes attrs) throws IOException {
                    final String relativePath = folder.relativize(file).toString();
                    final byte[] data = Files.readAllBytes(file);

                    fileBuffer.put(relativePath, data);

                    return FileVisitResult.CONTINUE;
                }
            });
        }catch (Exception e){
            throw new RuntimeException(e);
        }

        return ModelDataComponent.fromMapBuffer(fileBuffer);
    }

    @Override
    public boolean wantLoad(File modelFile) {
        if (!modelFile.isFile()) {
            // 检查文件夹中是否包含ayame.json配置文件
            File configFile = new File(modelFile, "ayame.json");
            return configFile.exists();
        }
        return false;
    }
}
