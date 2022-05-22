package mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import entity.TextBookEntity;

public class TextBookMapper implements RowMapper<TextBookEntity> {

	@Override
	public TextBookEntity mapRow(ResultSet resultSet) throws SQLException {
		String isbn = resultSet.getString("isbn");
		String title = resultSet.getString("title");
		int authorId = resultSet.getInt("author_id");
		int numberOfPages = resultSet.getInt("pages");
		float price = resultSet.getFloat("price");
		float discount = resultSet.getFloat("discount");
		int productsInStock = resultSet.getInt("product_stock");
		String subject = resultSet.getString("subject");
		int grade = resultSet.getInt("grade");
		return new TextBookEntity(isbn, title, authorId, numberOfPages, price, discount, productsInStock, subject, grade);
	}

}
