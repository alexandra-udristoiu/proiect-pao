package service;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CsvService {

	private static CsvService instance = null;
	
	private CsvService() {}
	
	public static CsvService getInstance() {
		if (instance == null) {
			instance = new CsvService();
		}
		return instance;
	}
	
	private List<Service> getServicesList() {
		List<Service> services = new ArrayList<>();
		services.add(AuthorService.getInstance());
		services.add(BookService.getInstance());
		services.add(CustomerService.getInstance());
		services.add(StationeryProductService.getInstance());
		return services;
	}
	
	public void write() {
		for (Service service : getServicesList()) {
			writeFile(service);
		}
	}
	
	private void writeFile(Service service) {
		BufferedWriter writer;
		try {
			writer = new BufferedWriter(new FileWriter(service.getCsvPath()));
		} catch (IOException e) {
			e.printStackTrace();
			return;
		}
		List<String> lines = service.getStringsForCsv();
		for (String line : lines) {
			try {
				writer.write(line + "\n");
				writer.flush();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public void read() {
		for (Service service : getServicesList()) {
			readFile(service);
		}
	}

	private void readFile(Service service) {
		try {
			BufferedReader reader = new BufferedReader(new FileReader(service.getCsvPath()));
			String line = reader.readLine();
			while (line != null) {
				String[] strings = line.split(",");
				service.addFromCsv(strings);
				line = reader.readLine();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
