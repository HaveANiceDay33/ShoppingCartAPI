
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.ObjectOutputStream;

import org.junit.jupiter.api.Test;

class ShoppingCartTesting {

	private String writeSerial(Object o) {
		try {
			String objString = "";

			ByteArrayOutputStream bo = new ByteArrayOutputStream();
			ObjectOutputStream so = new ObjectOutputStream(bo);
			so.writeObject((DiscountCode) o);
			so.flush();
			objString = bo.toString();

			so.close();
			bo.close();
			return objString;
		} catch (Exception e) {
			System.out.println(e);
			return "";
		}
	}

	@Test
	void discountCodeClassTests() {
		DiscountCode d = new DiscountCode(0.2);
		assertEquals(80, d.applyCode(100));
		assertEquals(0.2, d.getRate());
	}
	
	@Test
	void taxClassTests() {
		assertEquals(107, Tax.applyTax("Indiana", 100));
		assertEquals(100, Tax.applyTax("Alaska", 100));
		assertEquals(104.74, Tax.applyTax("Rose-Hulman", 100));
		assertEquals(-1, Tax.applyTax("Ohio", 100));
	}
	
	@Test
	void stockClassTests() {
		Stock.addItemToStock(new Item(0, "Cheetos", "A tasty, cheesy snack!", 1.50, "cheeto.png", 0), 30);
		Stock.addItemToStock(new Item(1, "Pepsi", "A tasty, sugary drink!", 2.75, "pepsi.jpeg", 0.1), 100);
		Stock.addItemToStock(new Item(2, "Sugar Cookies", "12 pack of tasty cookies with some frosting", 7.50, "sugarCookies.png", 0.25), 10);
		
		assertEquals(30, Stock.getInventory().get(0));
		assertEquals(3, Stock.getInventory().size());
		assertEquals(true, Stock.getInventory().containsKey(2));
		
		Stock.adjustStock(0, -6);
		Stock.adjustStock(2, 10);
		
		assertEquals(Stock.getInventory().get(0), 24);
		assertEquals(Stock.getInventory().get(2), 20);
	}
	
	@Test
	void messageClassTests() {
		Message m = new Message("This is a test message");
		assertEquals("This is a test message", m.getMessage());
	}
	
	@Test 
	void requestClassTests() {
		Request r = new Request(Request.RequestType.VIEW_CART, "data0", "data1");
		assertEquals(r.data[0], "data0");
		assertEquals(r.data[1], "data1");
		assertEquals(r.type, Request.RequestType.VIEW_CART);
	}
	
	
	@Test
	void costClassTest() {
		
		Stock.clearInventory();
		Stock.addItemToStock(new Item(0, "Cheetos", "A tasty, cheesy snack!", 1.50, "cheeto.png", 0), 30);
		NetworkHandler.setLocation("Indiana");
		
		Cost cost = new Cost();
		DiscountCode d = new DiscountCode(0.2);
		
		assertEquals(0, cost.getCost());
		cost.setDiscountCode(d);
		
		Cart cart = new Cart(cost);
		
		cart.addItem(0);
		
		assertEquals((1.50 -(0.2 * 1.50))+(0.07*(1.50 -(0.2 * 1.50))), cost.getCost());
		
	}
	
	@Test
	void cartClassTest() {
		Stock.clearInventory();
		Stock.addItemToStock(new Item(0, "Cheetos", "A tasty, cheesy snack!", 1.50, "cheeto.png", 0), 30);
		NetworkHandler.setLocation("Alaska");
		
		Cart cart = new Cart(new Cost());
		cart.addItem(0);
		assertEquals(1, cart.numItems());
		cart.addItem(0);
		assertEquals(2, cart.numItems());
		
		assertEquals(3.0, cart.getCost());
		
		cart.updateQuantity(0, 4);
		assertEquals(4, cart.getItems().get(0));
		assertEquals(6.0, cart.getCost());
		
		cart.setDiscountCode(new DiscountCode(0.25));
		assertEquals(4.5, cart.getCost());
		
		cart.updateQuantity(0, 0);
		assertEquals(0, cart.numItems());
		assertEquals(0, cart.getItems().size());
	}
	
