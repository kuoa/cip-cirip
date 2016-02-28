package database;

import java.sql.SQLException;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import com.mysql.jdbc.Connection;

public class DataBase {
	
	private DataSource dataSource;
	
	public DataBase (String jndiname) throws SQLException {
		
		try {
			dataSource = (DataSource) new InitialContext().lookup("java:comp/env/" + jndiname);
			
		} catch (NamingException e) {
			throw new SQLException(jndiname + " is missing in JNDI! : " + e.getMessage());
		}
	}
	
	public Connection getConnection() throws SQLException {
		return (Connection) dataSource.getConnection();
	}

}
