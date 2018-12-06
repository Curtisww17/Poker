import java.util.Collections;
import java.util.LinkedList;

/**
 * 
 * @author Adam Cramer, Wesley Curtis, Jake Murphy
 *
 */
public class Deck {
	private LinkedList<Card> cards;
	private final int highestCardValue = 14;
	private final String[] suit = {"Hearts","Spades","Clubs","Diamonds"};
	
	/**
	 * Default Constructor for Deck
	 * Creates a Standard deck (52 Cards) Unshuffled
	 */
	public Deck() {
		cards = new LinkedList<Card>();
		//Fill deck with cards
		for (int i = 2; i <= highestCardValue; i++) {
			for (int j = 0; j < suit.length; j++) {
				Card c = new Card(i, suit[j]);
				cards.add(c);
			}
		}
	}
	
	/**
	 * Shuffled the deck multiple times
	 */
	public void shuffle() {
		Collections.shuffle(cards);
	}
	
	/**
	 * Returns a card from the top of the deck and removes it from the deck
	 * @return a card from the top of the deck
	 */
	public Card draw() {
		return cards.removeFirst();
	}
	
}
