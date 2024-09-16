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

package org.ayamemc.ayame.client.api;

import net.minecraft.world.entity.player.Player;
import org.ayamemc.ayame.client.renderer.GeoPlayerRender;
import org.ayamemc.ayame.model.AyameModelCache;
import org.ayamemc.ayame.model.AyameModelType;

public class PlayerModelAPI {
    /**
     * 切换玩家模型的方法，同时告诉给服务端
     * @param player 玩家
     * @param model 模型
     */
    public static void switchModel(Player player, AyameModelType model) {
        GeoPlayerRender.GeoPlayerModel.switchModel(player, model);
    }

    /**
     * 切换玩家模型的方法，只告诉客户端
     * @param player 玩家
     * @param model 模型
     */
    public static void switchModelOnClient(Player player, AyameModelType model) {
        AyameModelCache.setPlayerModel(player, model);
    }
}
