### 0、重要知识点

1）如何自定义日志返回类型

2）@ResponseBody 和 ResponseEntity  的作用和区别是什么？

3)   什么是泛型，有哪些特性，如何使用

4） 理解springboot发现机制的思想

5）AutoPrefixUrlMapping,  springboot是如何发现这个自定义的普通的类的呢？



### 1、如何统一捕获异常

* @ControllerAdvice
* @ExceptionHandler

### 2、异常分类

* Error 错误

* Exception 异常

​       CheckedException   必须要处理的

​       RuntimeException   

1)  如何判断是CheckedException和RunntionException?

​     例如：无能无力类型：  网络原因连不上数据库，这种一般是RuntimeException

​               bug类型：  找不到文件，这种一般是CheckedExcetion

2)  示例

```java
@ControllerAdvice
public class GlobalExceptionAdvice {
    @ExceptionHandler(value = Exception.class)
    public void handleException(HttpServletRequest req, Exception e) {
        System.out.println("hello这里出错了！");
    }
}
```

```java
public class NotFoundException extends httpException{
    public NotFoundException(int code) {
        this.httpStatusCode = 404;
        this.code = code;
    }
}
```

```java
@RestController
@RequestMapping("/v1/banner")
public class BannerControler {
    @Autowired
    private ISkill iSkill;

    @GetMapping("/test")
    public String test() {
        iSkill.r();
        throw new NotFoundException(10001);
//        return "hello,world3!";
    }
}
```

### 3、已知异常和未知异常分别如何自定义错误信息

* ResponseEntity



### 4、异常信息写在配置文件里

1)  如何让类和配置文件关联起来？

### 5、自动生成路由前缀

1)   想要完成这样一个功能，怎么知道要去继承哪个类呢？继承了之后要做哪些事呢？

2)   AutoPrefixUrlMapping,  springboot是如何发现这个自定义的普通的类的呢？

​      a、是通过AutoPrefixConfiguration这个配置类发现的

​      b、由于AutoPrefixConfiguration  实现了特定的接口 WebMvcRegistrations, 而WebMvcRegistrations下面

​            有方法getRequestMappingHandlerMapping，这个方法返回RequestMappingHandlerMapping。

​      c、AutoPrefixUrlMapping 继承了 RequestMappingHandlerMapping，所以间接的AutoPrefixUrlMapping被加入到容器



### 100、思考

1） 我的目标是想实现一个自定义的异常类，在java中改如何处理？

* 思路

  a、首先定义一个全局的异常拦截器，拦截器中区分已知异常和未知异常

  b、自定义异常类。

* 逻辑步骤

  * 步骤一： 定义一个消息格式

    {
        "code": 9999,
        "message": "测试未知异常",
        "request": "GET /v1/banner/test"
    }

  * 步骤二： 定义一个类，用来生成这样的消息格式UnifyResponse

  * 步骤三：定义一个通用的Http异常类HttpException并继承RuntimeException

  * 步骤四：定义具体类型的Http异常类并继承HttpException。例如NotFoundException

  * 步骤五：定义全局异常拦截器GlobalExceptionAdvice

  * 步骤六： 在代码中引入并返回具体的异常类型和信息。例如使用NotFoundException

* 

