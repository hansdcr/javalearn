package st05_product_consumer;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class Teacher extends Thread {
    private String name;
    private List<String> punishWords = Arrays.asList("internationalization", "hedgehog", "penicillin", "oasis", "nirvana", "miserable");
    private LinkedList<Task> tasks;
    private int MAX = 10;

    public Teacher(String name, LinkedList<Task> tasks){
        super(name);
        this.name = name;
        this.tasks = tasks;
    }

    private void arrangePunishment() throws InterruptedException {
        String threadName = Thread.currentThread().getName();

        while (true){
            synchronized (tasks){
                if(tasks.size() < MAX){
                    Task task = new Task(getPunishedWord(), new Random().nextInt(3)+1);
                    tasks.add(task);
                    System.out.println(threadName + "留了作业，抄写" + task.getWord() + " " + task.getCopyCount() + "次");
                    tasks.notifyAll();
                }else {
                    System.out.println(threadName+"开始等待");
                    tasks.wait();
                    System.out.println("teacher线程 " + threadName + "线程-" + name + "等待结束");
                }
            }
        }
    }

    private String getPunishedWord() {
        return punishWords.get(new Random().nextInt(punishWords.size()));
    }

    @Override
    public void run(){
        try {
            arrangePunishment();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
