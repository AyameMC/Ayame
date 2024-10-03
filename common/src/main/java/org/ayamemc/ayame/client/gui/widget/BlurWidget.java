package org.ayamemc.ayame.client.gui.widget;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.network.chat.Component;

@Environment(EnvType.CLIENT)
public class BlurWidget extends AbstractWidget {
    final Minecraft minecraft = Minecraft.getInstance();

    public BlurWidget(int x, int y, int width, int height) {
        super(x, y, width, height, Component.empty());
    }

    @Override
    protected void renderWidget(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        // 保存当前的帧缓冲区状态
        this.minecraft.getMainRenderTarget().bindWrite(false);

        // 裁剪到小部件的区域
        guiGraphics.enableScissor(this.getX(), this.getY(), this.getX() + this.width, this.getY() + this.height);

        // 这里是应用模糊效果的代码
        this.minecraft.gameRenderer.processBlurEffect(partialTick);

        // 绘制小部件的内容（如背景、文本等）
        guiGraphics.fill(this.getX(), this.getY(), this.getX() + this.width, this.getY() + this.height, 0xFF3A3A3A);

        // 解除裁剪
        guiGraphics.disableScissor();

        // 恢复帧缓冲区状态
        this.minecraft.getMainRenderTarget().bindWrite(true);
    }

    @Override
    protected void updateWidgetNarration(NarrationElementOutput narrationElementOutput) {

    }
}
