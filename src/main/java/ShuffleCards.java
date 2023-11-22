import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class ShuffleCards {
    private final String[] cardSuites = {"Hearts", "Spades", "Clubs", "Diamonds"};
    private final String[] cardValues = {"Ace", "2", "3", "4", "5", "6", "7", "8", "9", "10", "Jack", "Queen", "King"};
    private int sequenceCountToFind = 2;

    public String[] createDeck() {
        String[] cards = new String[this.cardSuites.length * this.cardValues.length];
        int cardIndex = 0;

        for (int i = 0; i < this.cardSuites.length; i++) {
            for (int j = 0; j < this.cardValues.length; j++) {
                String card = this.cardValues[j] + " of " + this.cardSuites[i];
                cards[cardIndex] = card;
                cardIndex++;
            }
        }

        return cards;
    }

    public String[] shuffleDeck(String[] cards) {
        String[] cardsCopy = new String[cards.length];

        System.arraycopy(cards, 0, cardsCopy, 0, cards.length);

        List<String> cardsList = Arrays.asList(cardsCopy);
        Collections.shuffle(cardsList);
        cardsList.toArray(cardsCopy);

        return cardsCopy;
    }

    public void logCards(String[] cards) {
        System.out.println(Arrays.toString(cards));
    }

    public boolean checkForMatchingSequence(String[] deck1, String[] deck2) {
        for (int i = 0; i < deck1.length; i++) {
            for (int j = 0; j < deck2.length; j++) {
                if (deck1[i].equals(deck2[j])) {
                    int sequenceCount = 1;

                    for (int k = 1; k <= sequenceCountToFind; k++) {
                        if (k + i >= deck1.length || k + j >= deck2.length || !deck1[k + i].equals(deck2[k + j])) {
                            break;
                        }

                        sequenceCount++;
                    }

                    if (sequenceCount == sequenceCountToFind) {
                        return true;
                    }
                }
            }
        }

        return false;
    }

    public void shuffleAndCompare(String[] previousDeck) {
        String[] shuffledDeck = this.shuffleDeck(previousDeck);

        this.logCards(shuffledDeck);

        if (this.checkForMatchingSequence(previousDeck, shuffledDeck)) {
            this.shuffleAndCompare(shuffledDeck);
        }
    }

    public void setUpDeckAndTriggerShuffle() {
        String[] cards = this.createDeck();

        this.logCards(cards);

        String[] shuffledCards = this.shuffleDeck(cards);

        this.logCards(shuffledCards);

        this.shuffleAndCompare(shuffledCards);
    }
}
