import java.io.*;
import java.util.*;


public class ReadDeck {
	public Card [][] allDecks;

	public ReadDeck() throws FileNotFoundException{
		allDecks = new Card[250000][52];
		Scanner fileScanner = new Scanner(new File("HandData.txt"));
		for (int rows=0;rows<250;rows++)
			for (int cols=0;cols<52;cols++)
				allDecks[rows][cols]=cardBuild(fileScanner.next());
	}
	public Card cardBuild(String n){
		String suit;
		int val;
		String rank;
		if (n.substring(1).equals("h"))
			suit="hearts";
		else if (n.substring(1).equals("d"))
			suit="diamonds";
		else if (n.substring(1).equals("c"))
			suit="clubs";
		else suit = "spades";
		
		switch (n.substring(0, 1)){
		case "A":
			rank="Ace";
			val=1;
			break;
		case "2":
			rank="2";
			val=2;
			break;
		case "3":
			rank="3";
			val=3;
			break;
		case "4":
			rank="4";
			val=4;
			break;
		case "5":
			rank="5";
			val=5;
			break;
		case "6":
			rank="6";
			val=6;
			break;
		case "7":
			rank="7";
			val=7;
			break;
		case "8":
			rank="8";
			val=8;
			break;
		case "9":
			rank="9";
			val=9;
			break;
		case "T":
			rank="Ten";
			val=10;
			break;
		case "J":
			rank="Jack";
			val=10;
			break;
		case "Q":
			rank="Queen";
			val=10;
			break;
		default:
			rank="King";
			val=10;
			break;
		}
				
		Card newCard = new Card(rank, val, suit);
		return newCard;
	}
}
