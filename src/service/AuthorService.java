package service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import entity.AuthorEntity;
import exception.DuplicateIdException;
import exception.WrongInputException;
import person.Author;
import products.Book;
import repository.AuthorRepository;

public class AuthorService implements Service {
	
	private static AuthorService instance = null;
	
	private Map<Integer, Author> authors;
	
	private Scanner scanner;
	
	private AuthorService() {
		this.authors = new HashMap<>();
		this.scanner = new Scanner(System.in);
	}
	
	public static AuthorService getInstance() {
		if (instance == null) {
			instance = new AuthorService();
		}
		return instance;
	}
	
	public void addAuthor() throws DuplicateIdException {
		System.out.println("Author id:");
		int id = scanner.nextInt();
		if (authors.containsKey(id)) {
			throw new DuplicateIdException();
		}
		System.out.println("Last name:");
		String lastName = scanner.next();
		System.out.println("First name:");
		String firstName = scanner.next();
		Author author = new Author(lastName, firstName, id);
		authors.put(id, author);
		
		AuthorRepository.getInstance().add(new AuthorEntity(id, lastName, firstName));
	}
	
	public Author getAuthorById(int id) {
		return authors.get(id);
	}
	
	public void printAuthors() {
		for (Author author : authors.values()) {
			System.out.println(author);
		}
	}
	
	public void printBooksByAuthor() throws WrongInputException {
		System.out.println("Author ID:");
		int id = scanner.nextInt();
		if (!authors.containsKey(id)) {
			throw new WrongInputException("Author does not exist");
		}
		Author author = authors.get(id);
		System.out.println(author);
		for (Book book : author.getBooks()) {
			System.out.println(book);
		}
	}
	
	public void deleteAuthor() throws WrongInputException {
		System.out.println("Author ID:");
		int id = scanner.nextInt();
		Author author = authors.remove(id);
		if (author == null) {
			throw new WrongInputException("Author does not exist");
		}
		for (Book book : author.getBooks()) {
			BookService.getInstance().deleteBook(book.getIsbn());
		}
		
		AuthorRepository.getInstance().delete(id);
	}
	
	public void editAuthor() throws WrongInputException {
		System.out.println("Author ID:");
		int id = scanner.nextInt();
		Author author = authors.get(id);
		if (author == null) {
			throw new WrongInputException("Author does not exist");
		}
		System.out.println("Last name:");
		String lastName = scanner.next();
		System.out.println("First name:");
		String firstName = scanner.next();
		author.setLastName(lastName);
		author.setFirstName(firstName);
		
		AuthorRepository.getInstance().update(new AuthorEntity(id, lastName, firstName));
	}

	@Override
	public void printOptions() {
		System.out.println("1 - Add author");
		System.out.println("2 - Print authors");
		System.out.println("3 - Print books by author");
		System.out.println("4 - Delete author");
		System.out.println("5 - Edit author");
	}

	@Override
	public void chooseOption() {
		int option = scanner.nextInt();
		switch (option) {
		case 1:
			try {
				addAuthor();
			} catch (DuplicateIdException e1) {
				e1.printStackTrace();
			}
			AuditService.getInstance().addAction("Add author");
			break;
		case 2:
			printAuthors();
			AuditService.getInstance().addAction("Print authors");
			break;
		case 3:
			try {
				printBooksByAuthor();
			} catch (WrongInputException e) {
				e.printStackTrace();
			}
			AuditService.getInstance().addAction("Print books by author");
			break;
		case 4:
			try {
				deleteAuthor();
			} catch (WrongInputException e) {
				e.printStackTrace();
			}
			AuditService.getInstance().addAction("Delete author");
			break;
		case 5:
			try {
				editAuthor();
			} catch (WrongInputException e) {
				e.printStackTrace();
			}
			AuditService.getInstance().addAction("Edit author");
		}
	}

	@Override
	public String getCsvPath() {
		return "csvFiles/authors.csv";
	}

	@Override
	public void addFromCsv(String[] strings) {
		Author author = new Author(strings[0], strings[1], Integer.parseInt(strings[2]));
		authors.put(author.getId(), author);
	}

	@Override
	public List<String> getStringsForCsv() {
		List<String> strings = new ArrayList<>();
		for (Author author : authors.values()) {
			strings.add(author.getLastName() + "," + author.getFirstName() + "," + Integer.toString(author.getId()));
		}
		return strings;
	}

	@Override
	public void getFromDatabase() {
		List<AuthorEntity> authorEntities = AuthorRepository.getInstance().getAll();
		for (AuthorEntity entity : authorEntities) {
			Author author = new Author(entity.getLastName(), entity.getFirstName(), entity.getId());
			authors.put(author.getId(), author);
		}
		
	}

}
