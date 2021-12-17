import java.util.HashMap;

public class Stock {
	private static HashMap<Integer, Integer> inventory = new HashMap<>();
	private static HashMap<Integer, Item> itemList = new HashMap<>();
	
	protected static boolean adjustStock(int i, int differential) {
		int curStock = inventory.get(i);
		curStock += differential;
		if (curStock < 0) {
			NetworkHandler.returnMessage(new Message("Too much stock requested."));
			return false;
		} else {
			inventory.put(i, curStock);
			return true;
		}	
	}
	
	protected static void addItemToStock(Item i, int q) {
		inventory.put(i.getId(), q);
		itemList.put(i.getId(), i);
	}
	
	protected static HashMap<Integer, Integer> getInventory() {
		return inventory;
	}
	
	protected static void clearInventory() {
		inventory.clear();
	}
	
	protected static HashMap<Integer, Item> getItemList(){
		return itemList;
	}
}
