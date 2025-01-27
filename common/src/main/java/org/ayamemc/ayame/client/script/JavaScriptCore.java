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
import org.ayamemc.ayame.util.AyameModelScriptException;
import org.mozilla.javascript.Context;
import org.mozilla.javascript.Function;
import org.mozilla.javascript.NativeObject;
import org.mozilla.javascript.Scriptable;
import org.slf4j.LoggerFactory;

public class JavaScriptCore {
    public static class Ayame{
        public static final String VERSION = org.ayamemc.ayame.Ayame.VERSION;
    }
    public static class PlayerEvents {
        public static void tick(Function callback) {
            System.out.println("Tick event triggered");
            try {
                // 模拟事件对象
                Scriptable event = new NativeObject();
                event.put("level", event, "TestLevel");
                event.put("player", event, new Player("TestPlayer"));
                callback.call(Context.getCurrentContext(), callback, callback, new Object[]{event});
            } catch (Exception e) {
                org.ayamemc.ayame.Ayame.LOGGER.error(e.toString());
            }
        }
    }

    public static class Player {
        public String name;
        public Player(String name) {
            this.name = name;
        }

        public void sendChat(String message) {
            System.out.println(this.name + " sent chat: " + message);
        }
    }
    public static class ModLoader{
        public static final String FABRIC = "fabric";
        public static final String NEOFORGE = "neoforge";
        public static final String FORGE = "forge";
    }
    public static class Logger {
        private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger("Ayame Model Script");

        public static void info(String message) {
            LOGGER.info(message);
        }

        public static void info(String message, Object... obj) {
            LOGGER.info(message, obj);
        }

        public static void warn(String message) {
            LOGGER.warn(message);
        }
        public static void warn(String message, Object... obj) {
            LOGGER.warn(message, obj);
        }

        public static void error(String message) {
            LOGGER.error(message);
        }
        public static void error(String message, Object... obj) {
            LOGGER.error(message, obj);
        }

        public static void debug(String message) {
            LOGGER.debug(message);
        }
        public static void debug(String message, Object... obj) {
            LOGGER.debug(message, obj);
        }

        public static void fatal(String message) {
            LOGGER.error(message);
            throw new AyameModelScriptException(message);
        }
        public static void fatal(String message, Object... obj) {
            LOGGER.error(message, obj);
            throw new AyameModelScriptException(message);
        }
    }
}
