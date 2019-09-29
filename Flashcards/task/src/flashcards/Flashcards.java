package flashcards;

import java.io.File;
import java.util.Scanner;

public class Flashcards {

    private Scanner scanner;

    private CardsDB cards;
    private boolean running;

    public Flashcards(Scanner scanner ) {
        this.scanner = scanner;
        this.cards = new CardsDB();
    }

    public void run() {
        this.running = true;

        while(this.running) {
            System.out.println("Input the action (add, remove, import, export, ask, exit):");
            Actions action = getActionFromUser();
            executeAction(action);
            System.out.println();
        }
    }

    private void executeAction(Actions action) {
        switch(action) {
            case ADD: addCard(); break;
            case REMOVE: removeCard(); break;
            case IMPORT: importCardsFromFile(); break;
            case EXPORT: exportCardsToFile(); break;
            case ASK: ask(); break;
            case EXIT: exit(); break;
            default:
        }
    }

    private void exit() {
        System.out.println("Bye bye!");
        this.running = false;
    }

    private void ask() {
        System.out.println("How many times to ask?");
        int reps = Integer.parseInt(scanner.nextLine());

        if(cards.size() > 0) {
            for (int i = 0; i < reps; i++) {
                String cardDescription = cards.getRandomCard();
                System.out.println(cardDescription);
                String answer = scanner.nextLine();
                if (answer.equals(cards.getCardDef(cardDescription))) {
                    System.out.println("Correct answer.");
                } else {
                    System.out.println("Wrong answer.");
                }
            }
        } else {
            System.out.println("Cards database is empty!");
        }
    }

    private void exportCardsToFile() {
        System.out.println("File name:");
        String fileName = scanner.nextLine();
        fileName += ".txt";

        File file = new File(fileName);

        try {
            cards.exportToFile(file);
        } catch(Exception e) {
            System.out.println("Something went wrong!");
        }
    }

    private void importCardsFromFile() {
        System.out.println("File name:");
        String fileName = scanner.nextLine();
        fileName += ".txt";

        File file = new File(fileName);

        if(!file.exists()) {
            System.out.println("File not found.");
            return;
        }

        try {
            cards.importFromFile(file);
        } catch(Exception e) {
            System.out.println("Something went wrong!");
        }
    }

    private void removeCard() {
        System.out.println("The card:");
        String definition = scanner.nextLine();

        if(cards.containsCardDefinition(definition)) {
            System.out.println("Can't remove \"" + definition + "\": there is no such card.");
            return;
        }

        cards.removeCard(definition);
    }

    private void addCard() {
        System.out.println("The card:");
        String description = scanner.nextLine();

        if(cards.containsCardDescription(description)) {
            System.out.println("The card \"" + description + "\" already exists.");
            return;
        }

        System.out.println("The definition of the card:");
        String definition = scanner.nextLine();

        if(cards.containsCardDefinition(definition)) {
            System.out.println("The definition \"" + definition + "\" already exists.");
            return;
        }

        cards.addCard(description, definition);
    }

    private Actions getActionFromUser() {
        String input = scanner.nextLine().toUpperCase();

        Actions action = null;

        try {
            action = Actions.valueOf(input);
        } catch(Exception e) {
            action = Actions.UNSUPPORTED;
        }

        return action;
    }
}
