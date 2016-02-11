package services.tools;

import java.sql.ResultSet;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.json.JSONObject;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Statement;

import database.DB;

public class ServicesUser {
	
	/** Letters, digits and _, 3 <= size <= 10 */	
	
	public static final Pattern VALID_USER_REGEX = 
			Pattern.compile("^[a-z0-9_]{3,10}$");
	
	/** At least: 1 digit, 1 letter, 1 symbol, size >= 6 */
	
	public static final Pattern VALID_PASS_REGEX = 
			Pattern.compile("^(?=.*\\d)(?=.*[a-zA-Z])(?!\\w*$).{6,}");			
	
	/** Classic email pattern */
	
	public static final Pattern VALID_EMAIL_ADDRESS_REGEX = 
		    Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);

	
	/* check-up boolean functions */
	
	public static boolean userExists(String user){

		String sql = "SELECT id FROM users where login = \"" + user + "\";";
		boolean found = false;
		
		try {
			  Connection connection = DB.getMySQLConnection();
			  Statement st = (Statement) connection.createStatement();
			  ResultSet rs = st.executeQuery(sql);
			  
			  if (rs.next()){
				  found = true;
			  }			  
			  st.close();
			  connection.close();			  			  
			  
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return found;
	}	

	private static boolean validArg (String arg){
		
		return (arg != null && !arg.isEmpty());
	}
	
	private static boolean validUser(String user){
		
		Matcher matcher = VALID_USER_REGEX.matcher(user);
        return matcher.find();
	}
	
	private static boolean validMail(String mail){
		
		Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(mail);
        return matcher.find();        		
	}
	
	private static boolean validPassword(String pass){

		Matcher matcher = VALID_PASS_REGEX.matcher(pass);
        return matcher.find();  
		        
	}			
	
	private static boolean validKey(){
		// TODO
		return true;
	}
	
	/* utility functions */
	
	private static String insertSession(int id, boolean root){
		// TODO generates key and inserts on id 
		return "OK";		
	}
	
	private static int generateKey(){
		// TODO
		return 123456;
	}
	
	private static int generateId(){
		// ID AUTOMATED
		return 4321;
	}
	
	/* main functions */	
	
	public static JSONObject create(String user, String pass, String mail){		

		// TODO FINISH!
		
		final int EXISTING_LOGIN =  1;
				
		/* username check */
		
		if (!(validArg(user) && validUser(user))){
			return Services.serviceRefused("Invalid username.", Services.MISSING_ARG);
		}
		
		if (userExists(user)){
			return Services.serviceRefused("Existing username." + user + ".", EXISTING_LOGIN);
		}		
		
		/* password check */
		
		if (!(validArg(pass) && validPassword(pass))){
			return Services.serviceRefused("Invalid password.", Services.MISSING_ARG);
		}
		
		/* mail check */
		
		if (!(validArg(mail) && (validMail(mail)))){
			return Services.serviceRefused("Invalid mail adress.", Services.MISSING_ARG);
		}		
		
		/* insert in data-base */
		
		// TODO modify id!!!! ON LOGIN
		String dbRes = insertSession(1, false);
		
		if (!dbRes.equals("OK")){
			return Services.serviceRefused("Database error", Services.SQL_ERROR);
		}
			
		return Services.serviceAccepted();			
	}
		
	public static JSONObject login (String user, String pass){		

		// TODO
		
		final int INCORECT_LOGIN = 1;
		final int INCORECT_PASSWORD = 2;
		
		/* username check */
		
		if (!(validArg(user) && validUser(user))){
			return Services.serviceRefused("Invalid username.", Services.MISSING_ARG);
		}
		
		if (!userExists(user)){
			return Services.serviceRefused("Incorect login informations.", INCORECT_LOGIN);
		}		
		
		/* password check */
		
		if (!(validArg(pass) && validPassword(pass))){
			return Services.serviceRefused("Incorect login informations", INCORECT_PASSWORD);
		}
		
		int id = generateId();
		int key = generateKey();
		
		/* data-base part */
		
		/* TO DO */
		
		
		/* Response */
		HashMap<String, String> args = new HashMap<>();
		
		args.put("id", Integer.toString(id));
		args.put("key", Integer.toString(key));
		args.put("username", user);
		
		return Services.serviceAccepted(args);
		
	}
	
	public static JSONObject logout (int key){
		
		final int INCORECT_KEY = 1;
		
		if (!validKey()){
			return Services.serviceRefused("Incorect user key.", INCORECT_KEY);
		}
		
		return Services.serviceAccepted();
	}
	
	
}
