package services.test.user;

import java.sql.SQLException;

import org.json.JSONObject;

import auth.AuthUtils;
import database.DataBaseUtils;
import friends.FriendsUtils;
import services.servlets.user.Logout;
import services.tools.ServicesUser;

public class Test {
	
	public static void main (String [] args){
		
		System.out.println("HEY");
			
		//DataBaseUtils.printMySQLTable("users");
		
		System.out.println();
		//DataBaseUtils.printMySQLTable("session");
		
		DataBaseUtils.printMySQLTable("friends");
		
		//JSONObject jo = ServicesUser.create("Zeus", "I@MG0D", "zeus@thunder.com");
		
		//JSONObject jo = ServicesUser.login("Hero", "I@AMHER000");
						

		//JSONObject jo = ServicesUser.logout("Bade72251fa74685891a39c1a875fa8d");
		
		//JSONObject jo = ServicesUser.delete("Hero", "I@MHER0", "hero@heroes.cm");
		//System.out.println(jo);
		
		//System.out.println(jo);	
		
		try {
			//FriendsUtils.addFriendToDatabase("7dda5cfda8234677a0c937166a28a09b", "Zeus");
			
			FriendsUtils.removeFriendFromDatabase("7dda5cfda8234677a0c937166a28a09b", "Zeus");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		System.out.println();
		//DataBaseUtils.printMySQLTable("users");
		System.out.println();
		//DataBaseUtils.printMySQLTable("session");
		System.out.println();
		DataBaseUtils.printMySQLTable("friends");
	}
}
