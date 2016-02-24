package comments;

import org.bson.Document;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import database.DataBaseUtils;

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
	
	public static void removeComment(String sessionKey, String commentId){
		// TODO
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
	 * Returns all the comments for a specific user, sorted if needed.
	 * @param userId userId
	 * @param sort true -> sorted from most recent to last recent | false -> not sorted
	 * @return all the comments for a specific user.
	 */
	public static FindIterable<Document> getComments(int userId, boolean sort){
		
		MongoDatabase db = DataBaseUtils.getMongoConnection();
		MongoCollection<Document> collection = db.getCollection(COMMENTS);
		
		Document findMask = new Document("author_id", userId);
		
		
		FindIterable<Document> comments;
		
		if(sort){
			Document sortMask = new Document("date", -1);
			comments = collection.find(findMask).sort(sortMask);
		}
		else {
			comments = collection.find(findMask);
		}
		
		return comments;		
	}
	
	/**
	 * Returns all the comments for the user's friends, sorted from most recent to last recent.
	 * @param userId userId
	 * @return all the user's friends comments.
	 */
	public static FindIterable<Document> getFriendsComments(int userId){
		
		return null;
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
