package job;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import entities.Item;

public class BasketImpl implements IBasket {

	private Map<String, Double> content;
	private double total_price = 0;

	// here we instanciate each items in order to put dynamically the price
	// in the method calculateTotal_price bellow
	// (we use spring to inject these depencies)
	// Note:in the rest of the code please uncomment "sysout messages" if you need
	// to see if a step in particular is done
	private Item apple;
	private Item orange;
	private Item watermelon;
	private ApplicationContext context;

	// constructor
	public BasketImpl() {
		super();
		// Spring application context
		context = new ClassPathXmlApplicationContext("beans.xml");
		// System.out.println(" Application context is created !");

		// Basket content
		content = new HashMap<String, Double>();
		apple = (Item) context.getBean("Apple");
		orange = (Item) context.getBean("Orange");
		watermelon = (Item) context.getBean("Watermelon");
		// System.out.println("Basket is created !");

	}

	// getters and setters
	public double getTotal_price() {
		setTotal_price(calculateTotal_price());// the price is calculated before
		return total_price;
	}

	public void setTotal_price(double total_price) {
		this.total_price = total_price;
	}

	// methods availables:
	// ---------------------------------
	// - public void removeAllFromBasket()
	// - checkIfbasketContainsItem(Item i);
	// - addItemToBasket(Item item);
	// - removeItemFromBasket(Item item);
	// - displayContentBasket();
	// - calculateTotal_price();
	// ----------------------------------

	public void removeAllFromBasket() {
		content.clear();
	}

	public boolean checkIfbasketContainsItem(Item i) {
		if (i != null) {
			return content.containsKey(i.getName());
		}
		return false;
	}

	public void addItemToBasket(Item item) {
		if (item != null) {

			if (content.containsKey(item.getName())) {
				double amount = content.get(item.getName());
				content.put(item.getName(), new Double(amount + 1));
			} else {
				content.put(item.getName(), new Double(1));
			}
			// System.out.println(item.getName() + " is added to basket");
		}
	}

	public void removeItemFromBasket(Item item) {
		if (content.containsKey(item.getName())) {
			double amount = content.get(item.getName());
			if (amount > 1) {
				content.put(item.getName(), new Double(amount - 1));
			} else if (amount == 1) {
				content.remove(item.getName());
			}
		} else {
			System.out.println(" there is not any " + item.getName() + " in the basket");
		}
	}

	public void displayContentBasket() {
		// Get the entries
		Set set = content.entrySet();
		// Get an iterator
		Iterator i = set.iterator();
		// Display elements
		System.out.println("Item" + "     " + "Quantity");
		System.out.println();
		while (i.hasNext()) {
			Map.Entry me = (Map.Entry) i.next();
			System.out.print(me.getKey() + "     ");
			System.out.println(me.getValue());
		}

	}

	public double calculateTotal_price() {
		// because of the declarations above we can use
		// dynamically the price of each items
		//
		// Apples
		// Buy One Get One Free on Apples
		double total_price_calculated = 0;
		if (content.containsKey("apple")) { // means that there is at least one Apple
			for (int item = 1; item <= content.get("apple"); item++) {
				if (item % 2 == 0)
					total_price_calculated += apple.getPrice(); // Buy One Get One Free on Apples means one apple on two
																// given is free
			}
		}

		// Watermelons
		// Three For The Price Of Two on Watermelons
		if (content.containsKey("watermelon")) { // means that there is at least one Apple
			for (int item = 1; item <= content.get("watermelon"); item++) {
				if (item == 1 && content.get("watermelon") < 3)
					total_price_calculated += watermelon.getPrice();// if the customer buy only one watermelon
				// if he buy at least 2 watermelons each three watermelons
				// the price increase of twice the price of a watermelon
				// each time that the amount of items is a multiple of two
				// because the third one is not taken in acount
				if (item % 2 == 0 && item > 1)
					total_price_calculated += 1.60;
			}

		}
		// No special rules about Oranges, the price
		// increase of 0.50 at each orange bought
		if (content.containsKey("orange")) {
			for (int item = 1; item <= content.get("orange"); item++) {
				total_price_calculated += orange.getPrice();
			}

		}
		return total_price_calculated;

	}

}// Basket
