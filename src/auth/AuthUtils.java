package auth;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.UUID;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;
import com.mysql.jdbc.Statement;

import database.DataBaseUtils;

public class AuthUtils {

	/** validity of session key in hours */

	private static final int SESSION_HOUR_DURATION = 2;
	
	/**
	 * Adds a new username to the data-base
	 * 
	 * @param login
	 * @param password
	 * @param email
	 * @return true / false
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 * @throws IllegalAccessException
	 * @throws InstantiationException
	 */

	public static boolean addUserToDataBase(String login, String password, String email)
			throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {

		String sql = "INSERT INTO users " + "(login, password, email)" + "VALUES ('" + login + "', '" + password
				+ "', '" + email + "')";

		Boolean isAdded = false;

		Connection connection = DataBaseUtils.getMySQLConnection();
		Statement st = (Statement) connection.createStatement();

		st.executeUpdate(sql);
		isAdded = true;

		st.close();
		connection.close();

		System.out.println("User Added To Database");
		return isAdded;
	}	
	
	/**
	 * Deletes the user from the data-base
	 * @param id
	 * @return true | false
	 * @throws SQLException 
	 * @throws ClassNotFoundException 
	 * @throws IllegalAccessException 
	 * @throws InstantiationException 
	 */
	public static void removeUserFromDataBase(int id) throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException{
		
		String sql = "DELETE FROM users WHERE `id`=" + id;

		Connection connection = DataBaseUtils.getMySQLConnection();
		Statement st = (Statement) connection.createStatement();
		
		st.executeUpdate(sql);
		
		System.out.println("User removed for id : " + id);
		
		st.close();
		connection.close();	
						
	}

	/**
	 * Inserts a new entry in the `session` table.
	 * @param id
	 * @param root
	 * @return A unique key associated with the user
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	
	public static String insertSession(int id, boolean root)
			throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {

		/* SESSION_HOUR_DURATIONS starting from now */
		Timestamp expires = new Timestamp(System.currentTimeMillis() + SESSION_HOUR_DURATION * 60 * 60 * 1000);

		String key = generateKey();

		String sql = "INSERT INTO `session` " + "(`key`, `user_id`, `expires`, `root`)" 
		+ " VALUE(?,?,?,?)";		
				
		Connection connection = DataBaseUtils.getMySQLConnection();
		PreparedStatement ps = (PreparedStatement) connection.prepareStatement(sql);
		
		ps.setString(1, key);
		ps.setInt(2, id);
		ps.setTimestamp(3, expires);
		ps.setBoolean(4, root);
		
		ps.executeUpdate();
		
		System.out.println("Session inserted for id : " + id);
		
		ps.close();
		connection.close();
		
		return key;
	}
	
	/**
	 * Removes the entry associated with id, from the `sessions` table.
	 * @param id
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */

	public static void removeSession(String key) throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
		
		String sql = "DELETE FROM session WHERE `key`='" + key +"'";

		Connection connection = DataBaseUtils.getMySQLConnection();
		Statement st = (Statement) connection.createStatement();
		
		st.executeUpdate(sql);
		
		System.out.println("Session removed for key : " + key);
		
		st.close();
		connection.close();	
	}
	
	/**
	 * Checks if the user already exists in the database.
	 * 
	 * @param user
	 * @return true / false
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 * @throws IllegalAccessException
	 * @throws InstantiationException
	 */
	public static boolean userExists(String user)
			throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {

		String sql = "SELECT id FROM users WHERE login = \"" + user + "\";";
		boolean found = false;

		Connection connection = DataBaseUtils.getMySQLConnection();
		Statement st = (Statement) connection.createStatement();
		ResultSet rs = st.executeQuery(sql);

		if (rs.next()) {
			found = true;
		}

		st.close();
		connection.close();

		return found;
	}
	
	/**
	 * Checks if the databasePassword is the same as the provided password.
	 * 
	 * @param login
	 * @param password
	 * @return true / false
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */

	public static boolean checkPassword(String login, String password)
			throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {

		String sql = "SELECT password FROM users WHERE login='" + login + "'";
		String serverPassword = "";
		Boolean isValid = false;

		Connection connection = DataBaseUtils.getMySQLConnection();
		Statement st = (Statement) connection.createStatement();
		ResultSet rs = st.executeQuery(sql);

		if (rs.next()) {

			serverPassword = rs.getString("password");

			if (password.equals(serverPassword)) {
				isValid = true;
			}
		}

		st.close();
		connection.close();

		return isValid;
	}
	
	/**
	 * Generates a unique session-key for a user.
	 * @return unique-key
	 */
	
	public static String generateKey() {

		return UUID.randomUUID().toString().replaceAll("-", "");
	}
	
	/**
	 * Returns the id associated with the login from the users table.
	 * 
	 * @param login
	 * @return id | -1 (for debbuging purposes)
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */

	public static int getUserIdFromLogin(String login)
			throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {

		int id = -1;
		String sql = "SELECT id FROM users WHERE login='" + login + "'";

		Connection connection = DataBaseUtils.getMySQLConnection();
		Statement st = (Statement) connection.createStatement();
		ResultSet rs = st.executeQuery(sql);

		if (rs.next()) {
			id = rs.getInt("id");
		}

		st.close();
		connection.close();

		return id;
	}	
	
	public static int getUserIdFromKey(String key)
			throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException{
		
		int id = -1;
		String sql = "SELECT `user_id` FROM `session` WHERE `key`='" + key + "'";

		Connection connection = DataBaseUtils.getMySQLConnection();
		Statement st = (Statement) connection.createStatement();
		ResultSet rs = st.executeQuery(sql);

		if (rs.next()) {
			id = rs.getInt("user_id");
		}

		st.close();
		connection.close();

		return id;
	}	
			

	/**
	 * Returns the session-key associated with the user.
	 * @param id
	 * @return session-key
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	
	public static String getUserKey(int id)
			throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
		
		String sql = "SELECT `key` FROM session WHERE user_id=" + id;
		String key = "";

		Connection connection = DataBaseUtils.getMySQLConnection();
		Statement st = (Statement) connection.createStatement();
		ResultSet rs = st.executeQuery(sql);

		if (rs.next()) {
			key = rs.getString("key");
		}

		st.close();
		connection.close();

		return key;
	}

}
