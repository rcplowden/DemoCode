import java.util.ArrayList;
import java.util.Collections;


public class Deck {
	private ArrayList<Card> cardCollection = new ArrayList <Card>(); //Fixed deck of 52 playing cards
	private String [] suitArray = {"club", "spade", "diamond", "heart"}; // 4 suits
	private String [] rankArray = {"Ace", "2", "3", "4", "5", "6", "7", "8", "9", "T", "Jack", "Queen", "King"}; // 13 card ranks
	
	/**
	 * Initializes deck of 52 cards
	 */
	public Deck(){
		buildDeck();
	}
	
	public Deck(Card [] deck){
		for(Card deckCard: deck){
			cardCollection.add(deckCard);
		}
		//printDeck();
	}
	
	//Builds deck and shuffles (for null constructor)
	public void buildDeck(){
		for(int s = 0; s < suitArray.length; s++){
			for(int r = 0; r < rankArray.length; r++){
				cardCollection.add(new Card(rankArray[r], r + 1, suitArray[s]));
			}
		}
		
		Collections.shuffle(cardCollection);
		for(Card deckCard: cardCollection){
			System.out.println(deckCard);
		}
	}
	
	public Card [] dealHand(){
		Card playerCard1, playerCard2, dealerCard1, dealerCard2;
		Card [] initialDeal = new Card[4];
		playerCard1 = cardCollection.get(0);
		dealerCard1 = cardCollection.get(1);
		playerCard2 = cardCollection.get(2);
		dealerCard2 = cardCollection.get(3);
		
		initialDeal[0] = playerCard1;
		initialDeal[1] = dealerCard1;
		initialDeal[2] = playerCard2;
		initialDeal[3] = dealerCard2;
		
		cardCollection.remove(0);
		cardCollection.remove(1);
		cardCollection.remove(2);
		cardCollection.remove(3);
		
		return initialDeal;	
	}
	
	//Draws card from top of the deck
	public Card drawCard(){
		Card tempDrawCard = cardCollection.get(0);
		cardCollection.remove(0);
		return tempDrawCard;
	}
	
	public Card peekCard(){
		return cardCollection.get(0);
	}
	
	//Returns deck
	public ArrayList<Card> getDeck(){
		return this.cardCollection;
	}
	
	// Deck toString
	public void printDeck(){
		for(Card deckCard: cardCollection){
			System.out.println(deckCard);
		}
	}
}
