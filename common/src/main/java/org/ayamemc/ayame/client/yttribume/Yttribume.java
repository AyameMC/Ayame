/*
 *     Custom player model mod. Powered by GeckoLib.
 *     Copyright (C) 2024-2025  CrystalNeko, HappyRespawnanchor, pertaz(Icon Designer)
 *
 *     This file is part of Ayame.
 *
 *     Ayame is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU Lesser General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     Ayame is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU Lesser General Public License for more details.
 *
 *     You should have received a copy of the GNU Lesser General Public License
 *     along with Ayame.  If not, see <https://www.gnu.org/licenses/>.
 */

package org.ayamemc.ayame.client.yttribume;

public class Yttribume {
    private final float defaultValue;
    private final float min;
    private final float max;
    private final AttributeType type;
    private final boolean hideInGui;

    public Yttribume(float defaultValue, float min, float max, AttributeType type, boolean hideInGui) {
        this.defaultValue = defaultValue;
        this.min = min;
        this.max = max;
        this.type = type;
        this.hideInGui = hideInGui;
    }

    public float defaultValue() {
        return defaultValue;
    }
    public float min() {
        return min;
    }
    public float max() {
        return max;
    }
    public AttributeType type() {
        return type;
    }
    public boolean hideInGui() {
        return hideInGui;
    }
    //判断是否在范围内
    public boolean isRegal(float value,IYttribumable yttribumable){
        if (yttribumable.ayame$isRestricted()){
            return value >= min() && value <= max();
        }
        return true;
    }


    public enum AttributeType {
        MODEL, // 只对自己的模型生效
        GLOBAL, // 全局生效
    }
}
