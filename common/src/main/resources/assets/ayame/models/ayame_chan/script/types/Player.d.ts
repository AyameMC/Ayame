/**
 * 表示玩家对象。
 */
declare interface Player {
  /** 玩家 ID */
  get name(): string;

  /**
   * 使玩家向服务器发送一条聊天消息。
   *
   * @param message 要发送的消息
   */
  sendChat(message: string): void;

  /**
   * 使玩家向服务器发送一条命令。
   * @param command 要发送的命令
   */
  sendCommand(command: string): void;
}

export { Player };
