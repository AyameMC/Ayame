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

import org.lwjgl.openal.AL10;
import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import org.lwjgl.BufferUtils;

import static org.lwjgl.openal.AL10.*;

public class SimpleSoundPlayer {
    private int buffer;
    private int source;

    public SimpleSoundPlayer(File wavFile) throws Exception {
        // 加载音频文件
        AudioInputStream ais = AudioSystem.getAudioInputStream(wavFile);
        AudioFormat format = ais.getFormat();

        if (format.getEncoding() != AudioFormat.Encoding.PCM_SIGNED) {
            throw new IllegalArgumentException("需要16位PCM编码");
        }

        byte[] rawData = ais.readAllBytes();
        ByteBuffer data = BufferUtils.createByteBuffer(rawData.length).put(rawData);
        data.flip();

        int alFormat = getOpenALFormat(format.getChannels(), format.getSampleSizeInBits());

        // 生成缓冲区和音源
        buffer = alGenBuffers();
        source = alGenSources();

        alBufferData(buffer, alFormat, data, (int) format.getSampleRate());
        alSourcei(source, AL_BUFFER, buffer);
    }

    public void play() {
        alSourcePlay(source);
    }

    public void stop() {
        alSourceStop(source);
    }

    public void cleanup() {
        alDeleteSources(source);
        alDeleteBuffers(buffer);
    }

    private int getOpenALFormat(int channels, int bits) {
        if (channels == 1) {
            return bits == 8 ? AL_FORMAT_MONO8 : AL_FORMAT_MONO16;
        } else {
            return bits == 8 ? AL_FORMAT_STEREO8 : AL_FORMAT_STEREO16;
        }
    }
}