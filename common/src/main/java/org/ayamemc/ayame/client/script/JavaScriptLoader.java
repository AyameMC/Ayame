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

import org.ayamemc.ayame.Ayame;
import org.ayamemc.ayame.util.FileUtil;
import org.mozilla.javascript.Context;
import org.mozilla.javascript.Scriptable;
import org.mozilla.javascript.ScriptableObject;

public class JavaScriptLoader {
    public static void runJs() {
        Context context = Context.enter();
        try {
            context.setLanguageVersion(Context.VERSION_ECMASCRIPT);
            context.setInterpretedMode(false); // 禁用优化以支持动态特性
            final Scriptable scope = context.initStandardObjects();

            final Object wrappedAyame = Context.javaToJS(new JsAyame(), scope);
            final Object wrappedLogger = Context.javaToJS(new JsLogger(), scope);
            final Object wrappedModLoader = Context.javaToJS(new JsModLoader(), scope);
            final Object wrappedPlayerEvents = Context.javaToJS(new JsPlayerEvents(), scope);
            ScriptableObject.putProperty(scope, "Mod", wrappedAyame);
            ScriptableObject.putProperty(scope, "Logger", wrappedLogger);
            ScriptableObject.putProperty(scope, "ModLoader", wrappedModLoader);
            ScriptableObject.putProperty(scope, "PlayerEvents", wrappedPlayerEvents);

//            final Object mainFunObj = scope.get("_main", scope);
//            if (!(mainFunObj instanceof Function function)) {
//                throw new RuntimeException("_main should be a function");
//            } else {
//                function.call(context, scope, scope, null);
//            }


            // 加载并运行脚本
            // TODO: 在模型格式中包含脚本
            context.evaluateString(scope, FileUtil.getAyameBuiltinFileResourceAsString("models/ayame_chan/script/main.aym.js"), "main.aym.js", 1, null);

        } catch (Exception e) {
            Ayame.LOGGER.error("Failed to run ayame", e);
        } finally {
            Context.exit();
        }
    }
}
