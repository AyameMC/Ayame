/**
 * 日志输出工具。
 */
declare class Logger {
    // new Logger("Ayame Model Script");

    /**
     * 输出调试级别的日志信息。
     * @param message 要输出的日志消息
     */
    static debug(message: string): void;

    /**
     * 将消息与参数分离。
     * @see {@link debug}
     *
     * @param message 要输出的日志消息
     * @param args 参数列表
     */
    static debug<T>(message: string, ...args: T[]): void;

    /**
     * 输出信息级别的日志信息。
     * @param message 要输出的日志消息
     */
    static info(message: string): void;

    /**
     * 将消息与参数分离。
     * @see {@link info}
     *
     * @param message 要输出的日志消息
     * @param args 参数列表
     */
    static info<T>(message: string, ...args: T[]): void;

    /**
     * 输出警告级别的日志信息。
     * @param message 要输出的日志消息
     */
    static warn(message: string): void;

    /**
     * 将消息与参数分离。
     * @see {@link warn}
     *
     * @param message 要输出的日志消息
     * @param args 参数列表
     */
    static warn<T>(message: string, ...args: T[]): void;

    /**
     * 输出错误级别的日志信息。
     * @param message 要输出的日志消息
     */
    static error(message: string): void;

    /**
     * 将消息与参数分离。
     * @see {@link error}
     *
     * @param message 要输出的日志消息
     * @param args 参数列表
     */
    static error<T>(message: string, ...args: T[]): void;

    /**
     * 输出错误级别的日志信息并使 JVM 抛出一个异常。
     *
     * 此异常一般不会被捕获，这意味着这通常会使 JVM 因此而崩溃。
     * @throws AyameModelScriptException
     *
     * @param message 要输出的日志消息
     */
    static fatal(message: string): void;

    /**
     * 将消息与参数分离。
     * @see {@link fatal}
     *
     * @param message 要输出的日志消息
     * @param args 参数列表
     */
    static fatal<T>(message: string, ...args: T[]): void;
}

export = Logger;
