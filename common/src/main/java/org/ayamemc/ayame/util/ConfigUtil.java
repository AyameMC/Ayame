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

import com.google.gson.JsonObject;

import java.io.File;

public class ConfigUtil {

    private static final File CONFIG_FILE = new File("config/Ayame/config.json"); // 写死的配置文件路径
    private JsonObject config; // 用于缓存配置文件内容
    private final boolean defaultSkipAyameWarning = false; // 默认值

    // 构造函数
    public ConfigUtil() {
        ensureConfigDirectoryExists();
        loadConfig();
    }

    /**
     * 确保配置文件目录存在，如果不存在，则创建它。
     */
    private void ensureConfigDirectoryExists() {
        File parentDir = CONFIG_FILE.getParentFile();
        if (!parentDir.exists()) {
            if (parentDir.mkdirs()) {
                System.out.println("创建配置文件目录: " + parentDir.getAbsolutePath());
            } else {
                System.err.println("无法创建配置文件目录: " + parentDir.getAbsolutePath());
            }
        }
    }

    /**
     * 加载配置文件内容到内存中。
     */
    private void loadConfig() {
        if (!CONFIG_FILE.exists()) {
            config = new JsonObject();
            initializeDefaults();
            saveConfig(); // 如果文件不存在，创建一个包含默认值的配置文件
        } else {
            config = JsonUtil.readJsonFile(CONFIG_FILE);
            ensureDefaults(); // 确保默认值存在
        }
    }

    /**
     * 初始化默认配置值。
     */
    private void initializeDefaults() {
        config.addProperty("skipAyameWarning", defaultSkipAyameWarning);
    }

    /**
     * 确保配置文件中存在默认值。
     */
    private void ensureDefaults() {
        if (!config.has("skipAyameWarning")) {
            config.addProperty("skipAyameWarning", defaultSkipAyameWarning);
            saveConfig(); // 保存更新后的配置文件
        }
    }

    /**
     * 保存当前内存中的配置内容到配置文件。
     */
    private void saveConfig() {
        JsonUtil.writeJsonFile(CONFIG_FILE, config);
    }

    /**
     * 获取 skipAyameWarning 配置项的值。
     *
     * @return 如果配置项存在且为 true，返回 true；否则返回 false。
     */
    public boolean getSkipAyameWarning() {
        return config.has("skipAyameWarning") && config.get("skipAyameWarning").getAsBoolean();
    }

    /**
     * 设置 skipAyameWarning 配置项的值。
     *
     * @param skip 设置为 true 或 false。
     */
    public void setSkipAyameWarning(boolean skip) {
        config.addProperty("skipAyameWarning", skip);
        saveConfig(); // 更新配置文件
    }
}