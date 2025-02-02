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

/// <reference path="./types/Ayame.d.ts" />
/// <reference path="./types/Logger.d.ts" />
/// <reference path="./types/ModLoader.d.ts" />

// const Logger = require("./types/Logger.d.ts");
// const Ayame = require("./types/Ayame.d.ts");
// const ModLoader = require("./types/ModLoader.d.ts");


function _main() {
    Logger.info("Version:" + Ayame.VERSION);
    Logger.info("Version {}", Ayame.VERSION);
    switch (Ayame.modLoader) {
        case ModLoader.FABRIC:
            Logger.info("It's Fabric!");
            break;
        case ModLoader.NEOFORGE:
            Logger.info("It's NeoForge!");
            break;
    }

}

// function test(){
//     Logger.info("loop...");
//     test();
// }
// test();
_main()

PlayerEvents.tick((event) => {
    const {level, player} = event;
    player.sendChat("I'm " + player.name);
    if (player.name === "homewool") {
        Logger.debug("羊毛")
    }

});
