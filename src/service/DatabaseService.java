package service;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.DriverManager;
import java.util.ArrayList;
import java.util.List;

import mapper.RowMapper;

public class DatabaseService {
	
	static final String JDBC_DRIVER = "org.h2.Driver";
	
    static final String DB_URL = "jdbc:h2:tcp://localhost/~/proiect-pao";
    
    static final String USER = "sa";
    
    static final String PASS = "123";
    
    private static DatabaseService instance = null;
    
    private DatabaseService() {}
    
    public static DatabaseService getInstance() {
    	if (instance == null) {
    		instance = new DatabaseService();
    	}
    	return instance;
    }

    public List<Object> executeReadQuery(String sql, RowMapper<?> rowMapper) {
        Connection conn = null;
        Statement stmt = null;
        List<Object> result = new ArrayList<>();
        try {
            Class.forName(JDBC_DRIVER);

            System.out.println("Connecting to database...");
            conn = DriverManager.getConnection(DB_URL, USER, PASS);

            System.out.println("Connected database successfully...");
            stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                result.add(rowMapper.mapRow(rs));
            }
            rs.close();
        } catch (SQLException se) {
            se.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (stmt != null) stmt.close();
            } catch (SQLException se2) {
            }
            try {
                if (conn != null) conn.close();
            } catch (SQLException se) {
                se.printStackTrace();
            }
        }

        return result;
    }

    public void executeUpdateQuery(String sql) {
        Connection conn = null;
        Statement stmt = null;
        try {
            Class.forName(JDBC_DRIVER);
            System.out.println("Connected database successfully...");
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            stmt = conn.createStatement();

            stmt.executeUpdate(sql);
        } catch (SQLException se) {
            se.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (stmt != null) stmt.close();
            } catch (SQLException se2) {
            }
            try {
                if (conn != null) conn.close();
            } catch (SQLException se) {
                se.printStackTrace();
            }
        }
    }

}
