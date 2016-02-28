package services.test.comments;

import org.json.JSONObject;

import comments.CommentsUtils;
import database.DataBaseUtils;
import services.tools.ServicesComments;


public class CommentsTest {

	public static void main(String[] args) {				
		
		//CommentsUtils.addComment(4, "Hero", "I am hero's comment");
		//CommentsUtils.addComment(5, "Zeus", "I am Zeus's comment");
		//CommentsUtils.addComment(4, "Hero", "I am hero's second comment");
		//CommentsUtils.addComment(24, "Zeus1", "By Zeus's Hammer23");
		//CommentsUtils.addComment(24, "Zeus2", "By Zeus's Hammer24");
		
		//DataBaseUtils.printMongoCollection("comments");		
		
		//JSONObject jo = ServicesFriends.get("769221ad24cc4ba6b493b0b4a977e6ad", "TestUser");
		//ServicesFriends.add("769221ad24cc4ba6b493b0b4a977e6ad", "Zeus");
		//ServicesFriends.add("769221ad24cc4ba6b493b0b4a977e6ad", "Hero");
		
		DataBaseUtils.printMySQLTable("users");
		DataBaseUtils.printMySQLTable("friends");
		DataBaseUtils.printMySQLTable("session");
		
		DataBaseUtils.printMongoCollection("comments");	
		
		//System.out.println("\n" + jo.toString() + "\n");
		/*
		try {
			System.out.println("All comments\n" + CommentsUtils.getComments(6, false));
			List<JSONObject> list = CommentsUtils.getFriendsComments(6);
			
			for (JSONObject comment : list){
				System.out.println(comment);
			}
		} catch (InstantiationException | IllegalAccessException | ClassNotFoundException | SQLException
				| JSONException e) {
			
			e.printStackTrace();
		}
		*/
				
		
		CommentsUtils.addComment(5, "Zeus", "ZEUS ROCKS");
		JSONObject jo = ServicesComments.getForFriends("769221ad24cc4ba6b493b0b4a977e6ad", "TestUser");
		
		System.out.println(jo);
									
		//CommentsUtils.printComments(CommentsUtils.getAllComments());
		//CommentsUtils.printComments(CommentsUtils.getComments(6, false));
		
		//CommentsUtils.printComments(CommentsUtils.getCommentsByUserId(24, false));
		//CommentsUtils.printComments(CommentsUtils.getCommentsByUserId(24, true));
		
		
	}
}
