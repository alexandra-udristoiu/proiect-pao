package products;

public class StationeryProduct implements Product {
	
	private String name;
	
	private float price;
	
	private int productsInStock;
	
	private int numberOfItems;
	
	private int id;

	public StationeryProduct(String name, float price, int productsInStock, int numberOfItems, int id) {
		this.name = name;
		this.price = price;
		this.productsInStock = productsInStock;
		this.numberOfItems = numberOfItems;
		this.id = id;
	}

	@Override
	public float getCurrentPrice() {
		return price;
	}

	@Override
	public boolean checkInStock() {
		return productsInStock > 0;
	}

	@Override
	public boolean isBook() {
		return false;
	}

	@Override
	public void decreaseStock() {
		productsInStock --;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setPrice(float price) {
		this.price = price;
	}

	public void setProductsInStock(int productsInStock) {
		this.productsInStock = productsInStock;
	}

	public void setNumberOfItems(int numberOfItems) {
		this.numberOfItems = numberOfItems;
	}

	@Override
	public void addInStock(int number) {
		productsInStock += number;
	}
	
	@Override
	public String toString() {
		return new StringBuilder()
				.append("Stationary product " + this.id + " " + this.name + "\n")
				.append("Price " + this.price + "\n")
				.append("Number of items " + this.numberOfItems)
				.toString();
	}

	public String getName() {
		return name;
	}

	public float getPrice() {
		return price;
	}

	public int getProductsInStock() {
		return productsInStock;
	}

	public int getNumberOfItems() {
		return numberOfItems;
	}

	public int getId() {
		return id;
	}

}
