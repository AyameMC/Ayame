/*
 *     Custom player model mod. Powered by GeckoLib.
 *     Copyright (C) 2024  CrystalNeko, HappyRespawnanchor, pertaz(Icon Designer)
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

/**
 * 检测Java类是否存在的工具类
 */
public class JavaUtil {
    /**
     * 检测Java类是否存在
     * @param clasName 需要检测的目标类，传入{@link String}类型，例如{@code dev.kingtux.tms.api.TMSKeyBinding}
     * @return {@code boolean}类型，代表该类是否存在
     */
    public static boolean tryClass(String clasName) {
        try {
            Class.forName(clasName);
            return true;
        } catch (ClassNotFoundException e) {
            return false;
        }
    }
}
