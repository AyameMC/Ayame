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
import org.ayamemc.ayame.client.script.event.JsKeyPressEvent;
import org.ayamemc.ayame.client.script.event.JsPlayerTickEvent;
import org.ayamemc.ayame.util.FileUtil;
import org.ayamemc.ayame.util.ModLoader;
import org.mozilla.javascript.Context;
import org.mozilla.javascript.Scriptable;
import org.mozilla.javascript.ScriptableObject;
import static net.minecraft.client.Minecraft.getInstance;
public class JavaScriptLoader {
    public static void runJs() {
        Context context = Context.enter();
        try {
            context.setLanguageVersion(Context.VERSION_ECMASCRIPT);
            context.setInterpretedMode(false); // 禁用优化以支持动态特性
            final Scriptable scope = context.initStandardObjects();

            final Object wrappedAyame = Context.javaToJS(new JsAyame(), scope);
            final Object wrappedLogger = Context.javaToJS(new JsLogger(), scope);
            final Object wrappedModLoader = Context.javaToJS(ModLoader.class, scope);
            final Object wrappedPlayerTickEvent = Context.javaToJS(new JsPlayerTickEvent(), scope);
            final Object wrappedKeyPressEvent = Context.javaToJS(new JsKeyPressEvent(), scope);
            ScriptableObject.putProperty(scope, "Ayame", wrappedAyame);
            ScriptableObject.putProperty(scope, "Logger", wrappedLogger);
            ScriptableObject.putProperty(scope, "ModLoader", wrappedModLoader);
            ScriptableObject.putProperty(scope, "PlayerTickEvent", wrappedPlayerTickEvent);
            ScriptableObject.putProperty(scope, "KeyPressEvent", wrappedKeyPressEvent);
            ScriptableObject.putProperty(scope, "Player", Context.javaToJS(new JsPlayer(getInstance().player), scope));
            ScriptableObject.putProperty(scope, "World", Context.javaToJS(new JsWorld(getInstance().level), scope));

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
