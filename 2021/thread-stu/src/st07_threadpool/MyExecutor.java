package st07_threadpool;

import java.util.ArrayList;
import java.util.List;

public class MyExecutor {
    private final int poolSize;
    private final List<Thread> threadPool = new ArrayList<>();
    private final RunnableQueueTask runnableTaskQueue;

    public MyExecutor(int poolSize){
        this.poolSize = poolSize;
        this.runnableTaskQueue = new RunnableQueueTask();

        initThreadPool();

    }

    public void initThreadPool(){
        // 生成poolSize大小的线程池
        for(int i=0; i<=poolSize;i++){
            initThread();
        }
    }

    public void initThread(){
        if(threadPool.size() <= poolSize){
            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    while (true) {
                        Runnable task = null;
                        try {
                            task = runnableTaskQueue.getTask();
                            task.run();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            });
            threadPool.add(thread);
            thread.start();
        }
    }

    public void execute(Runnable runnable){
        runnableTaskQueue.addTask(runnable);
    }
}
