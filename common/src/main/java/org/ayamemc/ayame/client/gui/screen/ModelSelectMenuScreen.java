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

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import org.ayamemc.ayame.Ayame;
import org.ayamemc.ayame.client.api.ModelResourceAPI;
import org.ayamemc.ayame.client.gui.widget.BlurWidget;
import org.ayamemc.ayame.client.gui.widget.OutlineWidget;
import org.ayamemc.ayame.client.resource.IModelResource;
import org.ayamemc.ayame.model.AyameModelCache;
import org.ayamemc.ayame.model.AyameModelType;
import org.ayamemc.ayame.util.ConfigUtil;
import org.jetbrains.annotations.Nullable;

import java.util.List;

/**
 * 显示Ayame模型选择界面，此屏幕不直接设置玩家模型，需要在CloseCallback中处理
 *
 * @see StatementScreen
 */
@Environment(EnvType.CLIENT)
public class ModelSelectMenuScreen extends Screen {

    public static final ResourceLocation MENU_BACKGROUND = withAyameNamespace("textures/gui/background.png");
    public static final ResourceLocation SETTINGS_ICON = withAyameNamespace("textures/gui/settings.png");
    public static final int BACKGROUND_COLOR = 0xCC212121;
    public final Screen lastScreen;
    public final boolean skipWarningOnce;
    public final List<IModelResource> modelResources;
    public @Nullable CloseCallback closeCallback;
    public @Nullable SwitchModelCallback switchModelCallback;
    public @Nullable AyameModelType selectedModel = AyameModelCache.getPlayerModel(Minecraft.getInstance().player);
    int rectWidth;
    int rectHeight;
    int rectX;
    int rectY;


    /**
     * 重载构造方法，包含skipWarningOnce的布尔值
     *
     * @param lastScreen      上个显示的屏幕
     * @param skipWarningOnce {@code boolean}类型，传入{@code true}则跳过一次{@link StatementScreen}
     */
    public ModelSelectMenuScreen(@Nullable Screen lastScreen, boolean skipWarningOnce) {
        super(Component.empty());
        this.lastScreen = lastScreen;
        this.skipWarningOnce = skipWarningOnce;
        this.modelResources = ModelResourceAPI.listModels(true);
    }

    /**
     * 带有回调函数的构造方法
     * @param lastScreen 上个显示的屏幕
     * @param skipWarningOnce {@code boolean}类型，传入{@code true}则跳过一次{@link StatementScreen}
     * @param callback  关闭回调函数，在屏幕关闭时会执行<br></br>
     * 回调示例
     * <pre>{@code
     * (resources, selectedModel)->{
     *     // 你的代码
     * }}</pre>
     * @param switchModelCallback  切换模型回调函数，在切换模型时会执行<br></br>
     * 回调示例
     * <pre>{@code
     * (resources, selectedModel)->{
     *     // 你的代码
     * }}
     */
    public ModelSelectMenuScreen(@Nullable Screen lastScreen, boolean skipWarningOnce, @Nullable CloseCallback callback, @Nullable SwitchModelCallback switchModelCallback) {
        this(lastScreen, skipWarningOnce);
        this.closeCallback = callback;
        this.switchModelCallback = switchModelCallback;
    }

    /**
     * 重载构造方法，没有{@code skipWarningOnce}参数
     *
     * @param lastScreen 上个显示的屏幕
     */
    public ModelSelectMenuScreen(@Nullable Screen lastScreen) {
        this(lastScreen, false);
    }


    /**
     * 打开模型选择菜单并在选择后切换模型
     *
     * @param lastScreen 上一个屏幕
     */
    public static void openDefaultModelSelectMenu(Screen lastScreen) {
        ModelSelectMenuScreen screen = new ModelSelectMenuScreen(lastScreen, false, (modelResources, selectedModel) -> {
            // close的callback,也许以后用的上
        }, (modelResources, selectedModel) -> {
            if (selectedModel != null) {
                AyameModelCache.setPlayerModel(Minecraft.getInstance().player,selectedModel);
            }
        });
        Minecraft.getInstance().setScreen(screen);
    }

