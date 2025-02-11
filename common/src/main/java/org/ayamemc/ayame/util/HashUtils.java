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

package org.ayamemc.ayame.util;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Locale;

public class HashUtils {
    private static final ThreadLocal<MessageDigest> MESSAGE_DIGEST_THREAD_LOCAL = ThreadLocal.withInitial(() -> {
        try {
            return MessageDigest.getInstance("sha-256");
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    });

    public static @NotNull String hashOf(byte[] data) {
        final byte[] digest = MESSAGE_DIGEST_THREAD_LOCAL.get().digest(data);

        return bytesToHexString(digest);
    }

    public static @NotNull String lowercaseHashOf(byte[] data) {
        final byte[] digest = MESSAGE_DIGEST_THREAD_LOCAL.get().digest(data);

        return bytesToHexStringLowercase(digest);
    }

    public static @NotNull String bytesToHexStringLowercase(byte[] bytes) {
        return bytesToHexString(bytes).toLowerCase(Locale.ROOT);
    }

    @Contract("_ -> new")
    public static @NotNull String bytesToHexString(byte @NotNull [] bytes) {
        final char[] hexChars = new char[bytes.length * 2];

        for (int i = 0; i < bytes.length; i++) {
            int v = bytes[i] & 0xFF;
            hexChars[i * 2] = Character.forDigit(v >>> 4, 16);
            hexChars[i * 2 + 1] = Character.forDigit(v & 0x0F, 16);
        }

        return new String(hexChars);
    }
}
