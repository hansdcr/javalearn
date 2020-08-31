### MySQL语法基础

#### 1.1  数据的插入

* 创建一张表

```mssql
CREATE TABLE `imc_class` (
`class_id` SMALLINT(5) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '课程分类id',
`class_name` VARCHAR(10) NOT NULL DEFAULT '' COMMENT '分类名称',
`add_time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '添加时间',
PRIMARY KEY (`class_id`)
) ENGINE=INNODB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8 COMMENT="课程分类"

```

* 向imc_class表的class_name列添加多个课程数据

```mysql
use missyou;
INSERT INTO imc_class (class_name) VALUES ('Redis'),('MYSQL'),('MoogoDB'),('Oracle')
```





