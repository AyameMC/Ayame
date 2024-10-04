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

public class FormatUtil {
    /**
     * 将输入字符串转换为仅包含小写字母、下划线和数字（1-9）的形式。
     * 如果字符不符合这些条件，则转换为Unicode形式。
     *
     * @param input 输入字符串
     * @return 转换后的字符串
     */
    public static String convertToValidFormat(String input) {
        StringBuilder result = new StringBuilder();
        for (char ch : input.toCharArray()) {
            if (Character.isLowerCase(ch) || ch == '_' || (ch >= '1' && ch <= '9')) {
                result.append(ch);
            } else {
                // 对于不符合条件的字符，将其转换为Unicode形式并添加到结果中
                result.append(String.format("\\u%04x", (int) ch));
            }
        }
        return result.toString();
    }

    /**
     * 封装了{@link #convertToValidFormat(String)}方法。
     *
     * @param input
     * @return
     */
    public static String cv(String input) {
        return convertToValidFormat(input);
    }
}
