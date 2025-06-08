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


import org.ayamemc.ayame.model.molang.MochaContext;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import software.bernie.geckolib.loading.math.value.Variable;
import team.unnamed.mocha.MochaEngine;

import java.util.concurrent.atomic.AtomicReference;
import java.util.function.DoubleSupplier;

import static software.bernie.geckolib.GeckoLibConstants.LOGGER;

@Mixin(value = Variable.class, remap = false)
public abstract class VariableMixin {
    @Shadow
    @Final
    private AtomicReference<DoubleSupplier> value;

    @Shadow
    @Final
    private String name;

    /**
     * a
     *
     * @return a
     * @author a
     * @reason a
     */
    @Overwrite
    public double get() {
        try {
            return this.value.get().getAsDouble();
        } catch (Exception ex) {
            MochaEngine<?> mocha = MochaContext.get();
            if (mocha != null) {
                return mocha.eval(this.name);
            } else {
                LOGGER.error("Attempted to use Molang variable for incompatible animatable type ({}). An animation json needs to be fixed", this.name, ex);
                return 0;
            }
            //throw new RuntimeException(ex);
            //GeckoLibConstants.LOGGER.error("Attempted to use Molang variable for incompatible animatable type (" + this.name + "). An animation json needs to be fixed", ex.getMessage());
//            return 0;
        }
    }
}
