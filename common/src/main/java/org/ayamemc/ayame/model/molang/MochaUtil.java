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

package org.ayamemc.ayame.model.molang;

import com.google.common.collect.Sets;
import org.ayamemc.ayame.client.renderer.AyamePlayerRender;
import org.ayamemc.ayame.mixin.accessor.MolangQueriesAccessor;
import software.bernie.geckolib.loading.math.MolangQueries;
import software.bernie.geckolib.loading.math.value.Variable;
import team.unnamed.mocha.MochaEngine;
import team.unnamed.mocha.runtime.MochaFunction;
import team.unnamed.mocha.runtime.value.MutableObjectBinding;
import team.unnamed.mocha.runtime.value.ObjectValue;
import team.unnamed.mocha.runtime.value.Value;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class MochaUtil {
    public static final List<String> AYAME_MOLANG_ACTOR_VARIABLES = List.of(
            MolangQueries.ACTOR_COUNT,
            "query.anim_time",
            "query.blocking",
            "query.block_state",
            "query.body_x_rotation",
            "query.body_y_rotation",
            "query.cardinal_facing",
            "query.cardinal_facing_2d",
            "query.cardinal_player_facing",
            "query.controller_speed",
            "query.day",
            "query.death_ticks",
            "query.distance_from_camera",
            "query.equipment_count",
            "query.frame_alpha",
            "query.get_actor_info_id",
            "query.ground_speed",
            "query.has_cape",
            "query.has_collision",
            "query.has_gravity",
            "query.has_head_gear",
            "query.has_owner",
            "query.has_player_rider",
            "query.has_rider",
            "query.head_x_rotation",
            "query.head_y_rotation",
            "query.health",
            "query.hurt_time",
            "query.invulnerable_ticks",
            "query.is_alive",
            "query.is_angry",
            "query.is_baby",
            "query.is_breathing",
            "query.is_fire_immune",
            "query.is_first_person",
            "query.is_invisible",
            "query.is_in_contact_with_water",
            "query.is_in_lava",
            "query.is_in_water",
            "query.is_in_water_or_rain",
            "query.is_leashed",
            "query.is_moving",
            "query.is_on_fire",
            "query.is_on_ground",
            "query.is_powered",
            "query.is_riding",
            "query.is_saddled",
            "query.is_silent",
            "query.is_sleeping",
            "query.is_sneaking",
            "query.is_sprinting",
            "query.is_swimming",
            "query.is_using_item",
            "query.is_wall_climbing",
            "query.life_time",
            "query.main_hand_item_max_duration",
            "query.main_hand_item_use_duration",
            "query.max_health",
            "query.moon_brightness",
            "query.moon_phase",
            "query.movement_direction",
            "query.player_level",
            "query.rider_body_x_rotation",
            "query.rider_body_y_rotation",
            "query.rider_head_x_rotation",
            "query.rider_head_y_rotation",
            "query.scale",
            "query.sleep_rotation",
            "query.time_of_day",
            "query.time_stamp",
            "query.vertical_speed",
            "aym.has_boots",
            "aym.has_leggings",
            "query.yaw_speed"
    );

    public static Map<String, Variable> filterMolangVariables(Map<String, Variable> map, Collection<String> allowedKeys) {
        final Set<String> allowedSet = new HashSet<>(allowedKeys);
        return Sets.intersection(map.keySet(), allowedSet).stream().collect(Collectors.toMap(Function.identity(), map::get));
    }

    public static void createMolangVars(MochaEngine<?> mocha) {
         Map<String, Variable> geckoVariablesMap = MolangQueriesAccessor.getVariables();
         Map<String, Variable> filteredVariablesMap = filterMolangVariables(geckoVariablesMap, AYAME_MOLANG_ACTOR_VARIABLES);

        for (Map.Entry<String, Variable> entry : filteredVariablesMap.entrySet()) {
            final String fullVarName = entry.getKey();
            final String[] parts = fullVarName.split("\\.");

            if (parts.length == 1) {
                // 直接设为顶层变量
                mocha.scope().set(parts[0], Value.of(
                        (MochaFunction) () -> AyamePlayerRender.execMolangInGecko(fullVarName)
                ));
                continue;
            }

            // 多级结构时，逐层构建对象
            ObjectValue currentScope = mocha.scope();
            for (int i = 0; i < parts.length - 1; i++) {
                String key = parts[i];

                // 尝试取现有对象，否则创建新对象
                Value childValue = currentScope.get(key);
                ObjectValue childScope;
                if (childValue instanceof ObjectValue obj) {
                    childScope = obj;
                } else {
                    childScope = new MutableObjectBinding();
                    currentScope.set(key, childScope);
                }

                currentScope = childScope;
            }

            // 设置最终属性
            if (currentScope instanceof MutableObjectBinding mut) {
                var a =  AyamePlayerRender.execMolangInGecko(fullVarName);
                // TODO 修复 gekco 没有与mocha和谐共处的问题
                mut.set(parts[parts.length - 1], Value.of(
                        new MochaFunction() {
                            @Override
                            public double evaluate() {
                                return a;
                            }
                        }

                ));
            }
        }
    }
}
