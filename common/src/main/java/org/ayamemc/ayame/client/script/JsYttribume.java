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

package org.ayamemc.ayame.client.script;

import net.minecraft.resources.ResourceLocation;
import org.ayamemc.ayame.client.yttribume.Yttribume;
import org.ayamemc.ayame.client.yttribume.Yttribumes;
import org.mozilla.javascript.annotations.JSFunction;
import org.mozilla.javascript.annotations.JSGetter;
import org.mozilla.javascript.annotations.JSStaticFunction;

public class JsYttribume {
    public final Yttribume yttribume;
    public final ResourceLocation id;

    public JsYttribume(ResourceLocation id, Yttribume yttribume) {
        this.id = id;
        this.yttribume = yttribume;
    }

    @JSGetter
    public String getId() {
        return id.toString(); // 返回完整的ResourceLocation字符串
    }


    @JSGetter
    public float getMinValue() {
        return yttribume.min(); // 返回最小值
    }

    @JSGetter
    public float getMaxValue() {
        return yttribume.max(); // 返回最大值
    }

    @JSGetter
    public String getType() {
        return yttribume.type().toString(); // 返回属性类型
    }

    @JSFunction
    public boolean isRegal(float value,JsPlayer player) {
        return yttribume.isRegal(value, player.getEntity()); // 返回是否合法
    }

    @JSStaticFunction
    public static JsYttribume get(String namespace, String path) {
        ResourceLocation id = ResourceLocation.fromNamespaceAndPath(namespace, path); // 创建ResourceLocation
        Yttribume yttribume = Yttribumes.get(id); // 获取Yttribume对象
        return new JsYttribume(id, yttribume); // 返回JsYttribume对象
    }
}
