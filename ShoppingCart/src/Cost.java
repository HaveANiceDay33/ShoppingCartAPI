import java.util.ArrayList;
import java.util.Map;

public class Cost {
	private double cost;
	ArrayList<DiscountCode> discountCodes;
	
	public Cost() {
		discountCodes = new ArrayList<>();
		cost = 0;
	}
	
	public void updateTotal(Cart c) {
		while(c.getItemIterator().hasNext()) {
			Map.Entry<Item, Integer> itemSet = 
					(Map.Entry<Item, Integer>) c.getItemIterator().next();
			Item i = itemSet.getKey();
			int quantity = itemSet.getValue();
			
			cost += i.getMarkOffRate() * i.getPrice() * quantity;
		}
		cost = getDiscountedAmount(discountCodes);
		cost = getTaxedAmount(NetworkHandler.getLocation());
	}
	
	private double getTaxedAmount(String location) {
		return Tax.applyTax(location, cost);
	}
	
	private double getDiscountedAmount(ArrayList<DiscountCode> d) {
		double c = cost;
		for(int yndex = 0; yndex < d.size(); yndex++) {
			c = d.get(yndex).applyCode(c);
		}
		return c;
	}
	
	public double getCost() {
		return cost;
	}
	
}
