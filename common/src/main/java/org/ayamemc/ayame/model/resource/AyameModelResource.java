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

import org.ayamemc.ayame.model.AyameModelData;
import org.ayamemc.ayame.util.JsonInterpreter;

import java.io.InputStream;
import java.util.List;

public class AyameModelResource implements IModelResource {
    private final ModelResourceRegistry.ModelFile modelFile;
    private final AyameModelData ayameModelData;

    public AyameModelResource(ModelResourceRegistry.ModelFile modelFile) {
        this.modelFile = modelFile;
        this.ayameModelData = AyameModelData.parse(modelFile.getIndexJson().toString());
    }


    @Override
    public AyameModelData.MetaData getMetaData() {
        return this.ayameModelData.metadata;
    }

    @Override
    public List<AyameModelData.ModelData> getModels() {
        return ayameModelData.models;
    }

    @Override
    public JsonInterpreter getModelJson(AyameModelData.ModelData model) {
        return JsonInterpreter.of(modelFile.getContent(model.model));
    }

    @Override
    public JsonInterpreter getAnimationJson(AyameModelData.ModelData model) {
        return JsonInterpreter.of(modelFile.getContent(model.animation));
    }

    @Override
    public InputStream getTexture(AyameModelData.ModelData model) {
        return modelFile.getContent(model.texture);
    }

    @Override
    public JsonInterpreter getArmJson(AyameModelData.ModelData model) {
        return JsonInterpreter.of(modelFile.getContent(model.arm));
    }
}
