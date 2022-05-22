package repository;

import java.util.ArrayList;
import java.util.List;

import entity.TextBookEntity;
import mapper.TextBookMapper;
import service.DatabaseService;

public class TextBookRepository implements BookRepository {
	
	private static TextBookRepository instance = null;
	
	private TextBookRepository() {}
	
	public static TextBookRepository getInstance() {
		if (instance == null) {
			instance = new TextBookRepository();
		}
		return instance;
	}
	
	public List<TextBookEntity> getAll() {
		List<Object> objects = DatabaseService.getInstance().executeReadQuery("select * from text_book", new TextBookMapper());
		List<TextBookEntity> books = new ArrayList<>();
		for (Object object : objects) {
			if (object instanceof TextBookEntity) {
				books.add((TextBookEntity) object);
			}
			else {
				throw new RuntimeException("Expected TextBookEntity instace");
			}
		}
		return books;
	}
	
	@Override
	public void delete(String isbn) {
		String query = "delete from text_book where isbn = '" + isbn + "'";
		DatabaseService.getInstance().executeUpdateQuery(query);
	}
	
	public void add(TextBookEntity book) {
		String query = "insert into text_book values('" + book.getIsbn() + "','" + book.getTitle() + "'," + book.getAuthorId() + ","
				+ book.getNumberOfPages() + "," + book.getPrice() + "," + Float.toString(book.getDiscount()) + ","
				+ book.getProductsInStock() + ",'" + book.getSubject() + "'," + book.getGrade() + ")";
		DatabaseService.getInstance().executeUpdateQuery(query);
	}
	
	@Override
	public void updateDiscount(String isbn, float discount) {
		String query = "update text_book set discount = " + Float.toString(discount) + " where isbn = '" + isbn + "'";
		DatabaseService.getInstance().executeUpdateQuery(query);
	}
	
	@Override
	public void updateStock(String isbn, int productsInStock) {
		String query = "update text_book set product_stock = " + productsInStock + " where isbn = '" + isbn + "'";
		DatabaseService.getInstance().executeUpdateQuery(query);
	}

}
