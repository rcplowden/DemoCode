
public class Histogram {
	private double a; // Upperbound
	private double b; // Lowerbound
	private double dx;
	private int binCount;
	private long[] histogram;
	private double total = 0.0;
	
	public Histogram(double upperBound, double lowerBound, int bins){
		a = upperBound;
		b = lowerBound;
		binCount = bins;
		dx = (a-b)/bins;
		histogram = new long[bins];
	}
	
	public void findBin(double val){
		double binIndex = (val-b)/dx;
		histogram[((int)binIndex)] ++;
		total++;
	}

	public long[] getHistogram(){
		return histogram;
	}
	
	public void printHistogram(){
		for(double i = 0.0; i < binCount; i++){
			System.out.println("Bin"+ (int)i +" ("+ (i*dx+b) +" - " + ((i+1)*dx+b) +"): " + (histogram[(int)i]/total));
			System.out.println();
		}
	}
}
