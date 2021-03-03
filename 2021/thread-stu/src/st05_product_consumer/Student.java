package st05_product_consumer;

import java.util.LinkedList;

public class Student extends Thread {
    private String name;
    private LinkedList<Task> tasks;

    public Student(String name, LinkedList<Task> tasks){
        super(name);
        this.name = name;
        this.tasks = tasks;
    }

    public void copyWord() throws InterruptedException {
        String threadName = Thread.currentThread().getName();

        while (true){
            Task task = null;

            synchronized (tasks){
                if(tasks.size()>0){
                    task = tasks.removeFirst();
                    sleep(100);
                    tasks.notifyAll();
                }else{
                    System.out.println(threadName+"开始等待");
                    tasks.wait();
                    System.out.println("学生线程 "+threadName + "线程-" + name + "等待结束");
                }
            }

            if(task!=null){
                for(int i=1;i<task.getCopyCount();i++){
                    System.out.println(threadName + "线程-" + name + "抄写" + task.getWord() + "。已经抄写了" + i + "次");
                }
            }

        }
    }

    public void run(){
        try {
            copyWord();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
