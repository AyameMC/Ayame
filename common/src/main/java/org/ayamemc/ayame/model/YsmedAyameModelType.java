/*
 *      Custom player model mod. Powered by GeckoLib.
 *      Copyright (C) 2024  CrystalNeko, HappyRespawnanchor, pertaz(Icon Desiger)
 *
 *      This file is part of Ayame.
 *
 *     Ayame is free software: you can redistribute it and/or modify it under the terms of the GNU Lesser General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.
 *
 *     Ayame is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more details.
 *
 *     You should have received a copy of the GNU Lesser General Public License along with Ayame. If not, see <https://www.gnu.org/licenses/>.
 */

package org.ayamemc.ayame.model;

import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;


/**
 * 兼容YSM模组格式的模型
 * 暂未使用
 */
public class YsmedAyameModelType implements AyameModelType {
    private final ResourceLocation model;
    private final ResourceLocation texture;
    private final ResourceLocation animation;

    public YsmedAyameModelType(@NotNull ResourceLocation model, @NotNull ResourceLocation texture, @NotNull ResourceLocation animation) {
        this.model = model;
        this.texture = texture;
        this.animation = animation;
    }

    @Override
    public ResourceLocation getGeoModel() {
        return this.model;
    }

    @Override
    public ResourceLocation getTexture() {
        return this.texture;
    }

    @Override
    public ResourceLocation getAnimation() {
        return this.animation;
    }

    // TODO 完善YSM模型metadata创建
    @Override
    public ModelMetaData metaData() {
        return ModelMetaData.Builder.create().setType(ModelMetaData.DefaultModelTypes.YSM).build();
    }
}
