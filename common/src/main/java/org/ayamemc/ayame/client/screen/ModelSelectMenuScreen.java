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
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.PreparableReloadListener;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.util.profiling.InactiveProfiler;
import net.minecraft.util.profiling.ProfilerFiller;
import org.ayamemc.ayame.client.resource.ResourceUtil;
import org.ayamemc.ayame.util.ConfigUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.cache.GeckoLibCache;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;

import static org.ayamemc.ayame.Ayame.MOD_ID;

@Environment(EnvType.CLIENT)
public class ModelSelectMenuScreen extends Screen  implements PreparableReloadListener, AutoCloseable {
    public final Screen lastScreen;
    public final boolean skipWarningOnce;

    public ModelSelectMenuScreen(Component title, @Nullable Screen lastScreen, boolean skipWarningOnce) {
        super(title);
        this.lastScreen = lastScreen;
        this.skipWarningOnce = skipWarningOnce;
    }

    // 重载构造方法，默认 skipWarningOnce 为 false
    public ModelSelectMenuScreen(Component title, @Nullable Screen lastScreen) {
        this(title, lastScreen, false);
    }

    @Override
    protected void init() {
        if (!ConfigUtil.SKIP_AYAME_WARNING && !skipWarningOnce) {
            this.minecraft.setScreen(new StatementScreen(this, lastScreen));
            return;
        }

        // TODO ：删除这段代码，我只是拿来测试的
        ResourceUtil.writeResource(ResourceLocation.fromNamespaceAndPath(
                MOD_ID, "geo/ayame/"
        ), "Hello World!");


        // TODO: 模型切换
        // TODO: 调用GeckoLib 的reload方法
        // 创建按钮
        Button buttonWidget = Button.builder(Component.literal("Model 1"), (btn) -> {
            Minecraft minecraft = this.minecraft;
            ResourceManager resourceManager = minecraft.getResourceManager();
            Executor backgroundExecutor = minecraft;
            Executor gameExecutor = minecraft;
            ProfilerFiller preparationsProfiler = InactiveProfiler.INSTANCE;
            ProfilerFiller reloadProfiler = InactiveProfiler.INSTANCE;
            // 想办法调用this.reload


            this.minecraft.player.connection.sendChat("大家好啊今天给大家来点想看的东西");
        }).bounds(150, 40, 120, 20).build();



        Button buttonWidget1 = Button.builder(Component.literal("Model 2"), (btn) -> {
            // When the button is clicked, we can display a toast to the screen.

            // 行为这里改
            this.minecraft.player.connection.sendChat("大家好啊昨天给大家来点不想看的东西");
        }).bounds(200, 60, 120, 20).build();

        // x, y, width, height
        // It's recommended to use the fixed height of 20 to prevent rendering issues with the button
        // textures.

        // Register the button widget.
        this.addRenderableWidget(buttonWidget);
        this.addRenderableWidget(buttonWidget1);

    }

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
    public void onClose() {
        super.onClose();
        // minecraft.setScreen(lastScreen); 有必要吗，换完模型直接关就行，没必要这样
    }

    @Override
    public void close() throws Exception {

    }

    @Override
    public @NotNull CompletableFuture<Void> reload(PreparationBarrier preparationBarrier, ResourceManager resourceManager, ProfilerFiller preparationsProfiler, ProfilerFiller reloadProfiler, Executor backgroundExecutor, Executor gameExecutor) {
        return GeckoLibCache.reload(
                preparationBarrier,
                resourceManager,
                preparationsProfiler,
                reloadProfiler,
                backgroundExecutor,
                gameExecutor
        );
    }
}