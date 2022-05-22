package mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import entity.CustomerEntity;

public class CustomerMapper implements RowMapper<CustomerEntity> {

	@Override
	public CustomerEntity mapRow(ResultSet resultSet) throws SQLException {
		String email = resultSet.getString("email");
		String lastName = resultSet.getString("last_name");
		String firstName = resultSet.getString("first_name");
		String address = resultSet.getString("address");
		String phoneNumber = resultSet.getString("phone_number");
		return new CustomerEntity(email, lastName, firstName, address, phoneNumber);
	}

}
