package mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import entity.AuthorEntity;

public class AuthorMapper implements RowMapper<AuthorEntity> {

	@Override
	public AuthorEntity mapRow(ResultSet resultSet) throws SQLException {
		int id = resultSet.getInt("id");
		String lastName = resultSet.getString("last_name");
		String firstName = resultSet.getString("first_name");
		return new AuthorEntity(id, lastName, firstName);
	}

}
