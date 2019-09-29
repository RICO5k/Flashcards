package flashcards;

public class Card {
    private String description;
    private String definition;
    private int mistakes;

    public Card(String description, String definition) {
        this.description = description;
        this.definition = definition;
        this.mistakes = 0;
    }

    public Card(String description, String definition, int mistakes) {
        this(description, definition);
        this.mistakes = mistakes;
    }

    public void mistake() {
        this.mistakes++;
    }

    public void resetMistakes() {
        this.mistakes = 0;
    }

    public String getDescription() {
        return description;
    }

    public String getDefinition() {
        return definition;
    }

    public int getMistakes() {
        return mistakes;
    }

    @Override
    public String toString() {
        return this.description + " " + this.definition + " " + this.mistakes;
    }
}
