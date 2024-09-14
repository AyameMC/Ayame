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
import org.ayamemc.ayame.model.ModelMetaData;
import org.ayamemc.ayame.util.FileUtil;
import org.ayamemc.ayame.util.JsonInterpreter;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.util.Map;


/**
 * 模型资源
 */
@Environment(EnvType.CLIENT)
public interface IModelResource {
    /**
     * 获取模型元数据
     *
     * @return 模型元数据
     */
    ModelMetaData getMetaData();

    /**
     * 获取模型类型
     *
     * @return 模型类型字符串
     */
    String getType();

    /**
     * 创建模型资源
     */
    void createModel();

    /**
     * 获取模型JSON解析器
     *
     * @return 模型JSON解析器
     */
    JsonInterpreter getModelJson();

    /**
     * 获取动画JSON解析器
     *
     * @return 动画JSON解析器
     */
    JsonInterpreter getAnimationJson();

    /**
     * 获取元数据JSON解析器
     *
     * @return 元数据JSON解析器
     */
    JsonInterpreter getMetaDataJson();

    /**
     * 获取纹理内容输入流
     *
     * @return 纹理内容输入流
     */
    InputStream getTextureContent();

    /**
     * 获取几何模型资源位置
     *
     * @return 几何模型资源位置
     */
    @Nullable
    ResourceLocation getGeoModelLocation();

    /**
     * 获取动画资源位置
     *
     * @return 动画资源位置
     */
    @Nullable
    ResourceLocation getAnimationLocation();

    /**
     * 获取纹理资源位置
     *
     * @return 纹理资源位置
     */
    ResourceLocation getTextureLocation();

    /**
     * 从文件创建模型资源
     * @param file 文件
     * @return 模型资源
     * @throws IOException 读取文件时发生错误
     */
    static IModelResource fromFile(Path file) throws IOException {
        return new AyameModelResource(readZip(file));
    }

    static IModelResource fromFile(File file) throws IOException {
        return fromFile(file.toPath());
    }


    private static Map<String, InputStream> readZip(Path file) {
        return FileUtil.readZipFile(file);
    }

    static DefaultAyameModelType createModelFromResource(IModelResource res) {
        res.createModel();
        return new DefaultAyameModelType(res.getGeoModelLocation(), res.getAnimationLocation(), res.getTextureLocation(), res.getMetaData());
    }
}
