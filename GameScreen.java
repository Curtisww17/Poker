import java.awt.EventQueue;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.imageio.ImageIO;
import javax.swing.Icon;
import javax.swing.JSlider;

import javax.swing.JButton;

public class GameScreen implements ActionListener {
	
    public static JFrame frame;
    
    public static JLabel player2Card1, player2Card2, player3Card1, player3Card2, player1Card1, player1Card2, 
    	yourCard1, yourCard2, dealerCard1, dealerCard2, dealerCard3, dealerCard4,
    	dealerCard5, betLabel, potLabel;
    
    public static JLabel player1Label, player2Label, player3Label, yourLabel, dealerLabel, yourPot,
    	sliderLabel, player1Pot, player2Pot, player3Pot, potAmountLabel;
    
	public static JButton fold_button, check_button, call_button, raise_button, bet_button;
	
	public static JSlider slider;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GameScreen window = new GameScreen();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		Game g = new Game(1,3);
		g.playHand();
	}

	/**
	 * Create the application.
	 */
	public GameScreen() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize(){
		frame = new JFrame();
		frame.setBounds(100, 100, 910, 650);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		BufferedImage img = null;
		try {
		    img = ImageIO.read(new File(".\\CardImages\\10c.png"));
		} catch (IOException e) {
		    e.printStackTrace();
		}
		
		Image dimg = img.getScaledInstance(83, 114,
		        Image.SCALE_SMOOTH);
		
		ImageIcon icon = new ImageIcon(dimg);
		
		//Player 2's Cards
		
		frame.getContentPane().setLayout(null);
		player2Card1 = new JLabel(icon);
		player2Card1.setBounds(448, 0, 83, 114);
		frame.getContentPane().add(player2Card1);
		
		
		player2Card2 = new JLabel(icon);
		player2Card2.setBounds(355, 0, 83, 114);
		frame.getContentPane().add(player2Card2);
		
		//Player 3's Cards
		
		player3Card1 = new JLabel(icon);
		player3Card1.setBounds(712, 264, 83, 114);
		frame.getContentPane().add(player3Card1);
		
		player3Card2 = new JLabel(icon);
		player3Card2.setBounds(805, 264, 83, 114);
		frame.getContentPane().add(player3Card2);
		
		//Player 1's Cards
		
		player1Card1 = new JLabel(icon);
		player1Card1.setBounds(10, 264, 83, 114);
		frame.getContentPane().add(player1Card1);
		
		player1Card2 = new JLabel(icon);
		player1Card2.setBounds(103, 264, 83, 114);
		frame.getContentPane().add(player1Card2);
		
		//Your Cards
		
		yourCard1 = new JLabel(icon);
		yourCard1.setBounds(355, 492, 83, 114);
		frame.getContentPane().add(yourCard1);
		
		yourCard2 = new JLabel(icon);
		yourCard2.setBounds(448, 492, 83, 114);
		frame.getContentPane().add(yourCard2);
		
		//Dealer's Cards
		
		dealerCard1 = new JLabel(icon);
		dealerCard1.setBounds(225, 264, 83, 114);
		frame.getContentPane().add(dealerCard1);
		
		dealerCard2 = new JLabel(icon);
		dealerCard2.setBounds(320, 264, 83, 114);
		frame.getContentPane().add(dealerCard2);
		
		dealerCard3 = new JLabel(icon);
		dealerCard3.setBounds(412, 264, 83, 114);
		frame.getContentPane().add(dealerCard3);
		
		dealerCard4 = new JLabel(icon);
		dealerCard4.setBounds(504, 264, 83, 114);
		frame.getContentPane().add(dealerCard4);
		
		dealerCard5 = new JLabel(icon);
		dealerCard5.setBounds(596, 264, 83, 114);
		frame.getContentPane().add(dealerCard5);
		
		//Labels
		
		player2Label = new JLabel("Player 2");
		player2Label.setBounds(421, 125, 47, 14);
		frame.getContentPane().add(player2Label);
		
		player1Label = new JLabel("Player 1");
		player1Label.setBounds(75, 389, 47, 14);
		frame.getContentPane().add(player1Label);
		
		player3Label = new JLabel("Player 3");
		player3Label.setBounds(779, 389, 47, 14);
		frame.getContentPane().add(player3Label);

		dealerLabel = new JLabel("Dealer");
		dealerLabel.setBounds(426, 389, 105, 14);
		frame.getContentPane().add(dealerLabel);
		
		bet_button = new JButton("Place Bet");
		bet_button.setBounds(397, 447, 89, 35);
		frame.getContentPane().add(bet_button);
		
		yourLabel = new JLabel("You");
		yourLabel.setBounds(318, 548, 47, 14);
		frame.getContentPane().add(yourLabel);
		
		yourPot = new JLabel("$150");
		yourPot.setBounds(541, 548, 47, 14);
		frame.getContentPane().add(yourPot);
		
		sliderLabel = new JLabel("$50");
		sliderLabel.setBounds(551, 417, 47, 14);
		frame.getContentPane().add(sliderLabel);
		
		player2Pot = new JLabel("$150");
		player2Pot.setBounds(431, 150, 73, 14);
		frame.getContentPane().add(player2Pot);
		
		player1Pot = new JLabel("$150");
		player1Pot.setBounds(85, 414, 47, 14);
		frame.getContentPane().add(player1Pot);
		
		player3Pot = new JLabel("$150");
		player3Pot.setBounds(789, 414, 47, 14);
		frame.getContentPane().add(player3Pot);
		
		betLabel = new JLabel("Bet");
		betLabel.setBounds(318, 193, 47, 14);
		frame.getContentPane().add(betLabel);
		
		potLabel = new JLabel("Pot");
		potLabel.setBounds(541, 193, 47, 14);
		frame.getContentPane().add(potLabel);
		
		betLabel = new JLabel("$10");
		betLabel.setBounds(318, 218, 47, 14);
		frame.getContentPane().add(betLabel);
		
		potAmountLabel = new JLabel("$100");
		potAmountLabel.setBounds(541, 218, 47, 14);
		frame.getContentPane().add(potAmountLabel);
		
		fold_button = new JButton("Fold");
		fold_button.addActionListener(this);
		fold_button.setBounds(600, 447, 89, 35);
		frame.getContentPane().add(fold_button);
		
		check_button = new JButton("Check");
		check_button.addActionListener(this);
		check_button.setBounds(298, 446, 89, 35);
		frame.getContentPane().add(check_button);
		
		call_button = new JButton("Call");
		call_button.addActionListener(this);
		call_button.setBounds(499, 446, 89, 35);
		frame.getContentPane().add(call_button);
		
		raise_button = new JButton("Raise");
		raise_button.addActionListener(this);
		raise_button.setBounds(199, 447, 89, 35);
		frame.getContentPane().add(raise_button);
		
		//Betting Slider
		slider = new JSlider();
		slider.setBounds(341, 401, 200, 48);
		frame.getContentPane().add(slider);
		
		
		
	}
	
	
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == fold_button) {
			System.out.println("Fold");
		}
		else if (e.getSource() == check_button) {
			System.out.println("Check");
		}
		else if (e.getSource() == call_button) {
			System.out.println("Call");
		}
		else if (e.getSource() == raise_button) {
			System.out.println("Raise");
		}
		
	}

}
