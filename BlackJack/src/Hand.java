import java.util.*;



public class Hand{
	protected ArrayList<Card> handArray;
	
	
	public Hand(){
		handArray=new ArrayList<Card>();	   
	}
	
	public Hand(Card card1, Card card2){
		handArray=new ArrayList<Card>();
		handArray.add(card1);
		handArray.add(card2);
	}
	
	public void hit(Card newCard){
		handArray.add(newCard);
	}
	
	public void setHand(Card card1){
		handArray.add(card1);
	}
	
	public int getHandValue(){
		int val =0;
		for (Card hA : handArray)
			val+=hA.value;
		return val;
	}
	
	public void popLastCard(){
		handArray.remove(handArray.size() - 1);
	}
	
	public boolean hasAce(){
		for (Card ha : handArray){
			if (ha.rank.equals("Ace"))
				return true;}
		return false;
	}
	public Card getCard(int i){
		return handArray.get(i);
	}
	
	public ArrayList<Card> getHand(){
		return handArray;
	}
	public int getFAHandValue(){
		int val=0;
		if (hasAce()==true){
			for (Card hA : handArray)
				val+=hA.value;
			if (val+10<22)
				return val+10;
			else return val;
		}
		else {
			for (Card hA : handArray)
				val+=hA.value;
		}
		return val;
	}
}