	@Test
	void systemTest() {
		
		File file = new File("Cart.txt");
        file.delete();
        
		NetworkHandler nw = new NetworkHandler();
		NetworkHandler.setLocation("Alaska");
		
		Stock.addItemToStock(new Item(0, "Cheetos", "A tasty, cheesy snack!", 1.50, "cheeto.png", 0), 30);
		Stock.addItemToStock(new Item(1, "Pepsi", "A tasty, sugary drink!", 2.75, "pepsi.jpeg", 0.1), 100);
		Stock.addItemToStock(new Item(2, "Sugar Cookies", "12 pack of tasty cookies with some frosting", 7.50, "sugarCookies.png", 0.25), 10);
		
		Request viewCart = new Request(Request.RequestType.VIEW_CART);
		Request addItem = new Request(Request.RequestType.ADD_ITEM, "1");
		Request modifyQuantity  = new Request(Request.RequestType.MODIFY_QUANTITY, "1", "3");
		Request applyDiscount = new Request(Request.RequestType.APPLY_DISCOUNT, writeSerial(new DiscountCode(0.2)));
		
		nw.receiveRequest(viewCart);
		assertEquals("Cart with 0 items costing 0.0",NetworkHandler.lastReturnedMessage);
		
		nw.receiveRequest(addItem);
		nw.receiveRequest(viewCart);
		assertEquals("Cart with 1 items costing 2.475",NetworkHandler.lastReturnedMessage);
		
		nw.receiveRequest(modifyQuantity);
		nw.receiveRequest(viewCart);
		assertEquals("Cart with 3 items costing 7.425",NetworkHandler.lastReturnedMessage);
		
		nw.receiveRequest(applyDiscount);
		nw.receiveRequest(viewCart);
		assertEquals("Cart with 3 items costing 5.94",NetworkHandler.lastReturnedMessage);
		
		//Testing object read from disk
		
		NetworkHandler nw2 = new NetworkHandler();
		NetworkHandler.setLocation("Alaska");
		
		nw.receiveRequest(viewCart);
		assertEquals("Cart with 3 items costing 5.94",NetworkHandler.lastReturnedMessage);
		
	}
	
	@Test
	void exceptionTesting() {
		File file = new File("Cart.txt");
        file.delete();
        
		NetworkHandler nw = new NetworkHandler();
		NetworkHandler.setLocation("Alaska");
		
		Request addItem = new Request(Request.RequestType.ADD_ITEM, "2");
		Request addOutOfStockItem = new Request(Request.RequestType.ADD_ITEM, "0");
		Request modifyQuantity  = new Request(Request.RequestType.MODIFY_QUANTITY, "2", "11");
		
		Request addRemovableItem = new Request(Request.RequestType.ADD_ITEM, "1");
		Request removeItem  = new Request(Request.RequestType.MODIFY_QUANTITY, "1", "0");
		
		Request badRequest = new Request(Request.RequestType.VIEW_CART, "JUNK");
		
		Stock.addItemToStock(new Item(0, "Cheetos", "A tasty, cheesy snack!", 1.50, "cheeto.png", 0), 0);
		Stock.addItemToStock(new Item(1, "Pepsi", "A tasty, sugary drink!", 2.75, "pepsi.jpeg", 0.1), 100);
		Stock.addItemToStock(new Item(2, "Sugar Cookies", "12 pack of tasty cookies with some frosting", 7.50, "sugarCookies.png", 0.25), 10);
		
		nw.reconnect();
		assertEquals("Reconnected!",NetworkHandler.lastReturnedMessage);
		
		nw.receiveRequest(addItem);
		nw.receiveRequest(modifyQuantity);
		assertEquals("Too much stock requested.",NetworkHandler.lastReturnedMessage);
		
		nw.receiveRequest(addOutOfStockItem);
		assertEquals("Item is out of stock.",NetworkHandler.lastReturnedMessage);	
		
		nw.receiveRequest(badRequest);
		assertEquals("Incorrect number of arguments for request.",NetworkHandler.lastReturnedMessage);
		
	}

}
















