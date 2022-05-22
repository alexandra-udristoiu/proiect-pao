package repository;

import java.util.ArrayList;
import java.util.List;

import entity.AuthorEntity;
import mapper.AuthorMapper;
import service.DatabaseService;

public class AuthorRepository {
	
	private static AuthorRepository instance = null;
	
	private AuthorRepository() {}
	
	public static AuthorRepository getInstance() {
		if (instance == null) {
			instance = new AuthorRepository();
		}
		return instance;
	}
	
	public List<AuthorEntity> getAll() {
		List<Object> objects = DatabaseService.getInstance().executeReadQuery("select * from author", new AuthorMapper());
		List<AuthorEntity> authors = new ArrayList<>();
		for (Object object : objects) {
			if (object instanceof AuthorEntity) {
				authors.add((AuthorEntity) object);
			}
			else {
				throw new RuntimeException("Expected AuthorEntity instace");
			}
		}
		return authors;
	}
	
	public void delete(int id) {
		String query = "delete from author where id = " + id;
		DatabaseService.getInstance().executeUpdateQuery(query);
	}
	
	public void add(AuthorEntity authorEntity) {
		String query = "insert into author values(" + authorEntity.getId() + ",'" + authorEntity.getLastName() + "','" + authorEntity.getFirstName() + "')";
		DatabaseService.getInstance().executeUpdateQuery(query);
	}
	
	public void update(AuthorEntity authorEntity) {
		String query = "update author set last_name = '" + authorEntity.getLastName() + "', first_name = '" + authorEntity.getFirstName() + "' where id = "
				+ authorEntity.getId();
		DatabaseService.getInstance().executeUpdateQuery(query);
	}

}
