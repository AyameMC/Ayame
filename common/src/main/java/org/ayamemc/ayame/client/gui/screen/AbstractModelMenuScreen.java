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

package org.ayamemc.ayame.client.gui.screen;

import com.mojang.blaze3d.systems.RenderSystem;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.ImageButton;
import net.minecraft.client.gui.components.WidgetSprites;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import org.ayamemc.ayame.Ayame;
import org.ayamemc.ayame.client.gui.widget.BlurWidget;
import org.jetbrains.annotations.Nullable;

@Environment(EnvType.CLIENT)
public abstract class AbstractModelMenuScreen extends Screen {
    public static final ResourceLocation MENU_BACKGROUND_TEXTURE = withAyameNamespace("textures/gui/background.png");
    public static final ResourceLocation MENU_BACKGROUND_OUTLINE_TEXTURE = withAyameNamespace("textures/gui/background_outline.png");
    public static ResourceLocation MENU_TOP_LAYER_TEXTURE = withAyameNamespace("textures/gui/top_layer.png");
    public static ResourceLocation SETTINGS_TEXTURE = withAyameNamespace("textures/gui/settings.png");

    private static final int BACKGROUND_TEXTURE_WIDTH = 410;
    private static final int BACKGROUND_TEXTURE_HEIGHT = 220;
    private static final int BUTTON_SIZE = 32;
    private static final int LEFT_MARGIN = 0;
    private static final int BOTTOM_MARGIN = 0;

    protected final Screen lastScreen;

    public AbstractModelMenuScreen(@Nullable Screen lastScreen) {
        super(Component.empty());
        this.lastScreen = lastScreen;
    }

    private static ResourceLocation withAyameNamespace(String location) {
        return ResourceLocation.fromNamespaceAndPath(Ayame.MOD_ID, location);
    }

    /**
     * 初始化屏幕方法，可以由子类覆盖
     */
    @Override
    protected void init() {
        BlurWidget blurredBackgroundWidget = new BlurWidget(getCenterX(BACKGROUND_TEXTURE_WIDTH), getCenterY(BACKGROUND_TEXTURE_HEIGHT), BACKGROUND_TEXTURE_WIDTH, BACKGROUND_TEXTURE_HEIGHT);
        this.addRenderableOnly(blurredBackgroundWidget);
    }

    /**
     * 渲染背景纹理
     */
    @Override
    public void renderBackground(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        RenderSystem.enableBlend();
        guiGraphics.blit(MENU_BACKGROUND_TEXTURE, getCenterX(BACKGROUND_TEXTURE_WIDTH), getCenterY(BACKGROUND_TEXTURE_HEIGHT), 0, 0, BACKGROUND_TEXTURE_WIDTH, BACKGROUND_TEXTURE_HEIGHT, BACKGROUND_TEXTURE_WIDTH, BACKGROUND_TEXTURE_HEIGHT);
        RenderSystem.disableBlend();
    }

    /**
     * 渲染屏幕内容，包括背景边框
     */
    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float delta) {
        super.render(guiGraphics, mouseX, mouseY, delta);
        RenderSystem.enableBlend();
        guiGraphics.blit(MENU_BACKGROUND_OUTLINE_TEXTURE, getCenterX(BACKGROUND_TEXTURE_WIDTH), getCenterY(BACKGROUND_TEXTURE_HEIGHT), 0, 0, BACKGROUND_TEXTURE_WIDTH, BACKGROUND_TEXTURE_HEIGHT, BACKGROUND_TEXTURE_WIDTH, BACKGROUND_TEXTURE_HEIGHT);
        guiGraphics.blit(MENU_TOP_LAYER_TEXTURE, getCenterX(BACKGROUND_TEXTURE_WIDTH), getCenterY(BACKGROUND_TEXTURE_HEIGHT), 0, 0, BACKGROUND_TEXTURE_WIDTH, BACKGROUND_TEXTURE_HEIGHT, BACKGROUND_TEXTURE_WIDTH, BACKGROUND_TEXTURE_HEIGHT);
        RenderSystem.disableBlend();

        WidgetSprites settingSprites = new WidgetSprites(
                withAyameNamespace("settings"),
                withAyameNamespace("settings_disabled"), // 似乎不会被使用
                withAyameNamespace("settings_enabled_focused")
        );
        ImageButton imageButton = new ImageButton(
                getLeftAlignedX(BACKGROUND_TEXTURE_WIDTH, LEFT_MARGIN),
                getBottomAlignedY(BACKGROUND_TEXTURE_HEIGHT, BUTTON_SIZE, BOTTOM_MARGIN),
                BUTTON_SIZE,
                BUTTON_SIZE,
                settingSprites,
                button -> {
                    // 按钮点击后的行为
                    System.out.println("ImageButton");
                },
                Component.literal("Image Button")
        );
        addRenderableWidget(imageButton);
    }

    /**
     * 获取指定宽度在屏幕中心的X坐标
     */
    protected int getCenterX(int elementWidth) {
        return (this.width - elementWidth) / 2;
    }

    /**
     * 获取指定高度在屏幕中心的Y坐标
     */
    protected int getCenterY(int elementHeight) {
        return (this.height - elementHeight) / 2;
    }

    /**
     * 获取元素在屏幕左边对齐的X坐标
     */
    protected int getLeftAlignedX(int containerWidth, int margin) {
        return getCenterX(containerWidth) + margin;
    }

    /**
     * 获取元素在屏幕底部对齐的Y坐标
     */
    protected int getBottomAlignedY(int containerHeight, int elementHeight, int margin) {
        return getCenterY(containerHeight) + containerHeight - elementHeight - margin;
    }
}
