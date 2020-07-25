### SpringBoot控制器与路由

#### 1、关键词

* 注解、控制器、路由、Request方法

2、重点理解的内容

* 注解

3、代码示例及知识点

```
@Controller  // 注解
public class BannerController {

    @GetMapping("/test")  // 路由
    @ResponseBody         //  Reqeust Response对象
    public String test() {
        return "hello,world";
    }
}
```

#### 2、热重启配置步骤

```
热重启步骤
1.打开点击pom.xml配置文件
2.找到配置文件节点 <dependencies></dependencies>
3.在节点中插入以下代码
<dependency>
<groupId>org.springframework.boot</groupId>
<artifactId>spring-boot-devtools</artifactId>
<scope>runtime</scope>
<optional>true</optional>
</dependency>
4.点击编辑器菜单栏view ->Tool Windows->Maven Projects 中查看是否添加成功，没有自己建立建立项目并勾选上这个功能
5.编辑器配置热重启 File->Settings->搜索：Compiler ->勾选 Bulid project automatically
↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓
最后一步：
编辑器中 ctrl +shift +a 搜索 Registry
找到以下内容并在右边打钩
compiler.automake.allow.when.app.running √
然后点击右下角close
重新运行下项目

```



#### 3、 Restfull API的写法

* 重点理解内容

  RestController

  RequestMapping

  需要掌握注解的写法

* 代码示例

  ```
  @RestController
  @RequestMapping("/v1/banner")
  public class BannerControler {
      @GetMapping("/test")
      public String test() {
          return "hello,world3!";
      }
  }
  
  curl http://localhost:8080/v1/banner/test
  ```

