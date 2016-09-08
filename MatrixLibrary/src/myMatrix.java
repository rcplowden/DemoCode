import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Scanner;

/**
 * 
 * @author Rene Plowden
 *
 */
public class myMatrix {
	public int rows;
	public int columns;
	private double mean;
	private myMatrix meanVector;
	double[][] matrix;
	Complex[] cmatrix;
	private String classification;
	
	public myMatrix(int rows, int columns){
		this.rows = rows;
		this.columns = columns;
		cmatrix = new Complex[this.rows];
		generateMatrix("");
	}
	
	public myMatrix(int rows, int columns, String instruction){
		this.rows = rows;
		this.columns = columns;
		generateMatrix(instruction);
	}
	
	public myMatrix(int columns, String filepath){
		this.columns = columns;
		int fileRows = 0;
		File file = new File(filepath);
		
		try {
			Scanner rowCounter = new Scanner(file);
			
			while(rowCounter.hasNextLine()){
				rowCounter.nextLine();
				fileRows ++;
			}
			
			this.rows = fileRows - 1;
			
			cmatrix = new Complex[this.rows];
			
			matrix = new double[this.rows][this.columns];
			
			FileReader is = new FileReader(filepath);
			BufferedReader br = new BufferedReader(is);
			
			//Steps over variable line 0
			br.readLine();
			
			for (int r = 0; r < this.rows; r++){
				String[] tempString = br.readLine().split("\\s+");
				
				
				for(int c = 0; c < this.columns; c++){
					matrix[r][c] = Double.parseDouble(tempString[c]);
				}
			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	/**
	 * Instantiates matrix
	 * @throws IOException 
	 */
	public myMatrix deepCopyMatrix(){
		myMatrix deepCopy = new myMatrix(rows,columns);
		
		for(int r = 0; r < rows; r++){
			for(int c = 0; c < columns; c++){
				deepCopy.matrix[r][c] = matrix[r][c];
			}
		}
		
		return deepCopy;
	}
	
	private void generateMatrix(String instruction) {
		matrix = new double[rows][columns];
		
		if(instruction == "zeroes"){
			zeroes();
		}
		
		if (instruction == "identity"){
			identity();
		}		
	}
	
	/**
	 * Reflects elements across diagonal into a c x r Matrix
	 */
	public myMatrix transpose(){
		myMatrix matrixT = new myMatrix(columns,rows);
		
		for(int r = 0; r < rows; r++){
			for(int c = 0; c < columns; c++){
				matrixT.matrix[c][r] = matrix[r][c];
			}
		}
		
		return matrixT;
	}
	
	public myMatrix augmentMatrix(myMatrix y){
		myMatrix matrixAug = new myMatrix(rows, columns+ y.columns);
		
		for (int r = 0; r < rows; r++){
			for (int c = 0; c < columns; c++){
				matrixAug.matrix[r][c] = matrix[r][c];
				matrixAug.matrix[r][c+columns] = y.matrix[r][c];
			} 
		}
	
		return matrixAug;	
	}
		
	
	public void augmentCoefficientMatrix(double [] coefficients){
		double[][] matrixAUG = new double[rows][columns+1];
			for (int r = 0; r < rows; r++){
				for (int c = 0; c < columns; c++){
					matrixAUG[r][c] = matrix[r][c];
				}
				
				matrixAUG[r][columns] = coefficients[r]; 
			}
			
			matrix = matrixAUG;
			columns = columns + 1;
		}
		
	public void augmentIdentityMatrix(){
		//printMatrix();
		myMatrix identity = new myMatrix(rows,columns, "identity");
		
		//identity.printMatrix();
		double[][] matrixAUG = new double[rows][columns*2];
		
		for (int r = 0; r < rows; r++){
			for (int c = 0; c < columns; c++){
				matrixAUG[r][c] = matrix[r][c];
				matrixAUG[r][c+columns] = identity.matrix[r][c];
			}
		}

		matrix = matrixAUG;
		columns = columns * 2;
	}
	
	/**
	 * Fills matrix with zeroes
	 */
	public void zeroes(){
		for(int r = 0; r< rows; r++){
			for(int c = 0; c< columns; c++){
				matrix[r][c] = 0;
			}
		}
	}
	
	/**
	 * Generates identity matrix
	 */
	public void identity(){
		for(int r = 0; r< rows; r++){
			for(int c = 0; c< columns; c++){
				if(r == c){
					matrix[r][c] = 1;
				}
				
				else{
					matrix[r][c] = 0;
				}
			}
		}
	}
	
	/**
	 *  Adds matrix to matrix2
	 * @param matrix2
	 */
	public void add(myMatrix matrix2){
		if(rows == matrix2.rows & columns == matrix2.columns){
			for(int r = 0; r< rows; r++){
				for(int c = 0; c< columns; c++){
					matrix[r][c] = matrix[r][c] + matrix2.matrix[r][c];
				}
			}
		}
	}
	
	/**
	 * Subtracts matrix2 from matrix
	 * @param matrix2
	 */
	public void subtract(myMatrix matrix2){
		myMatrix matrixN = matrix2.deepCopyMatrix();
		// Negates matrix2 and then adds the matrices together
		matrixN.scalarMultiply(-1);
		add(matrixN);
	}
	
	public void scalarMultiply(double scalar){
			for(int r = 0; r< rows; r++){
				for(int c = 0; c< columns; c++){
					matrix[r][c] = matrix[r][c] * scalar;
				}
			}
	}
	
	public myMatrix scalarMultiplyR(double scalar){
		myMatrix x = new myMatrix(rows,columns);
		for(int r = 0; r< rows; r++){
			for(int c = 0; c< columns; c++){
				x.matrix[r][c] = matrix[r][c] * scalar;
			}
		}
		return x;
}
	
	public myMatrix mean(){
		double sum = 0.0;
		myMatrix meanVector = new myMatrix(columns, 1);
		for(int c = 0; c < columns; c++){
			sum = 0;
			for(int r = 0; r< rows; r++){	
					sum = sum + matrix[r][c];
				}
			meanVector.matrix[c][0] = sum/(rows);
		}		
		
		this.meanVector = meanVector;
		return meanVector;
	}
	
	public double trace(){
		double tracesum = 0;
		
		if(rows == columns){
			for(int i = 0; i < rows; i++){
				tracesum+= matrix[i][i];
			}
		}
		
		else{
			System.out.println("Matrix isn't square");
		}
		return tracesum;
	}
	
	public myMatrix getRow(int rowindex){
		
		myMatrix row = new myMatrix(1, columns);
		
		for(int c = 0; c < columns; c++){
			row.matrix[0][c] = matrix[rowindex][c];
		}
		
		return row;
	}
	
	public double getMean(){
		return mean;
	}
	
	public myMatrix getMeanVector(){
		return meanVector;
	}
	
	public void setClassification(String classification){
		this.classification = classification;
	}
	
	public String getClassification(){
		return classification;
	}
	
	/**
	 * Rounds matrix to avoid float point error (might not need)
	 */
/*	public void printMatrix(){
		for(int r = 0; r < rows; r++){
			System.out.print("[ ");
			for(int c = 0; c< columns; c++){
				System.out.print(matrix[r][c] + " ");
			}
			System.out.println("]");
		}
	}*/
	
	/**
	 * Prints matrix to console
	 */
	public void printMatrix(){
		for(int r = 0; r < rows; r++){
			System.out.print("[ ");
			for(int c = 0; c< columns; c++){
				System.out.print(String.format("%.2f",matrix[r][c]) + " ");
			}
			System.out.println("]");
		}
		System.out.println();
	}
	
	public void printPoints(){
		for(int r = 0; r < rows; r++){
			//System.out.print("(");
			for(int c = 0; c< columns; c++){
				if(c == 0){
					System.out.print(matrix[r][c] + ",");
				}
				else{
					System.out.print(matrix[r][c]);
				}
				
			}
			System.out.println();
			//System.out.println(")");
		}
	}
	
	public void printBrackets(){
		System.out.print("{");
		for(int r = 0; r < rows; r++){
			
			if(r == 0){
				System.out.print("{");
			}
			
			else{
				System.out.print(",{");
			}
			for(int c = 0; c< columns; c++){
				if(c < columns-1){
					System.out.print(matrix[r][c] + ",");
				}
				else{
					System.out.print(matrix[r][c] + "");
				}	
			}
			System.out.println("}");
		}
		System.out.println("}");
	}
	
	public void multiply(myMatrix matrix2){
		int m = rows;
		int p = matrix2.columns;
		double sum= 0;

		
		if(columns == matrix2.rows){
			myMatrix multMatrix = new myMatrix(m, p);
			for (int i = 0; i < m; i ++){	
				
				for (int k = 0; k < p; k++){
					
					for (int j = 0; j < columns; j++){					
	
							sum = sum + (matrix[i][j] * matrix2.matrix[j][k]);
					}
						
				multMatrix.matrix[i][k] = sum;
				sum = 0;
				}
		}
		
		matrix = multMatrix.matrix;
		}
		columns = p;
		
	}
	
	public myMatrix multiplyR(myMatrix matrix2){
		int m = rows;
		int p = matrix2.columns;
		myMatrix multMatrix = new myMatrix(m, p);
		double sum= 0;

		
		if(columns == matrix2.rows){
			for (int i = 0; i < m; i ++){	
				
				for (int k = 0; k < p; k++){
					
					for (int j = 0; j < columns; j++){					
	
							sum = sum + (matrix[i][j] * matrix2.matrix[j][k]);
					}
						
				multMatrix.matrix[i][k] = sum;
				sum = 0;
				}
		}
		}
		columns = p;
		
		return multMatrix;
	}
	
	public myMatrix covariance(){

		myMatrix[] vectorArray = new myMatrix[rows];
		
		// Compile x and y into r x 2 matrix and subtract means from measurements
		for(int r = 0; r < rows; r++){
			vectorArray[r] = new myMatrix(columns, 1);
			for(int c = 0; c < columns; c++){	
				vectorArray[r].matrix[c][0] = matrix[r][c] - meanVector.matrix[c][0];
			}
		}	
		
		
		
		// Multiply each r x 2 matrix by its transpose
		for(int r = 0; r < rows; r++){
			vectorArray[r].multiply(vectorArray[r].transpose());
		}
		
		int covRows = vectorArray[0].rows;
		int covColumns = vectorArray[0].columns;
		
		myMatrix covarianceMatrix = new myMatrix(covRows,covColumns);
		
		// Sum all matrices	
		for(int i = 0; i < vectorArray.length; i++){
				//System.out.println("Covariance Matrix");
				//covarianceMatrix.printMatrix();
				//System.out.println("+");
				//vectorArray[i].printMatrix();
				
				//System.out.println("=");
				covarianceMatrix.add(vectorArray[i]); 
				//covarianceMatrix.printMatrix();
		}
		covarianceMatrix.scalarMultiply(1.0/rows);
		
		return covarianceMatrix;
	}
	
	public double innerProduct(myMatrix b){
		double innerProd = 0;
		
		for(int r = 0; r < rows; r++){
			for(int c = 0; c < columns; c++){
				innerProd += matrix[r][c] * b.matrix[r][c];
			}
		}
		
		return innerProd;
	}
	
	public double[][] deepCopy(){
		double[][] deepCopy = new double[rows][columns];
		for(int r = 0; r < rows; r++){
			for(int c = 0; c < columns; c++){
				deepCopy[r][c] = matrix[r][c];
			}
		}
		
		return deepCopy;
	}
	
/*	public void round(){
		for(int r = 0; r < rows; r++){
			for(int c = 0; c < columns; c++){
				if(matrix[r][c]< .00000001){
					matrix[r][c] = 0;
				}
				if(Math.abs(matrix[r][c] - 1) < .00000001){
					matrix[r][c] = 1;
				}
				
			}
		}
	}*/
	
	  ////////////////////
	 // ROW OPERATIONS///
	////////////////////
	
	/**
	 * Swaps row j with pivot index, p
	 * @param j
	 * @param p
	 */
	public void swapRows(int j, int p){
			for(int c = 0; c < columns; c++){
				double temp = matrix[j][c];
				matrix[j][c] = matrix[p][c];
				matrix[p][c] = temp;
		}
	}
	
	public void multiplyRow(double multiple, int row){
		for(int c = 0; c < columns; c++){
			matrix[row][c] = matrix[row][c] * multiple;
		}
	}
	
	public void addRow(int i, int j){
		for(int c = 0; c < columns; c++){
			matrix[i][c] = matrix[i][c] + matrix[j][c];
		}
	}
	
	public void subtractRow(double[]rowJ, int i){
		for(int c = 0; c < columns; c++){
			matrix[i][c] = matrix[i][c] - rowJ[c];
		}
	}
	
	  /////////////////////
	 // Transformations //
	/////////////////////
	
	public myMatrix gaussJordan(double [] coefficients){
		myMatrix C = new myMatrix(rows, columns);
		C.matrix = deepCopy();
		C.augmentCoefficientMatrix(coefficients);
		
		//C.printMatrix();
		int E = 1;
		int p = 0;
			
		outerloop:
		for(int j = 0; j < rows; j++){
			
			for(int i = 0; i  < rows; i++){
				p = 0;
				if(Math.abs(C.matrix[i][j]) > Math.abs(C.matrix[p][j])){
					p = i;
				}
			}
			
			if(C.matrix[p][j] == 0){
				E = 0;
				System.out.println("No unique solution");
				break outerloop;
			}
			
			if(p>j){
				C.swapRows(j, p);
				//System.out.println("Swapping rows");
				//C.printMatrix();
			}
			
			C.multiplyRow((1/C.matrix[j][j]), j);
			//System.out.println("Multiplying row " + j + " by 1/C[j][j]");
			//C.printMatrix();
			//System.out.println();
			
			for(int i = 0; i < rows; i++){
				if(i != j){
					double[] JcIJ = new double[columns+1];
					for(int c = 0; c < C.columns; c++){
						JcIJ[c] = C.matrix[i][j] * C.matrix[j][c];
					}
					
					C.subtractRow(JcIJ, i);
					//System.out.println("Subtracting C[i][j]* row " + j + " from row " + i);
					//System.out.println();
				}
				
				//C.printMatrix();
			}
			
			
		}
		//System.out.println("Final");
		//C.printMatrix();
		myMatrix newCoefficients = new myMatrix(columns, 1);
		for(int i = 0; i < coefficients.length; i++){
			newCoefficients.matrix[i][0] = C.matrix[i][C.columns-1];
		}
		
		return newCoefficients;
		
	}
	
	public myMatrix inverse(){
		myMatrix C = new myMatrix(rows,columns);
		C.matrix = deepCopy();
		
		C.augmentIdentityMatrix();
		
		
		int E = 1;
		int p = 0;
			
		outerloop:
		for(int j = 0; j < rows; j++){
			
			for(int i = 0; i  < rows; i++){
				p = 0;
				if(Math.abs(C.matrix[i][j]) > Math.abs(C.matrix[p][j])){
					p = i;
				}
			}
			
			if(C.matrix[p][j] == 0){
				E = 0;
				System.out.println("No unique solution");
				break outerloop;
			}
			
			if(p>j){
				C.swapRows(j, p);
				//System.out.println("Swapping rows");
				//C.printMatrix();
			}
			
			C.multiplyRow((1/C.matrix[j][j]), j);
			//System.out.println("Multiplying row " + j + " by 1/C[j][j]");
			//C.printMatrix();
			//System.out.println();
			
			for(int i = 0; i < rows; i++){
				if(i != j){
					double[] JcIJ = new double[columns*2];
					for(int c = 0; c < C.columns; c++){
						JcIJ[c] = C.matrix[i][j] * C.matrix[j][c];
					}
					
					C.subtractRow(JcIJ, i);
					//System.out.println("Subtracting C[i][j]* row " + j + " from row " + i);
					//System.out.println();
				}
				
				//C.printMatrix();
			}
			
			
		}
		//System.out.println("Final");
		myMatrix Ci = new myMatrix(rows, C.columns/2);
		
		for(int r = 0; r < rows; r++){
			for(int c = columns; c < C.columns; c++){
				Ci.matrix[r][c-columns] = C.matrix[r][c];
			}
		}
		
		//Ci.printMatrix();
		
		//Ci.printMatrix();
		
		return Ci;
	}
	
	public myMatrix gauss(double [] coefficients){
		myMatrix C = new myMatrix(rows, columns);
		C.matrix = matrix;
		C.augmentCoefficientMatrix(coefficients);
		C.printMatrix();
		int E = 1;
		int p = 0;
			
		outerloop:
		for(int j = 0; j < rows; j++){
			
			for(int i = j; i  < rows; i++){
				p = j;
				if(Math.abs(C.matrix[i][j]) > Math.abs(C.matrix[p][j])){
					p = i;
				}
			}
			
			if(C.matrix[p][j] == 0){
				E = 0;
				System.out.println("No unique solution");
				break outerloop;
			}
			
			if(p>j){
				C.swapRows(j, p);
				System.out.println("Swapping rows");
				C.printMatrix();
			}
			
			for(int i = 0; i < rows; i++){
				if(i > j){
					double[] JcIJ = new double[columns+1];
					for(int c = 0; c < C.columns; c++){
						JcIJ[c] = (C.matrix[i][j]/C.matrix[j][j]) * C.matrix[j][c];
					}
					
					C.subtractRow(JcIJ, i);
					System.out.println("Subtracting C[i][j]* row " + j + " from row " + i);
					System.out.println();
				}
				
				C.printMatrix();
			}
			
			
		}
		System.out.println("Final");
		C.printMatrix();
		
		return C;
		
	}
	
	public double determinantFinder(){
	myMatrix A = new myMatrix(rows, columns);
	A.matrix = deepCopy();
	double determinant=1;
	int r = 0;
	int p = 0;
		
	outerloop:
	for(int j = 0; j < rows; j++){
		
		for(int i = j; i  < rows; i++){
			p = j;
			if(Math.abs(A.matrix[i][j]) > Math.abs(A.matrix[p][j])){
				p = i;
			}
		}
		
		if(A.matrix[p][j] == 0){
			determinant = 0;
			System.out.println("No unique solution");
			break outerloop;
		}
		
		if(p>j){
			A.swapRows(j, p);
			//System.out.println("Swapping rows");
			//A.printMatrix();
			r++;
		}
		
		for(int i = 0; i < rows; i++){
			if(i > j){
				double[] JcIJ = new double[columns+1];
				for(int c = 0; c < A.columns; c++){
					JcIJ[c] = (A.matrix[i][j]/A.matrix[j][j]) * A.matrix[j][c];
				}
				
				A.subtractRow(JcIJ, i);
				//System.out.println("Subtracting C[i][j]* row " + j + " from row " + i);
				//System.out.println();
			}
			
			//A.printMatrix();
		}				
	}
	
	for(int j = 0; j < rows; j++){
		determinant *= A.matrix[j][j];
	}
	
	determinant *= Math.pow(-1, r);
	
	//System.out.println("Final");
	//A.printMatrix();
	
	return determinant;
	}
	
	public myMatrix leverrier(){

		myMatrix A = this.deepCopyMatrix();
		int n = A.rows;
		myMatrix roots = new myMatrix(1, A.columns);
		
		myMatrix identity = new myMatrix(n, A.columns,"identity");
		myMatrix ai = new myMatrix(n, A.columns);
		
		//Set bn to A
		myMatrix Bn = A.deepCopyMatrix();
		
		//Calculate and store an
		double an = -(Bn.trace());
		roots.matrix[0][n-1] = an; 
		
		//Find remaining roots
		for(int k = n-1; k > 0; k--){
			ai = identity.deepCopyMatrix();
	
			//Multiply identity matrix by a k+1
			ai.scalarMultiply(an);
			
			//Add ai to bk+1
			ai.add(Bn);
			
			//Multiply bk (ai) by matrix A
			ai.multiply(A);
			
			//Calculate ak = -(trace(bk)/(n-k+1)
			an = -(ai.trace()/(n-k+1));
			
			//Store root
			roots.matrix[0][k-1] = an;
			
			//Store bk+1
			Bn = ai.deepCopyMatrix();
		}
		
		return roots;
	}
	
	public myMatrix evalQuadratic(){
		myMatrix roots = new myMatrix(1, columns);
		
		double b = this.matrix[0][1];
		double c = this.matrix[0][0];
		
		double r1 = (-b + Math.sqrt(Math.pow(b, 2) - 4 * 1 * c))/2;
		
		double r2 = (-b - Math.sqrt(Math.pow(b, 2) - 4 * 1 * c))/2;
		
		roots.matrix[0][0] = r1;
		roots.matrix[0][1] = r2;
		
		return roots;
		
	}
	
	public double norm(){
		double rowSum = 0;
		double maxRowSum = 0;
		
		
		for(int r = 0; r < this.rows; r++){
			rowSum = 0;
			for(int c = 0; c < this.columns; c++){
				rowSum += Math.abs(this.matrix[r][c]);
			}
			
			if(rowSum > maxRowSum){
				maxRowSum = rowSum;
			}
		}
		
		return maxRowSum;
	}
	
	public double powerMethod(){
		double e;
		double epsilon = 0.0001;
		double m = 200;
		int k = 0;
		double mu;
		
		myMatrix A = this.deepCopyMatrix();
		myMatrix y = new myMatrix(A.columns, 1);
		myMatrix r;
		
		for(double i = 0.0; i < y.rows; i++){
			y.matrix[(int)i][0] = i+1;
		}
		
		System.out.println("THIS IS A");
		A.printMatrix();
		
		System.out.println("THIS IS Y");
		y.printMatrix();
		
		myMatrix x = A.multiplyR(y);
		
		A.multiply(y);
		
		System.out.println("THIS IS A AGAIN");
		x.printMatrix();
		
		
		do{
			x.scalarMultiplyR(1/x.norm());
			
			y = x.scalarMultiplyR(1/x.norm());
			
			x = A.multiplyR(y);
			
			myMatrix yt = y.transpose();
			
			yt.multiply(x);
			
			myMatrix yt2 = y.transpose();
			
			yt2.multiply(x);
			
			mu = yt.matrix[0][0]/yt2.matrix[0][0];
			
			r = y.scalarMultiplyR(mu);
			
			r.subtract(x);
					
			k++;
		} while(r.norm()>epsilon && k < m);
		
		y.printMatrix();
		
		return mu;
	}
	
	public Complex[] fft(double d){
		int N = rows;
	
		int r = N/2;
		Complex[] Z = new Complex[N];
		
		if(d > 0){
			for(int i = 0; i < N; i++){
				Z[i] = new Complex(this.matrix[i][0], 0.0);
			}
		}
		
		else{
			for(int i = 0; i < N; i++){
				Z[i] = new Complex(cmatrix[i].re,cmatrix[i].im);
			}
		}

		
		double theta = (-2*Math.PI*d)/N;
		
		Complex j;
		Complex w;
		Complex u;
		Complex nRecip = new Complex(1.0/(double)N,0);
		
		for(int i = 1; i < N; i=2*i){
			w = new Complex(Math.cos(i*theta), Math.sin(i*theta));
		
			for(int k = 0 ; k < N-1 ; k+= 2*r){
				u = new Complex(1, 0.0);
				
				for(int m = 0; m <= r-1; m++){
					//System.out.println("i="+ i + "; k=" + k + "; m=" + m + "; r=" + r);
					Complex t =  Z[k+m].minus(Z[k+m+r]);
					Z[k+m] = Z[k+m].plus(Z[k+m+r]);
					Z[k+m+r] = t.times(u);
					u=w.times(u);
				}
			}
			r = r/2;
		}
		
		for(int i = 0; i < N; i++){
			//System.out.println(Z[i].toString());
		}
		
		//System.out.println("Rearranging Results");
		for(int i = 0; i < N; i++){
			r = i;
			int k = 0;
			//System.out.println("N" + N);
			
			for(int m = 1; m < N-1; m = 2*m){
				//System.out.println("i="+ i + "; k=" + k + "; m=" + m + "; r=" + r);
				k = 2*k + (r%2);
				r = r/2;
			}
			
			if(k > i){
				Complex t = new Complex(Z[i].re,Z[i].im);
				Z[i] = Z[k];
				Z[k] = t;
			}

		}	
		
		if(d < 0){
/*		System.out.println("Inverse FFT");
		System.out.println(N);*/
		for(int i = 0; i < N; i++){
			//System.out.println("INITIAL Z[" + i +"] = " + Z[i]);
			//this.matrix[i][0] = (Z[i].re/(double)N);
			Complex temp = new Complex(Z[i].re, Z[i].im);
			Z[i] = temp.times(nRecip);
			//System.out.println(Z[i].re);
			//System.out.println(N);
			
			//System.out.println(this.matrix[i][0]);
			//System.out.println("Z[" + i +"] = " + Z[i]);
		}
	}
	for(int i = 0; i < N; i++){
		//System.out.println("Z[" + i +"] = " + Z[i]);
		this.cmatrix[i] = new Complex(Z[i].re, Z[i].im);
		//System.out.println(Z[i].toString());
	}
		
	return Z;
	
	}
	
	public myMatrix psd(){
		Complex [] Zconj = new Complex[this.rows];
		
		for(int i = 0; i < rows; i++){
			Zconj[i] = cmatrix[i].conjugate();
		}
		
		myMatrix psd = new myMatrix(rows,1);
		
		for(int i = 0; i < rows; i++){
			psd.matrix[i][0] = Zconj[i].times(cmatrix[i]).re;
			psd.cmatrix[i] = Zconj[i].times(cmatrix[i]);
		}
		
		return psd;
	}
	
	public myMatrix revpsd(myMatrix fft){
		Complex [] Zconj = new Complex[this.rows];
		
		for(int i = 0; i < rows; i++){
			Zconj[i] = fft.cmatrix[i].conjugate();
		}
		
		myMatrix revpsd = new myMatrix(rows,1);
		
		for(int i = 0; i < rows; i++){
			revpsd.cmatrix[i] = this.cmatrix[i].divides(Zconj[i]);
		}
		
		return revpsd;
	}
}
