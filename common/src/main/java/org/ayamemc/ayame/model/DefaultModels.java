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

public class DefaultModels {
    // TODO: 修复使用外置模型（内置模型没问题）时neoforge进世界ayame:geo/ayame_chan.json: Unable to find model的问题
    /*public static final IModelResource AYAME_CHAN_RESOURCE = create("ayame_chan");
    public static final ModelSelection AYAME_CHAN_TYPE = ModelResourceLoadUtil.registerModelSafely(AYAME_CHAN_RESOURCE).build();
    public static final ModelSelection BUILTIN_MODEL_TYPE;

    static {
        final var metaData = new AyameModelData.MetaData();
        metaData.name = "Ayame酱";
        metaData.id = "ayame_chan";
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
        BUILTIN_MODEL_TYPE = DefaultModelSelection.Builder.create()
                .setGeoModel(withAyamePath("geo/ayame/ayame_chan.json"))
                .setAnimation(withAyamePath("animations/ayame/ayame_chan.json"))
                .setTexture(withAyamePath("textures/ayame/ayame_chan.png"))
                .setArm(withAyamePath("textures/ayame/ayame_chan_arm.png"))
                .setMetaData(metaData)
                .build();
    }*/

    // 静态初始化
    public static void init() {
    }

    @SuppressWarnings("SameParameterValue")
    private static void create(String name) {
        /*final String targetPath = AyameModelResource.MODEL_PATH + name;
        FileUtil.copyAyameBuiltinDirectoryToDirectory("models/ayame_chan/", targetPath);
        try {
            return ModelResourceRegistry.create(ModelContent.create().createDirectoryPack(Path.of(targetPath)));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }*/
    }
}
