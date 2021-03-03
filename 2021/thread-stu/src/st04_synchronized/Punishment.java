package st04_synchronized;

public class Punishment {
    private String word;
    private int copyWordCount = 100;

    public Punishment(String word, int copyWordCount) {
        this.word = word;
        this.copyWordCount = copyWordCount;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public int getCopyWordCount() {
        return copyWordCount;
    }

    public void setCopyWordCount(int copyWordCount) {
        this.copyWordCount = copyWordCount;
    }
}
