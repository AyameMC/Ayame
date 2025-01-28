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

import org.mozilla.javascript.Context;
import org.mozilla.javascript.Function;
import org.mozilla.javascript.NativeObject;
import org.mozilla.javascript.Scriptable;
import org.mozilla.javascript.annotations.JSStaticFunction;

import static org.ayamemc.ayame.Ayame.minecraft;

public class JsPlayerEvents {
    @JSStaticFunction
    public static void tick(Function callback) {
        System.out.println("Tick event triggered");
        try {
            // 模拟事件对象
            Scriptable event = new NativeObject();
            event.put("level", event, "TestLevel");
            event.put("player", event, new JsPlayer(minecraft.getGameProfile().getName()));
            callback.call(Context.getCurrentContext(), callback, callback, new Object[]{event});
        } catch (Exception e) {
            org.ayamemc.ayame.Ayame.LOGGER.error(e.toString());
        }
    }
}
