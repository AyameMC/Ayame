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

import net.minecraft.core.Direction;
import org.ayamemc.ayame.mixin.accessor.AnimationControllerAccessor;
import org.ayamemc.ayame.mixin.accessor.AnimationControllerInvoker;
import software.bernie.geckolib.animatable.GeoAnimatable;
import software.bernie.geckolib.animation.AnimationController;
import software.bernie.geckolib.animation.AnimationState;
import software.bernie.geckolib.animation.PlayState;
import software.bernie.geckolib.animation.keyframe.BoneAnimation;
import software.bernie.geckolib.animation.keyframe.BoneAnimationQueue;
import software.bernie.geckolib.animation.keyframe.Keyframe;
import software.bernie.geckolib.animation.keyframe.KeyframeStack;
import software.bernie.geckolib.animation.state.BoneSnapshot;
import software.bernie.geckolib.cache.object.GeoBone;
import software.bernie.geckolib.loading.math.MathParser;
import software.bernie.geckolib.loading.math.MathValue;
import software.bernie.geckolib.loading.math.MolangQueries;
import software.bernie.geckolib.model.GeoModel;

import java.util.Map;

public class AyameBlendedAnimationController<T extends GeoAnimatable> extends AnimationController<T> {

    public AyameBlendedAnimationController(T animatable, String name, AnimationStateHandler<T> animationHandler) {
        super(animatable, name, animationHandler);
    }

    public AyameBlendedAnimationController(T animatable, AnimationStateHandler<T> animationHandler) {
        super(animatable, animationHandler);
    }

    public AyameBlendedAnimationController(T animatable, int transitionTickTime, AnimationStateHandler<T> animationHandler) {
        super(animatable, transitionTickTime, animationHandler);
    }

    public AyameBlendedAnimationController(T animatable, String name, int transitionTickTime, AnimationStateHandler<T> animationHandler) {
        super(animatable, name, transitionTickTime, animationHandler);
    }

    @Override
    public void process(GeoModel<T> model, AnimationState<T> state, Map<String, GeoBone> bones, Map<String, BoneSnapshot> snapshots, final double seekTime, boolean crashWhenCantFindBone) {
        double adjustedTick = adjustTick(seekTime);
        this.lastModel = model;

        if (animationState == State.TRANSITIONING && adjustedTick >= this.transitionLength) {
            this.shouldResetTick = true;
            this.animationState = State.RUNNING;
            adjustedTick = adjustTick(seekTime);
        }

        PlayState playState = handleAnimationState(state);

        if (playState == PlayState.STOP || (this.currentAnimation == null && this.animationQueue.isEmpty())) {
            this.animationState = State.STOPPED;
            ((AnimationControllerAccessor) this).setJustStopped(true);

            return;
        }

        ((AnimationControllerInvoker) this).invokeCreateInitialQueues(bones.values());

        if (this.justStartedTransition && (this.shouldResetTick || ((AnimationControllerAccessor) this).getJustStopped())) {
            ((AnimationControllerAccessor) this).setJustStopped(false);
            adjustedTick = adjustTick(seekTime);

            if (this.currentAnimation == null)
                this.animationState = State.TRANSITIONING;
        } else if (this.currentAnimation == null) {
            this.shouldResetTick = true;
            this.animationState = State.TRANSITIONING;
            this.justStartedTransition = true;
            this.needsAnimationReload = false;
            adjustedTick = adjustTick(seekTime);
        } else if (this.animationState != State.TRANSITIONING) {
            this.animationState = State.RUNNING;
        }

        if (getAnimationState() == State.RUNNING) {
            ((AnimationControllerInvoker) this).invokeProcessCurrentAnimation(adjustedTick, seekTime, crashWhenCantFindBone);
        } else if (this.animationState == State.TRANSITIONING) {
            if (this.lastPollTime != seekTime && (adjustedTick == 0 || this.isJustStarting)) {
                this.justStartedTransition = false;
                this.lastPollTime = seekTime;
                this.currentAnimation = this.animationQueue.poll();

                ((AnimationControllerInvoker) this).invokeResetEventKeyFrames();

                if (this.currentAnimation == null)
                    return;

                ((AnimationControllerInvoker) this).invokeSaveSnapshotsForAnimation(this.currentAnimation, snapshots);
            }

            if (this.currentAnimation != null) {
                MathParser.setVariable(MolangQueries.ANIM_TIME, () -> 0);

                for (BoneAnimation boneAnimation : this.currentAnimation.animation().boneAnimations()) {
                    BoneAnimationQueue boneAnimationQueue = this.boneAnimationQueues.get(boneAnimation.boneName());
                    BoneSnapshot boneSnapshot = this.boneSnapshots.get(boneAnimation.boneName());
                    GeoBone bone = bones.get(boneAnimation.boneName());

                    if (boneSnapshot == null)
                        continue;

                    if (bone == null) {
                        if (crashWhenCantFindBone)
                            throw new RuntimeException("Could not find bone: " + boneAnimation.boneName());

                        continue;
                    }

                    KeyframeStack<Keyframe<MathValue>> rotationKeyFrames = boneAnimation.rotationKeyFrames();
                    KeyframeStack<Keyframe<MathValue>> positionKeyFrames = boneAnimation.positionKeyFrames();
                    KeyframeStack<Keyframe<MathValue>> scaleKeyFrames = boneAnimation.scaleKeyFrames();

                    if (!rotationKeyFrames.xKeyframes().isEmpty()) {
                        boneAnimationQueue.addNextRotation(null, adjustedTick, this.transitionLength, boneSnapshot, bone.getInitialSnapshot(),
                                ((AnimationControllerInvoker) this).invokeGetAnimationPointAtTick(rotationKeyFrames.xKeyframes(), 0, true, Direction.Axis.X),
                                ((AnimationControllerInvoker) this).invokeGetAnimationPointAtTick(rotationKeyFrames.yKeyframes(), 0, true, Direction.Axis.Y),
                                ((AnimationControllerInvoker) this).invokeGetAnimationPointAtTick(rotationKeyFrames.zKeyframes(), 0, true, Direction.Axis.Z));
                    }

                    if (!positionKeyFrames.xKeyframes().isEmpty()) {
                        boneAnimationQueue.addNextPosition(null, adjustedTick, this.transitionLength, boneSnapshot,
                                ((AnimationControllerInvoker) this).invokeGetAnimationPointAtTick(positionKeyFrames.xKeyframes(), 0, false, Direction.Axis.X),
                                ((AnimationControllerInvoker) this).invokeGetAnimationPointAtTick(positionKeyFrames.yKeyframes(), 0, false, Direction.Axis.Y),
                                ((AnimationControllerInvoker) this).invokeGetAnimationPointAtTick(positionKeyFrames.zKeyframes(), 0, false, Direction.Axis.Z));
                    }

                    if (!scaleKeyFrames.xKeyframes().isEmpty()) {
                        boneAnimationQueue.addNextScale(null, adjustedTick, this.transitionLength, boneSnapshot,
                                ((AnimationControllerInvoker) this).invokeGetAnimationPointAtTick(scaleKeyFrames.xKeyframes(), 0, false, Direction.Axis.X),
                                ((AnimationControllerInvoker) this).invokeGetAnimationPointAtTick(scaleKeyFrames.yKeyframes(), 0, false, Direction.Axis.Y),
                                ((AnimationControllerInvoker) this).invokeGetAnimationPointAtTick(scaleKeyFrames.zKeyframes(), 0, false, Direction.Axis.Z));
                    }
                }
            }
        }
    }
}
