
public class Deck {
	private Card [] cardCollection; //Fixed deck of 52 playing cards
	private String [] suitArray = {"club", "spade", "diamond", "heart"}; // 4 suits
	private String [] rankArray = {"Ace", "2", "3", "4", "5", "6", "7", "8", "9", "T", "Jack", "Queen", "King"}; // 13 card ranks
	
	/**
	 * Initializes deck of 52 cards
	 */
	public Deck(){
		buildDeck();
	}
	
	public void buildDeck(){
		cardCollection = new Card[52];
		int deckIndex = 0;
		for(int s = 0; s < suitArray.length; s++){
			for(int r = 0; r < rankArray.length; r++){
				cardCollection[deckIndex] = new Card(rankArray[r], r + 1, suitArray[s]);
				deckIndex++;
			}
		}
		for(Card deckCard: cardCollection){
			System.out.println(deckCard);
		}
	}
}
