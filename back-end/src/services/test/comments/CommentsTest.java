package services.test.comments;
import database.DataBaseUtils;


public class CommentsTest {

	public static void main(String[] args) {
				
		
		//CommentsUtils.addComment(23, "Zeus", "By Zeus's Hammer");
		//CommentsUtils.addComment(24, "Zeus1", "By Zeus's Hammer23");
		//CommentsUtils.addComment(24, "Zeus2", "By Zeus's Hammer24");
		
		//DataBaseUtils.printMongoCollection("comments");		
		
		//JSONObject jo = ServicesComments.add("769221ad24cc4ba6b493b0b4a977e6ad", "live is good");
		
		//DataBaseUtils.printMySQLTable("users");
		//DataBaseUtils.printMySQLTable("session");
		
		//System.out.println("\n" + jo + "\n");
		
		DataBaseUtils.printMongoCollection("comments");
		//CommentsUtils.printComments(CommentsUtils.getAllComments());
		//CommentsUtils.printComments(CommentsUtils.getComments(6, false));
		
		//CommentsUtils.printComments(CommentsUtils.getCommentsByUserId(24, false));
		//CommentsUtils.printComments(CommentsUtils.getCommentsByUserId(24, true));
		
		
	}
}
