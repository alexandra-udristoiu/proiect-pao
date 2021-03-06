package service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

import entity.CustomerEntity;
import exception.DuplicateIdException;
import exception.WrongInputException;
import order.Order;
import person.Customer;
import products.Book;
import products.Product;
import products.StationeryProduct;
import repository.CustomerRepository;
import repository.StationeryProductRepository;

public class CustomerService implements Service {

	private static CustomerService instance = null;
	
	private Map<String, Customer> customers;
	
	private Scanner scanner;
	
	public static CustomerService getInstance() {
		if (instance == null) {
			instance = new CustomerService();
		}
		return instance;
	}
	
	private CustomerService() {
		this.customers = new HashMap<>();
		this.scanner = new Scanner(System.in);
	}
	
	public void addCustomer() throws DuplicateIdException {
		System.out.println("Email:");
		String email = scanner.next();
		if (customers.containsKey(email)) {
			throw new DuplicateIdException();
		}
		System.out.println("Last name:");
		String lastName = scanner.next();
		System.out.println("First name:");
		String firstName = scanner.next();
		System.out.println("Phone number:");
		String phoneNumber = scanner.next();
		System.out.println("Address:");
		scanner.nextLine();
		String address = scanner.nextLine();
		Customer customer = new Customer(lastName, firstName, address, email, phoneNumber);
		customers.put(email, customer);
		
		CustomerRepository.getInstance().add(new CustomerEntity(email, lastName, firstName, address, phoneNumber));
	}
	
	private Customer getValidatedCustomer() throws WrongInputException {
		System.out.println("Email:");
		String email = scanner.next();
		if (!customers.containsKey(email)) {
			throw new WrongInputException("Customer does not exist");
		}
		return customers.get(email);
	}
	
	public void updateCustomer() throws WrongInputException {
		Customer customer = getValidatedCustomer();
		System.out.println("Last name:");
		String lastName = scanner.next();
		System.out.println("First name:");
		String firstName = scanner.next();
		System.out.println("Phone number:");
		String phoneNumber = scanner.next();
		System.out.println("Address:");
		scanner.nextLine();
		String address = scanner.nextLine();
		customer.setLastName(lastName);
		customer.setFirstName(firstName);
		customer.setAddress(address);
		customer.setPhoneNumber(phoneNumber);
		
		CustomerRepository.getInstance().update(new CustomerEntity(customer.getEmail(), lastName, firstName, address, phoneNumber));
	}
	
	private ArrayList<Product> getOrderProducts() throws WrongInputException {
		ArrayList<Product> products = new ArrayList<>();
		while (true) {
			System.out.println("Next product: (book for books, stationery for stationery products)");
			String type = scanner.next();
			if (type.equals("book")) {
				System.out.println("ISBN:");
				String isbn = scanner.next();
				Book book = BookService.getInstance().getBookById(isbn);
				if (book == null) {
					throw new WrongInputException("Product does not exist");
				}
				products.add(book);
			}
			else {
				if (type.equals("stationery")) {
					System.out.println("ID:");
					int id = scanner.nextInt();
					StationeryProduct stationery = StationeryProductService.getInstance().getStationeryProductById(id);
					if (stationery == null) {
						throw new WrongInputException("Product does not exist");
					}
					products.add(stationery);
				}
				else {
					break;
				}
			}
		}
		return products;
	}
	
	public void addOrder() throws WrongInputException {
		Customer customer = getValidatedCustomer();
		ArrayList<Product> products = getOrderProducts();
		Order order = new Order(customer, products);
		if (!order.checkProductsAreInStock()) {
			throw new WrongInputException("Products are not in stock");
		}
		System.out.println("Total price: " + order.getPrice());
		order.decreaseStock();
		for (Product product : products) {
			if (product instanceof StationeryProduct) {
				StationeryProduct stationeryProduct = (StationeryProduct) product;
				StationeryProductRepository.getInstance().updateStock(stationeryProduct.getId(), stationeryProduct.getProductsInStock());
			}
			else {
				Book book = (Book) product;
				book.getRepository().updateStock(book.getIsbn(), book.getProductsInStock());
			}
		}
		customer.addOrder(order);
	}
	
