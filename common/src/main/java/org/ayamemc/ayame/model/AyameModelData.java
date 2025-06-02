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

package org.ayamemc.ayame.model;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.SerializedName;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

/**
 * 模型 ayame.json 的处理
 */
public class AyameModelData {
    private static final Gson GSON = new GsonBuilder().serializeNulls().setPrettyPrinting().create();

    @SerializedName("version")
    public short version;
    @SerializedName("metadata")
    public MetaData metadata;
    @SerializedName("model")
    public ModelData model;
    @SerializedName("script")
    public ScriptData script;

    public static AyameModelData parse(String jsonString) {
        return GSON.fromJson(jsonString, AyameModelData.class);
    }

    public static String toJson(AyameModelData ayameModelData) {
        return GSON.toJson(ayameModelData);
    }

    /**
     * 单个模型数据
     */
    public static class ModelData {
        @SerializedName("name")
        @Nullable
        public String name = "default";
        @SerializedName("scale")
        public float scale;
        @SerializedName("model")
        public String model;
        @SerializedName("animation")
        public String animation;
        @SerializedName("texture")
        public String texture;
        @SerializedName("arm")
        public String arm;
        @SerializedName("sub_models")
        public List<ModelData> subModels = new ArrayList<>();
        @SerializedName("controllers")
        public List<String> controllers = new ArrayList<>();
    }

    /**
     * 模型元数据
     */
    public static class MetaData {
        @SerializedName("name")
        public String name;
        @SerializedName("id")
        public String id;
        @SerializedName("authors")
        public List<String> authors;
        @SerializedName("description")
        public String description;
        @SerializedName("license")
        public String license;
        @SerializedName("license_link")
        public String licenseLink;
        @SerializedName("links")
        public List<String> links;
        @SerializedName("tags")
        public List<String> tags;
    }

    public static class ScriptData {
        @SerializedName("main")
        public String main;
    }
}
