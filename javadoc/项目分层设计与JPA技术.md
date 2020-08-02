### 1、重要知识点

* 分层的目的是什么？
* Java语法规范
* 将类加入容器的时候，为什么@Service是加在interface上面，而不是类上面？
* 层与层之间要用接口衔接，而不要直接使用类
* 要重点理解接口interface的作用和使用方法
* 自己实现一个工厂模式
* 什么事策略模式？
* 如何创建数据库
* 如何查询数据库
* 需要补充SQL语法的基础知识
* 抽象类和接口的区别
* 数据库扩展问题
* Model中定义的Long和int/integer的区别是什么？
* 如何理解延迟思考，逐层深入
* 为什么要使用VO对象
* 如何提取一个Model中的一部分字段信息？dozermapper的作用？
* 如何进行分页？
* 通过分页的封装理解如何使用泛型来对功能进行抽象和封装
* 分类数据表的设计
* JPA接口命名规则
* 什么叫Java Bean? 和普通的类有什么区别
* 要熟练掌握map这种数据类型的使用

### 2、分层思想

1） 分层的目的是什么？

* 大型项目每一层都是由不同团队完成
* 水平分隔
* 垂直分隔

### 3、JPA

1） java语法规范

* 类名（如果实现一个接口的话）BannerServiceImpl 
* 接口名 BannerService

2)  将类加入容器的时候，为什么@Service是加在interface上面，而不是类上面？

### 4、如何创建数据表

1） 可视化管理工具（navicat/workbench）

2)   手写SQL语句

3） Model模型类

### 5、多环境配置

* 总的入口

```
spring:
  profiles:
    active: dev

hans:
  api-package: com.hans.demo.api

```

在这里只要遵守命名规范，就可以通过active: dev 找到application-dev.yml

* 开发环境

```
server:
  port: 8081
```

* 生产环境

```
server:
  port: 8080
```

### 6、连接数据库

1）JPA安装

```java
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-data-jpa</artifactId>
        </dependency
          
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-jdbc</artifactId>
        </dependency>

        <dependency>
            <groupId>mysql</groupId>
            <artifactId>mysql-connector-java</artifactId>
            <scope>runtime</scope>
        </dependency>
```

```java
spring:
  profiles:
    active: dev
  datasource:
    url: jdbc:mysql://localhost:3306/missyou?characterEncoding=utf-8&serverTimezone=GMT%2B8
    username: root
    password:
  jpa:
    hibernate:
      ddl-auto: update
```

关闭JPA

```
  jpa:
    hibernate:
      ddl-auto: none
```



2)  数据库相关的数据

* @Entity

* @Id

  

### 7、数据库

1)  什么是外键

2)  以Banner这个小程序的轮播图为例子，如何将图形界面抽象为数据库模型？

* 轮播图这个功能可以看出是Banner
* 这个功能中多个图片，每个图片可以看成是BannerItem
* 一个Banner可以对应多个BannerItem

3) 示例

```java
curl localhost:8081/v1/banner/name/xxx
```



```java
    @GetMapping("/name/{name}")
    public Banner getByName(@PathVariable @NotBlank String name){
       Banner banner =  bannerService.getByName(name);
       return banner;
    }
```

```java
@Service
public class BannerServiceImpl implements BannerService{
    @Autowired
    private BannerRepository bannerRepository;

    public Banner getByName(String name) {
        Banner banner = bannerRepository.findOneByName(name);
        return banner;
    }
}
```

```java
public interface BannerRepository extends JpaRepository<Banner,Long> {
    Banner findOneById(Long id);
    Banner findOneByName(String name);
}
```

* 调用逻辑

  api --> service --> repository

* 每一层具体作用是啥

  * API  控制层，处理路由
  * Service  服务层，处理业务逻辑
  * Repository  数据处理层， 处理SQL查询语句

* 为什么定义了interface BannerRepository 这个抽象接口和里面的方法，就自动生成查询语句了？

4）在开发环境打印SQL语句

```java
spring:
  jpa:
    hibernate:
      ddl-auto: update
    properties:
      hibernate:
        show_sql: true
        format_sql: true
```



5) 懒加载和急加载

### 8、双向一对多配置

* 为什么要有双向一对多配置？

* 理解双向一对多种种的概念

* 步骤：

  * 步骤一：首先区分出“一端”（Banner）和“多端”(BannerItem)

  * 步骤二：在“一端”（Banner）使用注解@OneToMany

  * 步骤三：在“多端”(BannerItem)， 使用注解@ManyToOne

  * 步骤四：在双向一对多配置中，不需要在创建外建了，系统会自动帮你创建

    ```java
    
        @ManyToOne
        private Banner banner;
    ```

  * 步骤五：在“一端”要使用(mappedBy = "banner"）对应“多端”中的private Banner banner的banner

* 一端

```java
@Entity
public class Banner {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 自增长
    private long id;
    private String name;
    private String description;
    private String img;
    private String title;

    @OneToMany(mappedBy = "banner")
    private List<BannerItem> items; // 导航属性
```

* 多端

```java
@Entity
@Getter
@Setter
public class BannerItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String img;
    private String keyword;
    private Short type;
    private String name;

    private Long bannerId;

    @ManyToOne
    @JoinColumn(name = "bannerId")
    private Banner banner;
```



### 9、多对多配置

* 单向多对多

  ```java
  @Entity
  public class Theme {
      @Id
      private Long id;
      private String title;
      private String name;
  
      @ManyToMany
      @JoinTable(name="theme_spu", joinColumns = @JoinColumn(name = "theme_id"),
      inverseJoinColumns = @JoinColumn(name = "spu_id"))
      private List<Spu> spuList;
  }
  ```

  ```java
  @Entity
  public class Spu {
      @Id
      private Long id;
      private String title;
      private String subtitle;
  
      @ManyToMany
      private List<Theme> themeList;
  }
  ```

* 双向多对多

  * 那一端有mappedBy， 哪一端就是被维护端

  ```java
  @Entity
  public class Theme {
      @Id
      private Long id;
      private String title;
      private String name;
  
      @ManyToMany
      @JoinTable(name="theme_spu", joinColumns = @JoinColumn(name = "theme_id"),
      inverseJoinColumns = @JoinColumn(name = "spu_id"))
      private List<Spu> spuList;
  }
  ```

  ```java
  @Entity
  public class Spu {
      @Id
      private Long id;
      private String title;
      private String subtitle;
  
      @ManyToMany(mappedBy = "spuList")
      private List<Theme> themeList;
  }
  
  ```

### 10、逆向生成Entity



### 11、静态资源的访问方式

### 12、提取Model中的一部分字段dozermapper

```
        <dependency>
            <groupId>com.github.dozermapper</groupId>
            <artifactId>dozer-core</artifactId>
            <version>6.5.0</version>
        </dependency>
```

```java
    @GetMapping("/latest")
    public List<SpuSimplifyVO> getLatestSpuList(){
        Mapper mapper = DozerBeanMapperBuilder.buildDefault();
        List<Spu> spuList = spuService.getLatestPagingSpu();
        List<SpuSimplifyVO> vos = new ArrayList<>();
        spuList.forEach(s->{
            SpuSimplifyVO vo = mapper.map(s, SpuSimplifyVO.class);
            vos.add(vo);
        });
        return vos;
    }
```

### 13、分页

### 14、bo、vo、dto的分层的作用

* vo: 返回数据到前端
* 

### 15、SKU规格的设计

* SPU --> Spec_Key   多对多
* SKU --> Spec_Value 多对多
* Spu --> Sku  一对多

### 16、通用泛型Converter

### 17、JPA自动调用，完成序列化和反序列化过程

