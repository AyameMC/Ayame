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

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.core.Direction;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import software.bernie.geckolib.animatable.GeoAnimatable;
import software.bernie.geckolib.animation.AnimationController;
import software.bernie.geckolib.animation.keyframe.*;
import software.bernie.geckolib.animation.state.BoneSnapshot;
import software.bernie.geckolib.loading.math.MathValue;

import java.util.List;

@Mixin(AnimationController.class)
public abstract class AnimationControllerMixin<T extends GeoAnimatable> {
    @Shadow
    protected abstract AnimationPoint getAnimationPointAtTick(List<Keyframe<MathValue>> frames, double tick, boolean isRotation, Direction.Axis axis);
    
    @WrapOperation(
            method = "process",
            at = @At(value = "INVOKE", target = "Lsoftware/bernie/geckolib/animation/keyframe/BoneAnimationQueue;addNextRotation(Lsoftware/bernie/geckolib/animation/keyframe/Keyframe;DDLsoftware/bernie/geckolib/animation/state/BoneSnapshot;Lsoftware/bernie/geckolib/animation/state/BoneSnapshot;Lsoftware/bernie/geckolib/animation/keyframe/AnimationPoint;Lsoftware/bernie/geckolib/animation/keyframe/AnimationPoint;Lsoftware/bernie/geckolib/animation/keyframe/AnimationPoint;)V"),
            remap = false
    )
    private void process(BoneAnimationQueue instance, Keyframe<?> keyFrame, double lerpedTick, double transitionLength, BoneSnapshot startSnapshot, BoneSnapshot initialSnapshot, AnimationPoint nextXPoint, AnimationPoint nextYPoint, AnimationPoint nextZPoint, Operation<Void> original,
                         @Local(name = "rotationKeyFrames") KeyframeStack<Keyframe<MathValue>> rotationKeyFrames, @Local BoneAnimation boneAnimation) {
        final List<Keyframe<MathValue>> xKeyframes = rotationKeyFrames.xKeyframes();
        final List<Keyframe<MathValue>> yKeyframes = rotationKeyFrames.yKeyframes();
        final List<Keyframe<MathValue>> zKeyframes = rotationKeyFrames.zKeyframes();

        for (int i = 0; i < xKeyframes.size(); i++) {
            final Keyframe<MathValue> xKeyframe = xKeyframes.get(i);
            final Keyframe<MathValue> yKeyframe = yKeyframes.get(i);
            final Keyframe<MathValue> zKeyframe = zKeyframes.get(i);
            final Keyframe<MathValue> xKeyframeCopy = new Keyframe<>(
                    xKeyframe.length(),
                    boxingToMathValue(xKeyframe.startValue().get() + 1),
                    xKeyframe.endValue(),
                    xKeyframe.easingType(),
                    xKeyframe.easingArgs()
            );
            final Keyframe<MathValue> yKeyframeCopy = new Keyframe<>(
                    yKeyframe.length(),
                    boxingToMathValue(yKeyframe.startValue().get() + 1),
                    yKeyframe.endValue(),
                    yKeyframe.easingType(),
                    yKeyframe.easingArgs()
            );
            final Keyframe<MathValue> zKeyframeCopy = new Keyframe<>(
                    zKeyframe.length(),
                    boxingToMathValue(zKeyframe.startValue().get() + 1),
                    zKeyframe.endValue(),
                    zKeyframe.easingType(),
                    zKeyframe.easingArgs()
            );

            xKeyframes.set(i, xKeyframeCopy);
            yKeyframes.set(i, yKeyframeCopy);
            zKeyframes.set(i, zKeyframeCopy);
        }

        original.call(instance, keyFrame, lerpedTick, transitionLength, startSnapshot, initialSnapshot,
                getAnimationPointAtTick(rotationKeyFrames.xKeyframes(), 0, true, Direction.Axis.X),
                getAnimationPointAtTick(rotationKeyFrames.yKeyframes(), 0, true, Direction.Axis.Y),
                getAnimationPointAtTick(rotationKeyFrames.zKeyframes(), 0, true, Direction.Axis.Z));
    }

    @Unique
    private static double unboxToDouble(MathValue value) {
        return value.get();
    }

    @Unique
    private static MathValue boxingToMathValue(double value) {
        return () -> value;
    }
}