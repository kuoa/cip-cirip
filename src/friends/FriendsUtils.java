package friends;

import java.sql.SQLException;
import java.sql.Timestamp;

import auth.AuthUtils;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;
import com.mysql.jdbc.Statement;

import database.DataBaseUtils;

public class FriendsUtils {

	public static void addFriendToDatabase(String userKey, String friendLogin)
			throws InstantiationException, IllegalAccessException,
			ClassNotFoundException, SQLException {

		Timestamp date = new Timestamp(System.currentTimeMillis());

		int fromId = AuthUtils.getUserIdFromKey(userKey);
		int toId = AuthUtils.getUserIdFromLogin(friendLogin);

		String sql = "INSERT INTO `friends` "
				+ "(`from`, `to`, `date`)" + " VALUE(?,?,?)";
		
		Connection connection = DataBaseUtils.getMySQLConnection();
		PreparedStatement ps = (PreparedStatement) connection.prepareStatement(sql);

		ps.setInt(1, fromId);
		ps.setInt(2, toId);
		ps.setTimestamp(3, date);

		ps.executeUpdate();

		ps.close();
		connection.close();

		System.out.println("Friend added to : " + toId + " from : " + fromId);

	}

	public static void removeFriendFromDatabase(String userKey,
			String friendLogin) throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {		

		int fromId = AuthUtils.getUserIdFromKey(userKey);
		int toId = AuthUtils.getUserIdFromLogin(friendLogin);		
		
		String sql = "DELETE FROM `friends` WHERE `from`=" + fromId 
				+ " AND `to`=" + toId;

		Connection connection = DataBaseUtils.getMySQLConnection();
		Statement st = (Statement) connection.createStatement();
		
		st.executeUpdate(sql);
		
		System.out.println("Friend removed to : " + toId + " from : " + fromId);
		
		st.close();
		connection.close();	

	}

}
