package auth.test;

import java.sql.SQLException;

import auth.AuthUtils;
import database.DataBaseUtils;

public class AuthTest {

	public static void main(String[] args) {

		DataBaseUtils.printMySQLTable("users");		
		System.out.println();
		DataBaseUtils.printMySQLTable("session");
		System.out.println();
		DataBaseUtils.printMySQLTable("friends");

		try {
			
			/* addUser */
			//AuthUtils.addUserToDataBase("Jimmy", "I@mJimm1", "jim@gmail.com");
			
			/* removeUser */
			//AuthUtils.removeUserFromDataBase(7);
			
			/* insertSession */
			//AuthUtils.insertSession(3, false);
			
			/* removeSession */
			//AuthUtils.removeSession("7dda5cfda8234677a0c937166a28a09b");
			
			/* userExists */
			//System.out.println(AuthUtils.userExists("Zeus"));
			//System.out.println(AuthUtils.userExists("ZeusI"));
			
			/* checkPassword */
			//System.out.println(AuthUtils.checkPassword("Hero", "I@AMHER000"));
			//System.out.println(AuthUtils.checkPassword("Hero", "I@MHER0"));
			
			/* getUserIdFromLogin */
			//System.out.println(AuthUtils.getUserIdFromLogin("Alex"));
			//System.out.println(AuthUtils.getUserIdFromLogin("Alex123"));
			
			/* getUserIdFromKey */
			//System.out.println(AuthUtils.getUserIdFromKey("1137b6eb950b4df7a159e508df12f0a6"));
			
			/* getUserLoginFromId */
			//System.out.println(AuthUtils.getUserLoginFromId(4);
					
			
			/* getUserKey */
			System.out.println(AuthUtils.getUserKey(3));
			
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
