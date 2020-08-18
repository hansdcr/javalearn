### 0、重要知识点

* 类是谁的对象？
* 什么是动态加载类？什么是动态加载类？
* 使用动态加载类有什么好处？
* 为什么要有匿名类和匿名函数？可以帮助我们解决什么问题？

### 1、反射

#### 1.1 面向对象中万事万物皆对象

#### 1.2  java中不是对象的类型：静态变量，基本数据类型

#### 1.3  类是谁的对象？



* 类是java.lang.Class类的实力对象

  ```
  class Foo{}
  ```

  这个Foo是不是对象？是谁的对象？

  第一种表示方式

  ```java
  Class c1 = Foo.class;
  ```

  第二种表示方式： 已经知道类的对象的情况下

  ```
  Class c2 = fool.getClass();
  ```

  第三种表达方式:

  ```java
  Class c3 = null;
  c3 = Class.forName("com.hans.reflect.Foo")
  ```

  

* c1和c2是否相等，为什么？

  ```java
  System.out.println(c1==c2);
  System.out.println(c2==c3);
  ```

* 通过类类型（class type）创建类的对象实例

  ```java
  Class Foo = (Foo)c1.newinstance()
  ```

#### 1.4 示例： 动态加载类的好处。通过功能的演进来说明动态加载类的优点。

假设现在需要生产一套office软件，用户点击office就使用office功能；点击excel就使用excel功能。

```
public class Word {
    public void start() {
        System.out.println("我是word");
    }
}
```

```
public class Excel {
    public void start() {
        System.out.println("我是excel");
    }
}
```

```
public class Office {
    public static void main(String[] args) {
        String cmd = args[0];
        System.out.println(cmd);

        if ("Word".equals(cmd)){
            Word word = new Word();
            word.start();
        };


    }
}
```

现在只编译Word和Office, 发现编译的时候报错，这是为什么？

* 因为new Word() 创建对象的时候，是在编译期间做检查
* 我们只编译了Word, 因此在编译Office期间检查不到Excel，所以编译会报错

```
javac Word.java 
javac Office.java
```

* 处理方法： 将Excel也进行编译
* 这个例子意在说明： new创建对象，是静态加载类，在编译时加载

我们希望的是什么样的？ 在使用期间加载，不适用不加载，下面是改进方法：

```java
public class OfficeBetter {
    public static void main(String[] args) {
        try {
            Class c = Class.forName(args[0]);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
```

* 上面的OfficeBetter编译的时候没有报任何错误，
* 但是有一个问题，比如我想使用word这里需要对类型强转，并且不能自动适配word或者excel, 解决方法使用接口

```
package dynamicclass;

public class Excel implements IOffice{
    public void start() {
        System.out.println("我是excel");
    }
}
```

```java
package dynamicclass;

public class Word implements IOffice{
    public void start() {
        System.out.println("我是word");
    }
}

```

```java
package dynamicclass;

public interface IOffice {
    void start();
}
```

```java
public class OfficeBetter{

    public static void main(String[] args) {
        try {
            Class c = Class.forName(args[0]);
            IOffice oa = (IOffice) c.newInstance();
            oa.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
```

#### 1.5 获取类的信息

* getName()
* getMethods()
* getDeclaredMethods()
* getReturnType()
* getParameterTypes()
* getFields()
* getDeclaredFields()
* field,getType()
* fieldType.getName()
* Field.getName()
* getConstructors()

#### 1.6  

### 2、lambda

#### 2.1 函数式接口

* 接口中只有一个抽象方法
* Java8的函数式接口注解： @FunctionInterface
* 函数式接口的抽象方法签名： 函数描述符

#### 2.2  自定义函数式接口

#### 2.3. 方法引用



### 3、流Stream

#### 3.1  流的好处

* 以声明的方式处理集合数据
* 将基础的操作链接起来，完成复杂的数据处理流水线
* 提供透明的并行处理

#### 3.2 流和集合的区别

* 流偏向于计算
* 集合偏向于存储

#### 3.3 流操作的分类

* 中间操作

  a、无状态操作（filter/map/peek/flatMap）

  b、有状态操作 (dictinct/sorted/limit/skip)

* 终端操作

  a、 非短路操作 (forEach/collect/count/reduce/max/min)

  b、短路操作 (anyMatch/findFirst/findAny/allMatch/noneMatch)

### 4、IO流

* IO流的分类
* IO流的四个基类
* 如何读写一个文件
* 为什么要序列化和反序列化

#### 4.1 创建文件

* File类

```java
public class FileDemoTest {
    @Test
    public void createFileTest(){
        String path="/Users/11091752/Desktop/HansDev/javalearn/demo2/src/test/java/com/hans/demo/learn/file/";
        File file = new File(path+"test.txt");
        try {

            boolean mark = file.createNewFile();
            if (mark){
                System.out.println("创建文件成功");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
```

遍历目录下所有的文件

```java
    @Test
    public void printFiles(){
        String path="/Users/11091752/Desktop/HansDev/javalearn/demo2/src/test/java/com/hans/demo/learn";
        this.printFiles(path);
    }

    private void printFiles(String path){
        File file = new File(path);
        File[] files = file.listFiles();
      
        for(int i=0; i< files.length; i++){
            if(files[i].isDirectory()){
                printFiles(files[i].toString());
            } else if(files[i].isFile()){
                System.out.println(JSON.toJSONString(files[i]));
            }
        }
    }
```

### 5、异常

#### 5.1  异常类型

````
                        Throwable
Error																						                Exception
																					RuntimeException 
                                          (非检查异常)								           (检查异常)
                                   NullPointerExcetion....
                             
````

* Error
* 运行时异常 RuntimeException
  * NullPointerException
  * ArrayIndexOutOfBoundsException
  * ClassCastExcetion
  * ArithmeticException
* 检查异常
  * IOException

#### 5.2  异常处理

```
try{}catch(){}
try{}catch(){}finally{}
```

#### 5.3 异常抛出顺序

* 从子类到父类

#### 5.4 自定义异常

* t hrow :  动作 throw new Exception()
* throws :  throws Exception
* 什么样的异常需要向上一级抛出？（自己无能为力，处理不了的）

#### 5.5 Java的异常链



### 6、注解

#### 6.1 注解分类

* JDK自带注解

  @Override

  @Deprecated

  @Suppvisewarnings

* Java中的第三方注解

  @Autowired

  @Service

  @Repository

  @InsertProvider

* 注解分类

  源码注解

  编译注解

  运行注解

  元注解

#### 6.2 自定义注解

##### 6.2.1 自定义注解语法要求

* 注解语法解释

  ```
  
  ```

  

* 自定义注解

```

```

* 使用自定义注解

```

```





### 7、线程

### 8、继承和多态







