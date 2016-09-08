import java.util.ArrayList;

/**
 * 
 * @author Rene
 *
 */

public class MatrixProjectI {
	public static void main(String[] args){
/*		myMatrix a = new myMatrix(3, 3);
		double[][] testMatrix = {{5,-2,3},{0,7,-8}, {4,3,1}};
		//double[][] testMatrix2 = {{0,3},{-2,-1},{0,4}};
		
		a.matrix = testMatrix;
		
		double determinant = a.determinantFinder();
		
		System.out.println();
		System.out.println("MOMENT OF TRUTH");
		
		System.out.println(determinant);
*/
		
		//myMatrix C = test.gaussJordan(coefficients);
		
		ArrayList<myMatrix> G1 = new ArrayList<myMatrix>();
		ArrayList<myMatrix> G1E = new ArrayList<myMatrix>();
		ArrayList<myMatrix> G2 = new ArrayList<myMatrix>();
		ArrayList<myMatrix> G2E = new ArrayList<myMatrix>();
		
		myMatrix textMatrix = new myMatrix(4, "S2016 P1 data.txt");
		int rows  = textMatrix.rows;
		int columns  = textMatrix.columns;
		
		

		myMatrix x1 = new myMatrix(rows, 1);
		myMatrix x2 = new myMatrix(rows, 1);
		
		myMatrix y1 = new myMatrix(rows, 1);
		myMatrix y2 = new myMatrix(rows, 1);
		
		myMatrix[] matrixArray = {x1,y1,x2,y2};
		
		//Instantiate matrices
		for(int r = 0; r < rows; r++){
			for(int c = 0; c < columns; c++){
				matrixArray[c].matrix[r][0] = textMatrix.matrix[r][c];
			}
		}
		
		myMatrix xy1 = x1.augmentMatrix(y1);
		
		myMatrix xy2 = x2.augmentMatrix(y2);
		
		myMatrix[] matrixArrayz = {xy1, xy2};

		//Find mean of each vector
		
		System.out.println("~~~~~~~~~~~~~~~~~~~~#1~~~~~~~~~~~~~~~~~~~~~~~~~");
		
		System.out.println("Mean vector for m1");
		myMatrix xyMean = xy1.mean();
		xyMean.printMatrix();
		
		System.out.println("Mean vector for m2");
		myMatrix xyMean2 = xy2.mean();
		xyMean2.printMatrix();
		
		for(int c = 0; c < matrixArray.length; c++){
			matrixArray[c].mean();
		}
				
		//Find covariance matrices for each class
		
		System.out.println("~~~~~~~~~~~~~~~~~~~~#2~~~~~~~~~~~~~~~~~~~~~~~~~");
		
		myMatrix covarianceMatrix1 = xy1.covariance();
		System.out.println("CovarianceMatrix1");
		covarianceMatrix1.printMatrix();
		
		myMatrix covarianceMatrix2 = xy2.covariance();
		System.out.println("CovarianceMatrix2");
		covarianceMatrix2.printMatrix();
		
		
		//Find determinants
		
		System.out.println("~~~~~~~~~~~~~~~~~~~~#3~~~~~~~~~~~~~~~~~~~~~~~~~");
		System.out.println();
		
		double determinant1 = covarianceMatrix1.determinantFinder();
		System.out.println("Determinant 1: " + determinant1);
		
		double determinant2 = covarianceMatrix2.determinantFinder();
		System.out.println("Determinant 2: " + determinant2);
		
		//Find inverses of covariance matrices
		
		System.out.println();
		System.out.println("~~~~~~~~~~~~~~~~~~~~#4~~~~~~~~~~~~~~~~~~~~~~~~~");
		System.out.println();
		
		myMatrix covarianceMatrix1I = covarianceMatrix1.inverse();
		System.out.println("CovarianceMatrix1 Inverted: ");
		covarianceMatrix1I.printMatrix();
		
		myMatrix covarianceMatrix2I = covarianceMatrix2.inverse();
		System.out.println("CovarianceMatrix2 Inverted: ");
		covarianceMatrix2I.printMatrix();
		
		//////////////////////////////////////////////////////
		//Evaluate discriminant functions for mean vectors///
		////////////////////////////////////////////////////
		
		System.out.println("~~~~~~~~~~~~~~~~~~~~#6~~~~~~~~~~~~~~~~~~~~~~~~~");
		System.out.println();
		
		double g1scoremean;
		double g2scoremean;
		
		myMatrix xy1MeanVector = xy1.getMeanVector();
		myMatrix xy2MeanVector = xy2.getMeanVector();
		
		//Instantiate Discriminant Functions
		DiscriminantFunction g1fun = new DiscriminantFunction(covarianceMatrix1I, xy1MeanVector, determinant1);
		DiscriminantFunction g2fun = new DiscriminantFunction(covarianceMatrix2I, xy2MeanVector, determinant2);
		
		//evalVector xy1
		myMatrix evalVector = xy1MeanVector.deepCopyMatrix();
		
		//g1
		g1scoremean = g1fun.evaluateFunction(evalVector);
		
		System.out.println("G1 "+ g1scoremean);
		
		//g2
		g2scoremean = g2fun.evaluateFunction(evalVector);
		
		System.out.println("G2 "+ g2scoremean);
		
		
		//evalVector xy2 
		evalVector = xy2MeanVector.deepCopyMatrix();
		
		//g1
		g1scoremean = g1fun.evaluateFunction(evalVector);
		
		System.out.println("G1 "+ g1scoremean);
		
		//g2
		g2scoremean = g2fun.evaluateFunction(evalVector);
		
		System.out.println("G2 "+ g2scoremean);
		
		////////////////////////////////////////////////////////////////////////////
		//Evaluate discriminant function for all points and classify///////////////
		//////////////////////////////////////////////////////////////////////////
		
		System.out.println();
		System.out.println("~~~~~~~~~~~~~~~~~~~~#7~~~~~~~~~~~~~~~~~~~~~~~~~");
		System.out.println();
		
		double g1score = 0;
		double g2score = 0;
		
		myMatrix[][] classificationArray = new myMatrix[rows][2];
		myMatrix evalMatrix;
		for(int v = 0; v < matrixArrayz.length; v++){
			myMatrix pointVector = matrixArrayz[v];
			
			System.out.println("CLASSIFYING THESE POINTS");
			matrixArrayz[v].printMatrix();
			System.out.println();
			
			for(int r = 0; r < pointVector.rows; r++){
				evalMatrix = new myMatrix(2,1);
				g1score = 0;
				g2score = 0;
				


				evalMatrix = pointVector.getRow(r);
				System.out.println("EVALUATING");
				evalMatrix.printMatrix();
				
				//G1 Score
				g1score = g1fun.evaluateFunction(evalMatrix);
				
				System.out.println("G1 Score: " + g1score);
				System.out.println();
				
				System.out.println("EVALUATING AGAIN");
				evalMatrix.printMatrix();
				
				//G2 Score
				g2score = g2fun.evaluateFunction(evalMatrix);

				System.out.println("G2 Score: " + g2score); 
				System.out.println();

				
				if(Math.abs(g1score) < Math.abs(g2score)){
					evalMatrix.setClassification("G1");
					
					if(v == 0){
						G1.add(evalMatrix.deepCopyMatrix());
						System.out.println("CLASSIFIED AS G1");
					}
					
					else{
						G1E.add(evalMatrix.deepCopyMatrix());
						System.out.println("CLASSIFIED AS G1E");
					}
					
				}
				
				else{
					evalMatrix.setClassification("G2");
					
					if(v == 1){
						G2.add(evalMatrix.deepCopyMatrix());
						System.out.println("CLASSIFIED AS G2");
					}
					
					else{
						G2E.add(evalMatrix.deepCopyMatrix());
						System.out.println("CLASSIFIED AS G2E");
					}
				}
				
				
			}
		}

		
		
		
		//Estimate and plot the boundary contour
		System.out.println("Points in G1: " + G1.size());
		for(myMatrix g1c: G1){
			g1c.printPoints();
		}
		System.out.println();
		
		System.out.println("Points misplaced in G1: " + G1E.size());
		for(myMatrix g1ec: G1E){
			g1ec.printPoints();
		}
		System.out.println();
		
		System.out.println("Points in G2: " + G2.size());
		for(myMatrix g2c: G2){
			g2c.printPoints();
		}
		System.out.println();
		
		System.out.println("Points misplaced in G2 :" + G2E.size());
		for(myMatrix g2ec: G2E){
			g2ec.printPoints();
		}
		System.out.println();
		//GaussJordan Elimination to estimate solution of linear system
		myMatrix linearSystem = new myMatrix(8,8);
		double [][] variables = {{2, 3, 8, 2, 0, 1, 4, -3},{-14, 3 , 1, 2, -1, 1, 2, 1},{2, 13, 2, 2, 3, -4, 2, 1},{1,7,0,8,-1,6,-1,1},{3,-2,2,3,0,2,3,4},{1,1,2,2,1,2,-3,5},{-9,0,3,1,-3,-1,0,-2},{2,-1,0,0,3,0,1,-3}};
		linearSystem.matrix = variables;
		
		double[] coefficients = {5,3,11,2,21,1,12,22};
		
		//linearSystem.printMatrix();
		
		//Form meanvector of A
		linearSystem.mean();
		
		//Find Covariance matrix of A
		double determinantA= linearSystem.determinantFinder();
		
		System.out.println("Determinant of A :" + determinantA);
		
		myMatrix AI = linearSystem.inverse();
		
		AI.printMatrix();

		double determinantAI = AI.determinantFinder();
		
		System.out.println("Determinant of AI :" + determinantAI);
		
		myMatrix solvedCoefficients = linearSystem.gaussJordan(coefficients);
		
		solvedCoefficients.printMatrix();
		myMatrix A = linearSystem.deepCopyMatrix();
		A.multiply(AI);
		
		A.printMatrix();
		
		//Find condition number of A
		double rowSum = 0;
		double maxRowSum = 0;
		
		for(int r = 0; r < linearSystem.rows; r++){
			rowSum = 0;
			for(int c = 0; c < linearSystem.columns; c++){
				rowSum += Math.abs(linearSystem.matrix[r][c]);
			}
			
			if(rowSum > maxRowSum){
				maxRowSum = rowSum;
			}
		}
		
		linearSystem.printMatrix();
		System.out.println(maxRowSum);
		
		double rowSumI = 0;
		double maxRowSumI = 0;
		for(int r = 0; r < AI.rows; r++){
			rowSumI = 0;
			for(int c = 0; c < AI.columns; c++){
				rowSumI += Math.abs(AI.matrix[r][c]);
			}
			
			if(rowSumI > maxRowSumI){
				maxRowSumI = rowSumI;
			}
		}
		
		AI.printMatrix();
		System.out.println(maxRowSumI);		
		
		System.out.println("Condition Number :" + maxRowSum*maxRowSumI);
	}
}