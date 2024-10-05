package org.ayamemc.ayame.client.gui.screen;

import com.mojang.blaze3d.systems.RenderSystem;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.ChatFormatting;
import net.minecraft.Util;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.components.ImageButton;
import net.minecraft.client.gui.components.Tooltip;
import net.minecraft.client.gui.components.WidgetSprites;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import org.ayamemc.ayame.Ayame;
import org.ayamemc.ayame.client.gui.Alignment;
import org.ayamemc.ayame.client.gui.widget.BlurWidget;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.nio.file.Path;

import static org.ayamemc.ayame.util.ResourceLocationHelper.withAyameNamespace;

@Environment(EnvType.CLIENT)
public abstract class AyameMainScreen extends Screen {
    public static final ResourceLocation MENU_BACKGROUND_TEXTURE = withAyameNamespace("textures/gui/background.png");
    public static final ResourceLocation MENU_BACKGROUND_OUTLINE_TEXTURE = withAyameNamespace("textures/gui/background_outline.png");
    public static final ResourceLocation MENU_TOP_LAYER_TEXTURE = withAyameNamespace("textures/gui/top_layer.png");
    public static final ResourceLocation SETTINGS_TEXTURE = withAyameNamespace("textures/gui/settings.png");
    private static final Path MODEL_DIR = Path.of("config/ayame/models/");
    private static final int BACKGROUND_TEXTURE_WIDTH = 410;
    private static final int BACKGROUND_TEXTURE_HEIGHT = 220;
    private static final int BUTTON_SIZE = 32;

    protected final Screen lastScreen;

    public AyameMainScreen(@Nullable Screen lastScreen) {
        super(Component.empty());
        this.lastScreen = lastScreen;
    }

    @Override
    protected void init() {
        BlurWidget blurredBackgroundWidget = new BlurWidget(getCenteredX(BACKGROUND_TEXTURE_WIDTH), getCenteredY(BACKGROUND_TEXTURE_HEIGHT), BACKGROUND_TEXTURE_WIDTH, BACKGROUND_TEXTURE_HEIGHT);
        this.addRenderableOnly(blurredBackgroundWidget);

        final int searchBarWidth = 112;
        final int searchBarHeight = 23;
        EditBox searchBarEditBox = new EditBox(
                this.font,
                getAlignedX(BACKGROUND_TEXTURE_WIDTH, searchBarWidth, 0, Alignment.CENTER) + 27,
                getAlignedY(BACKGROUND_TEXTURE_HEIGHT, searchBarHeight, 0, Alignment.TOP) + 1,
                searchBarWidth,
                searchBarHeight,
                Component.translatable("ayame.widget.searchBarEditBox")
        );
        searchBarEditBox.setHint(Component.translatable("ayame.widget.searchBarEditBox").withStyle(ChatFormatting.DARK_GRAY));
        searchBarEditBox.setBordered(true);
        addRenderableWidget(searchBarEditBox);
    }

    @Override
    public void renderBackground(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        if (minecraft.level == null) {
            super.renderBackground(guiGraphics, mouseX, mouseY, partialTick);
            renderBackgroundTexture(guiGraphics, mouseX, mouseY, partialTick);
        } else {
            renderBackgroundTexture(guiGraphics, mouseX, mouseY, partialTick);
        }
    }

