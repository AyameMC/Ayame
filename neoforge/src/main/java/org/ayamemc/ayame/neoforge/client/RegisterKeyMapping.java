package org.ayamemc.ayame.neoforge.client;

import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.KeyMapping;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RegisterKeyMappingsEvent;
import net.neoforged.neoforge.client.settings.KeyConflictContext;
import net.neoforged.neoforge.client.settings.KeyModifier;
import net.neoforged.neoforge.common.util.Lazy;
import org.ayamemc.ayame.Ayame;
import org.ayamemc.ayame.util.TranslatableName;
import org.lwjgl.glfw.GLFW;

@EventBusSubscriber(modid = Ayame.MOD_ID, bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class RegisterKeyMapping {
    public static final Lazy<KeyMapping> MODEL_SELECT_MENU = Lazy.of(() -> new KeyMapping(
            "key." + Ayame.MOD_ID + "." + TranslatableName.SELECT_MODEL_MENU,
            KeyConflictContext.IN_GAME,
            KeyModifier.ALT,
            InputConstants.Type.KEYSYM,
            GLFW.GLFW_KEY_Y,
            TranslatableName.MOD_KEY_MENU_NAME
    ));
    @SubscribeEvent
    public static void onKeyPressed(RegisterKeyMappingsEvent event) {
        event.register(MODEL_SELECT_MENU.get());
    }
}
