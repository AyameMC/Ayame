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

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import org.ayamemc.ayame.client.resource.ModelResource;
import org.ayamemc.ayame.util.FileUtil;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.ayamemc.ayame.Ayame.LOGGER;
/**
 * Ayame的默认演示模型
 */
@Environment(EnvType.CLIENT)
public class DefaultAyameModels {
    public static final String MODEL_PATH = "config/ayame/models/";
    public static AyameModelType TEST_MODEL;
    public static ModelResource TEST_MODEL_RESOURCE;

    public static void init(){
        createModel("grmmy_neko");
        try {
            TEST_MODEL_RESOURCE = ModelResource.fromFile(Path.of(MODEL_PATH + "grmmy_neko.zip"));
            TEST_MODEL = DefaultAyameModelType.createModel(TEST_MODEL_RESOURCE);
        } catch (IOException e) {
            LOGGER.error("Cannot create model: {}", "grmmy_neko" , e);
        }
    }

    private static void createModel(String name){
        Path path = Path.of(MODEL_PATH + name + ".zip");
        // 检查文件是否存在
        if (!Files.exists(path)){
            FileUtil.copyResource("assets/ayame/models/" + name + ".zip", path);
        }
    }
}
