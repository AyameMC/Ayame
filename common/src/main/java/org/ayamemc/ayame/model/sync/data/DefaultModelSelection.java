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
 *
 * @param geoModel  模型文件
 * @param animation 动画文件
 * @param texture   贴图文件
 * @param id  模型id
 */

public record DefaultModelSelection(ResourceLocation geoModel,
                                    ResourceLocation animation,
                                    ResourceLocation texture,
                                    ResourceLocation arm,
                                    String id,
                                    AyameModelData.ScriptData scriptData
) implements ModelSelection {

    @Override
    public @NotNull CompoundTag serializeToNbt() {
        final CompoundTag built = new CompoundTag();

        built.putString("geo_model", this.geoModel.toString());
        built.putString("animation", this.animation.toString());
        built.putString("texture", this.texture.toString());
        built.putString("arm", this.arm.toString());
        built.putString("model_id", this.id);

        final CompoundTag scriptData = new CompoundTag();

        scriptData.putString("config", this.scriptData.config);
        scriptData.putString("main", this.scriptData.main);

        built.put("script_data", scriptData);

        return built;
    }

    @Override
    public ModelSelection fromNbt(CompoundTag tag) {
        final CompoundTag scriptDataNbt = (CompoundTag) tag.get("script_data");

        final AyameModelData.ScriptData scriptData = new AyameModelData.ScriptData();

        scriptData.config = scriptDataNbt.getString("config");
        scriptData.main = scriptDataNbt.getString("main");

        return new DefaultModelSelection(
                ResourceLocation.parse(tag.getString("geo_model")),
                ResourceLocation.parse(tag.getString("animation")),
                ResourceLocation.parse(tag.getString("texture")),
                ResourceLocation.parse(tag.getString("arm")),
                tag.getString("model_id"),
                scriptData
        );
    }

    @Override
    public ResourceLocation getGeoModel() {
        return geoModel;
    }

    @Override
    public ResourceLocation getTexture() {
        return texture;
    }

    @Override
    public ResourceLocation getAnimation() {
        return animation;
    }

    @Override
    public ResourceLocation getArm() {
        return arm;
    }

    @Override
    public String getId() {
        return this.id;
    }

    @Override
    public ModelSelection withArm(ResourceLocation location) {
        return new DefaultModelSelection(this.geoModel, this.animation, this.texture, location, this.id, this.scriptData);
    }

    @Override
    public ModelSelection withGeoModel(ResourceLocation location) {
        return new DefaultModelSelection(location, this.animation, this.texture, this.arm, this.id, this.scriptData);
    }

    @Override
    public ModelSelection withTexture(ResourceLocation location) {
        return new DefaultModelSelection(this.geoModel, this.animation, location, this.arm, this.id, this.scriptData);
    }

    @Override
    public ModelSelection withAnimation(ResourceLocation location) {
        return new DefaultModelSelection(this.geoModel, location, this.texture, this.arm, this.id, this.scriptData);
    }

    @Override
    public ModelSelection withScriptData(AyameModelData.ScriptData scriptData) {
        return new DefaultModelSelection(this.geoModel, this.animation, this.texture, this.arm, this.id, scriptData);
    }

    public static class Builder {
        private ResourceLocation geoModel;
        private ResourceLocation animation;
        private ResourceLocation texture;
        private ResourceLocation arm;
        private String id;
        private AyameModelData.ScriptData scriptData;


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

        public DefaultModelSelection build() {
            return new DefaultModelSelection(geoModel, animation, texture, arm, this.id, scriptData);
        }
    }
}
