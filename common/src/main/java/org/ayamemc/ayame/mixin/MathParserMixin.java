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

import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import org.ayamemc.ayame.mixin.accessor.MathParserAccessor;
import org.ayamemc.ayame.model.molang.LazyMathValue;
import org.ayamemc.ayame.model.molang.MochaContext;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import software.bernie.geckolib.loading.math.MathParser;
import software.bernie.geckolib.loading.math.MathValue;
import software.bernie.geckolib.loading.math.value.Constant;
import software.bernie.geckolib.util.CompoundException;

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


    @Inject(method = "parseJson", at = @At("RETURN"), cancellable = true)
    private static void parseJsonPost(JsonElement element, CallbackInfoReturnable<MathValue> cir) {
        if (MochaContext.get() == null) {
            return;
        }

        MathValue returnValue = cir.getReturnValue();
        if (returnValue instanceof MathValue && !(returnValue instanceof Constant)) {
            cir.setReturnValue(new LazyMathValue(element.getAsString()));
        }
    }

//    /**
//     * @author a
//     * @reason a
//     */
//    @Overwrite
//    public static MathValue parseJson(JsonElement element) {
//        if (!(element instanceof JsonPrimitive primitive))
//            throw new CompoundException("Invalid Molang expression format: " + element);
////
////        if (primitive.isBoolean()) // 无意义
////            throw new CompoundException("Boolean not allowed in Molang keyframes");
//
//        if (primitive.isNumber())
//            return new Constant(primitive.getAsDouble());
//
//        if (primitive.isString()) {
//            String value = primitive.getAsString();
//
//            if (MathParserAccessor.getValidDouble().matcher(value).matches()) {
//                return new Constant(Double.parseDouble(value));
//            }
//
//            return new LazyMathValue(value); // 核心修改
//        }
//
//        return new Constant(0);
//    }


}
