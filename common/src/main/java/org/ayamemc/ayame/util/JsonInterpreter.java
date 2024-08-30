/*
 *      Custom player model mod. Based on GeckoLib.
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

package org.ayamemc.ayame.util;

import com.google.gson.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.*;
import java.util.stream.Collectors;

public class JsonInterpreter {
    private final String original;
    private final JsonObject jsonObject;
    private Path filePath;

    /**
     * 将json字符串转换为JsonInterpreter
     *
     * @param jsonString json字符串
     */
    public JsonInterpreter(String jsonString) {
        this.original = jsonString;
        // 创建 Gson 对象
        Gson gson = new Gson();
        // 将 JSON 字符串转换为 JsonObject
        this.jsonObject = gson.fromJson(jsonString, JsonObject.class);
    }

    /**
     * 将JsonObject转换为JsonInterpreter
     *
     * @param jsonObject JsonObject
     */
    public JsonInterpreter(JsonObject jsonObject) {
        this.original = jsonObject.toString();
        this.jsonObject = jsonObject;
    }

    /**
     * 将文件转换为JsonInterpreter
     *
     * @param file 文件
     * @throws IOException 文件读取失败
     */
    public JsonInterpreter(File file) throws IOException {
        this(file.toPath());
    }

    /**
     * 将文件转换为JsonInterpreter
     *
     * @param filePath 文件路径
     */
    public JsonInterpreter(Path filePath) throws IOException {
        this(FileUtil.readFileWithException(filePath));
        this.filePath = filePath;
    }

    public static JsonInterpreter of(String jsonString) {
        return new JsonInterpreter(jsonString);
    }

    public static JsonInterpreter fromFile(File file) throws IOException {
        return new JsonInterpreter(file);
    }

    public static JsonInterpreter fromFile(Path filePath) throws IOException {
        return new JsonInterpreter(filePath);
    }

    /**
     * 读取值
     *
     * @param path 键
     * @return 值
     */
    public JsonElement get(String path) {
        return jsonObject.get(path);
    }

    /**
     * 获取JsonPrimitive
     *
     * @param path 键
     * @return JsonPrimitive或空JsonPrimitive
     */
    public JsonPrimitive getJsonPrimitive(String path) {
        JsonElement element = get(path);
        if (element != null && element.isJsonPrimitive()) {
            return element.getAsJsonPrimitive();
        }
        return new JsonPrimitive("");
    }

    /**
     * 获取JsonArray
     *
     * @param path 键
     * @return JsonArray或空JsonArray
     */
    public JsonArray getJsonArray(String path) {
        JsonElement element = get(path);
        if (element != null && element.isJsonArray()) {
            return element.getAsJsonArray();
        }
        return new JsonArray();
    }

    /**
     * 设置值
     *
     * @param path  键
     * @param value 值
     */
    public void set(String path, Object value) {
        // 如果是JsonInterpreter对象，则转换为JsonElement对象
        if (value instanceof JsonInterpreter) {
            jsonObject.add(path, ((JsonInterpreter) value).jsonObject);
            return;
        }
        if (value instanceof JsonElement) {
            jsonObject.add(path, (JsonElement) value);
            return;
        }
        if (value instanceof String) {
            jsonObject.addProperty(path, (String) value);
            return;
        }
        if (value instanceof Number) {
            jsonObject.addProperty(path, (Number) value);
            return;
        }
        if (value instanceof Boolean) {
            jsonObject.addProperty(path, (Boolean) value);
            return;
        }
        if (value instanceof Character) {
            jsonObject.addProperty(path, (Character) value);
            return;
        }
        if (value instanceof List) {
            List<Object> list = (List<Object>) value;
            JsonArray jsonArray = new JsonArray();
            // 空的List
            if (list.isEmpty()) {
                jsonObject.add(path, jsonArray);
                return;
            }
            // 如果是List<JsonInterpreter>，则转换为JsonElement对象
            if (list.get(0) instanceof JsonInterpreter) {
                for (Object o : list) {
                    jsonArray.add(((JsonInterpreter) o).jsonObject);
                }
                jsonObject.add(path, jsonArray);
                return;
            }
            if (list.get(0) instanceof JsonElement) {
                for (Object o : list) {
                    jsonArray.add((JsonElement) o);
                }
                jsonObject.add(path, jsonArray);
                return;
            }
            if (list.get(0) instanceof String) {
                for (Object o : list) {
                    jsonArray.add((String) o);
                }
                jsonObject.add(path, jsonArray);
                return;
            }
            if (list.get(0) instanceof Number) {
                for (Object o : list) {
                    jsonArray.add((Number) o);
                }
                jsonObject.add(path, jsonArray);
                return;
            }
            if (list.get(0) instanceof Boolean) {
                for (Object o : list) {
                    jsonArray.add((Boolean) o);
                }
                jsonObject.add(path, jsonArray);
                return;
            }
            if (list.get(0) instanceof Character) {
                for (Object o : list) {
                    jsonArray.add((Character) o);
                }
                jsonObject.add(path, jsonArray);
                return;
            }
        }
        this.jsonObject.addProperty(path, value.toString());

    }

    /**
     * 保存到指定文件(如果可以的话)
     *
     * @param filePath 文件路径
     */
    public void save(Path filePath) {
        if (filePath != null) {
            FileUtil.overwriteFile(filePath, this.jsonObject.toString());
        }
    }

    /**
     * 保存到文件
     */
    public void save() {
        save(this.filePath);
    }

    /**
     * 获取String值
     *
     * @param path 键
     * @return String
     */
    public String getString(String path) {
        try {
            return getJsonPrimitive(path).getAsString();
        } catch (Exception e) {
            return "";
        }
    }

    /**
     * 获取String列表
     *
     * @param path 键
     * @return List或空List
     */
    public List<String> getStringList(String path) {
        JsonArray array = getJsonArray(path);
        List<String> list = new ArrayList<>();
        for (JsonElement e : array) {
            if (e.isJsonPrimitive()) {
                list.add(e.getAsJsonPrimitive().getAsString());
            }
        }
        return list;
    }

    /**
     * 获取Float值
     *
     * @param path 键
     * @return Float或0
     */
    public float getFloat(String path) {
        try {
            return getJsonPrimitive(path).getAsFloat();
        } catch (Exception e) {
            return 0;
        }
    }

    /**
     * 获取Double值
     *
     * @param path 键
     * @return Double或0
     */
    public double getDouble(String path) {
        try {
            return getJsonPrimitive(path).getAsDouble();
        } catch (Exception e) {
            return 0;
        }
    }

    /**
     * 获取Int值
     *
     * @param path 键
     * @return Int或0
     */
    public int getInt(String path) {
        try {
            return getJsonPrimitive(path).getAsInt();
        } catch (Exception e) {
            return 0;
        }
    }

    /**
     * 获取Boolean值
     *
     * @param path 键
     * @return Boolean或false
     */
    public boolean getBoolean(String path) {
        try {
            return getJsonPrimitive(path).getAsBoolean();
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 获取Boolean值
     *
     * @param path     键
     * @param defValue 默认值
     * @return boolean或defValue
     */
    public boolean getBoolean(String path, boolean defValue) {
        try {
            return getJsonPrimitive(path).getAsBoolean();
        } catch (Exception e) {
            return defValue;
        }
    }

    /**
     * 获取JsonInterpreter
     *
     * @param path 键
     * @return JsonInterpreter或空JsonInterpreter
     */
    public JsonInterpreter getJsonInterpreter(String path) {
        try {
            return new JsonInterpreter(getJsonPrimitive(path).getAsJsonObject());
        } catch (Exception e) {
            return new JsonInterpreter("{}");
        }
    }

    /**
     * 判断是否包含key
     *
     * @param key key
     * @return 是否包含key
     */
    public boolean contains(String key) {
        return jsonObject.has(key);
    }

    /**
     * 获取Int列表
     *
     * @param path 键
     * @return List或空List
     */
    public List<Integer> getIntList(String path) {
        JsonArray array = getJsonArray(path);
        List<Integer> list = new ArrayList<>();
        for (JsonElement e : array) {
            if (e.isJsonPrimitive()) {
                list.add(e.getAsJsonPrimitive().getAsInt());
            }
        }
        return list;
    }

    /**
     * 获取Double列表
     *
     * @param path 键
     * @return List或空List
     */
    public List<Double> getDoubleList(String path) {
        JsonArray array = getJsonArray(path);
        List<Double> list = new ArrayList<>();
        for (JsonElement e : array) {
            if (e.isJsonPrimitive()) {
                list.add(e.getAsJsonPrimitive().getAsDouble());
            }
        }
        return list;
    }

    /**
     * 获取Float列表
     *
     * @param path 键
     * @return List或空List
     */
    public List<Float> getFloatList(String path) {
        JsonArray array = getJsonArray(path);
        List<Float> list = new ArrayList<>();
        for (JsonElement e : array) {
            if (e.isJsonPrimitive()) {
                list.add(e.getAsJsonPrimitive().getAsFloat());
            }
        }
        return list;
    }

    /**
     * 获取一个JsonInterpreter列表
     *
     * @param path 键
     * @return JsonInterpreter列表或空List
     */
    public List<JsonInterpreter> getJsonList(String path) {
        JsonArray array = getJsonArray(path);
        return array.asList().stream().map(e -> new JsonInterpreter(e.getAsJsonObject())).collect(Collectors.toList());
    }

    /**
     * 获取List
     *
     * @param path 键
     * @return List或空List
     */
    public List<Object> getList(String path) {
        JsonArray array = getJsonArray(path);
        List<Object> list = new ArrayList<>();
        for (JsonElement e : array) {
            if (e.isJsonPrimitive()) {
                list.add(e.getAsJsonPrimitive().getAsJsonObject());
            }
        }
        return list;
    }

    /**
     * 获取原始Json字符串
     *
     * @return 原始Json字符串
     */
    public String getOriginal() {
        return this.original;
    }

    /**
     * 判断值是否相等(忽略大小写)
     *
     * @param obj 对象
     * @return 是否相等
     */
    public boolean equalsCaseIgnoreCase(Object obj) {
        if (obj instanceof JsonInterpreter) {
            JsonInterpreter other = (JsonInterpreter) obj;
            return this.jsonObject.equals(other.jsonObject);
        }
        return obj.toString().equalsIgnoreCase(this.jsonObject.toString());
    }

    /**
     * 判断值是否相等
     *
     * @param obj 对象
     * @return 是否相等
     */
    public boolean equals(Object obj) {
        if (obj instanceof JsonInterpreter) {
            JsonInterpreter other = (JsonInterpreter) obj;
            return this.jsonObject.equals(other.jsonObject);
        }
        return obj.toString().equals(this.jsonObject.toString());
    }

    /**
     * 转换为JsonInterpreter列表(如果是的话)
     *
     * @return JsonInterpreter列表
     */
    public List<JsonInterpreter> toJsonList() {
        if (jsonObject.isJsonArray()) {
            return jsonObject.getAsJsonArray().asList().stream().map(e -> {
                return new JsonInterpreter(e.getAsJsonObject());
            }).collect(Collectors.toList());
        }
        return Collections.emptyList();
    }

    /**
     * 转换为Gson
     *
     * @return Gson
     */
    public JsonObject toGson() {
        return jsonObject;
    }

    private Object convertJsonElementToJavaType(JsonElement element) {
        if (element.isJsonPrimitive()) {
            JsonPrimitive primitive = element.getAsJsonPrimitive();
            if (primitive.isBoolean()) {
                return primitive.getAsBoolean();
            } else if (primitive.isNumber()) {
                return primitive.getAsNumber();
            } else if (primitive.isString()) {
                return primitive.getAsString();
            }
        } else if (element.isJsonArray()) {
            JsonArray array = element.getAsJsonArray();
            List<Object> list = new ArrayList<>();
            for (JsonElement item : array) {
                list.add(convertJsonElementToJavaType(item));
            }
            return list;
        } else if (element.isJsonObject()) {
            JsonObject object = element.getAsJsonObject();
            Map<String, Object> map = new HashMap<>();
            for (Map.Entry<String, JsonElement> entry : object.entrySet()) {
                map.put(entry.getKey(), convertJsonElementToJavaType(entry.getValue()));
            }
            return map;
        }
        return null;
    }

    @Override
    public String toString() {
        return jsonObject.toString();
    }
}
