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

package org.ayamemc.ayame.model.sync.data;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import org.ayamemc.ayame.model.AyameModelData;
import org.ayamemc.ayame.model.sync.ModelSelection;
import org.jetbrains.annotations.NotNull;

/**
 * 默认模型类型，适用于Ayame模型
 * 统一持有 ModelData
 */
public class DefaultModelSelection implements ModelSelection {
    private final ModelData data;

    public DefaultModelSelection(ModelData data) {
        this.data = data;
    }

    @Override
    public @NotNull CompoundTag serializeToNbt() {
        final CompoundTag built = new CompoundTag();
        built.putString("geo_model", data.geoModel().toString());
        built.putString("animation", data.animation().toString());
        built.putString("texture", data.texture().toString());
        built.putString("arm", data.arm().toString());
        built.putString("model_id", data.id());
        built.putFloat("scale", data.scale());
        final CompoundTag scriptData = new CompoundTag();
        scriptData.putString("main", data.scriptData().main);
        built.put("script_data", scriptData);
        return built;
    }

    @Override
    public ModelSelection fromNbt(CompoundTag tag) {
        final CompoundTag scriptDataNbt = (CompoundTag) tag.get("script_data");
        final AyameModelData.ScriptData scriptData = new AyameModelData.ScriptData();
        scriptData.main = scriptDataNbt.getString("main");
        ModelData modelData = new ModelData(
                ResourceLocation.parse(tag.getString("geo_model")),
                ResourceLocation.parse(tag.getString("animation")),
                ResourceLocation.parse(tag.getString("texture")),
                ResourceLocation.parse(tag.getString("arm")),
                tag.getString("model_id"),
                scriptData,
                tag.contains("scale") ? tag.getFloat("scale") : 1.0f
        );
        return new DefaultModelSelection(modelData);
    }

    @Override
    public ResourceLocation getGeoModel() {
        return data.geoModel();
    }

    @Override
    public ResourceLocation getTexture() {
        return data.texture();
    }

    @Override
    public ResourceLocation getAnimation() {
        return data.animation();
    }

    @Override
    public ResourceLocation getArm() {
        return data.arm();
    }

    @Override
    public String getId() {
        return data.id();
    }

    @Override
    public AyameModelData.ScriptData scriptData() {
        return data.scriptData();
    }

    public float getScale() {
        return data.scale();
    }

    public ModelData getModelData() {
        return data;
    }

    @Override
    public ModelSelection withArm(ResourceLocation location) {
        return new DefaultModelSelection(new ModelData(data.geoModel(), data.animation(), data.texture(), location, data.id(), data.scriptData(), data.scale()));
    }

    @Override
    public ModelSelection withGeoModel(ResourceLocation location) {
        return new DefaultModelSelection(new ModelData(location, data.animation(), data.texture(), data.arm(), data.id(), data.scriptData(), data.scale()));
    }

    @Override
    public ModelSelection withTexture(ResourceLocation location) {
        return new DefaultModelSelection(new ModelData(data.geoModel(), data.animation(), location, data.arm(), data.id(), data.scriptData(), data.scale()));
    }

    @Override
    public ModelSelection withAnimation(ResourceLocation location) {
        return new DefaultModelSelection(new ModelData(data.geoModel(), location, data.texture(), data.arm(), data.id(), data.scriptData(), data.scale()));
    }

    @Override
    public ModelSelection withScriptData(AyameModelData.ScriptData scriptData) {
        return new DefaultModelSelection(new ModelData(data.geoModel(), data.animation(), data.texture(), data.arm(), data.id(), scriptData, data.scale()));
    }

    public static class Builder {
        private ResourceLocation geoModel;
        private ResourceLocation animation;
        private ResourceLocation texture;
        private ResourceLocation arm;
        private String id;
        private AyameModelData.ScriptData scriptData;
        private float scale = 1.0f;

        public static Builder create() {
            return new Builder();
        }

        public Builder setGeoModel(ResourceLocation geoModel) {
            this.geoModel = geoModel;
            return this;
        }

        public Builder setAnimation(ResourceLocation animation) {
            this.animation = animation;
            return this;
        }

        public Builder setTexture(ResourceLocation texture) {
            this.texture = texture;
            return this;
        }

        public Builder setId(String id) {
            this.id = id;
            return this;
        }

        public Builder setScriptData(AyameModelData.ScriptData scriptData) {
            this.scriptData = scriptData;
            return this;
        }

        public Builder setArm(ResourceLocation arm) {
            this.arm = arm;
            return this;
        }

        public Builder setScale(float scale) {
            this.scale = scale;
            return this;
        }

        public DefaultModelSelection build() {
            return new DefaultModelSelection(new ModelData(geoModel, animation, texture, arm, this.id, scriptData, scale));
        }
    }
}
