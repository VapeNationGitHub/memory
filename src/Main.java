import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class Main {

    private static List<Box> boxes;
    static BufferedReader br;

    public static void main(String[] args) throws IOException {
        br = new BufferedReader(new InputStreamReader(System.in));

        boxes = new ArrayList<>();
        int intervals[] = {0,1,2}; //первый бокс будет спрашивать свои слова каждый день, второй - через день, третий - через два дня.
        for (int interval: intervals) {
            boxes.add(new Box(interval));
        }

        boxes.get(0).addWords( new Word[]{new Word("cat", "кошка"), new Word("dog", "собака"), new Word("room", "комната")});

        while (true) {
            System.out.println("--- \n Новый день \n ---");
            showBoxes();  //показываем, что в данный момент находится в боксах (для наглядности)
            checkWords(); //проверяем знание слов
            for (Box box: boxes) {
                box.unmarkNewWords(); //отмечаем все слова как не новые. слова отмечаются как новые, когда перемещаются в следующий бокс, чтобы слово не показывалось два раза за день.
            }
            if(checkForExit()) { //спрашиваем пользователя, выйти или продолжить симуляцию
                break;
            }
        }
    }

    private static void showBoxes() {  //показывает содержимое боксов
        for(Box box: boxes) {
            System.out.println("Box " + boxes.indexOf(box) + ":");
            for(Word word: box.getWords()) {
                System.out.println(word.word);
            }
        }
    }

    private static void checkWords() throws IOException {  //проверяет знание слов
        System.out.println();
        for(Box box: boxes) {
            List<Word> words = box.checkWords(); //у каждого бокса получаем список слов для проверки
            if (words != null) { //если null, значит слова в этот день проверять не надо
                List<Word> toDelete = new ArrayList<>();
                for (Word word : words) {
                    if (word.newToTheBox) {
                        continue;
                    }
                    boolean res = checkWord(word); //для каждого слова вызываем метод checkWord, который проверяет знание слова пользователем. результат обрабатывается
                    if (res) {
                        word.repeatsNumber--;
                        if (word.repeatsNumber < 1) {
                            putInTheNextBox(box, word); //помещаем в следующий бокс, если пользователь правильно перевел слово нужное количество раз в этом боксе
                            toDelete.add(word);
                        }
                    } else {
                        putInThePreviousBox(box, word); //помещаем в предыдущий бокс, если пользователь неправильно перевел слово.
                        if (boxes.indexOf(box) > 0) {
                            toDelete.add(word);
                        }
                    }
                    System.out.println();
                }
                for (Word word: toDelete) {
                    box.deleteWord(word);
                }
            }
        }
    }

    private static void putInThePreviousBox(Box box, Word word) { //помещает слово из указанного бокса в предыдущий
        int index = boxes.indexOf(box);
        if (index > 0) {
            boxes.get(index - 1).addWord(word);
            word.newToTheBox = true;
        }
        word.refreshRepeatsNumber();
    }

    private static void putInTheNextBox(Box box, Word word) { //помещает слово из указанного бокса в следующий
        int index = boxes.indexOf(box);
        if (index < boxes.size() - 1) {
            boxes.get(index + 1).addWord(word);
            word.refreshRepeatsNumber();
            word.newToTheBox = true;
        }
    }

    private static boolean checkWord(Word word) throws IOException { //проверяет знание пользователем перевода слова
        System.out.println("Слово: " + word.word  + ". Введите перевод:");
        String line = br.readLine();
        if (line.toLowerCase().trim().equals(word.translation)) {
            System.out.println("Правильно!");
            return true;
        } else {
            System.out.println("Неправильно, правильный перевод: " + word.translation);
            return false;
        }
    }

    private static boolean checkForExit() throws IOException { //проверяет, хочет ли пользователь выйти из приложения
        System.out.println("Для выхода введите n, для продолжения что-либо другое.");
        String line = br.readLine();
        if (line.equals("n")) {
            return true;
        }
        return false;
    }
}
