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

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import org.ayamemc.ayame.Ayame;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Unique;
import software.bernie.geckolib.loading.math.MathParser;
import software.bernie.geckolib.loading.math.MathValue;
import software.bernie.geckolib.loading.math.value.Constant;
import software.bernie.geckolib.util.CompoundException;
import team.unnamed.mocha.MochaEngine;
import team.unnamed.mocha.runtime.MochaFunction;
import team.unnamed.mocha.runtime.value.Value;

import java.util.function.DoubleSupplier;

@Mixin(value = MathParser.class, remap = false)
public abstract class MathParserMixin {
    @Unique
    private static final MochaEngine<?> ayame$mocha = MochaEngine.createStandard();

    /**
     * Overwrites the original setVariable method to set variables in MochaEngine's scope.
     *
     * @param name  The name of the variable
     * @param value The value of the variable
     * @author
     * @reason
     */
    @Overwrite
    public static void setVariable(String name, DoubleSupplier value) {
        ayame$mocha.scope().set(name, Value.of(value));
    }

    /**
     * Wraps the compileMolang method to use MochaEngine for expression evaluation.
     *
     * @param expression The Molang expression to compile
     * @param original   The original compileMolang method
     * @return The result of the expression as a MathValue
     */
    @WrapMethod(method = "compileMolang")
    private static MathValue selectMolangEngine(String expression, Operation<MathValue> original) {
        // Check if MochaEngine should be used (e.g., via configuration)
        if (shouldUseMochaEngine()) {
            try {
                // Compile the expression
                MochaFunction function = ayame$mocha.prepareEval(expression);

                // Evaluate the compiled function
                Object result = function.evaluate();

                // Convert the result to MathValue
                return ayame$convertToMathValue(result);
            } catch (Exception e) {
                Ayame.LOGGER.error("Failed to evaluate expression: {}", expression, e);
                throw new CompoundException("Failed to evaluate expression: " + expression);
            }
        }

        // Fallback to the original method
        return original.call(expression);
    }

    /**
     * Converts a MochaEngine result to a MathValue.
     *
     * @param result The result from MochaEngine
     * @return The result as a MathValue
     * @throws IllegalArgumentException If the result type is unsupported
     */
    @Unique
    private static MathValue ayame$convertToMathValue(Object result) {
        if (result instanceof Number number) {
            // Convert numbers to Constant
            return new Constant(number.doubleValue());
        } else if (result instanceof Boolean bool) {
            // Convert booleans to Constant (1.0 for true, 0.0 for false)
            return new Constant(bool ? 1.0 : 0.0);
        } else if (result instanceof MathValue mathValue) {
            // If the result is already a MathValue, return it directly
            return mathValue;
        } else {
            // Throw an exception for unsupported types
            throw new IllegalArgumentException("Unsupported result type: " + result.getClass().getSimpleName());
        }
    }

    /**
     * Determines whether to use MochaEngine for expression evaluation.
     *
     * @return True if MochaEngine should be used, false otherwise
     */
    @Unique
    private static boolean shouldUseMochaEngine() {
        // Add logic to determine whether to use MochaEngine
        // For example, check a configuration flag or system property
        return true; // Default to true for now
    }
}