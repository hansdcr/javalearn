package threadcoreknowledge.createthreads;

public class BothRunnableThead {
    public static void main(String[] args) {
        new Thread(new Runnable(){
            @Override
            public void run(){
                System.out.println("我是runnable");
            }
        }){
            public void run(){
                System.out.println("我是thread");
            }
        }.start();
    }
}
