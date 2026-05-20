import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Deck {
    private final List<Card> cards = new ArrayList<>();

    public Deck() {
        shuffle();
    }
    public void shuffle() {
        cards.clear();
        String[] suits = {"♠", "♥", "♦", "♣"};
        String[] ranks = {"2","3","4","5","6","7","8","9","10","J","Q","K","A"};

        for (String suit : suits) {
            for (String rank : ranks) {
                cards.add(new Card(rank, suit));
            }
        }
        Collections.shuffle(cards);
    }

    public Card deal() {
        if (cards.isEmpty()) {
            shuffle(); // reshuffle if needed
        }
        return cards.remove(cards.size() - 1);
    }
}