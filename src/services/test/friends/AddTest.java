package services.test.friends;

import static org.junit.Assert.assertEquals;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Test;

import services.tools.ServicesFriends;

public class AddTest {
	
	@Test
	public void testAddFriend1() throws JSONException{
					
		/* User logged out */
		
		JSONObject jo = ServicesFriends.add("1137b6eb950b4df7a159e508df12f0a7", "Zeus");
		int  error_code = jo.getInt("code");		
		String status = jo.getString("status");
		
		assertEquals(status, "error");
		assertEquals(error_code, 1);
		
		System.out.println(jo.toString());		
	}
	
	@Test
	public void testAddFriend2() throws JSONException {
		
		/* Incorect friend login */		
		
		JSONObject jo = ServicesFriends.add("1137b6eb950b4df7a159e508df12f0a6", "Zeuasds");
		int  error_code = jo.getInt("code");		
		String status = jo.getString("status");
		
		assertEquals(status, "error");
		assertEquals(error_code, 2);
		
		System.out.println(jo.toString());					
	}
	
	@Test
	public void testAddFriend3() throws JSONException {
		
		/* Already friends */		
		
		JSONObject jo = ServicesFriends.add("1137b6eb950b4df7a159e508df12f0a6", "TestUser");
		int  error_code = jo.getInt("code");		
		String status = jo.getString("status");
		
		assertEquals(status, "error");
		assertEquals(error_code, 3);						
		System.out.println(jo.toString());	
				
	}
	
}
