package flashcards;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ConsoleInOut {
    List<String> log;
    Scanner scanner;

    public ConsoleInOut(Scanner scanner) {
        this.log = new ArrayList<>();
        this.scanner = scanner;
    }

    public String readLine() {
        String text = scanner.nextLine();
        addToLog(text);

        return text;
    }

    public void println(String text) {
        addToLog(text);
        System.out.println(text);
    }

    public List<String> getLog() {
        return this.log;
    }

    private void addToLog(String text) {
        log.add(text);
    }
}
