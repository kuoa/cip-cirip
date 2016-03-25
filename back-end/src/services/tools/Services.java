package services.tools;

import java.util.HashMap;

import javax.servlet.http.HttpServletResponse;

import org.json.JSONException;
import org.json.JSONObject;

public class Services {

	
	public final static int MISSING_ARG =  -1;	
	public final static int JSON_ERROR = 100;
	public final static int SQL_ERROR = 1000;
	public final static int JAVA_ERROR = 10000;	
	
	
	/** @return a JSONObject containing a short @param message and a @param errorCode */
	
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
	
	/** @return a JSONObject containing "status" "succes" */
	
	public static JSONObject serviceAccepted(){
		
	JSONObject jo = new JSONObject();
		
		try {
			jo.put("status", "succes");		
			
		} catch (JSONException e) {		
			e.printStackTrace();
		}
				
		return jo;
	}	
	
	/** @return a JSONObject containing "status" "succes" and aditional information exctracted from @param map */
	
	public static JSONObject serviceAccepted(HashMap<String, String> map){
		
		JSONObject jo = new JSONObject(map);
		
		try {
			jo.put("status", "succes");		
			
		} catch (JSONException e) {		
			e.printStackTrace();
		}
				
		return jo;			
	}
	
	public static void addHeader(HttpServletResponse res){
		res.addHeader("Access-Control-Allow-Origin", "*");
	}
}
