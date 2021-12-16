import java.util.HashMap;

public class Stock {
	private static HashMap<Integer, Integer> inventory = new HashMap<>();
	
	public static void adjustStock(int i, int differential) {
		int curStock = inventory.get(i);
		curStock += differential;
		inventory.put(i, curStock);
	}
}
