/*
 *     Custom player model mod. Powered by GeckoLib.
 *     Copyright (C) 2024  CrystalNeko, HappyRespawnanchor, pertaz(Icon Designer)
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

import java.io.InputStream;
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
        register("SIMPLE", SimpleModelResource::new);
    }

    @FunctionalInterface
    public interface ResourceFactory {
        IModelResource create(ModelFile modelFile);
    }

    public static class ModelFile {
        private final ZipFile zipFile;

        public ModelFile(ZipFile zipFile) {
            this.zipFile = zipFile;
        }

        public JsonInterpreter getIndexJson() {
            return JsonInterpreter.of(FileUtil.getInputStreamFromZip(zipFile, "index.json"));
        }

        public String getFormat() {
            return getIndexJson().getString("format");
        }

        public InputStream getContent(String path) {
            return FileUtil.getInputStreamFromZip(zipFile, path);
        }
    }
}
