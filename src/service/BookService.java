package service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

import entity.FictionBookEntity;
import entity.NonfictionBookEntity;
import entity.TextBookEntity;
import exception.DuplicateIdException;
import exception.WrongInputException;
import person.Author;
import products.Book;
import products.FictionBook;
import products.NonfictionBook;
import products.TextBook;
import repository.FictionBookRepository;
import repository.NonfictionBookRepository;
import repository.TextBookRepository;

public class BookService implements Service {
	
	private static BookService instance = null;
	
	private Set<Book> books;
	
	private Map<String, Book> booksMap;
	
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
		
		book.getRepository().delete(isbn);
	}
	
	public void deleteBook() {
		System.out.println("Book ISBN: ");
		String isbn = scanner.next();
		deleteBook(isbn);
	}
	
	public void printBooks() {
		for (Book book : books) {
			System.out.println(book);
		}
	}
	
	public Book getBookById(String isbn) {
		return booksMap.get(isbn);
	}
	
	private void addBook(Book book) {
		books.add(book);
		booksMap.put(book.getIsbn(), book);
		book.getAuthor().addBook(book);
	}
	
	public void addBook() throws WrongInputException, DuplicateIdException {
		System.out.println("Author ID:");
		int authorId = scanner.nextInt();
		Author author = AuthorService.getInstance().getAuthorById(authorId);
		if (author == null) {
			throw new WrongInputException("Author does not exist");
		}
		System.out.println("ISBN:");
		String isbn = scanner.next();
		if (booksMap.containsKey(isbn)) {
			throw new DuplicateIdException();
		}
		Book book;
		System.out.println("Book type (1 fiction, 2 nonfiction, 3 textbook):");
		int bookType = scanner.nextInt();
		switch (bookType) {
		case 1:
			book = createFictionBook(author, isbn);
			break;
		case 2:
			book = createNonFicitonBook(author, isbn);
			break;
		case 3:
			book = createTextBook(author, isbn);
			break;
		default:
			throw new WrongInputException("Book type does not exist");
		}
		addBook(book);
	}

	private Book createFictionBook(Author author, String isbn) {
		System.out.println("Title:");
		scanner.nextLine();
		String title = scanner.nextLine();
		System.out.println("Number of pages:");
		int numberOfPages = scanner.nextInt();
		System.out.println("Price:");
		float price = scanner.nextFloat();
		System.out.println("Products in stock:");
		int productsInStock = scanner.nextInt();
		System.out.println("Genre:");
		String genre = scanner.next();
		
		FictionBookRepository.getInstance().add(new FictionBookEntity(isbn, title, author.getId(), numberOfPages, price, 0, productsInStock, genre));
		
		return new FictionBook(title, author, isbn, numberOfPages, price, productsInStock, genre);
	}

	private Book createNonFicitonBook(Author author, String isbn) {
		System.out.println("Title:");
		scanner.nextLine();
		String title = scanner.nextLine();
		System.out.println("Number of pages:");
		int numberOfPages = scanner.nextInt();
		System.out.println("Price:");
		float price = scanner.nextFloat();
		System.out.println("Products in stock:");
		int productsInStock = scanner.nextInt();
		System.out.println("Domain:");
		String domain = scanner.next();
		
		NonfictionBookRepository.getInstance().add(new NonfictionBookEntity(isbn, title, author.getId(), numberOfPages, price, 0, productsInStock, domain));
		
		return new NonfictionBook(title, author, isbn, numberOfPages, price, productsInStock, domain);
	}

	private Book createTextBook(Author author, String isbn) {
		System.out.println("Title:");
		scanner.nextLine();
		String title = scanner.nextLine();
		System.out.println("Number of pages:");
		int numberOfPages = scanner.nextInt();
		System.out.println("Price:");
		float price = scanner.nextFloat();
		System.out.println("Products in stock:");
		int productsInStock = scanner.nextInt();
		System.out.println("Subject:");
		String subject = scanner.next();
		System.out.println("Grade:");
		int grade = scanner.nextInt();
		
		TextBookRepository.getInstance().add(new TextBookEntity(isbn, title, author.getId(), numberOfPages, price, 0, productsInStock, subject, grade));
		
		return new TextBook(title, author, isbn, numberOfPages, price, productsInStock, subject, grade);
	}
	
	public void addInStock() throws WrongInputException {
		System.out.println("ISBN:");
		String isbn = scanner.next();
		if (!booksMap.containsKey(isbn)) {
			throw new WrongInputException("Book does not exist");
		}
		System.out.println("Number of products added:");
		int number = scanner.nextInt();
		Book book = booksMap.get(isbn);
		book.addInStock(number);
		
		book.getRepository().updateStock(isbn, book.getProductsInStock());
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
		Book book = booksMap.get(isbn);
		book.setDiscount(discount);
		
		book.getRepository().updateDiscount(isbn, discount);
	}
	
	public void printBooksInStock() {
		List<Book> booksInStock = books.stream()
				.filter(book -> book.checkInStock())
				.sorted((x, y) -> Float.compare(x.getCurrentPrice(), y.getCurrentPrice()))
				.collect(Collectors.toList());
		for (Book book : booksInStock) {
			System.out.println(book);
		}
	}
	
	@Override
	public void printOptions() {
		System.out.println("1 - Add book");
		System.out.println("2 - Delete book");
		System.out.println("3 - Print books");
		System.out.println("4 - Add book in stock");
		System.out.println("5 - Set book discount");
		System.out.println("6 - Print books in stock sorted by price");
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
			break;
		case 6:
			printBooksInStock();
			AuditService.getInstance().addAction("Print books in stock sorted by price");
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

	@Override
	public void getFromDatabase() {
		List<FictionBookEntity> fictionBookEntities = FictionBookRepository.getInstance().getAll();
		for (FictionBookEntity entity : fictionBookEntities) {
			Author author = AuthorService.getInstance().getAuthorById(entity.getAuthorId());
			Book book = new FictionBook(entity.getTitle(), author, entity.getIsbn(), entity.getNumberOfPages(), entity.getPrice(), entity.getProductsInStock(), 
					entity.getGenre());
			this.addBook(book);
		}
		
		List<NonfictionBookEntity> nonfictionBookEntities = NonfictionBookRepository.getInstance().getAll();
		for (NonfictionBookEntity entity : nonfictionBookEntities) {
			Author author = AuthorService.getInstance().getAuthorById(entity.getAuthorId());
			Book book = new NonfictionBook(entity.getTitle(), author, entity.getIsbn(), entity.getNumberOfPages(), entity.getPrice(), entity.getProductsInStock(), 
					entity.getDomain());
			this.addBook(book);
		}
		
		List<TextBookEntity> textBookEntities = TextBookRepository.getInstance().getAll();
		for (TextBookEntity entity : textBookEntities) {
			Author author = AuthorService.getInstance().getAuthorById(entity.getAuthorId());
			Book book = new TextBook(entity.getTitle(), author, entity.getIsbn(), entity.getNumberOfPages(), entity.getPrice(), entity.getProductsInStock(), 
					entity.getSubject(), entity.getGrade());
			this.addBook(book);
		}
	}
	
}
