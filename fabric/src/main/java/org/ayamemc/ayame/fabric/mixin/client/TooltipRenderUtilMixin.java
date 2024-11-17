/*
 *     Custom player model mod. Powered by GeckoLib.
 *     Copyright (C) 2024  CrystalNeko, HappyRespawnanchor, pertaz(Icon Designer)
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

package org.ayamemc.ayame.fabric.mixin.client;

import net.minecraft.client.gui.screens.inventory.tooltip.TooltipRenderUtil;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.ModifyArgs;


/*
        event.setBorderStart(0xCC_fdc7f5);
        event.setBorderEnd(0xCC_fde8f5);
        event.setBackground(0xCC_5f5f5f);
 */
@Mixin(TooltipRenderUtil.class)
public abstract class TooltipRenderUtilMixin {
    @ModifyArg(
            method = "renderTooltipBackground",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/screens/inventory/tooltip/TooltipRenderUtil;renderHorizontalLine(Lnet/minecraft/client/gui/GuiGraphics;IIIII)V"),
            index = 5
    )
    private static int renderHorizontalLine(int color) {
        return 0xCC_a83632;
    }

    @ModifyArg(
            method = "renderTooltipBackground",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/screens/inventory/tooltip/TooltipRenderUtil;renderRectangle(Lnet/minecraft/client/gui/GuiGraphics;IIIIII)V"),
            index = 5
    )
    private static int renderRectangle(int color) {
        return 0xCC_54a832;
    }

    @ModifyArg(
            method = "renderTooltipBackground",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/screens/inventory/tooltip/TooltipRenderUtil;renderVerticalLine(Lnet/minecraft/client/gui/GuiGraphics;IIIII)V"),
            index = 5
    )
    private static int renderVerticalLine(int color) {
        return 0xCC_323aa8;
    }

    @ModifyArg(
            method = "renderTooltipBackground",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/screens/inventory/tooltip/TooltipRenderUtil;renderFrameGradient(Lnet/minecraft/client/gui/GuiGraphics;IIIIIII)V"),
            index = 5
    )
    private static int renderFrameGradientTopColor(int topColor) {
        return 0xCC_a8329c;
    }
    @ModifyArg(
            method = "renderTooltipBackground",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/screens/inventory/tooltip/TooltipRenderUtil;renderFrameGradient(Lnet/minecraft/client/gui/GuiGraphics;IIIIIII)V"),
            index = 5
    )
    private static int renderFrameGradientBottomColor(int topColor) {
        return 0xCC_55a832;
    }



}
