package products;

import person.Author;

public class FictionBook extends Book {
	
	private String genre;
	
	public FictionBook(String title, Author author, String isbn, int pages, float price, int numberInStock, String genre) {
		super(title, author, isbn, pages, price, numberInStock);
		this.genre = genre;
	}

	@Override
	public void printInformation() {
		System.out.println("Fiction Book " + this.title);
		System.out.println(this.author.getFirstName() + " " + this.author.getLastName());
		System.out.println("Genre: " + this.genre);
		System.out.println("Number of pages: " + this.numberOfPages);
		System.out.println("ISBN: " + this.isbn);
		System.out.println("Price: " + this.getCurrentPrice());
		System.out.println("Average rating: " + this.bookReview.getAverage());
	}

	@Override
	public BookTypes getType() {
		return BookTypes.FICTION;
	}

	public void setGenre(String genre) {
		this.genre = genre;
	}

	public String getGenre() {
		return genre;
	}

}
