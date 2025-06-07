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

package org.ayamemc.ayame.mixin;

import com.google.common.collect.Sets;
import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import net.minecraft.world.entity.player.Player;
import org.ayamemc.ayame.mixin.accessor.MolangQueriesInvoker;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import software.bernie.geckolib.animatable.GeoAnimatable;
import software.bernie.geckolib.animation.AnimationProcessor;
import software.bernie.geckolib.animation.AnimationState;
import software.bernie.geckolib.loading.math.MolangQueries;
import software.bernie.geckolib.loading.math.value.Variable;
import team.unnamed.mocha.MochaEngine;
import team.unnamed.mocha.runtime.value.Value;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Mixin(value = AnimationProcessor.class, remap = false)
public abstract class AnimationProcessorMixin<T extends GeoAnimatable> {

    @Unique
    private static final List<String> ayame$molangActorVariables = List.of(MolangQueries.ACTOR_COUNT, "query.anim_time", "query.blocking", "query.block_state", "query.body_x_rotation", "query.body_y_rotation", "query.cardinal_facing", "query.cardinal_facing_2d", "query.cardinal_player_facing", "query.controller_speed", "query.day", "query.death_ticks", "query.distance_from_camera", "query.equipment_count", "query.frame_alpha", "query.get_actor_info_id", "query.ground_speed", "query.has_cape", "query.has_collision", "query.has_gravity", "query.has_head_gear", "query.has_owner", "query.has_player_rider", "query.has_rider", "query.head_x_rotation", "query.head_y_rotation", "query.health", "query.hurt_time", "query.invulnerable_ticks", "query.is_alive", "query.is_angry", "query.is_baby", "query.is_breathing", "query.is_fire_immune", "query.is_first_person", "query.is_invisible", "query.is_in_contact_with_water", "query.is_in_lava", "query.is_in_water", "query.is_in_water_or_rain", "query.is_leashed", "query.is_moving", "query.is_on_fire", "query.is_on_ground", "query.is_powered", "query.is_riding", "query.is_saddled", "query.is_silent", "query.is_sleeping", "query.is_sneaking", "query.is_sprinting", "query.is_swimming", "query.is_using_item", "query.is_wall_climbing", "query.life_time", "query.main_hand_item_max_duration", "query.main_hand_item_use_duration", "query.max_health", "query.moon_brightness", "query.moon_phase", "query.movement_direction", "query.player_level", "query.rider_body_x_rotation", "query.rider_body_y_rotation", "query.rider_head_x_rotation", "query.rider_head_y_rotation", "query.scale", "query.sleep_rotation", "query.time_of_day", "query.time_stamp", "query.vertical_speed", "query.yaw_speed");

    @WrapMethod(method = "preAnimationSetup")
    private void preAnimationSetupWrap(AnimationState<T> animationState, double animTime, Operation<Void> original) {
        final T animatable = animationState.getAnimatable();

        if (animatable instanceof Player) {
            final MochaEngine<?> mocha = ayame$createOrGetMocha(animationState);

            final Map<String, Variable> geckoVariablesMap = MolangQueriesInvoker.getVariables();
            final Map<String, Variable> filteredVariablesMap = ayame$filterVariables(geckoVariablesMap, ayame$molangActorVariables);

            filteredVariablesMap.forEach((key, variable) -> mocha.scope().set(key, Value.of(variable.get())));
        } else {
            original.call(animationState, animTime);
        }
    }

    @Unique
    private MochaEngine<?> ayame$createOrGetMocha(AnimationState<T> animationState) {
        final Player player = (Player) animationState.getAnimatable();
        if (player.ayame$getMochaEngine() == null) {
            player.ayame$setMochaEngine(MochaEngine.createStandard(player));
        }
        return player.ayame$getMochaEngine();
    }

    @Unique
    private static Map<String, Variable> ayame$filterVariables(Map<String, Variable> map, Collection<String> allowedKeys) {
        final Set<String> allowedSet = new HashSet<>(allowedKeys);
        return Sets.intersection(map.keySet(), allowedSet).stream().collect(Collectors.toMap(Function.identity(), map::get));
    }
}
