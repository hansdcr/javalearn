package dynamicclass;

public class Office {
    public static void main(String[] args) {
        String cmd = args[0];
        System.out.println(cmd);

        if ("Word".equals(cmd)){
            Word word = new Word();
            word.start();
        };


    }
}
