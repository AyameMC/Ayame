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
}
