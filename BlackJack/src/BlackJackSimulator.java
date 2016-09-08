
public class BlackJackSimulator {

	public static void main(String[] args) {
		//Deck BJ = new Deck();
		int [] deckTally = {4,4,4,3,4,4,4,4,4,4,4,4,4,4};
		new DepthFirstSearch(new Card("5", 5, "club"), deckTally);
	}

}
