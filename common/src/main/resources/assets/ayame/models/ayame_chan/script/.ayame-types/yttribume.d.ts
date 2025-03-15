/**
 * Yttribume（模型属性）系统
 */
declare interface Yttribume {
    /**
     * 获取完整的 ResourceLocation 字符串
     */
    getId(): string;

    getMinValue(): number;

    getMaxValve(): number;
}

/**
 * Yttribume
 */
declare namespace yttribume {
    /**
     * 获取模型属性对象。
     * @param namespace 命名空间
     * @param attribute 属性名称
     */
    function get(namespace: string, attribute: string): Yttribume;
}
