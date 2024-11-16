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
import net.minecraft.ChatFormatting;
import net.minecraft.Util;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.components.ImageButton;
import net.minecraft.client.gui.components.Tooltip;
import net.minecraft.client.gui.components.WidgetSprites;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.inventory.InventoryScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import org.ayamemc.ayame.client.api.ModelResourceAPI;
import org.ayamemc.ayame.model.AyameModelCache;
import org.ayamemc.ayame.model.ModelType;
import org.ayamemc.ayame.model.resource.IModelResource;
import org.ayamemc.ayame.util.ConfigUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.joml.Quaternionf;
import org.joml.Vector3f;

import javax.swing.text.html.parser.Entity;
import java.nio.file.Path;
import java.util.List;

import static org.ayamemc.ayame.util.ResourceLocationHelper.withAyameNamespace;

/**
 * {@code ModelSelectMenuScreen} 负责处理 Ayame 模型的选择界面。
 * <p>
 * 该类继承自 {@link AyameScreen}，主要功能是：
 * <ul>
 *     <li>显示可供选择的模型列表</li>
 *     <li>处理模型选择的逻辑</li>
 *     <li>提供回调接口用于处理屏幕关闭或模型切换时的操作</li>
 * </ul>
 *
 *
 * @see AyameScreen
 */
@Environment(EnvType.CLIENT)
public class ModelSelectMenuScreen extends AyameScreen {
    public static final int searchBarWidth = 112;
    public static final int searchBarHeight = 23;
    protected static final Path MODEL_DIR = Path.of("config/ayame/models/");
    public final List<IModelResource> modelResources;
    public @Nullable ModelType selectedModel = AyameModelCache.getPlayerModel(Minecraft.getInstance().player);


    /**
     * 构造方法，允许设置是否单次跳过警告界面。
     *
     * @param lastScreen      上一个屏幕
     * @param skipWarningOnce 是否跳过一次警告界面
     */
    public ModelSelectMenuScreen(@Nullable Screen lastScreen, boolean skipWarningOnce) {
        super(Component.translatable("ayame.screen.warningscreen.modelselectscreen.title"), lastScreen, skipWarningOnce);
        this.modelResources = ModelResourceAPI.listModels(true);
    }
    public ModelSelectMenuScreen(@Nullable Screen lastScreen) {
        super(Component.translatable("ayame.screen.warningscreen.modelselectscreen.title"), lastScreen, false);
        this.modelResources = ModelResourceAPI.listModels(true);
    }


    @Override
    protected void init() {
        super.init();  // 继承父类的初始化方法，包括警告检查逻辑
        // 搜索框组件初始化
        EditBox searchBox = new EditBox(
                this.font,
                getAlignedX(BACKGROUND_TEXTURE_WIDTH, searchBarWidth, 0, Alignment.CENTER) + 27,
                getAlignedY(BACKGROUND_TEXTURE_HEIGHT, searchBarHeight, 0, Alignment.TOP) + 1,
                searchBarWidth,
                searchBarHeight,
                Component.translatable("ayame.widget.searchBox")
        );
        searchBox.setHint(Component.translatable("ayame.widget.searchBox").withStyle(ChatFormatting.DARK_GRAY));
        searchBox.setBordered(true);
        addRenderableWidget(searchBox);
    }

    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float delta) {
        super.render(guiGraphics, mouseX, mouseY, delta);

        WidgetSprites opendirSprites = new WidgetSprites(
                withAyameNamespace("opendir"),
                withAyameNamespace("opendir"),
                withAyameNamespace("opendir_enabled_focused")
        );
        WidgetSprites listmodeSprites = new WidgetSprites(
                withAyameNamespace("listmode"),
                withAyameNamespace("listmode"),
                withAyameNamespace("listmode_enabled_focused")
        );
        ImageButton opendirButton = new ImageButton(
                getAlignedX(BACKGROUND_TEXTURE_WIDTH, MINI_BUTTON_SIZE, 0, Alignment.RIGHT) - 125,
                getAlignedY(BACKGROUND_TEXTURE_HEIGHT, MINI_BUTTON_SIZE, 0, Alignment.BOTTOM) - 3,
                MINI_BUTTON_SIZE,
                MINI_BUTTON_SIZE,
                opendirSprites,
                button -> {
                    Util.getPlatform().openPath(MODEL_DIR);
                },
                Component.translatable("ayame.button.opendir.tooltip")
        );
        ImageButton listmodeButton = new ImageButton(
                getAlignedX(BACKGROUND_TEXTURE_WIDTH, MINI_BUTTON_SIZE, 0, Alignment.LEFT) + 35,
                getAlignedY(BACKGROUND_TEXTURE_HEIGHT, MINI_BUTTON_SIZE, 0, Alignment.BOTTOM) - 3,
                MINI_BUTTON_SIZE,
                MINI_BUTTON_SIZE,
                listmodeSprites,
                button -> {
                    Util.getPlatform().openPath(MODEL_DIR);
                },
                Component.translatable("ayame.button.listmode.tooltip")
        );
        opendirButton.setTooltip(Tooltip.create(Component.translatable("ayame.button.opendir.tooltip")));
        listmodeButton.setTooltip(Tooltip.create(Component.translatable("ayame.button.listmode.tooltip")));
        addRenderableWidget(opendirButton);
        addRenderableWidget(listmodeButton);
        InventoryScreen.renderEntityInInventoryFollowsMouse(guiGraphics, leftPos + 26, topPos + 8, leftPos + 75, topPos + 78, 30, 0.0625F, mouseX, mouseY, this.minecraft.player);

    }


    @Override
    protected @NotNull ResourceLocation renderTopLayerResourceLocation() {
        return MENU_TOP_LAYER_TEXTURE;
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
    }

}
