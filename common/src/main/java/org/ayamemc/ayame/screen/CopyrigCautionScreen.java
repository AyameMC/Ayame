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

public class CopyrigCautionScreen extends WarningScreen {

    private static final Component TITLE = Component.literal("警告：版权声明").withStyle(ChatFormatting.BOLD);
    private static final Component CONTENT = Component.literal("Ayame是自由软件：您可以根据自由软件基金会发布的GNU宽通用公共许可证的条款，重新分发和/或修改Ayame；可以使用许可证的第3版或（根据您的选择）任何更高版本。\nAyame的发布目的是希望它能发挥作用，但不提供任何形式的担保；甚至不包含适销性或适用于特定目的的默示担保。有关更多详细信息，请参阅GNU宽通用公共许可证。\n您应该已经随Ayame一起收到了一份GNU宽通用公共许可证。如果没有，请参见 <https://www.gnu.org/licenses/>。");
    private static final Component CHECK = Component.translatable("multiplayerWarning.check");
    private static final Component NARRATION = TITLE.copy().append("\n").append(CONTENT);
    private final Screen previous;

    public CopyrigCautionScreen(Screen previous) {
        super(TITLE, CONTENT, CHECK, NARRATION);
        this.previous = previous;
    }

    @Override
    protected Layout addFooterButtons() {
        LinearLayout linearLayout = LinearLayout.horizontal().spacing(8);
        linearLayout.addChild(Button.builder(CommonComponents.GUI_PROCEED, button -> {
            if (this.stopShowing.selected()) {
                ConfigUtil configUtil = new ConfigUtil();
                configUtil.setSkipAyameWarning(true);
            }

            this.minecraft.setScreen(new ModelSelectMenuScreen(Component.empty(),true));
        }).build());
        linearLayout.addChild(Button.builder(CommonComponents.GUI_BACK, button -> this.onClose()).build());
        return linearLayout;
    }

//        @Override
//        public void onClose() {
//        this.minecraft.setScreen(e);
//    }

}
