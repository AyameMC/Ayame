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
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.resources.ResourceLocation;
import org.ayamemc.ayame.client.api.ModelResourceAPI;
import org.ayamemc.ayame.client.resource.IModelResource;
import org.ayamemc.ayame.model.AyameModelCache;
import org.ayamemc.ayame.model.AyameModelType;
import org.ayamemc.ayame.util.ConfigUtil;
import org.jetbrains.annotations.Nullable;

import java.util.List;

/**
 * {@code ModelSelectMenuScreen} 负责处理 Ayame 模型的选择界面。
 * <p>
 * 该类继承自 {@link AbstractModelMenuScreen}，主要功能是：
 * <ul>
 *     <li>显示可供选择的模型列表</li>
 *     <li>处理模型选择的逻辑</li>
 *     <li>提供回调接口用于处理屏幕关闭或模型切换时的操作</li>
 * </ul>
 *
 * <p>
 * 在模型选择菜单关闭时，可通过 {@link CloseCallback} 进行关闭时的自定义处理；
 * 在模型切换时，可通过 {@link SwitchModelCallback} 进行切换时的自定义处理。
 * </p>
 *
 * @see AbstractModelMenuScreen
 */
@Environment(EnvType.CLIENT)
public class ModelSelectMenuScreen extends AbstractModelMenuScreen {
    public final boolean skipWarningOnce;
    public final List<IModelResource> modelResources;
    public @Nullable AyameModelType selectedModel = AyameModelCache.getPlayerModel(Minecraft.getInstance().player);
    public @Nullable CloseCallback closeCallback;
    public @Nullable SwitchModelCallback switchModelCallback;

    /**
     * 构造方法，允许设置是否单次跳过警告界面。
     *
     * @param lastScreen      上一个屏幕
     * @param skipWarningOnce 是否跳过一次警告界面
     */
    public ModelSelectMenuScreen(@Nullable Screen lastScreen, boolean skipWarningOnce) {
        super(lastScreen);
        this.skipWarningOnce = skipWarningOnce;
        this.modelResources = ModelResourceAPI.listModels(true);
    }

    /**
     * 带有回调函数的构造方法。
     * <p>
     * 该方法允许为屏幕关闭和模型切换设置回调函数。
     *
     * @param lastScreen          上一个屏幕
     * @param skipWarningOnce     是否跳过一次警告界面
     * @param closeCallback       屏幕关闭时执行的回调
     * @param switchModelCallback 模型切换时执行的回调
     */
    public ModelSelectMenuScreen(@Nullable Screen lastScreen, boolean skipWarningOnce, @Nullable CloseCallback closeCallback, @Nullable SwitchModelCallback switchModelCallback) {
        this(lastScreen, skipWarningOnce);
        this.closeCallback = closeCallback;
        this.switchModelCallback = switchModelCallback;
    }

    /**
     * 默认构造方法，不跳过警告界面。
     *
     * @param lastScreen 上一个屏幕
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
                AyameModelCache.setPlayerModel(Minecraft.getInstance().player, selectedModel);
            }
        });
        Minecraft.getInstance().setScreen(screen);
    }

    /**
     * 屏幕初始化方法，负责初始化模型选择界面。
     * <p>
     * 如果未设置跳过警告，则会先显示警告屏幕。
     */
    @Override
    protected void init() {
        if (!ConfigUtil.SKIP_AYAME_WARNING && !skipWarningOnce) {
            this.minecraft.setScreen(new StatementScreen(this, lastScreen));
            return;
        }
        super.init(); // 调用父类的初始化方法，加载通用的背景和组件
    }


    @Override
    protected ResourceLocation renderTopLayerResourceLocation() {
        return MENU_TOP_LAYER_TEXTURE;
    }

    @Override
    protected String setTranslatableTitle() {
        return "ayame.screen.warningscreen.modelselectscreen.title";
    }


    //        int count = 0;
    //        for (IModelResource res : modelResources){
    //            int x = (this.width / 3); // 一排显示3个按钮
    //            x = x*count + ( x - buttonWidth) / 2;
    //            AyameModelType model = IModelResource.createModelFromResource(res);
    //            // TODO 完成按钮
    //            this.addRenderableWidget(Button.builder(Component.literal(model.metaData().name()), (btn) -> {
    //                if (switchModelCallback != null) {
    //                    // 执行回调
    //                    switchModelCallback.switchModel(modelResources, model);
    //                }
    //                // TODO 切换预览模型
    //            }).bounds(x, y, buttonWidth, buttonHeight).build());
    //            count++;
    //            if (count == 3) {
    //                count = 0;
    //                y += buttonHeight + buttonSpacing; // 下一个按钮的位置
    //            }
    //        }

    /**
     * 当屏幕关闭时，执行回调函数并返回到上一个屏幕。
     */
    @Override
    public void onClose() {
        super.onClose();
        if (closeCallback != null) {
            closeCallback.close(modelResources, selectedModel);
        }
    }

    /**
     * 关闭回调函数的接口，允许在屏幕关闭时进行自定义处理。
     */
    @FunctionalInterface
    public interface CloseCallback {
        void close(List<IModelResource> modelResources, @Nullable AyameModelType selectedModel);
    }

    /**
     * 模型切换回调函数的接口，允许在模型切换时进行自定义处理。
     */
    @FunctionalInterface
    public interface SwitchModelCallback {
        void switchModel(List<IModelResource> modelResources, @Nullable AyameModelType selectedModel);
    }
}
