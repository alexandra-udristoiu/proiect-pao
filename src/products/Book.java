package products;

import java.util.Objects;

import person.Author;
import person.Customer;
import repository.BookRepository;

public abstract class Book implements Product, Comparable<Book> {

	protected String title;
	
	protected Author author;
	
	protected String isbn;

	protected int numberOfPages;
	
	protected float price;
	
	protected float discount;
	
	protected int productsInStock;
	
	protected BookReview bookReview;

	public Book(String title, Author author, String isbn, int pages, float price, int productsInStock) {
		this.title = title;
		this.author = author;
		this.isbn = isbn;
		this.numberOfPages = pages;
		this.price = price;
		this.discount = 0;
		this.productsInStock = productsInStock;
		this.bookReview = new BookReview();
	}

	public void setPrice(float price) {
		this.price = price;
	}

	public void setDiscount(float discount) {
		this.discount = discount;
	}
		
	public int getNumberOfPages() {
		return numberOfPages;
	}
	
	public Author getAuthor() {
		return author;
	}
	
	@Override
	public float getCurrentPrice() {
		return price - discount * price;
	}
	
	@Override
	public void decreaseStock() {
		this.productsInStock --;
	}
	
	@Override
	public void addInStock(int number) {
		this.productsInStock += number;
	}
	
	@Override
	public boolean checkInStock() {
		return this.productsInStock > 0;
	}

	public String getTitle() {
		return title;
	}

	public float getPrice() {
		return price;
	}

	public float getDiscount() {
		return discount;
	}

	public int getProductsInStock() {
		return productsInStock;
	}

	public String getIsbn() {
		return isbn;
	}
	
	public int getAuthorId() {
		return author.getId();
	}

	@Override
	public boolean isBook() {
		return true;
	}
	
	@Override
	public int compareTo(Book o) {
		if (title.compareTo(o.title) != 0) {
			return title.compareTo(o.title);
		}
		return isbn.compareTo(o.isbn);
	}

	public void addReview(Customer customer, int mark) {
		this.bookReview.addReview(customer, mark);
	}
	
	public abstract BookTypes getType();
	
	public abstract BookRepository getRepository();
	
}
