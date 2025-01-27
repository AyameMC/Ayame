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

package org.ayamemc.ayame.client;

import org.ayamemc.ayame.Ayame;
import org.ayamemc.ayame.client.script.JavaScriptCore;
import org.ayamemc.ayame.model.resource.ModelScanner;
import org.ayamemc.ayame.util.ConfigUtil;
import org.mozilla.javascript.Context;
import org.mozilla.javascript.Scriptable;

import java.io.FileReader;


public class AyameClient {
    public static void init() {
        ConfigUtil.init();
        runJs();
        // 扫描模型
        ModelScanner.scanModel();
    }

    private static void runJs() {
        try (Context context = Context.enter()) {
            try {
                context.setInterpretedMode(false); // 禁用优化以支持动态特性
                Scriptable scope = context.initStandardObjects();

                // 注入 Ayame、Logger、ModLoader、PlayerEvents 类
                scope.put("Ayame", scope, Context.javaToJS(new JavaScriptCore.Ayame(), scope));
                scope.put("Logger", scope, Context.javaToJS(new JavaScriptCore.Logger(), scope));
                scope.put("ModLoader", scope, Context.javaToJS(new JavaScriptCore.ModLoader(), scope));
                scope.put("PlayerEvents", scope, Context.javaToJS(new JavaScriptCore.PlayerEvents(), scope));

                // 加载并运行脚本
                context.evaluateReader(scope, new FileReader("/Users/user/Documents/dev/Ayame/common/src/main/resources/assets/ayame/models/ayame_chan/script/main.aym.js"), "main.aym.js", 1, null);

            } catch (Exception e) {
                Ayame.LOGGER.error("Failed to run ayame", e);
            } finally {
                Context.exit();
            }
        }
    }
}
