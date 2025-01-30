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

package org.ayamemc.ayame.util;


import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

import java.io.File;
import java.io.IOException;

import static org.ayamemc.ayame.Ayame.LOGGER;

public class ConfigUtil {
    public static final File CONFIG_FILE = new File("config/ayame/config.json");
    private static final Gson GSON = new Gson();
    public static ConfigData config;


    public static void init() {
        if (!CONFIG_FILE.exists()) {
            // 创建空文件
            try {
                CONFIG_FILE.createNewFile();
                config = new ConfigData();
                // 写入默认配置
                FileUtil.overwriteStringToFile(CONFIG_FILE.toPath(), GSON.toJson(config));
            } catch (IOException e) {
                LOGGER.error("Unable to create config file", e);
            }
        }else {
            config = GSON.fromJson(FileUtil.getFileAsString(CONFIG_FILE.toPath()), ConfigData.class);
        }
    }

    /**
     * 保存配置
     */
    public static void save() {
        FileUtil.overwriteStringToFile(CONFIG_FILE.toPath(), GSON.toJson(config));
    }

    public static class ConfigData{
        @SerializedName("skipAyameWarning")
        public boolean skipAyameWarning = false;
    }

}