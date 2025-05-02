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

import net.minecraft.server.packs.resources.Resource;
import org.ayamemc.ayame.Constants;
import org.ayamemc.ayame.client.AyameClient;
import org.ayamemc.ayame.client.script.event.JsRouletteOption;
import org.ayamemc.ayame.client.yttribume.Yttribumes;
import org.ayamemc.ayame.model.sync.data.InMemoryModelData;
import org.ayamemc.ayame.util.FileUtil;
import org.ayamemc.ayame.util.ModLoader;
import org.jetbrains.annotations.Nullable;
import org.mozilla.javascript.*;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.UUID;

import static org.ayamemc.ayame.Ayame.LOGGER;
import static org.ayamemc.ayame.Ayame.withAyamePath;
import static org.ayamemc.ayame.client.AyameClient.MINECRAFT;

public class JavaScriptLoader {
    private static final String COMPILE_TS_FUNC_STR = "function compileTs(tsCode){var options={target:ts.ScriptTarget.ES5,module:ts.ModuleKind.CommonJS,removeComments:true};var result=ts.transpileModule(tsCode,{compilerOptions:options});return result.outputText}";
    private static Optional<Resource> optionalTsCompilerSourceCode = getTsSourceCode();

    private static Optional<Resource> getTsSourceCode() {
        return MINECRAFT.getResourceManager().getResource(withAyamePath("script_lib/typescript.min.js"));
    }

    private static Scriptable sharedScope;
    private static Function compileTsFunc;

    public static void runJs() {

        if (optionalTsCompilerSourceCode.isEmpty()) {
            throw new RuntimeException("No compiler source code found");
        }

        new Thread(() -> {
            final Context context = Context.enter();
            context.setClassShutter(AyameClassShutter.getInstance());
            try {
                context.setLanguageVersion(Context.VERSION_ECMASCRIPT);

                final UUID playerUUID = MINECRAFT.player.getUUID();
                final String modelId = AyameClient.modelManagerClient.getModelOfPlayer(playerUUID).getId();
                final @Nullable InMemoryModelData modelSelection = AyameClient.modelManagerClient.getModel(modelId);

                if (modelSelection == null) {
                    LOGGER.warn("No model selected for player: {}", playerUUID);
                    return;
                }

                JavaScriptHelper.clearAllCallbacks();
                final Scriptable scope = createExecutionScope(context);
                scope.delete("getClass");
                final String tscCode = FileUtil.convertInputStreamToString(optionalTsCompilerSourceCode.get().open());
                context.evaluateString(scope, tscCode + COMPILE_TS_FUNC_STR, "typeScript.js", 1, null);

                compileTsFunc = (Function) scope.get("compileTs", scope);
                Path mainScriptPath = Constants.MODELS_DIR.resolve(modelSelection.getId()).resolve(modelSelection.getScriptData().main).normalize();

                LOGGER.info("Running Ayame model script: {}", mainScriptPath);
                String scriptSource = FileUtil.getFileAsString(mainScriptPath);
                Object compiledJs = compileTsFunc.call(context, scope, scope, new Object[]{scriptSource});
                if (!(compiledJs instanceof String jsCode)) {
                    throw new IllegalStateException("TypeScript compilation did not return a JS string.");
                }

                context.evaluateString(scope, jsCode, "main.aym.js", 1, null);
                sharedScope = scope;
            } catch (Exception e) {
                LOGGER.error("Failed to run ayame model script", e);
            } finally {
                Context.exit();
            }
        }, "Ayame-Script-Loader").start();
    }

    //    public static void reload() {
//        sharedScope = null;
//        compileTsFunc = null;
//        runJs();
//    }
    public static void reloadTs() {
        optionalTsCompilerSourceCode = getTsSourceCode();
    }

    public static @Nullable Scriptable getSharedScope() {
        return sharedScope;
    }

    public static @Nullable Function getCompileTsFunc() {
        return compileTsFunc;
    }

    public static @Nullable String getTsVersion() {

        if (optionalTsCompilerSourceCode.isEmpty()) {
            throw new RuntimeException("No compiler source code found");
        }

        final Context context = Context.enter();
        context.setClassShutter(fullClassName -> false);
        try {
            context.setLanguageVersion(Context.VERSION_ECMASCRIPT);
            Scriptable scope = context.initStandardObjects();
            final String tscCode = FileUtil.convertInputStreamToString(optionalTsCompilerSourceCode.get().open());
            context.evaluateString(scope, tscCode, "ts.version.eval", 1, null);

            Object tsObject = scope.get("ts", scope);
            if (tsObject instanceof Scriptable tsScope) {
                Object version = tsScope.get("version", tsScope);
                if (version != Scriptable.NOT_FOUND) {
                    return Context.toString(version);
                }
            }

            return null;
        } catch (IOException e) {
            throw new RuntimeException("Error reading tsc code", e);
        } finally {
            Context.exit();
        }
    }

    public static @Nullable Object runCode(String code) {
        Scriptable scope = getSharedScope();
        if (scope == null) return null;

        final Context context = Context.enter();
        context.setClassShutter(AyameClassShutter.getInstance());
        try {
            context.setLanguageVersion(Context.VERSION_ECMASCRIPT);
            return context.evaluateString(scope, code, "<command>", 1, null);
        } catch (Exception e) {
            LOGGER.error("Error running inline script code", e);
            return e;
        } finally {
            Context.exit();
        }
    }

    private static Scriptable createExecutionScope(Context context) {
        Scriptable scope = context.initStandardObjects();
        injectAyameGlobals(scope);

        return scope;
    }

    private static void injectAyameGlobals(Scriptable scope) {
        ScriptableObject.putProperty(scope, "Ayame", Context.javaToJS(new JsAyame(), scope));
        ScriptableObject.putProperty(scope, "Molang", Context.javaToJS(new JsMolang(), scope));
        ScriptableObject.putProperty(scope, "logger", Context.javaToJS(new JsLogger(), scope));
        ScriptableObject.putProperty(scope, "ModLoader", Context.javaToJS(new ModLoader(), scope));
        ScriptableObject.putProperty(scope, "PlayerEvents", Context.javaToJS(new PlayerEventsWrapper(), scope));
        ScriptableObject.putProperty(scope, "RouletteOption", Context.javaToJS(new JsRouletteOption(), scope));
        ScriptableObject.putProperty(scope, "Entity", Context.javaToJS(new JsEntity(MINECRAFT.player), scope));
        ScriptableObject.putProperty(scope, "Player", Context.javaToJS(new JsPlayer(MINECRAFT.player), scope));
        ScriptableObject.putProperty(scope, "World", Context.javaToJS(new JsWorld(MINECRAFT.level), scope));
        ScriptableObject.putProperty(scope, "yttribume", Context.javaToJS(new JsYttribume(withAyamePath("empty"), Yttribumes.EMPTY), scope));
    }
}


