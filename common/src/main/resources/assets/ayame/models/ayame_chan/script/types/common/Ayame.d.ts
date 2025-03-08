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

import ModLoader from "./ModLoader.js";

/**
 * Ayame 的主要功能入口点，
 * 提供各类实用功能，如模组加载器与 Ayame 版本号。
 */
declare class Ayame {
    /**
     * Ayame 的版本号，例如0.1.0。
     * @example
     * "0.1.0"
     */
    static VERSION: string; // = "0.1.0";

    private constructor();

    /**
     * 检查 Ayame 正在使用的模组加载器，例如 Fabric 或 NeoForge。
     */
    static get modLoader(): ModLoader;
}

export = Ayame;
