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

package org.ayamemc.ayame.client.script;

import org.ayamemc.ayame.Ayame;
import org.ayamemc.ayame.mixin.accessor.MathParserAccessor;
import org.mozilla.javascript.Function;
import org.mozilla.javascript.annotations.JSStaticFunction;
import software.bernie.geckolib.loading.math.MathParser;
import software.bernie.geckolib.loading.math.MathValue;
import software.bernie.geckolib.loading.math.function.MathFunction;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import static org.ayamemc.ayame.Ayame.LOGGER;

public class JsAyame {
    public static final String version = Ayame.VERSION;
    public static final String modLoader = Ayame.modLoader;

    // 记录由 JS 注册的函数
    private static final Set<String> userDefinedFunctions = new HashSet<>();

    @JSStaticFunction
    public static String[] listRegisteredMolangFunctions() {
        Set<String> functions = MathParserAccessor.getFunctionFactories().keySet();
        return functions.toArray(new String[0]);
    }

    @JSStaticFunction
    public static void registerMolangFunction(String name, Function compute) {
        int paramCount = ((Number) compute.get("length", compute)).intValue();
        LOGGER.info("Registering Molang Function '{}'", name);
        MathParser.registerFunction(
                name,
                values -> new MathFunction(values) {
                    @Override
                    public String getName() {
                        return name;
                    }

                    @Override
                    public double compute() {
                        Object[] jsArgs = Arrays.stream(values)
                                .map(MathValue::get)
                                .toArray();
                        return ((Number) Objects.requireNonNull(JavaScriptHelper.executeCallback(compute, jsArgs))).doubleValue();
                    }

                    @Override
                    public int getMinArgs() {
                        return paramCount;
                    }

                    @Override
                    public MathValue[] getArgs() {
                        return values;
                    }
                }
        );

        userDefinedFunctions.add(name);
    }

    @SuppressWarnings("RedundantOperationOnEmptyContainer")
    @JSStaticFunction
    public static void clearFunctions() {
        for (String name : userDefinedFunctions) {
            LOGGER.info("UnRegistering Molang Function '{}'", name);

            MathParserAccessor.getFunctionFactories().remove(name);
        }
        userDefinedFunctions.clear();
    }
}
