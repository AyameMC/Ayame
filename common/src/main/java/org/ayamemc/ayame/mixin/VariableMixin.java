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
import org.ayamemc.ayame.client.api.VariableMixinInterface;
import org.ayamemc.ayame.model.molang.MochaPlayerMolangManager;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import software.bernie.geckolib.GeckoLibConstants;
import software.bernie.geckolib.loading.math.value.Variable;

import java.util.concurrent.atomic.AtomicReference;
import java.util.function.DoubleSupplier;

import static org.ayamemc.ayame.Ayame.LOGGER;

@Mixin(value = Variable.class, remap = false)
public abstract class VariableMixin implements VariableMixinInterface {
    @Override
    public double ayame$getInGecko() {
        try {
            return this.value.get().getAsDouble();
        } catch (Exception ex) {
//            GeckoLibConstants.LOGGER.error("Attempted to use Molang variable for incompatible animatable type ({}). An animation json needs to be fixed", this.name, ex.getMessage());
            return 0;
        }
    }

    @Shadow
    public abstract AtomicReference<DoubleSupplier> value();

    @Shadow
    public abstract String name();

    @Shadow
    @Final
    private String name;

    @Shadow
    @Final
    private AtomicReference<DoubleSupplier> value;

    @Shadow
    public abstract double get();

    @WrapMethod(method = "get")
    private double get(Operation<Double> original) {
        if (MochaPlayerMolangManager.isPresent()) {
            return MochaPlayerMolangManager.execMolang(this.name);
        } else {
            return original.call();
        }
    }
}
