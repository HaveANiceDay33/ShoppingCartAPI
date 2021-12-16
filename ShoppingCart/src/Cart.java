import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Iterator;

public class Cart implements Serializable{
	private HashMap<Item, Integer> items;
	private Cost cost;
	
	public Cart() {
		items = new HashMap<>();
		cost = new Cost();
	}
	
	public void addItem(Item i) {
		if(items.containsKey(i)) {
			int curQ = items.get(i);
			items.put(i, curQ + 1);
		} else {
			items.put(i, 1);
		}
		Stock.adjustStock(i.getId(), -1);
		cost.updateTotal(this);
		saveCartToDisk();
	}
	
	public void updateQuantity (Item i, int q) {
		int initQ = items.get(i);
		if(q == 0) {
			items.remove(i);
			Stock.adjustStock(i.getId(), initQ - q);
		} else if (q > 0) {
			items.put(i,q);
			Stock.adjustStock(i.getId(), initQ - q);
		} else {
			System.out.println("Quantity cannot be negative");
		}
		saveCartToDisk();
	}
	
	public Iterator getItemIterator() {
		return items.entrySet().iterator();
	}
	
	private void saveCartToDisk() {
		try {
			FileOutputStream f = new FileOutputStream(new File("Cart.txt"));
			ObjectOutputStream o = new ObjectOutputStream(f);
			
			o.writeObject(this);
			
			o.close();
			f.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public HashMap<Item, Integer> getItems(){
		return items;
	}
}
