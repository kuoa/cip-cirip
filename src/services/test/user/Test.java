package services.test.user;

import java.sql.SQLException;

import org.json.JSONObject;

import auth.AuthUtils;
import database.DB;
import services.tools.ServicesUser;

public class Test {
	
	public static void main (String [] args){

		DB.printMySQLTable("users");	
		System.out.println();
		DB.printMySQLTable("session");
		
		JSONObject jo = ServicesUser.create("Zeus", "I@MG0D", "zeus@thunder.com");
		
		//JSONObject jo = ServicesUser.login("Hero", "I@MHER0");

		//JSONObject jo = ServicesUser.logout("Bade72251fa74685891a39c1a875fa8d");
		
		//JSONObject jo = ServicesUser.delete("Hero", "I@MHER0", "hero@heroes.cm");
		System.out.println(jo);
		
		//System.out.println(jo);	
		
		System.out.println();
		DB.printMySQLTable("users");
		System.out.println();
		DB.printMySQLTable("session");
									
	}
}
