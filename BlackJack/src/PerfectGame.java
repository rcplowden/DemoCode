import java.io.FileNotFoundException;
import java.util.ArrayList;



public class PerfectGame {
	Hand player=new Hand();
	Hand dealer=new Hand();
	int[] results = new int[9];
	String state="";
	int currentDeck;
	int currentCard;

	public static void main(String[] args) throws FileNotFoundException {
		int counter=0;int x=0;
		PerfectGame pg = new PerfectGame();
		ReadDeck newTest = new ReadDeck();
		pg.currentDeck=0;
		for (int n=0;n<250;n++){
			pg.currentCard=0;
			for (int m=0;m<4;m++){
				pg.intialDeal(newTest,pg);
				if (!pg.state.equals("")){
					if (pg.state.equals("win")){
						pg.addPBlackjack();
						pg.addWin();}
					else if (pg.state.equals("lost")){
						pg.addDBlackjack();
						pg.addLost();}
					else {
						pg.addDualBlackjack();
						pg.addPush();}

				}
					if (pg.player.handArray.get(0).rank.equals(pg.player.handArray.get(1).rank)){
						pg.splitandDouble(newTest, pg);
						if (pg.state=="Double Down"){
							pg.addDoubleDowns();
							pg.doubleDown(newTest, pg);
						}
						if (pg.state=="Split"){
							pg.addSplit();
							pg.split(newTest, pg);
						}
						if (pg.state=="Nonsplit"){
							pg.normalPlay(newTest, pg);}
					}
					else {
						pg.splitandDouble(newTest, pg);
						if (pg.state=="Double Down"){
							pg.addDoubleDowns();
							pg.doubleDown(newTest, pg);
						}
						else 
							pg.normalPlay(newTest, pg);
						}
				counter++;
				pg.dealer.handArray.clear();pg.player.handArray.clear();pg.state="";
			}
			pg.currentDeck++;
		}
		System.out.println("win "+pg.results[0]+ " lost "+pg.results[1]+" push "+pg.results[2]+" splits "+pg.results[3]+" double downs " + pg.results[4]+" player blackjack "+pg.results[5]+" dealer blackjack "+pg.results[6]+" dual blackjack "+pg.results[7]);
	}
	public void intialDeal(ReadDeck y, PerfectGame x){
		x.player.setHand(y.allDecks[x.currentDeck][x.currentCard++]);
		x.dealer.setHand(y.allDecks[x.currentDeck][x.currentCard++]);
		x.player.setHand(y.allDecks[x.currentDeck][x.currentCard++]);
		x.dealer.setHand(y.allDecks[x.currentDeck][x.currentCard++]);
		x.checkBlackjack(x.player, x.dealer, x);
	}
	public void checkBlackjack(Hand p, Hand d, PerfectGame y){
		String playerStat="";String dealerStat="";
		if (p.hasAce()&&p.getHandValue()==11)
			playerStat="BlackJack";
		if (d.hasAce()&&d.getHandValue()==11)
			dealerStat="BlackJack";
		if (playerStat.equals("BlackJack")){
			if (playerStat.equals(dealerStat)){
				//System.out.println("no winner push");
				y.state="push";
			}
			else{ 
				//System.out.println("BLACKJACK you win");
				y.state="win";
			}
		}
		if (dealerStat.equals("BlackJack")&&!dealerStat.equals(playerStat)){
			//System.out.println("dealer Blackjack you loose");
			y.state="lost";
		}
	}
	public void hitP(ReadDeck y, PerfectGame z){
		z.player.setHand(y.allDecks[z.currentDeck][z.currentCard++]);
	}
	public void hitD(ReadDeck y, PerfectGame z){
		z.dealer.setHand(y.allDecks[z.currentDeck][z.currentCard++]);
	}
	public void addWin(){
		//System.out.print("win ");
		results[0]++;
	}
	public void addLost(){
		//System.out.print("lost ");
		results[1]++;
	}
	public void addPush(){
		//System.out.print("push ");
		results[2]++;
	}
	public void addSplit(){
		results[3]++;
	}
	public void addDoubleDowns(){
		results[4]++;
	}
	public void addPBlackjack(){
		results[5]++;
	}
	public void addDBlackjack(){
		results[6]++;
	}
	public void addDualBlackjack(){
		results[7]++;
	}
	public void playerPlays(ReadDeck x, PerfectGame y,PerfectGame z){
		while (z.player.getHandValue()>22||!z.player.hasAce()){
			if (z.player.getHandValue()+x.allDecks[y.currentDeck][y.currentCard].value<22)
				z.hitP(x, z);
			else break;				
		}
		while (z.player.getHandValue()>22&&(z.player.hasAce())){
			int aceVal=0;
			if (z.player.getHandValue()+10>21)
				break;
			else{
				while (aceVal>21){
					aceVal=z.player.getHandValue()+10;
					for (int i=y.currentCard;i<52;i++){
						if (aceVal+x.allDecks[y.currentDeck][i].value<22)
							aceVal+=x.allDecks[y.currentDeck][i].value;
						else break;
					}
				}
			}
			int nonAceVal=0;
			while (nonAceVal>21){
				nonAceVal=z.player.getHandValue();
				for (int i=z.currentCard;i<52;i++){
					if (nonAceVal+x.allDecks[y.currentDeck][i].value>22)
						nonAceVal+=x.allDecks[y.currentDeck][i].value;
					else break;
				}
			}
			if (nonAceVal>aceVal){
				while (z.player.getHandValue()>21){
					if (z.player.getHandValue()+x.allDecks[y.currentDeck][y.currentCard].value<22)
						z.hitP(x, z);
					else break;		
				}	
			}
			else{
				while (z.player.getHandValue()+10>21){
					if (z.player.getHandValue()+10+x.allDecks[y.currentDeck][y.currentCard].value<22)
						z.hitP(x, z);
					else break;
				}
			}

		}
		//show player hand
		//for (int i=0;i<y.player.handArray.size();i++)
		//System.out.print(y.player.handArray.get(i)+" ");
		//System.out.println();

	}
	public void dealerPlays(ReadDeck x, PerfectGame y){
		while (!y.dealer.hasAce()){
			do{
				if (y.dealer.getHandValue()<17)
					y.hitD(x, y);
				else break;					
			}
			while (y.dealer.getHandValue()<17);
			break;
		}
		if (y.dealer.hasAce()&&y.dealer.getHandValue()+10<17){
			do{
				y.hitD(x, y);
				if (y.dealer.getHandValue()+10>17||y.dealer.getHandValue()>17)
					break;
			}
			while (y.dealer.getHandValue()+10<17);
		}
		if (y.dealer.hasAce()&&y.dealer.getHandValue()+10>21){
			while (y.dealer.getHandValue()<17){
				y.hitD(x, y);
			}
		}
		//show dealer hand
		//for (int i=0;i<pg.dealer.handArray.size();i++)
		//System.out.print(pg.dealer.handArray.get(i)+" ");
		//System.out.println();

	}
	public void whoWon(PerfectGame y){
		if (y.dealer.getHandValue()>21)
			y.addWin();
		else{
			if (y.player.hasAce()&&!y.dealer.hasAce()){
				if (y.player.getHandValue()+10<22){
					if (y.player.getHandValue()+10>y.dealer.getHandValue())
						y.addWin();
					else if (y.player.getHandValue()+10==y.dealer.getHandValue())
						y.addPush();
					else y.addLost();
				}
				if (y.player.getHandValue()+10>21){
					if (y.player.getHandValue()>y.dealer.getHandValue())
						y.addWin();
					else if (y.player.getHandValue()==y.dealer.getHandValue())
						y.addPush();
					else y.addLost();
				}
			}
			if (!y.player.hasAce()&&y.dealer.hasAce()){
				if (y.dealer.getHandValue()+10<22){
					if (y.dealer.getHandValue()+10>y.player.getHandValue())
						y.addLost();
					else if (y.dealer.getHandValue()+10==y.player.getHandValue())
						y.addPush();
					else y.addWin();
				}
				if (y.dealer.getHandValue()+10>21){
					if (y.dealer.getHandValue()>y.player.getHandValue())
						y.addLost();
					else if (y.dealer.getHandValue()==y.player.getHandValue())
						y.addPush();
					else y.addWin();
				}
			}
			if (y.player.hasAce()&&y.dealer.hasAce()){
				if (y.dealer.getHandValue()+10<22&&y.player.getHandValue()+10>21){
					if (y.dealer.getHandValue()+10>y.player.getHandValue())
						y.addLost();
					else if (y.dealer.getHandValue()+10==y.player.getHandValue())
						y.addPush();
					else y.addWin();
				}
				if (y.dealer.getHandValue()+10>22&&y.player.getHandValue()+10>21){
					if (y.dealer.getHandValue()>y.player.getHandValue())
						y.addLost();
					else if (y.dealer.getHandValue()==y.player.getHandValue())
						y.addPush();
					else y.addWin();
				}
				if (y.player.getHandValue()+10<22&&y.dealer.getHandValue()+10>21){
					if (y.dealer.getHandValue()>y.player.getHandValue()+10)
						y.addLost();
					else if (y.dealer.getHandValue()==y.player.getHandValue()+10)
						y.addPush();
					else y.addWin();
				}
				if (y.player.getHandValue()+10<22&&y.dealer.getHandValue()+10<22){
					if (y.dealer.getHandValue()+10>y.player.getHandValue()+10)
						y.addLost();
					else if (y.dealer.getHandValue()+10==y.player.getHandValue()+10)
						y.addPush();
					else y.addWin();
				}
			}
			if (!y.player.hasAce()&&!y.dealer.hasAce()){
				if (y.dealer.getHandValue()>y.player.getHandValue())
					y.addLost();
				else if (y.dealer.getHandValue()==y.player.getHandValue())
					y.addPush();
				else y.addWin();
			}
		}
	}
	public int[] testNonSplit(ReadDeck x, PerfectGame y, PerfectGame z){
		for (Card ts : y.player.handArray)
			z.player.handArray.add(ts);
		for (Card ts : y.dealer.handArray)
			z.dealer.handArray.add(ts);
		z.currentDeck=y.currentDeck;
		z.currentCard=y.currentCard;
		z.playerPlays(x, z, z);
		z.dealerPlays(x, z);
		z.whoWon(z);
		return z.results;
	}
	public int[] testDoubleDown(ReadDeck x, PerfectGame y){
		PerfectGame t2 = new PerfectGame();
		PerfectGame t3 = new PerfectGame();
		t2.currentDeck=y.currentDeck;t2.currentCard=y.currentCard;
		t2.player.handArray.add(y.player.handArray.get(0));
		t2.player.handArray.add(x.allDecks[t2.currentDeck][t2.currentCard++]);
		t3.player.handArray.add(y.player.handArray.get(1));
		t3.player.handArray.add(x.allDecks[t2.currentDeck][t2.currentCard++]);
		t3.currentCard=t2.currentCard;t3.currentDeck=t2.currentDeck;
		for (Card ts : y.dealer.handArray)
			t3.dealer.handArray.add(ts);
		t3.dealerPlays(x, t3);
		for (Card ts : t3.dealer.handArray){
			t2.dealer.handArray.add(ts);
			}
		t2.whoWon(t2);
		t3.whoWon(t3);
		for (int i=0;i<3;i++){
			t2.results[i]=t2.results[i]+t3.results[i];
			}
		return t2.results;

	}
	public int[] testSplit(ReadDeck x, PerfectGame y){
		PerfectGame t4 = new PerfectGame();
		PerfectGame t5 = new PerfectGame();
		t4.currentDeck=y.currentDeck;t4.currentCard=y.currentCard;
		t4.player.handArray.add(y.player.handArray.get(0));
		t4.playerPlays(x, t4, t4);
		t5.player.handArray.add(y.player.handArray.get(1));
		t5.playerPlays(x, t4, t5);
		for (Card ts : y.dealer.handArray)
			t4.dealer.handArray.add(ts);
		t4.dealerPlays(x, t4);
		for (Card dc : t4.dealer.handArray)
			t5.dealer.handArray.add(dc);
		t4.whoWon(t4);
		t5.whoWon(t5);
		for (int i=0;i<3;i++){
			t4.results[i]=t4.results[i]+t5.results[i];
			}
		return t4.results;
	}
	public void splitandDouble(ReadDeck x, PerfectGame y){
		//no split results
		PerfectGame t1 = new PerfectGame();
		int [] bc = new int[9];
		t1.results=t1.testNonSplit(x, y, t1);
		bc[0]=t1.results[0];bc[1]=t1.results[1];bc[2]=t1.results[2];
		//double down
		t1.results=t1.testDoubleDown(x, y);
		bc[3]=t1.results[0];bc[4]=t1.results[1];bc[5]=t1.results[2];
		//split
		t1.results=t1.testSplit(x, y);
		bc[6]=t1.results[0];bc[7]=t1.results[1];bc[8]=t1.results[2];
		if (bc[3]==2)
			y.state="Double Down";
		else if (bc[6]==2)
			y.state="Split";
		else if (bc[0]==1)
			y.state="Nonsplit";
		else if (bc[1]==1&&(bc[3]>0))
			y.state="Double Down";
		else if (bc[1]==1&&bc[3]==0&&bc[6]>0)
			y.state="Split";
		else if (bc[1]==1&&bc[3]==1)
			y.state="Double Down";
		else if (bc[1]==1&&bc[6]==1)
			y.state="Split";
		else
			y.state="Nonsplit";
		}
	public void normalPlay(ReadDeck x, PerfectGame y){
		//player plays
		y.playerPlays(x, y, y);

		//Dealer plays
		y.dealerPlays(x, y);
		//who won
		y.whoWon(y);
	}
	public void doubleDown(ReadDeck x, PerfectGame y){
		PerfectGame t2 = new PerfectGame();
		PerfectGame t3 = new PerfectGame();
		t2.player.handArray.add(y.player.handArray.get(0));
		t2.player.handArray.add(x.allDecks[y.currentDeck][y.currentCard++]);
		t3.player.handArray.add(y.player.handArray.get(1));
		t3.player.handArray.add(x.allDecks[y.currentDeck][y.currentCard++]);
		t3.currentCard=y.currentCard;t3.currentDeck=y.currentDeck;
		for (Card ts : y.dealer.handArray)
			t3.dealer.handArray.add(ts);
		t3.dealerPlays(x, t3);
		for (Card ts : t3.dealer.handArray)
			t2.dealer.handArray.add(ts);
		t2.whoWon(t2);
		t3.whoWon(t3);
		/*for (Card t1 : t2.player.handArray)
			System.out.print(t1+"p ");
		for (Card t1 : t2.dealer.handArray)
			System.out.print(t1+"d ");
		for (Card t1 : t3.player.handArray)
			System.out.print(t1+"p ");
		for (Card t1 : t3.dealer.handArray)
			System.out.print(t1+"d ");
		System.out.println();*/
		if (t2.results[0]==1)
			y.addWin();
		if (t2.results[1]==1)
			y.addLost();
		if (t2.results[2]==1)
			y.addPush();
		if (t3.results[0]==1)
			y.addWin();
		if (t3.results[1]==1)
			y.addLost();
		if (t3.results[2]==1)
			y.addPush();
	}
	public void split(ReadDeck x, PerfectGame y){
		PerfectGame otherHand= new PerfectGame();
		otherHand.player.handArray.add(y.player.handArray.get(1));
		y.player.handArray.remove(1);
		y.playerPlays(x, y, y);
		otherHand.playerPlays(x, y, otherHand);
		y.dealerPlays(x, y);
		for (Card ts : y.dealer.handArray)
			otherHand.dealer.handArray.add(ts);
		y.whoWon(y);
		otherHand.whoWon(otherHand);
		
		/*for (Card s1 : y.player.handArray)
			System.out.print(s1+"p ");
		for (Card s2 : y.dealer.handArray)
			System.out.print(s2+"d ");
		for (Card s3 : otherHand.player.handArray)
			System.out.print(s3+"p ");
		for (Card s4 : otherHand.dealer.handArray)
			System.out.print(s4+"d ");
		System.out.println();*/
		if (otherHand.results[0]==1)
			y.addWin();
		if (otherHand.results[1]==1)
			y.addLost();
		if (otherHand.results[2]==1)
			y.addPush();

	}
}
