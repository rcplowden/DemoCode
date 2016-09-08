
public class fftTest {
	public static void main(String[] args){
		double[][] xReal = {{26160.0},{19011.0},{18757.0},{18405.0},{17888.0},{14720.0},{14285.0},{17018.0},{18014.0},{17119.0},{16400.0},{17497.0},{17846.0},{15700.0},{17636.0},{17181.0}};
		
		//myMatrix X = new myMatrix(16,1);
		
		myMatrix X = new myMatrix(16, 1);
		X.matrix = xReal;
		
		System.out.println("FFT");
		X.fft(1);
		
		//X.cmatrix[0] = new Complex(0,0);
		
		for(int i = 0; i < X.rows; i++){
			System.out.println(X.cmatrix[i]);
		}
		
		myMatrix xpsd = X.psd();
		
		System.out.println("PSD");
		for(int i = 0; i < X.rows; i++){
			System.out.println(xpsd.matrix[i][0]);
		}
		
		System.out.println("Smoke");
		myMatrix smoke = xpsd.revpsd(X);
		
		for(int i = 0; i < X.rows; i++){
			System.out.println(smoke.cmatrix[i]);
		}

		
		System.out.println();
		
//		System.out.println("INVERSE FFT");
//		X.fft(-1);
		

		for(int i = 0; i < X.rows; i++){
			System.out.println(X.cmatrix[i].re);
		}

		
		
		
		
	}
}
