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


import net.minecraft.resources.ResourceLocation;
import org.ayamemc.ayame.model.AyameModelData;
import org.ayamemc.ayame.model.sync.ModelSelection;

/**
 * 默认模型类型，适用于Ayame模型
 *
 * @param geoModel  模型文件
 * @param animation 动画文件
 * @param texture   贴图文件
 * @param metaData  模型元数据
 */

public record DefaultModelSelection(ResourceLocation geoModel,
                                    ResourceLocation animation,
                                    ResourceLocation texture,
                                    ResourceLocation arm,
                                    AyameModelData.MetaData metaData,
                                    AyameModelData.ScriptData scriptData
) implements ModelSelection {


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
    public AyameModelData.MetaData metaData() {
        return metaData;
    }

    @Override
    public ModelSelection withArm(ResourceLocation location) {
        return new DefaultModelSelection(this.geoModel, this.animation, this.texture, location, this.metaData, this.scriptData);
    }

    @Override
    public ModelSelection withGeoModel(ResourceLocation location) {
        return new DefaultModelSelection(location, this.animation, this.texture, this.arm, this.metaData, this.scriptData);
    }

    @Override
    public ModelSelection withTexture(ResourceLocation location) {
        return new DefaultModelSelection(this.geoModel, this.animation, location, this.arm, this.metaData, this.scriptData);
    }

    @Override
    public ModelSelection withAnimation(ResourceLocation location) {
        return new DefaultModelSelection(this.geoModel, location, this.texture, this.arm, this.metaData, this.scriptData);
    }

    @Override
    public ModelSelection withScriptData(AyameModelData.ScriptData scriptData) {
        return new DefaultModelSelection(this.geoModel, this.animation, this.texture, this.arm, this.metaData, scriptData);
    }

    public static class Builder {
        private ResourceLocation geoModel;
        private ResourceLocation animation;
        private ResourceLocation texture;
        private ResourceLocation arm;
        private AyameModelData.MetaData metaData;
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

        public Builder setMetaData(AyameModelData.MetaData metaData) {
            this.metaData = metaData;
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
            return new DefaultModelSelection(geoModel, animation, texture, arm, metaData, scriptData);
        }
    }
}
