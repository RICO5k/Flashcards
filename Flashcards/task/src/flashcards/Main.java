package flashcards;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Map<String, String> cards = new HashMap<>();

        System.out.println("Input the number of cards:");

        int howMany = Integer.parseInt(scanner.nextLine());

        for(int i=0; i<howMany; i++) {
            System.out.println("The card #" + i + ":");
            String card = scanner.nextLine();
            System.out.println("The definition of the card #" + i + ":");
            String definition = scanner.nextLine();

            cards.put(card, definition);
        }

        for(String card : cards.keySet()) {
            System.out.println("Print the definition of \"" + card + "\":");

            String answer = scanner.nextLine();
            if(answer.equals(cards.get(card))) {
                System.out.println("Correct answer.");
            } else {
                System.out.println("Wrong answer. The correct one is \"" + cards.get(card) + "\".");
            }
        }
    }
}
