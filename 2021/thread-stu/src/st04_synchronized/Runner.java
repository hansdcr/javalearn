package st04_synchronized;

public class Runner {
    public static void main(String[] args) {
        Punishment punishment = new Punishment("hello", 1000);
        Thread xiaomin = new Thread(new Student("小明", punishment));
        xiaomin.start();

        Thread xiaowang = new Thread(new Student("小王", punishment));
        xiaowang.start();

        Thread xiaozhang = new Thread(new Student("小张", punishment));
        xiaozhang.start();
    }
}
