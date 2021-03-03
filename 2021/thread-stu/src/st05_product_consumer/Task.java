package st05_product_consumer;

public class Task {
    private String word;
    private int copyCount;

    public Task(String word, int copyCount){
        this.word = word;
        this.copyCount = copyCount;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public int getCopyCount() {
        return copyCount;
    }

    public void setCopyCount(int copyCount) {
        this.copyCount = copyCount;
    }
}
