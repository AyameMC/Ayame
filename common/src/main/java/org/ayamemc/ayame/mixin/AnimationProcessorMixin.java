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

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import net.minecraft.world.entity.player.Player;
import org.ayamemc.ayame.model.molang.MochaPlayerMolangManager;
import org.ayamemc.ayame.model.molang.MochaUtil;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import software.bernie.geckolib.animatable.GeoAnimatable;
import software.bernie.geckolib.animation.AnimationProcessor;
import software.bernie.geckolib.animation.AnimationState;
import software.bernie.geckolib.loading.math.MolangQueries;
import software.bernie.geckolib.model.GeoModel;
import team.unnamed.mocha.MochaEngine;

@Mixin(value = AnimationProcessor.class, remap = false)
public abstract class AnimationProcessorMixin<T extends GeoAnimatable> {


    @Shadow
    @Final
    private GeoModel<T> model;

    @WrapMethod(method = "preAnimationSetup")
    private void preAnimationSetupWrap(AnimationState<T> animationState, double animTime, Operation<Void> original) {
        final T animatable = animationState.getAnimatable();

        if (animatable instanceof Player player) {
            final MochaEngine<?> mocha = ayame$createOrGetMocha(animationState);

            MolangQueries.updateActor(animationState, animTime);
            this.model.applyMolangQueries(animationState, animTime);

            MochaUtil.createMolangVars(mocha);

            MochaPlayerMolangManager.set(mocha);

        } else {
            // 非 Player 模型保持原逻辑
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

}
