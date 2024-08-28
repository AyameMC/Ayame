/*
 *      This file is part of Ayame.
 *
 *     Ayame is free software: you can redistribute it and/or modify it under the terms of the GNU Lesser General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.
 *
 *     Ayame is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more details.
 *
 *     You should have received a copy of the GNU Lesser General Public License along with Ayame. If not, see <https://www.gnu.org/licenses/>.
 */

package org.ayamemc.ayame.screen;

import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.layouts.Layout;
import net.minecraft.client.gui.layouts.LinearLayout;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.multiplayer.WarningScreen;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import org.ayamemc.ayame.util.ConfigUtil;

public class CopyrightCautionScreen extends WarningScreen {

    private static final Component TITLE = Component.translatable("ayame.screen.warningscreen.caution.title").withStyle(ChatFormatting.BOLD);
    private static final Component CONTENT = Component.translatable("ayame.screen.warningscreen.caution.content");
    private static final Component CHECK = Component.translatable("multiplayerWarning.check");
    private static final Component NARRATION = TITLE.copy().append("\n").append(CONTENT);
    private final Screen previous;

    public CopyrightCautionScreen(Screen previous) {
        super(TITLE, CONTENT, CHECK, NARRATION);
        this.previous = previous;
    }

    @Override
    protected Layout addFooterButtons() {
        LinearLayout linearLayout = LinearLayout.horizontal().spacing(8);
        linearLayout.addChild(Button.builder(CommonComponents.GUI_PROCEED, button -> {
            if (this.stopShowing.selected()) {
                ConfigUtil.SKIP_AYAME_WARNING = true;
            }

            this.minecraft.setScreen(new ModelSelectMenuScreen(Component.empty(),true));
        }).build());
        linearLayout.addChild(Button.builder(CommonComponents.GUI_BACK, button -> this.onClose()).build());
        return linearLayout;
    }

    @Override
    public void onClose() {
        // 不要setScreen，否则ESC退出不去
        ConfigUtil.save(); // 保存配置
    }

}
