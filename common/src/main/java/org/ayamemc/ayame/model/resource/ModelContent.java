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

package org.ayamemc.ayame.model.resource;

import net.minecraft.server.packs.repository.PackDetector;
import net.minecraft.world.level.validation.DirectoryValidator;
import org.ayamemc.ayame.util.TODO;
import org.jetbrains.annotations.Nullable;

import java.io.IOException;
import java.nio.file.Path;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

public class ModelContent extends PackDetector<ModelResourceRegistry.ModelFile> {

    public ModelContent(DirectoryValidator validator) {
        super(validator);
    }

    public static ModelContent create() {
        return new ModelContent(new DirectoryValidator(path1 -> true));
    }

    @Nullable
    @Override
    public ModelResourceRegistry.ModelFile createZipPack(Path path) {
        ZipFile zipFile = null;
        try {
            zipFile = new ZipFile(path.toFile());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        ZipEntry entry = zipFile.getEntry("index.json");
        if (entry != null) {
            return new ModelResourceRegistry.ModelFile(zipFile);
        }
        return null;
    }

    @Override
    protected ModelResourceRegistry.ModelFile createDirectoryPack(Path path) {
        throw new TODO("createDirectoryPack");
//        Path indexFilePath = path.resolve("index.json");
//        if (Files.exists(indexFilePath)) {
//            return new ModelResourceRegistry.ModelFile(null);
//        }
//        return null;
    }
}
