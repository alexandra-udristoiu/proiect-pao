package person;

import java.util.Set;
import java.util.TreeSet;

import products.Book;

public class Author extends Person {
	
	private Set<Book> books;
	
	private int id;

	public Author(String lastName, String firstName, int id) {
		super(lastName, firstName);
		this.books = new TreeSet<>();
		this.id = id;
	}
	
	public void addBook(Book book) {
		books.add(book);
	}
	
	public void removeBook(Book book) {
		books.remove(book);
	}

	@Override
	public void printInformation() {
		System.out.println("Author " + this.id + " " + this.firstName + " " + this.lastName);
		System.out.println("Number of books by this author " + books.size());
	}
	
	public void printBooksByAuthor() {
		for (Book b : books) {
			b.printInformation();
		}
	}

	public Set<Book> getBooks() {
		return books;
	}
	
	public int getId() {
		return id;
	}
	
}
