import java.util.HashMap;
import java.util.Map;

public class Tax {
	private interface Algorithm{double calculateTax(double cost);}
	
	private static Map<String, Algorithm> createAlgorithms() {
		Map<String, Algorithm> map = new HashMap<String, Algorithm>();
		map.put("Indiana", cost -> cost * 0.13);
		
		return map;
	};
	
	static Map<String, Algorithm> algorithms = createAlgorithms();
	
	public static double applyTax(String location, double cost) {
		return cost + algorithms.get(location).calculateTax(cost);
	}
}
