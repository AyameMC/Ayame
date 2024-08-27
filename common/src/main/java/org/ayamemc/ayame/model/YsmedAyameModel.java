/*
 *      This file is part of Ayame.
 *
 *     Ayame is free software: you can redistribute it and/or modify it under the terms of the GNU Lesser General Public License as published by the Free Software Foundation, either version 3 of the License, or any later version.
 *
 *     Ayame is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more details.
 *
 *     You should have received a copy of the GNU Lesser General Public License along with Ayame. If not, see <https://www.gnu.org/licenses/>.
 */

package org.ayamemc.ayame.model;

import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

public class YsmedAyameModel implements AyameModel{
    private final ResourceLocation model;
    private final ResourceLocation texture;
    private final ResourceLocation animation;
    private final ModelMetaData metaData;
    public YsmedAyameModel(@NotNull ResourceLocation model,@NotNull ResourceLocation texture,@NotNull ResourceLocation animation,/*这里可能不应该传入它*/ @NotNull ModelMetaData metaData) {
        this.model = model;
        this.texture = texture;
        this.animation = animation;
        this.metaData = metaData;
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
    public ModelMetaData getMetaData() {
        return this.metaData;
    }
}
