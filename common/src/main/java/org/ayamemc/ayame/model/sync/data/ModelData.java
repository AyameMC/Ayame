package org.ayamemc.ayame.model.sync.data;

import net.minecraft.resources.ResourceLocation;
import org.ayamemc.ayame.model.AyameModelData;

import java.util.Objects;

/**
 * 统一模型数据结构
 */
public record ModelData(ResourceLocation geoModel, ResourceLocation animation, ResourceLocation texture,
                        ResourceLocation arm, String id, AyameModelData.ScriptData scriptData, float scale) {
    /**
     *
     */
    public ModelData {
    }

    @Override
    public String toString() {
        return "ModelData[" +
                "geoModel=" + geoModel + ", " +
                "animation=" + animation + ", " +
                "texture=" + texture + ", " +
                "arm=" + arm + ", " +
                "id=" + id + ", " +
                "scriptData=" + scriptData + ", " +
                "scale=" + scale + ']';
    }

}

