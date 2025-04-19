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
 * 轮盘选项事件
 * 当玩家选择轮盘选项时触发
 */
declare interface RouletteOptionEvent extends EventContext {
    player: Player;
    // 可能包含的其他字段
    optionName: string;       // 选项ID（如示例中的"awsl"）
    iconTexture?: string;   // 图标纹理路径（如示例中的"minecraft:textures/block/tnt_side.png"）
}

/**
 * 严格校验字符串包含且仅包含一个冒号
 * 格式必须为 [非空部分]:[非空部分]
 * 示例有效值："a:b", "minecraft:block/stone"
 * 示例无效值：":b", "a:", "a:b:c", "no-colon"
 */
type StrictColonString<T extends string> =
    T extends `${infer Prefix}:${infer Suffix}`  // 分割为前后两部分
    ? Prefix extends ""                     // 检查前缀是否为空
    ? never                             // 前缀为空 → 排除
    : Suffix extends ""                 // 检查后缀是否为空
    ? never                         // 后缀为空 → 排除
    : Suffix extends `${infer _}:${infer _}`  // 检查是否包含多个冒号
    ? never                     // 包含多个冒号 → 排除
    : T                         // 符合条件 → 保留原类型
    : never;                                // 没有冒号 → 排除

/**
 * 轮盘选项注册器（增强版）
 */
declare interface RouletteOptionRegistry {
    /**
     * 添加一个轮盘选项。
     * @param name 轮盘选项名称
     * @param icon 图标路径
     * @param callback 回调函数内容
     */
    add<T extends string>(
        name: string,
        icon: StrictColonString<T>, // 强约束图标路径
        callback: (event: RouletteOptionEvent) => void
    ): void;
}


declare const RouletteOption: RouletteOptionRegistry;
/**
 * 事件注册对象
 */
declare const PlayerTickEvent: EventRegistry<PlayerTickEvent>;
declare const KeyPressEvent: EventRegistry<KeyPressEvent>;
declare const AttackEntityEvent: EventRegistry<AttackEntityEvent>;
