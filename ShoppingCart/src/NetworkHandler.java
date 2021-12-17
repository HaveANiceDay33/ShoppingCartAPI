import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class NetworkHandler {
	private static String location;
	private Cart cart;

	// For testing ONLY
	public static String lastReturnedMessage;
	//

	public NetworkHandler() {
		if (new File("Cart.txt").isFile()) {

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
			cart = new Cart(new Cost());
		}

		lastReturnedMessage = "";
	}

	public void receiveRequest(Request r) {
		
		if (r.type == Request.RequestType.ADD_ITEM) {
			this.cart.addItem(Integer.parseInt(r.data[0]));
		} else if (r.type == Request.RequestType.MODIFY_QUANTITY) {
			int q = Integer.parseInt(r.data[1]);
			this.cart.updateQuantity(Integer.parseInt(r.data[0]), q);
		} else if (r.type == Request.RequestType.VIEW_CART) {
			try {
				String cartString = "";

				ByteArrayOutputStream bo = new ByteArrayOutputStream();
				ObjectOutputStream so = new ObjectOutputStream(bo);
				so.writeObject(this.cart);
				so.flush();
				cartString = bo.toString();
				
				so.close();
				bo.close();
				NetworkHandler.returnMessage(new Message("Cart with "+this.cart.numItems() + " items costing "+this.cart.getCost()));
			} catch (Exception e) {
				System.out.println(e);
			}

		} else if (r.type == Request.RequestType.APPLY_DISCOUNT) {
			try {
				byte b[] = r.data[0].getBytes();
				ByteArrayInputStream bi = new ByteArrayInputStream(b);
				ObjectInputStream si = new ObjectInputStream(bi);
				DiscountCode dc = (DiscountCode) si.readObject();
				
				this.cart.setDiscountCode(dc);
				this.cart.updateTotal();
				NetworkHandler.returnMessage(new Message("Discount Code applied!"));
				
				bi.close();
				si.close();
			} catch (Exception e) {
				System.out.println(e);
			}

		}	
		else {
			NetworkHandler.returnMessage(new Message("Invalid Request Type"));
		}
	}

	public static void returnMessage(Message m) {
		lastReturnedMessage = m.getMessage();
	}

	protected void reconnect() {
		NetworkHandler.returnMessage(new Message("Reconnecting..."));
		NetworkHandler.returnMessage(new Message("Reconnected!"));
	}

	public static void setLocation(String loc) {
		location = loc;
	}

	public static String getLocation() {
		return location;
	}

	// for testing ONLY
	public String getLast() {
		return lastReturnedMessage;
	}
	//
}
