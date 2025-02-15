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

import org.ayamemc.ayame.client.script.JsEntity;
import org.ayamemc.ayame.client.script.JsPlayer;
import org.ayamemc.ayame.client.script.JsWorld;
import org.mozilla.javascript.Function;
import org.mozilla.javascript.NativeObject;
import org.mozilla.javascript.Scriptable;
import org.mozilla.javascript.annotations.JSStaticFunction;

import java.util.ArrayList;
import java.util.List;

public class JsAttackEntityEvent {
    private static final List<Function> tickCallbacks = new ArrayList<>();

    // 注册回调函数
    @JSStaticFunction
    public static void register(Function callback) {
        tickCallbacks.add(callback);
    }

    // 触发所有注册的回调函数
    public static void trigger(JsPlayer player, JsWorld world, JsEntity target) {
        Scriptable event = new NativeObject();
        event.put("player", event, player);
        event.put("world", event, world);
        event.put("target", event, target);
        JsEventHelper.executeCallbacks(tickCallbacks, event);
    }
}
