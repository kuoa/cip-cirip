package friends.test;

import java.sql.SQLException;

import database.DataBaseUtils;
import friends.FriendsUtils;

public class FriendsTest {

	public static void main(String[] args) {
		
		DataBaseUtils.printMySQLTable("users");		
		System.out.println();
		DataBaseUtils.printMySQLTable("session");
		System.out.println();
		DataBaseUtils.printMySQLTable("friends");		

		try {
			/* addFriend */
			//FriendsUtils.addFriendToDatabase(1, 6);
			
			/* removeFriends */
			//FriendsUtils.removeFriendFromDatabase(1, 6);
			
			/* alreadyFriends */
			
			//System.out.println(FriendsUtils.alreadyFriends(1, 6));
			//System.out.println(FriendsUtils.alreadyFriends(6, 1));
			System.out.println(FriendsUtils.alreadyFriends(5, 1));
			
		} catch (InstantiationException | IllegalAccessException | ClassNotFoundException | SQLException e) {
		
			e.printStackTrace();
		}
								
		DataBaseUtils.printMySQLTable("users");		
		System.out.println();
		DataBaseUtils.printMySQLTable("session");
		System.out.println();
		DataBaseUtils.printMySQLTable("friends");

	}

}
