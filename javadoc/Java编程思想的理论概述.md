### 1、springboot框架好在哪里？和其他框架比优势是什么？缺点是什么？

### 2、常见的编程原则

* 开闭原则（OCP）Open Closed Principle
* 里式替换原则
* 迪米特法则
* IOC
* DI （Dependency Injection）
* DIP (Dependency Inversion Principle)
* interface
* 工厂模式



  重点理解：OCP

    *  扩展式开发的，修改是关闭的

   1） 举例解释什么是“扩展是开放的，修改是关闭的”？

​         比如：我们写了一个类，类的功能是打印A, 那过一段时间需求变了，需要改成打印B，此时由于可能

​         原来的工程比较旧或很复杂，你不清楚修改了会带来什么问题，此时一般可以重新写一个类实现打印B

​         的需求， 这样带来的影响是最小的。

​        比如： 对接口区分V1和V2都可以理解成是OCP原则的使用。

### 3、面向抽象编程

​     1）为什么要实现OCP原则，就要面向抽象编程

### 4、示例，一步步优化代码实现OCP原则

1）版本一： 最小白的写法

```java
public class Diana {
    public void q() {
        System.out.println("Diana Q");
    }
    public void w() {
        System.out.println("Diana W");
    }
    public void e() {
        System.out.println("Diana E");
    }
    public void r() {
        System.out.println("Diana R");
    }
}
```

```java
public class Main {

    public static void main(String[] args) {
        String name = Main.getPlayerInput();
        switch (name) {
            case "Diana":
                Diana diana = new Diana();
                diana.r();
                break;
            case "Irelia":
                Irelia irelia = new Irelia();
                irelia.r();
                break;
            case "Camille":
                Camille camille = new Camille();
                camille.r();
                break;
        }
    }
    public static String getPlayerInput() {
        System.out.println("Enter a Hero Name: ");
        Scanner scanner = new Scanner(System.in);
        return scanner.nextLine();
    }
}
```

* 上面的代码的问题是什么？

  a、没增加一个英雄都需要修改代码，在switch中添加新的英雄

  ​      这样写虽然比较容易理解，但是不符合OCP的原则，一旦

  ​      英雄数量有变化，都需要修改代码去适配

* 我们希望达到什么样的程度？

  a、 新增英雄后，不需要修改过多的代码

*  如何修改代码进行优化？使用Java的哪种思想去优化？

2）版本2:

```java
public interface ISkill {
    void q();
    void w();
    void e();
    void r();
}
```

```java
public class Camille implements ISkill {
    public void q() {
        System.out.println("Camille Q");
    }
    public void w() {
        System.out.println("Camille W");
    }
    public void e() {
        System.out.println("Camille E");
    }
    public void r() {
        System.out.println("Camille R");
    }
}
```

```java
    public static void main(String[] args) throws Exception {
        String name = Main.getPlayerInput();
        ISkill iSkill;
        switch (name) {
            case "Diana":
                iSkill = new Diana();
                break;
            case "Irelia":
                iSkill = new Irelia();
                break;
            case "Camille":
                iSkill = new Camille();
                break;
            default:
                throw new Exception();
        }
        iSkill.r();
    }
```

3) 版本3

```java
package com.company.factory;

import com.company.factory.hero.Camille;
import com.company.factory.hero.Diana;
import com.company.factory.hero.Irelia;

public class HeroFactory {
    public static ISkill getHero(String name) throws Exception {
        ISkill iSkill;
        switch (name) {
            case "Diana":
                iSkill = new Diana();
                break;
            case "Irelia":
                iSkill = new Irelia();
                break;
            case "Camille":
                iSkill = new Camille();
                break;
            default:
                throw new Exception();
        }
        return iSkill;
    }
}

```

```java
    public static void main(String[] args) throws Exception {
        String name = Main.getPlayerInput();
        ISkill iSkill = HeroFactory.getHero(name);
        iSkill.r();
    }
```

4) 版本4

```java
public class HeroFactory {
    public static ISkill getHero(String name) throws Exception {
        ISkill iSkill;
        String classStr = "com.company.reflect.hero."+name;
        Class<?> cla = Class.forName(classStr);
        Object obj = cla.newInstance();
        return (ISkill)obj;
    }
}
```

```java
    public static void main(String[] args) throws Exception {
        String name = Main.getPlayerInput();
        ISkill iSkill = HeroFactory.getHero(name);
        iSkill.r();
    }
```



### 5、重点理论

1）单纯interface可以统一方法的调用，但是它不能统一对象的实例化

2）面向对象： 就是对象实例化、调用方法。（完成业务逻辑的过程）

3）只有一段代码中没有new关键字的出现，才能保持代码的相对稳定，才能逐步实现OCP

4）上面的代码只是表象，实质是一段代码要保持稳定，就不应该负责对象的实例化。

5）对象的实例化是不能被消除的，只能把实例化的过程转移到其他的代码片段中

6） 代码中总是会存在不稳定，尽可能隔离这些不稳定，保障其他代码是稳定的。



### 6、思考题（作业）

1） 如果我们实现了OCP，那么还需要IOC和DI做什么？



### 7、DIP依赖倒置是什么

1）高层模块不依赖底层模块，两者都应该依赖抽象

2）抽象不应该依赖细节

3）细节应该依赖抽象

### 8、DI依赖注入

1）属性注入

2）构造注入

3）接口注入

### 8、IOC

1）IOC的具体意义： 把对象加入容器，同时在需要的时候注入代码片段

2）IOC抽象意义： 把控制权交给用户

3） 容器、加入容器、注入

















