### 0、重点知识点

* 一个线程两次调用start()方法会出现什么情况？为什么？
* 既然start()方法会调用run()方法，为什么我们还选择调用start()方法而不是run()方法呢？

### 1、线程8大核心基础知识

* 实现多线程的方法有几种？（1种？2种？4种？）
* 启动线程的正确和错误方法
* 停止线程的正确方法（重要、难点）
* 线程的6个状态（线程的生命周期）
* Thead和Object中和线程相关的重要方法
* 线程各属性
* 线程的未捕获异常该如何处理
* 多线程会导致性能问题（线程引入开销、上下文切换）


### 2、实现多线程的方法

* 实现Runnable接口
* 继承Thread类
* 为什么Runnable方式要优于Thread的创建方式？
  * 解耦
  * 开销小
  * 便于继承

### 3、启动线程的正确和错误方法

1） start()和run()比较

2)	start()原理解读

* 启动新线程检查线程状态
* 加入线程组
* 调用start0()

3）run()原理解读

### 4、让程序停下来

* 使用interrupt来通知，而不是强制停止

1)  使用interrupt停止线程

```java
public class RightWayStopThreadWithoutSleep implements Runnable{
    public static void main(String[] args) {
        Thread thread = new Thread(new RightWayStopThreadWithoutSleep());
        thread.start();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        thread.interrupt(); // 1）使用interrupt通知线程停止
    }

    @Override
    public void run() {
        int num = 0;
        //  2）获取interrupt的状态
        while (!Thread.currentThread().isInterrupted() && num <= Integer.MAX_VALUE / 2){
            if (num % 10000 == 0){
                System.out.println(num+"是10000的倍数");
            }
            num++;
        }
        System.out.println("任务运行结束了");

    }
}
```

2）线程被阻塞时该如何停止线程

* 一个线程执行任务时，假如有阻塞的功能，比如睡眠1秒，这个时候在500毫秒的时候收到其他线程的interrup信号，该如何处理？

```java
public class RightWayStopThreadWithSleep {
    public static void main(String[] args) throws InterruptedException {
        Runnable runnable = () -> {
            int num = 0;
            while (!Thread.currentThread().isInterrupted() && num <= 300){
                if(num % 100 == 0){
                    System.out.println(num+"是100的倍数");
                }
                num++;
            }
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        };


        Thread thread = new Thread(runnable);
        thread.start();
        Thread.sleep(500);
        thread.interrupt();

    }
}
```

* 如何阻塞出现在每次循环中，则不需要做interrupt判断，因为在阻塞处会被判断

```java
public class RightWayStopThreadWithSleep {
    public static void main(String[] args) throws InterruptedException {
        Runnable runnable = () -> {
            int num = 0;
            try {
                while (num <= 300) {
                    if (num % 100 == 0) {
                        System.out.println(num + "是100的倍数");
                    }
                    num++;
                }
                Thread.sleep(1000);
            } catch (InterruptedException e){
                e.printStackTrace();
            }
        };


        Thread thread = new Thread(runnable);
        thread.start();
        Thread.sleep(500);
        thread.interrupt();

    }
}
```

* while内使用try/catch， 程序不会中断

  原因是sleep触发一次try/catch后，interrupt标记位会被清除

```java
public class CantNotInterrup{
    public static void main(String[] args) throws InterruptedException {
        Runnable runnable = () -> {
            int num = 0;
            while (!Thread.currentThread().isInterrupted() && num <= 1000){
                if(num % 100 == 0){
                    System.out.println(num+"是100的倍数");
                }
                num++;
                // while循环内使用try/catch不会中断
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };

        Thread thread = new Thread(runnable);
        thread.start();
        Thread.sleep(5000);
        thread.interrupt();
    }
}
```

3） 开发中的最佳实践

* 优先选择：传递中断
* 不想或者无法传递： 恢复中断
* 不应屏蔽中断



优先选择：传递中断

```java
public class RightWayStopThreadInProd implements Runnable {
    public static void main(String[] args) throws InterruptedException {
        Thread thread = new Thread(new RightWayStopThreadInProd());
        thread.start();
        Thread.sleep(500);
        thread.interrupt();
    }

    @Override
    public void run() {
        try {
            while (true) {
                System.out.println("go");
                throwInMethod();
            }
        }catch (InterruptedException e){
            e.printStackTrace();
        }
    }

    public void throwInMethod() throws InterruptedException{ // 不要用try/catch, 要在方法签名中抛出，又调用方处理
        Thread.sleep(1000);
    }
}
```

恢复中断

* catch子句中调用Thread.currentTread().interrupt()来恢复设置中断状态，以便在后续的执行中，依然能够检查到刚才发生了中断，回到刚才的 RightWayStopThreadInProd类，补上中断，让他跳出

```
public class RightWayStopThreadInProd2 implements Runnable {
    public static void main(String[] args) throws InterruptedException {
        Thread thread = new Thread(new RightWayStopThreadInProd2());
        thread.start();
        Thread.sleep(500);
        thread.interrupt();
    }

    @Override
    public void run() {
        while (true) {
            if (Thread.currentThread().isInterrupted()){ // 对中断状态进行判断
                System.out.println("Interruped, 程序被中断，运行结束");
                break;
            }
            reInterrupt();
            System.out.println("go");
        }
    }
    public void reInterrupt(){
    	  /**
    	  *。 这里自己使用try/catch处理了异常，同时在catch中把异常抛出了，这样调用他的人可以去判断这个中断
    	  */
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt(); // 恢复中断
            e.printStackTrace();
        }
    }
}
```



具有响应中断能力的方法列表

* Object.wait()/Object.wait(Long)/Object.wait(int)
* Thread.sleep(long)/Thread.sleep(long, int)
* Thread.join()/Thread.join(long)/Thread.join(long, int)
* java.util.concurrent.BlockQueue.take()/put(E)
* java.util.concurrent.locks.Lock.lockInterruptibly()
* java.util.concurrent.CountDownLatch.await()
* java.util.concurrent.CyclicBarrier.await()
* java.util.concurrent.Exchanger.exchange(V)
* java.nio.channels.InterruptibleChannel的相关方法
* java.nio.channels.Selector的相关方法

### 5、线程的生命周期

* New
* Runnable
* Blocked
* Waiting
* Timed Waiting
* Terminated



### 100、思考

* 同时使用Runnable和 Thead方式创建线程会怎么样？

  下面的代码：先输出哪一行？为什么？

```java
public class BothRunnableThead {
    public static void main(String[] args) {
        new Thread(new Runnable(){
            @Override
            public void run(){
                System.out.println("我是runnable");
            }
        }){
            public void run(){
                System.out.println("我是thread");
            }
        }.start();
    }
}
```

* 如何了解技术领域的最新动态

  1）高质量固定途径：ohmyrss.com

  2）InfoQ

*  如何在业务开发中成长

  1）偏业务方向

  2）偏技术方向

  3）25%理论

*  能力是啥？

    宏观上：

  * 能力不是工作年限
  * 有责任心，不放过任何bug 找到原因，去解决，去提高
  * 主动：不断复盘，重构，优化，学习，总结
  * 敢于承担：敢于挑战技术难题
  * 关心产品、关心业务，而不仅仅是代码

   微观上

  * 看经典书籍（Java并发编程实践）
  * 看官方文档
  * Google stackoverflow
  * 自己动手写demo, 并运用到项目中
  * 学习开源项目，分析源码

  

  