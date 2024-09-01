/*
 *      Custom player model mod. Based on GeckoLib.
 *      Copyright (C) 2024  CrystalNeko, HappyRespawnanchor, pertaz(Icon Desiger)
 *
 *      This file is part of Ayame.
 *
 *     Ayame is free software: you can redistribute it and/or modify it under the terms of the GNU Lesser General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.
 *
 *     Ayame is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more details.
 *
 *     You should have received a copy of the GNU Lesser General Public License along with Ayame. If not, see <https://www.gnu.org/licenses/>.
 */

package org.ayamemc.ayame.client.screen;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import org.ayamemc.ayame.Ayame;
import org.ayamemc.ayame.client.renderer.GeoPlayerRender;
import org.ayamemc.ayame.client.resource.ModelResource;
import org.ayamemc.ayame.client.util.GeckoLibCacheWriteMapUtil;
import org.ayamemc.ayame.util.ConfigUtil;
import org.jetbrains.annotations.Nullable;

/**
 * 显示Ayame模型选择界面
 *
 * @see StatementScreen
 */
@Environment(EnvType.CLIENT)
public class ModelSelectMenuScreen extends Screen {
    public final Screen lastScreen;
    public final boolean skipWarningOnce;

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
    }

    /**
     * 重载构造方法，没有skipWarningOnce参数
     *
     * @param title      {@link Component}类型，为屏幕标题
     * @param lastScreen 上个显示的屏幕
     */
    public ModelSelectMenuScreen(Component title, @Nullable Screen lastScreen) {
        this(title, lastScreen, false);
    }

    /**
     * Ayame 本体使用的模型添加方法，<b><font color="red">Ayame以外的模组不应该调用</font></b>
     * <p>
     * <s>{@code private}的你也调不了啊，难道上访问加宽器？你访问它干嘛</s>
     * @param modelRes 传入{@link ModelResource}类型，模型资源（如json）的路径
     */
    private static void addModelResource(ModelResource modelRes) {
        final String MOD_ID = Ayame.MOD_ID;
        GeckoLibCacheWriteMapUtil.addModelResource(MOD_ID, modelRes);
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

        // TODO: 模型切换
        // TODO: 调用GeckoLib 的reload方法
        // 创建按钮
        Button buttonWidget = Button.builder(Component.literal("Model 1"), (btn) -> {


            // 加载模型
            ModelResource modelRes = ModelResource.addModel("config/ayame/models/classic_neko.zip");
            ModelSelectMenuScreen.addModelResource(modelRes);
            GeoPlayerRender.GeoPlayerModel.switchModel(modelRes);

            this.minecraft.player.connection.sendChat("大家好啊今天给大家来点想看的东西");
        }).bounds(150, 40, 120, 20).build();


        Button buttonWidget1 = Button.builder(Component.literal("Model 2"), (btn) -> {
            // 行为这里改
            this.minecraft.player.connection.sendChat("大家好啊昨天给大家来点不想看的东西");
        }).bounds(200, 60, 120, 20).build();


        // 注册按钮组件
        this.addRenderableWidget(buttonWidget);
        this.addRenderableWidget(buttonWidget1);

    }

    /**
     * 渲染屏幕的方法，继承自{@link Screen}
     * @param context the GuiGraphics object used for rendering.
     * @param mouseX the x-coordinate of the mouse cursor.
     * @param mouseY the y-coordinate of the mouse cursor.
     * @param delta the partial tick time.
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

    /**
     * 当屏幕退出时执行的代码
     */
    @Override
    public void onClose() {
        super.onClose();
        // minecraft.setScreen(lastScreen); 有必要吗，换完模型直接关就行，没必要这样
    }
}