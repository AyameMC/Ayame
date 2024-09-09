/*
 *      Custom player model mod. Powered by GeckoLib.
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

package org.ayamemc.ayame.client;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import org.ayamemc.ayame.client.resource.IModelResource;
import org.jetbrains.annotations.ApiStatus;

import java.util.List;

///
/// 内部事件，实现见 `AyameClientEventsFabricImpl` 与 `AyameClientEventsNeoForgeImpl`
///
@ApiStatus.Internal
@Environment(EnvType.CLIENT)
public interface IAyameClientEvents {
    void ModelResource_onResourceCreate(IModelResource modelResource);

    void ModelResource_onListResource(List<IModelResource> modelResources, boolean sorted);

    class Instance {
        public static IAyameClientEvents INSTANCE;
    }
}
