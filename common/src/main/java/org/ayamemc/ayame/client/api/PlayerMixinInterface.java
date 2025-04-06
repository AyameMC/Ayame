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

package org.ayamemc.ayame.client.api;

// 扩展方法
public interface PlayerMixinInterface {
    // 有没有被伤害（未完成）
    // TODO: 完成
    default void ayame$setHurting(boolean hurting) {
    }

    default boolean ayame$isHurting() {
        return false;
    }

    // 是否坐下的
    default void ayame$setSitting(boolean sitting) {
    }

    default boolean ayame$isSitting() {
        return false;
    }

    // 播放动画
    default void ayame$playAnimation(String animationName) {

    }
}
