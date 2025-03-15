"use strict";
function _main() {
    logger.info("Version: ".concat(Ayame.version));
}
_main();
PlayerTickEvent.register(function (event) {
    var player = event.player;
    player.sendChatMessage('a');
});
KeyPressEvent.register(function (event) {
    var key = event.key, player = event.player;
    if (key === 0) {
        var scale = yttribume.get('ayame', 'model.scale');
        var value = player.getYttribumeValve(scale);
        player.setYttribume(scale, value + 0.1);
    }
    if (key === 1) {
        var alpha = yttribume.get('ayame', 'model.alpha');
        var value = player.getYttribumeValve(alpha);
        player.setYttribume(alpha, value - 0.01);
    }
    if (key === 2) {
        var shake = yttribume.get('ayame', 'global.screen.shake');
        var value = player.getYttribumeValve(shake);
        player.setYttribume(shake, value + 0.01);
    }
    if (key == 3) {
        player.playSound('minecraft', 'entity.cat.ambient', 1.0, 1.0);
    }
});
AttackEntityEvent.register(function (event) {
    var player = event.player, target = event.target;
    var name = target.getName();
    logger.info("\u4F60\u653B\u51FB\u4E86\u4E0B".concat(name));
});
