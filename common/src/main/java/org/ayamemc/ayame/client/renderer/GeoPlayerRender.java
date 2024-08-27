/*
 *      This file is part of Ayame.
 *
 *     Ayame is free software: you can redistribute it and/or modify it under the terms of the GNU Lesser General Public License as published by the Free Software Foundation, either version 3 of the License, or any later version.
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
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

import static org.ayamemc.ayame.Ayame.MOD_ID;

public class GeoPlayerRender extends GeoEntityRenderer<Player> {
    // TODO : 完善代码 & 添加API
    public GeoPlayerRender(EntityRendererProvider.Context renderManager) {
        super(renderManager, new GeoPlayerModel());
    }

    @Override
    public void render(Player entity, float f, float g, PoseStack poseStack, MultiBufferSource multiBufferSource, int i) {
        super.render(entity, f, g, poseStack, multiBufferSource, i);
    }

    // TODO : 添加API
    public static class GeoPlayerModel extends GeoModel<Player> {
        @Override
        public ResourceLocation getModelResource(Player animatable) {
            // TODO 替换test
            return ResourceLocation.fromNamespaceAndPath(MOD_ID, "geo/test.geo.json");
        }

        @Override
        public ResourceLocation getTextureResource(Player animatable) {
            return ResourceLocation.fromNamespaceAndPath(MOD_ID, "textures/test.png");
        }

        @Override
        public ResourceLocation getAnimationResource(Player animatable) {
            return ResourceLocation.fromNamespaceAndPath(MOD_ID, "animations/test.animation.json");
        }

    }
}