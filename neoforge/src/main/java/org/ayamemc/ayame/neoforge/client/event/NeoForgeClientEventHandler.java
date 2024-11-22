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

package org.ayamemc.ayame.neoforge.client.event;

import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.client.event.ClientTickEvent;
import net.neoforged.neoforge.client.event.RenderHandEvent;
import net.neoforged.neoforge.client.event.RenderTooltipEvent;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import org.ayamemc.ayame.client.gui.screen.ModelSelectMenuScreen;
import org.ayamemc.ayame.client.handler.ClientEventHandler;
import org.ayamemc.ayame.util.TaskManager;
import software.bernie.geckolib.util.JsonUtil;

public class NeoForgeClientEventHandler {
    /**
     * 按下按键后打开{@link ModelSelectMenuScreen}屏幕
     */
    @SubscribeEvent
    public static void onClientClick(ClientTickEvent.Post event) {
        while (RegisterKeyEventHandler.MODEL_SELECT_MENU.get().consumeClick()) {
            ClientEventHandler.openSelectMenuKeyPressed();
        }
    }

    @SubscribeEvent
    public static void onPlayerJoin(PlayerEvent.PlayerLoggedInEvent event) {
        // 执行所有任务
        TaskManager.TaskManagerImpls.CLIENT_IN_WORLD_TASKS.executeAll();
        TaskManager.TaskManagerImpls.CLIENT_IN_WORLD_TASKS.setCanExecute(true);
    }

    @SubscribeEvent
    public static void onPlayerLeave(PlayerEvent.PlayerLoggedOutEvent event) {
        TaskManager.TaskManagerImpls.CLIENT_IN_WORLD_TASKS.setCanExecute(false);
    }

    @SubscribeEvent
    public static void renderCustomModelHand(RenderHandEvent event) {
        event.setCanceled(true); // 取消渲染默认手臂
        ClientEventHandler.renderCustomHandEventHandler(
                event.getHand(),
                event.getPoseStack(),
                event.getMultiBufferSource(),
                event.getPackedLight(),
                event.getPartialTick(),
                event.getInterpolatedPitch(),
                event.getSwingProgress(),
                event.getEquipProgress(),
                event.getItemStack()
        );
    }
    @SubscribeEvent
    public static void renderAyameTooltipColor(RenderTooltipEvent.Color event) {
        if (ClientEventHandler.shouldUseAyameTooltipColor()) {
            event.setBorderStart(ClientEventHandler.TOOLTIP_BORDER_TOP_COLOR);
            event.setBorderEnd(ClientEventHandler.TOOLTIP_BORDER_BOTTOM_COLOR);
            event.setBackground(ClientEventHandler.TOOLTIP_BACKGROUND_COLOR);
        }
    }


}
