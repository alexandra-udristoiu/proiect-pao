package mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import entity.NonfictionBookEntity;

public class NonfictionBookMapper implements RowMapper<NonfictionBookEntity> {

	@Override
	public NonfictionBookEntity mapRow(ResultSet resultSet) throws SQLException {
		String isbn = resultSet.getString("isbn");
		String title = resultSet.getString("title");
		int authorId = resultSet.getInt("author_id");
		int numberOfPages = resultSet.getInt("pages");
		float price = resultSet.getFloat("price");
		float discount = resultSet.getFloat("discount");
		int productsInStock = resultSet.getInt("product_stock");
		String domain = resultSet.getString("domain");
		return new NonfictionBookEntity(isbn, title, authorId, numberOfPages, price, discount, productsInStock, domain);
	}
	
	

}
