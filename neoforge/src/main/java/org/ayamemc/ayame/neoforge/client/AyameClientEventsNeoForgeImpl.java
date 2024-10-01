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

package org.ayamemc.ayame.neoforge.client;

import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.neoforge.common.NeoForge;
import org.ayamemc.ayame.client.IAyameClientEvents;
import org.ayamemc.ayame.client.resource.IModelResource;
import org.ayamemc.ayame.neoforge.client.api.event.ModelResourceEvents;
import org.jetbrains.annotations.ApiStatus;

import java.util.List;


@OnlyIn(Dist.CLIENT)
@ApiStatus.Internal
public class AyameClientEventsNeoForgeImpl implements IAyameClientEvents {
    @Override
    public void ModelResource_onResourceCreate(IModelResource modelResource) {
        NeoForge.EVENT_BUS.post(new ModelResourceEvents.OnResourceCreate(modelResource));
    }

    @Override
    public void ModelResource_onListResource(List<IModelResource> modelResources, boolean sorted) {
        NeoForge.EVENT_BUS.post(new ModelResourceEvents.OnListResource(modelResources, sorted));
    }
}
