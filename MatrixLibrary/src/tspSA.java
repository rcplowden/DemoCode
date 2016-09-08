import java.util.ArrayList;
import java.util.Collections;

public class tspSA {
	private String[] labelArray = {"A","B","C","D","E","F","G","H","I","J","K","L","M","N"};
	
	myMatrix cityPoints;
	myMatrix distMatrix;
	
	private int stops;
	private int [] val;
	private int now = -1;
	private int count = 0;
	
    private double sigmaDist;
    private double sigmaDistSquared;
    
    private double meanRoute;
    private double sdRoute;
	private int crossoverSize;
	
	private final int POPSIZE = 1000;
	private final int asOffset = 10;
	
	private Histogram H;

	double t = 1;
	
	private double distSum = 0;
	private double maxCost = 0;
	private double minCost = Double.MAX_VALUE;
	
	private double shortestRoute = Double.MAX_VALUE;
	private Route shortestRouteLabels;
	
	private ArrayList <City> remCities = new ArrayList<>();
	
	public tspSA(int cities){
		stops = cities;
		cityPoints = new myMatrix(2, "TSPCities.txt");
		
		distMatrix = new myMatrix(stops,stops);
	 
	   	generateDistanceMatrix();

	   	H = new Histogram(maxCost,minCost,10);
	   	
	   	ArrayList<Route> population = genInitPop(POPSIZE);
		int count = 0;
		int timer = 0;
	   	while(t > .0001){  		
	   		//System.out.println("T VAL : " + t);
	   		population = annealPop(population);
	   		calcPopStats(population);
	   		
	   		t = t/(1+Math.log(1+t));
	   		
	   		count++;
	   		//System.out.println("Count :" + count);
	   	}
	   	
	   	for(Route route: population){
	   		H.findBin(route.getDistance());
	   	}
	   	
	   	H.printHistogram();
	   	
	   	System.out.println("Shortest route: " + shortestRoute);
	   	shortestRouteLabels.printLabels();
	   	
	   	System.out.println("Mean route: " + meanRoute);
	   	System.out.println("StdDev: " + sdRoute);
	   	System.out.println();
	   	
	}
	
	//Simulated Annealing
	public ArrayList<Route> annealPop(ArrayList<Route> pop){
		ArrayList<Route> newPop = new ArrayList();
		for(Route route: pop){
			Route tempRoute = route.deepCopy();
			
			tempRoute.shuffleRoute();
			
	   		double distance = 0;
	   		
		    for(int index = 0; index < stops; index++){
		    	distance += distMatrix.matrix[tempRoute.getCityInt(index)][tempRoute.getCityInt((index+1)%stops)]; 
		    }
		    
		    if(distance < route.getDistance() | Math.random() < t){
		    	newPop.add(tempRoute.deepCopy());
		    }
		    
		    else{
		    	newPop.add(route);
		    }
		}
		
		return newPop;
	}
	//Generates random population
	public ArrayList<Route> genInitPop(int popsize){
		ArrayList<Route> basePop = new ArrayList<Route>();
		
	   	for (int i = 0; i < stops; i++){
	   		remCities.add(new City(i));
	   		shortestRouteLabels.setCity(i, new City(i));
	   	}
	   	
	   	for (int route=0; route < popsize; route++){
	   		remCities.remove(0);
	   		Collections.shuffle(remCities);
	   		remCities.add(0,new City(0));
	   		
	   		Route randRoute = new Route(stops);
	   		double distance = 0;
	   		
	   		
	   		for(int k = 0; k < stops; k ++){
	   			randRoute.setCity(k, remCities.get(k));
	   		}
		    
	   		basePop.add(randRoute);
	   	}
	   	calcPopStats(basePop);
	   	
		return basePop;
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
		shortestRouteLabels = new Route(stops);
		
		//distMatrix.printMatrix();
		System.out.println("Shortest possible route: " + minCost);
		System.out.println("Longest possible route: " + maxCost);
		System.out.println();
	}
	
	// Calculate distance, mean, std deviation, and bestcand
	public void calcPopStats(ArrayList<Route> pop){
   		sigmaDist = 0;
   		sigmaDistSquared = 0;
		
	   	for (Route route: pop){
	   		double distance = 0;
	   		
		    for(int index = 0; index < stops; index++){
		    	distance += distMatrix.matrix[route.getCityInt(index)][route.getCityInt((index+1)%stops)]; 
		    }
		    
		    if(distance < shortestRoute){
		    	for(int i = 0; i < stops; i++){
		    		shortestRouteLabels.setCity(i, route.getCityInt(i));
		    		shortestRouteLabels.setDistance(distance);
		    		shortestRoute = distance;
		    	}
		    }
	   		//System.out.println("Route distance :" + distance);
		    sigmaDist += distance;
		    sigmaDistSquared += Math.pow(distance,2);
		    
		    route.setDistance(distance);
		    
	   	}
	   	//System.out.println("Shortest route: " + shortestRoute);
	   	//shortestRouteLabels.printLabels();
	   	
	   	meanRoute = sigmaDist/POPSIZE;
	   	sdRoute = Math.sqrt((sigmaDistSquared - (Math.pow(sigmaDist,2)/POPSIZE))/(POPSIZE-1));
		
/*	   	System.out.println("Mean route: " + meanRoute);
	   	System.out.println("StdDev: " + sdRoute);
	   	System.out.println();*/
	}
	
}
