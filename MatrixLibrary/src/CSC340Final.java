import java.awt.Color;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class CSC340Final {

	public static void main(String[] args) throws IOException {
		final double SAMPLES = 1024.0;
		final int[] SVALS = {10,100,10000};
		
		//File outputs
		File txtFileEven = new File("C:/users/Rene/Desktop/sinResultsEven.txt");
		File txtFileOdd = new File("C:/users/Rene/Desktop/sinResultsOdd.txt");
		File txtFilefPSD = new File("C:/users/Rene/Desktop/fPSD.txt");
		File txtFilegPSD = new File("C:/users/Rene/Desktop/gPSD.txt");
		File txtFileFilter = new File("C:/users/Rene/Desktop/Filter.txt");
		File txtFileXYPSD = new File("C:/users/Rene/Desktop/xyPSD.txt");
		File txtFileTone = new File("C:/users/Rene/Desktop/tonePSD.txt");
		
		if(!txtFileEven.exists()){
			txtFileEven.createNewFile();
		}
		
		if(!txtFileOdd.exists()){
			txtFileOdd.createNewFile();
		}
		
		FileWriter evenFw = new FileWriter(txtFileEven.getAbsoluteFile());
		BufferedWriter evenBw = new BufferedWriter(evenFw);
		
		FileWriter oddFw = new FileWriter(txtFileOdd.getAbsoluteFile());
		BufferedWriter oddBw = new BufferedWriter(oddFw);
		
		FileWriter fPSDw = new FileWriter(txtFilefPSD.getAbsoluteFile());
		BufferedWriter fPSDbw = new BufferedWriter(fPSDw);
		
		FileWriter gPSDw = new FileWriter(txtFilegPSD.getAbsoluteFile());
		BufferedWriter gPSDbw = new BufferedWriter(gPSDw);
		
		FileWriter xyPSDw = new FileWriter(txtFileXYPSD.getAbsoluteFile());
		BufferedWriter xyPSDbw = new BufferedWriter(xyPSDw);
		
		FileWriter FilterW = new FileWriter(txtFileFilter.getAbsoluteFile());
		BufferedWriter filterBW = new BufferedWriter(FilterW);
		
		FileWriter txtFileToneW = new FileWriter(txtFileTone.getAbsoluteFile());
		BufferedWriter txtFileToneBW = new BufferedWriter(txtFileToneW);
		
		// #1
		//
		// parts a and b
		//
		// Odd harmonics
		myMatrix f = new myMatrix(1024,1);
		//Even harmonics
		myMatrix g = new myMatrix(1024,1);
		double sum = 0.0;
		
		// Calculate data points for odd harmonics
		oddBw.write("Odd Harmonics");
		oddBw.newLine();
		
		for(int S: SVALS){
			oddBw.write("S: " + S);
			oddBw.newLine();
			for(double i = 0.0; i < 1.0; i+= 1/SAMPLES){
				sum = 0.0;
				for(int k = 1; k <= S; k++){
					double val = Math.sin((2*Math.PI)*(2*k-1)*i)/(2*k-1);
					//System.out.println("i = " + i + "k = " + k + "val = " + val );
					sum += val;
				}
				//System.out.println(i);
			f.matrix[(int)(i*SAMPLES)][0] = sum;
			}
			for(int i = 0; i < SAMPLES; i++){
				oddBw.write("" + f.matrix[i][0]);
				oddBw.newLine();
			}
		}
		
		// Calculate data points for even harmonics
		evenBw.write("Even Harmonics");
		evenBw.newLine();
		
		for(int S: SVALS){
			evenBw.write("S: " + S);
			evenBw.newLine();
			for(double i = 0.0; i < 1.0; i+= 1/SAMPLES){
				sum = 0.0;
				for(int k = 1; k <= S; k++){
					double val = Math.sin((2*Math.PI)*(2*k)*i)/(2*k);
					//System.out.println("i = " + i + "k = " + k + "val = " + val );
					sum += val;
				}
				//System.out.println(i);
			g.matrix[(int)(i*SAMPLES)][0] = sum;
			}
			for(int i = 0; i < SAMPLES; i++){
				evenBw.write("" + g.matrix[i][0]);
				evenBw.newLine();
			}
		}
		



		
		//fft f and g for S = 10000
		f.fft(1);
		myMatrix fPSD = f.psd();
		
		g.fft(1);
		myMatrix gPSD = g.psd();
		
		
		//Write results of f and g to text files
		fPSDbw.write("f FFT vals");
		fPSDbw.newLine();
		
		for(int i = 0; i < f.rows; i++){
			fPSDbw.write("" + f.cmatrix[i]);
			fPSDbw.newLine();
		}
		
		fPSDbw.newLine();
		
		fPSDbw.write("PSD for f S = 10,000");
		fPSDbw.newLine();
		for(int i = 0; i < fPSD.rows; i++){
			fPSDbw.write("" + fPSD.matrix[i][0]);
			fPSDbw.newLine();
		}
					
		gPSDbw.write("g FFT vals");
		gPSDbw.newLine();
		
		for(int i = 0; i < g.rows; i++){
			gPSDbw.write("" + g.cmatrix[i]);
		}
		
		gPSDbw.newLine();
		
		gPSDbw.write("PSD for g S = 10,000");
		gPSDbw.newLine();
		
		for(int i = 0; i < gPSD.rows; i++){
			gPSDbw.write("" + gPSD.matrix[i][0]);
			gPSDbw.newLine();
		}
		
		// parts c and d
		// Odd harmonics
		myMatrix fPW = new myMatrix(1024,1);
		//Even harmonics
		myMatrix gPW = new myMatrix(1024,1);
		
		 double val = 0.0;
		 
		// Calculate data points for fPW	
		for(double i = 0.0; i < 1.0; i+= 1/SAMPLES){;
			if (i < 0.5){
				val = 0.7836;
			}
			
			else if (i > 0.5){
				val = -0.7836;
			}
			
			else if(i == 0.5){
				val = 0; 
			}
			
			fPW.matrix[(int)(i*SAMPLES)][0] = val;
				//System.out.println(i);
		}
		
		oddBw.newLine();
		
		oddBw.write("fPW Values");
		oddBw.newLine();
		
		for(int i = 0; i < fPW.rows; i++){
			oddBw.write("" + fPW.matrix[i][0]);
			oddBw.newLine();
		}
		
		// Calculate data points for gPW	
		for(double i = 0.0; i < 1.0; i+= 1/SAMPLES){;
			if (i < 0.5){
				val = -3.1452*i + 0.7863;
			}
			
			else if (i > 0.5){
				val = -3.1452*i + 2.3589;
			}
			
			else if(i == 0.5){
				val = 0; 
			}
			
			gPW.matrix[(int)(i*SAMPLES)][0] = val;
				//System.out.println(i);
		}
		
		evenBw.newLine();
		evenBw.newLine();
		
		evenBw.write("gPW Values");
		evenBw.newLine();
		
		for(int i = 0; i < gPW.rows; i++){
			evenBw.write("" + gPW.matrix[i][0]);
			evenBw.newLine();
		}
		
		//fft f and g for S = 10000
		fPW.fft(1);
		myMatrix fPWPSD = fPW.psd();
		
		gPW.fft(1);
		myMatrix gPWPSD = gPW.psd();
		
		fPSDbw.newLine();
		fPSDbw.newLine();
		fPSDbw.newLine();
		
		//Write results of f and g to text files
		
		//f 
		fPSDbw.write("fPW FFT vals");
		fPSDbw.newLine();
		
		for(int i = 0; i < fPW.rows; i++){
			fPSDbw.write("" + fPW.cmatrix[i]);
			fPSDbw.newLine();
		}
		
		fPSDbw.newLine();
		
		fPSDbw.write("PSD for fPW");
		fPSDbw.newLine();
		for(int i = 0; i < fPWPSD.rows; i++){
			fPSDbw.write("" + fPWPSD.matrix[i][0]);
			fPSDbw.newLine();
		}
		
		
		//g
		gPSDbw.write("gPW FFT vals");
		gPSDbw.newLine();
		
		for(int i = 0; i < gPW.rows; i++){
			gPSDbw.write("" + gPW.cmatrix[i]);
			gPSDbw.newLine();
		}
		
		gPSDbw.newLine();
		
		gPSDbw.write("PSD for gPW");
		gPSDbw.newLine();
		
		for(int i = 0; i < gPWPSD.rows; i++){
			gPSDbw.write("" + gPWPSD.matrix[i][0]);
			gPSDbw.newLine();
		}
		
		
		
		evenBw.close();
		oddBw.close();
		gPSDbw.close();
		fPSDbw.close();
	
	//#2
	
	//Generate data for x(t) and y(t)

	//Sum of sin waves
	myMatrix xt = new myMatrix(256,1);
	//Product of sin waves
	myMatrix yt = new myMatrix(256,1);
	
	final double F1 = 7;
	final double F2 = 17;

	
	
	for(double i = 0.0; i < 1.0; i+= 1.0/256.0){
		double valf1 = Math.sin((2*Math.PI)*F1*i);
		double valf2 = Math.sin((2*Math.PI)*F2*i);
		//System.out.println(i);
		xt.matrix[(int)(i*256)][0] = valf1+valf2;
		yt.matrix[(int)(i*256)][0] = valf1*valf2;
	}
	
	xt.fft(1);
	myMatrix xtPSD = xt.psd();
	
	yt.fft(1);
	myMatrix ytPSD = yt.psd();
	
	xyPSDbw.newLine();
	xyPSDbw.newLine();
	xyPSDbw.write("x(t) PSD");
	xyPSDbw.newLine();
	
	for(int i = 0; i < xt.rows; i++){
		xyPSDbw.write("" + xtPSD.matrix[i][0]);
		xyPSDbw.newLine();
	}
	
	xyPSDbw.newLine();
	xyPSDbw.newLine();
	xyPSDbw.write("y(t) PSD");
	xyPSDbw.newLine();
	
	for(int i = 0; i < yt.rows; i++){
		xyPSDbw.write("" + ytPSD.matrix[i][0]);
		xyPSDbw.newLine();
	}
	
	xyPSDbw.close();
	
	
	//#3
	
	//a
	myMatrix aPulse = new myMatrix(256,1);
	
	for(int i = 0; i < aPulse.rows; i++){
		aPulse.matrix[i][0] = 0.0;
	}
	
	aPulse.matrix[125][0] = 1.0;
	aPulse.matrix[126][0] = 1.0;
	
	aPulse.fft(1);
	myMatrix aPSD = aPulse.psd();

	System.out.println();
	System.out.println("a PSD");
	
	for(int i = 0; i < aPulse.rows; i++){
		System.out.println(""+aPSD.matrix[i][0]);
	}
	
	//b
	myMatrix hX = new myMatrix(256,1);
	
	//phase shift
	double c = 0.0;
	
	for(double i = 0.0; i < 1.0; i+= 1.0/256.0){
		hX.matrix[(int)(i*256)][0] = Math.sin((2*Math.PI)*18*(i-c));
	}
	
	System.out.println();
	System.out.println("H(x)");
	
	hX.fft(1);
	
	System.out.println("FFT VALS");
	for(int i = 0 ; i < hX.rows; i++){
		System.out.println("" + hX.cmatrix[i]);
	}
	
	System.out.println();
	System.out.println("PSD VALS");
	myMatrix hPSD = hX.psd();
	
	for(int i = 0; i < hPSD.rows; i++){
		System.out.println(""+hPSD.matrix[i][0]);
	}
	
	//#4
	myMatrix f10 = new myMatrix(1024, 1);
	
	//LPF
	myMatrix lpf = new myMatrix(1024, 1);
	for(int i = 0; i < lpf.rows; i++){
		lpf.cmatrix[i] = new Complex(0,0);
	}
	for(int i = 0; i < 3; i++){
		lpf.cmatrix[i] = new Complex(1,0);
	}
	for(int i = lpf.rows - 3; i < lpf.rows; i++){
		lpf.cmatrix[i] = new Complex(1,0);
	}
	
	//HPF
	myMatrix hpf = new myMatrix(1024, 1);
	for(int i = 0; i < hpf.rows; i++){
		hpf.cmatrix[i] = new Complex(1,0);
	}
	for(int i = 0; i < 3; i++){
		hpf.cmatrix[i] = new Complex(0,0);
	}
	for(int i = hpf.rows- 3; i < lpf.rows; i++){
		hpf.cmatrix[i] = new Complex(0,0);
	}
	
	//BPF
	myMatrix bpf = new myMatrix(1024, 1);
	for(int i = 0; i < bpf.rows; i++){
		bpf.cmatrix[i] = new Complex(0,0);
	}
	for(int i = 3; i < 7 ; i++){
		bpf.cmatrix[i] = new Complex(1,0);
	}
	
	for(int i = bpf.rows-7; i < bpf.rows-3 ; i++){
		bpf.cmatrix[i] = new Complex(1,0);
	}
	
	//NF
	myMatrix nf = new myMatrix(1024, 1);
	for(int i = 0; i < nf.rows; i++){
		nf.cmatrix[i] = new Complex(1,0);
	}
	for(int i = 3; i < 7 ; i++){
		nf.cmatrix[i] = new Complex(0,0);
	}
	
	for(int i = nf.rows-7; i < bpf.rows-3 ; i++){
		nf.cmatrix[i] = new Complex(0,0);
	}
	
	
	
	
	//terms
	int S = 10;
	
	//generate data for f10
	//System.out.println("Points");
		for(double i = 0.0; i < 1.0; i+= 1.0/SAMPLES){
			sum = 0.0;
			for(int k = 1; k <= S; k++){
				val = Math.sin((2*Math.PI)*(2*k-1)*i)/(2*k-1);
				sum += val;
			}
			//System.out.println(i);
		f10.matrix[(int)(i*SAMPLES)][0] = sum;
		//System.out.println(f10.matrix[(int)(i*SAMPLES)][0]);
		}
		
		f10.fft(1);
		myMatrix f10psd = f10.psd();
		

		
		System.out.println("FFT");
		
		for(int i = 0; i < f10.rows; i ++){
			System.out.println("" + f10.cmatrix[i]);
		}
		
		//LPF
		//System.out.println("FFT*LPF");
		for(int i = 0; i < lpf.rows; i ++){
			lpf.cmatrix[i] = lpf.cmatrix[i].times(f10psd.cmatrix[i]);
			//System.out.println(lpf.cmatrix[i]);
		}
		
		//HPF
		//System.out.println("FFT*HPF");
		for(int i = 0; i < hpf.rows; i ++){
			hpf.cmatrix[i] = hpf.cmatrix[i].times(f10psd.cmatrix[i]);
			//System.out.println(hpf.cmatrix[i]);
		}
		
		//BPF	
		//System.out.println("FFT*BPF");
		for(int i = 0; i < bpf.rows; i ++){
			bpf.cmatrix[i] = bpf.cmatrix[i].times(f10psd.cmatrix[i]);
			//System.out.println(hpf.cmatrix[i]);
		}
		
		//NF
		//System.out.println("FFT*NF");
		for(int i = 0; i < nf.rows; i ++){
			nf.cmatrix[i] = nf.cmatrix[i].times(f10psd.cmatrix[i]);
			//System.out.println(hpf.cmatrix[i]);
		}
		
/*		System.out.println("NF PSD");
		for(int i = 0; i < nf.rows; i++){
			System.out.println(nf.cmatrix[i]);
		}*/
		

		

/*		System.out.println("Filtered FFT");
		
		for(int i = 0; i < f10.rows; i ++){
			System.out.println("" + f10.cmatrix[i]);
		}*/
		
		myMatrix lpfFFT = lpf.revpsd(f10);
		myMatrix hpfFFT = hpf.revpsd(f10);
		myMatrix bpfFFT = bpf.revpsd(f10);
		myMatrix nfFFT = nf.revpsd(f10);
		
		
		lpfFFT.fft(-1);
		hpfFFT.fft(-1);
		bpfFFT.fft(-1);
		nfFFT.fft(-1);
		
		
/*		System.out.println("Low Pass Signal");
		for(int i = 0; i < lpfFFT.rows; i++){
			System.out.println(lpfFFT.cmatrix[i].re);
		}*/
		
/*		System.out.println("High Pass Signal");
		for(int i = 0; i < hpfFFT.rows; i++){
			System.out.println(hpfFFT.cmatrix[i].re);
		}*/
		
/*		System.out.println("Band Pass Signal");
		for(int i = 0; i < bpfFFT.rows; i++){
			System.out.println(bpfFFT.cmatrix[i].re);
		}*/
		
		System.out.println("Notch Signal");
		for(int i = 0; i < nfFFT.rows; i++){
			System.out.println(nfFFT.cmatrix[i].re);
		}
		

/*		System.out.println("f10 PSD");
		for(int i = 0; i < lpfpsd.rows; i++){
			System.out.println(f10psd.matrix[i][0]);
		}*/		
		
/*		System.out.println("LPF Signal");
		for(int i = 0; i < lpf.rows; i ++){
			System.out.println("" + lpf.cmatrix[i].re);
		}*/
		
/*		System.out.println("LPF PSD");
		for(int i = 0; i < lpfpsd.rows; i++){
			System.out.println(lpfpsd.matrix[i][0]);
		}*/
		
/*		System.out.println("HPF Signal");
		for(int i = 0; i < hpf.rows; i ++){
			System.out.println("" + hpf.cmatrix[i].re);
		}
		*/
/*		System.out.println("HPF PSD");
		for(int i = 0; i < hpfpsd.rows; i++){
			System.out.println(hpfpsd.matrix[i][0]);
		}*/
		
/*		System.out.println("BPF Signal");
		for(int i = 0; i < bpf.rows; i ++){
			System.out.println("" + bpf.cmatrix[i].re);
		}*/

	//#5
	
	
	myMatrix toneA = new myMatrix(1, "tonedataA.txt");
	
	myMatrix toneB = new myMatrix(1, "tonedataB.txt");
	
	myMatrix toneC = new myMatrix(1, "tonedataC.txt");

	myMatrix toneD = new myMatrix(1, "tonedataD.txt");
	
	myMatrix toneE = new myMatrix(1, "tonedataE.txt");
	toneA.fft(1);
	
	toneB.fft(1);
	
	toneC.fft(1);
	
	toneD.fft(1);
	
	toneE.fft(1);
		
	myMatrix toneAPSD = toneA.psd();
	
	myMatrix toneBPSD = toneB.psd();
	
	myMatrix toneCPSD = toneC.psd();
	
	myMatrix toneDPSD = toneD.psd();
	
	myMatrix toneEPSD = toneE.psd();
	
	myMatrix[] Tones = {toneAPSD,toneBPSD,toneCPSD,toneDPSD,toneEPSD};
	int toneindex = 0;
	for(myMatrix tone: Tones){
		txtFileToneBW.write("$" + toneindex);
		txtFileToneBW.newLine();
		toneindex++;
		for(int i = 0; i < tone.rows; i++){
			txtFileToneBW.write(""+tone.matrix[i][0]);
			txtFileToneBW.newLine();
		}
		
		
	}
	txtFileToneBW.close();
	
	
	//#6
	//a
	myMatrix pulse = new myMatrix(1,"rangeTestDataSpring2016.txt");
	myMatrix echosignal = new myMatrix(1, "echoSignal.txt");
	myMatrix paddedPulse = new myMatrix(1024,1);
	
	for(int i = 0; i < paddedPulse.rows; i++){
		paddedPulse.matrix[i][0] = 0;
	}
	
	for(int i = 0; i < pulse.rows; i++){
		paddedPulse.matrix[i][0] = pulse.matrix[i][0];
	}
	
	paddedPulse.fft(1);
	echosignal.fft(1);
	
	for(int i = 0; i < pulse.rows; i ++){
		paddedPulse.cmatrix[i] = echosignal.cmatrix[i].times(paddedPulse.cmatrix[i]);
	}
	
	paddedPulse.fft(-1);
	
	System.out.println("CORRELATION");
	for(int i = 0; i < paddedPulse.rows; i++){
		System.out.println(paddedPulse.cmatrix[i].re);
	}
	
	
	
	//b
	myMatrix echoPulse = new myMatrix(1, "echoSignal.txt");
	myMatrix convolution = new myMatrix(echoPulse.rows, 1);
	
	//Instantiate convolution filter
	for(int i = 0; i < 10; i ++){
		convolution.matrix[i][0] = 0.1;
	}
	
	for(int i = 10; i > convolution.rows; i ++){
		convolution.matrix[i][0] = 0.0;
	}
	
/*	System.out.println("Signal");
	for(int i = 0; i < echoPulse.rows; i++){
		System.out.println(echoPulse.matrix[i][0]);
	}*/
	
	echoPulse.fft(1);
	convolution.fft(1);
	
	//Multiply fft(echoPulse) by fft(convolution)
	for(int i = 0; i < echoPulse.rows; i++){
		convolution.cmatrix[i] = convolution.cmatrix[i].times(echoPulse.cmatrix[i]);
	}
	
	
	convolution.fft(-1);

/*	System.out.println("Smoothed Signal");
	for(int i = 0; i < convolution.rows; i++){
		System.out.println(convolution.cmatrix[i].re);
	}*/
	
	
	//#7
	Picture testsignal = new Picture(512,512);
	Picture pulseimg = new Picture(512,512);
	
	for(int row = 0; row < testsignal.height(); row++){
		for(int col = 0; col < testsignal.width(); col++){
			testsignal.set(col, row, new Color(0));
		}
	}
	
	for(int row = 100 ; row < 275; row++){
		for(int col = 250; col < 400; col++){
			testsignal.set(col, row, new Color(255,255,255));
		}
	}
	
	for(int row = 0; row < pulseimg.height(); row++){
		for(int col = 0; col < pulseimg.width(); col++){
			pulseimg.set(col, row, new Color(0));
		}
	}
	
	for(int row = 0 ; row < 30; row++){
		for(int col = 0; col < 90; col++){
			pulseimg.set(col, row, new Color(255,255,255));
		}
	}
	
	testsignal.show();
	pulseimg.show();
/*	
	myMatrix signalRow = new myMatrix(512,1);
	myMatrix pulseRow = new myMatrix(512,1);
	myMatrix correlationMatrix = new myMatrix(512,512);
	
	for(int col = 0; col < 512; col++){
		for(int row = 0; row < 512; row++){
			pulseRow.matrix[col][0] = (double)pulse.get(col, row).getRed();
			signalRow.matrix[col][0] = (double)testsignal.get(col, row).getRed();
		}
		pulseRow.fft(1);
		signalRow.fft(1);
		
		for(int i = 0; i < 512; i ++){
			signalRow.cmatrix[i] = signalRow.cmatrix[i].times(pulseRow.cmatrix[i]);	
		}
		
		signalRow.fft(-1);
		
		for(int i = 0; i < 512; i ++){
			correlationMatrix.matrix[col][i] = signalRow.cmatrix[i].re;
			if(correlationMatrix.matrix[col][i] > 0){
				System.out.println(correlationMatrix.matrix[col][i]+" HOLY FUCK!!!!!");
			}
		}

	}*/
	
	
	

	
	//Find correlation of rows
	
	}
	
	
	
}

