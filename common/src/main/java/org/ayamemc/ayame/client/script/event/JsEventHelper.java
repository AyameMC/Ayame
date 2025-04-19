package org.ayamemc.ayame.client.script.event;

import org.ayamemc.ayame.Ayame;
import org.mozilla.javascript.Context;
import org.mozilla.javascript.Function;
import org.mozilla.javascript.Scriptable;

import java.util.List;

public class JsEventHelper {
    public static void executeCallbacks(List<Function> callbacks, Object... args) {
        if (callbacks.isEmpty()) return;

        Context context = Context.enter();
        try {
            Scriptable scope = context.initStandardObjects();

            for (Function callback : callbacks) {
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

    public static void clearAllCallbacks() {
        JsAttackEntityEvent.clearCallbacks();
        JsPlayerTickEvent.clearCallbacks();
        JsKeyPressEvent.clearCallbacks();
        JsRouletteOption.clearCallbacks();
    }
}