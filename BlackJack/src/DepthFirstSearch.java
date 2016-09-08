import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Stack;




public class DepthFirstSearch {
	private Hand dealerHand;
	private int [] deckTally; // Used to track remaining cards
	private Stack deckState = new Stack(); // Stack of deckTallys
	private Stack probabilityStack = new Stack(); //Stack to track probability
	
	private int cardsRemaining; 
	

	// Used to instantiate card objects
	private static String [] rankArray = {"2", "3", "4", "5", "6", "7", "8", "9", "T", "Jack", "Queen", "King", "Ace"};
	private static int [] valArray = {2, 3, 4, 5, 6, 7, 8, 9, 10, 10, 10, 10, 1};
	private static ArrayList<Card> dealerCardArray = new ArrayList<Card>();

	
	// Absorbing states
	private double state17;
	private double state18;
	private double state19;
	private double state20;
	private double state21;
	private double blackjack;
	private double bust;
	private double totalStates;

	private double stateProbability;
	
	
	private double [] stateArray = new double[7];
	
	
	public DepthFirstSearch(Card dealerUpcard, int [] deckTally){
		if (dealerCardArray.size() == 13){
	
		}
		
		else{
			dealerCards(); 
		}
		//Card objects for constructing combinations
		
		
		
		this.deckTally = deckTally;
		
		//state of deck before adding 2nd dealer card
		deckState.push(deckTally.clone()); 
		
		// for all possible dealer downcards
		for(int i = 0; i < dealerCardArray.size(); i++){ 
			// Pushes probability of drawing card before decrementing decktally
			probabilityStack.push(calculateProbability(i, deckTally));
			
			// Updates deckstate
			deckTally = (int[]) deckState.peek();
			
			// Gets down-card and instantiates new dealer hand
			Card downCard = dealerCardArray.get(i);
			dealerHand = new Hand(dealerUpcard, downCard);
			
			
			//Pushes current deck distribution on to stack
			deckState.push(this.deckTally.clone());
			this.deckTally[i]--;
			
			// Checks for blackjack
			if (dealerHand.getFAHandValue() == 21){
				blackjack+= calculateProbability(i, deckTally);
				//blackjack+= 4.0/51.0; //temporarily hard-coded
			}
			
			/** Print statements for debugging **/
			//System.out.println(dealerHand.getHand());
			//printDeckState(deckTally);
			//System.out.println(dealerHand.getFAHandValue());
			/** Print statements for debugging **/
			
			// If dealer does not have blackjack, begin depth first search
			else{
				combinatorialSearch(dealerHand);
			}
		}
		// Prints results of algorithm
		totalStates = state17 + state18 + state19 + state20 + state21 + blackjack + bust;
		
		stateArray[0] = state17;
		stateArray[1] = state18;
		stateArray[2] = state19;
		stateArray[3] = state20;
		stateArray[4] = state21;
		stateArray[5] = blackjack;
		stateArray[6] = bust;
		
		//System.out.println(totalStates + " The dealer busts " + bust + " percent of the time");
	}
	
	public void combinatorialSearch(Hand dealerHand){
		// If dealer is in an end state, increment absorbing state
		if (dealerHand.getFAHandValue() > 16){
			stateProbability = (double) probabilityStack.pop();
			if (dealerHand.getFAHandValue() == 17){
				state17 += stateProbability;
			}
			if (dealerHand.getFAHandValue() == 18){
				state18 += stateProbability;
			}
			if (dealerHand.getFAHandValue() == 19){
				state19 += stateProbability;
			}
			if (dealerHand.getFAHandValue() == 20){
				state20 += stateProbability;
			}
			if (dealerHand.getFAHandValue() == 21){
				state21 += stateProbability;
			}
			
			if (dealerHand.getFAHandValue() > 21){
				bust += stateProbability;
			}
			
			// Backtracks dealer hand and deckstate
			dealerHand.popLastCard();
			deckTally = (int[]) deckState.pop();
			
		}
		// else dealer takes another card
		else{		
			int i = 0;
			while (i <	dealerCardArray.size()){
				// if the card remains in the deck, add it to the dealers hand
				if (deckTally[i] > 0){
					
						//Pushes current deck distribution and probability multiplier on to stack
						deckState.push(deckTally.clone()); 
						probabilityStack.push(calculateProbability(i, deckTally)* (double)probabilityStack.peek());
						
						// Adds new dealer card and adjusts deckTally
						dealerHand.hit(dealerCardArray.get(i));
						deckTally[i]--;
						
						
						/** Print statements for debugging **/
						//System.out.println(dealerHand.getHand());
						//printDeckState(deckTally);
						//System.out.println("Dealer total: " + dealerHand.getFAHandValue());
						/** Print statements for debugging **/
						
						// Calls combinatorial search recursively
						combinatorialSearch(dealerHand);
					}
				// Moves pointer to add next card
				i++;
				}
			
			// Backtracks dealer hand, deckstate, and probability
			dealerHand.popLastCard();			
			deckTally = (int[]) deckState.pop();
			stateProbability = (double) probabilityStack.pop();
			
		}
	}
	
	/** Helper Methods **/
	
	// Calculates probability of drawing card
	public double calculateProbability(int i, int [] deckTally){
		cardsRemaining = 0;
		for(int c = 0; c < deckTally.length; c++){
			cardsRemaining += deckTally[c];
		}
		double p = ((double)deckTally[i]/(double)cardsRemaining);
		
		return p;
	}
	
	//String representation of Deckstate
	public void printDeckState(int [] deckTally){
		System.out.print("[");
		for(int i = 0; i < deckTally.length; i++){
			System.out.print(deckTally[i]);
			if (i < deckTally.length - 1){
				System.out.print(", ");
			}
		}
		System.out.print("]");
		System.out.println("");
	}
	
	// Builds array used to iteratively construct hand combinations	
	public static void dealerCards(){
		
		for(int i = 0; i < rankArray.length; i++){
			dealerCardArray.add(new Card(rankArray[i], valArray[i], "club"));
		}
	}
	
	public double [] getDealerStates(){
//		for(double state: stateArray){
//			System.out.println(state);
//		}
		
		return stateArray;
	}
	
}


	

