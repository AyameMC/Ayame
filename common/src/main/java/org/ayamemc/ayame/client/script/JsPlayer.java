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
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.player.Player;
import org.ayamemc.ayame.music.AyameSoundMusic;
import org.mozilla.javascript.annotations.JSFunction;
import org.mozilla.javascript.annotations.JSStaticFunction;

import static net.minecraft.client.Minecraft.getInstance;
public class JsPlayer extends JsEntity {
    private final LocalPlayer player;

    public JsPlayer(LocalPlayer player) {
        super(player);
        this.player = player;
    }

    @Override
    public Player getEntity() {
        return player;
    }

    @JSStaticFunction
    public static JsPlayer getPlayer() {
        return new JsPlayer(getInstance().player);
    }

    @JSFunction
    public void sendCommand(String command) {
        player.connection.sendCommand(command);
    }

    @JSFunction
    public void sendChatMessage(String message) {
        player.connection.sendChat(message);
    }

    @JSFunction
    public float getYttribumeValve(JsYttribume yttribume) {
        return player.ayame$getYttribume(yttribume.yttribume);
    }

    @JSFunction
    public void setYttribume(JsYttribume yttribume, float value) {
        player.ayame$setYttribume(yttribume.yttribume, value);
    }

    @JSFunction
    public void playSound(String location, float volume, float pitch) {
        player.playNotifySound(SoundEvent.createVariableRangeEvent(ResourceLocation.parse(location)), SoundSource.PLAYERS, volume, pitch);
    }

    @JSFunction
    public void playAnim(String animationName, boolean isLoop) {
        player.ayame$playAnimation(animationName, isLoop);
    }

    @JSFunction
    public void playSound(String soundName) {
        AyameSoundMusic.play("", soundName, url -> new  AyameSoundMusic());
    }
}
