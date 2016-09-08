import java.io.FileNotFoundException;


public class FirstAttempt {
	
	Hand dealer = new Hand();
	Hand player = new Hand();
	int [] results = new int[8];
	int [] tally= new int[11];
	int total;
	String state="";
	int currentCard;
	int currentDeck;
	
	public static void main(String[] args) throws FileNotFoundException {
		FirstAttempt newA = new FirstAttempt();
		ReadDeck deck = new ReadDeck();
		newA.currentDeck=0;
		for (int i=0;i<250;i++){
			newA.currentCard=0;
			newA.initTally(newA.tally);
			newA.total=52;
			for (int k=0;k<4;k++){
				newA.intialDeal(deck, newA);
				if (!newA.state.equals("")){
					if (newA.state.equals("win"))
						newA.addWin();
					else if (newA.state.equals("lost"))
						newA.addLost();
					else
						newA.addPush();
				}
				else {	
					
				newA.playerPlays(deck, newA);
				newA.dealerPlays(deck, newA);
				newA.testing(newA);
				newA.whoWon(newA);
				
				}
				newA.player.handArray.clear();newA.state="";newA.dealer.handArray.clear();
			}
			newA.currentDeck++;
		}
		for (int ts : newA.results)
			System.out.print(ts+" ");
	}
	public void intialDeal(ReadDeck y, FirstAttempt x){
		x.hitP(y, x);
		x.hitD(y, x);
		x.hitP(y, x);
		x.hitSecretD(y, x);
		x.checkBlackjack(x.player, x.dealer, x);
	}
	public void checkBlackjack(Hand p, Hand d, FirstAttempt y){
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
	public void hitP(ReadDeck y, FirstAttempt z){
		Card newC=(y.allDecks[z.currentDeck][z.currentCard++]);
		z.tally[newC.value]--;
		z.total--;
		z.player.handArray.add(newC);
	}
	public void hitD(ReadDeck y, FirstAttempt z){
		Card newC=(y.allDecks[z.currentDeck][z.currentCard++]);
		z.tally[newC.value]--;
		z.total--;
		z.dealer.handArray.add(newC);
	}
	public void hitSecretD(ReadDeck y, FirstAttempt z){
		Card newC=(y.allDecks[z.currentDeck][z.currentCard++]);
		z.dealer.handArray.add(newC);
	}
	public int calculateHitPercent(FirstAttempt y){
		int n=21-y.player.getFAHandValue();
		double percent=0.0;
		if (n>10)
			n=10;
		for (int i=n;i>0;i--){
			percent+=y.tally[i];
			}
		return (int)((percent/y.total)*100);
	}
	public void testing(FirstAttempt z){
		for (Card t1 : z.player.handArray)
			System.out.print(t1+"play ");
		for (Card t2 : z.dealer.handArray)
			System.out.print(t2+"deal ");
		System.out.println();
		for (int t2 : z.tally)
			System.out.print(t2+" ");
		System.out.println();
		System.out.print(z.calculateHitPercent(z)+"\n");
	}
	public void initTally(int [] a){
		for (int i=1;i<10;i++)
			a[i]=4;
		a[10]=16;
	
	}
	public void playerPlays(ReadDeck x, FirstAttempt y){
		while (y.player.getFAHandValue()<22){
			if (y.calculateHitPercent(y)>60){
				y.hitP(x, y);
			}
			else {
				break;
			}
		}
	}
	public void dealerPlays(ReadDeck x, FirstAttempt y){
		y.tally[y.dealer.handArray.get(1).value]--;
		y.total--;
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
	public void whoWon(FirstAttempt y){
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
}

