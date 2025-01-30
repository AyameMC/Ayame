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
 * 模组加载器枚举，表示对应的加载器。
 */
declare enum ModLoader {
    /**
     * 代表 Fabric 加载器。
     *
     * 由于加载原理，Quilt 也会被识别为 Fabric，此特性大概率不会被修复。
     */
    FABRIC = "fabric",
    /**
     * 代表 NeoForge 加载器。
     *
     * 由于加载原理，在 Minecraft 1.20.1，NeoForge 也会被识别为 Forge，此特性永远不会被修复。
     */
    NEOFORGE = "neoforge",
    /**
     * 代表 Forge 加载器。
     *
     * 由于加载原理，在 Minecraft 1.20.1，NeoForge 也会被识别为此项。
     * @see {@link NEOFORGE}
     */
    FORGE = "forge",
}

export = ModLoader;
