package job;
import entities.Item;

public interface IBasket {
	public boolean checkIfbasketContainsItem(Item i);
	public void addItemToBasket(Item item);
	public void removeItemFromBasket(Item item);
	public void displayContentBasket();
	public double calculateTotal_price();
	public void removeAllFromBasket();

}
