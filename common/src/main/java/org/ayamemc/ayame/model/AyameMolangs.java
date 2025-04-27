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

import net.minecraft.client.CameraType;
import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.player.Player;
import org.ayamemc.ayame.util.MolangUtil;
import software.bernie.geckolib.loading.math.MathParser;
import software.bernie.geckolib.loading.math.MathValue;
import software.bernie.geckolib.loading.math.MolangQueries;
import software.bernie.geckolib.loading.math.function.MathFunction;
import software.bernie.geckolib.loading.math.value.Variable;

import java.util.function.ToDoubleFunction;

public class AyameMolangs {
    /**
     * 护甲值（0-20）。
     */
    public static final String ARMOR_VALUE = "aym.armor_value";
    /**
     * 玩家穿戴头盔时为 true，否则为 false。
     */
    public static final String HAS_HELMET = "aym.has_helmet";
    /**
     * 玩家穿戴胸甲时为 true，否则为 false。
     */
    public static final String HAS_CHEST_PLATE = "aym.has_chest_plate";
    /**
     * 玩家穿戴护腿时为 true，否则为 false。
     */
    public static final String HAS_LEGGINGS = "aym.has_leggings";
    /**
     * 玩家穿戴靴子时为 true，否则为 false。
     */
    public static final String HAS_BOOTS = "aym.has_boots";
    /**
     * 玩家主手持有物品时为 true，否则为 false。
     */
    public static final String HAS_MAINHAND = "aym.has_mainhand";
    /**
     * 玩家副手持有物品时为 true，否则为 false。
     */
    public static final String HAS_OFFHAND = "aym.has_offhand";
    /**
     * 默认为 false，当玩家需要眨眼返回 true。
     */
    public static final String IS_CLOSE_EYES = "aym.is_close_eyes";
    /**
     * 玩家处于激流状态时为 true，否则为 false。
     */
    public static final String IS_RIPTIDE = "aym.is_riptide";
    /**
     * 玩家穿戴鞘翅时返回 true，否则为 false。
     */
    public static final String HAS_ELYTRA = "aym.has_elytra";
    /**
     * 玩家鞘翅的 X 旋转角度。
     */
    public static final String ELYTRA_ROT_X = "aym.elytra_rot_x";
    /**
     * 玩家鞘翅的 Y 旋转角度。
     */
    public static final String ELYTRA_ROT_Y = "aym.elytra_rot_y";
    /**
     * 玩家鞘翅的 Z 旋转角度。
     */
    public static final String ELYTRA_ROT_Z = "aym.elytra_rot_z";
    /**
     * 返回玩家饥饿值。
     */
    public static final String FOOD_LEVEL = "aym.food_level";

    /**
     * 玩家客户端相机的人称，第一人称为0，第三人称背面为1，正面为2
     */
    public static final String WHICH_PERSON = "aym.which_person";

    /**
     * 玩家身体与头部旋转角的差
     */
    public static final String BODY_HEAD_DIFF = "aym.body_head_diff";

    public static void registerMolangs() {

        MolangQueries.setActorVariable(WHICH_PERSON, (ToDoubleFunction<MolangQueries.Actor<Player>>) actor -> {
            final Minecraft minecraft = actor.mc();
            final CameraType person = minecraft.options.getCameraType();
            return switch (person) {
                case FIRST_PERSON -> 0;
                case THIRD_PERSON_BACK -> 1;
                case THIRD_PERSON_FRONT -> 2;
            };
        });

        MolangUtil.<Player>setActorVariable(BODY_HEAD_DIFF,
                actor -> {
                    final Player player = actor.animatable();
                    return player.yBodyRotO - player.yBodyRot;
                }
        );


        MathParser.registerVariable(
                new Variable(AyameMolangs.HAS_BOOTS, 0)
        );
        MathParser.registerVariable(
                new Variable(AyameMolangs.HAS_LEGGINGS, 0)
        );
        MathParser.registerVariable(
                new Variable(AyameMolangs.HAS_CHEST_PLATE, 0)
        );
        MathParser.registerVariable(
                new Variable(AyameMolangs.HAS_HELMET, 0)
        );
        MathParser.registerVariable(new Variable(AyameMolangs.HAS_MAINHAND, 0));
    }

}
