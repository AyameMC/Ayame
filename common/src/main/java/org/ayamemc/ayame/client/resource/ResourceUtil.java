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

package org.ayamemc.ayame.client.resource;

import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.util.GsonHelper;
import org.ayamemc.ayame.util.JsonInterpreter;
import software.bernie.geckolib.cache.GeckoLibCache;
import software.bernie.geckolib.cache.object.BakedGeoModel;
import software.bernie.geckolib.loading.json.raw.Model;
import software.bernie.geckolib.loading.json.typeadapter.KeyFramesAdapter;
import software.bernie.geckolib.loading.object.BakedAnimations;
import software.bernie.geckolib.loading.object.BakedModelFactory;
import software.bernie.geckolib.loading.object.GeometryTree;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.function.BiConsumer;
import java.util.function.Function;

import static org.ayamemc.ayame.Ayame.LOGGER;

@Environment(EnvType.CLIENT)
public class ResourceUtil {
    /**
     * 写入资源
     *
     * @param loc     资源位置
     * @param content 内容
     */
    public static void writeResource(ResourceLocation loc, String content) {
        // 获取ResourceManager
        ResourceManager resourceManager = Minecraft.getInstance().getResourceManager();
        // TODO 动态加载资源


//        try {
//            BakedAnimations animations = KeyFramesAdapter.GEO_GSON.fromJson(GsonHelper.getAsJsonObject(JsonInterpreter.fromFile(Path.of("config/ayame/custom/retest/animations.json")).toGson(), "animations"), BakedAnimations.class);
//            GeckoLibCache.getBakedAnimations().put(ResourceLocation.fromNamespaceAndPath(loc.getNamespace(), "animations/retest.json"), animations);
//
//            Model model = KeyFramesAdapter.GEO_GSON.fromJson(JsonInterpreter.fromFile(Path.of("config/ayame/custom/retest/model.json")).toGson(),Model.class);
//            BakedGeoModel bakedModel = BakedModelFactory.getForNamespace(loc.getNamespace()).constructGeoModel(GeometryTree.fromModel(model));
//            GeckoLibCache.getBakedModels().put(loc.withPath("geo/ayame/retest.json"), bakedModel);
//        }catch (Exception e){
//            LOGGER.error("Error reading resource: {}", loc, e);
//        }



//        try {
//            System.out.println(convertInputStreamToString(resourceManager.open(loc)));
//        } catch (IOException e) {
//            LOGGER.error("Error reading resource: " + loc, e);
//        }
//
//        resourceManager.getResourceStack(loc).forEach( (r) -> {
//            try {
//                System.out.println(convertInputStreamToString(r.open()));
//            } catch (IOException e) {
//                LOGGER.error("Error reading resource: " + loc, e);
//            }
//        });

//        resourceManager.listResources("assets/ayame/geo/ayame", (path) -> path.getPath().endsWith(".json")).forEach((rl,r)->{
//            LOGGER.info(rl.toString());
//            try {
//                LOGGER.info(convertInputStreamToString(r.open()));
//            } catch (IOException e) {
//                LOGGER.error("Error reading resource: " + loc, e);
//            }
//        });



    }



    private static <T> CompletableFuture<Void> loadResources(Executor executor, ResourceManager resourceManager,
                                                             String type, Function<ResourceLocation, T> loader, BiConsumer<ResourceLocation, T> map) {
        return CompletableFuture.supplyAsync(
                        () -> resourceManager.listResources(type, fileName -> fileName.toString().endsWith(".json")), executor)
                .thenApplyAsync(resources -> {
                    Map<ResourceLocation, CompletableFuture<T>> tasks = new Object2ObjectOpenHashMap<>();

                    for (ResourceLocation resource : resources.keySet()) {
                        tasks.put(resource, CompletableFuture.supplyAsync(() -> loader.apply(resource), executor));
                    }

                    return tasks;
                }, executor)
                .thenAcceptAsync(tasks -> {
                    for (Map.Entry<ResourceLocation, CompletableFuture<T>> entry : tasks.entrySet()) {
                        // Skip known namespaces that use an "animation" or "geo" folder as well
                        map.accept(entry.getKey(), entry.getValue().join());
                    }
                }, executor);
    }

    public static String convertInputStreamToString(InputStream inputStream) throws IOException {
        StringBuilder resultStringBuilder = new StringBuilder();
        try (BufferedReader br = new BufferedReader(new InputStreamReader(inputStream))) {
            String line;
            while ((line = br.readLine()) != null) {
                resultStringBuilder.append(line).append("\n");
            }
        }
        return resultStringBuilder.toString();
    }

}
