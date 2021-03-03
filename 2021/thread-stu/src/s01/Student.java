package s01;

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


    private void copyWord1(){
        System.out.println("我在被处罚");
        String threadName = Thread.currentThread().getName();
        while (true){
            if(punishment.getCopyWordCount() > 0){
                System.out.println("线程"+threadName+"--"+name+"正在抄写单词, 还剩下: "+leftCount+"没写完");
                punishment.setCopyWordCount(--this.leftCount);
            }else{
                break;
            }
        }
    }

    @Override
    public void run(){
        copyWord1();
    }
}
