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

import org.ayamemc.ayame.client.util.ModelResourceWriterUtil;
import org.ayamemc.ayame.model.resource.IModelResource;
import org.ayamemc.ayame.model.resource.ModelContent;
import org.ayamemc.ayame.model.resource.ModelResourceRegistry;
import org.ayamemc.ayame.util.FileUtil;

import java.nio.file.Path;
import java.util.List;

import static org.ayamemc.ayame.Ayame.withAyamePath;

public class DefaultModels {
    public static final String MODEL_PATH = "config/ayame/models/";
    public static final IModelResource AYAME_CHAN_RESOURCE = create("ayame_chan");
    public static final ModelType AYAME_CHAN_TYPE = ModelResourceWriterUtil.addModelResource(AYAME_CHAN_RESOURCE).build();
    public static final ModelType BUILTIN_MODEL_TYPE;

    static {
        final var ayameModelData = new AyameModelData();
        final var metaData = new AyameModelData.MetaData();
        metaData.description = "Ayame 的默认演示模型。（内置）";
        metaData.authors = List.of("羊毛wool_Official");
        metaData.license = "CC0-1.0";
        metaData.licenseLink = "https://creativecommons.org/publicdomain/zero/1.0/";
        metaData.links = List.of(
                "https://space.bilibili.com/85335217"
        );
        metaData.tags = List.of(
                "loli",
                "girl"
        );
        BUILTIN_MODEL_TYPE = DefaultModelType.Builder.create()
                .setGeoModel(withAyamePath("geo/ayame/default.json"))
                .setAnimation(withAyamePath("animations/ayame/default.json"))
                .setTexture(withAyamePath("textures/ayame/default.png"))
                .setArm(withAyamePath("textures/ayame/default_arm.png"))
                .setMetaData(metaData)
                .build();
    }

    // 静态初始化
    public static void init() {
    }

    private static IModelResource create(String name) {
        final String targetPath = MODEL_PATH + name;
        FileUtil.copyAyameBuiltinDirectoryToDirectory("models/ayame_chan/", targetPath);

        return ModelResourceRegistry.create(ModelContent.create().createDirectoryPack(Path.of(targetPath)));
    }
}
