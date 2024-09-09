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

package org.ayamemc.ayame.client.resource;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * 客户端模型资源的缓存
 */
public class ModelResourceCache {
    /** 使用ConcurrentHashMap来保证线程安全
        String 为模型名， ModelResource 为模型资源
     */
    public static Map<String, IModelResource> cache = new ConcurrentHashMap<>();
    /**
     * 用来保证插入顺序的List
     */
    public static List<String> sortedCache = new CopyOnWriteArrayList<>();
    /**
     * 添加模型资源到缓存，最好使用{@link #addModelResource(IModelResource)}这个方法
     * @param name 模型名称
     * @param modelRes 模型资源
     */
    public static void addModelResource(String name, IModelResource modelRes) {
        cache.put(name,modelRes);
        sortedCache.add(name);
    }

    /**
     * 添加模型资源到缓存
     * @param modelRes 模型资源
     */
    public static void addModelResource(IModelResource modelRes) {
        addModelResource(modelRes.getMetaData().name(),modelRes);
    }

    /**
     * 批量添加模型资源到缓存
     * @param modelRes 模型资源
     */
    public static void addModelResource(Collection<IModelResource> modelRes){
        modelRes.forEach(ModelResourceCache::addModelResource);
    }

    /**
     * 批量添加模型资源到缓存
     * @param modelRes 模型资源
     */
    public static void addModelResource(Map<String, IModelResource> modelRes){
        modelRes.forEach(ModelResourceCache::addModelResource);
    }

    /**
     * 获取模型资源
     * @param name 模型名称
     * @return 模型资源
     */
    public static IModelResource getModelResource(String name) {
        return cache.get(name);
    }


    /**
     * 获取所有模型资源
     * @param sorted 是否要保证插入顺序
     * @return 所有模型资源
     */
    public static List<IModelResource> getAllModelResource(boolean sorted){
        return sorted ? sortedCache.stream().map(ModelResourceCache::getModelResource).toList() : cache.values().stream().toList();
    }


    /**
     * 清空缓存
     */
    public static void clearCache(){
        cache.clear();
        sortedCache.clear();
    }
}
