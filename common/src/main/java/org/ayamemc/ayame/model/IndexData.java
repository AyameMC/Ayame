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

package org.ayamemc.ayame.model;


import net.minecraft.resources.ResourceLocation;
import org.ayamemc.ayame.util.JsonInterpreter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 模型index.json的处理
 *
 * @param defaultModel 默认模型
 * @param presets      预设
 */
public record IndexData(ModelMetaData metaData, ModelData defaultModel, ModelData[] presets) {
    public static class Builder {
        private ModelMetaData metadata = null;
        private ModelData defaultModel = null;
        private ModelData[] presets = new ModelData[0];

        public static Builder create() {
            return new Builder();
        }

        public Builder parseJson(JsonInterpreter json) {
            this.metaData(ModelMetaData.Builder.create().parseJson(json.getJsonInterpreter("metadata")).build());
            this.defaultModel(ModelData.Builder.create().parseJson(json.getJsonInterpreter("default")).build());
            List<ModelData> ps = new ArrayList<>();
            json.getJsonList("presets").forEach(preset -> ps.add(ModelData.Builder.create().parseJson(preset).build()));
            this.presets(ps.toArray(new ModelData[0]));
            return this;
        }

        public Builder metaData(ModelMetaData metaData) {
            this.metadata = metaData;
            return this;
        }

        public Builder defaultModel(ModelData defaultModel) {
            this.defaultModel = defaultModel;
            return this;
        }

        public Builder presets(ModelData... presets) {
            this.presets = Arrays.copyOf(presets, presets.length);
            return this;
        }

        public IndexData build() {
            return new IndexData(metadata, defaultModel, presets);
        }
    }

    /**
     * 单个模型数据
     *
     * @param name        模型名称
     * @param model       模型的json路径
     * @param animation   动画
     * @param texture     材质
     * @param arm         手臂文件
     * @param controllers 控制器
     */
    public record ModelData(String name, String model, String animation, String texture, String arm,
                            String[] controllers) {

        public ModelData build() {
            return new ModelData(name, model, animation, texture, arm, controllers);
        }

        public static class Builder {
            private String name = "Unknown";
            private String model = "Unknown";
            private String animation = "Unknown";
            private String texture = "Unknown";
            private String arm = "Unknown";
            private String[] controllers = new String[0];

            public static Builder create() {
                return new Builder();
            }

            public Builder parseJson(JsonInterpreter json) {
                this.name(json.getString("name"));
                this.model(json.getString("model"));
                this.animation(json.getString("animation"));
                this.texture(json.getString("texture"));
                this.arm(json.getString("arm"));
                this.controllers(json.getStringList("controllers").toArray(new String[0]));
                return this;
            }

            public Builder name(String name) {
                this.name = name;
                return this;
            }

            public Builder model(String model) {
                this.model = model;
                return this;
            }

            public Builder animation(String animation) {
                this.animation = animation;
                return this;
            }

            public Builder texture(String texture) {
                this.texture = texture;
                return this;
            }

            public Builder arm(String arm) {
                this.arm = arm;
                return this;
            }

            public Builder controllers(String... controllers) {
                this.controllers = Arrays.copyOf(controllers, controllers.length);
                return this;
            }

            public ModelData build() {
                return new ModelData(name, model, animation, texture, arm, controllers);
            }
        }
    }

    /**
     * 模型元数据
     *
     * @param authors     作者
     * @param name        名称
     * @param description 描述
     * @param license     许可证
     * @param links       链接
     */
    public record ModelMetaData(@NotNull String[] authors, @NotNull String name, @Nullable String description,
                                @Nullable String license, @Nullable String[] links, @Nullable String[] tags,
                                @NotNull String type, @NotNull String version, @Nullable String[] animations) {


        public JsonInterpreter conversion() {
            JsonInterpreter json = new JsonInterpreter("{}");
            json.set("authors", authors);
            json.set("name", name);
            json.set("description", description);
            json.set("license", license);
            json.set("links", links);
            json.set("tags", tags);
            json.set("type", type);
            json.set("version", version);
            json.set("animations", animations);
            return json;
        }

        public static class Builder {
            private String[] authors = new String[]{};
            private String name = "Unknown";
            private String description = "Unknown";
            private String license = "Unknown";
            private String[] links = new String[]{};
            private String[] tags = new String[]{};
            private String type = "ayame";
            private String version = "1.0.0";
            private String[] animations = new String[]{};

            public static Builder create() {
                return new Builder();
            }

            public Builder setAuthors(String[] authors) {
                this.authors = authors;
                return this;
            }

            public Builder setName(String name) {
                this.name = name;
                return this;
            }

            public Builder setDescription(String description) {
                this.description = description;
                return this;
            }

            public Builder setLicense(String license) {
                this.license = license;
                return this;
            }

            public Builder setLinks(String[] links) {
                this.links = links;
                return this;
            }

            public Builder setTags(String[] tags) {
                this.tags = tags;
                return this;
            }

            public Builder setType(String type) {
                this.type = type;
                return this;
            }

            public Builder setVersion(String version) {
                this.version = version;
                return this;
            }

            public Builder setAnimations(String[] animations) {
                this.animations = animations;
                return this;
            }

            public Builder parseJson(JsonInterpreter json) {
                return this.setName(json.getString("name"))
                        .setType(type)
                        .setAuthors(json.getStringList("authors").toArray(new String[0]))
                        .setDescription(json.getString("description"))
                        .setLinks(json.getStringList("links").toArray(new String[0]))
                        .setLicense(json.getString("license"))
                        .setAnimations(json.getStringList("animations").toArray(new String[0]))
                        .setVersion(json.getString("version"));
            }

            public Builder parseJsonFromResource(ResourceLocation resourceLocation) {
                return parseJson(JsonInterpreter.fromResource("assets/" + resourceLocation.getNamespace() + "/" + resourceLocation.getPath()));
            }

            public ModelMetaData build() {
                return new ModelMetaData(authors, name, description, license, links, tags, type, version, animations);
            }
        }

        public static class DefaultModelTypes {
            public static final String AYAME = "ayame";
        }
    }
}
