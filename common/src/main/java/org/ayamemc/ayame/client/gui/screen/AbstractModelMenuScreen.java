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
    protected final Screen lastScreen;
    protected final int textureWidth = 410;
    protected final int textureHeight = 220;

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
        BlurWidget blurredBackgroundWidget = new BlurWidget(getCenterX(), getCenterY(), textureWidth, textureHeight);
        this.addRenderableOnly(blurredBackgroundWidget);
    }

    /**
     * 渲染背景纹理
     */
    @Override
    public void renderBackground(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        RenderSystem.enableBlend();
        guiGraphics.blit(MENU_BACKGROUND_TEXTURE, getCenterX(), getCenterY(), 0, 0, textureWidth, textureHeight, textureWidth, textureHeight);
        RenderSystem.disableBlend();
    }

    /**
     * 渲染屏幕内容，包括背景边框
     */
    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float delta) {
        super.render(guiGraphics, mouseX, mouseY, delta);
        RenderSystem.enableBlend();
        guiGraphics.blit(MENU_BACKGROUND_OUTLINE_TEXTURE, getCenterX(), getCenterY(), 0, 0, textureWidth, textureHeight, textureWidth, textureHeight);
        guiGraphics.blit(MENU_TOP_LAYER_TEXTURE, getCenterX(), getCenterY(), 0, 0, textureWidth, textureHeight, textureWidth, textureHeight);
        RenderSystem.disableBlend();
    }

    protected int getCenterX() {
        return (this.width - textureWidth) / 2;
    }

    protected int getCenterY() {
        return (this.height - textureHeight) / 2;
    }
}
