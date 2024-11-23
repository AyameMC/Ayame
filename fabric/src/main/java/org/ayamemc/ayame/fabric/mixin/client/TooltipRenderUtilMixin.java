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
import org.ayamemc.ayame.client.handler.ClientEventHandler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(TooltipRenderUtil.class)
public abstract class TooltipRenderUtilMixin {

    @ModifyArg(
            method = "renderTooltipBackground",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/screens/inventory/tooltip/TooltipRenderUtil;renderHorizontalLine(Lnet/minecraft/client/gui/GuiGraphics;IIIII)V"),
            index = 5
    )
    private static int modifyHorizontalLineColor(int backgroundColor) {
        return getAyameTooltipColor(backgroundColor, ClientEventHandler.TOOLTIP_BACKGROUND_COLOR);
    }

    @ModifyArg(
            method = "renderTooltipBackground",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/screens/inventory/tooltip/TooltipRenderUtil;renderRectangle(Lnet/minecraft/client/gui/GuiGraphics;IIIIII)V"),
            index = 6
    )
    private static int modifyRectangleColor(int backgroundColor) {
        return getAyameTooltipColor(backgroundColor, ClientEventHandler.TOOLTIP_BACKGROUND_COLOR);
    }

    @ModifyArg(
            method = "renderTooltipBackground",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/screens/inventory/tooltip/TooltipRenderUtil;renderVerticalLine(Lnet/minecraft/client/gui/GuiGraphics;IIIII)V"),
            index = 5
    )
    private static int modifyVerticalLineColor(int backgroundColor) {
        return getAyameTooltipColor(backgroundColor, ClientEventHandler.TOOLTIP_BACKGROUND_COLOR);
    }

    @ModifyArg(
            method = "renderTooltipBackground",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/screens/inventory/tooltip/TooltipRenderUtil;renderFrameGradient(Lnet/minecraft/client/gui/GuiGraphics;IIIIIII)V"),
            index = 6
    )
    private static int modifyFrameGradientTopColor(int topColor) {
        return getAyameTooltipColor(topColor, ClientEventHandler.TOOLTIP_BORDER_TOP_COLOR);
    }

    @ModifyArg(
            method = "renderTooltipBackground",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/screens/inventory/tooltip/TooltipRenderUtil;renderFrameGradient(Lnet/minecraft/client/gui/GuiGraphics;IIIIIII)V"),
            index = 7
    )
    private static int modifyFrameGradientBottomColor(int bottomColor) {
        return getAyameTooltipColor(bottomColor, ClientEventHandler.TOOLTIP_BORDER_BOTTOM_COLOR);
    }

    @Unique
    private static int getAyameTooltipColor(int defaultColor, int ayameColor) {
        return ClientEventHandler.shouldUseAyameTooltipColor() ? ayameColor : defaultColor;
    }
}

