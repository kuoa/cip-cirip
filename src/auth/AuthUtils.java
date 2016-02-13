package auth;

import java.sql.ResultSet;
import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Statement;

import database.DB;

public class AuthUtils {

	public static boolean userExists(String user) {

		String sql = "SELECT id FROM users WHERE login = \"" + user + "\";";
		boolean found = false;

		try {
			Connection connection = DB.getMySQLConnection();
			Statement st = (Statement) connection.createStatement();
			ResultSet rs = st.executeQuery(sql);

			if (rs.next()) {
				found = true;
			}
			st.close();
			connection.close();

		} catch (Exception e) {
			e.printStackTrace();
		}

		return found;
	}

	public static boolean addUserToDataBase(String login, String password, String email) {

		String sql = "INSERT INTO users " + "(login, password, email)" + 
					 "VALUES ('" + login + "', '" + password + "', '" + email + "')";
		
		System.out.println (sql);		

		try {
			Connection connection = DB.getMySQLConnection();
			Statement st = (Statement) connection.createStatement();
			
			st.executeUpdate(sql);

			st.close();
			connection.close();
			
			System.out.println ("User Added To Database");
			
			return true;
			
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		
	}

	/* utility functions */

	public static boolean validKey() {
		// TODO
		return true;
	}

	public static String insertSession(int id, boolean root) {
		// TODO generates key and inserts on id
		return "OK";
	}

	public static int generateKey() {
		// TODO
		return 123456;
	}

	public static int generateId() {
		// ID AUTOMATED
		return 4321;
	}
}
