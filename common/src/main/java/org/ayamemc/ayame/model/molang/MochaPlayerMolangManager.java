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

package org.ayamemc.ayame.model.molang;


import team.unnamed.mocha.MochaEngine;
import team.unnamed.mocha.runtime.MochaFunction;

public class MochaPlayerMolangManager {
    private static final ThreadLocal<MochaEngine<?>> current = new ThreadLocal<>();

    public static void set(MochaEngine<?> engine) {
        current.set(engine);
    }

    public static boolean isPresent() {
        return current.get() != null;
    }
    public static MochaEngine<?> get() {
        return current.get();
    }

    public static void clear() {
        current.remove();
    }


    public static double execMolang(String molangCode){
        MochaEngine<?> mocha = get();
        return mocha.eval(molangCode);
    }
}
