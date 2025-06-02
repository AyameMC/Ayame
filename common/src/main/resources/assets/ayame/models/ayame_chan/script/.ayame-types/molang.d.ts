declare namespace Molang {
    /**
     * 以玩家对象执行 Molang。
     */
    function exec(molangCode: string): string | number;

    /**
     * 获取当前 Molang 函数列表。
     * @returns 返回字符串数组，包含所有已注册的函数名称。
     * @example
     * ['math.abs', 'math.sin', 'ayame:custom.func']
     */
    function listFunctions(): string[];

    /**
     * 获取当前 Molang 函数列表（以字符串形式）。
     * @returns 返回字符串，包含所有已注册的函数名称。
     * @example
     * ['math.abs', 'math.sin', 'ayame:custom.func']
     */
    function listFunctionsAsString(): string;

    /**
     * 注册一个新的 Molang 函数。
     * @param name 函数名称。
     * @param compute 一个回调函数，接受一个或以上个参数。
     * @example
     * Ayame.registerMolangFunction('aym.math.parabola', (x: number) =>  x * x);
     */
    function registerFunction(
        name: string,
        compute: (...args: number[]) => number
    ): void;

    /**
     * 注册一个新的 Molang 变量。
     * @param name 变量名称
     * @param value 变量的值
     */
    function registerVariable(
        name: string,
        value: number
    ): void;

    /**
     * 设置一个 Molang 变量，如果变量已存在，则覆盖其值。
     * @param name 变量名称
     * @param value 变量的值
     */
    function setVariable(
        name: string,
        value: number
    ): void;

}