import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.TreeMap;

/*
 * Known Issues:
 * if there is a tie the method only returns null instead of showing which players tied
 * no support for more than 2 people with the same hand
 * tie only checks highest tiebreaker card and will return a tie without checking anymore cards
 */
/**
 * 
 * @author CRAMERAM17
 *
 */
public class Score {
	private LinkedList<Player> playerList;//list of players
	private ArrayList<Card> coCards;//community cards
	
	/**
	 * 
	 * @param players list of all the players
	 * @param cCards list of community cards
	 */
	public Score(LinkedList<Player> players, ArrayList<Card> cCards) {
		playerList = players; //Intentionally shallow copy
		coCards = cCards; //also Intentionally shallow
	}
	/**
	 * Runs through the list of players and returns the player who's hand won
	 * @return player that won
	 */
	public Player getWinner() {
		System.out.println("Scoring hands");
		TreeMap<Integer, HashSet<Player>> playerRank = new TreeMap<Integer, HashSet<Player>>();
		int tempRank;
		for(Player p: playerList) {
			tempRank = rankHand(p);
			if(playerRank.containsKey(tempRank)) {
				HashSet<Player> temp = new HashSet<Player>(playerRank.get(tempRank));
				temp.add(p);
				playerRank.put(tempRank, temp);
			}
			else {
				HashSet<Player> temp = new HashSet<Player>();
				temp.add(p);
				playerRank.put(tempRank, temp);
			}
			
		}
		HashSet<Player> tempHash = playerRank.get(playerRank.firstKey());
		Iterator<Player> iter = tempHash.iterator();
		if(tempHash.size()==1) {
			return iter.next();
		}
		else {
			Player winner = iter.next();
			Player tempPlayer;
			boolean isTie = false;
			
			while (iter.hasNext()) {
				tempPlayer = iter.next();
				if(handHigh(winner) == handHigh(tempPlayer)) {
					isTie = true;
				}
				if(handHigh(winner)<handHigh(tempPlayer)) {
					winner = tempPlayer;
				}
			}
			if(isTie) {
				iter = tempHash.iterator();//resets iterator to find tie
				tempPlayer = iter.next();
				while(iter.hasNext()) {
					if(handHigh(winner) == handHigh(tempPlayer) && !winner.equals(tempPlayer)) {
						System.out.println("Tie!");//TODO remove
						return null;//this is bad but wesley wants it 
					}
					tempPlayer = iter.next();
				}
			}
			System.out.println(winner.name + " wins");
			return winner;
		}
	}
	/**
	 * ranks hand based on rankings specified in handRanks.txt (royal flush = 1 ect)
	 * @return integer ranking of hand
	 */
	public int rankHand(Player p) {
		System.out.println("Scoring " + p.name);//TODO remove
		if (isRFlush(p.hand, coCards)) {
			return 1;
		}
		if(isSFlush(p.hand, coCards) != 0) {
			return 2;
		}
		if(isFour(p.hand, coCards) != 0) {
			return 3;
		}
		if(isFHouse(p.hand, coCards) != 0) {
			return 4;
		}
		if(isFlush(p.hand, coCards) != 0) {
			return 5;
		}
		if(isStraight(p.hand, coCards) != 0) {
			return 6;
		}
		if(isThree(p.hand, coCards) != 0) {
			return 7;
		}
		if(is2Pair(p.hand, coCards) != 0) {
			return 8;
		}
		if(isPair(p.hand, coCards) != 0) {
			return 9;
		}
		return 10;
	}
	
	/**
	 * 
	 * @param p
	 * @return most valuable card within the hand (not including cards not being played)
	 */
	public int handHigh(Player p) {
		if (isRFlush(p.hand, coCards)) {
			return 14;//ace
		}
		if(isSFlush(p.hand, coCards) != 0) {
			return isSFlush(p.hand, coCards);
		}
		if(isFour(p.hand, coCards) != 0) {
			return isFour(p.hand, coCards);
		}
		if(isFHouse(p.hand, coCards) != 0) {
			return isFHouse(p.hand, coCards);
		}
		if(isFlush(p.hand, coCards) != 0) {
			return isFlush(p.hand, coCards);
		}
		if(isStraight(p.hand, coCards) != 0) {
			return isStraight(p.hand, coCards);
		}
		if(isThree(p.hand, coCards) != 0) {
			return isThree(p.hand, coCards);
		}
		if(is2Pair(p.hand, coCards) != 0) {
			return is2Pair(p.hand, coCards);
		}
		if(isPair(p.hand, coCards) != 0) {
			return isPair(p.hand, coCards);
		}
		//High card
		int highCard = 0;
		for(Card c: p.hand) {
			if(c.getValue() > highCard)
				highCard = c.getValue();
		}
		for(Card c: coCards) {
			if(c.getValue() > highCard)
				highCard = c.getValue();
		}
		return highCard;
		
		
	}
	
