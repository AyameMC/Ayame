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

import com.mojang.blaze3d.platform.NativeImage;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import org.ayamemc.ayame.client.renderer.CustomModelTexture;
import org.ayamemc.ayame.model.AyameModel;
import org.ayamemc.ayame.model.DefaultAyameModel;
import org.ayamemc.ayame.model.ModelMetaData;
import org.ayamemc.ayame.util.FileUtil;
import org.ayamemc.ayame.util.JsonInterpreter;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.util.Map;

import static org.ayamemc.ayame.util.FileUtil.inputStreamToString;

/**
 * 模型资源
 */
@Environment(EnvType.CLIENT)
public class ModelResource {
    private final Path file;
    private final Map<String, InputStream> zip;
    private final ModelMetaData metaData;

    /**
     * @param file 模型文件
     */
    public ModelResource(Path file) {
        this.file = file;
        this.zip = readZip();
        this.metaData = createMetaData();
    }

    public Map<String, InputStream> readZip() {
        return FileUtil.readZipFile(file);
    }

    public ModelMetaData createMetaData() {
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
        if (zip.containsKey("metadata.json")) return ModelMetaData.DefaultModelTypes.AYAME;
        // TODO 完成ysm格式
        return ModelMetaData.DefaultModelTypes.AYAME;
    }

    public AyameModel getModel() {
        // 为ayame模型读取
        if (getType().equals(ModelMetaData.DefaultModelTypes.AYAME)) {
            return DefaultAyameModel.of(metaData);
        }
        // TODO 完成ysm格式
        return null;
    }


    public JsonInterpreter getModelJson() {
        return JsonInterpreter.of(inputStreamToString(zip.get("model.json")));
    }

    public JsonInterpreter getAnimationJson() {
        return JsonInterpreter.of(inputStreamToString(zip.get("animation.json")));
    }

    public JsonInterpreter getMetaDataJson() {
        return JsonInterpreter.of(inputStreamToString(zip.get("metadata.json")));
    }

    public InputStream getTexture() {
        return zip.get("texture.png");
    }

    public ResourceLocation registerTexture() throws IOException {
        return Minecraft.getInstance().getTextureManager().register(metaData.name(),new CustomModelTexture(NativeImage.read(this.getTexture())));
    }

    private static ModelResource fromFile(Path file){
        return new ModelResource(file);
    }

    public static ModelResource addModel(String resourcePath) {
        return ModelResource.fromFile(Path.of(resourcePath));
    }

    public static ModelResource addModel(String resourcePath, boolean fromFile) {
        return ModelResource.fromFile(new File(resourcePath));
    }


    private static ModelResource fromFile(File file){
        return new ModelResource(file.toPath());
    }
}
