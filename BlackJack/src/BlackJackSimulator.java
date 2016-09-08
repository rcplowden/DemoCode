import java.io.FileNotFoundException;
import java.util.ArrayList;

public class BlackJackSimulator {
	private double playerWin = 0.0;
	private double playerLoss = 0.0;
	private int playerBlackJack = 0;

	private boolean stand, playerBust;

	public BlackJackSimulator(int testDecks) throws FileNotFoundException {
		System.out.println("Reading in deck...");
		ReadDeck shoe = new ReadDeck(); // Deck that cards are drawn from
		// Iterates through test set of 250000 decks
		System.out.println("Done reading in deck, calculating...");
		long startTime = System.currentTimeMillis();
		for (int r = 0; r < testDecks; r++) {
//			System.out.println("Deck " + r);
//			System.out.println("******************************");
			int handsCompleted = 0; // Tracks hands completed with deck
			Deck currentDeck = shoe.allDecks[r];

			// Plays 4 hands and then switches deck
			while (handsCompleted < 4){
//			System.out.println("HAND " + handsCompleted + " Deck " + r);
//			System.out.println("-----------------------");
			playHand(currentDeck);
			handsCompleted ++;
			 }
		}
		System.out.println("Player loss: " + playerLoss);
		System.out.println("Player win: " + playerWin);
		System.out.println();
		System.out.println();
		long endTime = System.currentTimeMillis();
		
		System.out.println("This took " + ((endTime-startTime)/1000.0) + " seconds");
	}

	public static void main(String[] args) throws FileNotFoundException {
		new BlackJackSimulator(10000);
	}

	public void playHand(Deck deck) {

		// Deal cards and adjust deck state
		stand = false;
		playerBust = false;
		Card[] initialDeal = deck.dealHand();
		Hand playerHand = new Hand(initialDeal[0], initialDeal[2]);
		Hand dealerHand = new Hand(initialDeal[1], initialDeal[3]);

		DeckState deckTally = new DeckState();
		for (Card card : initialDeal) {
			deckTally.adjustDeckTally(card);
		}

		// Check if dealer or player has blackjack
		if (dealerHand.getFAHandValue() == 21) {
			playerLoss++;
//			System.out.println("Player LOSES ROFL!");
		}
		
		else if (playerHand.getFAHandValue() == 21) {
			playerWin+= 1.5;
//			System.out.println("Player WINS BLACKJACK STYLE!");
		}

		else {
			DepthFirstSearch dealerStates = new DepthFirstSearch(dealerHand.getCard(1), deckTally.clone());
			double[] stateArray = dealerStates.getDealerStates();
			
			double hitLoss = deckTally.calcBustProbability(playerHand);
			double standLoss = calcDealerProbability(playerHand, stateArray);

			// Player's turn until stand or bust
			while (!(stand) & !(playerBust) & playerHand.getFAHandValue() < 22) {
//				System.out.println(playerHand.getHand());
//				System.out.println("Player hand total " + playerHand.getFAHandValue());
//				System.out.println(dealerHand.getHand());
//				System.out.println("Dealer hand total " + dealerHand.getFAHandValue());
//				System.out.println();
				if (standLoss > hitLoss){ // then HIT
//					System.out.println("Stand Loss: " + standLoss);
//					System.out.println("Hit Loss " + hitLoss);
//					System.out.println();
					playerHand.hit(deck.peekCard());
					deckTally.adjustDeckTally(deck.drawCard());
					hitLoss = deckTally.calcBustProbability(playerHand);
					
					// Checks if player busts
					if (playerHand.getFAHandValue() > 21){
//						System.out.println("Player BUSTS " + playerHand.getHand());
//						System.out.println("Player hand total " + playerHand.getFAHandValue());
						playerBust = true;
					}
				}
				else{
//					System.out.println("PLAYER STANDS");
					stand = true;
//					System.out.println("Stand Loss: " + standLoss);
//					System.out.println("Hit Loss " + hitLoss);
//					System.out.println();
				}
			}
			
			// If player busts end hand
			if (playerBust) {
				playerLoss++;
//				System.out.println("Player LOSES ROFL!");
			}
			
			// Else it is the Dealer's turn
			else {
				// Dealer hits until hand total is greater than 17
				while (dealerHand.getFAHandValue() < 17){
					dealerHand.hit(deck.peekCard());
					deckTally.adjustDeckTally(deck.drawCard());
//					System.out.println(dealerHand.getHand());
//					System.out.println("Dealer hand total " + dealerHand.getFAHandValue());
				}
				
				// Checks if dealer busted
				if (dealerHand.getFAHandValue() > 21){
					playerWin++;
//					System.out.println("Player WINS!");
				}
				
				// Else checks who has the greater hand
				else{
//					System.out.println("Player hand total " + playerHand.getFAHandValue());
					if(dealerHand.getFAHandValue() > playerHand.getFAHandValue()){
						playerLoss++;
//						System.out.println("Player LOSES ROFL!");
					}
					
					else{
						playerWin++;
//						System.out.println("Player WINS!");
					}
				}
			}
		}
	}
	public double calcDealerProbability(Hand playerHand, double [] stateArray) {
		double dealerProbability= 0;
		dealerProbability = 1.0 - (stateArray[6]);
		
		if (playerHand.getFAHandValue() > 16){
			int stateIndex = playerHand.getFAHandValue() - 17;

			for(int i = 0; i <= stateIndex; i++){
				dealerProbability-= stateArray[i];
			}
		}
		return dealerProbability;
	}
}	
