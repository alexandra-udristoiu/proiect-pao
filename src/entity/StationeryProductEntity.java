package entity;

public class StationeryProductEntity {

	private int id;
	
	private String name;
	
	private float price;
	
	private int productsInStock;
	
	private int numberOfItems;
	
	public StationeryProductEntity(int id, String name, float price, int productsInStock, int numberOfItems) {
		this.id = id;
		this.name = name;
		this.price = price;
		this.productsInStock = productsInStock;
		this.numberOfItems = numberOfItems;
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public float getPrice() {
		return price;
	}

	public void setPrice(float price) {
		this.price = price;
	}

	public int getProductsInStock() {
		return productsInStock;
	}

	public void setProductsInStock(int productsInStock) {
		this.productsInStock = productsInStock;
	}

	public int getNumberOfItems() {
		return numberOfItems;
	}

	public void setNumberOfItems(int numberOfItems) {
		this.numberOfItems = numberOfItems;
	}

}
