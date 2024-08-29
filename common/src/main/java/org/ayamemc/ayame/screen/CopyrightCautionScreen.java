package org.ayamemc.ayame.screen;

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
import org.jetbrains.annotations.Nullable;

public class CopyrightCautionScreen extends WarningScreen {
    private static final Component TITLE = Component.translatable("ayame.screen.warningscreen.caution.title").withStyle(ChatFormatting.BOLD);
    private static final Component CONTENT = Component.translatable("ayame.screen.warningscreen.caution.content").withStyle(Style.EMPTY.withClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, "https://www.gnu.org/licenses/")));
    private static final Component CHECK = Component.translatable("multiplayerWarning.check");
    private static final Component NARRATION = TITLE.copy().append("\n").append(CONTENT);

    public final Screen lastScreen;
    public final Screen lastLastScreen;
    private boolean open = false;
    private boolean skipWarningOnce = false; // 新增变量用于控制单次跳过

    public CopyrightCautionScreen(@Nullable Screen lastScreen, @Nullable Screen lastLastScreen) {
        super(TITLE, CONTENT, CHECK, NARRATION);
        this.lastScreen = lastScreen;
        this.lastLastScreen = lastLastScreen;
    }

    @Override
    protected Layout addFooterButtons() {
        this.stopShowing.selected = true;
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
