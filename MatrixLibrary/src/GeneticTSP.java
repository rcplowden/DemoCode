import java.util.ArrayList;
import java.util.Collections;

public class GeneticTSP {
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
	
	private final double mutationRate = 0.000;

	private double distSum = 0;
	private double maxCost = 0;
	private double minCost = Double.MAX_VALUE;
	
	private double shortestRoute = Double.MAX_VALUE;
	private Route shortestRouteLabels;
	
	private ArrayList <City> remCities = new ArrayList<>();
	
	public GeneticTSP(int cities, int generations){
		stops = cities;
		crossoverSize = stops/2;
		cityPoints = new myMatrix(2, "TSPCities.txt");
		
		distMatrix = new myMatrix(stops,stops);
	 
	   	generateDistanceMatrix();
	   	
	   	H = new Histogram(maxCost,minCost,10);
		
		//Create random population (primordial soup)
	   	ArrayList<Route> initpop = genPrimordialSoup(POPSIZE);
		
		for(int i = 0; i < generations; i++){
			System.out.println("Generation " + i);
		   	initpop = evolve(initpop);
		   	initpop = mutate(initpop);
		}
		
		for(Route route: initpop){
			H.findBin(route.getDistance());
		}
		
		H.printHistogram();
/*		for(Route route: initpop){
			System.out.println(route.getDistance());
		}*/
	}
	
	public ArrayList<Route> genPrimordialSoup(int popsize){
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
	   	System.out.println("Shortest route: " + shortestRoute);
	   	shortestRouteLabels.printLabels();
	   	
	   	meanRoute = sigmaDist/POPSIZE;
	   	sdRoute = Math.sqrt((sigmaDistSquared - (Math.pow(sigmaDist,2)/POPSIZE))/(POPSIZE-1));
		
	   	System.out.println("Mean route: " + meanRoute);
	   	System.out.println("StdDev: " + sdRoute);
	   	System.out.println();
	}
	
	public ArrayList<Route> evolve(ArrayList<Route> pop){
		boolean candFound = false;
		
		Route parent1 = new Route(stops);
		Route parent2 = new Route(stops);
		Route offspring1 = new Route(stops);
		Route offspring2 = new Route(stops);
		
		ArrayList<Route> newpop = new ArrayList();
		
		ArrayList<Route> familyfeud = new ArrayList();
		
		//Overwrite 10 random Routes with copies of best route
		for(int i = 0; i < asOffset; i ++){
			newpop.add(shortestRouteLabels.deepCopy());
		}
		
		//Crossover 990 remaining routes
		for(int i = asOffset; i < POPSIZE; i+=2){
			//System.out.println("Crossover number: " + i);
			candFound = false;
			int rLimit = 0;
			int randIndex = -1;
			
			//System.out.println("Selecting Parent1");
			//Finding parent1
			while(!candFound && rLimit < 3){
				randIndex = (int)(Math.random()*pop.size());
				//System.out.println(randIndex + " " + pop.size());
				parent1 = pop.get(randIndex);
				
				if(meanRoute - parent1.getDistance() > sdRoute){
					candFound = true;
				}
				rLimit++;
			}
			//System.out.println("Selected Parent1");
			pop.remove(randIndex);
			
			rLimit = 0;
			candFound = false;
			
			//System.out.println("Selecting Parent2");
			//Finding parent2
			while(!candFound && rLimit < 3){
				randIndex = (int)(Math.random()*pop.size());
				//System.out.println(randIndex + " " + pop.size());
				parent2 = pop.get(randIndex);
				
				if(meanRoute - parent2.getDistance() > sdRoute){
					candFound = true;
				}
				rLimit++;
			}
			//System.out.println("Selected Parent2");
			pop.remove(randIndex);
			
			//Create 2 offspring by reversing the crossover order of parents
			offspring1 = crossOver(parent1, parent2);
			
			offspring2 = crossOver(parent2, parent1);
			
/*			System.out.println("Offspring1");
			offspring1.printInts();
			
			System.out.println("Offspring2");
			offspring2.printInts();*/
			
			familyfeud.add(parent1);
			familyfeud.add(parent2);
			familyfeud.add(offspring1);
			familyfeud.add(offspring2);
			
			//Find 2 fittest candidates
			for (int cand = 0; cand < 2; cand++){
				double shortest = Double.MAX_VALUE;
				int bestRoute = -1;
				
				//Find fittest in family of 4 (2 parents, 2 offspring)
				for(int r = 0; r < familyfeud.size(); r++){
					
					if(familyfeud.get(r).getDistance() < shortest){
						bestRoute = r;
						shortest = familyfeud.get(r).getDistance();
					}
				}
				newpop.add(familyfeud.get(bestRoute));
				familyfeud.remove(bestRoute);
			}
		}
		
		//Calculate stats for new population
		calcPopStats(newpop);
		
		return newpop;
	}
	
