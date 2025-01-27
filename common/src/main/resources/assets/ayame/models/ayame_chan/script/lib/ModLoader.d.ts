/**
 * 模组加载器枚举，表示对应的加载器。
 */
declare enum ModLoader {
  /**
   * 代表 Fabric 加载器。
   *
   * 由于加载原理，Quilt 也会被识别为 Fabric，此特性大概率不会被修复。
   */
  FABRIC = "fabric",
  /**
   * 代表 NeoForge 加载器。
   *
   * 由于加载原理，在 Minecraft 1.20.1，NeoForge 也会被识别为 Forge，此特性永远不会被修复。
   */
  NEOFORGE = "neoforge",
  /**
   * 代表 Forge 加载器。
   *
   * 由于加载原理，在 Minecraft 1.20.1，NeoForge 也会被识别为此项。
   * @see {@link NEOFORGE}
   */
  FORGE = "forge",
}

export = ModLoader;
