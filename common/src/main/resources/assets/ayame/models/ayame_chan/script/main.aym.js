"use strict";
var A = (function () {
    function A() {
    }
    return A;
}());
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
_main();
PlayerTickEvent.register(function (event) {
    var player = event.player;
});
KeyPressEvent.register(function (key, player) {
    if (key == 0) {
        var scale = Yttribume.get("ayame", "model.scale");
        var value = player.getYttribume(scale);
        player.setYttribume(scale, value + 0.1);
    }
    if (key == 1) {
        var alpha = Yttribume.get("ayame", "model.alpha");
        var value = player.getYttribume(alpha);
        player.setYttribume(alpha, value - 0.01);
    }
    if (key == 2) {
        var shake = Yttribume.get("ayame", "global.screen.shake");
        var value = player.getYttribume(shake);
        player.setYttribume(shake, value + 0.01);
    }
    if (key == 3) {
        player.playSound("minecraft", "entity.cat.ambient", 1.0, 1.0);
    }
});
AttackEntityEvent.register(function (event) {
    var player = event.player, world = event.world, target = event.target;
    var name = target.getName();
    Logger.info("\u4F60\u653B\u51FB\u4E86\u4E0B".concat(name));
});
