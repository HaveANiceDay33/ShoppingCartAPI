
public class Item {
	private int id;
	private String name;
	private String description;
	private double price;
	private String picture;
	private double markOffRate;
	
	public Item(int id, String name, String description, double price, 
			String picture, double markOffRate) {
		this.id = id;
		this.name = name;
		this.description = description;
		this.price = price;
		this.picture = picture;
		this.markOffRate = markOffRate;
	}
	
	protected int getId() {
		return id;
	}

	protected String getName() {
		return name;
	}

	protected String getDescription() {
		return description;
	}

	protected double getPrice() {
		return price;
	}

	protected String getPicture() {
		return picture;
	}

	protected double getMarkOffRate() {
		return markOffRate;
	}

}
