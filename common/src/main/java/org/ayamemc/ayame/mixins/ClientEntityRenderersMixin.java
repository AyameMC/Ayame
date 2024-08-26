package org.ayamemc.ayame.mixins;

import com.google.common.collect.ImmutableMap;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraft.client.resources.PlayerSkin;
import net.minecraft.world.entity.player.Player;
import org.ayamemc.ayame.client.renderer.GeoPlayerRender;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Map;

@Mixin(EntityRenderers.class)
public class ClientEntityRenderersMixin {
    @Inject(method = "createPlayerRenderers", at = @At("RETURN"), cancellable = true)
    private static void createPlayerRenderers(EntityRendererProvider.Context context, CallbackInfoReturnable<Map<PlayerSkin.Model, EntityRenderer<? extends Player>>> cir) {
        Map<PlayerSkin.Model, EntityRenderer<? extends Player>> m = new java.util.HashMap<>(Map.of(PlayerSkin.Model.WIDE, new GeoPlayerRender(context)));
        m.put(PlayerSkin.Model.SLIM, new GeoPlayerRender(context));
        cir.setReturnValue(ImmutableMap.copyOf(m));
    }
}