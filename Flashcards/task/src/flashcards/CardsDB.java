package flashcards;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class CardsDB {
    private Map<String, String> cards;

    public CardsDB() {
        this.cards = new HashMap<>();
    }

    public String getCardDef(String desc) {
        return cards.get(desc);
    }

    public int size() {
        return cards.size();
    }

    public boolean containsCardDescription(String description) {
        return cards.containsKey(description);
    }

    public boolean containsCardDefinition(String definition) {
        return cards.containsValue(definition);
    }

    public void addCard(String desc, String def) {
        cards.put(desc, def);
        System.out.println("The pair (\"" + desc + "\":\"" + def + "\") has been added.");
    }

    public void removeCard(String definition) {
        cards.remove(definition);
        System.out.println("The card has been removed.");
    }

    public void importFromFile(File file) throws IOException{
        int i=0;
        try(Scanner scanner = new Scanner(file)) {
            while(scanner.hasNext()) {
                String[] data = scanner.nextLine().split("\\s+");
                cards.put(data[0], data[1]);
                i++;
            }
            System.out.println(i + " cards have been loaded.");
        } catch(Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    public void exportToFile(File file) throws IOException {
        try(FileWriter writer = new FileWriter(file)) {
            for(String desc : cards.keySet()) {
                String line = desc + ' ' + cards.get(desc) + "\n";
                writer.append(line);
            }
            System.out.println(cards.size() + " cards have been saved.");
        } catch(Exception e) {
            throw e;
        }
    }

    public String getRandomCard() {
        List<String> cardDescriptions = new ArrayList<>(cards.keySet());

        int cardNumber = new Random().nextInt(cardDescriptions.size());

        return cardDescriptions.get(cardNumber);
    }
}
