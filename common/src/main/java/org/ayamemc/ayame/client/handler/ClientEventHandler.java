/*
 *     Custom player model mod. Powered by GeckoLib.
 *     Copyright (C) 2024-2025  CrystalNeko, HappyRespawnanchor, pertaz(Icon Designer)
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

package org.ayamemc.ayame.client.handler;

import com.mojang.brigadier.CommandDispatcher;
import net.minecraft.client.Camera;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.commands.CommandBuildContext;
import net.minecraft.commands.SharedSuggestionProvider;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.ayamemc.ayame.client.AyameClient;
import org.ayamemc.ayame.client.command.AyameCommandManager;
import org.ayamemc.ayame.client.gui.screen.AnimationRouletteScreen;
import org.ayamemc.ayame.client.gui.screen.AyameScreen;
import org.ayamemc.ayame.client.gui.screen.ModelSelectMenuScreen;
import org.ayamemc.ayame.client.script.*;
import org.ayamemc.ayame.client.script.event.JsAttackEntityEvent;
import org.ayamemc.ayame.client.script.JavaScriptHelper;
import org.ayamemc.ayame.client.script.event.JsPlayerTickEvent;
import org.ayamemc.ayame.client.yttribume.Yttribumes;
import org.ayamemc.ayame.util.TaskManager;

import java.util.Random;

import static org.ayamemc.ayame.client.AyameClient.MINECRAFT;

public class ClientEventHandler {
    public static final int TOOLTIP_BACKGROUND_COLOR = 0xCC_5f5f5f;
    public static final int TOOLTIP_BORDER_TOP_COLOR = 0xCC_fdc7f5;
    public static final int TOOLTIP_BORDER_BOTTOM_COLOR = 0xCC_fde8f5;

    private static Player getPlayer() {
        return MINECRAFT.player;
    }

    public static boolean shouldUseAyameTooltipColor() {
        return MINECRAFT.screen instanceof AyameScreen;
    }


    public static <T extends SharedSuggestionProvider> void registerClientCommands(CommandDispatcher<T> dispatcher, CommandBuildContext context) {
        AyameCommandManager.createCommands(dispatcher, context);
    }

    public static void openSelectMenuKeyPressed() {
        MINECRAFT.setScreen(new ModelSelectMenuScreen(null));
    }

    public static void openRouletteScreen() {
        AnimationRouletteScreen.open();
    }


    public static void renderHud(GuiGraphics guiGraphics, DeltaTracker tickDelta) {
        float shake = getPlayer().ayame$getYttribume(Yttribumes.GLOBAL_SCREEN_SHAKE);
        if (shake != 0F) {
            // 抖起来
            applyScreenShake(guiGraphics, shake);
        }

    }

    private static void applyScreenShake(GuiGraphics guiGraphics, float intensity) {
        var random = new Random();
        var shakeX = (random.nextFloat() - 0.5f) * 2 * intensity; // 随机偏移X，范围 [-intensity, intensity]
        var shakeY = (random.nextFloat() - 0.5f) * 2 * intensity; // 随机偏移Y，范围 [-intensity, intensity]
        // 在当前渲染矩阵中应用偏移
        guiGraphics.pose().translate(shakeX, shakeY, 0.0);
    }


    public static void renderCamera() {
        Camera camera = MINECRAFT.gameRenderer.getMainCamera();
        Vec3 currentPosition = camera.getPosition();
        camera.setPosition(currentPosition.x, currentPosition.y + getPlayer().ayame$getYttribume(Yttribumes.GLOBAL_CAMERA_Y_OFFSET), currentPosition.z);
    }

    public static void plusCameraYOffset(float offset, boolean reset) {
        if (reset)
            getPlayer().ayame$setYttribume(Yttribumes.GLOBAL_CAMERA_Y_OFFSET, Yttribumes.GLOBAL_CAMERA_Y_OFFSET.defaultValue());
        getPlayer().ayame$setYttribume(Yttribumes.GLOBAL_CAMERA_Y_OFFSET, getPlayer().ayame$getYttribume(Yttribumes.GLOBAL_CAMERA_Y_OFFSET) + offset);
    }

    public static void tick(Minecraft minecraft) {
        TaskManager.TaskManagerImpls.CLIENT_IN_WORLD_TASKS.executeAll();
        JsPlayerTickEvent.INSTANCE.trigger(new JsPlayer(minecraft.player)); // 执行脚本
    }

    public static void attackEntity(Player player, Level level, InteractionHand hand, Entity target) {
        JsAttackEntityEvent.INSTANCE.trigger(new JsPlayer((LocalPlayer) player), new JsWorld(level), new JsEntity(target));
    }

    public static void johnWorld() {
        JavaScriptLoader.runJs();

        // 执行玩家进入世界的任务
        TaskManager.TaskManagerImpls.CLIENT_IN_WORLD_TASKS.setCanExecute(true);
        TaskManager.TaskManagerImpls.CLIENT_IN_WORLD_TASKS.executeAll();

        if (MINECRAFT.isLocalServer()) {
            AyameClient.loadAllModelLocal().join();
            return;
        }

        AyameClient.requestServerSync();
    }

    public static void quiltWorld() {
        JavaScriptHelper.clearAllCallbacks();
        // 停止执行玩家进入世界的任务
        TaskManager.TaskManagerImpls.CLIENT_IN_WORLD_TASKS.setCanExecute(false);
        AyameClient.unloadAllModels();
    }
}
