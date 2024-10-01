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

package org.ayamemc.ayame.client.renderer;

import net.minecraft.world.entity.Entity;
import software.bernie.geckolib.animation.AnimationController;
import software.bernie.geckolib.animation.PlayState;

import java.util.HashMap;
import java.util.Map;

public class AnimationTask {
    public static Map<Entity, AnimationTaskData> pendingAnimations = new HashMap<>();

    public static void addAnimation(Entity entity, AnimationTaskData animation) {
        pendingAnimations.put(entity, animation);
    }

    public static boolean shouldAnimationProcess(Entity entity) {
        if (!pendingAnimations.containsKey(entity))
            return false;
        return pendingAnimations.get(entity).processTicks() > 0;
    }

    public static void removeAnimation(Entity entity) {
        pendingAnimations.remove(entity);
    }

    public static PlayState handle(Entity entity, AnimationController<?> controller) {
        if (pendingAnimations.containsKey(entity)) {
            var data = pendingAnimations.get(entity);
            data.runATick();
            return pendingAnimations.get(entity).handler().handle(controller);
        } else return PlayState.STOP;
    }

    @FunctionalInterface
    public interface AnimationTaskHandler {
        PlayState handle(AnimationController<?> controller);
    }

    public static class AnimationTaskData {
        private final AnimationTaskHandler handler;
        private int processTicks;

        public AnimationTaskData(AnimationTaskHandler handler, int processTicks) {
            this.handler = handler;
            this.processTicks = processTicks;
        }

        public int processTicks() {
            return processTicks;
        }

        public AnimationTaskHandler handler() {
            return handler;
        }

        public void runATick() {
            processTicks--;
        }
    }
}
