
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
	
	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getDescription() {
		return description;
	}

	public double getPrice() {
		return price;
	}

	public String getPicture() {
		return picture;
	}

	public double getMarkOffRate() {
		return markOffRate;
	}

}
