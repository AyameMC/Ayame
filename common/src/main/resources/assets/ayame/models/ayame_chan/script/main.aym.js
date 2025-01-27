// const Ayame = require("./lib/Ayame.d.ts");
// const Logger = require("./lib/Logger.d.ts");
// const PlayerEvents = require("./lib/PlayerEvents.d.ts");
// const ModLoader = require("./lib/ModLoader.d.ts");

function _main() {
    Logger.info("Ayame Version:" + Ayame.VERSION);
    Logger.info("Ayame {}", Ayame.VERSION);
    switch (Ayame.modLoader) {
        case ModLoader.FABRIC:
            Logger.info("It's Fabric!");
            break;
        case ModLoader.NEOFORGE:
            Logger.info("It's NeoForge!");
            break;
    }
}
_main();
PlayerEvents.tick((event) => {
    const { level, player } = event;
    player.sendChat("aaa")
    Logger.fatal("aaaa")
});
