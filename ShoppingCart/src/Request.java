
public class Request {
	protected static enum RequestType {
		VIEW_CART,
		ADD_ITEM,
		APPLY_DISCOUNT,
		MODIFY_QUANTITY
	}
	
	protected String [] data;
	protected RequestType type;
	
	protected Request(RequestType type, String ... data) {
		this.data = data;
		this.type = type;
	}
	
}
