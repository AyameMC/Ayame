/**
 * 事件系统的基础接口。
 * 所有事件都继承自这个接口，但不强制要求任何具体字段。
 */
declare interface EventContext { }

/**
 * 泛型事件注册接口。
 * @template T 事件的具体类型
 */
declare interface EventRegistry<T extends EventContext> {
    register(callback: (event: T) => void): void;
}

/**
 * 具体事件类型
 */
declare interface PlayerTickEvent extends EventContext {
    player: Player;
}

declare interface KeyPressEvent extends EventContext {
    player: Player;
    key: number;
}

declare interface AttackEntityEvent extends EventContext {
    player: Player;
    target: Entity;
}

/**
 * 事件注册对象
 */
declare const PlayerTickEvent: EventRegistry<PlayerTickEvent>;
declare const KeyPressEvent: EventRegistry<KeyPressEvent>;
declare const AttackEntityEvent: EventRegistry<AttackEntityEvent>;
