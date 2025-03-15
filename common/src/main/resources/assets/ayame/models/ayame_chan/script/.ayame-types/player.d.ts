/**
 * 玩家
 */
declare interface Player {
    /**
     * 玩家名称
     */
    name: string;

    /**
     * 播放音效
     */
    playSound(namespace: string, soundName: string, volume: number, pitch: number): void;

    /**
     * 使玩家向服务器发送一条聊天消息。
     * @param message 要发送的消息
     */
    sendChatMessage(message: string): void;

    /**
     * 使玩家向服务器发送一条命令。
     * @param command 要发送的命令
     */
    sendCommand(command: string): void;

    /**
     * 设置模型属性。
     */
    setYttribume(attribute: Yttribume, valve: number): void;

    /**
     * 获取模型属性的值。
     */
    getYttribumeValve(attribute: Yttribume): number;
}
