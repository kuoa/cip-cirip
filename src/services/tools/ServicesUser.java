package services.tools;

import auth.AuthUtils;
import auth.RegexUtils;

import java.util.HashMap;

import org.json.JSONObject;

public class ServicesUser {	
	
	/* main functions */	
	
	public static JSONObject create(String user, String pass, String mail){		

		// TODO FINISH!
		
		final int EXISTING_LOGIN =  1;
				
		/* username check */
		
		if (!(RegexUtils.validArg(user) && RegexUtils.validUser(user))){
			return Services.serviceRefused("Invalid username.", Services.MISSING_ARG);
		}
		
		if (AuthUtils.userExists(user)){
			return Services.serviceRefused("Existing username." + user + ".", EXISTING_LOGIN);
		}		
		
		/* password check */
		
		if (!(RegexUtils.validArg(pass) && RegexUtils.validPassword(pass))){
			return Services.serviceRefused("Invalid password.", Services.MISSING_ARG);
		}
		
		/* mail check */
		
		if (!(RegexUtils.validArg(mail) && (RegexUtils.validMail(mail)))){
			return Services.serviceRefused("Invalid mail adress.", Services.MISSING_ARG);
		}		
		
		/* insert in data-base */
		
		// TODO modify id!!!! ON LOGIN
		String dbRes = AuthUtils.insertSession(1, false);
		
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
		
		if (!(RegexUtils.validArg(user) && RegexUtils.validUser(user))){
			return Services.serviceRefused("Invalid username.", Services.MISSING_ARG);
		}
		
		if (!AuthUtils.userExists(user)){
			return Services.serviceRefused("Incorect login informations.", INCORECT_LOGIN);
		}		
		
		/* password check */
		
		if (!(RegexUtils.validArg(pass) && RegexUtils.validPassword(pass))){
			return Services.serviceRefused("Incorect login informations", INCORECT_PASSWORD);
		}
		
		int id = AuthUtils.generateId();
		int key = AuthUtils.generateKey();
		
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
		
		if (!AuthUtils.validKey()){
			return Services.serviceRefused("Incorect user key.", INCORECT_KEY);
		}
		
		return Services.serviceAccepted();
	}		
}
