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

package org.ayamemc.ayame;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import org.ayamemc.ayame.model.sync.FolderBasedModelLoader;
import org.ayamemc.ayame.model.sync.IModelLoader;
import org.ayamemc.ayame.model.sync.data.ModelDataComponent;

import java.nio.file.Path;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

public class Constants {
    private static final Path BASE_DIR = Path.of("config/ayame");

    public static final Path CACHE_DIR = BASE_DIR.resolve("cache");
    public static final Path CACHE_DIR_SERVER = CACHE_DIR.resolve("server");
    public static final Path CACHE_DIR_CLIENT = CACHE_DIR.resolve("client");

    public static final Path MODELS_DIR = BASE_DIR.resolve("models");

    // Our default models
    @SuppressWarnings("SpellCheckingInspection")
    public static final Map<String, DefaultModelInfo> DEFAULT_MODELS = Map.of(
            "ayame_chan", new DefaultModelInfo(
                    "assets/ayame/models/ayame_chan/",
                    List.of(
                            "models/ayame_chan/default/animation.json",
                            "models/ayame_chan/default/arm.json",
                            "models/ayame_chan/default/model.json",
                            "models/ayame_chan/default/texture.png",
                            "models/ayame_chan/script/.ayame-types/ayame.d.ts",
                            "models/ayame_chan/script/.ayame-types/entity.d.ts",
                            "models/ayame_chan/script/.ayame-types/events.d.ts",
                            "models/ayame_chan/script/.ayame-types/logger.d.ts",
                            "models/ayame_chan/script/.ayame-types/player.d.ts",
                            "models/ayame_chan/script/.ayame-types/yttribume.d.ts",
                            "models/ayame_chan/script/.ayame-types/molang.d.ts",
                            "models/ayame_chan/script/main.aym.ts",
                            "models/ayame_chan/script/tsconfig.json",
                            "models/ayame_chan/ayame.json",
                            "models/ayame_chan/zufolo_impazzito.ogg"
                    )
            )
    );


    public static Consumer<ModelDataComponent> DEFAULT_MODEL_DATA_MODIFIER = data -> {
        data.setCanUnload(false);
        data.setLoadAsDefaultModel(true);
    };

    public static List<IModelLoader> DEFAULT_MODEL_LOADERS = List.of(new FolderBasedModelLoader());

    public record DefaultModelInfo(String prefix, List<String> files) {
    }

}
