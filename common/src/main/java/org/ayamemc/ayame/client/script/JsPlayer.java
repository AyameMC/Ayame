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

package org.ayamemc.ayame.client.script;

import net.minecraft.client.player.LocalPlayer;
import net.minecraft.network.chat.Component;
import net.minecraft.world.level.Level;
import org.ayamemc.ayame.client.yttribume.Yttribume;
import org.mozilla.javascript.annotations.JSFunction;

import java.util.List;

import static net.minecraft.client.Minecraft.getInstance;
public class JsPlayer {
    public LocalPlayer player;

    public JsPlayer(LocalPlayer player) {
        this.player = player;
    }

    @JSFunction
    public void sendMessage(String message) {
        getInstance().player.sendSystemMessage(Component.nullToEmpty(message));
    }

    @JSFunction
    public JsWorld getWorld() {
        Level world = player.level(); // 获取玩家所在的Level对象
        return new JsWorld(world);    // 返回JsWorld实例
    }
    @JSFunction
    public float getYttribume(JsYttribume yttribume){
        return player.ayame$getYttribume(yttribume.yttribume);
    }
    @JSFunction
    public void setYttribume(JsYttribume yttribume,float value){
        player.ayame$setYttribume(yttribume.yttribume,value);
    }

}
