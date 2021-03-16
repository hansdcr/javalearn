package com.hans.threadDemo;

import javax.sound.midi.Sequence;
import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

public class Thread1115 {
    private int n;
    private AtomicBoolean fooStatus = new AtomicBoolean(false);
    private AtomicBoolean barStatus = new AtomicBoolean(false);

    private Semaphore foo = new Semaphore(0);
    private Semaphore bar = new Semaphore(1);

    public Thread1115(){}

    public void foo(Runnable printFoo){
        for(int i=0;i<n;i++){
            try {
                foo.acquire();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if(!fooStatus.get()){
                printFoo.run();
                fooStatus.set(true);
                barStatus.set(false);
            }
        }
    }

    public void bar(Runnable printBar){
        for(int i=0; i<n; i++){
            if(fooStatus.get()){
                printBar.run();
                barStatus.set(true);
                fooStatus.set(false);
            }
        }
    }

    public static void main(String[] args) {
//        Thread1115 thread1115 = new Thread1115();
//        Thread threadFoo = thread1115.foo(new Runnable() {
//            @Override
//            public void run() {
//
//            }
//        });
    }
}
