
public class DiscountCode {
	private double rate;
	
	public DiscountCode(double rate) {
		this.rate = rate;
	}
	
	public double applyCode(double c) {
		return c - (c * rate);
	}
	
	public double getRate() {
		return rate;
	}
}
