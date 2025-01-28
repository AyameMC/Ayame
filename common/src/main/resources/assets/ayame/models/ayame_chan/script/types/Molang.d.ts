/**
 * Molang 类用于操作和获取 Molang 表达式相关的变量。
 */
declare class Molang {
  private constructor();
  /**
   * 获取指定名称的 Molang 变量的值。
   *
   * @param varName 变量名称
   * @returns 返回指定变量的数值。
   * @throws 如果变量不存在或无效，则抛出错误。
   */
  static getVar(varName: string): number;

  /**
   * 将数字装箱为 Molang 数字。
   */
  static valueOf(number: number): Molang;

  /**
   * 将 Molang 数字拆箱为 number。
   */
  valueOf(): number;
}

export = Molang;
