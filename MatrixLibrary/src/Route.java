import java.util.ArrayList;
import java.util.Collections;

public class Route {
	private double distance;
	private City[] labels;
	private int citycount;
	
	public Route(int cities){
		labels = new City[cities];
		citycount = cities;
	}
	
	
	public void setCity(int index, City city){
		labels[index] = city;
	}
	
	/**
	 * Sets cityindex of city at index
	 * @param int index
	 * @param int cityIndex
	 */
	public void setCity(int index, int cityIndex){
		labels[index].setIndex(cityIndex);
	}
	
	
	public City getCity(int index){
		return labels[index];
	}
	
	public String getCityLabel(int index){
		return labels[index].getLabel();
	}
	
	public int getCityInt(int index){
		return labels[index].getIndex();
	}
	
	public City[] getCityLabels(){
		return labels;
	}
	
	public void setCityLabels(City[] citylabels){
		labels = citylabels;
	}
	
	public void setDistance(double dist){
		distance = dist;
	}
	
	public double getDistance(){
		return distance;
	}
	
	public void printLabels(){
		for(int i = 0; i < citycount; i++){
			System.out.print(labels[i].getLabel() + " ");
		}
		System.out.println();
	}
	
	public void printInts(){
		for(int i = 0; i < citycount; i++){
			System.out.print(labels[i].getIndex() + " ");
		}
		System.out.println();
	}
	
	public Route deepCopy(){
		Route dc = new Route(citycount);
		dc.distance = this.distance;
		
		for(int i = 0; i < citycount; i++){
			dc.setCity(i, new City(this.getCityInt(i)));
		}
		
		return dc;
	}
	
	public void shuffleRoute(){
		ArrayList<Integer> sroute = new ArrayList();
		
		for(City city: labels){
			sroute.add(city.getIndex());
		}
		
		Collections.shuffle(sroute);
		
		for(int i = 0; i < sroute.size(); i++){
			labels[i].setIndex(sroute.get(i));
		}
		
		
	}
}
