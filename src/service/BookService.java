package service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;
import java.util.TreeSet;

import exception.DuplicateIdException;
import exception.WrongInputException;
import person.Author;
import products.Book;
import products.FictionBook;
import products.NonfictionBook;
import products.TextBook;

public class BookService implements Service {
	
	private static BookService instance = null;
	
	private TreeSet<Book> books;
	
	private HashMap<String, Book> booksMap;
	
	private Scanner scanner;
	
	private BookService() {
		books = new TreeSet<>();
		booksMap = new HashMap<>();
		scanner = new Scanner(System.in);
	}
	
	public static BookService getInstance() {
		if (instance == null) {
			instance = new BookService();
		}
		return instance;
	}
	
	public void deleteBook(String isbn) {
		Book book = booksMap.remove(isbn);
		books.remove(book);
		book.getAuthor().removeBook(book);
	}
	
	public void deleteBook() {
		System.out.println("Book ISBN: ");
		String isbn = scanner.next();
		deleteBook(isbn);
	}
	
	public void printBooks() {
		for (Book b : books) {
			b.printInformation();
		}
	}
	
	public Book getBookById(String isbn) {
		return booksMap.get(isbn);
	}
	
	public void addBook() throws WrongInputException, DuplicateIdException {
		System.out.println("Author ID:");
		int authorId = scanner.nextInt();
		Author author = AuthorService.getInstance().getAuthorById(authorId);
		if (author == null) {
			throw new WrongInputException("Author does not exist");
		}
		Book book;
		System.out.println("Book type (1 fiction, 2 nonfiction, 3 textbook):");
		int bookType = scanner.nextInt();
		switch (bookType) {
		case 1:
			book = createFictionBook(author);
			break;
		case 2:
			book = createNonFicitonBook(author);
			break;
		case 3:
			book = createTextBook(author);
			break;
		default:
			throw new WrongInputException("Book type does not exist");
		}
		if (booksMap.containsKey(book.getIsbn())) {
			throw new DuplicateIdException();
		}
		books.add(book);
		booksMap.put(book.getIsbn(), book);
		author.addBook(book);
	}

	private Book createFictionBook(Author author) {
		System.out.println("Title:");
		String title = scanner.next();
		System.out.println("ISBN:");
		String isbn = scanner.next();
		System.out.println("Number of pages");
		int numberOfPages = scanner.nextInt();
		System.out.println("Price:");
		float price = scanner.nextFloat();
		System.out.println("Genre:");
		String genre = scanner.next();
		return new FictionBook(title, author, isbn, numberOfPages, price, 0, genre);
	}

	private Book createNonFicitonBook(Author author) {
		System.out.println("Title:");
		String title = scanner.next();
		System.out.println("ISBN:");
		String isbn = scanner.next();
		System.out.println("Number of pages");
		int numberOfPages = scanner.nextInt();
		System.out.println("Price:");
		float price = scanner.nextFloat();
		System.out.println("Domain:");
		String domain = scanner.next();
		return new NonfictionBook(title, author, isbn, numberOfPages, price, 0, domain);
	}

	private Book createTextBook(Author author) {
		System.out.println("Title:");
		String title = scanner.next();
		System.out.println("ISBN:");
		String isbn = scanner.next();
		System.out.println("Number of pages");
		int numberOfPages = scanner.nextInt();
		System.out.println("Price:");
		float price = scanner.nextFloat();
		System.out.println("Subject:");
		String subject = scanner.next();
		System.out.println("Grade:");
		int grade = scanner.nextInt();
		return new TextBook(title, author, isbn, numberOfPages, price, 0, subject, grade);
	}
	
	public void addInStock() throws WrongInputException {
		System.out.println("ISBN:");
		String isbn = scanner.next();
		if (!booksMap.containsKey(isbn)) {
			throw new WrongInputException("Book does not exist");
		}
		System.out.println("Number of products added:");
		int number = scanner.nextInt();
		booksMap.get(isbn).addInStock(number);
	}
	
