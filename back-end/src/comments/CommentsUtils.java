package comments;

import org.json.JSONException;
import org.json.JSONObject;

import java.sql.SQLException;
import java.util.ArrayList;

import java.util.Collections;
import java.util.List;


import org.bson.Document;
import org.bson.types.ObjectId;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import auth.AuthUtils;
import database.DataBaseUtils;
import friends.FriendsUtils;

public class CommentsUtils {
	
	public static final String COMMENTS = "comments";		/** name of the comments collection */
	
	
	/**
	 * Add a new comment to the data base.
	 * @param authorId authorId
	 * @param authorLogin authorLogin
	 * @param authorComment comment
	 */
	public static void addComment(int authorId, String authorLogin, String authorComment){
		
		MongoDatabase db = DataBaseUtils.getMongoConnection(); 
		MongoCollection<Document> collection = db.getCollection(COMMENTS);
		
		Comment comment = new Comment(authorId, authorLogin, authorComment);
		Document commentDoc = comment.getDocument();
		
		collection.insertOne(commentDoc);								
	}
	
	public static void removeComment(String commentId){
		
		MongoDatabase db = DataBaseUtils.getMongoConnection(); 
		MongoCollection<Document> collection = db.getCollection(COMMENTS);
		
		Document commentMask= new Document("_id", new ObjectId(commentId));		
		collection.findOneAndDelete(commentMask);		
	}
	
	/**
	 * Returns all the comments.
	 * @return all the comments in the database.
	 */
	public static FindIterable<Document> getAllComments(){
		
		MongoDatabase db = DataBaseUtils.getMongoConnection();
		MongoCollection<Document> collection = db.getCollection(COMMENTS);
		
		FindIterable<Document> comments = collection.find();							
		
		return comments;						
	}
			
	
	/**
	 * Returns all the comments as Documents, for a specific user, sorted if needed.
	 * @param userId userId
	 * @param sort true -> sorted from most recent to last recent | false -> not sorted
	 * @return all the comments as Documents for a specific user.
	 */
	
	public static FindIterable<Document> getCommentsDoc(int userId, boolean sort){
		
		MongoDatabase db = DataBaseUtils.getMongoConnection();
		MongoCollection<Document> collection = db.getCollection(COMMENTS);
				
		Document findMask = new Document("author.id", userId);
			
		FindIterable<Document> comments;
						
		if(sort){		
			Document sortMask = new Document("comment.date", -1);
			comments = collection.find(findMask).sort(sortMask);
		}
		else {
			comments = collection.find(findMask);
		}
		
		return comments;		
	}

	
	/**
	 * Returns all the comments as a list of {@link JSONObject}, for a specific user, sorted if needed.
	 * @param userId userId
	 * @param sort true -> sorted from most recent to last recent | false -> not sorted
	 * @return all the comments as a list of {@link JSONObject} for a specific user.
	 */
	
	public static List<JSONObject> getComments(int userId, boolean sort) throws JSONException{
		
		List<JSONObject> comments = new ArrayList<JSONObject>();
		FindIterable<Document> commentsDoc = getCommentsDoc(userId, sort);
		
		for (Document doc : commentsDoc){
			comments.add(new JSONObject(doc.toJson()));
		}
		
		return comments;
							
	}
	
	/**
	 * Returns all the comments for the user's friends, sorted from most recent to last recent.
	 * @param userId userId
	 * @return all the user's friends comments.
	 * @throws JSONException 
	 * @throws SQLException 
	 * @throws ClassNotFoundException 
	 * @throws IllegalAccessException 
	 * @throws InstantiationException 
	 */
	public static List<JSONObject> getFriendsComments(int userId) 
			throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException, JSONException{
		
		List <JSONObject> friendList = FriendsUtils.getFriendsForUserId(userId);			// get list of friends
		
		JSONObject user = new JSONObject().put("id", userId).put("login", AuthUtils.getUserLoginFromId(userId));				
		friendList.add(user);
		
		List<JSONObject> friendsComments = new ArrayList<JSONObject>();					// prepare list of comments
		
		for (JSONObject f : friendList){
			int friendId = f.getInt("id");						
			
			FindIterable<Document> comments = getCommentsDoc(friendId, false);				// for each friend get comment list
			
			for (Document comment : comments){
				friendsComments.add(new JSONObject(comment.toJson()));					// convert to JSON
			}
		}	
		
		Collections.sort(friendsComments, new CommentCompare());
		
		return friendsComments;
	}
	
	
	/**
	 * 
	 * @param query
	 * @param forFriends
	 * @param sort
	 * @return
	 * @throws JSONException
	 */
	
	public static List<JSONObject> search(int userId, String query, boolean forFriends) throws JSONException{
		
		MongoDatabase db = DataBaseUtils.getMongoConnection();
		MongoCollection<Document> collection = db.getCollection(COMMENTS);
		
		FindIterable<Document> comments;
		List<JSONObject> resultComments = new ArrayList<JSONObject>();											
						
	
		Document sortMask = new Document("comment.date", -1);
		comments = collection.find().sort(sortMask);		
		
		for (Document doc : comments){
			resultComments.add(new JSONObject(doc.toJson()));
		}
		
		return resultComments;
		
	}	
	
	/**
	 * Prints a list of comments in a JSON format. 
	 * @param comments list of comments
	 */
	public static void printComments(FindIterable<Document> comments){
		
		for (Document comment : comments){
			System.out.println(comment.toJson());
		}
	}
}
