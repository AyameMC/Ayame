/**
 * 表示一个事件处理器的通用接口。
 */
interface EventHandler<T> {
    /**
     * 注册一个事件监听器。
     *
     * @param callback 回调函数，在事件触发时执行。
     */
    (callback: (event: T) => void): void;
}

export = EventHandler;
