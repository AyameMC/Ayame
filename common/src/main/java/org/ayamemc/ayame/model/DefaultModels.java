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

import org.ayamemc.ayame.Ayame;
import org.ayamemc.ayame.client.util.ModelResourceWriterUtil;
import org.ayamemc.ayame.model.resource.IModelResource;
import org.ayamemc.ayame.model.resource.ModelContent;
import org.ayamemc.ayame.model.resource.ModelResourceRegistry;
import org.ayamemc.ayame.util.FileUtil;

import java.io.InputStream;
import java.nio.file.Path;

import static org.ayamemc.ayame.Ayame.withAyamePath;

public class DefaultModels {
    public static final String MODEL_PATH = "config/ayame/models/";
    public static final IModelResource AYAME_CHAN_RESOURCE = create("ayame_chan/");
    public static final ModelType AYAME_CHAN_TYPE = ModelResourceWriterUtil.addModelResource(AYAME_CHAN_RESOURCE).build();
    public static final ModelType BUILTIN_MODEL_TYPE = DefaultModelType.Builder.create()
            .setGeoModel(withAyamePath("geo/ayame/default.json"))
            .setAnimation(withAyamePath("animations/ayame/default.json"))
            .setTexture(withAyamePath("textures/ayame/default.png"))
            .setArm(withAyamePath("textures/ayame/default_arm.png"))
            .setMetaData(IndexData.ModelMetaData.Builder.create()
                    .setVersion("v1.0.0")
                    .setDescription("为了防止意外崩溃而添加的builtin模型")
                    .setAuthors(new String[]{"hoomwool"})
                    .setLicense("cc0")
                    .setLinks(new String[]{""})
                    .setTags(new String[]{"default"})
                    .build()
            )
            .build();

    // 静态初始化
    public static void init() {
    }

    private static IModelResource create(String name) {
        Path targetPath = Path.of(MODEL_PATH + name);
        FileUtil.copyBuiltinFileToDirectory(Ayame.withAyamePath("models/" + name), targetPath);

        return ModelResourceRegistry.create(ModelContent.create().createDirectoryPack(targetPath));
    }
}
