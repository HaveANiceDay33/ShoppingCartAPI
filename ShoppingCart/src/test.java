import java.util.HashMap;

public class test {
	
	interface Tax {
		double calc(double cost);
	}
	public static void main(String [] args) {
		HashMap<String, Tax> algs = new HashMap<String, Tax>();
		
		algs.put("Indiana", (cost) -> cost*5);
		algs.put("Ohio", (cost) -> cost*0.89);
		
		System.out.println(algs.get("Indiana").calc(5));
	}
}
