import ModLoader from "./ModLoader.d.ts";

/**
 * Ayame 的主要功能入口点，
 * 提供各类实用功能，如模组加载器与 Ayame 版本号。
 */
declare class Mod {
    /**
     * Ayame 的版本号，例如0.1.0。
     * @example
     * "0.1.0"
     */
    static VERSION: string; // = "0.1.0";

    private constructor();

    /**
     * 检查 Ayame 正在使用的模组加载器，例如 Fabric 或 NeoForge。
     */
    static get modLoader(): ModLoader;
}

export = Mod;
