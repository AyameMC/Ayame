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

import org.ayamemc.ayame.util.AyameModelScriptException;
import org.mozilla.javascript.annotations.JSStaticFunction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JsLogger {
    private static final Logger LOGGER = LoggerFactory.getLogger("Ayame Model Script");

    @JSStaticFunction
    public static void info(String message) {
        LOGGER.info(message);
    }

    @JSStaticFunction
    public static void info(String message, Object... obj) {
        LOGGER.info(message, obj);
    }

    @JSStaticFunction
    public static void warn(String message) {
        LOGGER.warn(message);
    }

    @JSStaticFunction
    public static void warn(String message, Object... obj) {
        LOGGER.warn(message, obj);
    }

    @JSStaticFunction
    public static void error(String message) {
        LOGGER.error(message);
    }

    @JSStaticFunction
    public static void error(String message, Object... obj) {
        LOGGER.error(message, obj);
    }

    @JSStaticFunction
    public static void debug(String message) {
        LOGGER.debug(message);
    }

    @JSStaticFunction
    public static void debug(String message, Object... obj) {
        LOGGER.debug(message, obj);
    }

    @JSStaticFunction
    public static void fatal(String message) {
        LOGGER.error(message);
        throw new AyameModelScriptException(message);
    }

    @JSStaticFunction
    public static void fatal(String message, Object... obj) {
        LOGGER.error(message, obj);
        throw new AyameModelScriptException(message);
    }
}