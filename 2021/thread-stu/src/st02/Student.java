package st02;

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
