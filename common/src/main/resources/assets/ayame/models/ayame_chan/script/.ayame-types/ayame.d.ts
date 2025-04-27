/**
 * Ayame
 */
declare namespace Ayame {
    /**
     * Ayame 的版本号，例如 0.1.0。
     * @example
     * '0.1.0'
     */
    const version: string;

    /**
     * Ayame 的 Mod 加载器，例如 'forge'、'fabric'。
     * @example
     * 'forge'
     */
    const modLoader: string;

    /**
     * 获取当前已注册的 Molang 函数列表。
     * @returns 返回字符串数组，包含所有已注册的函数名称。
     * @example
     * Ayame.listRegisteredMolangFunctions(); // ['math.abs', 'math.sin', 'ayame:custom.func']
     */
    function listRegisteredMolangFunctions(): string[];

    /**
     * 注册一个新的 Molang 函数。
     * @param name 函数名称。
     * @param compute 一个回调函数，接受一个或以上个参数。
     * @example
     * Ayame.registerMolangFunction('aym.math.parabola', (x: number) =>  x * x);
     */
    function registerMolangFunction(
        name: string,
        compute: (...args: number[]) => number
    ): void;

    /**
     * 注销（移除）一个指定名称的 Molang 函数。
     * @param name 要移除的函数名称。
     * @example
     * Ayame.unregisterMolangFunction('aym.math.parabola');
     */
    function unregisterMolangFunction(name: string): void;

}
