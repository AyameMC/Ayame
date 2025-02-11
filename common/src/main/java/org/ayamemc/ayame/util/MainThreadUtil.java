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

package org.ayamemc.ayame.util;

import net.minecraft.client.Minecraft;

public class MainThreadUtil {
    private static final Class<?> MOONRISE_TICK_THREAD_CLASS;

    // Due to that we cannot access minecraft server directly
    private static volatile Thread mainThreadServer;

    static {
        Class<?> temp;
        try {
            temp = Class.forName("ca.spottedleaf.moonrise.common.util.TickThread");
        }catch (ClassNotFoundException e) {
            temp = null;
        }
        MOONRISE_TICK_THREAD_CLASS = temp;
    }

    /**
     * Check current if it is running on the main thread of the client
     * @return true if it is running on the main thread of the client
     */
    public static boolean runningOnClientMain() {
        final Thread currentThread = Thread.currentThread();

        // Considering for the compatibility with Moonrise
        if (MOONRISE_TICK_THREAD_CLASS != null && MOONRISE_TICK_THREAD_CLASS.isAssignableFrom(currentThread.getClass())) {
            return true;
        }

        return Minecraft.getInstance().isSameThread();
    }

    public static void setMainThreadServer(Thread thread) {
        ensureThreadNoDuplicateServer();
        mainThreadServer = thread;
    }

    // Idk if it is necessary to do such a check
    private static void ensureThreadNoDuplicateServer() {
        if (mainThreadServer != null) {
            throw new IllegalStateException("Main thread was already set!(Running more than one instance on a single JVM?)");
        }
    }

    /**
     * Check current if it is running on the main thread of the server
     * @return true if it is running on the main thread of the server
     */
    public static boolean runningOnServerMain() {
        final Thread currentThread = Thread.currentThread();

        // Considering for the compatibility with Moonrise
        if (MOONRISE_TICK_THREAD_CLASS != null && MOONRISE_TICK_THREAD_CLASS.isAssignableFrom(currentThread.getClass())) {
            return true;
        }

        // Server haven't started yet, bypass
        if (mainThreadServer == null) {
            return true;
        }

        return mainThreadServer == currentThread;
    }
}
