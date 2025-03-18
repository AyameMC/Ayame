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


function _main() {
    logger.info(`Version: ${Ayame.version}`);

}

_main()


PlayerTickEvent.register((event) => {
    const { player } = event;
    let scale: Yttribume = yttribume.get('ayame', 'model.scale') // Yttribume对象
    let value = player.getYttribumeValve(scale);
    player.setYttribume(scale, 2);
})

KeyPressEvent.register((event) => {

    const { key, player } = event;
    player.sendChatMessage("aa")
    if (key === 0) {
        // 增大scale
        let scale: Yttribume = yttribume.get('ayame', 'model.scale') // Yttribume对象
        let value: number = player.getYttribumeValve(scale) // 属性值
        player.setYttribume(scale, value + 0.1)
    }
    if (key === 1) {
        // 减小alpha
        let alpha = yttribume.get('ayame', 'model.alpha')
        let value = player.getYttribumeValve(alpha)
        player.setYttribume(alpha, value - 0.01)
    }
    if (key === 2) {
        // 抖起来
        let shake = yttribume.get('ayame', 'global.screen.shake')
        let value = player.getYttribumeValve(shake)
        player.setYttribume(shake, value + 0.01)
    }
    if (key == 3) {
        // 播放猫叫
        player.playSound('minecraft', 'entity.cat.ambient', 1.0, 1.0)
    }
})

AttackEntityEvent.register((event) => {
    const { player, target } = event
    let name = target.getName()
    player.sendChatMessage(`你攻击了下${name}`)
})
