
public class Word {

    public String word;
    public String translation;
    public int repeatsNumber;
    public int maxRepeatsNumber;
    public boolean newToTheBox;

    public Word(String word, String translation) {
        this.word = word;
        this.translation = translation;
        maxRepeatsNumber = 1;
        refreshRepeatsNumber();
        newToTheBox = false;
    }

    public void refreshRepeatsNumber() {
        repeatsNumber = maxRepeatsNumber;
    } //обновляет количество показов слова. вызывается при перемещении в другой бокс

}
