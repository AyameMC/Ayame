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

import com.mojang.datafixers.types.Func;
import org.ayamemc.ayame.Ayame;
import org.ayamemc.ayame.util.ModLoader;
import org.mozilla.javascript.Function;
import org.mozilla.javascript.annotations.JSStaticFunction;
import software.bernie.geckolib.loading.math.MathParser;
import software.bernie.geckolib.loading.math.MathValue;
import software.bernie.geckolib.loading.math.function.MathFunction;

public class JsAyame {
    public static final String version = Ayame.VERSION;
    public static final String modLoader = Ayame.modLoader;

    @JSStaticFunction
    public static void registerMolangFunction(String name, Function callback) {
        MathParser.registerFunction(
                name,
                values -> new MathFunction() {
                    @Override
                    public String getName() {
                        return name;
                    }

                    @Override
                    public double compute() {
                        return 0;
                    }

                    @Override
                    public int getMinArgs() {
                        return 0;
                    }

                    @Override
                    public MathValue[] getArgs() {
                        return new MathValue[0];
                    }
                }
        );
    }
}
