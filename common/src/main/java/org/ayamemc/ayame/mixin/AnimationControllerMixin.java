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

import net.minecraft.core.Direction;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import software.bernie.geckolib.animatable.GeoAnimatable;
import software.bernie.geckolib.animation.AnimationController;
import software.bernie.geckolib.animation.keyframe.AnimationPoint;
import software.bernie.geckolib.animation.keyframe.Keyframe;
import software.bernie.geckolib.loading.math.MathValue;

import java.util.List;

@Mixin(AnimationController.class)
public abstract class AnimationControllerMixin<T extends GeoAnimatable> {
    @Unique
    private static double ayame$unboxToDouble(MathValue value) {
        return value.get();
    }

    @Unique
    private static MathValue ayame$boxingToMathValue(double value) {
        return () -> value;
    }

    @Shadow
    protected abstract AnimationPoint getAnimationPointAtTick(List<Keyframe<MathValue>> frames, double tick, boolean isRotation, Direction.Axis axis);

//    @Inject(
//            method = "processCurrentAnimation",
//            at = @At(value = "INVOKE", target = "Lsoftware/bernie/geckolib/animation/keyframe/BoneAnimationQueue;addRotations(Lsoftware/bernie/geckolib/animation/keyframe/AnimationPoint;Lsoftware/bernie/geckolib/animation/keyframe/AnimationPoint;Lsoftware/bernie/geckolib/animation/keyframe/AnimationPoint;)V"),
//            remap = false
//    )
//    private void processCurrentAnimation(double adjustedTick, double seekTime, boolean crashWhenCantFindBone, CallbackInfo ci,
//                                         @Local(name = "rotationKeyFrames") KeyframeStack<Keyframe<MathValue>> rotationKeyFrames,
//                                         @Local BoneAnimation boneAnimation) {
//        for (Keyframe<MathValue> keyframe : rotationKeyFrames.xKeyframes()) {
//            ((KeyframeAccessor<MathValue>) (Object) keyframe).setStartValue(ayame$boxingToMathValue(ayame$unboxToDouble(keyframe.startValue()) + 1));
//        }
//        for (Keyframe<MathValue> keyframe : rotationKeyFrames.yKeyframes()) {
//            ((KeyframeAccessor<MathValue>) (Object) keyframe).setStartValue(ayame$boxingToMathValue(ayame$unboxToDouble(keyframe.startValue()) + 1));
//        }
//        for (Keyframe<MathValue> keyframe : rotationKeyFrames.zKeyframes()) {
//            ((KeyframeAccessor<MathValue>) (Object) keyframe).setStartValue(ayame$boxingToMathValue(ayame$unboxToDouble(keyframe.startValue()) + 1));
//        }
//    }
}