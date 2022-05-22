package repository;

import java.util.ArrayList;
import java.util.List;

import entity.CustomerEntity;
import mapper.CustomerMapper;
import service.DatabaseService;

public class CustomerRepository {

	private static CustomerRepository instance = null;
	
	private CustomerRepository() {}
	
	public static CustomerRepository getInstance() {
		if (instance == null) {
			instance = new CustomerRepository();
		}
		return instance;
	}
	
	public List<CustomerEntity> getAll() {
		List<Object> objects = DatabaseService.getInstance().executeReadQuery("select * from customer", new CustomerMapper());
		List<CustomerEntity> customers = new ArrayList<>();
		for (Object object : objects) {
			if (object instanceof CustomerEntity) {
				customers.add((CustomerEntity) object);
			}
			else {
				throw new RuntimeException("Expected CustomerEntity instace");
			}
		}
		return customers;
	}
	
	public void delete(String email) {
		String query = "delete from customer where email = '" + email + "'";
		DatabaseService.getInstance().executeUpdateQuery(query);
	}
	
	public void add(CustomerEntity customerEntity) {
		String query = "insert into customer values('" + customerEntity.getEmail() + "','" + customerEntity.getLastName() + "','" + customerEntity.getFirstName()
				+ "','" + customerEntity.getAddress() + "','" + customerEntity.getPhoneNumber() + "')";
		DatabaseService.getInstance().executeUpdateQuery(query);
	}
	
	public void update(CustomerEntity customerEntity) {
		String query = "update customer set last_name = '" + customerEntity.getLastName() + "', first_name = '" + customerEntity.getFirstName() + "', address = '"
				+ customerEntity.getAddress() + "', phone_number = '" + customerEntity.getPhoneNumber() + "' where email = '" + customerEntity.getEmail() + "'";
		DatabaseService.getInstance().executeUpdateQuery(query);
	}
}