	public void setBookDiscount() throws WrongInputException {
		System.out.println("ISBN:");
		String isbn = scanner.next();
		if (!booksMap.containsKey(isbn)) {
			throw new WrongInputException("Book does not exist");
		}
		System.out.println("Discount:");
		float discount = scanner.nextFloat();
		if (discount < 0 || discount >= 1) {
			throw new WrongInputException("Discount must be between 0 and 1");
		}
		booksMap.get(isbn).setDiscount(discount);
	}
	
	@Override
	public void printOptions() {
		System.out.println("1 - Add book");
		System.out.println("2 - Delete book");
		System.out.println("3 - Print books");
		System.out.println("4 - Add book in stock");
		System.out.println("5 - Set book discount");
	}

	@Override
	public void chooseOption() {
		int option = scanner.nextInt();
		switch (option) {
		case 1:
			try {
				addBook();
			} catch (WrongInputException | DuplicateIdException e) {
				e.printStackTrace();
			}
			AuditService.getInstance().addAction("Add book");
			break;
		case 2:
			deleteBook();
			AuditService.getInstance().addAction("Delete book");
			break;
		case 3:
			printBooks();
			AuditService.getInstance().addAction("Print books");
			break;
		case 4:
			try {
				addInStock();
			} catch (WrongInputException e) {
				e.printStackTrace();
			}
			AuditService.getInstance().addAction("Add book in stock");
			break;
		case 5:
			try {
				setBookDiscount();
			} catch (WrongInputException e) {
				e.printStackTrace();
			}
			AuditService.getInstance().addAction("Set book discount");
		}
	}

	@Override
	public String getCsvPath() {
		return "csvFiles/books.csv";
	}

	@Override
	public void addFromCsv(String[] strings) {
		Author author = AuthorService.getInstance().getAuthorById(Integer.parseInt(strings[2]));
		Book book = null;
		switch (strings[0]) {
		case "Fiction":
			book = new FictionBook(strings[1], author, strings[3], Integer.parseInt(strings[4]), Float.parseFloat(strings[5]), Integer.parseInt(strings[6]),
					strings[7]);
			break;
		case "Nonfiction":
			book = new NonfictionBook(strings[1], author, strings[3], Integer.parseInt(strings[4]), Float.parseFloat(strings[5]), Integer.parseInt(strings[6]),
					strings[7]);
			break;
		case "Textbook":
			book = new TextBook(strings[1], author, strings[3], Integer.parseInt(strings[4]), Float.parseFloat(strings[5]), Integer.parseInt(strings[6]),
					strings[7], Integer.parseInt(strings[8]));
		}
		books.add(book);
		booksMap.put(book.getIsbn(), book);
		author.addBook(book);
	}

	@Override
	public List<String> getStringsForCsv() {
		List<String> strings = new ArrayList<>();
		for (Book book : books) {
			String current = null;
			switch (book.getType()) {
			case FICTION:
				current = "Fiction," + book.getTitle() + "," + Integer.toString(book.getAuthorId()) + "," + book.getIsbn() + "," + Integer.toString(book.getNumberOfPages())
					+ "," + Float.toString(book.getPrice()) + "," + Integer.toString(book.getProductsInStock()) + "," + ((FictionBook) book).getGenre();
				break;
			case NONFICTION:
				current = "Nonfiction," + book.getTitle() + "," + Integer.toString(book.getAuthorId()) + "," + book.getIsbn() + "," + Integer.toString(book.getNumberOfPages())
					+ "," + Float.toString(book.getPrice()) + "," + Integer.toString(book.getProductsInStock()) + "," + ((NonfictionBook) book).getDomain();
				break;
			case TEXTBOOK:
				current = "Textbook," + book.getTitle() + "," + Integer.toString(book.getAuthorId()) + "," + book.getIsbn() + "," + Integer.toString(book.getNumberOfPages())
					+ "," + Float.toString(book.getPrice()) + "," + Integer.toString(book.getProductsInStock()) + "," + ((TextBook) book).getSubject() + "," 
					+ Integer.toString(((TextBook) book).getGrade());
				break;
			}
			strings.add(current);
		}
		return strings;
	}
	
}
