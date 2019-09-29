package flashcards;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class CardsDB {
    private List<Card> cards;
    private final String splitter;

    public CardsDB() {
        this.cards = new ArrayList<>();
        splitter = ":";
    }

    public Card getCardByDescription(String description) {
        for(Card card : this.cards) {
            if(card.getDescription().equals(description)) {
                return card;
            }
        }

        return null;
    }

    public Card getCardByDefinition(String definition) {
        for(Card card : this.cards) {
            if(card.getDescription().equals(definition)) {
                return card;
            }
        }

        return null;
    }

    public boolean containsCardDescription(String description) {
        Card card = getCardByDescription(description);

        return card == null ? false : true;
    }

    public boolean containsCardDefinition(String definition) {
        Card card = getCardByDefinition(definition);

        return card == null ? false : true;
    }

    public int size() {
        return cards.size();
    }

    public void addCard(Card card) {
        cards.add(card);
    }

    public void removeCardByDescription(String description) {
        Card card = this.getCardByDescription(description);

        if(card != null) {
            cards.remove(card);
            System.out.println("The card has been removed.");
        } else {
            System.out.println("Can't remove \"" + description + "\": there is no such card.");
        }
    }

    public void importFromFile(File file) throws IOException{
        int loadedCards=0;
        try(Scanner scanner = new Scanner(file)) {
            while(scanner.hasNextLine()) {
                String[] data = scanner.nextLine().split(this.splitter);
                Card card = new Card(data[0], data[1], Integer.parseInt(data[2]));
                cards.add(card);
                loadedCards++;
            }
            System.out.println(loadedCards + " cards have been loaded.");
        } catch(Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    public void exportToFile(File file) throws IOException {
        try(FileWriter writer = new FileWriter(file)) {
            for(Card card : this.cards) {
                String line =  card.toString().replace(" ", splitter) + "\n";
                writer.append(line);
            }
            System.out.println(cards.size() + " cards have been saved.");
        } catch(Exception e) {
            throw e;
        }
    }

    public List<Card> getHardestCards() {
        List<Card> hardest = new ArrayList<>();
        int mostMistakes = 1;

        for(Card card : cards) {
            if(card.getMistakes() == mostMistakes) {
                hardest.add(card);
            } else if(card.getMistakes() > mostMistakes) {
                hardest.clear();
                hardest.add(card);
            }
        }

        return hardest;
    }

    public void resetMistakes() {
        for(Card card : cards) {
            card.resetMistakes();
        }
    }

    public Card getRandomCard() {
        int cardNumber = new Random().nextInt(this.size());

        return cards.get(cardNumber);
    }
}
