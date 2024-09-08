/*
 *      Custom player model mod. Powered by GeckoLib.
 *      Copyright (C) 2024  CrystalNeko, HappyRespawnanchor, pertaz(Icon Desiger)
 *
 *      This file is part of Ayame.
 *
 *     Ayame is free software: you can redistribute it and/or modify it under the terms of the GNU Lesser General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.
 *
 *     Ayame is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more details.
 *
 *     You should have received a copy of the GNU Lesser General Public License along with Ayame. If not, see <https://www.gnu.org/licenses/>.
 */

/*
 *      Custom player model mod. Powered by GeckoLib.
 *      Copyright (C) 2024  CrystalNeko, HappyRespawnanchor, pertaz(Icon Desiger)
 *
 *      This file is part of Ayame.
 *
 *     Ayame is free software: you can redistribute it and/or modify it under the terms of the GNU Lesser General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.
 *
 *     Ayame is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more details.
 *
 *     You should have received a copy of the GNU Lesser General Public License along with Ayame. If not, see <https://www.gnu.org/licenses/>.
 */

package org.ayamemc.ayame.model;

import net.minecraft.world.entity.player.Player;
import org.ayamemc.ayame.client.DefaultAyameModels;
import org.jetbrains.annotations.NotNull;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 正在渲染中的模型缓存，它同时运行在服务端和客户端
 */
public class AyameModelCache {
    // 考虑到未来可能涩及多线程操作，所以使用 ConcurrentHashMap
    public static Map<Player, AyameModelType> playerModelCache = new ConcurrentHashMap<>();

    public static void addPlayerModel(Player player, AyameModelType model) {
        playerModelCache.put(player, model);
    }

    public static void removePlayerModel(Player player) {
        playerModelCache.remove(player);
    }

    /**
     * 获取玩家模型，如果没有就返回默认的
     * @param player 玩家
     * @return 玩家模型
     */
    @NotNull
    public static AyameModelType getPlayerModel(Player player) {
        return playerModelCache.getOrDefault(player, DefaultAyameModels.DEFAULT_MODEL);
    }

    public static boolean hasPlayerModel(Player player) {
        return playerModelCache.containsKey(player);
    }

}
