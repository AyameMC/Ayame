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
import org.spongepowered.asm.mixin.gen.Accessor;
import software.bernie.geckolib.loading.math.MathParser;
import software.bernie.geckolib.loading.math.function.MathFunction;

import java.util.Map;

@Mixin(value = MathParser.class, remap = false)
public interface MathParserAccessor {
    @Accessor("FUNCTION_FACTORIES")
    static void setFunctionFactories(Map<String, MathFunction.Factory<?>> map) {
        throw new AssertionError();
    }

    @Accessor("FUNCTION_FACTORIES")
    static Map<String, MathFunction.Factory<?>> getFunctionFactories() {
        throw new AssertionError();
    }
}
