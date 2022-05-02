package person;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import order.Order;
import products.Book;
import products.Product;

public class Customer extends Person {
	
	private String address;
	
	private String email;
	
	private String phoneNumber;
	
	private List<Order> orders;
	
	private Set<Book> boughtBooks;

	public Customer(String lastName, String firstName, String address, String email, String phoneNumber) {
		super(lastName, firstName);
		this.address = address;
		this.email = email;
		this.phoneNumber = phoneNumber;
		this.orders = new ArrayList<>();
		this.boughtBooks = new TreeSet<>();
	}
	
	public void addOrder(Order order) {
		orders.add(order);
		for (Product p : order.getProducts()) {
			if (p.isBook()) {
				boughtBooks.add((Book) p);
			}
		}
	}
	
	public void printReaderProfile() {
		int pagesSum = 0;
		for (Book b : boughtBooks) {
			b.printInformation();
			pagesSum += b.getNumberOfPages();
		}
		System.out.println("Total number of books " + boughtBooks.size());
		System.out.println("Total number of pages " + pagesSum);
	}

	@Override
	public void printInformation() {
		System.out.println("Customer information");
		System.out.println("Email: " + this.email);
		System.out.println("Name: " + this.lastName + " " + this.firstName);
		System.out.println("Address " + this.address);
		System.out.println("Phone number " + this.phoneNumber);
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	
	public String getAddress() {
		return address;
	}

	public String getEmail() {
		return email;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public List<Order> getOrders() {
		return orders;
	}

	public Set<Book> getBoughtBooks() {
		return boughtBooks;
	}

	public void reviewBook(Book book, int mark) {
		if (this.boughtBooks.contains(book)) {
			book.addReview(this, mark);
		}
	}

}
