
public class testdc {
		public static void main(String[] args){
			Route smoke = new Route(10);
			
			for(int i = 0; i < 10; i++){
				smoke.setCity(i, new City((int)Math.random()*345));
			}
			smoke.printInts();
			Route copy = smoke.deepCopy();
			
			copy.setCity(0, 5000);
			
			copy.printInts();
			smoke.printInts();
		}
}
