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

import org.ayamemc.ayame.util.FileUtil;
import org.ayamemc.ayame.util.JsonInterpreter;
import org.jetbrains.annotations.ApiStatus;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import java.util.zip.ZipFile;

/**
 * 模型格式的注册表
 */
public class ModelResourceRegistry {
    private static final Map<String, ResourceFactory> registry = new HashMap<>();

    /**
     * 注册一个模型格式
     *
     * @param name    模型格式的名称
     * @param factory 模型格式的工厂
     */
    public static void register(String name, ResourceFactory factory) {
        registry.put(name, factory);
    }

    /**
     * 以模型格式的名称获取一个模型格式的实例
     *
     * @param format    模型格式的名称
     * @param modelFile 模型文件
     * @return 模型实例
     */

    public static IModelResource create(String format, ModelFile modelFile) {
        return registry.get(format).create(modelFile);
    }

    /**
     * 获取一个模型格式的实例，根据模型文件的元数据获取模型格式的名称
     *
     * @param modelFile 模型文件
     * @return 模型实例
     */
    public static IModelResource create(ModelFile modelFile) {
        return create(modelFile.getFormat(), modelFile);
    }

    /**
     * 判断一个模型格式是否被注册
     *
     * @param name 模型格式的名称
     * @return 是否被注册
     */
    public static boolean contains(String name) {
        return registry.containsKey(name);
    }

    /**
     * 检查一个模型是否支持被读取
     *
     * @param modelFile 模型文件
     * @return 是否支持被读取
     */
    public static boolean contains(ModelFile modelFile) {
        return contains(modelFile.getFormat());
    }

    @ApiStatus.Internal
    public static void init() {
        // 注册默认的模型格式
        register("Ayame", AyameModelResource::new);
    }

    @FunctionalInterface
    public interface ResourceFactory {
        IModelResource create(ModelFile modelFile);
    }

    public static class ModelFile {
        private final ZipFile zipFile;
        private final Path directory;

        public ModelFile(ZipFile zipFile) {
            this.zipFile = zipFile;
            this.directory = null;
        }

        public ModelFile(Path directory) {
            this.directory = directory;
            this.zipFile = null;
        }

        public JsonInterpreter getIndexJson() {
            if (zipFile != null) {
                return JsonInterpreter.of(FileUtil.getInputStreamFromZip(zipFile, "ayame.json"));
            } else if (directory != null) {
                Path indexPath = directory.resolve("ayame.json");
                if (Files.exists(indexPath)) {
                    try {
                        return JsonInterpreter.of(Files.newInputStream(indexPath));
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                } else {
                    try {
                        throw new IOException("ayame.json not found in directory: " + directory);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
            throw new IllegalStateException("ModelFile must be either a directory or a zip file");
        }

        public String getFormat() {
            return "Ayame";
        }

        public InputStream getContent(String path) {
            if (zipFile != null) {
                return FileUtil.getInputStreamFromZip(zipFile, path);
            } else if (directory != null) {
                Path filePath = directory.resolve(path);
                if (Files.exists(filePath)) {
                    try {
                        return Files.newInputStream(filePath);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
            return null;
        }
    }

}
