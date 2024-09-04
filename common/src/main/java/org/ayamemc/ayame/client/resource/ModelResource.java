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

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import org.ayamemc.ayame.client.AyameClientEvents;
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

import static org.ayamemc.ayame.Ayame.MOD_ID;
import static org.ayamemc.ayame.util.FileUtil.inputStreamToString;

/**
 * 模型资源
 */
@Environment(EnvType.CLIENT)
public class ModelResource {
    private final Map<String, InputStream> content;
    private final ModelMetaData metaData;
    private @Nullable ResourceLocation texture;
    private @Nullable ResourceLocation geoModel;
    private @Nullable ResourceLocation animation;

    /**
     * @param content 模型内容
     */
    public ModelResource(Map<String, InputStream> content) throws IOException {
        this.content = content;
        this.metaData = createMetaData();
        AyameClientEvents.Instance.INSTANCE.ModelResource_onResourceCreate(this);
    }



    private ModelMetaData createMetaData() {
        String type = getType();
        if (type.equalsIgnoreCase(ModelMetaData.DefaultModelTypes.AYAME)) {
            JsonInterpreter json =this.getMetaDataJson();
            return ModelMetaData.Builder.create()
                    .parseJson(json)
                    .build();
        }
        // TODO 完成ysm
        return ModelMetaData.Builder.create().build();
    }
    public ModelMetaData getMetaData() {
        return metaData;
    }

    public String getType() {
        if (content.containsKey("metadata.json")) return ModelMetaData.DefaultModelTypes.AYAME;
        // TODO 完成ysm格式
        return ModelMetaData.DefaultModelTypes.AYAME;
    }

    public void createModel() {
        // 创建任务，在Minecraft能够启动后执行
        Minecraft.getInstance().execute(()-> {
            // 为ayame模型读取
            if (getType().equals(ModelMetaData.DefaultModelTypes.AYAME)) {
                // 创建模型
                ModelResourceWriterUtil.ModelResourceLocationRecord locations = ModelResourceWriterUtil.addModelResource(MOD_ID, this);
                this.animation = locations.animationLocation();
                this.geoModel = locations.modelLocation();
                this.texture = locations.textureLocation();
            }
            // TODO 完成ysm格式
        });
    }


    public JsonInterpreter getModelJson() {
        return JsonInterpreter.of(inputStreamToString(content.get("model.json")));
    }

    public JsonInterpreter getAnimationJson() {
        return JsonInterpreter.of(inputStreamToString(content.get("animation.json")));
    }

    public JsonInterpreter getMetaDataJson() {
        return JsonInterpreter.of(inputStreamToString(content.get("metadata.json")));
    }

    public InputStream getTextureContent() {
        return content.get("texture.png");
    }

    /**
     * 获取模型资源，仅在{@link #createModel}后调用
     * @return 模型资源
     */
    @Nullable
    public ResourceLocation getGeoModelLocation() {
        return geoModel;
    }
    /**
     * 获取动画资源，仅在{@link #createModel}后调用
     * @return 动画资源
     */
    @Nullable
    public ResourceLocation getAnimationLocation() {
        return animation;
    }

    /**
     * 获取材质资源，仅在{@link #createModel}后调用
     * @return 材质资源
     */
    public ResourceLocation getTextureLocation() {
        return texture;
    }

    /**
     * 从文件创建模型资源
     * @param file 文件
     * @return 模型资源
     * @throws IOException 读取文件时发生错误
     */
    public static ModelResource fromFile(Path file) throws IOException {
        return new ModelResource(readZip(file));
    }

    public static ModelResource fromFile(File file) throws IOException {
        return fromFile(file.toPath());
    }


    private static Map<String, InputStream> readZip(Path file) {
        return FileUtil.readZipFile(file);
    }

    public static DefaultAyameModelType createModelFromResource(ModelResource res) {
        res.createModel();
        return new DefaultAyameModelType(res.getGeoModelLocation(), res.getAnimationLocation(), res.getTextureLocation(), res.getMetaData());
    }
}
