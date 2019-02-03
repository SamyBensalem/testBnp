package test;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import entities.Item;
import job.BasketImpl;
import job.IBasket;

@SuppressWarnings("deprecation")
public class TestSupermarquet {
	/*
	 * We are a national supermarket chain that is interested in starting to use
	 * special offers in our stores. We stock the following products: Item Price (£)
	 * Apple 0.20 Orange 0.50 Watermelon 0.80
	 * 
	 * We would like to allow the following special offers: • Buy One Get One Free
	 * on Apples • Three For The Price Of Two on Watermelons
	 */
   
	private Item apple;
	private Item orange;
	private Item watermelon;
	private IBasket basket;
	private ApplicationContext context;

	

	public TestSupermarquet() {
		super();
		context = new ClassPathXmlApplicationContext("beans.xml");
		watermelon = (Item) context.getBean("Watermelon");
		apple = (Item) context.getBean("Apple");
		orange = (Item) context.getBean("Orange");
		basket = new BasketImpl();
	}

	@Test
	public void TestAddWatermelonToBasket() {
		basket.removeAllFromBasket();
		basket.addItemToBasket(watermelon);
		Assert.assertTrue(basket.checkIfbasketContainsItem(watermelon));
	}

	@Test
	public void TestAddAppleToBasket() {
		basket.removeAllFromBasket();
		basket.addItemToBasket(apple);
		Assert.assertTrue(basket.checkIfbasketContainsItem(apple));
	}

	@Test
	public void TestAddOrangeToBasket() {
		basket.removeAllFromBasket();
		basket.addItemToBasket(orange);
		Assert.assertTrue(basket.checkIfbasketContainsItem(orange));
	}

	@Test
	public void TestRemoveOrangeFromBasket() {
		basket.removeAllFromBasket();
		basket.addItemToBasket(orange); // we add orange
		basket.removeItemFromBasket(orange);// we remove the orange
		Assert.assertFalse(basket.checkIfbasketContainsItem(orange));
	}

	@Test
	public void TestRemoveAppleFromBasket() {
		basket.removeAllFromBasket();
		basket.addItemToBasket(apple); // we add orange
		basket.removeItemFromBasket(apple);// we remove the apple
		Assert.assertFalse(basket.checkIfbasketContainsItem(apple));
	}

	@Test
	public void TestRemoveWatermelonFromBasket() {
		basket.removeAllFromBasket();
		basket.addItemToBasket(watermelon); // we add orange
		basket.removeItemFromBasket(watermelon);// we remove the apple
		Assert.assertFalse(basket.checkIfbasketContainsItem(watermelon));
	}
	@Test
	public void TestremoveAllFromBasketMethod() {
		basket.addItemToBasket(apple);
		basket.addItemToBasket(orange);
		basket.addItemToBasket(watermelon);
		
		basket.removeAllFromBasket();
	
		Assert.assertFalse(basket.checkIfbasketContainsItem(apple)
				          ||basket.checkIfbasketContainsItem(orange)
				          ||basket.checkIfbasketContainsItem(watermelon));
	}

	@Test
	public void TestThatBasketDoesntContentAnElement() {
		basket.removeAllFromBasket();
		basket.addItemToBasket(apple);
		basket.addItemToBasket(orange);
		Assert.assertFalse(basket.checkIfbasketContainsItem(watermelon));
	}

	@Test
	public void TestTotalPriceCalculation() {
		/*
		 * We would like to see the output for an example basket containing the
		 * following items: Item Quantity Apple 4 Orange 3 Watermelon 5 • Buy One Get
		 * One Free on Apples • Three For The Price Of Two on Watermelons
		 * 
		 * so apples:0.20+0+0.20+0 = 0.40 oranges :0.50+0.50+0.50 = 1.50
		 * watermelons:0.80+ 0.80 + 0 + 0.80 +0.80 =3.20 total price = 0.40+1.50+3.20 =
		 * 5.1
		 * 
		 */
		basket.removeAllFromBasket();

		basket.addItemToBasket(apple);
		basket.addItemToBasket(apple);
		basket.addItemToBasket(apple);
		basket.addItemToBasket(apple);

		basket.addItemToBasket(orange);
		basket.addItemToBasket(orange);
		basket.addItemToBasket(orange);

		basket.addItemToBasket(watermelon);
		basket.addItemToBasket(watermelon);
		basket.addItemToBasket(watermelon);
		basket.addItemToBasket(watermelon);
		basket.addItemToBasket(watermelon);

		double expectedPrice = 5.1;
		double calculatedPrice = basket.calculateTotal_price();

		Assert.assertTrue(calculatedPrice == expectedPrice);
	}

}
