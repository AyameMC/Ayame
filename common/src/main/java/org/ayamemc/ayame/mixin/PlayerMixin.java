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


import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.UseAnim;
import org.ayamemc.ayame.client.api.PlayerMixinInterface;
import org.ayamemc.ayame.client.renderer.AnimationTask;
import org.ayamemc.ayame.client.yttribume.IYttribumable;
import org.ayamemc.ayame.client.yttribume.Yttribume;
import org.ayamemc.ayame.model.AyameAnimations;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.animation.AnimatableManager;
import software.bernie.geckolib.animation.AnimationController;
import software.bernie.geckolib.animation.AnimationProcessor;
import software.bernie.geckolib.animation.PlayState;
import software.bernie.geckolib.util.GeckoLibUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;


/**
 * 玩家的动画
 */

@Mixin(Player.class)
public abstract class PlayerMixin implements GeoEntity, PlayerMixinInterface, IYttribumable {
    @Unique
    private final AnimatableInstanceCache ayame$geoCache = GeckoLibUtil.createInstanceCache(this);
    @Unique
    private final Map<Yttribume, Float> ayame$yttribumeMap = new HashMap<>();
    @Unique
    private boolean ayame$isYttribumeRestricted = true;
    @Unique
    private boolean ayame$isSitting = false;
    @Unique
    private boolean ayame$isHurting = false;
    @Unique
    private String ayame$playAnimationName;
    @Unique
    private boolean ayame$isLoopAnimation;

    @Shadow
    protected abstract boolean freeAt(BlockPos pos);

    @Unique
//    private boolean ayame$isrResetAnimation = false;

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {

        // TODO 完善默认动画，支持自定义动画
        final Player player = (Player) (Object) this;
        final Pose pose = player.getPose();


        controllers.add(new AnimationController<>(this, 2, state -> {
            // 动画任务处理
            if (AnimationTask.shouldAnimationProcess(player)) {
                return AnimationTask.handle(player, state.getController());
            }
//            // TODO: 不要一直设置
//            if (player.isSpectator()) {
//                ayame$setYttribume(Yttribumes.MODEL_ALPHA, 0.6F);
//            } else {
//                ayame$setYttribume(Yttribumes.MODEL_ALPHA, 1.0F);
//            }
            // 动画判断列表
            List<Supplier<PlayState>> animationChecks = List.of(
                    // ---- 非循环
                    // 玩家死亡，todo 修复无效问题
                    () -> (player.isDeadOrDying()) ?
                            state.setAndContinue(AyameAnimations.SPECIAL_DEATH) : null,
                    // 玩家被攻击，todo 修复时有时无问题
                    () -> (player.ayame$isHurting()) ?
                            state.setAndContinue(AyameAnimations.ACTION_ATTACKED) : null,


                    // ----循环
                    // TODO: 制作悬挂动画
                    // 上梯子，有效
                    // 悬挂梯子，有效
                    () -> (player.onClimbable() && player.isSuppressingSlidingDownLadder()) ?
                            state.setAndContinue(AyameAnimations.MOVE_LADDER_STILL) : null,
                    // 下梯子，有效
                    () -> (player.onClimbable()) ?
                            state.setAndContinue(AyameAnimations.MOVE_LADDER_DOWN) : null,


                    //  玩家移动动画
                    // 普通开创飞，有效
                    () -> (player.getAbilities().flying) ?
                            state.setAndContinue(AyameAnimations.MOVE_FLY) : null,

                    // 潜行，不动，有效
                    () -> (!ayame$isWalking(player) && player.isCrouching()) ?
                            state.setAndContinue(AyameAnimations.MOVE_SNEAK_STILL) : null,
                    // 潜行，移动，有效
                    () -> (player.isCrouching()) ?
                            state.setAndContinue(AyameAnimations.MOVE_SNEAKING) : null,
                    // 游泳，不动（直立），有效
                    () -> (!ayame$isWalking(player) && player.isSwimming()) ?
                            state.setAndContinue(AyameAnimations.MOVE_SWIM_STAND) : null,
                    // 游泳，移动，有效
                    () -> (player.isSwimming()) ?
                            state.setAndContinue(AyameAnimations.MOVE_SWIM) : null,


                    // 在活版门状态，todo 修复无效问题
                    () -> (player.isSwimming() && !ayame$isWalking(player) && !player.isInLiquid()) ?
                            state.setAndContinue(AyameAnimations.MOVE_CLIMB_STILL) : null,
                    // 在活版门状态，todo 修复无效问题
                    () -> (player.isSwimming() && !player.isInLiquid()) ?
                            state.setAndContinue(AyameAnimations.MOVE_CLIMBING) : null,
                    // 鞘翅飞，有效
                    () -> (player.isFallFlying()) ?
                            state.setAndContinue(AyameAnimations.MOVE_ELYTRA_FLY) : null,
                    // 朴实无华地走，有效
                    () -> (ayame$isWalking(player) && !player.isSprinting()) ?
                            state.setAndContinue(AyameAnimations.MOVE_WALK) : null,
                    // 疾跑，有效
                    () -> (ayame$isWalking(player) && player.isSprinting()) ?
                            state.setAndContinue(AyameAnimations.MOVE_RUN) : null,


                    // 跳跃，有效
                    () -> (player.jumping) ?
                            state.setAndContinue(AyameAnimations.MOVE_JUMP) : null,

                    // 睡觉，有效
                    () -> (player.isSleeping()) ?
                            state.setAndContinue(AyameAnimations.STATE_SLEEP) : null,

                    //  玩家的一些移动状态
                    // 坐着，船有效，todo 修复无效问题 比如，马不行
                    () -> (!ayame$isWalking(player) && player.ayame$isSitting()) ?
                            state.setAndContinue(AyameAnimations.STATE_SIT) : null,
                    // 禁止不动，有效
                    () -> (!ayame$isWalking(player)) ?
                            state.setAndContinue(AyameAnimations.STATE_IDLE) : null

            );

            // 按顺序执行判断逻辑，返回首个非 null 的状态
            for (Supplier<PlayState> check : animationChecks) {
                PlayState result = check.get();
                AnimationProcessor.QueuedAnimation queuedAnimation = state.getController().getCurrentAnimation();

                if (queuedAnimation != null) {
//                   MINECRAFT.player.sendSystemMessage(Component.literal("正在播放：" +  queuedAnimation.animation().name()));
                }

                if (result != null) {
                    return result;
                }
            }
            return PlayState.CONTINUE;
        }));

        for (byte i = 0; i < (Byte.MAX_VALUE - 1); i++) {
            byte finalI = (byte) (i + 1);
            controllers.add(new AnimationController<>(this, "mix_controller" + finalI, 2,
                    state ->
                            state.setAndContinue(AyameAnimations.create(AyameAnimations.MIX_PARALLEL + finalI, true))));
        }

        controllers.add(new AnimationController<>(this, "swing", 2, state -> {
            final ItemStack itemStack = player.getUseItem();
            final UseAnim handAnimation = itemStack.getUseAnimation();
//            if (player.swingingArm != null)
//                Ayame.LOGGER.info("hand {}", player.swingingArm);
            // 玩家使用右手
            if (player.isUsingItem() && (player.getUsedItemHand() == InteractionHand.MAIN_HAND))
                return state.setAndContinue(AyameAnimations.ACTION_USE_MAINHAND);
            // 玩家使用左手
            if (player.isUsingItem() && (player.getUsedItemHand() == InteractionHand.OFF_HAND))
                return state.setAndContinue(AyameAnimations.ACTION_USE_OFFHAND);
            // TODO: 弃用swingingArm
            // 玩家挥动右手
            if (player.swinging && (player.swingingArm == InteractionHand.MAIN_HAND))
                return state.setAndContinue(AyameAnimations.ACTION_SWING_MAINHAND);
            // 玩家挥动左手
            if (player.swinging && (player.swingingArm == InteractionHand.OFF_HAND))
                state.setAndContinue(AyameAnimations.ACTION_SWING_OFFHAND);

            state.getController().forceAnimationReset();

            return PlayState.CONTINUE;
        }));
        controllers.add(new AnimationController<>(this, "animation_player", 2, state -> {
            state.resetCurrentAnimation();
            if (this.ayame$playAnimationName != null) {
                state.setAnimation(AyameAnimations.create(ayame$playAnimationName, ayame$isLoopAnimation));
                this.ayame$playAnimationName = null;
                state.resetCurrentAnimation();
            }


//            state.getController().forceAnimationReset();

            return PlayState.CONTINUE;

        }));
        // TODO 添加events
    }

