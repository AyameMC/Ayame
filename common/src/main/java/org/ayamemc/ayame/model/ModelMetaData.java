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

package org.ayamemc.ayame.model;

import org.ayamemc.ayame.util.JsonInterpreter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

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

        public ModelMetaData build() {
            return new ModelMetaData(authors, name, description, license, links, tags, type, version, animations);
        }
    }

    public static class DefaultModelTypes {
        public static final String AYAME = "ayame";
        public static final String YSM = "ysm";
    }
}
