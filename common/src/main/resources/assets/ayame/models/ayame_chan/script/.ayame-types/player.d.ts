/**
 * 玩家
 */
declare interface Player {
    /**
     * 玩家名称
     */
    name: string;

    /**
     * 播放音效。
     * @param namespace 命名空间
     * @param soundName 音频名称
     * @param volume 音量
     * @param pitch 音调
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
     * 是玩家客户端显示一条消息。
     * @param message 要发送的消息
     */
    sendClientMessage(message: string): void;

    /**
     * 设置模型属性。
     */
    setYttribume(attribute: Yttribume, valve: number): void;

    /**
     * 获取模型属性的值。
     */
    getYttribumeValve(attribute: Yttribume): number;

    /**
     * 手动播放动画。
     */
    playAnim(animationName: String, isLoop: boolean)
}
