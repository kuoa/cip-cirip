package services.test.user;

import org.json.JSONObject;

import database.DB;
import services.tools.ServicesUser;

public class Test {
	
	public static void main (String [] args){

		DB.printMySQLTable("users");	
		System.out.println();
		DB.printMySQLTable("session");
		
		//JSONObject jo = ServicesUser.create("Hero", "I@AMHER000", "hero@heroes.com");
		
		//JSONObject jo = ServicesUser.login("Hero", "I@MHER0");

		//JSONObject jo = ServicesUser.logout("Bade72251fa74685891a39c1a875fa8d");
		
		//System.out.println(jo);	
		
		System.out.println();
		DB.printMySQLTable("users");
		System.out.println();
		DB.printMySQLTable("session");
									
	}
}
