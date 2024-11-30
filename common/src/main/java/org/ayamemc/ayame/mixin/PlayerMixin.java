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


import com.mojang.datafixers.util.Either;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.Unit;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.player.Player;
import org.ayamemc.ayame.client.api.IAbleToSit;
import org.ayamemc.ayame.client.renderer.AnimationTask;
import org.ayamemc.ayame.model.AyameAnimations;
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

    @Shadow
    public abstract Either<Player.BedSleepingProblem, Unit> startSleepInBed(BlockPos bedPos);

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
        // TODO 完善默认动画，支持自定义动画
        final Player player = (Player) (Object) this;
        final Pose pose = player.getPose();
//        final boolean isInLiquid = player.isInLiquid();
//        final boolean isInWater = player.isEyeInFluid(FluidTags.WATER);
//        final boolean isSitting = player.ayame$isSitting();

        controllers.add(new AnimationController<>(player, 20, state -> {
            // 动画任务处理
            if (AnimationTask.shouldAnimationProcess(player)) {
                return AnimationTask.handle(player, state.getController());
            }

            // 动画判断列表
            List<Supplier<PlayState>> animationChecks = List.of(
                    // ---- 非循环

                    // 玩家右键右手
                    () -> (player.isUsingItem() && player.getUsedItemHand() == InteractionHand.MAIN_HAND) ?
                            state.setAndContinue(AyameAnimations.ACTION_USE_MAINHAND) : null,
                    // 玩家右键左手
                    () -> (player.isUsingItem() && player.getUsedItemHand() == InteractionHand.OFF_HAND) ?
                            state.setAndContinue(AyameAnimations.ACTION_USE_OFFHAND) : null,
                    // 玩家被攻击
                    () -> (player.isHurt()) ?
                            state.setAndContinue(AyameAnimations.ACTION_ATTACKED) : null,
                    () -> (player.isDeadOrDying()) ?
                            state.setAndContinue(AyameAnimations.SPECIAL_DEATH) : null,

                    // ----循环
                    //  玩家移动动画
                    // 在活版门状态，不动
                    () -> (player.isSwimming() && !player.isInLiquid() && !state.isMoving()) ?
                            state.setAndContinue(AyameAnimations.MOVE_CLIMB_STILL) : null,
                    // 在活版门状态，移动
                    () -> (player.isSwimming() && !player.isInLiquid() && state.isMoving()) ?
                            state.setAndContinue(AyameAnimations.MOVE_CLIMBING) : null,
                    // 朴实无华地走
                    () -> (state.isMoving() && !player.isSprinting()) ?
                            state.setAndContinue(AyameAnimations.MOVE_WALK) : null,
                    // 疾跑
                    () -> (state.isMoving() && player.isSprinting()) ?
                            state.setAndContinue(AyameAnimations.MOVE_RUN) : null,
                    // 潜行，不动
                    () -> (!state.isMoving() && player.isCrouching()) ?
                            state.setAndContinue(AyameAnimations.MOVE_SNEAKING) : null,
                    // 潜行，移动
                    () -> (!state.isMoving() && player.isCrouching()) ?
                            state.setAndContinue(AyameAnimations.MOVE_SNEAKING) : null,
                    // 游泳，移动
                    () -> (state.isMoving() && player.isSwimming()) ?
                            state.setAndContinue(AyameAnimations.MOVE_SWIM) : null,
                    // 游泳，不动（直立），可靠性存疑
                    () -> (!state.isMoving() && player.isSwimming()) ?
                            state.setAndContinue(AyameAnimations.MOVE_SWIM_STAND) : null,
                    // 跳跃，可靠性存疑
                    () -> (player.jumping) ?
                            state.setAndContinue(AyameAnimations.MOVE_JUMP) : null,
                    // 普通开创飞，可靠性存疑
                    () -> (player.getAbilities().flying) ?
                            state.setAndContinue(AyameAnimations.MOVE_FLY) : null,
                    // 鞘翅飞，可靠性存疑
                    () -> (player.isFallFlying()) ?
                            state.setAndContinue(AyameAnimations.MOVE_ELYTRA_FLY) : null,
                    // TODO: 制作下梯子和上梯子动画
                    // 悬挂在梯子上不动
                    () -> (player.onClimbable() && player.isSuppressingSlidingDownLadder()) ?
                            state.setAndContinue(AyameAnimations.MOVE_LADDER_STILL) : null,
                    // 睡觉
                    () -> (player.isSleeping()) ?
                            state.setAndContinue(AyameAnimations.STATE_SLEEP) : null,

                    //  玩家的一些移动状态
                    // 坐着
                    () -> (!state.isMoving() && player.ayame$isSitting()) ?
                    state.setAndContinue(AyameAnimations.STATE_SIT) : null,
                    // 禁止不动
                    () -> (!state.isMoving()) ?
                            state.setAndContinue(AyameAnimations.STATE_IDLE) : null

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
