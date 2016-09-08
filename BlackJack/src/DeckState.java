
public class DeckState {
private int [] deckTally = new int [13];

	public DeckState(){
		for (int i = 0; i < deckTally.length; i++){
			deckTally[i] = 4;
		}
	}
	
	public void printDeckState(){
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
	
	public double calcBustProbability(Hand playerHand){
		int playerTotal = playerHand.getFAHandValue();
		int safeHit = 0;
		double bustHits = 0.0;
		double cardsLeft = 0.0;
			
		switch(playerTotal){
		case 12:
			safeHit = 8;
			break;
			
		case 13:
			safeHit = 7;
			break;
			
		case 14:
			safeHit = 6;
			break;
			
		case 15:
			safeHit = 5;
			break;
			
		case 16:
			safeHit = 4;
			break;
		
		case 17:
			safeHit = 3;
			break;
			
		case 18:
			safeHit = 2;
			break;
			
		case 19:
			safeHit = 1;
			break;
			
		case 20:
			safeHit = 0;
			break;
			
		case 21:
			safeHit = 0;
			break;
		}
		
		if (playerHand.getFAHandValue() < 12){
			bustHits = 0.0;
		}
		
		
		else{
			for(int i = safeHit; i < deckTally.length-1; i++){
				bustHits+= deckTally[i];
			}	
		}
		
		for (int i = 0; i < deckTally.length; i++){
			cardsLeft += deckTally[i];
		}
		
		
		
		return bustHits/cardsLeft;
	}
	
	public int [] clone(){
		int[] stateClone = deckTally.clone();
		return stateClone;
	}
	
	public int [] adjustDeckTally(Card card){
		int i = 0;	
		switch(card.getRank()){
		case "2":
			i = 0;
			break;
			
		case "3":
			i = 1;
			break;
			
		case "4":
			i = 2;
			break;
			
		case "5":
			i = 3;
			break;
			
		case "6":
			i = 4;
			break;
			
		case "7":
			i = 5;
			break;
			
		case "8":
			i = 6;
			break;
			
		case "9":
			i = 7;
			break;
			
		case "T":
			i = 8;
			break;
			
		case "J":
			i = 9;
			break;
			
		case "Q":
			i = 10;
			break;
			
		case "K":
			i = 11;
			break;
			
		case "A":
			i = 12;
			break;	
		}
		deckTally[i]--;
		return deckTally;
	}
	
}
