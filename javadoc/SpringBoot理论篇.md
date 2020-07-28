### 0、重点内容（作业）

1）自动配置有什么作用？原理是什么？

2）把对象注入容器的方式有哪些？各自的优势和劣势是什么？

3）springboot中，对象是在什么阶段被注入到容器的，在什么阶段被引入代码片段的，有没有办法更改注入阶段？

4）为什么要有@Configuration？

5)  条件注释可以解决什么问题？为什么要有条件注释？



### 1、springboot的核心优势是什么

1）自动配置有什么作用？原理是什么？



### 2、把对象注入容器的方式

1）XML

2）注解

3)  示例：

步骤一： 把对象加入容器

```java
@Component // 把对象加入容器
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

步骤二： 在代码片段中注入

```java
@RestController
@RequestMapping("/v1/banner")
public class BannerControler {
    @Autowired  // 代码片段中注入
    private Diana diana;

    @GetMapping("/test")
    public String test() {
        diana.r();  // 调用对象的方法（发现并没有new对象，但是可以成功调用对象的方法）
        return "hello,world3!";
    }
}
```

### 3、 延迟注入

* 注入生效的阶段本来是在springboot启动的时候对象就会被实例化，加入@ Lazy后可以延迟不在在启动的时候被实例化

```java
@Lazy // 延迟实例化
@Component
public class Diana {
    public Diana() {
        System.out.println("hello, i am hans!");
    }
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
@Lazy // 延迟实例化
@RestController
@RequestMapping("/v1/banner")
public class BannerControler {
    @Autowired
    private Diana diana;

    @GetMapping("/test")
    public String test() {
        diana.r();
        return "hello,world3!";
    }
}
```

### 4、@Autowired被动注入方式和顺序

* 方式：

  by type

  By name

* 顺序

  1）找不到任何一个bean报错

  2）只有一个 直接注入

  3）找到多个，并不一定会报错，按照字段名字推断选择哪个bean

* 主动注入可以使用：@Qualifier

### 5、面向对象中应对变化的方案

1）制定一个interface,然后多个类实现同一个interface

2)  一个类，通过属性的更改，应对变化



### 6、@Configuration

### 7、@ComponentScan

* 策略模式的变化方案

  1）byname 切换bean name

  2). @Qualifier 指定bean

  3)  有选择的只注入一个bean,注释掉某个bean的@Component注解

  4）使用@Primary ( 推荐使用此种方法)

### 8、条件注解 @Conditional

1)  自定义条件注解

2）成品条件注解

