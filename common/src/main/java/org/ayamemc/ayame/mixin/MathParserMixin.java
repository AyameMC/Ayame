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

import org.apache.logging.log4j.Level;
import org.ayamemc.ayame.mixin.accessor.MathParserAccessor;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Unique;
import software.bernie.geckolib.GeckoLibConstants;
import software.bernie.geckolib.loading.math.MathParser;
import software.bernie.geckolib.loading.math.MathValue;
import software.bernie.geckolib.loading.math.function.MathFunction;
import software.bernie.geckolib.loading.math.value.Constant;
import team.unnamed.mocha.MochaEngine;
import team.unnamed.mocha.runtime.value.ObjectValue;
import team.unnamed.mocha.runtime.value.Value;

import java.util.function.DoubleSupplier;

@Mixin(value = MathParser.class, remap = false)
public abstract class MathParserMixin {
//    @Unique
//    private static final MochaEngine<?> ayame$mocha = MochaEngine.createStandard();
//
//    /**
//     * @author a
//     * @reason a
//     */
//    @Overwrite
//    public static void registerFunction(String name, MathFunction.Factory<?> factory) {
//        ayame$mocha.scope().setFunction(name, new ObjectValue.DoubleFunction1() {
//            @Override
//            public double apply(double n) {
//                return ((MathFunction) factory).compute();
//            }
//        });
////            GeckoLibConstants.LOGGER.log(Level.WARN, "Duplicate registration of MathFunction: '" + name + "'. Ignore if intentional override");
////
////        GeckoLibConstants.LOGGER.log(Level.DEBUG, "Registered MathFunction '" + name + "'");
//    }
//
//    /**
//     * @author a
//     * @reason a
//     */
//    @Overwrite
//    public static MathValue compileExpression(String expression) {
//       return ayame$convertToMathValue(ayame$mocha.eval(expression));
//    }
//
//    /**
//     * Converts a MochaEngine result to a MathValue.
//     *
//     * @param result The result from MochaEngine
//     * @return The result as a MathValue
//     * @throws IllegalArgumentException If the result type is unsupported
//     */
//    @Unique
//    private static MathValue ayame$convertToMathValue(double result) {
//        return () -> result;
//    }
//
//    /**
//     * Determines whether to use MochaEngine for expression evaluation.
//     *
//     * @return True if MochaEngine should be used, false otherwise
//     */
//    @Unique
//    private static boolean shouldUseMochaEngine() {
//        // Add logic to determine whether to use MochaEngine
//        // For example, check a configuration flag or system property
//        return true; // Default to true for now
//    }
}