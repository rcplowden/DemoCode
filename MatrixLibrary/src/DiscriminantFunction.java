
public class DiscriminantFunction {
	private myMatrix covarianceI;
	private myMatrix meanVector;
	private double determinant;
	
	public DiscriminantFunction(myMatrix covI, myMatrix meanV, double determinant){
		covarianceI = covI;
		meanVector = meanV;
		this.determinant = determinant;
	}
	
	public double evaluateFunction(myMatrix vector){
		//evalVector 1, g1
		myMatrix evalVector = vector.deepCopyMatrix();
		
		//Subtract mean vector from evalVector
		evalVector.subtract(meanVector);
		
		//Transpose meanVector
		myMatrix evalVectorT = evalVector.transpose();
		
		//Multiply evalVectorT x covarianceMatrix1T
		evalVectorT.multiply(covarianceI);
		
		evalVectorT.multiply(evalVector);
		
		double discriminantMeanValueg1 = -0.5 * evalVectorT.matrix[0][0] - 0.5 * Math.log(determinant);
		
		return discriminantMeanValueg1;
	}
}
