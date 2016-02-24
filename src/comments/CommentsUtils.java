package comments;

import org.bson.Document;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import database.DataBaseUtils;

public class CommentsUtils {
	
	public static void addComment(int authorId, String authorLogin, String authorComment){
		
		MongoDatabase db = DataBaseUtils.getMongoConnection(); 
		MongoCollection<Document> collection = db.getCollection("comments");
		
		Comment comment = new Comment(authorId, authorLogin, authorComment);
		Document commentDoc = comment.getDocument();
		
		collection.insertOne(commentDoc);								
	}
	
	public static FindIterable<Document> getAllComments(){
		
		return null;
	}
	
	public static FindIterable<Document> getCommentsByUserId(int userId, boolean sorthed){
		return null;
	}

}
