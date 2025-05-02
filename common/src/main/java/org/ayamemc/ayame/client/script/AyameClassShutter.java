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

import org.mozilla.javascript.ClassShutter;

import java.util.List;

public class AyameClassShutter implements ClassShutter {
    private static final AyameClassShutter INSTANCE = new AyameClassShutter();

    private AyameClassShutter() {
    }

    public static AyameClassShutter getInstance() {
        return INSTANCE;
    }

    // 允许访问的类名前缀
    private static final List<String> ALLOWED_PREFIXES = List.of(
            "org.ayamemc.ayame.client.script",
            "org.ayamemc.ayame.util.ModLoader",
            "java.lang"
    );


    @Override
    public boolean visibleToScripts(String fullClassName) {
        // 先拒绝危险类
        if (fullClassName.startsWith("java.lang.reflect") ||
                fullClassName.startsWith("java.lang.invoke")) {
            return false;
        }
        for (String prefix : ALLOWED_PREFIXES) {
            if (fullClassName.startsWith(prefix)) {
                return true;
            }
        }
        return false;
    }
}
