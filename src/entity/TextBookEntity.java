package entity;

public class TextBookEntity {

	private String isbn;
	
	private String title;
	
	private int authorId;
	
	private int numberOfPages;
	
	private float price;
	
	private float discount;
	
	private int productsInStock;
	
	private String subject;
	
	private int grade;

	public TextBookEntity(String isbn, String title, int authorId, int numberOfPages, float price, float discount,
			int productsInStock, String subject, int grade) {
		this.isbn = isbn;
		this.title = title;
		this.authorId = authorId;
		this.numberOfPages = numberOfPages;
		this.price = price;
		this.discount = discount;
		this.productsInStock = productsInStock;
		this.subject = subject;
		this.grade = grade;
	}

	public String getIsbn() {
		return isbn;
	}

	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public int getAuthorId() {
		return authorId;
	}

	public void setAuthorId(int authorId) {
		this.authorId = authorId;
	}

	public int getNumberOfPages() {
		return numberOfPages;
	}

	public void setNumberOfPages(int numberOfPages) {
		this.numberOfPages = numberOfPages;
	}

	public float getPrice() {
		return price;
	}

	public void setPrice(float price) {
		this.price = price;
	}

	public float getDiscount() {
		return discount;
	}

	public void setDiscount(float discount) {
		this.discount = discount;
	}

	public int getProductsInStock() {
		return productsInStock;
	}

	public void setProductsInStock(int productsInStock) {
		this.productsInStock = productsInStock;
	}

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

}
