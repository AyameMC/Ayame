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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 模型 ayame.json 的处理
 *
 */
public class IndexData{
    private static final Gson GSON = new GsonBuilder().serializeNulls().setPrettyPrinting().create();
    @SerializedName("format")
    public String format;
    @SerializedName("metadata")
    public ModelMetaData metadata;
    @SerializedName("models")
    public List<ModelData> models;
    @SerializedName("script")
    public ScriptData script;

    /**
     * 单个模型数据

     */
    public static class ModelData {
        @SerializedName("name")
        public String name;
        @SerializedName("model")
        public String model;
        @SerializedName("animation")
        public String animation;
        @SerializedName("texture")
        public String texture;
        @SerializedName("arm")
        public String arm;
        @SerializedName("controllers")
        public List<String> controllers = new ArrayList<>();

    }

    /**
     * 模型元数据
     *
     */
    public static class ModelMetaData{
        @SerializedName("format")
        public String format;
        @SerializedName("authors")
        public List<String> authors;
        @SerializedName("name")
        public String name;
        @SerializedName("description")
        public String description;
        @SerializedName("license")
        public String license;
        @SerializedName("links")
        public List<String> links;
        @SerializedName("tags")
        public List<String> tags;
        @SerializedName("version")
        public String version;

    }

    public static class ScriptData{
        @SerializedName("main")
        public String main;
        @SerializedName("config")
        public String config;
    }

    public static IndexData parse(String jsonString){
        return GSON.fromJson(jsonString, IndexData.class);
    }
    public static String toJson(IndexData indexData){
        return GSON.toJson(indexData);
    }
}
