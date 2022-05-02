package service;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {

	public static void main(String[] args) {
		CsvService.getInstance().read();
		
		List<Service> services = new ArrayList<>();
		services.add(AuthorService.getInstance());
		services.add(BookService.getInstance());
		services.add(CustomerService.getInstance());
		services.add(StationeryProductService.getInstance());
		
		Scanner scanner = new Scanner(System.in);
		System.out.println("1 - Author service");
		System.out.println("2 - Book service");
		System.out.println("3 - Customer service");
		System.out.println("4 - Stationery product service");
		while (true) {
			System.out.println("Choose service:");
			int option = scanner.nextInt();
			if (option < 1 || option > 4) {
				break;
			}
			option--;
			services.get(option).printOptions();
			services.get(option).chooseOption();
		}
		scanner.close();
		
		CsvService.getInstance().write();
	}

}
