package flashcards;

import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Flashcards {

    private ConsoleInOut consoleInOut;

    private CardsDB cards;
    private boolean running;

    public Flashcards(ConsoleInOut consoleInOut) {
        this.consoleInOut = consoleInOut;
        this.cards = new CardsDB(consoleInOut);
    }

    public void run() {
        this.running = true;

        while(this.running) {
            consoleInOut.println("Input the action (add, remove, import, export, ask, exit, log, hardest card, reset stats):");
            Actions action = getActionFromUser();
            executeAction(action);
            consoleInOut.println("");
        }
    }

    private Actions getActionFromUser() {
        String input = consoleInOut.readLine().toUpperCase();

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
        consoleInOut.println("The card:");
        String description = consoleInOut.readLine();

        if(cards.containsCardDescription(description)) {
            consoleInOut.println("The card \"" + description + "\" already exists.");
            return;
        }

        consoleInOut.println("The definition of the card:");
        String definition = consoleInOut.readLine();

        if(cards.containsCardDefinition(definition)) {
            consoleInOut.println("The definition \"" + definition + "\" already exists.");
            return;
        }

        Card newCard = new Card(description, definition);

        cards.addCard(newCard);
        System.out.println("The pair (\"" + description + "\":\"" + definition + "\") has been added.");
    }

    private void removeCard() {
        consoleInOut.println("The card:");
        String description = consoleInOut.readLine();

        cards.removeCardByDescription(description);
    }

    private void importCardsFromFile() {
        consoleInOut.println("File name:");
        String fileName = consoleInOut.readLine();
        fileName += ".txt";

        File file = new File(fileName);

        if(!file.exists()) {
            consoleInOut.println("File not found.");
            return;
        }

        try {
            cards.importFromFile(file);
        } catch(Exception e) {
            consoleInOut.println("Something went wrong!");
        }
    }

    private void exportCardsToFile() {
        consoleInOut.println("File name:");
        String fileName = consoleInOut.readLine();
        fileName += ".txt";

        File file = new File(fileName);

        try {
            cards.exportToFile(file);
        } catch(Exception e) {
            consoleInOut.println("Something went wrong!");
        }
    }

    private void ask() {
        consoleInOut.println("How many times to ask?");
        int reps = Integer.parseInt(consoleInOut.readLine());

        if(cards.size() > 0) {
            for (int i = 0; i < reps; i++) {
                Card card = cards.getRandomCard();

                consoleInOut.println("Print the definition of \"" + card.getDescription() + "\":");
                String answer = consoleInOut.readLine();
                String correctAnswer = card.getDefinition();

                if (answer.equals(correctAnswer)) {
                    consoleInOut.println("Correct answer.");
                } else if(cards.containsCardDefinition(answer)) {
                    card.mistake();
                    consoleInOut.println("Wrong answer. The correct one is \"" + correctAnswer + "\", you've just written the definition of \"" + cards.getCardByDefinition(answer).getDescription() + "\"");
                } else {
                    card.mistake();
                    consoleInOut.println("Wrong answer. The correct one is \"" + correctAnswer + "\".");
                }
            }
        } else {
            consoleInOut.println("Cards database is empty!");
        }
    }

    private void saveLog() {
        consoleInOut.println("File name: ");
        String fileName = consoleInOut.readLine();

        File file = new File(fileName);
        List<String> todaysLog = consoleInOut.getLog();

        try(FileWriter writer = new FileWriter(file)) {
            for(String line : todaysLog) {
                writer.append(line);
            }
            consoleInOut.println("The log has been saved.");
        } catch(Exception e) {
            consoleInOut.println("Something went wrong!");
        }
    }

    private void printHardestCard() {
        List<Card> hardestCards = cards.getHardestCards();
        StringBuilder toPrint = new StringBuilder();
        if(!hardestCards.isEmpty()) {
            if(hardestCards.size() > 1) {
                toPrint.append("The hardest cards are ");
            } else {
                toPrint.append("The hardest card is ");
            }
            for (int i = 0; i < hardestCards.size(); i++) {
                toPrint.append("\"" + hardestCards.get(i).getDescription() + "\"");
                if (i != hardestCards.size() - 1) {
                    toPrint.append(", ");
                }
            }
            toPrint.append(". You have " + hardestCards.get(0).getMistakes() + " errors answering them.");
        } else {
            toPrint.append("There are no cards with errors.");
        }
        consoleInOut.println(toPrint.toString());
    }

    private void resetStats() {
        cards.resetMistakes();
        consoleInOut.println("Card statistics has been reset.");
    }

    private void exit() {
        consoleInOut.println("Bye bye!");
        this.running = false;
    }
}
