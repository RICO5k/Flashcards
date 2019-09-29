package flashcards;

import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Flashcards {

    private Scanner scanner;

    private List<String> log;
    private CardsDB cards;
    private boolean running;

    public Flashcards(Scanner scanner ) {
        this.scanner = scanner;
        this.log = new ArrayList<>();
        this.cards = new CardsDB();
    }

    public void run() {
        this.running = true;

        while(this.running) {
            System.out.println("Input the action (add, remove, import, export, ask, exit, log, hardest card, reset stats):");
            Actions action = getActionFromUser();
            executeAction(action);
            System.out.println();
        }
    }

    private Actions getActionFromUser() {
        String input = scanner.nextLine().toUpperCase();

        Actions action = null;

        try {
            input = input.split("\\s+")[0];
            action = Actions.valueOf(input);
        } catch(Exception e) {
            action = Actions.UNSUPPORTED;
        }

        return action;
    }

    private void executeAction(Actions action) {
        switch(action) {
            case ADD: addCard(); break;
            case REMOVE: removeCard(); break;
            case IMPORT: importCardsFromFile(); break;
            case EXPORT: exportCardsToFile(); break;
            case ASK: ask(); break;
            case LOG: saveLog(); break;
            case HARDEST: printHardestCard(); break;
            case RESET: resetStats(); break;
            case EXIT: exit(); break;
            default:
        }
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

        Card newCard = new Card(description, definition);

        cards.addCard(newCard);
    }

    private void removeCard() {
        System.out.println("The card:");
        String description = scanner.nextLine();

        cards.removeCardByDescription(description);
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

    private void ask() {
        System.out.println("How many times to ask?");
        int reps = Integer.parseInt(scanner.nextLine());

        if(cards.size() > 0) {
            for (int i = 0; i < reps; i++) {
                Card card = cards.getRandomCard();

                System.out.println("Print the definition of \"" + card.getDescription() + "\":");
                String answer = scanner.nextLine();
                String correctAnswer = card.getDefinition();

                if (answer.equals(correctAnswer)) {
                    System.out.println("Correct answer.");
                } else if(cards.containsCardDefinition(answer)) {
                    card.mistake();
                    System.out.println("Wrong answer. The correct one is \"" + correctAnswer + "\", you've just written the definition of \"" + cards.getCardByDefinition(answer) + "\"");
                } else {
                    card.mistake();
                    System.out.println("Wrong answer. The correct one is \"" + correctAnswer + "\".");
                }
            }
        } else {
            System.out.println("Cards database is empty!");
        }
    }

    private void saveLog() {
        System.out.println("File name: ");
        String fileName = scanner.nextLine();
        fileName += ".txt";

        File file = new File(fileName);

        try(FileWriter writer = new FileWriter(file)) {
            for(String line : this.log) {
                writer.append(line);
            }
            System.out.println("The log has been saved.");
        } catch(Exception e) {
            System.out.println("Something went wrong!");
        }
    }

    private void printHardestCard() {
        List<Card> hardestCards = cards.getHardestCards();
        if(hardestCards.isEmpty()) {
            System.out.print("The hardest cards are ");
            for (int i = 0; i < hardestCards.size(); i++) {
                System.out.print("\"" + hardestCards.get(i).getDescription() + "\"");
                if (i != hardestCards.size() - 1) {
                    System.out.print(", ");
                }
            }
            System.out.println(". You have " + hardestCards.get(0).getMistakes() + "errors answering them.");
        } else {
            System.out.println("There are no cards with errors.");
        }
    }

    private void resetStats() {
        cards.resetMistakes();
    }

    private void exit() {
        System.out.println("Bye bye!");
        this.running = false;
    }
}
