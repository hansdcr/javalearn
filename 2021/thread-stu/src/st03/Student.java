package st03;

/**
 * 1、 改为实现Runnable接口
 * 问题：
 * 1） 通过Runnable接口和继承Thread的区别
 * 2） 通过判断来确保更新时数据的正确，这种方法实际没有作用
 *                 if (leftCopyWord < punishment.getCopyWordCount()){
 *                     punishment.setCopyWordCount(leftCopyWord);
 *                 }
 *
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
            if(punishment.getCopyWordCount() > 0){
                int leftCopyWord = punishment.getCopyWordCount();
                leftCopyWord--;
                if (leftCopyWord < punishment.getCopyWordCount()){
                    punishment.setCopyWordCount(leftCopyWord);
                }
                //System.out.println("线程"+threadName+"--"+name+"正在抄写单词, 还剩下: "+leftCopyWord+"没写完");
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
