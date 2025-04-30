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

package org.ayamemc.ayame.client.script.event;

import java.util.ArrayList;
import java.util.List;

import org.ayamemc.ayame.client.script.JavaScriptHelper;
import org.ayamemc.ayame.client.script.JsPlayer;
import org.mozilla.javascript.*;
import org.mozilla.javascript.annotations.JSStaticFunction;

public class JsPlayerTickEvent {
    private static final List<Function> callbacks = new ArrayList<>();

    @JSStaticFunction
    public static void tick(Function callback) {
        callbacks.add(callback);
    }

    public static void trigger(JsPlayer player) {
        Scriptable event = new NativeObject();
        event.put("player", event, player);
        JavaScriptHelper.executeCallbacks(callbacks, event);
    }

    public static void clearCallbacks() {
        callbacks.clear();
    }
}