    protected void renderBackgroundTexture(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        RenderSystem.enableBlend();
        guiGraphics.blit(MENU_BACKGROUND_TEXTURE, getCenteredX(BACKGROUND_TEXTURE_WIDTH), getCenteredY(BACKGROUND_TEXTURE_HEIGHT), 0, 0, BACKGROUND_TEXTURE_WIDTH, BACKGROUND_TEXTURE_HEIGHT, BACKGROUND_TEXTURE_WIDTH, BACKGROUND_TEXTURE_HEIGHT);
        RenderSystem.disableBlend();
    }

    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float delta) {
        super.render(guiGraphics, mouseX, mouseY, delta);
        RenderSystem.enableBlend();
        guiGraphics.blit(MENU_BACKGROUND_OUTLINE_TEXTURE, getCenteredX(BACKGROUND_TEXTURE_WIDTH), getCenteredY(BACKGROUND_TEXTURE_HEIGHT), 0, 0, BACKGROUND_TEXTURE_WIDTH, BACKGROUND_TEXTURE_HEIGHT, BACKGROUND_TEXTURE_WIDTH, BACKGROUND_TEXTURE_HEIGHT);
        guiGraphics.blit(renderTopLayerResourceLocation(), getCenteredX(BACKGROUND_TEXTURE_WIDTH), getCenteredY(BACKGROUND_TEXTURE_HEIGHT), 0, 0, BACKGROUND_TEXTURE_WIDTH, BACKGROUND_TEXTURE_HEIGHT, BACKGROUND_TEXTURE_WIDTH, BACKGROUND_TEXTURE_HEIGHT);
        RenderSystem.disableBlend();

        WidgetSprites settingSprites = new WidgetSprites(
                withAyameNamespace("settings"),
                withAyameNamespace("settings_disabled"),
                withAyameNamespace("settings_enabled_focused")
        );
        WidgetSprites opendirSprites = new WidgetSprites(
                withAyameNamespace("opendir"),
                withAyameNamespace("opendir"),
                withAyameNamespace("opendir_enabled_focused")
        );
        ImageButton settingsButton = new ImageButton(
                getAlignedX(BACKGROUND_TEXTURE_WIDTH, BUTTON_SIZE, 0, Alignment.LEFT),
                getAlignedY(BACKGROUND_TEXTURE_HEIGHT, BUTTON_SIZE, 0, Alignment.BOTTOM),
                BUTTON_SIZE,
                BUTTON_SIZE,
                settingSprites,
                button -> {
                    Ayame.LOGGER.info("Setting button clicked.");
                    minecraft.setScreen(new SettingsScreen(this));
                },
                Component.empty()
        );
        ImageButton opendirButton = new ImageButton(
                getAlignedX(BACKGROUND_TEXTURE_WIDTH, BUTTON_SIZE, 0, Alignment.LEFT),
                getAlignedY(BACKGROUND_TEXTURE_HEIGHT, BUTTON_SIZE, 90, Alignment.BOTTOM),
                BUTTON_SIZE,
                BUTTON_SIZE,
                opendirSprites,
                button -> {
                    Ayame.LOGGER.info("Opendir button clicked.");
                    Util.getPlatform().openPath(MODEL_DIR);
                },
                Component.empty()
        );
        opendirButton.setTooltip(Tooltip.create(Component.translatable("ayame.button.opendir.tooltip")));

        addRenderableWidget(settingsButton);
        addRenderableWidget(opendirButton);

        Component titleText = Component.translatable(setTranslatableTitle());
        int centerX = getCenteredStringX(titleText);
        guiGraphics.drawString(this.font, titleText, centerX, font.lineHeight, 0xFFFFFFFF, true);
    }

    protected abstract @NotNull ResourceLocation renderTopLayerResourceLocation();

    protected abstract @NotNull String setTranslatableTitle();

    protected int getCenteredX(int elementWidth) {
        return (this.width - elementWidth) / 2;
    }

    protected int getCenteredY(int elementHeight) {
        return (this.height - elementHeight) / 2;
    }

    protected int getAlignedX(int containerWidth, int elementWidth, int margin, Alignment alignment) {
        int baseX = getCenteredX(containerWidth);
        switch (alignment) {
            case LEFT:
                return baseX + margin;
            case RIGHT:
                return baseX + containerWidth - elementWidth - margin;
            case CENTER:
            default:
                return baseX + (containerWidth - elementWidth) / 2;
        }
    }

    protected int getCenteredStringX(Component text) {
        int textWidth = this.font.width(text);
        return (this.width - textWidth) / 2;
    }


    protected int getAlignedY(int containerHeight, int elementHeight, int margin, Alignment alignment) {
        int baseY = getCenteredY(containerHeight);
        switch (alignment) {
            case TOP:
                return baseY + margin;
            case BOTTOM:
                return baseY + containerHeight - elementHeight - margin;
            case CENTER:
            default:
                return baseY + (containerHeight - elementHeight) / 2;
        }
    }

    @Override
    public void onClose() {
        minecraft.setScreen(lastScreen);
    }
}