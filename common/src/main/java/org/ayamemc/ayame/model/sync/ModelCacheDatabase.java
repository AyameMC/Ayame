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

package org.ayamemc.ayame.model.sync;

import org.jetbrains.annotations.NotNull;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class ModelCacheDatabase {
    private final Path parentFolder;

    private final FileChannel indexFileChannel;
    private final ReadWriteLock databaseLock = new ReentrantReadWriteLock();
    private final DatabaseIndex databaseIndex = new DatabaseIndex();

    public ModelCacheDatabase(Path parentFolder) throws IOException {
        this.parentFolder = parentFolder;
        this.indexFileChannel = FileChannel.open(this.parentFolder.resolve("cache_index.bin"), StandardOpenOption.CREATE, StandardOpenOption.READ, StandardOpenOption.WRITE);
    }

    private class DatabaseIndex {
        private final Set<String> modelCacheHashes = new HashSet<>();
        private final Set<String> modelCacheNames = new HashSet<>();

        private void loadFromEncoded(byte[] data){
            ModelCacheDatabase.this.databaseLock.writeLock().lock();
            try {
                try (
                        ByteArrayInputStream bis = new ByteArrayInputStream(data);
                        DataInputStream dis = new DataInputStream(bis)
                ) {
                    final int hashCount = dis.readInt();
                    for (int i = 0; i < hashCount; i++) {
                        this.modelCacheHashes.add(dis.readUTF());
                    }

                    final int nameCount = dis.readInt();
                    for (int i = 0; i < nameCount; i++) {
                        this.modelCacheNames.add(dis.readUTF());
                    }
                } catch (IOException e) {
                    throw new RuntimeException(e);

                }
            }finally {
                ModelCacheDatabase.this.databaseLock.writeLock().unlock();
            }
        }

        private byte @NotNull [] encode() {
            ModelCacheDatabase.this.databaseLock.readLock().lock();
            try (
                    final ByteArrayOutputStream bos = new ByteArrayOutputStream();
                    final DataOutputStream dos = new DataOutputStream(bos)
            ){
                dos.writeInt(this.modelCacheHashes.size());
                for (String hash : this.modelCacheHashes) {
                    dos.writeUTF(hash);
                }

                dos.writeInt(this.modelCacheNames.size());
                for (String name : this.modelCacheNames) {
                    dos.writeUTF(name);
                }

                return bos.toByteArray();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }finally {
                ModelCacheDatabase.this.databaseLock.readLock().unlock();
            }
        }

        public void loadFromFile() throws IOException {
            final ByteBuffer buffer = ByteBuffer.allocate((int) ModelCacheDatabase.this.indexFileChannel.size());

            ModelCacheDatabase.this.indexFileChannel.read(buffer);

            this.loadFromEncoded(buffer.array());
        }

        public void saveIndex() throws IOException {
            final byte[] encoded = this.encode();

            final ByteBuffer buffer = ByteBuffer.allocate(encoded.length);

            buffer.put(encoded);
            buffer.flip();

            ModelCacheDatabase.this.indexFileChannel.write(buffer);
        }

    }
}
