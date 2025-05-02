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

package org.ayamemc.ayame.client.script;

import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import org.mozilla.javascript.annotations.JSFunction;
import org.mozilla.javascript.annotations.JSGetter;

import java.util.Objects;

public class JsEntity {
    public final Entity entity;

    public JsEntity(Entity entity) {
        this.entity = entity;
    }

    public Entity getEntity() {
        return entity;
    }

    @JSFunction
    public JsWorld getWorld() {
        Level world = entity.level(); // 获取所在的Level对象
        return new JsWorld(world);    // 返回JsWorld实例
    }

    @JSFunction
    public void sendClientMessage(String message) {
        entity.sendSystemMessage(Component.literal(message));
    }

    @JSGetter
    public String getType() {
        return BuiltInRegistries.ENTITY_TYPE.getKey(entity.getType()).toString();
    }

    @JSGetter
    public String getLocalizedName() {
        return entity.getName().getString();
    }

    @JSGetter
    public double getX() {
        return entity.getX();
    }
    @JSGetter
    public double getY() {
        return entity.getY();
    }
    @JSGetter
    public double getZ() {
        return entity.getZ();
    }

    @JSFunction
    public void spawnParticle(String id, float xSpeed, float ySpeed, float zSpeed) {
        entity.level().addParticle(()-> Objects.requireNonNull(BuiltInRegistries.PARTICLE_TYPE.get(ResourceLocation.parse(id))), getX(), getY(), getZ(), xSpeed, ySpeed, zSpeed);
    }
}