    @Inject(method = "hurt", at = @At("HEAD"))
    private void startHurt(DamageSource source, float amount, CallbackInfoReturnable<Boolean> cir) {
        this.ayame$isHurting = true;
    }

    @Inject(method = "hurt", at = @At("RETURN"))
    private void endHurt(DamageSource source, float amount, CallbackInfoReturnable<Boolean> cir) {
        this.ayame$isHurting = false;
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

    @Override
    public void ayame$setYttribume(Yttribume yttribume, float value, boolean ignoredLimit) {
        if (ignoredLimit || yttribume.isRegal(value, this)) {
            this.ayame$yttribumeMap.put(yttribume, value);
        } else if (value > yttribume.max()) {
            this.ayame$yttribumeMap.put(yttribume, yttribume.max());
        } else if (value < yttribume.min()) {
            this.ayame$yttribumeMap.put(yttribume, yttribume.min());
        }
    }

    @Override
    public void ayame$setHurting(boolean hurting) {
        ayame$isHurting = hurting;
    }

    @Override
    public boolean ayame$isHurting() {
        return ayame$isHurting;
    }

    @Override
    public float ayame$getYttribume(Yttribume yttribume) {
        return this.ayame$yttribumeMap.getOrDefault(yttribume, yttribume.defaultValue());
    }

    @Override
    public boolean ayame$isRestricted() {
        return ayame$isYttribumeRestricted;
    }

    @Override
    public void ayame$setRestriction(boolean restricted) {
        this.ayame$isYttribumeRestricted = restricted;
    }

    @Override
    public void ayame$playAnimation(String animationName, boolean isLoop) {
        this.ayame$playAnimationName = animationName;
        this.ayame$isLoopAnimation = isLoop;
    }

    @Override
    public void ayame$resetAnimation() {
        this.ayame$playAnimation("", false);
    }


    @Unique
    public boolean ayame$isWalking(Player player) {
        return player.getDeltaMovement().horizontalDistanceSqr() > 1e-4;
    }
}
