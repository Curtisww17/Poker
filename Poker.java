import java.util.Scanner;

/**
 * 
 * @author Adam Cramer, Wesley Curtis, Jake Murphy
 *
 */
public class Poker{
	public static void main(String args[]) {
		
		
		System.out.println("How many human players do you have?");
		int numHuman = getNum();
		//TODO: add exceptions
		System.out.println("And how many computer opponents would you like?");
		int numComp = getNum();
		Game g = new Game(numHuman,numComp);
		boolean playAgain = true;
		do {
			g.playHand();
			g.reset();
			playAgain = PlayAgain();
		} while (playAgain);
	}
	
	private static boolean PlayAgain() {
		System.out.println("\nDo you want to play another hand? (y,n)");
		char answer = ' ';
		boolean isGood = false;
		while (!isGood) {
			Scanner scan = new Scanner(System.in);
			if (scan.hasNext())
				answer = scan.next().toLowerCase().charAt(0);
			else {
				System.out.println("That is not an answer.");
			}
			if (answer == 'y' || answer == 'n') {
				isGood = true;
			} else {
				System.out.println("Answer must be either \"y\" or \"n\".");
			}
		}
		if (answer == 'y') {
			return true;
		} else return false;
	}
	
	
	private static int getNum() {
		int num = 0;
		boolean isGood = false;
		while (!isGood) {
			Scanner scan = new Scanner(System.in);
			if (scan.hasNextInt())
				num = scan.nextInt();
			else {
				System.out.println("That is not a valid number.");
			}
			if (num > 0 && num < 5) {
				isGood = true;
			} else {
				System.out.println("Number must be between 2 and 4 please.");
			}
		}
		return num;
	}
}
