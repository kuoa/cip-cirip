package services.tools;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.json.JSONObject;

public class ServicesUser {
	
	/* Letters, digits and _, 3 <= size <= 10 */	
	public static final Pattern VALID_USER_REGEX = 
			Pattern.compile("^[a-z0-9_]{3-10}$");
	
	/* 	At least: 1 digit, 1 letter, 1 symbol, size >= 6 */
	public static final Pattern VALID_PASS_REGEX = 
			Pattern.compile("^(?=.*\\d)(?=.*[a-zA-Z])(?!\\w*$).{6,}");			
	
	/* Classic email pattern */
	public static final Pattern VALID_EMAIL_ADDRESS_REGEX = 
		    Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);

	
	
	private static boolean userExists(String user){
		// TODO
		return false;
	}	
	
	private static String insertSession(int id, boolean root){
		// TODO
		return "OK";		
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
	
	public static JSONObject create(String user, String pass, String mail){		

		// TODO FINISH!
		
		final int EXISTING_LOGIN =  1;
		
		/* username check */
		
		if (!(validArg(user) && validUser(user))){
			return Services.serviceRefused(user +" is not a valid username.", Services.MISSING_ARG);
		}
		
		if (userExists(user)){
			return Services.serviceRefused("A user already exists with the login: " + user + ".", EXISTING_LOGIN);
		}		
		
		/* password check */
		
		if (!(validArg(pass) && validPassword(pass))){
			return Services.serviceRefused("Please provide a valid password.", Services.MISSING_ARG);
		}
		
		/* mail check */
		
		if (!(validArg(mail) && (validMail(mail)))){
			return Services.serviceRefused(mail + " is not a valid mail adress.", Services.MISSING_ARG);
		}
		
		
		/* insert in data-base */
		
		// TODO modify id!!!!
		String dbRes = insertSession(1, false);
		
		if (!dbRes.equals("OK")){
			return Services.serviceRefused("Database error. Please try again", Services.SQL_ERROR);
		}
			
		return Services.serviceAccepted();			
	}
	
	
	public static JSONObject login (String user, String pass){		

		// TODO
		
		final int MISSING_ARG =  1;
		final int INCORECT_ARG = 2;
		

		return null;
	}
	
	public static JSONObject logout (String user, String pass){

		// TODO
		
		final int MISSING_ARG =  1;
		final int INCORECT_ARG = 2;	
		
		return null;
	}
	
	
}
