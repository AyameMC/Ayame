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

package org.ayamemc.ayame.mixin.accessor;

import net.minecraft.core.Direction;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.gen.Invoker;
import software.bernie.geckolib.animation.AnimationController;
import software.bernie.geckolib.animation.AnimationProcessor;
import software.bernie.geckolib.animation.keyframe.AnimationPoint;
import software.bernie.geckolib.animation.keyframe.Keyframe;
import software.bernie.geckolib.animation.state.BoneSnapshot;
import software.bernie.geckolib.cache.object.GeoBone;
import software.bernie.geckolib.loading.math.MathValue;

import java.util.Collection;
import java.util.List;
import java.util.Map;

@Mixin(value = AnimationController.class, remap = false)
public interface AnimationControllerAccessor {
    @Accessor
    boolean getJustStopped();

    @Accessor("justStopped")
    void setJustStopped(boolean justStopped);

    @Invoker("createInitialQueues")
    void invokeCreateInitialQueues(Collection<GeoBone> modelRendererList);

    @Invoker("processCurrentAnimation")
    void invokeProcessCurrentAnimation(double adjustedTick, double seekTime, boolean crashWhenCantFindBone);

    @Invoker("resetEventKeyFrames")
    void invokeResetEventKeyFrames();

    @Invoker("saveSnapshotsForAnimation")
    void invokeSaveSnapshotsForAnimation(AnimationProcessor.QueuedAnimation animation, Map<String, BoneSnapshot> snapshots);

    @Invoker("getAnimationPointAtTick")
    AnimationPoint invokeGetAnimationPointAtTick(List<Keyframe<MathValue>> frames, double tick, boolean isRotation,
                                                 Direction.Axis axis);
}
