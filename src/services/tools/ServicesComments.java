package services.tools;

import java.sql.SQLException;

import org.json.JSONObject;

import auth.AuthUtils;
import comments.CommentsUtils;

public class ServicesComments {

	/**
	 * Add a new comment to the database.
	 * @param key session-key
	 * @param comment comment
	 * @return A serviceAcepted / serviceRefused JSONObject.
	 */
	
	public static JSONObject add (String key, String comment){
		
		final int USER_LOGGED_OUT = 1;		

		try {

			/* check if the key is valid */
			int userId = AuthUtils.getUserIdFromKey(key);			

			if (userId < 0) {
				return Services.serviceRefused("Please log in order to post a comment.", USER_LOGGED_OUT);
			}			
			
			/* check if the login is valid */
			String userLogin = AuthUtils.getUserLoginFromId(userId);
			
			if (userLogin == null){
				return Services.serviceRefused("Incorect userId / userLogin", USER_LOGGED_OUT);
			}
			
			CommentsUtils.addComment(userId, userLogin, comment);

		} catch (SQLException e) {
			return Services.serviceRefused("SQL Error\n" + e.getMessage(), Services.SQL_ERROR);

		} catch (Exception e) {
			return Services.serviceRefused("Java Error\n" + e.getMessage(), Services.JAVA_ERROR);
		}

		return Services.serviceAccepted();
						
	}
}
