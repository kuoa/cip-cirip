package services.tools;

import org.json.JSONException;
import org.json.JSONObject;

public class Services {

	
	public final static int MISSING_ARG =  -1;	
	public static int JSON_ERROR = 100;
	public final static int SQL_ERROR = 1000;
	public final static int JAVA_ERROR = 10000;	
	
	public static JSONObject serviceRefused(String message, int errorCode){
		
		JSONObject jo = new JSONObject();
		
		try {			
			jo.put("code", new Integer (errorCode));
			jo.put("status", "error");
			jo.put("message", message);						
						
		} catch (JSONException e) {		
			e.printStackTrace();
		}
				
		return jo;
	}
	
	public static JSONObject serviceAccepted(){
		
	JSONObject jo = new JSONObject();
		
		try {
			jo.put("status", "succes");		
			
		} catch (JSONException e) {		
			e.printStackTrace();
		}
				
		return jo;
	}	
	
	public static JSONObject serviceAccepted(String msg){
		
		JSONObject jo = new JSONObject();
			
			try {
				jo.put("status", "succes");
				jo.put("message", msg);
				
			} catch (JSONException e) {		
				e.printStackTrace();
			}
					
			return jo;
		}
}
