/*
 *      This file is part of Ayame.
 *
 *     Ayame is free software: you can redistribute it and/or modify it under the terms of the GNU Lesser General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.
 *
 *     Ayame is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more details.
 *
 *     You should have received a copy of the GNU Lesser General Public License along with Ayame. If not, see <https://www.gnu.org/licenses/>.
 */

package org.ayamemc.ayame.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import org.ayamemc.ayame.client.model.DefaultModels;
import org.ayamemc.ayame.model.AyameModel;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

import static org.ayamemc.ayame.Ayame.MOD_ID;

public class GeoPlayerRender extends GeoEntityRenderer<Player> {
    private final GeoPlayerModel pModel;
    // TODO : 完善代码 & 添加API
    public GeoPlayerRender(EntityRendererProvider.Context context,GeoPlayerModel model) {
        super(context, model);
        this.pModel = model;
    }


    @Override
    public GeoPlayerModel getGeoModel() {
        return this.pModel;
    }

//    @Override
//    public void render(Player entity, float f, float g, PoseStack poseStack, MultiBufferSource multiBufferSource, int i) {
//        super.render(entity, f, g, poseStack, multiBufferSource, i);
//    }

    // TODO : 添加API
    public static class GeoPlayerModel extends GeoModel<Player> {
        public ResourceLocation geoModel;
        public ResourceLocation texture;
        public ResourceLocation animation;
        public void switchModel(AyameModel model) {
            this.geoModel = model.getGeoModel();
            this.texture = model.getTexture();
            this.animation = model.getAnimation();
        }

        public GeoPlayerModel(AyameModel model) {
            switchModel(model);
        }

        @Override
        public ResourceLocation getModelResource(Player animatable) {
            return geoModel;
        }

        @Override
        public ResourceLocation getTextureResource(Player animatable) {
            return texture;
        }

        @Override
        public ResourceLocation getAnimationResource(Player animatable) {
            return animation;
        }

    }
}