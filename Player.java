import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
/**
 * 
 * @author Adam Cramer, Wesley Curtis, Jake Murphy
 *
 */
public class Player {
	public ArrayList<Card> hand;
	//money is the total amount of money the player has
	public int money;
	//currentBid is the amount of money that the player has put in to the hand
	public int currentBid;
	public String name;
	public boolean isDealer;
	//hasFolded is true when the player folds their cards
	public boolean hasFolded;
	private Scanner scan;
	public boolean isComp;
	public boolean isBetting;
	
	public JLabel card1;
	public JLabel card2;
	
	/**
	 * Creates a blank player with no cards or money
	 */
	public Player(String name) {
		money = 500;
		this.name = name;
		hand = new ArrayList<>();
		hasFolded = false;
		isDealer = false;
		scan = new Scanner(System.in);
		isComp = false;
		isBetting = true;
	}
	
	public void Update() {
		BufferedImage img = null;
		int counter = 0;
		for (Card card : hand ) {
			counter++;
			try {
			    img = ImageIO.read(new File(card.getImage()));
			} catch (IOException e) {
			    e.printStackTrace();
			}
			
			Image dimg = img.getScaledInstance(83, 114,
			        Image.SCALE_SMOOTH);
			
			ImageIcon icon = new ImageIcon(dimg);
			if (counter == 1) {
				card1 = new JLabel(icon);			
			}
			else {
				card2 = new JLabel(icon);
			}
			if (!isComp) {
				GameScreen.yourCard1 = card1;	
				GameScreen.yourCard2 = card2;
			}
		}
	}
	
	/**
	 * this method takes input from the player and gets what the player wants to do.
	 * @param stakes the current highest bid
	 * @param pot the total amount of money in the pot
	 * @return the stakes after the players turn
	 */
	public int getBid(int stakes, int pot, ArrayList<Card> commCards) {
		if (money <= 0) {
			System.out.println(name + " is out of money.");
			fold();
			money = 0;
			System.out.println(name + " has lost.");
			System.exit(0);
		}
		System.out.println("\nYou have " + this.toString());
		System.out.println("Stakes: " + stakes + "\tPot: " + pot + 
				"\nCurrent bid: " + currentBid + "\tMoney: " + money);
		do {
			System.out.println("\nWould you like to fold(f), check(c), raise(r) or call(l)?");
			char answer = scan.next().toLowerCase().charAt(0);
			if (answer == 'f') {
				fold();
				return stakes;
			}
			else if (answer == 'c') {
				
				if (stakes == currentBid) {
					check();
					return stakes;
				}
				else {
					System.out.println("You cannot check. You must either call, raise, or fold.");
				}
			}
			else if (answer == 'l') {
			
				call(stakes);
				return stakes;
				
			}
			else if (answer == 'r') {
				System.out.println("What would you like to raise it to?");
				int amount = scan.nextInt();
				if (amount > stakes && money >= amount) {
					raise(amount);
					return amount;
				}
				else {
					System.out.println("Invalid bid (you must have enough money & bid must be a higher amount than it already is.");
				}
			} else
				System.out.println("You did not pick one of the options, try not being an idiot.");
			} while (true);
	}
	
	/**
	 * Raises the current bid to amount
	 * @param amount what the total bid will be
	 */
	public void raise(int amount) {
		
		System.out.println(name + " has raised the stakes to " + amount);
		money -= amount - currentBid;
		currentBid = amount;
		if (money < 0) {
			money = 0;
		}
		
	}
	
	/**
	 * Match the current bid
	 */
	public void call(int stakes) {
		System.out.println(name + " has called.");
		money -= stakes - currentBid;
		currentBid = stakes;
		if (money < 0) {
			money = 0;
		}
		
	}
	
	/**
	 * fold the hand
	 */
	public void fold() {
		System.out.println(name + " has folded.");
		money -= currentBid;
		hasFolded = true;
	}
	
	/**
	 * pass
	 */
	public void check() {
		System.out.println(name + " has checked.");
	}
	/* replaced by score class
	/**
	 * compares this player and other player to determine who wins
	 * @param other the player to compare
	 * @return true if this player is better than other player
	 *\/
	public boolean isBetter(Player other) {
		return true;
	}*/
	
	
	/**
	 * Prints out the hand of the player
	 */
	public String toString() {
		String output = "";
		for (int i = 0; i < hand.size(); i++) {
			if (i != hand.size() - 1)
				output += hand.get(i).toString() + ",";
			else
				output += hand.get(i).toString();
		}
		return output;
	}
	
	@Override
	public boolean equals(Object obj) {
		if(this == obj) {
			return true;
		}
		if(!(obj instanceof Player)) {
			return false;
		}
		
		Player p = (Player) obj;
		if(p.money == this.money) {
			if(p.name.equals(this.name)) {
				if(p.currentBid == this.currentBid) {
					if(p.isDealer==this.isDealer) {
						if(p.hasFolded==this.hasFolded) {
							if(p.isBetting == this.isBetting) {
								return true;
							}}}}}}
		return false;
	}
}
