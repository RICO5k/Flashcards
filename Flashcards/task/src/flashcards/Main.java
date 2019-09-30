package flashcards;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        ConsoleInOut consoleInOut = new ConsoleInOut(scanner);

        String importFrom = null;
        String exportTo = null;

        try {
            for(int i=0; i<args.length; i+=2) {
                if(args[i].equals("-import")) {
                    importFrom = args[i+1];
                } else if(args[i].equals("-export")) {
                    exportTo = args[i+1];
                }
            }
        } catch(Exception e) {
            throw new IllegalArgumentException();
        }

        Flashcards flashcards = new Flashcards(consoleInOut, importFrom, exportTo);
        flashcards.run();
    }
}
