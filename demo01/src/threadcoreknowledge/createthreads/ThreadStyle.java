package threadcoreknowledge.createthreads;

/**
 * 描述： 用Thread方式创建线程
 */
public class ThreadStyle extends Thread{
    public static void main(String[] args) {
        new ThreadStyle().start();
    }

    @Override
    public void run() {
        System.out.println("用thread方法启动线程");
    }
}
