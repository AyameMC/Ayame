package org.ayamemc.ayame.fabric.client.msic;

import com.mojang.blaze3d.platform.InputConstants;
import dev.kingtux.tms.api.modifiers.BindingModifiers;
import dev.kingtux.tms.api.modifiers.KeyModifier;
import dev.kingtux.tms.keybinding.ToggleAutoJumpKeyBinding;
import dev.kingtux.tms.api.KeyBindingUtils;
import net.minecraft.client.KeyMapping;
import dev.kingtux.tms.api.TMSKeyBinding;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

import static org.ayamemc.ayame.Ayame.MOD_ID;

public class AyameTMSKeyMappings {

    public static KeyMapping registerTMSKeyMapping(String name, @NotNull InputConstants.Type type, int keyCode, String category, @NotNull String modifier){
        BindingModifiers bindingModifiers = null;
        if (modifier.equalsIgnoreCase("alt")){
             bindingModifiers = new BindingModifiers();
             bindingModifiers.set(KeyModifier.ALT,true);
        }else if (modifier.equalsIgnoreCase("shift")){
            bindingModifiers = new BindingModifiers();
            bindingModifiers.set(KeyModifier.SHIFT,true);
        }else if (modifier.equalsIgnoreCase("ctrl")){
            bindingModifiers = new BindingModifiers();
            bindingModifiers.set(KeyModifier.CONTROL,true);
        }
        if (bindingModifiers!=null) {
            return new TMSKeyBinding(ResourceLocation.fromNamespaceAndPath(MOD_ID, name), type, keyCode, category, bindingModifiers);
        }else{
            throw new RuntimeException("Modifier only can be alt, shift or ctrl");
        }
    }
}
