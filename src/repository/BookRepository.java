package repository;

public interface BookRepository {

	void delete(String isbn);
	
	void updateDiscount(String isbn, float discount);
	
	void updateStock(String isbn, int productsInStock);
}
