package org.ayamemc.ayame.neoforge.client;

import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.KeyMapping;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RegisterKeyMappingsEvent;
import net.neoforged.neoforge.common.util.Lazy;
import org.ayamemc.ayame.Ayame;
import org.lwjgl.glfw.GLFW;

import java.awt.event.KeyEvent;

@EventBusSubscriber(modid = Ayame.MOD_ID, bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class RegisterKeyMapping {
    public static final Lazy<KeyMapping> MODEL_SELECT_MENU = Lazy.of(() -> new KeyMapping(
            "key.ayame.model_select_menu", // Will be localized using this translation key
            InputConstants.Type.KEYSYM, // Default mapping is on the keyboard
            GLFW.GLFW_KEY_Y, // Default key is P
            Ayame.MOD_KEY_MENU_NAME // Mapping will be in the misc category
    ));
    @SubscribeEvent
    public static void onKeyPressed(RegisterKeyMappingsEvent event) {
        event.register(MODEL_SELECT_MENU.get());
    }
}
