package org.ayamemc.ayame.fabric.client;

import com.mojang.blaze3d.platform.InputConstants;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.KeyMapping;
import org.ayamemc.ayame.util.TranslatableName;
import org.lwjgl.glfw.GLFW;

public class RegisterKeyMapping {
    public static KeyMapping keyMapping;
    public static void registerKeyMapping() {
        // This entrypoint is suitable for setting up client-specific logic, such as rendering.
        keyMapping = KeyBindingHelper.registerKeyBinding(new KeyMapping(
                TranslatableName.SELECT_MODEL_MENU,
                InputConstants.Type.KEYSYM, // The type of the keybinding, KEYSYM for keyboard, MOUSE for mouse.
                GLFW.GLFW_KEY_Y,// The keycode of the key
                TranslatableName.MOD_KEY_MENU_NAME
        ));
    }
}
