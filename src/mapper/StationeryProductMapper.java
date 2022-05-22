package mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import entity.StationeryProductEntity;

public class StationeryProductMapper implements RowMapper<StationeryProductEntity> {

	@Override
	public StationeryProductEntity mapRow(ResultSet resultSet) throws SQLException {
		int id = resultSet.getInt("id");
		String name = resultSet.getString("name");
		float price = resultSet.getFloat("price");
		int productsInStock = resultSet.getInt("product_stock");
		int numberOfItems = resultSet.getInt("items");
		return new StationeryProductEntity(id, name, price, productsInStock, numberOfItems);
	}

}
