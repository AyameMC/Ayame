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

package org.ayamemc.ayame.client.api;

import org.ayamemc.ayame.client.AyameClientEvents;
import org.ayamemc.ayame.client.resource.ModelResource;
import org.ayamemc.ayame.client.resource.ModelResourceCache;

import java.util.List;

/**
 * 模型资源API
 */
public class ModelResourceAPI {
    /**
     * 获取所有模型
     * @param sorted 是否排序
     * @return 模型列表
     */
    public static List<ModelResource> listModels(boolean sorted){
        // 从缓存中获取
        List<ModelResource> modelResources = ModelResourceCache.getAllModelResource(sorted);
        // 处理事件
        AyameClientEvents.Instance.INSTANCE.ModelResource_onListResource(modelResources, sorted);
        return modelResources;
    }
}
