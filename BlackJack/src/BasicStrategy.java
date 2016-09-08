import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;
import java.lang.reflect.Array;


public class BasicStrategy {
		int playerCard1;
		int playerCard2;
		int dealerTotal;
		// 0 = Stay, 1 = Hit, 2 = Double Down, 3 = Split
		// Rows = Your hand
		// Columns = Dealer's up card
			 // {2, 3, 4, 5, 6, 7, 8, 9, 10, A}
		int [][] strategyArray = new int[][]{
				{1, 1, 1, 2, 2, 1, 1, 1, 1, 1}, // 5, 3
				{2, 2, 2, 2, 2, 1, 1, 1, 1, 1}, // 9
				{2, 2, 2, 2, 2, 2, 2, 2, 1, 1}, // 10
				{2, 2, 2, 2, 2, 2, 2, 2, 2, 2}, // 11
				{1, 1, 0, 0, 0, 1, 1, 1, 1, 1}, // 12
				{0, 0, 0, 0, 0, 1, 1, 1, 1, 1}, // 13
				{0, 0, 0, 0, 0, 1, 1, 1, 1, 1}, // 14
				{0, 0, 0, 0, 0, 1, 1, 1, 1, 1}, // 15
				{0, 0, 0, 0, 0, 1, 1, 1, 1, 1}, // 16
				{1, 1, 2, 2, 2, 1, 1, 1, 1, 1}, // A2
				{1, 1, 2, 2, 2, 1, 1, 1, 1, 1}, // A3
				{1, 1, 2, 2, 2, 1, 1, 1, 1, 1}, // A4
				{1, 1, 2, 2, 2, 1, 1, 1, 1, 1}, // A5
				{2, 2, 2, 2, 2, 1, 1, 1, 1, 1}, // A6
				{0, 2, 2, 2, 2, 0, 0, 1, 1, 1}, // A7
				{0, 0, 0, 0, 0, 0, 0, 0, 0, 0}, // A8
				{1, 1, 3, 3, 3, 3, 1, 1, 1, 1}, // 22
				{1, 3, 3, 3, 3, 3, 1, 1, 1, 1}, // 33
				{1, 3, 3, 3, 3, 1, 1, 1, 1, 1}, // 66
				{3, 3, 3, 3, 3, 3, 1, 1, 0, 1}, // 77
				{3, 3, 3, 3, 3, 3, 3, 3, 3, 3}, // 88
				{3, 3, 3, 3, 3, 0, 3, 3, 0, 0}, // 99
				{3, 3, 3, 3, 3, 3, 3, 3, 3, 3}, // AA
			};
		public BasicStrategy(int playerCard1, int playerCard2, int dealerTotal) {
			this.playerCard1 = playerCard1;
			this.playerCard2 = playerCard2;
			this.dealerTotal = dealerTotal;
		}
		//Hard coded until a better method is realized
		public int getStrategy(){
			
			if (playerCard1 == 5 && playerCard2 == 3 | playerCard1 == 3 && playerCard2 == 5)
				return(strategyArray[0][dealerTotal - 2]);
			
			if (playerCard1 + playerCard2 < 9  && playerCard1 != 3 && playerCard2 != 5){
				return 1;
			}
			else{
				if (playerCard1 == 1 && playerCard2 !=1){
					return(strategyArray[7 + playerCard2][this.dealerTotal - 2]);
				}
				
				if (playerCard1 == 2 && playerCard2 == 2){
					return(strategyArray[16][this.dealerTotal - 2]);
				}
				
				if (playerCard1 == 3 && playerCard2 == 3){
					return(strategyArray[17][this.dealerTotal - 2]);
				}
				
				if (playerCard1 == 6 && playerCard2 == 6){
					return(strategyArray[18][this.dealerTotal - 2]);
				}
				
				if (playerCard1 == 7 && playerCard2 == 7){
					return(strategyArray[19][this.dealerTotal - 2]);
				}
				
				if (playerCard1 == 8 && playerCard2 == 8){
					return(strategyArray[20][this.dealerTotal - 2]);
				}
				
				if (playerCard1 == 9 && playerCard2 == 9){
					return(strategyArray[21][this.dealerTotal - 2]);
				}	
				if (playerCard1 == 1 && playerCard2 == 1){
					return(strategyArray[22][this.dealerTotal - 2]);
				}	
			return (strategyArray[(playerCard1+playerCard2 - 8)][dealerTotal-2]);
			}
			
		}
	}