	//currently unnessecary may be nessecary if we eventually iterate through cards properly to resolve ties
	public Player tieBreaker(Player p1, Player p2) {
		if(handHigh(p1) > handHigh(p2)) {
			return p1;
		}
		if(handHigh(p1) < handHigh(p2)) {
			return p2;
		}
		return null;
	}
	
	/**
	 * 
	 * @return True if is a Royal Flush
	 */
	public boolean isRFlush(ArrayList<Card> hand, ArrayList<Card> commCards) {
		for(int i = 0; i<4; i++) {
			//All the requirements for a royal flush
			char suit;
			boolean hasAce = false;
			boolean hasKing = false;
			boolean hasQueen = false;
			boolean hasJack = false;
			boolean has10 = false;
			
			if(i==0) {
				suit = 'H';
			}
			else if(i==1) {
				suit = 'S';
			}
			else if(i==2) {
				suit = 'C';
			}
			else{
				suit = 'D';
			}
			
			//Checks the cards in the hand for royal flush components
			for(Card c: hand) {
				if(c.getValue() == 14 && c.getSuit() == suit)//Checking for an ace
					hasAce = true;
				else if(c.getValue() == 10 && c.getSuit() == suit)//Checking for a 10
					has10 = true;
				else if(c.getValue() == 11 && c.getSuit() == suit)//Checking for a Jack
					hasJack = true;
				else if(c.getValue() == 12 && c.getSuit() == suit)//Checking for a Queen
					hasQueen = true;
				else if(c.getValue() == 13 && c.getSuit() == suit)//Checking for a King
					hasKing = true;
			}
			
			//Checks the cards in the Community Cards for royal flush components
			for(Card c: commCards) {
				if(c.getValue() == 14 && c.getSuit() == suit)//Checking for an ace
					hasAce = true;
				else if(c.getValue() == 10 && c.getSuit() == suit)//Checking for a 10
					has10 = true;
				else if(c.getValue() == 11 && c.getSuit() == suit)//Checking for a Jack
					hasJack = true;
				else if(c.getValue() == 12 && c.getSuit() == suit)//Checking for a Queen
					hasQueen = true;
				else if(c.getValue() == 13 && c.getSuit() == suit)//Checking for a King
					hasKing = true;
			}
			
			//Checks that all conditions are met
			if(hasAce && has10 && hasJack && hasQueen && hasKing)
				return true;
		}
		return false;
		
	}
	
	/**
	 *   
	 * @return value of the highest card if is a Straight Flush or 0 if it is not
	 */
	public int isSFlush(ArrayList<Card> hand, ArrayList<Card> commCards) {
		boolean isValid = false;
		//int max= 14;
		
		for(int i = 0; i<4; i++) {
			char suit;
			
			if(i==0) {//iterates through the suits
				suit = 'H';
			}
			else if(i==1) {
				suit = 'S';
			}
			else if(i==2) {
				suit = 'C';
			}
			else{
				suit = 'D';
			}
			
			int highCard = 0; //stores the value of the high card
			//sets the value of the high card
			for(Card c: hand) {
				if(c.getValue() > highCard)//For normal straight will have to check for ace
					highCard = c.getValue();
			}
			for(Card c: commCards) {
				if(c.getValue() > highCard && c.getSuit() == suit)//For normal straight will have to check for ace
					highCard = c.getValue();
			}
		
			while(!isValid && highCard > 5) {
				isValid = isNumHigh(hand, commCards, suit, highCard);
				if(!isValid)
					highCard--;
			}
			if(isValid)
				return highCard;
			}
		
			return 0;
	}
	
	/**
	 * Takes in @param highCard and checks if it is the high card in a straight
	 * @param hand 
	 * @param commCards
	 * @param suit suit of the hand
	 * @param highCard
	 * @return true if it is a valid highcard
	 *///used in SFlush
	public boolean isNumHigh(ArrayList<Card> hand, ArrayList<Card> commCards, char suit, int highCard) {
		if(highCard < 6)
			return false;
		
		boolean has1Low = false; //has card that is 1 lower than highCard
		boolean has2Low = false;
		boolean has3Low = false;
		boolean has4Low = false;
		
		
		
		//Check for 1 low
		for(Card c: hand) {
			if(c.getValue() == highCard-1)
				has1Low = true;
		}
		for(Card c: commCards) {
			if(c.getValue() == highCard-1 && c.getSuit() == suit)
				has1Low = true;
		}
		if(!has1Low)
			return false;
		
		//Check for 2 low
		for(Card c: hand) {
			if(c.getValue() == highCard-2)
				has2Low = true;
		}
		for(Card c: commCards) {
			if(c.getValue() == highCard-2 && c.getSuit() == suit)
				has2Low = true;
		}
		if(!has2Low)
			return false;
		
		//Checks for 3 low
		for(Card c: hand) {
			if(c.getValue() == highCard-3)
				has3Low = true;
		}
		for(Card c: commCards) {
			if(c.getValue() == highCard-3 && c.getSuit() == suit)
				has3Low = true;
		}
		if(!has3Low)
			return false;
		
		//Checks for 4 low
		for(Card c: hand) {
			if(c.getValue() == highCard-4)
				has3Low = true;
		}
		for(Card c: commCards) {
			if(c.getValue() == highCard-4 && c.getSuit() == suit)
				has3Low = true;
		}
		if(!has4Low)
			return false;
		else
			return true;
		
		
		
	}
	
