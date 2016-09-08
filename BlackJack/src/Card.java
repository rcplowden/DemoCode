
public class Card {
	String rank; //2-A classification
	int value; // 1-10 
	String suit; // Hearts, Clubs, Spades, Diamonds
	/**
	 * 
	 * @param rank
	 * @param value
	 * @param suit
	 */
	public Card(String rank, int value, String suit){
		this.rank = rank;
		this.value = value;
		this.suit = suit;
	}
	
	public String getRank(){
		return rank;
	}
	
	public int getValue(){
		return value;
	}
	
	public String getSuit(){
		return suit;
	}
	public int handVal(Card [] x){
		int val=0;
		for (Card xs : x)
			val+=xs.value;
		return val;
	}
	
	public String toString(){
		return rank.substring(0,1) + suit.substring(0,1);
	}
}
