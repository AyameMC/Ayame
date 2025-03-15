/**
 * 日志系统
 */
declare namespace logger {
    /**
     * 输出调试级别的日志信息。
     * @param message 要输出的日志消息
     */
    function debug(message: string): void;

    /**
     * 输出信息级别的日志信息。
     * @param message 要输出的日志消息
     */
    function info(message: string): void;

    /**
     * 输出警告级别的日志信息。
     * @param message 要输出的日志消息
     */
    function warn(message: string): void;

    /**
     * 输出错误级别的日志信息。
     * @param message 要输出的日志消息
     */
    function error(message: string): void;

    /**
     * 输出错误级别的日志信息并使 JVM 抛出一个异常。
     *
     * 此异常一般不会被捕获，这意味着这通常会使 JVM 因此而崩溃。
     * @throws AyameModelScriptException
     *
     * @param message 要输出的日志消息
     */
    function fatal(message: string): void;
}