	/**
	 * Takes in @param highCard and checks if it is the high card in a straight
	 * @param hand 
	 * @param commCards
	 * @param highCard
	 * @return true if it is a valid highcard
	 *///used in SFlush
	public boolean isNumHigh(ArrayList<Card> hand, ArrayList<Card> commCards,  int highCard) {
		if(highCard < 6)
			return false;
		
		boolean has1Low = false; //has card that is 1 lower than highCard
		boolean has2Low = false;
		boolean has3Low = false;
		boolean has4Low = false;
		
		
		
		//Check for 1 low
		for(Card c: hand) {
			if(c.getValue() == highCard-1)
				has1Low = true;
		}
		for(Card c: commCards) {
			if(c.getValue() == highCard-1)
				has1Low = true;
		}
		if(!has1Low)
			return false;
		
		//Check for 2 low
		for(Card c: hand) {
			if(c.getValue() == highCard-2)
				has2Low = true;
		}
		for(Card c: commCards) {
			if(c.getValue() == highCard-2)
				has2Low = true;
		}
		if(!has2Low)
			return false;
		
		//Checks for 3 low
		for(Card c: hand) {
			if(c.getValue() == highCard-3)
				has3Low = true;
		}
		for(Card c: commCards) {
			if(c.getValue() == highCard-3)
				has3Low = true;
		}
		if(!has3Low)
			return false;
		
		//Checks for 4 low
		for(Card c: hand) {
			if(c.getValue() == highCard-4)
				has3Low = true;
		}
		for(Card c: commCards) {
			if(c.getValue() == highCard-4)
				has3Low = true;
		}
		if(!has4Low)
			return false;
		else
			return true;
		
		
		
	}
	
	/**
	 * Finds the highest card of specified suit in either hand or commcards
	 * @param hand
	 * @param commCards
	 * @param suit
	 * @return
	 *///Used in flush
	public int findHighest(ArrayList<Card> hand, ArrayList<Card> commCards, char suit) {//TODO figure out if this is used
		int highCard = 0;
		for(Card c: hand) {
			if(c.getValue() > highCard)
				highCard = c.getValue();
		}
		for(Card c: commCards) {
			if(c.getValue() > highCard && c.getSuit() == suit)
				highCard = c.getValue();
		}
		return highCard;
	}
	
	/**
	 * 
	 * @return value of the card if is Four of a Kind or 0 if its not
	 */
	public int isFour(ArrayList<Card> hand, ArrayList<Card> commCards) {
		//for each card find if there are three others of the same 
		//make sure that at least one of the four is from the hand
		//Create an arraylist of all the possible cards for easier checking
		ArrayList<Card> all = new ArrayList<Card>();
		for(Card c: hand) {
			all.add(c);
		}
		for(Card c: commCards) {
			all.add(c);
		}
		
		//Brute force iterates through the list finding all value matches
		for(Card c: all) {
			for(Card d: all) {
				if(c.getValue() == d.getValue() && c.getSuit() != d.getSuit()) {
					for(Card e: all) {
						if(e.getValue() == d.getValue() && e.getSuit() != d.getSuit() && c.getSuit() != e.getSuit()) {
							for(Card f: all) {
								if(f.getValue() == d.getValue() && f.getSuit() != d.getSuit() && c.getSuit() != f.getSuit() && e.getSuit()!=f.getSuit()) {
									return f.getValue();
								}}}}}}}
		return 0;
		
		
	}
	
	/**
	 * 
	 * 
	 * @return 0 if not a full house: otherwise return value of the three of a kind
	 */
	public int isFHouse(ArrayList<Card> hand, ArrayList<Card> commCards) {
		
		ArrayList<Card> all = new ArrayList<Card>();
		for(Card c: hand) {
			all.add(c);
		}
		for(Card c: commCards) {
			all.add(c);
		}
		
		//requirements for full house
		boolean hasPair = false;
		//Brute force iterates through the list finding all value matches
		//searches for a pair
		for(Card c: all) {
			for(Card d: all) {
				if(c.getValue() == d.getValue() && c.getSuit() != d.getSuit()) {
					hasPair = true;
								}}}
		if(!hasPair) {
			return 0;
		}
		
		//searches for a three of a kind
		for(Card c: all) {
			for(Card d: all) {
				if(c.getValue() == d.getValue() && c.getSuit() != d.getSuit()) {
					for(Card e: all) {
						if(e.getValue() == d.getValue() && e.getSuit() != d.getSuit() && c.getSuit() != e.getSuit()) {
							return e.getValue();//three of a kind determines tiebreak so it gets passed
								}}}}}
		return 0;
	}
	
