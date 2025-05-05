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

import com.mojang.datafixers.util.Either;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import software.bernie.geckolib.loading.math.MathParser;
import software.bernie.geckolib.loading.math.MathValue;
import software.bernie.geckolib.loading.math.value.Ternary;
import software.bernie.geckolib.util.CompoundException;

import java.util.List;
import java.util.function.Supplier;

import static org.ayamemc.ayame.Ayame.LOGGER;

@Mixin(value = MathParser.class, remap = false)
public abstract class MathParserMixin {
    @Shadow
    public static MathValue parseSymbols(List<Either<String, List<MathValue>>> symbols) throws CompoundException {
        return null;
    }

    /**
     * @author a
     * @reason a
     */
//    @Overwrite
//    @Nullable
//    protected static Ternary compileTernary(List<Either<String, List<MathValue>>> symbols) throws CompoundException {
//        LOGGER.info("Compile Ternary: {}", symbols);
//        final int symbolCount = symbols.size();
//
//        if (symbolCount < 3)
//            return null;
//
//        Supplier<MathValue> condition = null;
//        Supplier<MathValue> ifTrue = null;
//        int ternaryState = 0;
//        int lastColon = -1;
//
//        for (int i = 0; i < symbolCount; i++) {
//            final int i2 = i;
//            final String string = symbols.get(i).left().orElse(null);
//
//            if ("?".equals(string)) {
//                if (condition == null)
//                    condition = () -> parseSymbols(symbols.subList(0, i2));
//
//                ternaryState++;
//            } else if (":".equals(string)) {
//                if (ternaryState == 1 && ifTrue == null)
//                    ifTrue = () -> parseSymbols(symbols.subList(0, i2));
//
//                ternaryState--;
//                lastColon = i;
//            }
//        }
//
//        if (ternaryState == 0 && condition != null && ifTrue != null && lastColon < symbolCount - 1)
//            return new Ternary(condition.get(), ifTrue.get(), parseSymbols(symbols.subList(lastColon + 1, symbolCount)));
//
//
//        LOGGER.info("null {}", symbols);
//        return null;
//    }
}
