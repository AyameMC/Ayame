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

package org.ayamemc.ayame.client.resource;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.resources.ResourceLocation;
import org.ayamemc.ayame.model.DefaultAyameModelType;
import org.ayamemc.ayame.model.IndexData;
import org.ayamemc.ayame.util.JsonInterpreter;
import org.ayamemc.ayame.util.ZipFileManager;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.util.List;


/**
 * 模型资源
 */
@Environment(EnvType.CLIENT)
public interface IModelResource {
    /**
     * 从文件创建模型资源
     *
     * @param file 文件
     * @return 模型资源
     * @throws IOException 读取文件时发生错误
     */
    static IModelResource fromFile(Path file) throws IOException {
        return new AyameModelResource(new ZipFileManager(file));
    }

    static IModelResource fromFile(File file) throws IOException {
        return fromFile(file.toPath());
    }

    /**
     * 获取模型元数据
     *
     * @return 模型元数据
     */
    IndexData.ModelMetaData getMetaData();

    /**
     * 获取模型类型
     *
     * @return 模型类型字符串
     */
    String getType();

    /**
     * 获取默认模型
     * @return 默认模型
     */
    AyameModelResource.ModelDataResource getDefault();

    /**
     * 获取预设模型
     * @return 预设模型
     */
    List<AyameModelResource.ModelDataResource> getPresets();

}
