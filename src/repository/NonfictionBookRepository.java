package repository;

import java.util.ArrayList;
import java.util.List;

import entity.NonfictionBookEntity;
import mapper.NonfictionBookMapper;
import service.DatabaseService;

public class NonfictionBookRepository implements BookRepository {

	private static NonfictionBookRepository instance = null;
	
	private NonfictionBookRepository() {}
	
	public static NonfictionBookRepository getInstance() {
		if (instance == null) {
			instance = new NonfictionBookRepository();
		}
		return instance;
	}
	
	public List<NonfictionBookEntity> getAll() {
		List<Object> objects = DatabaseService.getInstance().executeReadQuery("select * from nonfiction_book", new NonfictionBookMapper());
		List<NonfictionBookEntity> books = new ArrayList<>();
		for (Object object : objects) {
			if (object instanceof NonfictionBookEntity) {
				books.add((NonfictionBookEntity) object);
			}
			else {
				throw new RuntimeException("Expected NonfictionBookEntity instace");
			}
		}
		return books;
	}
	
	@Override
	public void delete(String isbn) {
		String query = "delete from nonfiction_book where isbn = '" + isbn + "'";
		DatabaseService.getInstance().executeUpdateQuery(query);
	}
	
	public void add(NonfictionBookEntity book) {
		String query = "insert into nonfiction_book values('" + book.getIsbn() + "','" + book.getTitle() + "'," + book.getAuthorId() + ","
				+ book.getNumberOfPages() + "," + Float.toString(book.getPrice()) + "," + Float.toString(book.getDiscount()) + ","
				+ book.getProductsInStock() + ",'" + book.getDomain() + "')";
		DatabaseService.getInstance().executeUpdateQuery(query);
	}
	
	@Override
	public void updateDiscount(String isbn, float discount) {
		String query = "update nonfiction_book set discount = " + Float.toString(discount) + " where isbn = '" + isbn + "'";
		DatabaseService.getInstance().executeUpdateQuery(query);
	}
	
	@Override
	public void updateStock(String isbn, int productsInStock) {
		String query = "update nonfiction_book set product_stock = " + productsInStock + " where isbn = '" + isbn + "'";
		DatabaseService.getInstance().executeUpdateQuery(query);
	}
}
