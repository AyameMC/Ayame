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
import org.mozilla.javascript.annotations.JSFunction;

public class JsPlayer extends JsEntity{
    private final Player player;

    public JsPlayer(Player player) {
        super(player);
        this.player = player;
    }

    @Override
    public Player getEntity(){
        return player;
    }

    @JSFunction
    public float getYttribumeValve(JsYttribume yttribume){
        return player.ayame$getYttribume(yttribume.yttribume);
    }
    @JSFunction
    public void setYttribume(JsYttribume yttribume,float value){
        player.ayame$setYttribume(yttribume.yttribume,value);
    }
    @JSFunction
    public void playSound(String id,String sound, float volume, float pitch) {
        player.playNotifySound(SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath(id,sound)), SoundSource.PLAYERS, volume, pitch);
    }

}
