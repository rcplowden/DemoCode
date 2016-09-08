
public class Project340II {
	public static void main(String[] args){
/*		//PART 1
		
		myMatrix textMatrix = new myMatrix(2, "eigendata.txt");
		int rows  = textMatrix.rows;
		int columns  = textMatrix.columns;
		
		myMatrix x = new myMatrix(rows, 1);
		myMatrix y = new myMatrix(rows, 1);
		
		myMatrix[] matrixArray = {x,y};
		
		//Instantiate matrices
		for(int r = 0; r < rows; r++){
			for(int c = 0; c < columns; c++){
				matrixArray[c].matrix[r][0] = textMatrix.matrix[r][c];
			}
		}
		
		myMatrix xy = x.augmentMatrix(y);
		
		//Find mean of each vector
		myMatrix meanxy = xy.mean();
		
		System.out.println("Mean Vector of xy");
		meanxy.printMatrix();
		System.out.println();
		
		//Find covariance of xy
		myMatrix covxy = xy.covariance();
		
		System.out.println("Covariance matrix of xy");
		covxy.printMatrix();
		System.out.println();
		
		//Find trace of xy
		double trace = covxy.trace();
		
		System.out.println("Trace of xy: " + trace);
		System.out.println();
		
		//Find determinant of xy
		double determinantxy = covxy.determinantFinder();
		
		System.out.println("Determinant of xy: " + determinantxy);
		System.out.println();*/
		
		//PART 2 TSP
		//tspExhaustive tspex = new tspExhaustive(14);
		tspRandom trand = new tspRandom(14);
		GeneticTSP gt = new GeneticTSP(14, 100);
		tspSA sa = new tspSA(14);
		
/*		System.out.println("test");
		double[][] smoke = {{1, -1, 0},{0,2,-1},{-1,0,1}};
		myMatrix smokem = new myMatrix(3,3);
		smokem.matrix = smoke;

		double[] roots = smokem.leverrier();
		
		for(double r : roots){
			System.out.println(r);
		}*/

	}
	
}
