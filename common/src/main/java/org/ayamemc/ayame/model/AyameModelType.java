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

import net.minecraft.resources.ResourceLocation;

/// Ayame通用模型接口，适用于所有Geo模型
///
/// 模型类型指的是使用了哪种模型，例如ayame的模型类型为`ayame`，兼容ysm的为`ysm`，值与{@link ModelMetaData#type()}的值对应

public interface AyameModelType {
    /**
     * 从geckolib缓存中获取模型资源
     *
     * @return 模型资源
     */
    ResourceLocation getGeoModel();

    ResourceLocation getTexture();

    ResourceLocation getAnimation();

    IndexData.ModelMetaData metaData();

}
