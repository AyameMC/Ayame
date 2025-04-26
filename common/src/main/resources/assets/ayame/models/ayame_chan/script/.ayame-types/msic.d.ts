/**
 * 资源文件字符串的匹配器。
 * 
 * 格式要求：[非空部分]:[非空部分]
 *
 * @example 'a:b', 'minecraft:block/stone'
 */
type ResourceLocationString<T extends string> =
    T extends `${infer Prefix}:${infer Suffix}`
    ? Prefix extends ''
    ? `命名空间不能为空，冒号前部分不能为空。`
    : Suffix extends ''
    ? `资源路径不能为空，冒号后部分不能为空。`
    : Suffix extends `${infer _}:${infer _}`
    ? `资源路径不能包含多个冒号。`
    : T
    : never;


type ResourceLocation = `${string}:${string}`;