/**
 * 实体
 */
declare interface Entity {
    /**
     * 获取实体的本地化名称。
     * @returns 本地化后的实体名称
     * @example '僵尸', 'Zombie'
     */
    getLocalizedName(): string;

    /**
     * 获取实体的类型。
     * @return 实体类型
     * @example 'minecraft:fox', 'twilightforest:naga'
     */
    getType(): ResourceLocation;

    /**
     * 获取实体的 X 坐标。
     * @return 坐标
     */
    getX(): number;

    /**
     * 获取实体的 Y 坐标。
     * @return 坐标
     */
    getY(): number;

    /**
     * 获取实体的 Z 坐标。
     * @return 坐标
     */
    getZ(): number;

    /**
     * 生成粒子效果。
     * @param location  粒子效果路径
     * @param x
     * @param y
     * @param z
     * @param xSpeed
     * @param ySpeed
     * @param zSpeed
     */
    spawnParticle<T extends string>(location: ResourceLocationString<T>, x: number, y: number, z: number, xSpeed: number, ySpeed: number, zSpeed: number);
}
