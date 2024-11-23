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

package org.ayamemc.ayame.model;

import net.minecraft.world.entity.Entity;
import org.ayamemc.ayame.Ayame;
import org.ayamemc.ayame.model.resource.IModelResource;
import org.ayamemc.ayame.model.resource.ModelContent;
import org.ayamemc.ayame.model.resource.ModelResourceRegistry;
import org.ayamemc.ayame.util.FileUtil;

import java.nio.file.Path;


public class DefaultModels {
    public static final String MODEL_PATH = "config/ayame/models/";
    public static final ModelType DEFAULT_MODEL = DefaultModelType.Builder.create()
            .setGeoModel(Ayame.withAyamePath("geo/ayame/default.json"))
            .setAnimation(Ayame.withAyamePath("animations/ayame/default.json"))
            .setTexture(Ayame.withAyamePath("textures/ayame/default.png"))
            .setArm(Ayame.withAyamePath("arm/ayame/default.json"))
            .setMetaData(IndexData.ModelMetaData.Builder.create()
                    .setName("default")
                    .setAuthors(new String[]{"CrystalNeko"})
                    .setDescription("Default model for Ayame")
                    .setVersion("0.0.1")
                    .build()
            )
            .build();

    public static final IModelResource AQUARTER_NEKO_RESOURCE = create("AQuarter_neko");

    // 静态初始化
    public static void init() {
    }

    private static IModelResource create(String name) {
        Path targetPath = Path.of(MODEL_PATH + name + ".zip");
        FileUtil.copyResource("assets/ayame/models/" + name + ".zip", targetPath);
        return ModelResourceRegistry.create(ModelContent.create().createZipPack(targetPath));
    }
}
