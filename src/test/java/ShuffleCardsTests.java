import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Arrays;

import static org.mockito.Mockito.*;


public class ShuffleCardsTests {

    @Test
    public void testCreateDeck() {
        ShuffleCards shuffleCards = new ShuffleCards();
        String[] createdDeck = shuffleCards.createDeck();

        String[] expected = {"Ace of Hearts", "2 of Hearts", "3 of Hearts", "4 of Hearts", "5 of Hearts", "6 of Hearts", "7 of Hearts", "8 of Hearts", "9 of Hearts", "10 of Hearts", "Jack of Hearts", "Queen of Hearts", "King of Hearts", "Ace of Spades", "2 of Spades", "3 of Spades", "4 of Spades", "5 of Spades", "6 of Spades", "7 of Spades", "8 of Spades", "9 of Spades", "10 of Spades", "Jack of Spades", "Queen of Spades", "King of Spades", "Ace of Clubs", "2 of Clubs", "3 of Clubs", "4 of Clubs", "5 of Clubs", "6 of Clubs", "7 of Clubs", "8 of Clubs", "9 of Clubs", "10 of Clubs", "Jack of Clubs", "Queen of Clubs", "King of Clubs", "Ace of Diamonds", "2 of Diamonds", "3 of Diamonds", "4 of Diamonds", "5 of Diamonds", "6 of Diamonds", "7 of Diamonds", "8 of Diamonds", "9 of Diamonds", "10 of Diamonds", "Jack of Diamonds", "Queen of Diamonds", "King of Diamonds"};

        Assertions.assertArrayEquals(expected, createdDeck);
    }

    @Test
    public void testShuffleDeck() {
        ShuffleCards shuffleCards = new ShuffleCards();
        String[] deck = {"Ace of Hearts", "2 of Hearts", "3 of Hearts"};

        String[] shuffledDeck = shuffleCards.shuffleDeck(deck);

        Assertions.assertFalse(Arrays.equals(deck, shuffledDeck));
        Assertions.assertTrue(Arrays.stream(shuffledDeck).anyMatch("Ace of Hearts"::equals));
        Assertions.assertTrue(Arrays.stream(shuffledDeck).anyMatch("2 of Hearts"::equals));
        Assertions.assertTrue(Arrays.stream(shuffledDeck).anyMatch("3 of Hearts"::equals));
    }

    @Test
    public void testLogCards() {
        ShuffleCards shuffleCards = new ShuffleCards();
        String[] deck = {"Ace of Hearts", "2 of Hearts", "3 of Hearts"};
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;

        System.setOut(new PrintStream(outContent));

        shuffleCards.logCards(deck);
        Assertions.assertEquals("[Ace of Hearts, 2 of Hearts, 3 of Hearts]\n", outContent.toString());

        System.setOut(originalOut);
    }

    @Test
    public void testCheckForMatchingSequenceHasSequence() {
        ShuffleCards shuffleCards = new ShuffleCards();
        String[] deck1 = {"Ace of Hearts", "2 of Hearts", "3 of Hearts"};
        String[] deck2 = {"2 of Hearts", "3 of Hearts", "Ace of Hearts"};

        boolean result = shuffleCards.checkForMatchingSequence(deck1, deck2);

        Assertions.assertTrue(result);
    }

    @Test
    public void testCheckForMatchingSequenceDoesNotHaveSequence() {
        ShuffleCards shuffleCards = new ShuffleCards();
        String[] deck1 = {"Ace of Hearts", "2 of Hearts", "3 of Hearts"};
        String[] deck2 = {"3 of Hearts", "2 of Hearts", "Ace of Hearts"};

        boolean result = shuffleCards.checkForMatchingSequence(deck1, deck2);

        Assertions.assertFalse(result);
    }

    @Test
    public void testSetUpDeckAndTriggerShuffle() {
        ShuffleCards shuffleCards = spy(new ShuffleCards());
        String[] deck1 = {"Ace of Hearts", "2 of Hearts", "3 of Hearts"};
        String[] deck2 = {"3 of Hearts", "2 of Hearts", "Ace of Hearts"};

        doReturn(deck1).when(shuffleCards).createDeck();
        doNothing().when(shuffleCards).logCards(deck1);
        doReturn(deck2).when(shuffleCards).shuffleDeck(deck1);
        doNothing().when(shuffleCards).logCards(deck2);
        doNothing().when(shuffleCards).shuffleAndCompare(deck2);

        shuffleCards.setUpDeckAndTriggerShuffle();

        verify(shuffleCards, times(1)).createDeck();
        verify(shuffleCards, times(1)).logCards(deck1);
        verify(shuffleCards, times(1)).shuffleDeck(deck1);
        verify(shuffleCards, times(1)).logCards(deck2);
        verify(shuffleCards, times(1)).shuffleAndCompare(deck2);
    }
}
