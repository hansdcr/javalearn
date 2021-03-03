package st02;

/**
 *  通过继承Thread的方式启动多个线程
 *  问题：
 *  虽然启动了多个线程，但是线程并没有协同工作，而是每个人都抄写了100遍
 */
public class Student extends Thread {
    private String name;
    private Punishment punishment;
    private int leftCount;

    public Student(String name, Punishment punishment){
        super(name);
        this.name = name;
        this.punishment = punishment;
        this.leftCount = punishment.getCopyWordCount();
    }


    private void copyWord(){
        int count = 0;
        System.out.println("我在被处罚");
        String threadName = Thread.currentThread().getName();

        while (true){
            if(punishment.getCopyWordCount() > 0){

                System.out.println("线程"+threadName+"--"+name+"正在抄写单词, 还剩下: "+leftCount+"没写完");
                punishment.setCopyWordCount(--this.leftCount);
                count++;
            }else{
                break;
            }
        }

        System.out.println(name+"共抄写了: "+count+"遍");
    }

    @Override
    public void run(){
        copyWord();
    }
}
