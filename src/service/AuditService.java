package service;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class AuditService {
	
	private static AuditService instance = null;
	
	private BufferedWriter writer;
	
	private AuditService() {
		try {
			this.writer = new BufferedWriter(new FileWriter("csvFiles/audit.csv"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static AuditService getInstance() {
		if (instance == null) {
			 instance = new AuditService();
		}
		return instance;
	}
	
	public void addAction(String action) {
		LocalDateTime time = LocalDateTime.now();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
		String timeString = time.format(formatter);
		try {
			writer.write(action + "," + timeString + "\n");
			writer.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
