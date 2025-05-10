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

// 做个卖萌轮盘，根据情绪系统改变，做全局变量

let shakeValue = 0;



// 注册自定义 Molang 函数
Molang.registerFunction('math.sinp2', (x: number) => {
    const molangSin = Molang.exec(`math.sin(${x})`) as number;
    return molangSin + Math.sin(x * (Math.PI / 180));
});

PlayerEvents.tick((event) => {
    const headRRotation = Molang.exec("query.head_x_rotation") as number;
    // logger.info(headRRotation)
    const { player } = event;
    let scale: Yttribume = yttribume.get('ayame:model.scale') // Yttribume对象
    let value = player.getYttribumeValve(scale);
    // player.setYttribume(scale, 2);
})

PlayerEvents.keyPress((event) => {

    const { player, key } = event;
    if (key === 0) {
        // 增大scale
        let scale: Yttribume = yttribume.get('ayame:model.scale') // Yttribume对象
        let value: number = player.getYttribumeValve(scale) // 属性值
        player.setYttribume(scale, value + 0.1)
    }
    if (key === 1) {
        // 减小alpha
        let alpha = yttribume.get('ayame:model.alpha')
        let value = player.getYttribumeValve(alpha)
        player.setYttribume(alpha, value - 0.01)
    }
    if (key === 2) {
        // 抖起来
        let shake = yttribume.get('ayame:global.screen.shake')
        let value = player.getYttribumeValve(shake)
        player.setYttribume(shake, value + 0.01)
    }
    if (key == 3) {
        // 播放猫叫
        player.playSound('minecraft:entity.cat.ambient', 1.0, 1.0);
    }
})

PlayerEvents.attackEntity((event) => {
    const { player, target } = event
    const type = target.getType();
    logger.info(shakeValue.toString());

    let shake = yttribume.get('ayame:global.screen.shake');
    shakeValue = shakeValue + 1;
    player.setYttribume(shake, shakeValue);
    if (shakeValue > 3) {
        player.sendClientMessage(`§d晕晕晕...`)
    } else {
        player.sendClientMessage(`§a晕晕晕aaa...`)
    }


})

RouletteOption.add("awsl", "minecraft:textures/block/tnt_side.png", (event) => {
    const { player } = event
    player.sendClientMessage("aswl！");
    player.playSound('minecraft:entity.panda.hurt', 1.0, 1.0);
    player.playAnim("special.death", false)
});

RouletteOption.add("坐下", "minecraft:textures/item/diamond.png", (event) => {
    const { player } = event
    player.sendChatMessage("坐下")
    player.playSound('ayame:models/ayame_chan/zufolo_impazzito.ogg', 1.0, 1.0);
    player.playAnim("state.sit", true)
});

RouletteOption.add("抱头蹲防", "minecraft:textures/item/diamond.png", (event) => {
    const { player } = event
    // player.playSound('ayame:models/ayame_chan/zufolo_impazzito.ogg', 1.0, 1.0);
    player.spawnParticle("minecraft:landing_obsidian_tear", player.getX() + 0.5, player.getY() + 0.5, player.getZ() + 0.5, 0.0, 0.0, 0.0)
    player.playAnim("抱头蹲防", true)
});



