import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;


public class tspRandom {
private String[] labelArray = {"A","B","C","D","E","F","G","H","I","J","K","L","M","N"};
	
	myMatrix cityPoints;
	myMatrix distMatrix;
	
	private double maxCost = 0;
	private double minCost = Double.MAX_VALUE;
	private double sigmaDist = 0;
	private double sigmaDistSquared = 0;
	
	private int stops;
	private City [] cityIndex;
	private Histogram H;
	
	public int[] freqBin = new int[100];
	
	private double shortestRoute;
	private int[] shortestRouteLabels;
	
	final int pCount = 1000000;
	
	private int count;
	
	private ArrayList <City> remCities = new ArrayList<>();
	
	public tspRandom(int cities){
		stops = cities;
		cityPoints = new myMatrix(2, "TSPCities.txt");
		
		distMatrix = new myMatrix(stops,stops);
	 
	   	generateDistanceMatrix();
	   	
		H = new Histogram(maxCost, minCost, 10);
	    
	   	for (int i = 0; i < stops; i++){
	   		remCities.add(new City(i));
	   	}
	   	
	   	for (int route=0; route < pCount; route++){
	   		remCities.remove(0);
	   		Collections.shuffle(remCities);
	   		remCities.add(0,new City(0));
	   		handleP();
	   	}
	    
	   	System.out.print("Best route: ");
	   	for(int i = 0; i < shortestRouteLabels.length; i++){
	   		System.out.print(labelArray[shortestRouteLabels[i]] + " ");
	   	}
	   	System.out.println();
	   	System.out.println("Shortest route of "+ pCount + ": " + shortestRoute);
	   	System.out.println();
	   	
	   	
	   	double meanRoute = sigmaDist/pCount;
	   	double sdRoute = Math.sqrt((sigmaDistSquared - (Math.pow(sigmaDist,2)/pCount))/(pCount-1));
	   	
	   	H.printHistogram();
	   	
	   	System.out.println();
	   	
	   	System.out.println("Mean route distance: " + meanRoute);
	   	
	   	System.out.println("Standard Deviation: " + sdRoute);
	}
	

	  public void handleP()
	  {
		  	count++;
		  
		  	//instantiate distance
		  	double distance = 0;
		  	Random intRand = new Random();
		  	double tempRand;
		  	
		  	
		  	
		    for(int index = 0; index < remCities.size(); index++){
		    	distance += distMatrix.matrix[remCities.get(index).getIndex()][remCities.get((index+1)%stops).getIndex()]; 
		    }
		    
		    //System.out.println("Distance: " + distance);
		    
		    if(distance < shortestRoute){
		    	shortestRoute = distance;
		    	for(int l = 0; l <remCities.size(); l++){
		    		shortestRouteLabels[l] = remCities.get(l).getIndex();
		    	}
		    	//System.out.println("New best route: " + distance);
		    }
		    
		    H.findBin(distance);
		    sigmaDist += distance;
		    sigmaDistSquared += Math.pow(distance,2);
		    
		   // System.out.print(count+" ** ");

		    for (int i=0; i<stops; i++){
		    	//System.out.print(remCities.get(i).label+" ");
		    }
		      
		   // System.out.println("**");  
		    //System.out.println();
	  }
	  
		public void generateDistanceMatrix(){
			myMatrix startPoint = new myMatrix(1,2);
			myMatrix destination = new myMatrix(1,2);
			myMatrix deltaXY = new myMatrix(1,2);
			double tempDist;
			
			System.out.println("Cities");
			cityPoints.printPoints();
			System.out.println();
			
			for(int r = 0; r < stops; r++){
				startPoint = cityPoints.getRow(r);
	/*			System.out.print("Starting point: ");
				System.out.println(labelArray[r]);*/
				distMatrix.matrix[r][r] = 0;

				//startPoint.printPoints();
				
				for(int c = r+1; c < stops; c++){
	/*				System.out.println("Destination :" + labelArray[c]);
					cityPoints.getRow(c).printPoints();*/
					if(c == 11){
						System.out.println();
					}
					destination = cityPoints.getRow((c));
					destination.subtract(startPoint);
					
					tempDist = Math.sqrt(Math.pow(destination.matrix[0][0], 2) + Math.pow(destination.matrix[0][1], 2));
					//System.out.println("Distance: " + tempDist);
					//System.out.println();
					distMatrix.matrix[r][c] = tempDist;
					distMatrix.matrix[c][r] = tempDist;
					
					if(tempDist < minCost/stops && tempDist != 0){
						
	/*					System.out.print("Starting point: ");
						System.out.println(labelArray[r]);
						startPoint.printPoints();
						
						System.out.println("Destination :" + labelArray[c]);
						cityPoints.getRow(c).printPoints();
						
						System.out.println("New min dist: " + tempDist);*/
						minCost = tempDist*stops;
	/*					System.out.println("New min cost: " + minCost);
						System.out.println();*/
					}
					
					if(tempDist > maxCost/stops){
						maxCost = tempDist*stops;
					}
				}
			}
			
			distMatrix.printMatrix();
			
			shortestRoute = maxCost;
			shortestRouteLabels = new int[stops];
			
			//distMatrix.printMatrix();
			System.out.println("Shortest possible route: " + minCost);
			System.out.println("Longest possible route: " + maxCost);
			System.out.println();
		}
}


