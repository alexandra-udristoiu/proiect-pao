package products;

public interface Product {

	float getCurrentPrice();
	
	boolean checkInStock();
	
	boolean isBook();
	
	void decreaseStock();
	
	void addInStock(int number);
}
