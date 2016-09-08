
public class City {
	private final String[] labelArray = {"A","B","C","D","E","F","G","H","I","J","K","L","M","N"};
	private int index;
	public City(int index){
		this.index = index;
	}
	
	public int getIndex(){
		return index;
	}
	
	public void setIndex(int index){
		this.index = index;
	}
	
	public String getLabel(){
		if(index != -1){
			return labelArray[index];
		}
		
		else{
			return "null";
		}

	}
}
