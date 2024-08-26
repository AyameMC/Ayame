package org.ayamemc.ayame.fabric.client;

import com.mojang.blaze3d.platform.InputConstants;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.KeyMapping;
import org.ayamemc.ayame.util.TranslatableName;
import org.lwjgl.glfw.GLFW;

public final class AyameFabricClient implements ClientModInitializer {
    private static KeyMapping keyMapping;
    @Override
    public void onInitializeClient() {
        RegisterKeyMapping.registerKeyMapping();
    }
}
