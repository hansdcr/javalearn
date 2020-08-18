package com.hans.demo.learn.threadcoreknowledge.stopthread;

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
            if (Thread.currentThread().isInterrupted()){
                System.out.println("Interruped, 程序被中断，运行结束");
                break;
            }
            reInterrupt();
            System.out.println("go");
        }
    }
    public void reInterrupt(){
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            e.printStackTrace();
        }
    }
}
