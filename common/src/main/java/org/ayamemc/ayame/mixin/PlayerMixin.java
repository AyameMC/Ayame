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
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.player.Player;
import org.ayamemc.ayame.client.api.IAbleToSit;
import org.ayamemc.ayame.client.renderer.AnimationTask;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.animation.AnimatableManager;
import software.bernie.geckolib.animation.AnimationController;
import software.bernie.geckolib.animation.PlayState;
import software.bernie.geckolib.animation.RawAnimation;
import software.bernie.geckolib.constant.DefaultAnimations;
import software.bernie.geckolib.util.GeckoLibUtil;


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

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
        // TODO 完善默认动画，支持自定义动画
        Player self = (Player) (Object) this;
        controllers.add(new AnimationController<>(self, 20, state -> {
            // 处理待处理动画
            if (AnimationTask.shouldAnimationProcess(self)) {
                return AnimationTask.handle(self, state.getController());
            }
            // 地上趴着
            if (self.getPose() == Pose.SWIMMING && !self.isInLiquid()) {
                return state.setAndContinue(DefaultAnimations.CRAWL);
            }
            // 在水里
            if (self.isInLiquid() && self.isEyeInFluid(FluidTags.WATER)) {
                return state.setAndContinue(DefaultAnimations.SWIM);
            }
            // 没有移动
            if (!state.isMoving()) {
                // 是否为sit
                if (self.ayame$isSitting()) return state.setAndContinue(RawAnimation.begin().thenLoop("misc.sit"));
                return state.setAndContinue(DefaultAnimations.IDLE);
            } else if (state.isMoving()) {
                return state.setAndContinue(DefaultAnimations.WALK);
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
