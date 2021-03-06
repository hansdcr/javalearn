package com.hans.demo.learn.threadcoreknowledge.createthreads;

/**
 *  描述： 用Runnable方式创建线程
 */
public class RunnableStyle implements Runnable {
    public static void main(String[] args) {
        Thread thread = new Thread(new RunnableStyle());
        thread.start();
    }


    @Override
    public void run() {
        System.out.println("用runnable方法启动线程");
    }
}
