package repository;

import java.util.ArrayList;
import java.util.List;

import entity.FictionBookEntity;
import mapper.FictionBookMapper;
import service.DatabaseService;

public class FictionBookRepository implements BookRepository {

	private static FictionBookRepository instance = null;
	
	private FictionBookRepository() {}
	
	public static FictionBookRepository getInstance() {
		if (instance == null) {
			instance = new FictionBookRepository();
		}
		return instance;
	}
	
	public List<FictionBookEntity> getAll() {
		List<Object> objects = DatabaseService.getInstance().executeReadQuery("select * from fiction_book", new FictionBookMapper());
		List<FictionBookEntity> books = new ArrayList<>();
		for (Object object : objects) {
			if (object instanceof FictionBookEntity) {
				books.add((FictionBookEntity) object);
			}
			else {
				throw new RuntimeException("Expected FictionBookEntity instace");
			}
		}
		return books;
	}
	
	@Override
	public void delete(String isbn) {
		String query = "delete from fiction_book where isbn = '" + isbn + "'";
		DatabaseService.getInstance().executeUpdateQuery(query);
	}
	
	public void add(FictionBookEntity book) {
		String query = "insert into fiction_book values('" + book.getIsbn() + "','" + book.getTitle() + "'," + book.getAuthorId() + ","
				+ book.getNumberOfPages() + "," + Float.toString(book.getPrice()) + "," + Float.toString(book.getDiscount()) + ","
				+ book.getProductsInStock() + ",'" + book.getGenre() + "')";
		DatabaseService.getInstance().executeUpdateQuery(query);
	}
	
	@Override
	public void updateDiscount(String isbn, float discount) {
		String query = "update fiction_book set discount = " + Float.toString(discount) + " where isbn = '" + isbn + "'";
		DatabaseService.getInstance().executeUpdateQuery(query);
	}
	
	@Override
	public void updateStock(String isbn, int productsInStock) {
		String query = "update fiction_book set product_stock = " + productsInStock + " where isbn = '" + isbn + "'";
		DatabaseService.getInstance().executeUpdateQuery(query);
	}
}
