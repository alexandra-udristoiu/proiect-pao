package mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import entity.FictionBookEntity;

public class FictionBookMapper implements RowMapper<FictionBookEntity> {

	@Override
	public FictionBookEntity mapRow(ResultSet resultSet) throws SQLException {
		String isbn = resultSet.getString("isbn");
		String title = resultSet.getString("title");
		int authorId = resultSet.getInt("author_id");
		int numberOfPages = resultSet.getInt("pages");
		float price = resultSet.getFloat("price");
		float discount = resultSet.getFloat("discount");
		int productsInStock = resultSet.getInt("product_stock");
		String genre = resultSet.getString("genre");
		return new FictionBookEntity(isbn, title, authorId, numberOfPages, price, discount, productsInStock, genre);
	}

}
