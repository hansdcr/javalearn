### 1、Springboot框架学习笔记

#### 1.1  目录结构及解释

* api  路由控制层
* service   处理业务逻辑，并返回给api路由控制层
* model    数据层， 定义数据实体Entity，存放数据。
* respository  数据处理层， 处理SQL查询语句
* vo  对返回给客户的数据进行处理， 比如精简返回给前端的信息
* bo
* dto   对客户传输过来的数据进行处理，比如参数校验
* core     负责全局性的功能的处理。比如全局的路由拦截，全局异常的定义等
* util   工具类
* exception.  自定义异常

#### 1.2  api路由控制层

##### 1.2.0  路由控制器部分涉及的知识点

* Restful风格的接口设计
* @RestController
* @RequestMapping
* @GetMapping
* 自定义返回数据格式
* 自定义异常
* 配置文件解析

##### 1.2.1   如何向浏览器输出一个helloword

```
curl http://localhost:8080/v1/banner/name
```

```java
@RestController
@RequestMapping("/v1/banner")
public class BannerController {
    @GetMapping("/name")
    public String test(){
       return "hello world";
    }
}
```

```
浏览器输出: hello world
```

##### 1.2.2   知识点分析

* 这里为什么要打这些注解？每个注解的作用是什么

  @RestController 用来声明这是一个路由控制器。一般用于返回ison类型的数据。 可以了解它和@Controller的区别

  @RequestMapping。把web请求映射到具体的类或者方法

* 对应restful接口的注解

  @GetMapping

  @PostMapping

  @PutMapping

  @DeleteMapping

##### 1.2.3  这个demo需要改进的地方有哪些

* 如何处理动态请求
* 如何对请求参数进行校验
* 如何返回json类型数据给前端
* 如何自动识别接口版本，代码中不硬编码版本
* 如何封装一个全局路由拦截的功能
* 如何通过配置文件自定义错误码

###### 1.2.3.1  全局响应拦截器

* 响应拦截的思路

  定义一个通用的响应请求的类，用于规范响应格式。UnifyResponse

  使用注解@RestControllerAdvice和接口拦截请求响应GlobalResponseAdvise

  再定一个全局异常响应的拦截器GlobalExceptionAdvise

* 希望返回的响应数据格式如下

  ```java
  {
  	code: 10000,
  	message: 'ok',
  	data: {}
  }
  ```

* 全局响应拦截功能实现

  ```java
  @Setter
  @Getter
  @AllArgsConstructor
  @NoArgsConstructor
  public class UnifyResponse<T> {
      private int code;
      private String message;
      private String request;
      private T data;
  
      public UnifyResponse(T data){
          this.setData(data);
      }
  }
  ```

  ```java
  @RestControllerAdvice
  public class GlobalResponseAdvise implements ResponseBodyAdvice<Object> {
  
      @Override
      public boolean supports(MethodParameter methodParameter, Class<? extends HttpMessageConverter<?>> aClass) {
          return true;
      }
  
      @Override
      public Object beforeBodyWrite(Object o, MethodParameter methodParameter, MediaType mediaType, Class<? extends HttpMessageConverter<?>> aClass, ServerHttpRequest serverHttpRequest, ServerHttpResponse serverHttpResponse) {
          if(o instanceof UnifyResponse){
              return o;
          }
          return new UnifyResponse(o);
      }
  }
  ```

  拦截器的使用

  ```java
  @RestController
  @RequestMapping("/v1/banner")
  public class BannerController {
      @GetMapping("/name")
      public Banner test(){
          Banner banner = new Banner(1,"b1-name","b1-desc","b1-title","b1-img");
          return banner;
      }
  }
  ```

  输出：

  ```java
  {
      "code": 0,
      "message": null,
      "request": null,
      "data": {
          "id": 1,
          "name": "b1-name",
          "description": "b1-desc",
          "title": "b1-title",
          "img": "b1-img"
      }
  }
  ```

