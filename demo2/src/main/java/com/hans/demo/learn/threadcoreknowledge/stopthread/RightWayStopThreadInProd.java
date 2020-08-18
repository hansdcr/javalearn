package com.hans.demo.learn.threadcoreknowledge.stopthread;

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
