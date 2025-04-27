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
import org.ayamemc.ayame.Constants;
import org.ayamemc.ayame.client.AyameClient;
import org.ayamemc.ayame.client.script.event.*;
import org.ayamemc.ayame.client.yttribume.Yttribumes;
import org.ayamemc.ayame.model.sync.data.InMemoryModelData;
import org.ayamemc.ayame.util.FileUtil;
import org.ayamemc.ayame.util.ModLoader;
import org.jetbrains.annotations.Nullable;
import org.mozilla.javascript.Context;
import org.mozilla.javascript.Function;
import org.mozilla.javascript.Scriptable;
import org.mozilla.javascript.ScriptableObject;

import java.nio.file.Path;

import static net.minecraft.client.Minecraft.getInstance;
import static org.ayamemc.ayame.Ayame.*;

public class JavaScriptLoader {
    public static void runJs() {
        new Thread(() -> {
            Context context = Context.enter();
            try {
                // 清理脚本事件
                JsEventHelper.clearAllCallbacks();
                context.setLanguageVersion(Context.VERSION_ECMASCRIPT);
                context.setInterpretedMode(false); // 禁用优化以支持动态特性
                final Scriptable scope = context.initStandardObjects();
                final String modelId = AyameClient.modelManagerClient.getModelOfPlayer(AyameClient.MINECRAFT.player.getUUID()).getId();
                final @Nullable InMemoryModelData modelSelection = AyameClient.modelManagerClient.
                        getModel(modelId);
                String tsc = FileUtil.getAyameBuiltinFileResourceAsString("script_lib/typescript.js");
                context.evaluateString(scope, tsc, "typeScript.js", 1, null);
                Function compileTsFunc = (Function) scope.get("compileTs", scope);
                Path mainScriptPath = Constants.MODELS_DIR.resolve(modelSelection.getId()).resolve(modelSelection.getScriptData().main);
                LOGGER.info(mainScriptPath.toString());
                Object compiledJs = compileTsFunc.call(context, scope, scope, new Object[]{

                        FileUtil.getFileAsString(mainScriptPath)
                });
                final Object wrappedAyame = Context.javaToJS(new JsAyame(), scope);
                final Object wrappedLogger = Context.javaToJS(new JsLogger(), scope);
                final Object wrappedModLoader = Context.javaToJS(new ModLoader(), scope);

                // 事件注册部分
                final Object wrappedPlayerEvents = Context.javaToJS(new PlayerEventsWrapper(), scope);
                final Object wrappedRouletteOption = Context.javaToJS(new JsRouletteOption(), scope);

                // 注册所有事件相关对象
                ScriptableObject.putProperty(scope, "Ayame", wrappedAyame);
                ScriptableObject.putProperty(scope, "logger", wrappedLogger);
                ScriptableObject.putProperty(scope, "ModLoader", wrappedModLoader);
                ScriptableObject.putProperty(scope, "PlayerEvents", wrappedPlayerEvents);
                ScriptableObject.putProperty(scope, "RouletteOption", wrappedRouletteOption);
                ScriptableObject.putProperty(scope, "Entity", Context.javaToJS(new JsEntity(getInstance().player), scope));
                ScriptableObject.putProperty(scope, "Player", Context.javaToJS(new JsPlayer(getInstance().player), scope));
                ScriptableObject.putProperty(scope, "World", Context.javaToJS(new JsWorld(getInstance().level), scope));
                ScriptableObject.putProperty(scope, "yttribume", Context.javaToJS(new JsYttribume(withAyamePath("empty"), Yttribumes.EMPTY), scope));

                String js = Context.toString(compiledJs);
                // 加载并运行脚本
                context.evaluateString(scope, js, "main.aym.js", 1, null);

            } catch (Exception e) {
                Ayame.LOGGER.error("Failed to run ayame model script", e);
            } finally {
                Context.exit();
            }
        }, "Ayame-Script-Loader thread").start();
    }
}
