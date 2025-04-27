package org.ayamemc.ayame.client.script.event;

import org.ayamemc.ayame.Ayame;
import org.mozilla.javascript.Context;
import org.mozilla.javascript.Function;
import org.mozilla.javascript.Scriptable;

import java.util.List;

public class JsHelper {
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
        JsAttackEntityEvent.clearCallbacks();
        JsPlayerTickEvent.clearCallbacks();
        JsKeyPressEvent.clearCallbacks();
        JsRouletteOption.clearCallbacks();
    }
}