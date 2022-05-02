package products;

import person.Author;

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

	public TextBook(String title, Author author, String isbn, int pages, float price, int numberInStock, String subject,
			int grade) {
		super(title, author, isbn, pages, price, numberInStock);
		this.subject = subject;
		this.grade = grade;
	}
	
	@Override
	public void printInformation() {
		System.out.println("Nonfiction Book " + this.title);
		System.out.println(this.author.getFirstName() + " " + this.author.getLastName());
		System.out.println("Subject: " + this.subject);
		System.out.println("Grade: " + this.grade);
		System.out.println("Number of pages: " + this.numberOfPages);
		System.out.println("ISBN: " + this.isbn);
		System.out.println("Price: " + this.getCurrentPrice());
		System.out.println("Average rating: " + this.bookReview.getAverage());
	}

	@Override
	public BookTypes getType() {
		return BookTypes.TEXTBOOK;
	}
}
