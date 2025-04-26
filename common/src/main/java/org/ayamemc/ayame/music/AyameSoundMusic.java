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

package org.ayamemc.ayame.music;

import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.sounds.AbstractTickableSoundInstance;
import net.minecraft.client.resources.sounds.SoundInstance;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundSource;
import org.ayamemc.ayame.Ayame;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.function.Function;

import static org.ayamemc.ayame.Ayame.LOGGER;

public class AyameSoundMusic extends AbstractTickableSoundInstance {
    public AyameSoundMusic() {
        super(Ayame.ayameSound, SoundSource.MUSIC, SoundInstance.createUnseededRandom());
    }

    private static final String ERROR_404 = "http://music.163.com/404";
    private static final String MUSIC_163_URL = "https://music.163.com/";
    private static final String LOCAL_FILE_PROTOCOL = "file";

    @Override
    public void tick() {

    }

    public static void play(String url, String songName, Function<URL, SoundInstance> sound) {
        url = "http://127.0.0.1:8000/a.mp3";
//        if (url.startsWith(MUSIC_163_URL)) {
//
//        }
//        if (url != null && !url.equals(ERROR_404)) {
            playMusic(url, songName, sound);
//        }
    }

    private static void playMusic(String url, String songName, Function<URL, SoundInstance> sound) {
        final URL urlFinal;
        try {
            urlFinal = new URL(url);
            // 如果是本地文件
            if (urlFinal.getProtocol().equals(LOCAL_FILE_PROTOCOL)) {
                File file = new File(urlFinal.toURI());
                if (!file.exists()) {
                    LOGGER.info("File not found: {}", url);
                    return;
                }
            }
            LOGGER.info("Playing {}", url);

            Minecraft.getInstance().submitAsync(() -> {
                Minecraft.getInstance().getSoundManager().play(sound.apply(urlFinal));
                Minecraft.getInstance().gui.setNowPlaying(Component.literal(songName));
            });
        } catch (MalformedURLException | URISyntaxException e) {
            e.printStackTrace();
        }
    }
}
