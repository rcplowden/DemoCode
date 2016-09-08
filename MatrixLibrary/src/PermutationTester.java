public class PermutationTester
{

  static int [] val;
  static int now = -1;
  static int V = 5;
  static int count = 0;
  

 
  public static void main(String arg [])
  {
    val = new int [V+1];
    for (int i=0; i<=V; i++)
      val[i]=0;
   	  p(0);
  }

  public static void p(int k)
  {
    now++;
    val[k]=now;
    if (now==V) handleP();
    for (int i=1; i<=V; i++)
      if (val[i]==0) p(i);
    now--;
    val[k]=0; 
  }
  
  public static void handleP()
  {
    count++;
    System.out.print(count+" ** ");
    for (int i=1; i<=V; i++){
    	System.out.print(val[i]+" ");
    }
      
    System.out.println("**");  
  }
  
}