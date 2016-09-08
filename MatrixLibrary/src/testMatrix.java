import java.util.Random;


public class testMatrix {
	public static void main(String[] args){
		Random rand = new Random();
		
		final String zeroes = "zeroes";
		final String identity = "identity";
		
		
		myMatrix m1 = new myMatrix(4, 4, identity);
/*		myMatrix m2 = new myMatrix(2, 4, identity);*/
		
		for(int r = 0; r< m1.rows; r++){
			for(int c = 0; c< m1.columns; c++){
				double holdRand = rand.nextDouble();
				m1.matrix[r][c] = holdRand;
			}
		}
		
		
		
		System.out.println("MATRIX 1");
		System.out.println("=========================");
		m1.printMatrix();
		System.out.println();
		
		double[] coefficients = {1.0, 1.0, 1.0, 1.0};
		
		m1.augmentIdentityMatrix();
		
		System.out.println("MATRIX 1 AUG IDENTITY");
		System.out.println("=========================");

		m1.printMatrix();
		
		System.out.println();
		
/*		m2.transpose();

		System.out.println("M2 TRANSPOSED");
		System.out.println("=========================");
		m2.printMatrix();
		m2.printBrackets();
		System.out.println();
		
		m1.multiply(m2);
		
		System.out.println("M1 * M2");
		System.out.println("=========================");
		m1.printMatrix();*/
	}
}
