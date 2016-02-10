package services.test.user;


import static org.junit.Assert.assertEquals;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Test;

import services.tools.ServicesUser;

public class CreateTest {
		
	@Test
	public void testUsername() throws JSONException {
		
		JSONObject res;
		String status;
		
		String userRef []= {"cool_username", "c@0l_u", "s"};
		String userAcc [] = {"coco", "happy_go", "giraffe"};
		
		String password = "rand0mP@ass";
		String mail = "cool@cooler.com";				
		
		for (String user: userRef){

			res = ServicesUser.create(user, password, mail);
			status = res.getString("status");
			
			System.out.println(res);
			assertEquals(status, "error");
		}	
		
		for (String user: userAcc){

			res = ServicesUser.create(user, password, mail);
			status = res.getString("status");
			
			System.out.println(res);
			assertEquals(status, "succes");
		}			
								
	}
	
	@Test
	public void testPass() throws JSONException {
		
		JSONObject res;
		String status;
		
		String passRef []= {"12345556", "Hello_HELLO", "@@@@"};
		String passAcc [] = {"P@ssw0rd", "nIC3P$sword"};
		
		String mail = "hey@hello.com";
		String user = "hello";
		
		for (String password: passRef){

			res = ServicesUser.create(user, password, mail);
			status = res.getString("status");
			
			System.out.println(res);
			assertEquals(status, "error");
		}	
		
		for (String password: passAcc){

			res = ServicesUser.create(user, password, mail);
			status = res.getString("status");
			
			System.out.println(res);
			assertEquals(status, "succes");
		}			
		
		System.out.println();
	}		
	
	@Test
	public void testMail() throws JSONException {
		
		JSONObject res;
		String status;
		
		String mailRef []= {"mail.com","fake-mail@com"};
		String mailAcc [] = {"good.mail_hey@gmail.com", "hey@hello.com"};
		
		String password = "rand0mP@ass";
		String user = "hello";
		
		for (String mail: mailRef){

			res = ServicesUser.create(user, password, mail);
			status = res.getString("status");
			
			System.out.println(res);
			assertEquals(status, "error");
		}	
		
		for (String mail: mailAcc){

			res = ServicesUser.create(user, password, mail);
			status = res.getString("status");
			
			System.out.println(res);
			assertEquals(status, "succes");
		}			
		
		System.out.println();					
	}		
}
