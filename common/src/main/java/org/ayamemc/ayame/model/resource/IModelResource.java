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

package org.ayamemc.ayame.model.resource;

import net.minecraft.resources.ResourceLocation;
import org.ayamemc.ayame.Ayame;
import org.ayamemc.ayame.model.AyameModelData;
import org.ayamemc.ayame.util.JsonInterpreter;
import org.ayamemc.ayame.util.TODO;

import java.io.File;
import java.io.InputStream;
import java.util.List;

public interface IModelResource {
    static IModelResource fromFile(File file) {
        throw new TODO("create model from file");
    }

    // TODO 完成
    AyameModelData.MetaData getMetaData();

    default String getName() {
        return getMetaData().name;
    }

    List<AyameModelData.ModelData> getModels();

    JsonInterpreter getModelJson(AyameModelData.ModelData model);

    JsonInterpreter getAnimationJson(AyameModelData.ModelData model);

    InputStream getTexture(AyameModelData.ModelData model);

    JsonInterpreter getArmJson(AyameModelData.ModelData model);

    default AyameModelData.ModelData getDefault() {
        return getModels().getFirst();
    }

    default ResourceLocation createModelResourceLocation() {
        return Ayame.withAyamePath("geo/" + getName() + ".json");
    }

    default ResourceLocation createAnimationResourceLocation() {
        return Ayame.withAyamePath("animations/" + getName() + ".json");
    }

    default ResourceLocation createTextureResourceLocation() {
        return Ayame.withAyamePath("textures/" + getName() + ".png");
    }

    default ResourceLocation createArmResourceLocation() {
        return Ayame.withAyamePath("arms/" + getName() + ".json");
    }
}
