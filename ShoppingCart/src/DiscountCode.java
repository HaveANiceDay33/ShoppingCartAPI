import java.io.Serializable;

public class DiscountCode implements Serializable {
	private double rate;
	
	protected DiscountCode(double rate) {
		this.rate = rate;
	}
	
	protected double applyCode(double c) {
		return c - (c * rate);
	}
	
	protected double getRate() {
		return rate;
	}
}