    /**
     * 屏幕初始化，按钮注册和{@code builder}都在里面
     */
    @Override
    protected void init() {
        if (!ConfigUtil.SKIP_AYAME_WARNING && !skipWarningOnce) {
            this.minecraft.setScreen(new StatementScreen(this, lastScreen));
            return;
        }
        int buttonWidth = 100; // 每个按钮的宽度
        int buttonHeight = 20; // 每个按钮的高度
        int buttonSpacing = 10; // 按钮之间的间距
        int y = this.height / 8; // 计算起始y坐标

        int count = 0;

        rectWidth = (int) (this.width * 0.8);
        rectHeight = (int) (this.height * 0.78);

        // 屏幕居中：矩形的左上角坐标 (x1, y1)
        rectX = (this.width - rectWidth) / 2;
        rectY = (this.height - rectHeight) / 2;
        BlurWidget blurredBackgroundWidget = new BlurWidget(rectX, rectY, rectWidth , rectHeight );
        this.addRenderableOnly(blurredBackgroundWidget);
        OutlineWidget outline = new OutlineWidget(rectX, rectY, rectWidth, rectHeight, 0xFF4e4e4e); // 淡灰色轮廓
        this.addRenderableOnly(outline);

        for (IModelResource res : modelResources){
            int x = (this.width / 3); // 一排显示3个按钮
            x = x*count + ( x - buttonWidth) / 2;
            AyameModelType model = IModelResource.createModelFromResource(res);
            // TODO 完成按钮
            this.addRenderableWidget(Button.builder(Component.literal(model.metaData().name()), (btn) -> {
                if (switchModelCallback != null) {
                    // 执行回调
                    switchModelCallback.switchModel(modelResources, model);
                }
                // TODO 切换预览模型
            }).bounds(x, y, buttonWidth, buttonHeight).build());
            count++;
            if (count == 3) {
                count = 0;
                y += buttonHeight + buttonSpacing; // 下一个按钮的位置
            }
        }



    }


    /**
     * 渲染屏幕的方法，继承自{@link Screen}
     *
     * @param context the GuiGraphics object used for rendering.
     * @param mouseX  the x-coordinate of the mouse cursor.
     * @param mouseY  the y-coordinate of the mouse cursor.
     * @param delta   the partial tick time.
     */
    @Override
    public void render(GuiGraphics context, int mouseX, int mouseY, float delta) {
        super.render(context, mouseX, mouseY, delta);

        // Minecraft doesn't have a "label" widget, so we'll have to draw our own text.
        // We'll subtract the font height from the Y position to make the text appear above the button.
        // Subtracting an extra 10 pixels will give the text some padding.
        // textRenderer, text, x, y, color, hasShadow
        //context.drawString(this.font, "Model Select Menu", 200, 40 - this.font.lineHeight - 10, 0xFFFFFFFF, true);
        //context.drawString(this.font, "Model 2", 40, 40 - this.font.lineHeight - 10, 0xFFFFFFFF, true);
    }

    /**
     * 渲染背景，填充背景颜色
     */
    @Override
    public void renderBackground(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        // 调整矩形的宽度为屏幕宽度的 80%，高度为屏幕高度的 78%


        // 绘制灰色的背景矩形
        rectWidth = (int) (this.width * 0.8);
        rectHeight = (int) (this.height * 0.78);

        // 屏幕居中：矩形的左上角坐标 (x1, y1)
        rectX = (this.width - rectWidth) / 2;
        rectY = (this.height - rectHeight) / 2;


        guiGraphics.fill(rectX, rectY, rectX + rectWidth, rectY + rectHeight, BACKGROUND_COLOR);
//        guiGraphics.renderOutline(rectX, rectY, rectWidth, rectHeight, 0xFFfa1f54);
    }

    /**
     * 当屏幕退出时执行的代码
     */
    @Override
    public void onClose() {
        minecraft.setScreen(lastScreen);
        if (closeCallback != null) {
            closeCallback.close(modelResources, selectedModel);
        }
    }

    /**
     * 关闭回调函数
     */
    @FunctionalInterface
    public interface CloseCallback {
        void close(List<IModelResource> modelResources, @Nullable AyameModelType selectedModel);
    }

    @FunctionalInterface
    public interface SwitchModelCallback {
        void switchModel(List<IModelResource> modelResources, @Nullable AyameModelType selectedModel);
    }
    private static ResourceLocation withAyameNamespace(String location) {
        return ResourceLocation.fromNamespaceAndPath(Ayame.MOD_ID, location);
    }

}