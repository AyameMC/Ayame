/**
 * 事件系统的基础接口。
 * 所有事件都继承自这个接口，但不强制要求任何具体字段。
 */
declare interface EventContext { }

/**
 * 玩家每 tick 更新事件
 */
declare interface PlayerTickEvent extends EventContext {
    player: Player;
}

/**
 * 玩家按下键盘事件
 */
declare interface KeyPressEvent extends EventContext {
    player: Player;
    key: number;
}

/**
 * 玩家攻击实体事件
 */
declare interface AttackEntityEvent extends EventContext {
    player: Player;
    target: Entity;
}

/**
 * 玩家选择轮盘选项事件
 */
declare interface RouletteOptionEvent extends EventContext {
    player: Player;
    optionName: string;    // 选项 ID，比如 "awsl"
    iconTexture?: string;  // 可选的图标纹理路径，比如 "minecraft:textures/block/tnt_side.png"
}

/**
 * 严格校验字符串格式：必须包含且仅包含一个冒号
 * 格式要求：[非空部分]:[非空部分]
 *
 * 示例有效值：
 *   - "a:b"
 *   - "minecraft:block/stone"
 *
 * 示例无效值：
 *   - ":b"
 *   - "a:"
 *   - "a:b:c"
 *   - "nocolon"
 *
 * 如果不符合，会在类型层面提示具体错误原因。
 */
type StrictColonString<T extends string> =
    T extends `${infer Prefix}:${infer Suffix}`
        ? Prefix extends ""
            ? "冒号前（命名空间）不能为空"
            : Suffix extends ""
                ? "冒号后（路径）不能为空"
                : Suffix extends `${infer _}:${infer _}`
                    ? "不能包含多个冒号"
                    : T
        : "必须包含且仅包含一个冒号";

/**
 * 玩家相关的事件集合
 */
declare namespace PlayerEvents {
    /**
     * 每 tick 触发
     */
    function tick(callback: (event: PlayerTickEvent) => void): void;

    /**
     * 按键时触发
     */
    function keyPress(callback: (event: KeyPressEvent) => void): void;

    /**
     * 攻击实体时触发
     */
    function attackEntity(callback: (event: AttackEntityEvent) => void): void;
}

/**
 * 轮盘选项相关的注册器
 */
declare namespace RouletteOption {
    /**
     * 添加一个轮盘选项
     * @param name 选项的显示名称（比如 "坐下"）
     * @param icon 图标纹理路径（格式必须是 namespace:path，比如 "minecraft:block/stone"）
     * @param callback 当玩家选择该选项时触发
     */
    function add<T extends string>(
        name: string,
        icon: StrictColonString<T>,
        callback: (event: RouletteOptionEvent) => void
    ): void;
}
