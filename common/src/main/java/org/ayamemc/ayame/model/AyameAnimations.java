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

package org.ayamemc.ayame.model;

import software.bernie.geckolib.animation.RawAnimation;

/**
 * Ayame 动画定义类，用于管理玩家不同状态的动画。
 */
public class AyameAnimations {
    // 调试动画
    /**
     * 调试用空动画，通常用于测试或无动画状态。
     */
    public static final RawAnimation DEBUG_EMPTY = create("debug.empty", true);

    public static final RawAnimation AYAME_TEST = create("aym.test", true);

    // 移动动画
    /**
     * 玩家行走时的循环动画。
     */
    public static final RawAnimation MOVE_WALK = create("move.walk", true);
    /**
     * 玩家疾跑时的循环动画。
     */
    public static final RawAnimation MOVE_RUN = create("move.run", true);
    /**
     * 玩家在活板门下方静止时的循环动画。
     */
    public static final RawAnimation MOVE_CLIMB_STILL = create("move.climb_still", true);
    /**
     * 玩家在活板门下方爬行时的循环动画。
     */
    public static final RawAnimation MOVE_CLIMBING = create("move.climbing", true);
    /**
     * 玩家潜行但静止时的循环动画。
     */
    public static final RawAnimation MOVE_SNEAK_STILL = create("move.sneak_still", true);
    /**
     * 玩家潜行并移动时的循环动画。
     */
    public static final RawAnimation MOVE_SNEAKING = create("move.sneaking", true);
    /**
     * 玩家游泳时的循环动画。
     */
    public static final RawAnimation MOVE_SWIM = create("move.swim", true);
    /**
     * 玩家在水中站立式游泳时的循环动画。
     */
    public static final RawAnimation MOVE_SWIM_STAND = create("move.swim_stand", true);
    /**
     * 玩家跳跃时的单次播放动画。
     */
    public static final RawAnimation MOVE_JUMP = create("move.jump", false);
    /**
     * 玩家在创造模式飞行时的循环动画。
     */
    public static final RawAnimation MOVE_FLY = create("move.fly", true);
    /**
     * 玩家鞘翅飞行时的循环动画。
     */
    public static final RawAnimation MOVE_ELYTRA_FLY = create("move.elytra_fly", true);
    /**
     * 玩家爬梯子向上移动时的循环动画。
     */
    public static final RawAnimation MOVE_LADDER_UP = create("move.ladder_up", true);
    /**
     * 玩家静止在梯子上时的循环动画。
     */
    public static final RawAnimation MOVE_LADDER_STILL = create("move.ladder_still", true);
    /**
     * 玩家爬梯子向下移动时的循环动画。
     */
    public static final RawAnimation MOVE_LADDER_DOWN = create("move.ladder_down", true);

    // 状态动画
    /**
     * 玩家坐在船上时的循环动画。
     */
    public static final RawAnimation STATE_BOAT = create("state.boat", true);
    /**
     * 玩家睡觉时的循环动画。
     */
    public static final RawAnimation STATE_SLEEP = create("state.sleep", true);
    /**
     * 玩家骑马（或驴）时的循环动画。
     */
    public static final RawAnimation STATE_RIDE = create("state.ride", true);
    /**
     * 玩家骑猪时的循环动画。
     */
    public static final RawAnimation STATE_RIDE_PIG = create("state.ride_pig", true);
    /**
     * 玩家坐下时的循环动画。
     */
    public static final RawAnimation STATE_SIT = create("state.sit", true);
    /**
     * 玩家静止（无操作）时的循环动画。
     */
    public static final RawAnimation STATE_IDLE = create("state.idle", true);

    // 动作动画
    /**
     * 玩家被攻击时的单次播放动画。
     */
    public static final RawAnimation ACTION_ATTACKED = create("action.attacked", false);
    /**
     * 玩家使用主手（右手）时的单次播放动画。
     */
    public static final RawAnimation ACTION_USE_MAINHAND = create("action.use_mainhand", false);
    /**
     * 玩家使用副手（左手）时的单次播放动画。
     */
    public static final RawAnimation ACTION_USE_OFFHAND = create("action.use_offhand", false);
    /**
     * 玩家挥动主手（右手）时的单次播放动画。
     */
    public static final RawAnimation ACTION_SWING_MAINHAND = create("action.swing_mainhand", false);
    /**
     * 玩家挥动副手（左手）时的单次播放动画。
     */
    public static final RawAnimation ACTION_SWING_OFFHAND = create("action.swing_offhand", false);

    // 特殊动画
    /**
     * 玩家使用“激流”附魔三叉戟时的循环动画。
     */
    public static final RawAnimation SPECIAL_RIPTIDE = create("special.riptide", true);
    /**
     * 玩家死亡时的单次播放动画。
     */
    public static final RawAnimation SPECIAL_DEATH = create("special.death", false);

    /**
     * 创建动画的辅助方法。
     *
     * @param animationName 动画名称。
     * @param loop          动画是否为循环播放。
     * @return 创建的动画对象。
     */
    private static RawAnimation create(String animationName, boolean loop) {
        return loop ? RawAnimation.begin().thenLoop(animationName) : RawAnimation.begin().thenPlay(animationName);
    }
}
