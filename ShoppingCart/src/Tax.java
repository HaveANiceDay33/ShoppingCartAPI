import java.util.HashMap;
import java.util.Map;

public class Tax {
	private static interface Algorithm{double calculateTax(double cost);}
	
	private static Map<String, Algorithm> createAlgorithms() {
		Map<String, Algorithm> map = new HashMap<String, Algorithm>();
		map.put("Indiana", cost -> cost * 0.07);
		
		return map;
	};
	
	static Map<String, Algorithm> algorithms = createAlgorithms();
	
	public static double applyTax(String location, double cost) {
		try {
			return cost + algorithms.get(location).calculateTax(cost);
		} catch (NullPointerException e) {
			System.out.println(e.toString());
			return -1;
		}
	}
}
