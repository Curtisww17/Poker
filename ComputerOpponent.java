import java.util.ArrayList;

public class ComputerOpponent extends Player {
	
	private static String[] names = {"Bob", "John", "Philip", "Adam", "Alan", "Fred",
									"Sam", "Alex", "Chuck", "Brad", "Computron", "Liam", "Alfred",
									"Chuck", "Logan", "Craig"};

	boolean hasPair;
	boolean sameSuit;
	
	public ComputerOpponent() {
		
		super(names[(int)Math.floor(Math.random() * names.length)]);
		isComp = true;
		hasPair = false;
		sameSuit = false;
	}
	
	
	
	@Override
	public int getBid(int stakes, int pot, ArrayList<Card> commCards) {
//		if (currentBid == stakes) {
//			check();
//			return stakes;
//		}
//		else {
//			call(stakes);
//			return stakes;
//		}
		
		int prob = 50; //Probability (higher this number the more likely to call/raise)
		
		//increases probability if the pot is higher
		prob += (pot/25);
		
		//decreases probability if they will go broke if they call
		if (money < (stakes - currentBid))
			prob -= 30;
		
		//Only does this when there are no community cards yet, because its a different 
		//system when there are community cards.
		if (commCards.size() == 0) {
			
			//Increases probability by a lot if they have a pair, decreases it if there
			//is not a pair
			if (hand.get(0).getValue() == hand.get(1).getValue()) {
				prob += 35;
				hasPair = true;
			} else {
				prob -= 7;
			}
			
			//Increases probability if the two cards in hand are the same suit, decreases it
			//otherwise
			if (hand.get(0).getSuit() == hand.get(1).getSuit()) {
				prob += 6;
				sameSuit = true;
			} else {
				prob -= 4;
			}
			
			//Goes through each card in hand,increases probability if value is higher, 
			//decreases it otherwise
			for (Card card : hand) {
				if (card.getValue() < 8)
					prob -= 10;
				if (card.getValue() < 5)
					prob -= 5;
				if (card.getValue() > 10)
					prob += 25;
				if (card.getValue() > 12)
					prob += 10;
			}
			
			
			//Does this when there are community cards in play
		} else {
			
			//Goes through each card in the community cards
			for (Card currentComm : commCards) {
				
				//Goes through cards in hand
				for (Card currentHand : hand) {
					if (currentComm.getValue() == currentHand.getValue()) {
						prob += 15;
						if (hasPair) {
							prob += 20;
						}
					} else {
						prob -= 5;
					}
					if (currentComm.getSuit() == currentHand.getSuit()) {
						prob += 5;
						if (sameSuit) {
							prob += 5;
						}
					} else {
						prob -= 3;
					}
				}
				
				//Goes through each card in hand,increases probability if value is higher, 
				//decreases it otherwise
				for (Card card : hand) {
					if (card.getValue() < 8)
						prob -= 8;
					if (card.getValue() < 5)
						prob -= 4;
					if (card.getValue() > 10)
						prob += 15;
					if (card.getValue() > 12)
						prob += 5;
				}
				
			}
		}
		
		
		//Generates a random number between 1 and 100
		int rand = (int)(Math.random() *100);
		//System.out.println("probability: " + prob + " rand: " + rand);
		
		//if the random number is less than the probability, they call or raise
		if (rand < prob) {
			
			//Less of a probability that the bot will raise
			if (rand < (prob/3)) {
				int amount = currentBid+(money/4);
				raise(amount);
				return amount;
			}
			call(stakes);
			return stakes;
			
			//If the random number is greater or equal to probability, the bot folds.
		} else {
			fold();
			return stakes;
		}
	}
}
