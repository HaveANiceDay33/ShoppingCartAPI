import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class Cart implements Serializable {
	private HashMap<Integer, Integer> items;
	private Cost cost;

	protected Cart(Cost c) {
		items = new HashMap<>();
		this.cost = c;
	}

	protected void addItem(int i) {
		if(Stock.adjustStock(i, -1)) {
			if (items.containsKey(i)) {
				int curQ = items.get(i);
				items.put(i, curQ + 1);
			} else {
				items.put(i, 1);
			}
			NetworkHandler.returnMessage(new Message("Item " + Stock.getItemList().get(i).getName() + " added to cart!"));
		} else {
			NetworkHandler.returnMessage(new Message("Item is out of stock."));
		}
		
		this.cost.updateTotal(this);
		saveCartToDisk();
	}

	protected void updateQuantity(int i, int q) {
		int initQ = items.get(i);
		if (q == 0) {
			items.remove(i);
			Stock.adjustStock(i, initQ - q);
		} else if (q > 0) {
			if (Stock.adjustStock(i, initQ - q)) {
				NetworkHandler.returnMessage(new Message(
						"Item " + Stock.getItemList().get(i).getName() + " modified with quantity " + q));
				items.put(i, q);
			}
		} else {
			System.out.println("Quantity cannot be negative");
		}
		this.cost.updateTotal(this);
		saveCartToDisk();
	}

	protected void setDiscountCode(DiscountCode d) {
		this.cost.setDiscountCode(d);
		this.cost.updateTotal(this);
	}

	protected void updateTotal() {
		this.cost.updateTotal(this);
	}
	
	protected double getCost() {
		return cost.getCost();
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

	protected HashMap<Integer, Integer> getItems() {
		return items;
	}
	
	protected int numItems() {
		Iterator iter = this.getItems().entrySet().iterator();
		int num = 0;
		while(iter.hasNext()) {
			Map.Entry itemSet = (Map.Entry) iter.next();
			
			Item i = Stock.getItemList().get(itemSet.getKey());
			
			int quantity = (int) itemSet.getValue();
			
			num += quantity;
		}
		
		return num;
	}
}
