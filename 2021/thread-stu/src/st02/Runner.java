package st02;

import st02.Punishment;
import st02.Student;

public class Runner {
    public static void main(String[] args) {
        Punishment punishment = new Punishment("internationalization", 100);
        Student student = new Student("小明", punishment);
        student.start();


        Student student2 = new Student("小王", punishment);
        student2.start();

        Student student3 = new Student("小张", punishment);
        student3.start();

        System.out.println("main thread is finished. another thread will finish the punishment");
    }
}
