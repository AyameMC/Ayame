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

/**
 * Molang 类用于操作和获取 Molang 表达式相关的变量。
 */
declare class Molang {
    private constructor();

    /**
     * 获取指定名称的 Molang 变量的值。
     *
     * @param varName 变量名称
     * @returns 返回指定变量的数值。
     * @throws 如果变量不存在或无效，则抛出错误。
     */
    static getVar(varName: string): number;

    /**
     * 将数字装箱为 Molang 数字。
     */
    static valueOf(number: number): Molang;

    /**
     * 将 Molang 数字拆箱为 number。
     */
    valueOf(): number;
}

export = Molang;
