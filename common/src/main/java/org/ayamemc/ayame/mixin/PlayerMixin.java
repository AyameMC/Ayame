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

package org.ayamemc.ayame.mixin;


import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.player.Player;
import org.ayamemc.ayame.client.api.IAbleToSit;
import org.ayamemc.ayame.client.renderer.AnimationTask;
import org.ayamemc.ayame.model.DefaultAnimations;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.animation.AnimatableManager;
import software.bernie.geckolib.animation.AnimationController;
import software.bernie.geckolib.animation.PlayState;
import software.bernie.geckolib.util.GeckoLibUtil;

import java.util.List;
import java.util.function.Supplier;


/**
 * 玩家的动画
 */
@Environment(EnvType.CLIENT)
@Mixin(Player.class)
public abstract class PlayerMixin implements GeoEntity, IAbleToSit {
    @Unique
    private final AnimatableInstanceCache ayame$geoCache = GeckoLibUtil.createInstanceCache(this);
    @Unique
    private boolean ayame$isSitting = false;

    @Shadow
    public abstract boolean setEntityOnShoulder(CompoundTag entityCompound);

    @Shadow
    public abstract void remove(Entity.RemovalReason reason);

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
        // TODO 完善默认动画，支持自定义动画
        final Player player = (Player) (Object) this;
        final Pose pose = player.getPose();
        final boolean isInLiquid = player.isInLiquid();
        final boolean isInWater = player.isEyeInFluid(FluidTags.WATER);
        final boolean isSitting = player.ayame$isSitting();

        controllers.add(new AnimationController<>(player, 20, state -> {
            // 动画任务处理
            if (AnimationTask.shouldAnimationProcess(player)) {
                return AnimationTask.handle(player, state.getController());
            }

            // 动画判断列表
            List<Supplier<PlayState>> animationChecks = List.of(
                    // 地上趴着（比如活版门）
                    () -> pose == Pose.SWIMMING && !isInLiquid ? state.setAndContinue(DefaultAnimations.MOVE_CLIMBING) : null,
                    // 在水里（游泳）
                    () -> isInLiquid && isInWater ? state.setAndContinue(DefaultAnimations.MOVE_SWIM) : null,
                    // 死亡动画
                    () -> player.isDeadOrDying() ? state.setAndContinue(DefaultAnimations.SPECIAL_DEATH) : null,
                    // 潜行
                    () -> player.isCrouching() ? state.setAndContinue(DefaultAnimations.MOVE_SNEAKING) : null,
                    // 未移动时的逻辑
                    () -> !state.isMoving() && isSitting ? state.setAndContinue(DefaultAnimations.STATE_SIT) : null,
                    () -> !state.isMoving() ? state.setAndContinue(DefaultAnimations.STATE_IDLE) : null,
                    // 移动时的逻辑
                    () -> state.isMoving() ? state.setAndContinue(DefaultAnimations.MOVE_WALK) : null
            );

            // 按顺序执行判断逻辑，返回首个非 null 的状态
            for (Supplier<PlayState> check : animationChecks) {
                PlayState result = check.get();
                if (result != null) {
                    return result;
                }
            }

            return PlayState.CONTINUE;
        }));


        // TODO 添加events
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return ayame$geoCache;
    }

    @Override
    public void ayame$setSitting(boolean sitting) {
        ayame$isSitting = sitting;
    }

    @Override
    public boolean ayame$isSitting() {
        return ayame$isSitting;
    }


}
