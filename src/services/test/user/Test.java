package services.test.user;

import auth.AuthUtils;
import database.DB;

public class Test {
	
	public static void main (String [] args){

		DB.printMySQLTable("users");		
		//AuthUtils.addUserToDataBase("GOD", "ZEUS", "THUNDER@THUNDERCORP.COM");
		//DB.printMySQLTable("users");
	}
}
