package services.tools;

import auth.AuthUtils;
import auth.RegexUtils;

import java.sql.SQLException;
import java.util.HashMap;

import org.json.JSONObject;

public class ServicesUser {

	/**
	 * Creates a new user and ads it to the data-base.
	 * 
	 * @param user
	 * @param pass
	 * @param mail
	 * @return A serviceAcepted / serviceRefused JSONObject.
	 */
	public static JSONObject create(String login, String pass, String mail) {

		// TODO FINISH!

		final int EXISTING_LOGIN = 1;

		/* username check */

		if (!(RegexUtils.validArg(login) && RegexUtils.validUser(login))) {
			return Services.serviceRefused("Invalid username.", Services.MISSING_ARG);
		}

		/* password check */

		if (!(RegexUtils.validArg(pass) && RegexUtils.validPassword(pass))) {
			return Services.serviceRefused("Invalid password.", Services.MISSING_ARG);
		}

		/* mail check */

		if (!(RegexUtils.validArg(mail) && (RegexUtils.validMail(mail)))) {
			return Services.serviceRefused("Invalid mail adress.", Services.MISSING_ARG);
		}

		try {
			if (AuthUtils.userExists(login)) {
				return Services.serviceRefused("Existing username.", EXISTING_LOGIN);
			}

			/* insert in data-base */
			AuthUtils.addUserToDataBase(login, pass, mail);

		} catch (SQLException e) {
			return Services.serviceRefused("SQL Error\n" + e.getMessage(), Services.SQL_ERROR);

		} catch (Exception e) {
			return Services.serviceRefused("JAVA Error\n" + e.getMessage(), Services.JAVA_ERROR);
		}

		return Services.serviceAccepted();
	}

	/**
	 * Logs in a existing user, generates a session key and inserts it in the
	 * database. This key is used during the session for accessing other
	 * services.
	 * 
	 * @param user
	 * @param pass
	 * @return A serviceAcepted / serviceRefused JSONObject.
	 */

	public static JSONObject login(String login, String pass) {

		final int INCORECT_LOGIN = 1;
		final int INCORECT_PASSWORD = 2;

		final boolean root = false; /* false by default */

		final String key;
		final int id;

		/* username check */

		if (!(RegexUtils.validArg(login) && RegexUtils.validUser(login))) {
			return Services.serviceRefused("Invalid username.", Services.MISSING_ARG);
		}

		/* password check */

		if (!(RegexUtils.validArg(pass) && RegexUtils.validPassword(pass))) {
			return Services.serviceRefused("Incorect password", INCORECT_PASSWORD);
		}

		try {

			/* database part */

			if (!AuthUtils.userExists(login)) {
				return Services.serviceRefused("Incorect login informations.", INCORECT_LOGIN);
			}

			if (!AuthUtils.checkPassword(login, pass)) {
				return Services.serviceRefused("Incorect password", INCORECT_PASSWORD);
			}

			id = AuthUtils.getUserIdFromLogin(login);
			key = AuthUtils.insertSession(id, root);

		} catch (SQLException e) {
			return Services.serviceRefused("SQL Error. " + e.getMessage(), Services.SQL_ERROR);

		} catch (Exception e) {
			return Services.serviceRefused("JAVA Error. " + e.getMessage(), Services.JAVA_ERROR);
		}

		/* Response */
		HashMap<String, String> args = new HashMap<>();

		args.put("id", Integer.toString(id));
		args.put("key", key);
		args.put("username", login);

		return Services.serviceAccepted(args);

	}

	/**
	 * Logs out the user associated with the session-key
	 * 
	 * @param key
	 * @return A serviceAcepted / serviceRefused JSONObject.
	 */

	public static JSONObject logout(String key) {
		
		try {
			AuthUtils.removeSession(key);
			
		} catch (SQLException e) {
			return Services.serviceRefused("SQL Error. " + e.getMessage(), Services.SQL_ERROR);

		} catch (Exception e) {
			return Services.serviceRefused("JAVA Error. " + e.getMessage(), Services.JAVA_ERROR);
		}
		return Services.serviceAccepted();
	}
	
	/**
	 * Delets the user from the data-base
	 * @param login
	 * @param pass
	 * @param mail
	 * @return A serviceAcepted / serviceRefused JSONObject.
	 */
	
	public static JSONObject delete(String login, String pass, String mail){
		
		final int INCORECT_LOGIN = 1;
		final int INCORECT_PASSWORD = 2;		

		final String key;
		final int id;

		/* username check */

		if (!(RegexUtils.validArg(login) && RegexUtils.validUser(login))) {
			return Services.serviceRefused("Invalid username.", Services.MISSING_ARG);
		}

		/* password check */

		if (!(RegexUtils.validArg(pass) && RegexUtils.validPassword(pass))) {
			return Services.serviceRefused("Incorect password", INCORECT_PASSWORD);
		}

		try {

			/* database part */

			if (!AuthUtils.userExists(login)) {
				return Services.serviceRefused("Incorect login informations.", INCORECT_LOGIN);
			}

			if (!AuthUtils.checkPassword(login, pass)) {
				return Services.serviceRefused("Incorect password", INCORECT_PASSWORD);
			}

			id = AuthUtils.getUserIdFromLogin(login);
			key = AuthUtils.getUserKey(id);

			/* TODO remove all/from friends */
			
			
			/* remove-session */
									
			AuthUtils.removeSession(key);
			
			/* delete user */
			
			AuthUtils.removeUserFromDataBase(id);
			

		} catch (SQLException e) {
			return Services.serviceRefused("SQL Error. " + e.getMessage(), Services.SQL_ERROR);

		} catch (Exception e) {
			return Services.serviceRefused("JAVA Error. " + e.getMessage(), Services.JAVA_ERROR);
		}		

		return Services.serviceAccepted();
		
	}
}

