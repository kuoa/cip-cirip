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
	
	/**
	 * Remove a comment by id, from the database.
	 * @param key	session key
	 * @param commentId	commentId
	 * @return A serviceAcepted / serviceRefused JSONObject.
	 */
	
	public static JSONObject remove (String key, String commentId){
		
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
			
			CommentsUtils.removeComment(commentId);

		} catch (SQLException e) {
			return Services.serviceRefused("SQL Error\n" + e.getMessage(), Services.SQL_ERROR);

		} catch (Exception e) {
			return Services.serviceRefused("Java Error\n" + e.getMessage(), Services.JAVA_ERROR);
		}

		return Services.serviceAccepted();
						
	}
	
	/**
	 * Returns all the comments of a user as a {@link JSONObject}, sorted from recent to last recent.
	 * @param key session-key
	 * @param userLogin userLogin
	 * @return all the comments of a user as a {@link JSONObject}, sorted from recent to last recent.
	 */
	
	public static JSONObject getForUser (String key, String userLogin){
		
		final int USER_LOGGED_OUT = 1;	
		final int INCORECT_LOGIN = 2;
		
		JSONObject accept = null;

		try {			

			/* check if the key is valid */
			int userId = AuthUtils.getUserIdFromKey(key);			

			if (userId < 0) {
				return Services.serviceRefused("Please log in.", USER_LOGGED_OUT);
			}			
			
			/* check if the login is valid */
			int forUserId = AuthUtils.getUserIdFromLogin(userLogin);
			
			if (forUserId< 0){
				return Services.serviceRefused("Incorect user login", INCORECT_LOGIN);
			}
			
			accept = Services.serviceAccepted();
			accept.put("comments", CommentsUtils.getComments(forUserId, true));			

		} catch (SQLException e) {
			return Services.serviceRefused("SQL Error\n" + e.getMessage(), Services.SQL_ERROR);

		} catch (Exception e) {
			return Services.serviceRefused("Java Error\n" + e.getMessage(), Services.JAVA_ERROR);
		}
		
		return accept;					
	}
	
	/**
	 * Returns all the comments of a user's friend as a {@link JSONObject}, sorted from recent to last recent.
	 * @param key session-key
	 * @param userLogin userLogin
	 * @return all the comments of a user's friend as a {@link JSONObject}, sorted from recent to last recent.
	 */
	
	public static JSONObject getForFriends (String key, String userLogin){
		
		final int USER_LOGGED_OUT = 1;	
		final int INCORECT_LOGIN = 2;
		
		JSONObject accept = null;

		try {			

			/* check if the key is valid */
			int userId = AuthUtils.getUserIdFromKey(key);			

			if (userId < 0) {
				return Services.serviceRefused("Please log in.", USER_LOGGED_OUT);
			}			
			
			/* check if the login is valid */
			int forUserId = AuthUtils.getUserIdFromLogin(userLogin);
			
			if (forUserId< 0){
				return Services.serviceRefused("Incorect user login", INCORECT_LOGIN);
			}
			
			accept = Services.serviceAccepted();
			accept.put("comments", CommentsUtils.getFriendsComments(forUserId));

		} catch (SQLException e) {
			return Services.serviceRefused("SQL Error\n" + e.getMessage(), Services.SQL_ERROR);

		} catch (Exception e) {
			return Services.serviceRefused("Java Error\n" + e.getMessage(), Services.JAVA_ERROR);
		}
		
		return accept;					
	}
	
	public static JSONObject search (String key, String query, boolean forFriends, boolean myself) {
				
		final int INCORECT_LOGIN = 1;
		
		JSONObject accept = null;

		try {			
			int forUserId = AuthUtils.getUserIdFromKey(key);
						
			if(!key.isEmpty() && forUserId < 0){																								
				return Services.serviceRefused("Incorect user login", INCORECT_LOGIN);				
			}						
			
			accept = Services.serviceAccepted();
			accept.put("comments", CommentsUtils.search(forUserId, query, forFriends, myself));

		} catch (SQLException e) {
			return Services.serviceRefused("SQL Error\n" + e.getMessage(), Services.SQL_ERROR);

		} catch (Exception e) {
			return Services.serviceRefused("Java Error\n" + e.getMessage(), Services.JAVA_ERROR);
		}
		
		return accept;					
	}
	
}
