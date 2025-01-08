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

package org.ayamemc.ayame.client.yttribume;

import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.loading.math.MathParser;
import software.bernie.geckolib.loading.math.value.Variable;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import static org.ayamemc.ayame.Ayame.withAyamePath;

public class Yttribumes {
    private static final Map<ResourceLocation, Yttribume> YTTRIBUMES = new HashMap<>();

    public static final Yttribume EMPTY = register(withAyamePath("empty"), new Yttribume(0.0F, 0.0F, 0.0F, Yttribume.AttributeType.MODEL,true)); // 空
    public static final Yttribume MODEL_SCALE = register(withAyamePath("model.scale"), new Yttribume(0.8F, 0.001F, 1000F, Yttribume.AttributeType.MODEL,false)); // 控制渲染缩放
    public static final Yttribume MODEL_ALPHA = register(withAyamePath("model.alpha"), new Yttribume(1.0F, -1.0F, 1.0F, Yttribume.AttributeType.MODEL,false)); // 控制透明度
    public static final Yttribume GLOBAL_SCREEN_SHAKE = register(withAyamePath("global.screen.shake"), new Yttribume(0.0F, 0.0F, 10.0F, Yttribume.AttributeType.GLOBAL,false)); // 抖起来！
    public static final Yttribume GLOBAL_CAMERA_Y_OFFSET = register(withAyamePath("global.camera.y.offset"), new Yttribume(0.0F, -10.0F, 10.0F, Yttribume.AttributeType.GLOBAL,false));
    public static Yttribume register(ResourceLocation id, Yttribume yttribume) {
        YTTRIBUMES.put(id, yttribume);
        // 注册到Molang
        MathParser.registerVariable(new Variable(id.getNamespace()+".yttribume."+id.getPath(),0));
        return yttribume;
    }

    public static Yttribume get(ResourceLocation id) {
        return YTTRIBUMES.getOrDefault(id, EMPTY);
    }

    public static Collection<ResourceLocation> getIds() {
        return YTTRIBUMES.keySet();
    }

    public static void init() {
    }
}
