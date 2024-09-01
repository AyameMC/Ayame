/*
 *      Custom player model mod. Powered by GeckoLib.
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
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.layouts.Layout;
import net.minecraft.client.gui.layouts.LinearLayout;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.multiplayer.WarningScreen;
import net.minecraft.network.chat.ClickEvent;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import org.ayamemc.ayame.util.ConfigUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;


/**
 * 显示Ayame模型选择界面的警告屏幕
 *
 * @see ModelSelectMenuScreen
 * @see WarningScreen
 */
@Environment(EnvType.CLIENT)
public class StatementScreen extends WarningScreen {
    private static final Component TITLE = Component.translatable("ayame.screen.warningscreen.statementscreen.title").withStyle(ChatFormatting.BOLD);
    private static final Component CONTENT = Component.translatable("ayame.screen.warningscreen.statementscreen.content").withStyle(Style.EMPTY.withClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, "https://www.gnu.org/licenses/")));
    private static final Component CHECK = Component.translatable("multiplayerWarning.check");
    private static final Component NARRATION = TITLE.copy().append("\n").append(CONTENT);

    public final Screen lastScreen;
    public final Screen lastLastScreen;
    private boolean open = false;
    private boolean skipWarningOnce = false; // 新增变量用于控制单次跳过

    /**
     * @param lastScreen     上一个显示的屏幕
     * @param lastLastScreen 上上个显示的屏幕
     */
    public StatementScreen(@Nullable Screen lastScreen, @Nullable Screen lastLastScreen) {
        super(TITLE, CONTENT, CHECK, NARRATION);
        this.lastScreen = lastScreen;
        this.lastLastScreen = lastLastScreen;
    }

    /**
     * 方法中的的{@code selected}通过访问拓宽器修改为{@code true}，以实现“【✔❌】不再显示此屏幕”默认【✔】勾选的效果
     *
     * @return 返回 {@link LinearLayout}类型
     */
    @Override
    protected @NotNull Layout addFooterButtons() {
        this.stopShowing.selected = true; // 默认勾选“不再显示此屏幕”
        LinearLayout linearLayout = LinearLayout.horizontal().spacing(8);
        linearLayout.addChild(Button.builder(CommonComponents.GUI_PROCEED, button -> {
            if (this.stopShowing.selected()) {
                ConfigUtil.SKIP_AYAME_WARNING = true;
            } else {
                skipWarningOnce = true; // 设置单次跳过
            }
            open = true;
            this.onClose();
        }).build());
        linearLayout.addChild(Button.builder(CommonComponents.GUI_BACK, button -> this.onClose()).build());
        return linearLayout;
    }


    /**
     * 当屏幕退出时执行的代码
     */
    @Override
    public void onClose() {
        if (open) {
            // 将 skipWarningOnce 传递给下一个屏幕
            minecraft.setScreen(new ModelSelectMenuScreen(Component.literal("Model Select"), lastScreen, skipWarningOnce));
        } else {
            minecraft.setScreen(lastLastScreen);
        }
        ConfigUtil.save(); // 保存配置
    }
}
