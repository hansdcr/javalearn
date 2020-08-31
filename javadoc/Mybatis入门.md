###   Mybatis入门

#### 1.1  基本配置和使用流程

第一步：配置数据库连接 

```java
spring:
  # 数据源配置，请修改为你项目的实际配置
  datasource:
    driver-class-name: com.mysql.cj.jdbc.Driver
    password:xx
    url: jdbc:mysql://localhost:3306/missyou?useSSL=false&serverTimezone=UTC&characterEncoding=UTF8
    username: xx
```

第二步：Mapper.xml 配置

* <mapper namespace="io.github.talelin.latticy.mapper.BannerMapper">

  关联BannerMapper接口

* <resultMap id="BaseResultMap" type="io.github.talelin.latticy.model.BannerDO">

  关联BannerDO模型

* select id="getAllBanners"

  关联接口BannerMapper中的方法getAllBanners

```java
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN" "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
<mapper namespace="io.github.talelin.latticy.mapper.BannerMapper">
    <resultMap id="BaseResultMap" type="io.github.talelin.latticy.model.BannerDO">
        <id column="id" property="id"/>
        <result column="image" property="image"/>
        <result column="type"  property="type"/>
        <result column="create_time"  property="createTime"/>
        <result column="update_time" property="updateTime"/>
        <result column="delete_time"  property="deleteTime"/>
        <result column="title"  property="title"/>
        <result column="name" property="name"/>
    </resultMap>
    <select id="getAllBanners" resultMap="BaseResultMap">
        SELECT *
        FROM banner
    </select>
</mapper>
```

第三步：模型定义

```java
@Getter
@Setter
public class BannerDO implements Serializable {
        private Long id;
        private String name;
        private String title;
        private String img;
        private String type;
        private Date createTime;
        private Date updateTime;
        private Date deleteTime;
}
```

第四步：Mapper接口定义

```java
@Repository
public interface BannerMapper {
    List<BannerDO> getAllBanners();
}
```

第五步：service定义

```java
@Service
public class BannerService {
    @Autowired
    private BannerMapper bannerMapper;

    public List<BannerDO> findAllBanners(){
        return bannerMapper.getAllBanners();
    }
}
```

第六步：controller中使用

```java
@RestController
@RequestMapping("/v1/banner")
public class TestSleeveController {
    @Autowired
    private BannerService bannerService;

    @GetMapping("/test/test1")
    public List<BannerDO> test(){
        return bannerService.findAllBanners();
    }
}
```

#### 1.3 数据库的查询

#### 1.4 数据库的插入

* service

```java
@Service
public class BannerService {
    @Autowired
    private BannerMapper bannerMapper;

    public List<BannerDO> findAllBanners(){
        return bannerMapper.getAllBanners();
    }
    public long insertBanner(){
        BannerDO bannerDO = new BannerDO();
        bannerDO.setName("newBanner");
        bannerDO.setTitle("newBannerTtile");

        bannerMapper.insertBanner(bannerDO);
        return bannerDO.getId();

    }
}
```

* mapper

```java
@Repository
public interface BannerMapper {
    List<BannerDO> getAllBanners();

    long insertBanner(BannerDO bannerDO);
}
```

* mapper.xml

  useGeneratedKeys

  keyProperty

```java
    <insert id="insertBanner" useGeneratedKeys="true" keyProperty="id" parameterType="io.github.talelin.latticy.model.BannerDO">
        INSERT INTO banner(name, title)
        VALUES (#{name}, #{title})
    </insert>
```

* controller

```java
@RestController
@RequestMapping("/v1/banner")
public class TestSleeveController {
    @Autowired
    private BannerService bannerService;

    @GetMapping("/test/test2")
    public long test2(){
        return bannerService.insertBanner();
    }
}
```

#### 1.5  数据的更新

* 路由层
  * 请求体校验
  * 参数校验
  * 通过id更新数据
  * 返回更新结果

```java
    @PutMapping("/{id}")
    public UpdatedVO update(@RequestBody @Validated BannerDTO dto,
                       @PathVariable @Positive Long id){
        BannerDTO bannerDTO = dto;
        bannerService.update(bannerDTO, id);
        return new UpdatedVO();
    }
```

* Service层

```java
    public void update(BannerDTO dto, Long id){
        BannerDO bannerDO = this.getById(id);
        if (bannerDO == null){
            throw new NotFoundException();
        }
        BeanUtils.copyProperties(dto, bannerDO);
        this.updateById(bannerDO); 
    }
```