* 全局异常响应拦截器

  * 根据异常的种类定义不同的异常拦截方法

    通用Exception异常

    通用Http请求异常

    Get请求参数校验异常

    Post请求参数校验异常

  * 全局异常拦截使用的类和方法@ControllerAdvice 和 @ExceptionHandler

  

  首先定义一个通用的HttpException

  ```java
  public class HttpException extends RuntimeException {
      public Integer code;
      public Integer httpStatusCode = 500;
  
  
      public Integer getCode() {
          return code;
      }
      public Integer getHttpStatusCode() {
          return httpStatusCode;
      }
  }
  ```

  然后根据具体的异常需求自定义异常

  ```java
  public class NotFoundException extends HttpException {
      public NotFoundException(Integer code){
          this.code = code;
          this.httpStatusCode = 404;
      }
  }
  ```

  设置全局异常的响应拦截

  ```java
  @ControllerAdvice
  public class GlobalExceptionAdvise{
  
      @ExceptionHandler(value = Exception.class)
      @ResponseStatus(code= HttpStatus.INTERNAL_SERVER_ERROR)
      @ResponseBody
      public UnifyResponse handleException(HttpServletRequest req, Exception e){
          String method = req.getMethod();
          String requestUri = req.getRequestURI();
          String request = method + " " + requestUri;
  
          System.out.println(e);
  
          UnifyResponse unifyResponse = new UnifyResponse(50000,"未知异常", request, null);
          return unifyResponse;
      }
  
      @ExceptionHandler(value = HttpException.class)
      public ResponseEntity<UnifyResponse> handleHttpException(HttpServletRequest req, HttpException e){
          String method = req.getMethod();
          String requestUri = req.getRequestURI();
          String request = method + " " + requestUri;
  
          // 通过ResponseEntity设置http响应头信息，比如状态码
          HttpHeaders headers = new HttpHeaders();
          headers.setContentType(MediaType.APPLICATION_JSON);
          HttpStatus httpStatus = HttpStatus.resolve(e.getHttpStatusCode());
  
          UnifyResponse unifyResponse = new UnifyResponse(e.getCode(), "http异常", request, null);
          ResponseEntity<UnifyResponse> r = new ResponseEntity<>(unifyResponse, headers, httpStatus);
  
          return r;
      }
  }
  ```

  使用自定义的异常

  ```java
  @RestController
  @RequestMapping("/v1/banner")
  public class BannerController {
      @GetMapping("/name")
      public Banner test(){
          // Banner banner = new Banner(1,"b1-name","b1-desc","b1-title","b1-img");
          Banner banner = new Banner();
          throw new NotFoundException(60000);
          // return banner;
      }
  }
  ```

  客户端返回信息

  ```java
  {
      "code": 60000,
      "message": "http异常",
      "request": "GET /v1/banner/name",
      "data": null
  }
  ```

###### 1.2.3.2 配置文件解析

* 配置文件解析的方法

  @Value： 可以通过此注解直接读取配置文件内容

  @ConfigurationProperties： 通过类的方式注入

* 使用@ConfigurationProperties的优点有哪些

  批量注入

  支持校验

  支持自动转为驼峰命名

  支持复杂类型注入

  不支持EL表达式

* 示例： 

  配置文件内容

  ```java
  learn.codes[9999] = 服务器未知异常
  learn.codes[20000] = 资源不存在
  learn.codes[30000] = 权限不被允许
  learn.codes[40000] = 请求参数校验错误
  learn.codes[40001] = Post请求参数校验错误
  ```

  

  定义一个配置解析的类

  * PropertySource 关联配置文件路径
  * ConfigurationProperties  配置文件相关树雄定义

  ```java
  @ConfigurationProperties(prefix = "learn")
  @PropertySource(value = "classpath:config/exception-code.properties")
  @Component
  public class ExceptionCodeConfiguration {
      private Map<Integer, String> codes = new HashMap<>();
  
      public Map<Integer, String> getCodes(){
          return this.codes;
      }
  
      public void setCodes(Map<Integer,String> codes){
          this.codes = codes;
      };
  
      public String getMessage(int code){
          return this.codes.get(code);
      }
  }
  
  ```

  配置类的使用

  ```java
  @ControllerAdvice
  public class GlobalExceptionAdvise{
      @Autowired
      private ExceptionCodeConfiguration exceptionCodeConfiguration;  // 配置类的使用
  
      @ExceptionHandler(value = Exception.class)
      @ResponseStatus(code= HttpStatus.INTERNAL_SERVER_ERROR)
      @ResponseBody
      public UnifyResponse handleException(HttpServletRequest req, Exception e){
          String method = req.getMethod();
          String requestUri = req.getRequestURI();
          String request = method + " " + requestUri;
  
          System.out.println(e);
  
          String message = exceptionCodeConfiguration.getMessage(9999); // 配置类的使用
          UnifyResponse unifyResponse = new UnifyResponse(9999, message, request, null);
          return unifyResponse;
      }
  
      @ExceptionHandler(value = HttpException.class)
      public ResponseEntity<UnifyResponse> handleHttpException(HttpServletRequest req, HttpException e){
          String method = req.getMethod();
          String requestUri = req.getRequestURI();
          String request = method + " " + requestUri;
  
          // 通过ResponseEntity设置http响应头信息，比如状态码
          HttpHeaders headers = new HttpHeaders();
          headers.setContentType(MediaType.APPLICATION_JSON);
          HttpStatus httpStatus = HttpStatus.resolve(e.getHttpStatusCode());
  
          String message = exceptionCodeConfiguration.getMessage(e.getCode()); // 配置类的使用
          UnifyResponse unifyResponse = new UnifyResponse(e.getCode(), message, request, null);
          ResponseEntity<UnifyResponse> r = new ResponseEntity<>(unifyResponse, headers, httpStatus);
  
          return r;
      }
  }
  ```

###### 1.2.3.3 处理动态请求

```java
    @GetMapping("/name/{name}")
    public Banner test(@PathVariable @NameLength(min = 3, max = 9) String name){
        Banner banner = new Banner(1,"b1-name","b1-desc","b1-title","b1-img");
        //Banner banner = new Banner();
        return banner;
    }
```



###### 1.2.3.4  对请求参数进行校验

