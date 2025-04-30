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
import org.ayamemc.ayame.client.script.event.JsAttackEntityEvent;
import org.ayamemc.ayame.client.script.event.JsKeyPressEvent;
import org.ayamemc.ayame.client.script.event.JsPlayerTickEvent;
import org.ayamemc.ayame.client.script.event.JsRouletteOption;
import org.mozilla.javascript.Context;
import org.mozilla.javascript.Function;
import org.mozilla.javascript.Scriptable;

import java.util.List;

public class JavaScriptHelper {
    public static void executeCallbacks(List<Function> callbacks, Object... args) {
        if (callbacks.isEmpty()) return;

        Context context = Context.enter();
        try {
            Scriptable scope = context.initStandardObjects();

            /*
            java.util.ConcurrentModificationException
	at java.base/java.util.ArrayList$Itr.checkForComodification(ArrayList.java:1095)
	at java.base/java.util.ArrayList$Itr.next(ArrayList.java:1049)
	at knot//org.ayamemc.ayame.client.script.event.JsEventHelper.executeCallbacks(JsEventHelper.java:18)
	at knot//org.ayamemc.ayame.client.script.event.JsPlayerTickEvent.trigger(JsPlayerTickEvent.java:41)
	at knot//org.ayamemc.ayame.client.handler.ClientEventHandler.tick(ClientEventHandler.java:114)
	at knot//net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents.lambda$static$0(ClientTickEvents.java:34)
             */
            for (Function callback : callbacks) { // TODO: 这里多人游戏有些情况会崩溃，待排查
                try {
                    Object[] jsArgs = new Object[args.length];
                    for (int i = 0; i < args.length; i++) {
                        jsArgs[i] = Context.javaToJS(args[i], scope);
                    }
                    callback.call(context, scope, null, jsArgs);
                } catch (Exception e) {
                    Ayame.LOGGER.error("Error triggering callback: ", e);
                }
            }
        } finally {
            Context.exit();
        }
    }

    public static Object executeCallback(Function callback, Object... args) {
        Context context = Context.getCurrentContext();
        boolean newContext = false;

        if (context == null) {
            context = Context.enter();
            newContext = true;
        }
        try {
            Scriptable scope = callback.getParentScope();
            Object[] jsArgs = new Object[args.length];
            for (int i = 0; i < args.length; i++) {
                jsArgs[i] = Context.javaToJS(args[i], scope);
            }
            return callback.call(context, scope, scope, jsArgs);
        } catch (Exception e) {
            Ayame.LOGGER.error("Error triggering callback: ", e);
        } finally {
            if (newContext) {
                Context.exit();
            }
        }
        return null;
    }


    public static void clearAllCallbacks() {
        JsAyame.clearFunctions();
        JsAttackEntityEvent.clearCallbacks();
        JsPlayerTickEvent.clearCallbacks();
        JsKeyPressEvent.clearCallbacks();
        JsRouletteOption.clearCallbacks();
    }
}