* VO层

```java
public class UpdatedVO<T> extends UnifyResponseVO {

    public UpdatedVO() {
        super(Code.UPDATED.getCode());
        ResponseUtil.setCurrentResponseHttpStatus(HttpStatus.CREATED.value());
    }

    public UpdatedVO(int code) {
        super(code);
        ResponseUtil.setCurrentResponseHttpStatus(HttpStatus.CREATED.value());
    }

    public UpdatedVO(T message) {
        super(message);
        ResponseUtil.setCurrentResponseHttpStatus(HttpStatus.CREATED.value());
    }

    public UpdatedVO(int code, T message) {
        super(code, message);
        ResponseUtil.setCurrentResponseHttpStatus(HttpStatus.CREATED.value());
    }

}
```



#### 1.6 数据的删除

* Controller

```java
    @DeleteMapping("/{id}")
    public DeletedVO delete(@PathVariable @Positive Long id){
        bannerService.delete(id);
        return new DeletedVO();
    }
```

* Service

```java
    public void delete(Long id){
        BannerDO bannerDO = this.getById(id);
        if (bannerDO == null){
            throw new NotFoundException();
        }
        this.bannerMapper.deleteById(id);
    }
```

* VO

```java
public class DeletedVO<T> extends UnifyResponseVO {

    public DeletedVO() {
        super(Code.DELETED.getCode());
        ResponseUtil.setCurrentResponseHttpStatus(HttpStatus.CREATED.value());
    }

    public DeletedVO(int code) {
        super(code);
        ResponseUtil.setCurrentResponseHttpStatus(HttpStatus.CREATED.value());
    }

    public DeletedVO(T message) {
        super(message);
        ResponseUtil.setCurrentResponseHttpStatus(HttpStatus.CREATED.value());
    }

    public DeletedVO(int code, T message) {
        super(code, message);
        ResponseUtil.setCurrentResponseHttpStatus(HttpStatus.CREATED.value());
    }

}
```



#### 1.7 Mybatis-plus简单使用

```java
    @GetMapping("/test/test3")
    public List<BannerDO> test3(){
        bannerMapper.selectList(null); // mybatis-plus测试
        return bannerService.findAllBanners();
    }
```

* 继承了BaseMapper，同时关联model对象

```java
@Repository
public interface BannerMapper extends BaseMapper<BannerDO> {
    List<BannerDO> getAllBanners();

    long insertBanner(BannerDO bannerDO);
}
```

* 使用@TableName使模型关联真正的数据库表名

```java
@Getter
@Setter
@TableName("banner")
public class BannerDO implements Serializable {
        private Long id;
        private String name;
        private String title;
        private String img;
        // private String type;
        private Date createTime;
        private Date updateTime;
        private Date deleteTime;
}
```

#### 1.8 mybatis设置分页

* 请求url

```java
http://localhost:5000/v1/banner/page?page=0&count=1
```

* 请求的返回值

```java
{
    "total": 5,
    "items": [
        {
            "id": 1,
            "name": "b-1",
            "title": null,
            "img": null,
            "create_time": "2019-07-28T04:47:15.000+0000",
            "update_time": "2019-08-04T01:03:16.000+0000",
            "delete_time": null
        }
    ],
    "page": 0,
    "count": 1
}
```

* 路由层
  * bannerService.getBaseMapper().selectPage 注意此处的用法
  * return new PageResponseVO<BannerDO> 注意此处的用法
  * @RequestParam 注意此处的用法
  * new Page<>(page, count) 这是lin-cms自定义的分页类，为啥要自己定义下Page？多做了哪些事？

```java
    @GetMapping("/page")
    public PageResponseVO<BannerDO> getBanners(
      @RequestParam(required = false, defaultValue = "0") @Min(value = 0) Integer page,
      @RequestParam(required = false, defaultValue = "10") @Max(value = 20) Integer count){

        Page<BannerDO> pager = new Page<>(page, count);
        IPage<BannerDO> paging = bannerService.getBaseMapper().selectPage(pager, null);

        return new PageResponseVO<BannerDO>(
                paging.getTotal(),
                paging.getRecords(),
                paging.getCurrent(),
                paging.getSize());
    }
```

*  注意此处的用法，继承ServiceImpl， 并且关联BannerMapper和BannerDO, 这样就可以不用写任何注入代码就可以直接使用BannerMapper中的方法

```java
@Service
public class BannerService extends ServiceImpl<BannerMapper, BannerDO> {
}
```





