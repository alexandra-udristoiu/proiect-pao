package products;

import person.Author;
import repository.BookRepository;
import repository.FictionBookRepository;

public class FictionBook extends Book {
	
	private String genre;
	
	public FictionBook(String title, Author author, String isbn, int pages, float price, int productsInStock, String genre) {
		super(title, author, isbn, pages, price, productsInStock);
		this.genre = genre;
	}
	
	@Override
	public String toString() {
		return new StringBuilder()
				.append("Fiction Book " + this.title + "\n")
				.append("Author: " + this.author.getFirstName() + " " + this.author.getLastName() + "\n")
				.append("Genre: " + this.genre + "\n")
				.append("Number of pages: " + this.numberOfPages + "\n")
				.append("ISBN: " + this.isbn + "\n")
				.append("Price: " + this.getCurrentPrice() + "\n")
				.append("Average rating: " + this.bookReview.getAverage())
				.toString();
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

	@Override
	public BookRepository getRepository() {
		return FictionBookRepository.getInstance();
	}

}
