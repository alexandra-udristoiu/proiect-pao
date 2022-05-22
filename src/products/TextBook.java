package products;

import person.Author;
import repository.BookRepository;
import repository.TextBookRepository;

public class TextBook extends Book {

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public int getGrade() {
		return grade;
	}

	public void setGrade(int grade) {
		this.grade = grade;
	}

	private String subject;
	
	private int grade;

	public TextBook(String title, Author author, String isbn, int pages, float price, int productsInStock, String subject,
			int grade) {
		super(title, author, isbn, pages, price, productsInStock);
		this.subject = subject;
		this.grade = grade;
	}
	
	@Override
	public String toString() {
		return new StringBuilder()
				.append("Fiction Book " + this.title + "\n")
				.append("Author: " + this.author.getFirstName() + " " + this.author.getLastName() + "\n")
				.append("Subject: " + this.subject + "\n")
				.append("Grade: " + this.grade + "\n")
				.append("Number of pages: " + this.numberOfPages + "\n")
				.append("ISBN: " + this.isbn + "\n")
				.append("Price: " + this.getCurrentPrice())
				.append("Average rating: " + this.bookReview.getAverage())
				.toString();
	}

	@Override
	public BookTypes getType() {
		return BookTypes.TEXTBOOK;
	}

	@Override
	public BookRepository getRepository() {
		return TextBookRepository.getInstance();
	}
}
