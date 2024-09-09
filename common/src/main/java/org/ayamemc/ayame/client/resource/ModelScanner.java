/*
 *      Custom player model mod. Powered by GeckoLib.
 *      Copyright (C) 2024  CrystalNeko, HappyRespawnanchor, pertaz(Icon Desiger)
 *
 *      This file is part of Ayame.
 *
 *     Ayame is free software: you can redistribute it and/or modify it under the terms of the GNU Lesser General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.
 *
 *     Ayame is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more details.
 *
 *     You should have received a copy of the GNU Lesser General Public License along with Ayame. If not, see <https://www.gnu.org/licenses/>.
 */

package org.ayamemc.ayame.client.resource;

import org.ayamemc.ayame.client.DefaultAyameModels;

import java.io.File;
import java.nio.file.Path;
import java.util.Objects;

public class ModelScanner {
    /**
     * 从指定目录扫描模型
     * @param dir 目录
     */
    private static int modelCount = 0;
    public static void scanModel(Path dir) {
        // 遍历目录
        for (File path : Objects.requireNonNull(dir.toFile().listFiles())) {
            // 如果是目录，递归扫描
            if (path.isDirectory()) {
                scanModel(path.toPath());
            } else {
                // 如果是文件，尝试解析为模型资源
                try {
                    IModelResource res = IModelResource.fromFile(path);
                    // 添加到缓存
                    ModelResourceCache.addModelResource(res);
                    // 增加数量
                    addModelCount(1);
                } catch (Exception e) {
                    // 忽略错误
                }
            }
        }
    }

    /**
     * 从默认目录扫描模型
     */
    public static void scanModel(){
        scanModel(Path.of(DefaultAyameModels.MODEL_PATH));
    }

    public static int getModelCount() {
        return modelCount;
    }
    private static void addModelCount(int count) {
        modelCount++;
    }
}
