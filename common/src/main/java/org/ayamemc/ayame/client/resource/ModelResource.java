/*
 *      This file is part of Ayame.
 *
 *     Ayame is free software: you can redistribute it and/or modify it under the terms of the GNU Lesser General Public License as published by the Free Software Foundation, either version 3 of the License, or any later version.
 *
 *     Ayame is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more details.
 *
 *     You should have received a copy of the GNU Lesser General Public License along with Ayame. If not, see <https://www.gnu.org/licenses/>.
 */

package org.ayamemc.ayame.client.resource;

import net.minecraft.resources.ResourceLocation;
import org.ayamemc.ayame.client.util.ResourceUtil;
import org.ayamemc.ayame.model.AyameModel;
import org.ayamemc.ayame.model.DefaultAyameModel;
import org.ayamemc.ayame.model.ModelMetaData;
import org.ayamemc.ayame.util.FileUtil;
import org.ayamemc.ayame.util.JsonInterpreter;

import java.io.File;
import java.nio.file.Path;
import java.util.Map;

import static org.ayamemc.ayame.Ayame.MOD_ID;

/**
 * 模型资源
 */
public class ModelResource {
    private final Path file;
    private final Map<String,String> zip;
    private final ModelMetaData metaData;
    /**
     * @param file 模型文件
     */
    public ModelResource(Path file){
        this.file = file;
        this.zip = FileUtil.readZipFile(file);
        this.metaData = getMetaData();
    }

    public Map<String,String> readZip(){
        return FileUtil.readZipFile(file);
    }

    public ModelMetaData getMetaData(){
        String type = getType();
        if (type.equalsIgnoreCase(ModelMetaData.DefaultModelTypes.AYAME)){
            JsonInterpreter json = JsonInterpreter.of(zip.get("metadata.json"));
            return ModelMetaData.Builder.create()
                    .parseJson(json)
                    .build();
        }
        // TODO 完成ysm
        return ModelMetaData.Builder.create().build();
    }

    public String getType(){
        if (zip.containsKey("metadata.json")) return ModelMetaData.DefaultModelTypes.AYAME;
        // TODO 完成ysm格式
        return ModelMetaData.DefaultModelTypes.AYAME;
    }

    public void loadModel(){
        String type = metaData.type();
        String name = metaData.name();
        ResourceUtil.writeResource(
                ResourceLocation.fromNamespaceAndPath(MOD_ID,"geo/"+type+"/"+name+".json")
                ,zip.get("model.json")
        );
        ResourceUtil.writeResource(
                ResourceLocation.fromNamespaceAndPath(MOD_ID,"textures/"+type+"/"+name+".png")
                ,zip.get("texture.png")
        );
        ResourceUtil.writeResource(
                ResourceLocation.fromNamespaceAndPath(MOD_ID,"animations/"+type+"/"+name+".json")
                ,zip.get("animation.json")
        );
        ResourceUtil.writeResource(
                ResourceLocation.fromNamespaceAndPath(MOD_ID,"model_metadata/"+type+"/"+name+".json")
                ,this.metaData.conversion().toString()
        );
        // TODO 完成ysm格式
    }
    public AyameModel getModel(){
        // 为ayame模型读取
        if (getType().equals(ModelMetaData.DefaultModelTypes.AYAME)){
            return DefaultAyameModel.of(metaData);
        }
        // TODO 完成ysm格式
        return null;
    }
}