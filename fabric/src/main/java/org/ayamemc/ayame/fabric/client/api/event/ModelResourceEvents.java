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

package org.ayamemc.ayame.fabric.client.api.event;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import org.ayamemc.ayame.client.resource.IModelResource;
import org.ayamemc.ayame.client.api.ModelResourceAPI;

import java.util.List;
import java.util.Map;

public class ModelResourceEvents {

    /**
     * 模型资源创建时的回调<br></br>
     * 参数：<br></br>
     *    modelResource：模型资源
     * @see IModelResource#ModelResource(Map)
     */
    public static Event<OnResourceCreate> ON_RESOURCE_CREATE = EventFactory.createArrayBacked(OnResourceCreate.class, (listeners) -> (modelResource) ->{
        for (OnResourceCreate listener : listeners) {
            listener.onResourceCreate(modelResource);
        }
    });

    /**
     * 获取模型列表时的回调<br></br>
     * 参数：<br></br>
     *    modelResources：模型列表<br></br>
     *    sorted：是否已排序
     * @see ModelResourceAPI#listModels(boolean)
     */
    public static Event<OnListResource> ON_LIST_RESOURCE = EventFactory.createArrayBacked(OnListResource.class, (listeners) -> (modelResources, sorted) ->{
        for (OnListResource listener : listeners) {
            listener.onListResource(modelResources, sorted);
        }
    });


    public interface OnResourceCreate {
        void onResourceCreate(IModelResource modelResource);
    }
    public interface OnListResource {
        void onListResource(List<IModelResource> modelResources, boolean sorted);
    }
}
