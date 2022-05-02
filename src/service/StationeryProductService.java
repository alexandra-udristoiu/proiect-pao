package service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

import exception.DuplicateIdException;
import exception.WrongInputException;
import products.StationeryProduct;

public class StationeryProductService implements Service {
	
	private static StationeryProductService instance = null;
	
	private HashMap<Integer, StationeryProduct> stationeryProducts;
	
	private Scanner scanner;
	
	private StationeryProductService() {
		this.stationeryProducts = new HashMap<>();
		this.scanner = new Scanner(System.in);
	}
	
	public static StationeryProductService getInstance() {
		if (instance == null) {
			instance = new StationeryProductService();
		}
		return instance;
	}
	
	public StationeryProduct getStationeryProductById(int id) {
		return stationeryProducts.get(id);
	}
	
	public void addStationeryProduct() throws DuplicateIdException {
		System.out.println("ID:");
		int id = scanner.nextInt();
		if (stationeryProducts.containsKey(id)) {
			throw new DuplicateIdException();
		}
		System.out.println("Name:");
		String name = scanner.next();
		System.out.println("Price:");
		float price = scanner.nextFloat();
		System.out.println("Number of items:");
		int numberOfItems = scanner.nextInt();
		StationeryProduct stationeryProduct = new StationeryProduct(name, price, 0, numberOfItems, id);
		stationeryProducts.put(id, stationeryProduct);
	}
	
	public void deleteStationeryProduct() throws WrongInputException {
		System.out.println("Stationery product id:");
		int id = scanner.nextInt();
		if (!stationeryProducts.containsKey(id)) {
			throw new WrongInputException("Stationery product does not exist");
		}
		stationeryProducts.remove(id);
	}
	
	public void printStationeryProducts() {
		for (StationeryProduct sp : stationeryProducts.values()) {
			sp.printInformarion();
		}
	}
	
	public void addInStock() throws WrongInputException {
		System.out.println("ID:");
		int id = scanner.nextInt();
		if (!stationeryProducts.containsKey(id)) {
			throw new WrongInputException("Stationery product does not exist");
		}
		System.out.println("Number of products added:");
		int number = scanner.nextInt();
		stationeryProducts.get(id).addInStock(number);
	}
	
	public void editProduct() throws WrongInputException {
		System.out.println("ID:");
		int id = scanner.nextInt();
		StationeryProduct stationery = stationeryProducts.get(id);
		if (stationery == null) {
			throw new WrongInputException("Stationery product does not exist");
		}
		System.out.println("Name:");
		String name = scanner.next();
		System.out.println("Price:");
		float price = scanner.nextFloat();
		System.out.println("Products in stock:");
		int productsInStock = scanner.nextInt();
		System.out.println("Number of items:");
		int numberOfItems = scanner.nextInt();
		stationery.setName(name);
		stationery.setPrice(price);
		stationery.setProductsInStock(productsInStock);
		stationery.setNumberOfItems(numberOfItems);
	}

	@Override
	public void printOptions() {
		System.out.println("1 - Add stationery product");
		System.out.println("2 - Delete stationery product");
		System.out.println("3 - Print stationery products");
		System.out.println("4 - Add product in stock");
		System.out.println("5 - Edit product");
	}

	@Override
	public void chooseOption() {
		int option = scanner.nextInt();
		switch(option) {
		case 1:
			try {
				addStationeryProduct();
			} catch (DuplicateIdException e1) {
				e1.printStackTrace();
			}
			AuditService.getInstance().addAction("Add stationery product");
			break;
		case 2:
			try {
				deleteStationeryProduct();
			} catch (WrongInputException e1) {
				e1.printStackTrace();
			}
			AuditService.getInstance().addAction("Delete stationery product");
			break;
		case 3:
			printStationeryProducts();
			AuditService.getInstance().addAction("Print stationery products");
			break;
		case 4:
			try {
				addInStock();
			} catch (WrongInputException e) {
				e.printStackTrace();
			}
			AuditService.getInstance().addAction("Add stationery product in stock");
			break;
		case 5:
			try {
				editProduct();
			} catch (WrongInputException e) {
				e.printStackTrace();
			}
			AuditService.getInstance().addAction("Edit stationery product");
		}
	}

	@Override
	public String getCsvPath() {
		return "csvFiles/stationeryProducts.csv";
	}

	@Override
	public void addFromCsv(String[] strings) {
		StationeryProduct stationery = new StationeryProduct(strings[0], Float.parseFloat(strings[1]), Integer.parseInt(strings[2]), 
				Integer.parseInt(strings[3]), Integer.parseInt(strings[4]));
		stationeryProducts.put(stationery.getId(), stationery);
		
	}

	@Override
	public List<String> getStringsForCsv() {
		List<String> strings = new ArrayList<>();
		for (StationeryProduct stationery : stationeryProducts.values()) {
			String current = stationery.getName() + "," + Float.toString(stationery.getPrice()) + "," + Integer.toString(stationery.getProductsInStock())
					+ "," + Integer.toString(stationery.getNumberOfItems()) + "," + Integer.toString(stationery.getId());
			strings.add(current);
		}
		return strings;
	}

}