	public void printCustomerInformation() throws WrongInputException {
		Customer customer = getValidatedCustomer();
		System.out.println(customer);
	}
	
	public void printCustomerReaderProfile() throws WrongInputException {
		Customer customer = getValidatedCustomer();
		Set<Book> boughtBooks = customer.getBoughtBooks();
		int pagesSum = 0;
		for (Book book : boughtBooks) {
			System.out.println(book);
			pagesSum += book.getNumberOfPages();
		}
		System.out.println("Total number of books " + boughtBooks.size());
		System.out.println("Total number of pages " + pagesSum);
	}
	
	public void addReview() throws WrongInputException {
		Customer customer = getValidatedCustomer();
		System.out.println("Book isbn:");
		String isbn = scanner.next();
		Book book = BookService.getInstance().getBookById(isbn);
		if (book == null) {
			throw new WrongInputException("Book does not exist");
		}
		System.out.println("Mark:");
		int mark = scanner.nextInt();
		customer.reviewBook(book, mark);
	}
	
	public void deleteCustomer() throws WrongInputException {
		System.out.println("Email:");
		String email = scanner.next();
		if (!customers.containsKey(email)) {
			throw new WrongInputException("Customer does not exist");
		}
		customers.remove(email);
		
		CustomerRepository.getInstance().delete(email);
	}

	@Override
	public void printOptions() {
		System.out.println("1 - Add customer");
		System.out.println("2 - Update customer");
		System.out.println("3 - Add order");
		System.out.println("4 - Print customer information");
		System.out.println("5 - Print customer reader profile");
		System.out.println("6 - Add review");
		System.out.println("7 - Delete customer");
	}

	@Override
	public void chooseOption() {
		int option = scanner.nextInt();
		switch(option) {
		case 1:
			try {
				addCustomer();
			} catch (DuplicateIdException e1) {
				e1.printStackTrace();
			}
			AuditService.getInstance().addAction("Add customer");
			break;
		case 2:
			try {
				updateCustomer();
			} catch (WrongInputException e) {
				e.printStackTrace();
			}
			AuditService.getInstance().addAction("Update customer");
			break;
		case 3:
			try {
				addOrder();
			} catch (WrongInputException e) {
				e.printStackTrace();
			}
			AuditService.getInstance().addAction("Add order");
			break;
		case 4:
			try {
				printCustomerInformation();
			} catch (WrongInputException e) {
				e.printStackTrace();
			}
			AuditService.getInstance().addAction("Print customer information");
			break;
		case 5:
			try {
				printCustomerReaderProfile();
			} catch (WrongInputException e) {
				e.printStackTrace();
			}
			AuditService.getInstance().addAction("Print customer reader profile");
			break;
		case 6:
			try {
				addReview();
			} catch (WrongInputException e) {
				e.printStackTrace();
			}
			AuditService.getInstance().addAction("Add book review");
			break;
		case 7:
			try {
				deleteCustomer();
			} catch (WrongInputException e) {
				e.printStackTrace();
			}
			AuditService.getInstance().addAction("Delete customer");
		}
	}

	@Override
	public String getCsvPath() {
		return "csvFiles/customers.csv";
	}

	@Override
	public void addFromCsv(String[] strings) {
		Customer customer = new Customer(strings[0], strings[1], strings[2], strings[3], strings[4]);
		customers.put(customer.getEmail(), customer);
	}

	@Override
	public List<String> getStringsForCsv() {
		List<String> strings = new ArrayList<>();
		for (Customer customer : customers.values()) {
			strings.add(customer.getLastName() + "," + customer.getFirstName() + "," + customer.getAddress() + "," + customer.getEmail() + "," + customer.getPhoneNumber());
		}
		return strings;
	}

	@Override
	public void getFromDatabase() {
		List<CustomerEntity> customerEntities = CustomerRepository.getInstance().getAll();
		for (CustomerEntity entity : customerEntities) {
			Customer customer = new Customer(entity.getLastName(), entity.getFirstName(), entity.getAddress(), entity.getEmail(), entity.getPhoneNumber());
			customers.put(customer.getEmail(), customer);
		}
	}

}
