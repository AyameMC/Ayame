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

package org.ayamemc.ayame.model.molang;

import org.ayamemc.ayame.client.script.JavaScriptLoader;
import software.bernie.geckolib.loading.math.MathValue;
import team.unnamed.mocha.MochaEngine;

public class LazyMathValue implements MathValue {
    private final String expression;
    private MathValue delegate;

    public static boolean firstRunOrReloadJs = true;

    public LazyMathValue(String expression) {
        this.expression = expression;
    }

    @Override
    public double get() {
        if (delegate == null) {
            if (firstRunOrReloadJs) {
                JavaScriptLoader.runJs();
                firstRunOrReloadJs = false;
            }

            MochaEngine<?> engine = MochaPlayerMolangManager.get();
            if (engine == null) {
                delegate = () -> 0;
                return 0;
            }

            try {
                double value = engine.eval(expression);
                delegate = () -> value;
                return value;
            } catch (Exception e) {
                delegate = () -> 0;
                return 0;
            }
        }
        return delegate.get();
    }
}
