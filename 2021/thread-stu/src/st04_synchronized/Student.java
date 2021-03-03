package st04_synchronized;

/**
 *  通过synchronized来保障只有一个线程操作共享资源
 */
public class Student implements Runnable{
    private String name;
    private Punishment punishment;

    public Student(String name, Punishment punishment){
        this.name = name;
        this.punishment = punishment;
    }


    private void copyWord(){
        int count = 0;
        // System.out.println("我在被处罚");
        String threadName = Thread.currentThread().getName();

        while (true){
            int leftCopyWordCount = 0;
            synchronized (punishment){
                if(punishment.getCopyWordCount() > 0){
                    leftCopyWordCount = punishment.getCopyWordCount();
                    punishment.setCopyWordCount(leftCopyWordCount-1);
                }
            }
            if (leftCopyWordCount>0){
                // System.out.println("线程"+threadName+"--"+name+"正在抄写单词, 还剩下: "+leftCopyWordCount+"没写完");
            }else {
                break;
            }
            count++;

        }
        System.out.println(name+"共抄写了: "+count+"遍");
    }

    @Override
    public void run(){
        copyWord();
    }
}