	public Route crossOver(Route parent1, Route parent2){
		Route offspring = new Route(stops);
		
/*		System.out.println("Parent1");
		parent1.printInts();
		System.out.println("Parent2");
		parent2.printInts();
		*/
		//Fill empty indices with -1 to avoid confusion with city A when checking
		for(int i = 0; i < stops; i++){
			offspring.setCity(i,new City(-1));
		}
		
		double minDist = Double.MAX_VALUE;
		double tempDist = 0;
		int startCity = -1;
		int destinationCity = -1;
		int[] subsetLabels = new int[crossoverSize];
		
		//Find subset of n cities in parent1 with shortest distance to crossover
		for(int i = 0; i < stops; i++){
			tempDist = 0;
	
			//Calculate distance of subset
			for(int j = i; j < i+crossoverSize; j++){
				startCity = parent1.getCityInt(j%stops);
				destinationCity = parent1.getCityInt((j+1)%stops);
				
				//System.out.println("Start: " + startCity + " Destination: " + destinationCity);
				tempDist += distMatrix.matrix[startCity][destinationCity];
			}
			
			//If distance is less than the current minimum, make it the minimum
			if(tempDist < minDist){
				minDist = tempDist;
				
/*				System.out.println("New subset found, dist: " + minDist);
				
				for(int j = i; j < i+crossoverSize; j++){
					System.out.print(parent1.getCityInt(j%stops));
				}
				
				System.out.println();*/
				
				
				for(int k = 0; k < stops; k++){
					offspring.setCity(k,-1);
				}
				
				//Crossover from parent1
				for(int j = i; j < i+crossoverSize; j++){
					offspring.setCity(j%stops,parent1.getCityInt(j%stops));
				}
				
			}
		}
/*		System.out.println("New offSpring 1");
		offspring.printInts();*/
		
		//Add remaining cities as they are observed in parent2
		for(int i = 0; i < stops; i++){
			boolean inChild = false;
			int cityIndex = parent2.getCityInt(i);
			
			//Check if cityIndex is already in child
			for(int j = 0; j < stops; j++){
				if(cityIndex == offspring.getCityInt(j)){
					inChild = true;
				}
			}
			
			//If cityIndex is not inChild, search for the first empty index to insert it
			if(!inChild){
				int cityPointer = 0;
				boolean inserted = false;
				while(cityPointer < stops && !inserted){
					
					if(offspring.getCityInt(cityPointer) == -1){
						offspring.setCity(cityPointer, cityIndex);
						inserted = true;
						cityPointer++;
					}
					
					else{
						cityPointer++;
					}
				}
			}
			
		}
		
		return offspring;
	}
	
	//mutationRate dictates the probability that a trip is shuffled
	public ArrayList<Route> mutate(ArrayList<Route> pop){
		int city1;
		int city2;
		int tempCity;
		
		for(Route route: pop){
			if(Math.random() < mutationRate){
				double newdistance = 0;
				route.printLabels();
				System.out.println("SWAPSKI");
				route.shuffleRoute();
				route.printLabels();
				
			    for(int index = 0; index < stops; index++){
			    	newdistance += distMatrix.matrix[route.getCityInt(index)][route.getCityInt((index+1)%stops)]; 
			    }
			    
			    System.out.println("NEW DIST " + newdistance);
			    route.setDistance(newdistance);
			}
		}
		return pop;
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
}