* 服务器端对客户的请求参数合法性进行校验
* 参数校验常用的注解
* 自定义参数校验注解

Get请求的参数校验

* 校验传入的get参数的name长度大于3小于10

第一步：定义校验注解

* ElementType.PARAMETER 用于限定此注解用于请求参数
* @Constraint(validatedBy = NameLengthValidator.class) 限定关联绑定到NameLengthValidator校验的类

```java
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.PARAMETER})
@Constraint(validatedBy = NameLengthValidator.class)
public @interface NameLength {
    int min() default 3;
    int max() default 10;
    String message() default "名字长度需要大于3小于10";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
```

第二步： 定义具体的验证逻辑

* 需要实现接口：ConstraintValidator
* ConstraintValidator<NameLength,String> 关联注解和校验的对象类型
* 实现接口initialize和isValid

```java
public class NameLengthValidator implements ConstraintValidator<NameLength,String> {
    private Integer min;
    private Integer max;

    @Override
    public void initialize(NameLength constraintAnnotation) {
        this.min = constraintAnnotation.min();
        this.max = constraintAnnotation.max();
    }

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        return s.length() <= this.min && s.length() >= this.max;
    }
}
```

第三步：使用自定义参数校验注解

* 需要校验的路由，要添加注解@Validated
*  使用注解：test(@PathVariable @NameLength(min = 3, max = 9) String name)

```java
@Validated
@RestController
@RequestMapping("/v1/banner")
public class BannerController {
    @GetMapping("/name/{name}")
    public Banner test(@PathVariable @NameLength(min = 3, max = 9) String name){
        Banner banner = new Banner(1,"b1-name","b1-desc","b1-title","b1-img");
        //Banner banner = new Banner();
        return banner;
    }
}
```

第四步： 设置全局异常拦截

```java
    @ResponseBody
    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    public UnifyResponse handleConstraintException(HttpServletRequest req, ConstraintViolationException e){
        String method = req.getMethod();
        String requestUri = req.getRequestURI();
        String request = method + " " + requestUri;


        return new UnifyResponse(40000, e.getMessage(), request,null);
    }
```



第五步： 查看校验结果

```
{
    "code": 40000,
    "message": "test.name: 名字长度需要大于3小于10",
    "request": "GET /v1/banner/name/%201",
    "data": null
}
```

Post请求的参数校验

* 定义注解

```java
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.FIELD})
@Constraint(validatedBy = BannerPostValidator.class)
public @interface BannerPost {
    int min() default 3;
    int max() default 10;
    String message() default "字段长度需要大于3小于10";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
```

* 定义验证器

```java
public class BannerPostValidator implements ConstraintValidator<BannerPost, BannerDTO> {
    private int min;
    private int max;

    @Override
    public void initialize(BannerPost constraintAnnotation) {
        this.min = constraintAnnotation.min();
        this.max = constraintAnnotation.max();
    }

    @Override
    public boolean isValid(BannerDTO bannerDTO, ConstraintValidatorContext constraintValidatorContext) {
        return bannerDTO.getTitle().length() >= this.min && bannerDTO.getTitle().length() >= this.max;
    }
}
```

* 定义验证对象

```java
@Getter
@Setter
@BannerPost // 使用校验注解
public class BannerDTO {
    private String name;

    private String title;
}
```

* 全局异常拦截

```java
    @ResponseBody
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(code= HttpStatus.BAD_REQUEST)
    public UnifyResponse handleBeanValidation(HttpServletRequest req, MethodArgumentNotValidException e) {
        String method = req.getMethod();
        String requestUri = req.getRequestURI();
        String request = method + " " + requestUri;

        List<ObjectError> errors = e.getBindingResult().getAllErrors();
        String message = this.formatAllErrorMessages(errors);
        return new UnifyResponse(40001, message, request, null);
    }

    public String formatAllErrorMessages(List<ObjectError> errors) {
        StringBuffer errorMsg = new StringBuffer();
        errors.forEach(error -> errorMsg.append(error.getDefaultMessage()).append(";"));
        return errorMsg.toString();
    }
```

* 在路由入口处引用

```java
    @PostMapping("/name")
    public Banner test(@RequestBody @Validated BannerDTO bannerDTO){
        Banner banner = new Banner(1,"b1-name","b1-desc","b1-title","b1-img");
        return banner;
    }
```

*  发送post请求

```java
{
	"name": "nametest",
	"title": "b",
	"img": "http://x.x/a.jpg"
}
```

* 校验结果

```
{
    "code": 40001,
    "message": "字段长度需要大于3小于10;",
    "request": "POST /v1/banner/name",
    "data": null
} 
```

###### 1.2.3.100  全局路由拦截

* 全局路由的几种拦截方式

  使用Filter接口

  使用HanlderInterceptor接口

  使用@ExceptionHandler 注解

* 选择@ExceptionHandler的原因

  可以方便的捕获全局异常，自定义输出结果

  通常与@ControllerAdvice注解一起使用

#### 1.3 Service 业务逻辑层

#### 1.4 Respotigory 层

#### 1.5 Model层

#### 2.1 JPA的使用

#### 2.2 Mybatis使用









