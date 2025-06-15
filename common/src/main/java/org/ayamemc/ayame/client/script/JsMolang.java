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

import org.ayamemc.ayame.mixin.accessor.MathParserAccessor;
import org.ayamemc.ayame.model.molang.MochaPlayerMolangManager;
import org.ayamemc.ayame.model.molang.MochaUtil;
import org.mozilla.javascript.Function;
import org.mozilla.javascript.annotations.JSStaticFunction;
import software.bernie.geckolib.loading.math.MathParser;
import team.unnamed.mocha.runtime.value.Value;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import static org.ayamemc.ayame.Ayame.LOGGER;

public class JsMolang {
    // 记录由 JS 注册的函数
    private static final Set<String> userDefinedFunctions = new HashSet<>();

    @JSStaticFunction
    public static String[] listFunctions() {
        Set<String> functions = MochaPlayerMolangManager.get().scope().entries().keySet();
        return functions.toArray(new String[0]);
    }

    // string || number (double)

    @JSStaticFunction
    public static Object exec(String molangCode) {
        return MochaPlayerMolangManager.get().eval(molangCode);
    }

    @JSStaticFunction
    public static String listFunctionsAsString() {
        return Arrays.toString(listFunctions());
    }

    @JSStaticFunction
    public static void setVariable(String name, double value) {
        var mocha = MochaPlayerMolangManager.get();
        MochaUtil.setNestedVariable(mocha.scope(), name, Value.of(value));

    }

    @JSStaticFunction
    public static void registerFunction(String name, Function compute) {
        LOGGER.info("Registering dynamic Molang Function '{}'", name);


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
