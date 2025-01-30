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
 * 表示玩家对象。
 */
declare interface Player {
    /** 玩家 ID */
    get name(): string;

    /**
     * 使玩家向服务器发送一条聊天消息。
     *
     * @param message 要发送的消息
     */
    sendChat(message: string): void;

    /**
     * 使玩家向服务器发送一条命令。
     * @param command 要发送的命令
     */
    sendCommand(command: string): void;
}

export { Player };
