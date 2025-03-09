// declare function myLib(a: string): string;
// declare function myLib(a: number): number;
/*~ 如果你希望这个库的名称成为一个有效的类型名称，
 *~ 你可以在这里进行声明。
 *~
 *~ 例如，这样就允许我们写 'var x: myLib';
 *~ 但请确保这样做是有意义的！如果没有必要，
 *~ 请直接删除此声明，并在下面的命名空间内添加类型。
 */
interface myLib {
  name: string;
  length: number;
  extras?: string[];
}
/*~ 如果你的库在全局变量上暴露了一些属性，
 *~ 请在这里添加它们。
 *~ 你也可以在这里添加类型（接口和类型别名）。
 */
declare namespace Ayame {
  /**
     * Ayame 的版本号，例如0.1.0。
     * @example
     * "0.1.0"
     */
  const version: string; // = "0.1.0";
  //~ 这里有一个类，我们可以通过 'let c = new myLib.Cat(42)' 创建它
  //~ 或者通过 'function f(c: myLib.Cat) { ... }' 引用它
  class Cat {
    constructor(n: number);
    //~ 我们可以通过 'c.age' 读取 'Cat' 实例的年龄
    readonly age: number;
    //~ 我们可以通过 'c.purr()' 调用 'Cat' 实例的 purr 方法
    purr(): void;
  }
  //~ 我们可以声明一个变量：
  //~   'var s: myLib.CatSettings = { weight: 5, name: "Maru" };'
  interface CatSettings {
    weight: number;
    name: string;
    tailLength?: number;
  }
  //~ 我们可以写 'const v: myLib.VetID = 42;'
  //~ 或者 'const v: myLib.VetID = "bob";'
  type VetID = string | number;
  //~ 我们可以调用 'myLib.checkCat(c)' 或 'myLib.checkCat(c, v);'
  function checkCat(c: Cat, s?: VetID);
}
