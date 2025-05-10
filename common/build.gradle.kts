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

architectury {
    common(rootProject.property("enabled_platforms").toString().split(','))
}

dependencies {
    // We depend on Fabric Loader here to use the Fabric @Environment annotations,
    // which get remapped to the correct annotations on each platform.
    // Do NOT use other classes from Fabric Loader.
    modImplementation("net.fabricmc:fabric-loader:${rootProject.property("fabric_loader_version")}")

    // GeckoLib
    modImplementation("software.bernie.geckolib:geckolib-fabric-${rootProject.property("minecraft_version")}:${rootProject.property("geckolib_version")}")
    // Ayame PaperDoll
    modImplementation("maven.modrinth:ayame-paperdoll:${rootProject.property("ayame_paperdoll_version")}-fabric")
    // Rhino库
    implementation("org.mozilla:rhino:${rootProject.property("rhino_version")}")
    // Mocha 库
    implementation("team.unnamed:mocha:${rootProject.property("mocha_version")}")

}
loom {
    accessWidenerPath = file("${rootProject.property("mod_access_widener_path")}")
}

