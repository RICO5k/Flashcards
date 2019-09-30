package flashcards;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        ConsoleInOut consoleInOut = new ConsoleInOut(scanner);

        Flashcards flashcards = new Flashcards(consoleInOut);
        flashcards.run();
    }
}
