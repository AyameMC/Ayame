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


import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.gen.Accessor;
import software.bernie.geckolib.animation.EasingType;
import software.bernie.geckolib.animation.keyframe.Keyframe;
import software.bernie.geckolib.loading.math.MathValue;

import java.util.List;

@Mixin(value = Keyframe.class, remap = false)
public interface KeyframeAccessor<T extends MathValue> {
    @Mutable
    @Accessor("length")
    void setLength(double length);

    @Mutable
    @Accessor("startValue")
    void setStartValue(T startValue);

    @Mutable
    @Accessor("endValue")
    void setEndValue(T endValue);

    @Mutable
    @Accessor("easingType")
    void setEasingType(EasingType easingType);

    @Mutable
    @Accessor("easingArgs")
    void setEasingArgs(List<T> easingArgs);
}
