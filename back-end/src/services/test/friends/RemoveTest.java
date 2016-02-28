package services.test.friends;

import static org.junit.Assert.assertEquals;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Test;

import services.tools.ServicesFriends;

public class RemoveTest {
	
	@Test
	public void testRemoveFriend1() throws JSONException{
					
		/* User logged out */
		
		JSONObject jo = ServicesFriends.remove("1137b6eb950b4df7a159e508df12f0a7", "Zeus");
		int  error_code = jo.getInt("code");		
		String status = jo.getString("status");
		
		assertEquals(status, "error");
		assertEquals(error_code, 1);
		
		System.out.println(jo.toString());		
	}
	
	@Test
	public void testRemoveFriend2() throws JSONException {
		
		/* Incorect friend login */		
		
		JSONObject jo = ServicesFriends.remove("1137b6eb950b4df7a159e508df12f0a6", "Zeuasds");
		int  error_code = jo.getInt("code");		
		String status = jo.getString("status");
		
		assertEquals(status, "error");
		assertEquals(error_code, 2);
		
		System.out.println(jo.toString());					
	}
	
	@Test
	public void testAddFriend3() throws JSONException {
		
		/* Not Friends */		
		
		JSONObject jo = ServicesFriends.remove("1137b6eb950b4df7a159e508df12f0a6", "Zeus");
		int  error_code = jo.getInt("code");		
		String status = jo.getString("status");
		
		assertEquals(status, "error");
		assertEquals(error_code, 3);						
		System.out.println(jo.toString());	
				
	}
	
}

