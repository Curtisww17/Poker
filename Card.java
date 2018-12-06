
/**
 * Holds values such as the value and suit of the card,
 * also contains a reference to the card's image file
 * @author Adam Cramer, Wesley Curtis, Jake Murphy
 */
public class Card {
	private int value;
	private String suit;
	private char suitChar;
	 
	/**
	 * Creates a Card of value val and suit suit, also creates variable with first character of suit
	 * @param val Value of the card
	 * @param suit Suit of the card
	 */
	public Card(int val, String suit) {
		this.value = val;
		this.suit = suit;
		this.suitChar = suit.charAt(0);
	}

	
	public int getValue() {
		return this.value;
	}

	
	public char getSuit() {
		return this.suitChar;
	}
	
	/**
	 * Returns the file path of the face image
	 * @return filepath of card image
	 */
	public String getImage() {
		//Adjust Ace to be worth 14
		if (this.value == 14) {
			return ".\\CardImages\\1" + this.suitChar + ".png";
		}
		else {
			return ".\\CardImages\\" + this.value + this.suitChar + ".png";
		}
	}
	
	public String toString() {
		String name = "";
		
		switch (this.value) {
	        case 11: 
	        	name = "Jack";
	            break;
	        case 12:  
	        	name = "Queen";
	            break;
	        case 13:  
	        	name = "King";
	            break;
	        case 14:  
	        	name = "Ace";
	        	break;
	        default: 
	        	name = String.valueOf(this.value);
	            break;
		}

		return name + " of " + this.suit;
	}
	
}
