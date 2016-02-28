package services.tools;

import java.sql.SQLException;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import auth.AuthUtils;
import friends.FriendsUtils;
import netscape.javascript.JSObject;

public class ServicesFriends {

	/**
	 * Forms a new friendship between two users.
	 * @param key user session key
	 * @param friendLogin friend login
	 * @return A serviceAcepted / serviceRefused JSONObject.
	 */
	
	public static JSONObject add(String key, String friendLogin) {

		final int USER_LOGGED_OUT = 1;
		final int INCORECT_FRIEND_LOGIN = 2;
		final int ALREADY_FRIENDS = 3;

		try {

			/* check if the key is valid */
			int fromId = AuthUtils.getUserIdFromKey(key);

			if (fromId < 0) {
				return Services.serviceRefused("Please log in to make a friend request.", USER_LOGGED_OUT);
			}

			/* check if friend login is valid */
			int toId = AuthUtils.getUserIdFromLogin(friendLogin);

			if (toId < 0) {
				return Services.serviceRefused("Invalid friend username.", INCORECT_FRIEND_LOGIN);
			}

			/* check if already friends */
			boolean alreadyFriends = FriendsUtils.alreadyFriends(fromId, toId);

			if (alreadyFriends) {
				return Services.serviceRefused("You are already friends with this user", ALREADY_FRIENDS);
			}

			FriendsUtils.addFriendToDatabase(fromId, toId);

		} catch (SQLException e) {
			return Services.serviceRefused("SQL Error\n" + e.getMessage(), Services.SQL_ERROR);

		} catch (Exception e) {
			return Services.serviceRefused("Java Error\n" + e.getMessage(), Services.JAVA_ERROR);
		}

		return Services.serviceAccepted();
	}
	
	/**
	 * Deletes a existing friendship between 2 users.
	 * @param key user session key
	 * @param friendLogin friend login
	 * A serviceAcepted / serviceRefused JSONObject.	 
	 */
	
	public static JSONObject remove(String key, String friendLogin) {

		final int USER_LOGGED_OUT = 1;
		final int INCORECT_FRIEND_LOGIN = 2;
		final int NOT_FRIENDS = 3;

		try {

			/* check if the key is valid */
			int fromId = AuthUtils.getUserIdFromKey(key);

			if (fromId < 0) {
				return Services.serviceRefused("Please log in to delete a friend.", USER_LOGGED_OUT);
			}

			/* check if friend login is valid */
			int toId = AuthUtils.getUserIdFromLogin(friendLogin);

			if (toId < 0) {
				return Services.serviceRefused("Invalid friend username.", INCORECT_FRIEND_LOGIN);
			}

			/* check if they are friends */
			boolean alreadyFriends = FriendsUtils.alreadyFriends(fromId, toId);

			if (!alreadyFriends) {
				return Services.serviceRefused("You are not friends with this user.", NOT_FRIENDS);
			}

			FriendsUtils.removeFriendFromDatabase(fromId, toId);

		} catch (SQLException e) {
			return Services.serviceRefused("SQL Error\n" + e.getMessage(), Services.SQL_ERROR);

		} catch (Exception e) {
			return Services.serviceRefused("Java Error\n" + e.getMessage(), Services.JAVA_ERROR);
		}

		return Services.serviceAccepted();
	}
	
	/**
	 * Returns a {@link JSObject} containing the list of friends for this user.
	 * @param key session-key
	 * @param friendLogin friendLogin
	 * @return a {@link JSObject} containing the list of friends for this user
	 */
	
	public static JSONObject get(String key, String friendLogin){
		
		final int USER_LOGGED_OUT = 1;
		final int INCORECT_LOGIN = 2;
		
		JSONObject rezult = null;
		
		try {

			/* check if the key is valid */
			int fromId = AuthUtils.getUserIdFromKey(key);

			if (fromId < 0) {
				return Services.serviceRefused("Please log in.", USER_LOGGED_OUT);
			}

			/* check if login is valid */
			int toId = AuthUtils.getUserIdFromLogin(friendLogin);

			if (toId < 0) {
				return Services.serviceRefused("Invalid username.", INCORECT_LOGIN);
			}			
						
			List<JSONArray> friendList = FriendsUtils.getFriendsForUserId(toId);
			
			rezult = Services.serviceAccepted().put("friends", friendList);
			
		} catch (SQLException e) {
			return Services.serviceRefused("SQL Error " + e.getMessage(), Services.SQL_ERROR);

		} catch (JSONException e){
			return Services.serviceRefused("JSON Error " + e.getMessage(), Services.JSON_ERROR);
		
		}catch (Exception e) {
			return Services.serviceRefused("Java Error " + e.getMessage(), Services.JAVA_ERROR);
		}
											
		return rezult;		
	}

}