	/**
	 * 
	 * @return 0 if not a flush: otherwise return value of the high card
	 */
	public int isFlush(ArrayList<Card> hand, ArrayList<Card> commCards) {
		
		ArrayList<Card> all = new ArrayList<Card>();
		for(Card c: hand) {
			all.add(c);
		}
		for(Card c: commCards) {
			all.add(c);
		}
		//Brute force iterates through the list finding all suit matches
		for(Card c: all) {
			for(Card d: all) {
				if(c.getSuit() == d.getSuit() && !c.equals(d)) {
					for(Card e: all) {
						if(e.getSuit() == d.getSuit() && !c.equals(e) && !d.equals(e)) {
							for(Card f: all) {
								if(f.getSuit() == d.getSuit() && !c.equals(f) && !d.equals(f) && !e.equals(f)) {
									for(Card g: all) {
										if(f.getSuit() == d.getSuit() && !c.equals(g) && !d.equals(g) && !e.equals(g) && !f.equals(g)) {
											return findHighest(hand, commCards, f.getSuit());
										}
									}
								}}}}}}}
		return 0;
	}
	
	/**
	 * 
	 * @return 0 if not a straight: otherwise return value of the high card
	 */
	public int isStraight(ArrayList<Card> hand, ArrayList<Card> commCards) {
		boolean isValid = false;
		int highCard = 0; //stores the value of the high card
		//sets the value of the high card
		for(Card c: hand) {
			if(c.getValue() > highCard)
				highCard = c.getValue();
		}
		for(Card c: commCards) {
			if(c.getValue() > highCard)
				highCard = c.getValue();
		}
	
		while(!isValid && highCard > 5) {
			isValid = isNumHigh(hand, commCards, highCard);
			if(!isValid)
				highCard--;
		}
		if(isValid)
			return highCard;
		return 0;
	}
	
	public int isThree(ArrayList<Card> hand, ArrayList<Card> commCards) {
		//Create an arraylist of all the possible cards for easier checking
		ArrayList<Card> all = new ArrayList<Card>();
		for(Card c: hand) {
			all.add(c);
		}
		for(Card c: commCards) {
			all.add(c);
		}
			
		//Brute force iterates through the list finding all value matches
		for(Card c: all) {
			for(Card d: all) {
				if(c.getValue() == d.getValue() && c.getSuit() != d.getSuit()) {
					for(Card e: all) {
						if(e.getValue() == d.getValue() && e.getSuit() != d.getSuit() && c.getSuit() != e.getSuit()) {
							return e.getValue();
								}}}}}
		return 0;
	}
	
	public int is2Pair(ArrayList<Card> hand, ArrayList<Card> commCards) {
		//Create an arraylist of all the possible cards for easier checking
		ArrayList<Card> all = new ArrayList<Card>();
		for(Card c: hand) {
			all.add(c);
		}
		for(Card c: commCards) {
			all.add(c);
		}
		int pair1 = 0;//value of the first pair
		int pair2 = 0;
		//Brute force iterates through the list finding all value matches
		for(Card c: all) {
			for(Card d: all) {
				if(c.getValue() == d.getValue() && c.getSuit() != d.getSuit()) {
					pair1 = c.getValue();
								}}}
		if(pair1 == 0)
			return 0;
		
		for(Card c: all) {
			for(Card d: all) {
				if(c.getValue() == d.getValue() && c.getSuit() != d.getSuit()) {
					pair2 = c.getValue();
								}}}
		
		if(pair2 == 0)
			return 0;
		if(pair1 > pair2)
			return pair1;
		return pair2;
	}
	
	public int isPair(ArrayList<Card> hand, ArrayList<Card> commCards) {
		ArrayList<Card> all = new ArrayList<Card>();
		for(Card c: hand) {
			all.add(c);
		}
		for(Card c: commCards) {
			all.add(c);
		}
		//Brute force iterates through the list finding all value matches
		for(Card c: all) {
			for(Card d: all) {
				if(c.getValue() == d.getValue() && c.getSuit() != d.getSuit()) {
					return c.getValue();
								}}}
		return 0;
	}
	
	public boolean isSameSuit(Card one, Card two) {
		return one.getSuit() == two.getSuit();
	}
	
	public boolean isSameRank(Card one, Card two) {
		return one.getValue() == two.getValue();
	}
	
	
}
