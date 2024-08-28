/*
 *      This file is part of Ayame.
 *
 *     Ayame is free software: you can redistribute it and/or modify it under the terms of the GNU Lesser General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.
 *
 *     Ayame is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more details.
 *
 *     You should have received a copy of the GNU Lesser General Public License along with Ayame. If not, see <https://www.gnu.org/licenses/>.
 */

package org.ayamemc.ayame.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class JsonUtil {

    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    /**
     * Reads a JSON file and converts it into a JsonObject.
     *
     * @param file the JSON file to read.
     * @return the JsonObject read from the file, or a new JsonObject if the file does not exist.
     */
    public static JsonObject readJsonFile(File file) {
        try (FileReader reader = new FileReader(file)) {
            return JsonParser.parseReader(reader).getAsJsonObject();
        } catch (IOException e) {
            e.printStackTrace();
            return new JsonObject(); // Return empty JsonObject on error
        }
    }

    /**
     * Writes a JsonObject to a JSON file.
     *
     * @param file the file to write to.
     * @param jsonObject the JsonObject to write.
     */
    public static void writeJsonFile(File file, JsonObject jsonObject) {
        try (FileWriter writer = new FileWriter(file)) {
            GSON.toJson(jsonObject, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Reads a JSON file and converts it into a specified class type.
     *
     * @param file the JSON file to read.
     * @param classOfT the class of T.
     * @param <T> the type of the desired object.
     * @return the object read from the JSON file, or null if an error occurs.
     */
    public static <T> T readJsonFile(File file, Class<T> classOfT) {
        try (FileReader reader = new FileReader(file)) {
            return GSON.fromJson(reader, classOfT);
        } catch (IOException e) {
            e.printStackTrace();
            return null; // Return null on error
        }
    }

    /**
     * Writes an object to a JSON file.
     *
     * @param file the file to write to.
     * @param object the object to write.
     */
    public static void writeJsonFile(File file, Object object) {
        try (FileWriter writer = new FileWriter(file)) {
            GSON.toJson(object, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Converts a JsonElement to a pretty-printed JSON string.
     *
     * @param element the JsonElement to convert.
     * @return a pretty-printed JSON string.
     */
    public static String toJsonString(JsonElement element) {
        return GSON.toJson(element);
    }
}
