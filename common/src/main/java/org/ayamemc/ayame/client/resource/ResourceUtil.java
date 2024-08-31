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

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.PackResources;
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

import static org.ayamemc.ayame.Ayame.LOGGER;
import static org.ayamemc.ayame.Ayame.MOD_ID;

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

        try {

            BakedAnimations ani = KeyFramesAdapter.GEO_GSON.fromJson(GsonHelper.getAsJsonObject(JsonInterpreter.fromFile(Path.of("file/to/path")).toGson(), "animations"), BakedAnimations.class);
            GeckoLibCache.getBakedAnimations().put(ResourceLocation.fromNamespaceAndPath(MOD_ID, "animations/ayame/ani.json"), ani);

            Model model = KeyFramesAdapter.GEO_GSON.fromJson(JsonInterpreter.fromFile(Path.of("path/to/file")).toGson(), Model.class);
            BakedGeoModel bakedGeoModel = BakedModelFactory.getForNamespace("ayame").constructGeoModel(GeometryTree.fromModel(model));
            GeckoLibCache.getBakedModels().put(ResourceLocation.fromNamespaceAndPath(MOD_ID, "geo/ayame/ayame.json"), bakedGeoModel);

        }catch (IOException e){
            LOGGER.error("Error reading resource: " + loc, e);
        }

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
