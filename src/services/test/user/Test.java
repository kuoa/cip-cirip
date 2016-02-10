package services.test.user;

import org.json.JSONObject;

import services.tools.Services;

public class Test {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		JSONObject jo = Services.serviceRefused ("msg", 101);
		
		System.out.println(jo);
		
		jo = Services.serviceAccepted();
		
		System.out.println(jo);
	}

}
