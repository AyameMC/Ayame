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

package org.ayamemc.ayame.neoforge.client.api.event;

import net.neoforged.bus.api.Event;
import org.ayamemc.ayame.client.api.ModelResourceAPI;
import org.ayamemc.ayame.client.resource.IModelResource;

import java.util.List;
import java.util.Map;

public class ModelResourceEvents {
    /**
     * 模型资源创建时的回调<br></br>
     * 参数：<br></br>
     * modelResource：模型资源
     *
     * @see IModelResource#ModelResource(Map)
     */
    public static class OnResourceCreate extends Event {
        private final IModelResource modelResource;

        public OnResourceCreate(IModelResource modelResource) {
            this.modelResource = modelResource;
        }

        public IModelResource getModelResource() {
            return modelResource;
        }
    }

    /**
     * 获取模型列表时的回调<br></br>
     * 参数：<br></br>
     * modelResources：模型列表<br></br>
     * sorted：是否已排序
     *
     * @see ModelResourceAPI#listModels(boolean)
     */
    public static class OnListResource extends Event {
        private final List<IModelResource> modelResources;
        private final boolean sorted;

        public OnListResource(List<IModelResource> modelResources, boolean sorted) {
            this.modelResources = modelResources;
            this.sorted = sorted;
        }

        public List<IModelResource> getModelResources() {
            return modelResources;
        }

        public boolean isSorted() {
            return sorted;
        }
    }
}
