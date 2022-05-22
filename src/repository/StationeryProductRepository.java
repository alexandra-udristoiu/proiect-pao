package repository;

import java.util.ArrayList;
import java.util.List;

import entity.StationeryProductEntity;
import mapper.StationeryProductMapper;
import service.DatabaseService;

public class StationeryProductRepository {

	private static StationeryProductRepository instance = null;
	
	private StationeryProductRepository() {}
	
	public static StationeryProductRepository getInstance() {
		if (instance == null) {
			instance = new StationeryProductRepository();
		}
		return instance;
	}
	
	public List<StationeryProductEntity> getAll() {
		List<Object> objects = DatabaseService.getInstance().executeReadQuery("select * from stationery_product", new StationeryProductMapper());
		List<StationeryProductEntity> stationeryProducts = new ArrayList<>();
		for (Object object : objects) {
			if (object instanceof StationeryProductEntity) {
				stationeryProducts.add((StationeryProductEntity) object);
			}
			else {
				throw new RuntimeException("Expected StationeryProductEntity instace");
			}
		}
		return stationeryProducts;
	}
	
	public void delete(int id) {
		String query = "delete from stationery_product where id = " + id;
		DatabaseService.getInstance().executeUpdateQuery(query);
	}
	
	public void add(StationeryProductEntity stationeryProduct) {
		String query = "insert into stationery_product values(" + stationeryProduct.getId() + ",'" + stationeryProduct.getName() + "',"
				+ Float.toString(stationeryProduct.getPrice()) + "," + stationeryProduct.getProductsInStock() + "," + 
				stationeryProduct.getNumberOfItems() + ")";
		DatabaseService.getInstance().executeUpdateQuery(query);
	}
	
	public void update(StationeryProductEntity stationeryProduct) {
		String query = "update stationery_product set name = '" + stationeryProduct.getName() + "', price = " + Float.toString(stationeryProduct.getPrice())
				+ ", product_stock = " + stationeryProduct.getProductsInStock() + ", items = " + stationeryProduct.getNumberOfItems() + " where id = " + stationeryProduct.getId();
		DatabaseService.getInstance().executeUpdateQuery(query);
	}
	
	public void updateStock(int id, int productsInStock) {
		String query = "update stationery_product set product_stock = " + Integer.toString(productsInStock) + " where id = " + id;
		DatabaseService.getInstance().executeUpdateQuery(query);
	}
	
}
