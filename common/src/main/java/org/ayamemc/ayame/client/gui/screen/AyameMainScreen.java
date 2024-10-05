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
import net.minecraft.ChatFormatting;
import net.minecraft.Util;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.components.ImageButton;
import net.minecraft.client.gui.components.Tooltip;
import net.minecraft.client.gui.components.WidgetSprites;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import org.ayamemc.ayame.Ayame;
import org.ayamemc.ayame.client.gui.widget.BlurWidget;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.nio.file.Path;

import static org.ayamemc.ayame.util.ResourceLocationHelper.withAyameNamespace;

@Environment(EnvType.CLIENT)
public abstract class AyameMainScreen extends Screen {
    public static final ResourceLocation MENU_BACKGROUND_TEXTURE = withAyameNamespace("textures/gui/background.png");
    public static final ResourceLocation MENU_BACKGROUND_OUTLINE_TEXTURE = withAyameNamespace("textures/gui/background_outline.png");
    public static final ResourceLocation MENU_TOP_LAYER_TEXTURE = withAyameNamespace("textures/gui/top_layer.png");
    public static final ResourceLocation SETTINGS_TEXTURE = withAyameNamespace("textures/gui/settings.png");
    private static final Path MODEL_DIR = Path.of("config/ayame/models/");
    private static final int BACKGROUND_TEXTURE_WIDTH = 410;
    private static final int BACKGROUND_TEXTURE_HEIGHT = 220;
    private static final int BUTTON_SIZE = 32;

    protected final Screen lastScreen;

    public AyameMainScreen(@Nullable Screen lastScreen) {
        super(Component.empty());
        this.lastScreen = lastScreen;
    }

    /**
     * 初始化屏幕方法，可以由子类覆盖
     */
    @Override
    protected void init() {
        BlurWidget blurredBackgroundWidget = new BlurWidget(getCenterX(BACKGROUND_TEXTURE_WIDTH), getCenterY(BACKGROUND_TEXTURE_HEIGHT), BACKGROUND_TEXTURE_WIDTH, BACKGROUND_TEXTURE_HEIGHT);
        this.addRenderableOnly(blurredBackgroundWidget);
        final int searchBarWidth = 208;
        final int searchBarHeight = 20;
        EditBox searchBarEditBox = new EditBox(
                this.font,
                getComponentCenterX(searchBarWidth),
                getComponentTopY(searchBarHeight),
                searchBarWidth,
                searchBarHeight,
                Component.translatable("ayame.widget.searchBarEditBox")
        );
        searchBarEditBox.setHint(Component.translatable("ayame.widget.searchBarEditBox").withStyle(ChatFormatting.DARK_GRAY));
        searchBarEditBox.setBordered(true);
        addRenderableWidget(searchBarEditBox);

    }


    /**
     * 渲染背景纹理
     */
    @Override
    public void renderBackground(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        if (minecraft.level == null) {
            //this.renderPanorama(guiGraphics, partialTick);
            super.renderBackground(guiGraphics, mouseX, mouseY, partialTick);
            renderBackgroundTexture(guiGraphics, mouseX, mouseY, partialTick);
        } else {
            renderBackgroundTexture(guiGraphics, mouseX, mouseY, partialTick);
        }
    }

    protected void renderBackgroundTexture(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
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
        guiGraphics.blit(renderTopLayerResourceLocation(), getCenterX(BACKGROUND_TEXTURE_WIDTH), getCenterY(BACKGROUND_TEXTURE_HEIGHT), 0, 0, BACKGROUND_TEXTURE_WIDTH, BACKGROUND_TEXTURE_HEIGHT, BACKGROUND_TEXTURE_WIDTH, BACKGROUND_TEXTURE_HEIGHT);
        RenderSystem.disableBlend();

        WidgetSprites settingSprites = new WidgetSprites(
                withAyameNamespace("settings"),
                withAyameNamespace("settings_disabled"), // 似乎不会被使用
                withAyameNamespace("settings_enabled_focused")
        );
        WidgetSprites opendirSprites = new WidgetSprites(
                withAyameNamespace("opendir"),
                withAyameNamespace("opendir"), // 似乎不会被使用
                withAyameNamespace("opendir_enabled_focused")
        );
        ImageButton settingsButton = new ImageButton(
                getLeftAlignedX(BACKGROUND_TEXTURE_WIDTH, 0),
                getBottomAlignedY(BACKGROUND_TEXTURE_HEIGHT, BUTTON_SIZE, 0),
                BUTTON_SIZE,
                BUTTON_SIZE,
                settingSprites,
                button -> {
                    Ayame.LOGGER.info("Setting button clicked.");
                    minecraft.setScreen(new SettingsScreen(this));
                },
                Component.empty()
        );
        ImageButton opendirButton = new ImageButton(
                getLeftAlignedX(BACKGROUND_TEXTURE_WIDTH, 0),
                getBottomAlignedY(BACKGROUND_TEXTURE_HEIGHT, BUTTON_SIZE, 90),
                BUTTON_SIZE,
                BUTTON_SIZE,
                opendirSprites,
                button -> {
                    Ayame.LOGGER.info("Opendir button clicked.");
                    Util.getPlatform().openPath(MODEL_DIR);
                },
                Component.empty()
        );
        opendirButton.setTooltip(Tooltip.create(Component.translatable("ayame.button.opendir.tooltip")));

        addRenderableWidget(settingsButton);
        addRenderableWidget(opendirButton);
//        if (this instanceof SettingsScreen) {
//            addRenderableOnly(settingsButton);
//        } else {
//            addRenderableWidget(settingsButton);
//        }

        Component titleText = Component.translatable(setTranslatableTitle());

        // 计算居中显示的 X 坐标
        int centerX = getCenteredStringX(titleText);

        // 渲染文本
        guiGraphics.drawString(this.font, titleText, centerX, font.lineHeight, 0xFFFFFFFF, true);
    }


    /**
     * 实现一个自定义的指定图层
     *
     * @return 你要传入的图片路径
     */
    protected abstract @NotNull ResourceLocation renderTopLayerResourceLocation();

    protected abstract @NotNull String setTranslatableTitle();

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

    protected int getCenteredStringX(Component text) {
        int textWidth = this.font.width(text);
        return (this.width - textWidth) / 2;
    }

    /**
     * 获取与贴图无关的组件在屏幕中心的 X 坐标
     */
    protected int getComponentCenterX(int componentWidth) {
        return (this.width - componentWidth) / 2;
    }

    /**
     * 获取与贴图无关的组件在屏幕中心的 Y 坐标
     */
    protected int getComponentCenterY(int componentHeight) {
        return (this.height - componentHeight) / 2;
    }
    /**
     * 获取组件在屏幕顶部对齐的 Y 坐标
     */
    protected int getComponentTopY(int margin) {
        return margin;
    }

    /**
     * 获取组件在屏幕底部对齐的 Y 坐标
     */
    protected int getComponentBottomY(int componentHeight, int margin) {
        return this.height - componentHeight - margin;
    }

    /**
     * 获取组件在屏幕左边对齐的 X 坐标
     */
    protected int getComponentLeftX(int margin) {
        return margin;
    }

    /**
     * 获取组件在屏幕右边对齐的 X 坐标
     */
    protected int getComponentRightX(int componentWidth, int margin) {
        return this.width - componentWidth - margin;
    }


    @Override
    public void onClose() {
        minecraft.setScreen(lastScreen);
    }
}
