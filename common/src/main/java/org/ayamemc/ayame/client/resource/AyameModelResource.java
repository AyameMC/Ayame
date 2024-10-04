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

import com.mojang.blaze3d.platform.NativeImage;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.resources.ResourceLocation;
import org.ayamemc.ayame.client.IAyameClientEvents;
import org.ayamemc.ayame.model.AyameModelType;
import org.ayamemc.ayame.model.DefaultAyameModelType;
import org.ayamemc.ayame.model.IndexData;
import org.ayamemc.ayame.util.JsonInterpreter;
import org.ayamemc.ayame.util.ZipFileManager;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

import static org.ayamemc.ayame.Ayame.LOGGER;
import static org.ayamemc.ayame.Ayame.MOD_ID;
import static org.ayamemc.ayame.util.FormatUtil.cv;

public class AyameModelResource implements IModelResource {
    private final ZipFileManager content;
    private final IndexData index;

    /**
     * @param content 模型内容
     */
    public AyameModelResource(ZipFileManager content) throws IOException {
        this.content = content;
        this.index = createIndexData();
        IAyameClientEvents.Instance.INSTANCE.ModelResource_onResourceCreate(this);
    }

    private IndexData createIndexData() throws IOException {
        return IndexData.Builder.create().parseJson(JsonInterpreter.of(content.readFileContent("index.json"))).build();
    }

    public IndexData.ModelMetaData getMetaData() {
        return index.metaData();
    }

    public String getType() {
        return IndexData.ModelMetaData.DefaultModelTypes.AYAME;
    }


    public ModelDataResource getDefault() {
        try {
            return ModelDataResource.Builder.create().getDefaultFromZip(content).build();
        } catch (IOException e) {
            LOGGER.error("Error when loading default model:", e);
            return null;
        }
    }

    public List<ModelDataResource> getPresets() {
        List<ModelDataResource> res = new ArrayList<>();
        for (IndexData.ModelData data : index.presets()) {
            try {
                res.add(ModelDataResource.Builder.create().getPresetFromZip(data.name(), content).build());
            } catch (IOException e) {
                LOGGER.error("Error when loading preset model:", e);
            }
        }
        return res;
    }


    /**
     * 模型数据资源，对应{@link IndexData.ModelData}
     *
     * @param mainName  主模型名称
     * @param name      当前模型名称
     * @param model
     * @param texture
     * @param animation
     */
    public record ModelDataResource(String mainName, String name, JsonInterpreter model, DynamicTexture texture,
                                    JsonInterpreter animation) {
        /**
         * 使用这个metadata 创建一个{@link DefaultAyameModelType}
         *
         * @param metaData
         * @return
         */
        public AyameModelType getOrCreateResource(IndexData.ModelMetaData metaData) {
            return ModelResourceWriterUtil.addModelResource(this).setMetaData(metaData).build();
        }

        public ResourceLocation createModelResourceLocation() {
            return ResourceLocation.fromNamespaceAndPath(MOD_ID, "geo/ayame/" + cv(mainName) + "/" + cv(name) + ".json");
        }

        public ResourceLocation createTextureResourceLocation() {
            return ResourceLocation.fromNamespaceAndPath(MOD_ID, "textures/ayame/" + cv(mainName) + "/" + cv(name) + ".png");
        }

        public ResourceLocation createAnimationResourceLocation() {
            return ResourceLocation.fromNamespaceAndPath(MOD_ID, "animations/ayame/" + cv(mainName) + "/" + cv(name) + ".json");
        }

        public static class Builder {
            private JsonInterpreter model;
            private DynamicTexture texture;
            private JsonInterpreter animation;
            private String mainName;
            private String name;

            public static Builder create() {
                return new Builder();
            }

            public Builder getPresetFromZip(String presetName, ZipFileManager zip) throws IOException {
                IndexData index = IndexData.Builder.create().parseJson(JsonInterpreter.of(zip.readFileContent("index.json"))).build();
                AtomicReference<IndexData.ModelData> data = new AtomicReference<>();
                Arrays.asList(index.presets()).forEach(d -> {
                    if (d.name().equals(presetName)) {
                        data.set(d);
                    }
                });
                this.model = JsonInterpreter.of(zip.readFileContent(data.get().model()));
                this.texture = new DynamicTexture(NativeImage.read(zip.readFileContent(data.get().texture())));
                this.animation = JsonInterpreter.of(zip.readFileContent(data.get().animation()));
                this.mainName = index.metaData().name();
                this.name = data.get().name();
                return this;
            }

            public Builder getDefaultFromZip(ZipFileManager zip) throws IOException {
                IndexData index = IndexData.Builder.create().parseJson(JsonInterpreter.of(zip.readFileContent("index.json"))).build();
                IndexData.ModelData data = index.defaultModel();
                this.model = JsonInterpreter.of(zip.readFileContent(data.model()));
                this.texture = new DynamicTexture(NativeImage.read(zip.readFileContent(data.texture())));
                this.animation = JsonInterpreter.of(zip.readFileContent(data.animation()));
                this.mainName = index.metaData().name();
                this.name = data.name();
                return this;
            }

            public Builder model(JsonInterpreter model) {
                this.model = model;
                return this;
            }

            public Builder texture(DynamicTexture texture) {
                this.texture = texture;
                return this;
            }

            public Builder animation(JsonInterpreter animation) {
                this.animation = animation;
                return this;
            }

            public Builder mainName(String mainName) {
                this.mainName = mainName;
                return this;
            }

            public Builder name(String name) {
                this.name = name;
                return this;
            }

            public ModelDataResource build() {
                return new ModelDataResource(mainName, name, model, texture, animation);
            }
        }
    }
}
