/*
 *      Custom player model mod. Based on GeckoLib.
 *      Copyright (C) 2024  CrystalNeko, HappyRespawnanchor, pertaz(Icon Desiger)
 *
 *      This file is part of Ayame.
 *
 *     Ayame is free software: you can redistribute it and/or modify it under the terms of the GNU Lesser General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.
 *
 *     Ayame is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more details.
 *
 *     You should have received a copy of the GNU Lesser General Public License along with Ayame. If not, see <https://www.gnu.org/licenses/>.
 */

package org.ayamemc.ayame.client.model;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import org.ayamemc.ayame.model.DefaultAyameModel;
import org.ayamemc.ayame.model.ModelMetaData;

@Environment(EnvType.CLIENT)
public class DefaultModels {
    public static final DefaultAyameModel TEST_MODEL = new DefaultAyameModel(ModelMetaData.Builder.create()
            .setVersion("0.0.1")
            .setAuthors(new String[0])
            .setLicense("cc0")
            .setName("test")
            .setAnimations(new String[0])
            .setLinks(new String[]{"https://github.com/CSneko/toNeko/blob/main/models/grmmy_neko.bbmodel"})
            .setDescription("From toNeko, just for example and test")
            .setTags(new String[]{"test", "vanilla-like"})
            .build());

}
