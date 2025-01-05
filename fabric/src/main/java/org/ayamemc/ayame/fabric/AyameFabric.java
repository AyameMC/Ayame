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

package org.ayamemc.ayame.fabric;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.minecraft.server.packs.PackType;
import org.ayamemc.ayame.Ayame;
import org.ayamemc.ayame.client.IAyameClientEvents;
import org.ayamemc.ayame.fabric.client.AyameClientEventsFabricImpl;
import org.ayamemc.ayame.fabric.client.event.ModelReloadEventHandler;
import org.mozilla.javascript.Context;
import org.mozilla.javascript.Scriptable;

/**
 * Fabric初始化使用的类，包括客户端与服务端
 *
 * @see ModInitializer
 */
public final class AyameFabric implements ModInitializer {
    @Override
    public void onInitialize() {
        Ayame.init();
        IAyameClientEvents.Instance.INSTANCE = new AyameClientEventsFabricImpl();
        ResourceManagerHelper.get(PackType.CLIENT_RESOURCES).registerReloadListener(new ModelReloadEventHandler());
        // 创建并进入一个 JavaScript 上下文
        Context context = Context.enter();
        try {
            // 初始化标准对象（例如全局对象）
            Scriptable scope = context.initStandardObjects();

            // JavaScript 代码
            String jsCode = "console.log(11114514)";

            // 执行 JavaScript 代码
            context.evaluateString(scope, jsCode, "HelloWorldScript", 1, null);
        } finally {
            // 退出 JavaScript 上下文
            Context.exit();
        }
    }
}
