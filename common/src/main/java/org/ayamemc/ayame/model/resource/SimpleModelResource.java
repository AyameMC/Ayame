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

package org.ayamemc.ayame.model.resource;

import org.ayamemc.ayame.model.IndexData;
import org.ayamemc.ayame.util.JsonInterpreter;

import java.io.InputStream;
import java.util.List;

public class SimpleModelResource implements IModelResource {
    private final ModelResourceRegistry.ModelFile modelFile;
    private final IndexData.ModelMetaData metaData;

    public SimpleModelResource(ModelResourceRegistry.ModelFile modelFile) {
        this.modelFile = modelFile;
        this.metaData = createMetaData();
    }

    private IndexData.ModelMetaData createMetaData() {
        return IndexData.ModelMetaData.Builder.create().parseJson(modelFile.getIndexJson()).build();
    }

    @Override
    public IndexData.ModelMetaData getMetaData() {
        return this.metaData;
    }

    @Override
    public List<IndexData.ModelData> getModels() {
        return List.of(IndexData.ModelData.Builder.create()
                .model("model.json")
                .name(getMetaData().name())
                .animation("animation.json")
                .texture("texture.png")
                .build()
        );
    }

    @Override
    public JsonInterpreter getModelJson(IndexData.ModelData model) {
        return JsonInterpreter.of(modelFile.getContent(model.model()));
    }

    @Override
    public JsonInterpreter getAnimationJson(IndexData.ModelData model) {
        return JsonInterpreter.of(modelFile.getContent(model.animation()));
    }

    @Override
    public InputStream getTexture(IndexData.ModelData model) {
        return modelFile.getContent(model.texture());
    }
}
