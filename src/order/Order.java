package order;

import java.util.ArrayList;

import person.Customer;
import products.Product;

public class Order {

	private Customer customer;
	
	private ArrayList<Product> products;
	
	private float price;

	public Order(Customer customer, ArrayList<Product> products) {
		this.customer = customer;
		this.products = products;
		this.price = calculatePrice();
	}
	
	private float calculatePrice() {
		float sum = 0;
		for (Product p : products) {
			sum += p.getCurrentPrice();
		}
		return sum;
	}
	
	public boolean checkProductsAreInStock() {
		for (Product p : products) {
			if (!p.checkInStock()) {
				return false;
			}
		}
		return true;
	}

	public ArrayList<Product> getProducts() {
		return products;
	}
	
	public float getPrice() {
		return price;
	}
	
	public void decreaseStock() {
		for (Product p : products) {
			p.decreaseStock();
		}
	}
}
