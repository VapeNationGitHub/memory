import java.util.ArrayList;
import java.util.List;

public class Box {

    private int interval;
    private int daysPassed;

    public List<Word> words;

    public Box(int interval) {
        this.interval = interval;
        words = new ArrayList<>();
        daysPassed = interval;
    }

    public void addWord(Word word) {
        words.add(word);
    }

    public void addWords(Word[] newWords) {
        for (Word word : newWords) {
            addWord(word);
        }
    }

    public void deleteWord(Word word) {
        words.remove(word);
    }

    public List<Word> getWords() {
        return words;
    }

    public List<Word> checkWords() { // возвращает слова для проверки пользователя, если нужное число дней прошло
        daysPassed++;
        if (daysPassed > interval){
            daysPassed = 0;
            return words;
        }
        return null;
    }

    public void unmarkNewWords() {
        for (Word word: words) {
            word.newToTheBox = false;
        }
    }

}
