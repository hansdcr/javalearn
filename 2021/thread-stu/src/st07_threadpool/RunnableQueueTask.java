package st07_threadpool;

import java.util.LinkedList;

public class RunnableQueueTask {
    private final LinkedList<Runnable> tasks = new LinkedList<>();

    public RunnableQueueTask(){}

    public void addTask(Runnable runnable){
        synchronized (tasks){
            tasks.add(runnable);
            tasks.notifyAll();
        }
    }

    public Runnable getTask() throws InterruptedException {
        synchronized (tasks){
            while (tasks.isEmpty()){
                System.out.println(Thread.currentThread().getName() + " says task queue is empty. i will wait");
                tasks.wait();
            }
            return tasks.removeFirst();
        }
    }

}
