package products;

import person.Author;
import repository.BookRepository;
import repository.NonfictionBookRepository;

public class NonfictionBook extends Book {
	
	private String domain;

	public NonfictionBook(String title, Author author, String isbn, int pages, float price, int productsInStock,
			String domain) {
		super(title, author, isbn, pages, price, productsInStock);
		this.domain = domain;
	}
	
	@Override
	public String toString() {
		return new StringBuilder()
				.append("Fiction Book " + this.title + "\n")
				.append("Author: " + this.author.getFirstName() + " " + this.author.getLastName() + "\n")
				.append("Domain: " + this.domain + "\n")
				.append("Number of pages: " + this.numberOfPages + "\n")
				.append("ISBN: " + this.isbn + "\n")
				.append("Price: " + this.getCurrentPrice() + "\n")
				.append("Average rating: " + this.bookReview.getAverage())
				.toString();
	}

	public String getDomain() {
		return domain;
	}

	public void setDomain(String domain) {
		this.domain = domain;
	}

	@Override
	public BookTypes getType() {
		return BookTypes.NONFICTION;
	}

	@Override
	public BookRepository getRepository() {
		return NonfictionBookRepository.getInstance();
	}
}
