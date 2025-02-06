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

package org.ayamemc.ayame.model.resource;


import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Objects;

import static org.ayamemc.ayame.Ayame.LOGGER;

public class ModelScanner {
    /**
     * 从指定目录扫描模型
     *
     * @param dir 目录
     */
    public static void scanModel(Path dir) {
        // 如果目录不存在，创建
        if (!Files.exists(dir)) {
            try {
                Files.createDirectories(dir);
            } catch (IOException e) {
                LOGGER.error("Failed to create directory:{}", dir, e);
            }
        }
        // 遍历目录
        for (File path : Objects.requireNonNull(dir.toFile().listFiles())) {
            IModelResource res = null;
            // 只扫描当前目录的目录与文件夹（不递归扫描）
            try {
                if (path.isDirectory()) {
                    res = ModelResourceRegistry.create(ModelContent.create().createDirectoryPack(path.toPath()));

                }
                if (path.isFile()) {
                    res = ModelResourceRegistry.create(ModelContent.create().createZipPack(path.toPath()));
                }
            } catch (Exception ignored) {
            } finally {
                if (res != null) {
                    ModelResourceCache.addModelResource(res);
                }
            }

        }

    }

    /**
     * 从默认目录扫描模型
     */
    public static void scanModel() {
        scanModel(Path.of(AyameModelResource.MODEL_PATH));
    }

}
