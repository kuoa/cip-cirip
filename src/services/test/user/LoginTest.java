package services.test.user;

import static org.junit.Assert.assertEquals;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Test;

import services.tools.ServicesUser;

public class LoginTest {

	@Test
	public void testUsername() throws JSONException {
		
		JSONObject res;
		String status;
		
		String userRef []= {"cool_username", "c@0l_u", "s"};
		String userAcc [] = {"coco", "happy_go", "giraffe"};
		
		String password = "rand0mP@ass";		
		
		for (String user: userRef){

			res = ServicesUser.login(user, password);
			status = res.getString("status");
			
			System.out.println(res);
			assertEquals(status, "error");
		}	
		
		for (String user: userAcc){
			
			// user doesn't exist
			res = ServicesUser.login(user, password);
			status = res.getString("status");
			
			System.out.println(res);
			assertEquals(status, "succes");
		}			
								
	}
}
