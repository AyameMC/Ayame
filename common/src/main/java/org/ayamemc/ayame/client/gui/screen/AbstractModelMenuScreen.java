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
    protected final Screen lastScreen;
    private final int backgroundTextureWidth = 410;
    private final int backgroundTextureHeight = 220;

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
        BlurWidget blurredBackgroundWidget = new BlurWidget(getCenterX(backgroundTextureWidth), getCenterY(backgroundTextureHeight), backgroundTextureWidth, backgroundTextureHeight);
        this.addRenderableOnly(blurredBackgroundWidget);
    }

    /**
     * 渲染背景纹理
     */
    @Override
    public void renderBackground(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        RenderSystem.enableBlend();
        guiGraphics.blit(MENU_BACKGROUND_TEXTURE, getCenterX(backgroundTextureWidth), getCenterY(backgroundTextureHeight), 0, 0, backgroundTextureWidth, backgroundTextureHeight, backgroundTextureWidth, backgroundTextureHeight);
        RenderSystem.disableBlend();
    }

    /**
     * 渲染屏幕内容，包括背景边框
     */
    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float delta) {
        super.render(guiGraphics, mouseX, mouseY, delta);
        RenderSystem.enableBlend();
        guiGraphics.blit(MENU_BACKGROUND_OUTLINE_TEXTURE, getCenterX(backgroundTextureWidth), getCenterY(backgroundTextureHeight), 0, 0, backgroundTextureWidth, backgroundTextureHeight, backgroundTextureWidth, backgroundTextureHeight);
        guiGraphics.blit(MENU_TOP_LAYER_TEXTURE, getCenterX(backgroundTextureWidth), getCenterY(backgroundTextureHeight), 0, 0, backgroundTextureWidth, backgroundTextureHeight, backgroundTextureWidth, backgroundTextureHeight);
        RenderSystem.disableBlend();

        WidgetSprites settingSprites = new WidgetSprites(
                withAyameNamespace("settings"),
                withAyameNamespace("settings"),
                withAyameNamespace("settings")
        );
        ImageButton imageButton = new ImageButton(
                getLeftAlignedX(),
                getBottomAlignedY(),
                32,
                32,
                settingSprites,
                button -> {
                    // 按钮点击后的行为
                    System.out.println("ImageButton");
                },
                Component.literal("Image Button")
        );
        addRenderableWidget(imageButton);

    }


    protected int getCenterX(int textureWidth) {
        return (this.width - textureWidth) / 2;
    }

    protected int getCenterY(int textureHeight) {
        return (this.height - textureHeight) / 2;
    }
    protected int getLeftAlignedX() {
        // 偏移左边的一个固定距离，使得按钮在屏幕左边对齐
        return this.width / 2 - backgroundTextureWidth / 2 + 10; // 10 为与左边框的距离，可以调整
    }

    protected int getBottomAlignedY() {
        // 偏移底部的一个固定距离，使得按钮在屏幕下方对齐
        return this.height / 2 + backgroundTextureHeight / 2 - 32 - 10; // 10 为与底边框的距离，可以调整
    }

}
