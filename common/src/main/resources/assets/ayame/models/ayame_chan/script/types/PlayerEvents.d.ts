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

import EventHandler from "./EventHandler.js";
import {Level} from "./Level.js";
import {Player} from "./Player.js";

/**
 * 与玩家相关的事件。
 */
declare namespace PlayerEvents {
    interface TickEvent {
        /** 当前世界 */
        level: Level;
        /** 当前玩家 */
        player: Player;
    }

    /**
     * 玩家每 tick 的事件处理器。
     * @see [Tick - Minecraft Wiki](https://minecraft.wiki/w/Tick#Game_tick)
     */
    const tick: EventHandler<TickEvent>;
}

export = PlayerEvents;
