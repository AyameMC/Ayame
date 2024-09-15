/*
 *     Custom player model mod. Powered by GeckoLib.
 *     Copyright (C) 2024  CrystalNeko, HappyRespawnanchor, pertaz(Icon Designer)
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

plugins {
    id("org.jetbrains.kotlin.jvm")
}

architectury {
    common(rootProject.ext["enabled_platforms"].toString().split(","))
}

dependencies {
    // We depend on Fabric Loader here to use the Fabric @Environment annotations,
    // which get remapped to the correct annotations on each platform.
    // Do NOT use other classes from Fabric Loader.
    modImplementation("net.fabricmc:fabric-loader:${rootProject.ext["fabric_loader_version"]}")

    // GeckoLib
    modImplementation("software.bernie.geckolib:geckolib-fabric-${rootProject.ext["minecraft_version"]}:${rootProject.ext["geckolib_version"]}")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
}

// 配置访问加宽器
loom {
    accessWidenerPath.set(file("src/main/resources/ayame.accesswidener"))
}

repositories {
    mavenCentral()
}

kotlin {
    jvmToolchain(21)
}