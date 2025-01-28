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
_main()

PlayerEvents.tick((event) => {
    const { level, player } = event;
    player.sendChat("I'm " + player.name);
    if(player.name === "homewool"){
        Logger.debug("羊毛")
    }

});
