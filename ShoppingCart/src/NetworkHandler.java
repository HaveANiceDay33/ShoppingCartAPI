import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;

public class NetworkHandler {
	private static String location;
	private Cart cart;
	
	//For testing ONLY
	private String lastReturnedMessage;
	//
	
	public NetworkHandler() {
		if(new File("Cart.txt").isFile()) {
			
			try {
				FileInputStream fi = new FileInputStream(new File("Cart.txt"));
				ObjectInputStream oi = new ObjectInputStream(fi);
				
				cart = (Cart) oi.readObject();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {
			cart = new Cart();
		}
		
		lastReturnedMessage = "";
	}
	
	public void receiveRequest(Request r) {
		switch(r.type) {
		case ADD_ITEM:
		case MODIFY_QUANTITY:
		case VIEW_CART:
		case APPLY_DISCOUNT:
		default:
			this.returnMessage(new Message("Invalid Request Type"));
		}
	}
	
	public void returnMessage(Message m) {
		lastReturnedMessage = m.getMessage();
	}
	
	private void reconnect() {
		
	}
	
	public static void setLocation(String loc) {
		location = loc;
	}
	
	public static String getLocation() {
		return location;
	}
	
	//for testing ONLY
	public String getLast() {
		return lastReturnedMessage;
	}
	//
}
