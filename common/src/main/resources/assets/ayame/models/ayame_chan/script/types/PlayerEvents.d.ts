import EventHandler from "./EventHandler.d.ts";
import {Level} from "./Level.d.ts";
import {Player} from "./Player.d.ts";

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
