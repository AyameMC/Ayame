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
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import software.bernie.geckolib.loading.math.MathParser;
import software.bernie.geckolib.loading.math.MathValue;
import software.bernie.geckolib.loading.math.value.CompoundValue;

import java.util.regex.Pattern;

@Mixin(value = MathParser.class, remap = false)
public abstract class MathParserMixin {


    @Shadow
    @Final
    private static Pattern VALID_DOUBLE;

    @Shadow
    public static MathValue compileExpression(String expression) {
        return null;
    }

    @Shadow
    @Final
    private static String MOLANG_RETURN;

    @Shadow
    @Final
    private static String STATEMENT_DELIMITER;


    @Inject(method = "compileMolang", at = @At("RETURN"), cancellable = true)
    private static void compileMolangPost(String expression, CallbackInfoReturnable<MathValue> cir) {
        var returnValue = cir.getReturnValue();
        var mocha = MochaContext.get();
        if (returnValue instanceof MathValue && !(returnValue instanceof CompoundValue) && (mocha != null)) {
            cir.setReturnValue(() -> mocha.eval(expression));
        }
    }



}
