/**
 * Yttribume（模型属性）系统
 */
declare interface Yttribume {
    /**
     * 获取完整的 ResourceLocation 字符串
     */
    getId(): ResourceLocation;

    getMinValue(): number;

    getMaxValve(): number;
}

type BuiltinYttribumeLocation =
    | "ayame:global.screen.shake"
    | "ayame:effect.bloom"
    | "ayame:ui.button.hover" // 举例
    | "ayame:player.glow"     // 举例

/**
 * Yttribume
 */
declare namespace yttribume {
    /**
     * 获取模型属性对象。
     * @param location 资源路径
     */
    function get<T extends string>(location: ResourceLocationString<T>): Yttribume;
}
