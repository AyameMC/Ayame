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

package org.ayamemc.ayame.client.screen;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import org.ayamemc.ayame.client.api.ModelResourceAPI;
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
    public final Screen lastScreen;
    public final boolean skipWarningOnce;
    public final List<IModelResource> modelResources;
    public @Nullable CloseCallback closeCallback;
    public @Nullable SwitchModelCallback switchModelCallback;
    public @Nullable AyameModelType selectedModel = AyameModelCache.getPlayerModel(Minecraft.getInstance().player);

    /**
     * 重载构造方法，包含skipWarningOnce的布尔值
     *
     * @param title           {@link Component}类型，为屏幕标题
     * @param lastScreen      上个显示的屏幕
     * @param skipWarningOnce {@code boolean}类型，传入{@code true}则跳过一次{@link StatementScreen}
     */
    public ModelSelectMenuScreen(Component title, @Nullable Screen lastScreen, boolean skipWarningOnce) {
        super(title);
        this.lastScreen = lastScreen;
        this.skipWarningOnce = skipWarningOnce;
        this.modelResources = ModelResourceAPI.listModels(true);
    }

    /**
     * 带有回调函数的构造方法
     * @param title      {@link Component}类型，为屏幕标题
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
    public ModelSelectMenuScreen(Component title, @Nullable Screen lastScreen, boolean skipWarningOnce,@Nullable CloseCallback callback,@Nullable SwitchModelCallback switchModelCallback) {
        this(title, lastScreen, skipWarningOnce);
        this.closeCallback = callback;
        this.switchModelCallback = switchModelCallback;
    }

    /**
     * 重载构造方法，没有{@code skipWarningOnce}参数
     *
     * @param title      {@link Component}类型，为屏幕标题
     * @param lastScreen 上个显示的屏幕
     */
    public ModelSelectMenuScreen(Component title, @Nullable Screen lastScreen) {
        this(title, lastScreen, false);
    }


    /**
     * 打开模型选择菜单并在选择后切换模型
     *
     * @param lastScreen 上一个屏幕
     */
    public static void openDefaultModelSelectMenu(Screen lastScreen) {
        ModelSelectMenuScreen screen = new ModelSelectMenuScreen(Component.empty(), lastScreen, false, (modelResources,selectedModel) -> {
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
//        // 创建按钮
//        Button buttonWidget = Button.builder(Component.literal("Model 1"), (btn) -> {
//
//            this.minecraft.player.connection.sendChat("大家好啊今天给大家来点想看的东西");
//            switchModelCallback.switchModel(availableModels, selectedModel);
//        }).bounds(150, 40, 120, 20).build();
//
//
//        Button buttonWidget1 = Button.builder(Component.literal("Model 2"), (btn) -> {
//            // 行为这里改
//            this.minecraft.player.connection.sendChat("大家好啊昨天给大家来点不想看的东西");
//        }).bounds(200, 60, 120, 20).build();
//
//
//        // 注册按钮组件
//        this.addRenderableWidget(buttonWidget);
//        this.addRenderableWidget(buttonWidget1);

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
        context.drawString(this.font, "Model Select Menu", 200, 40 - this.font.lineHeight - 10, 0xFFFFFFFF, true);
        //context.drawString(this.font, "Model 2", 40, 40 - this.font.lineHeight - 10, 0xFFFFFFFF, true);
    }

    @Override
    public void renderBackground(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        // 不渲染背景
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
}