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


import java.io.File;
import java.io.IOException;

import static org.ayamemc.ayame.Ayame.LOGGER;

public class ConfigUtil {
    public static final File CONFIG_FILE = new File("config/ayame/config.json");
    public static JsonInterpreter config = JsonInterpreter.of("{}");

    public static boolean SKIP_AYAME_WARNING = false;

    public static void init() {
        if (!CONFIG_FILE.exists()) {
            // 写入默认配置文件
            FileUtil.copyResource("assets/ayame/config.json", CONFIG_FILE.toPath());
        }
        try {
            config = JsonInterpreter.fromFile(CONFIG_FILE);
        } catch (IOException e) {
            LOGGER.error("Unable to read config file", e);
        }

        SKIP_AYAME_WARNING = config.getBoolean("skipAyameWarning", false);
    }

    /**
     * 保存配置
     */
    public static void save() {
        config.set("skipAyameWarning", SKIP_AYAME_WARNING);
        config.save(CONFIG_FILE.toPath());
    }

}