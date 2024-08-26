package org.ayamemc.ayame.fabric.client.msic;

import com.mojang.blaze3d.platform.InputConstants;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.KeyMapping;
import org.ayamemc.ayame.util.JavaUtil;
import org.ayamemc.ayame.util.TranslatableName;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.lwjgl.glfw.GLFW;

import static org.ayamemc.ayame.Ayame.MOD_ID;


public class AyameKeyMappings {
    public static final boolean IS_TMS_INSTALLED = JavaUtil.tryClass("dev.kingtux.tms.api.TMSKeyBinding");
    public static final KeyMapping MODEL_SELECT_MENU = registerKeyMapping(
            TranslatableName.SELECT_MODEL_MENU,
            InputConstants.Type.KEYSYM,
            GLFW.GLFW_KEY_Y,
            TranslatableName.MOD_KEY_MENU_NAME,
            "alt"
    );
    public static KeyMapping registerKeyMapping(String name, @NotNull InputConstants.Type type, int keyCode, String category,@Nullable String modifier){
        KeyMapping keyMapping = null;
        if (IS_TMS_INSTALLED && modifier != null){
            keyMapping = AyameTMSKeyMappings.registerTMSKeyMapping(name, type, keyCode, category, modifier);
        }else {
            keyMapping = new KeyMapping("key."+MOD_ID+"."+name, type, keyCode, category);
        }
        return KeyBindingHelper.registerKeyBinding(keyMapping);
    }

    public static void init(){}

    public static void processKeyPressed() {
        while (AyameKeyMappings.MODEL_SELECT_MENU.consumeClick()){
            // TODO open model select menu
        }
    }
}
