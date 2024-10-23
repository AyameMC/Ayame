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

package org.ayamemc.ayame.client;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import org.ayamemc.ayame.client.resource.AyameModelResource;
import org.ayamemc.ayame.client.resource.IModelResource;
import org.ayamemc.ayame.model.AyameModelType;
import org.ayamemc.ayame.model.DefaultAyameModelType;
import org.ayamemc.ayame.util.FileUtil;
import org.ayamemc.ayame.util.TaskManager;
import org.ayamemc.ayame.util.ZipFileManager;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.ayamemc.ayame.util.ResourceLocationHelper.withAyameNamespace;


/**
 * Ayame的默认演示模型
 */
@Environment(EnvType.CLIENT)
public class DefaultAyameModels {
    public static final String MODEL_PATH = "config/ayame/models/";

    /**
     * 此模型写死在代码里面，作为默认模型，仅可通过资源包修改
     */
    public static final AyameModelType DEFAULT_MODEL = DefaultAyameModelType.of(
            withAyameNamespace("geo/ayame/default.json"),
            withAyameNamespace("animations/ayame/default.json"),
            withAyameNamespace("textures/ayame/default.png"),
            withAyameNamespace("metadata/ayame/default.json")
    );

    public static IModelResource GRMMY_NEKO_MODEL_RESOURCE;
    public static AyameModelType GRMMY_NEKO_MODEL;

    public static void init() {
        //TaskManager.TaskManagerImpls.CLIENT_IN_WORLD_TASKS.addTask(() -> {
            GRMMY_NEKO_MODEL_RESOURCE = createModelResource("grmmy_neko");
            GRMMY_NEKO_MODEL = createModel(GRMMY_NEKO_MODEL_RESOURCE);
        //});
    }

    // TODO: JSON爆null问题在于此处，res.getDefault传入的content内的index.json为null
    private static @NotNull IModelResource createModelResource(String name) {
        Path path = Path.of(MODEL_PATH + name + ".zip");
        // 检查文件是否存在
        if (!Files.exists(path)) {
            FileUtil.copyResource("assets/ayame/models/" + name + ".zip", path);
        }
        try {
            return IModelResource.fromFile(path);
        } catch (IOException e) {
            // 直接抛出异常
            throw new RuntimeException(e);
        }
    }

    private static AyameModelType createModel(IModelResource res) {
        AyameModelResource.ModelDataResource defaultModel = res.getDefault();
        return defaultModel.getOrCreateResource(res.getMetaData());
    }
}
