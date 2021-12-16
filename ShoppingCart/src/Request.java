
public class Request {
	public enum RequestType {
		VIEW_CART,
		ADD_ITEM,
		APPLY_DISCOUNT,
		MODIFY_QUANTITY
	}
	
	public String [] data;
	public RequestType type;
	
	public Request(RequestType type, String ... data) {
		this.data = data;
		this.type = type;
	}
	
}
