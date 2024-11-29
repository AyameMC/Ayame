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

package org.ayamemc.ayame.model;

import software.bernie.geckolib.animation.RawAnimation;

public class DefaultAnimations {
    // 调试动画
    /** 调试用空动画，通常用于测试或无动画状态。 */
    public static final RawAnimation DEBUG_EMPTY = RawAnimation.begin().thenLoop("debug.empty");

    // 移动动画
    /** 玩家行走时的循环动画。 */
    public static final RawAnimation MOVE_WALK = RawAnimation.begin().thenLoop("move.walk");
    /** 玩家疾跑时的循环动画。 */
    public static final RawAnimation MOVE_RUN = RawAnimation.begin().thenLoop("move.run");
    /** 玩家在活板门下方移动时的循环动画。 */
    public static final RawAnimation MOVE_CLIMBING = RawAnimation.begin().thenLoop("move.climbing");
    /** 玩家在活板门下方爬行时的循环动画。 */
    public static final RawAnimation MOVE_CLIMB = RawAnimation.begin().thenLoop("move.climb");
    /** 玩家潜行但不移动时的循环动画。 */
    public static final RawAnimation MOVE_SNEAKING = RawAnimation.begin().thenLoop("move.sneaking");
    /** 玩家潜行并移动时的循环动画。 */
    public static final RawAnimation MOVE_SNEAK = RawAnimation.begin().thenLoop("move.sneak");
    /** 玩家游泳时的循环动画。 */
    public static final RawAnimation MOVE_SWIM = RawAnimation.begin().thenLoop("move.swim");
    /** 玩家在水中站立式游泳时的循环动画。 */
    public static final RawAnimation MOVE_SWIM_STAND = RawAnimation.begin().thenLoop("move.swim_stand");
    /** 玩家跳跃时的单次播放动画。 */
    public static final RawAnimation MOVE_JUMP = RawAnimation.begin().thenPlay("move.jump");
    /** 玩家在创造模式飞行时的循环动画。 */
    public static final RawAnimation MOVE_FLY = RawAnimation.begin().thenLoop("move.fly");
    /** 玩家鞘翅飞行时的循环动画。 */
    public static final RawAnimation MOVE_ELYTRA_FLY = RawAnimation.begin().thenLoop("move.elytra_fly");
    /** 玩家爬梯子向上移动时的循环动画。 */
    public static final RawAnimation MOVE_LADDER_UP = RawAnimation.begin().thenLoop("move.ladder_up");
    /** 玩家静止在梯子上时的循环动画。 */
    public static final RawAnimation MOVE_LADDER_STILLNESS = RawAnimation.begin().thenLoop("move.ladder_stillness");
    /** 玩家爬梯子向下移动时的循环动画。 */
    public static final RawAnimation MOVE_LADDER_DOWN = RawAnimation.begin().thenLoop("move.ladder_down");

    // 状态动画
    /** 玩家坐在船上时的循环动画。 */
    public static final RawAnimation STATE_BOAT = RawAnimation.begin().thenLoop("state.boat");
    /** 玩家睡觉时的循环动画。 */
    public static final RawAnimation STATE_SLEEP = RawAnimation.begin().thenLoop("state.sleep");
    /** 玩家骑马（或驴）时的循环动画。 */
    public static final RawAnimation STATE_RIDE = RawAnimation.begin().thenLoop("state.ride");
    /** 玩家骑猪时的循环动画。 */
    public static final RawAnimation STATE_RIDE_PIG = RawAnimation.begin().thenLoop("state.ride_pig");
    /** 玩家坐下时的循环动画。 */
    public static final RawAnimation STATE_SIT = RawAnimation.begin().thenLoop("state.sit");
    /** 玩家无操作时的循环动画。 */
    public static final RawAnimation STATE_IDLE = RawAnimation.begin().thenLoop("state.idle");

    // 动作动画
    /** 玩家被攻击时的单次播放动画。 */
    public static final RawAnimation ACTION_ATTACKED = RawAnimation.begin().thenPlay("action.attacked");
    /** 玩家使用主手（右手）时的单次播放动画。 */
    public static final RawAnimation ACTION_USE_MAINHAND = RawAnimation.begin().thenPlay("action.use_mainhand");
    /** 玩家使用副手（左手）时的单次播放动画。 */
    public static final RawAnimation ACTION_USE_OFFHAND = RawAnimation.begin().thenPlay("action.use_offhand");
    /** 玩家挥动主手（右手）时的单次播放动画。 */
    public static final RawAnimation ACTION_SWING_HAND = RawAnimation.begin().thenPlay("action.swing_hand");
    /** 玩家挥动副手（左手）时的单次播放动画。 */
    public static final RawAnimation ACTION_SWING_OFFHAND = RawAnimation.begin().thenPlay("action.swing_offhand");

    // 特殊动画
    /** 玩家使用“激流”附魔三叉戟时的循环动画。 */
    public static final RawAnimation SPECIAL_RIPTIDE = RawAnimation.begin().thenLoop("special.riptide");
    /** 玩家死亡时的单次播放动画。 */
    public static final RawAnimation SPECIAL_DEATH = RawAnimation.begin().thenPlay("special.death");

}
