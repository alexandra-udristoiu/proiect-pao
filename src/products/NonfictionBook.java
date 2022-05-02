package products;

import person.Author;

public class NonfictionBook extends Book {
	
	private String domain;

	public NonfictionBook(String title, Author author, String isbn, int pages, float price, int numberInStock,
			String domain) {
		super(title, author, isbn, pages, price, numberInStock);
		this.domain = domain;
	}
	
	@Override
	public void printInformation() {
		System.out.println("Nonfiction Book " + this.title);
		System.out.println(this.author.getFirstName() + " " + this.author.getLastName());
		System.out.println("Domain: " + this.domain);
		System.out.println("Number of pages: " + this.numberOfPages);
		System.out.println("ISBN: " + this.isbn);
		System.out.println("Price: " + this.getCurrentPrice());
		System.out.println("Average rating: " + this.bookReview.getAverage());
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
}
