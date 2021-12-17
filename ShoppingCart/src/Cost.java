import java.io.Serializable;
import java.util.Iterator;
import java.util.Map;

public class Cost implements Serializable {
	private double cost;
	protected DiscountCode discountCode;
	
	protected Cost() {
		discountCode = null;
		cost = 0;
	}
	
	protected void updateTotal(Cart c) {
		Iterator iter = c.getItems().entrySet().iterator();
		cost = 0;
		while(iter.hasNext()) {
			Map.Entry itemSet = (Map.Entry) iter.next();
			
			Item i = Stock.getItemList().get(itemSet.getKey());
			
			int quantity = (int) itemSet.getValue();
			
			cost += (i.getPrice() - (i.getMarkOffRate() * i.getPrice())) * quantity;
		}
		cost = getDiscountedAmount();
		cost = getTaxedAmount(NetworkHandler.getLocation());
	}
	
	private double getTaxedAmount(String location) {
		return Tax.applyTax(location, cost);
	}
	
	private double getDiscountedAmount() {
		if (this.discountCode == null) {
			return this.cost;
		}
		return this.discountCode.applyCode(this.cost);
	}
	
	protected double getCost() {
		return (double) Math.round(this.cost * 1000)/1000;
	}
	
	protected void setDiscountCode(DiscountCode d) {
		this.discountCode = d;
	}
	
}
