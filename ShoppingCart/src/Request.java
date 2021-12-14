
public class Request {
	public enum requestType {
		VIEW_CART,
		ADD_ITEM,
		APPLY_DISCOUNT,
		MODIFY_QUANTITY
	}
	
	public String [] data;
	public requestType type;
	
	public Request(requestType type, String ... data) {
		this.data = data;
		this.type = type;
	}
	
}
