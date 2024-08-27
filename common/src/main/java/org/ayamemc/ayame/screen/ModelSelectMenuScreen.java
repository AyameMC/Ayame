/*
 *      This file is part of Ayame.
 *
 *     Ayame is free software: you can redistribute it and/or modify it under the terms of the GNU Lesser General Public License as published by the Free Software Foundation, either version 3 of the License, or any later version.
 *
 *     Ayame is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more details.
 *
 *     You should have received a copy of the GNU Lesser General Public License along with Ayame. If not, see <https://www.gnu.org/licenses/>.
 */

package org.ayamemc.ayame.screen;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.toasts.SystemToast;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;

public class ModelSelectMenuScreen extends Screen {
    public ModelSelectMenuScreen(Component title) {
        super(title);
    }

    @Override
    protected void init() {
        // TODO: 模型切换
        Button buttonWidget = Button.builder(Component.literal("Model 1"), (btn) -> {
            // When the button is clicked, we can display a toast to the screen.

            // 行为这里改
            this.minecraft.getToasts().addToast(
                    SystemToast.multiline(this.minecraft, SystemToast.SystemToastId.NARRATOR_TOGGLE, Component.literal("Hello World!"), Component.literal("This is a toast."))
            );
        }).bounds(40, 40, 120, 20).build();

        Button buttonWidget1 = Button.builder(Component.literal("Model 2"), (btn) -> {
            // When the button is clicked, we can display a toast to the screen.

            // 行为这里改
            this.minecraft.getToasts().addToast(
                    SystemToast.multiline(this.minecraft, SystemToast.SystemToastId.NARRATOR_TOGGLE, Component.literal("Hello World!"), Component.literal("This is a toast."))
            );
        }).bounds(40, 60, 120, 20).build();

        // x, y, width, height
        // It's recommended to use the fixed height of 20 to prevent rendering issues with the button
        // textures.

        // Register the button widget.
        this.addRenderableWidget(buttonWidget);
        this.addRenderableWidget(buttonWidget1);

    }

    @Override
    public void render(GuiGraphics context, int mouseX, int mouseY, float delta) {
        super.render(context, mouseX, mouseY, delta);

        // Minecraft doesn't have a "label" widget, so we'll have to draw our own text.
        // We'll subtract the font height from the Y position to make the text appear above the button.
        // Subtracting an extra 10 pixels will give the text some padding.
        // textRenderer, text, x, y, color, hasShadow
        context.drawString(this.font, "Model Select Menu", 40, 40 - this.font.lineHeight - 10, 0xFFFFFFFF, true);
        //context.drawString(this.font, "Model 2", 40, 40 - this.font.lineHeight - 10, 0xFFFFFFFF, true);
    }
}
