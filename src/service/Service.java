package service;

import java.util.List;

public interface Service {

	void printOptions();
	
	void chooseOption();
	
	String getCsvPath();
	
	void addFromCsv(String[] strings);
	
	List<String> getStringsForCsv();
}
