package swim.entities;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class SQLBasedTest {
protected static Connection jdbcConnection;
	
	private static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
	
	private static final String DB_URL = "jdbc:mysql://localhost:3306/swim";
	
	private static final String USER = "siuser";
	
	private static final String PASS = "sipass";
	
	static{
		try {
			jdbcConnection=createConnection();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			throw new RuntimeException(e);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			throw new RuntimeException(e);
		}
	}

	private static Connection createConnection() throws ClassNotFoundException, SQLException {
		
		Class.forName(JDBC_DRIVER);
		return DriverManager.getConnection(DB_URL,USER,PASS);
	}
	
	public static void renewConnection() throws ClassNotFoundException, SQLException {
		try {
			jdbcConnection.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		jdbcConnection = createConnection();
	}
	
	public int getLastInsertedId(Statement statement) throws SQLException {
		ResultSet rs = statement.getGeneratedKeys();
		rs.next();
		return rs.getInt(1);
	}
	
}
