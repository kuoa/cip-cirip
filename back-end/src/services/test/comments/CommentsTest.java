package services.test.comments;

import java.sql.SQLException;

import org.json.JSONException;
import org.json.JSONObject;

import database.DataBaseUtils;
import services.tools.ServicesComments;
import services.tools.ServicesFriends;

public class CommentsTest {

	public static void main(String[] args) throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException, JSONException {

		//DataBaseUtils.getMongoConnection().getCollection("comments").drop();
		 
		//CommentsUtils.addComment(4, "Hero", "I am hero's comment");
		//CommentsUtils.addComment(5, "Zeus", "I am Zeus's comment");
		//CommentsUtils.addComment(4, "Hero", "I am hero's second comment");
		//CommentsUtils.addComment(24, "Zeus1", "By Zeus's Hammer23");
		//CommentsUtils.addComment(6, "TestUser", "MyOwnComment");

		// DataBaseUtils.printMongoCollection("comments");

		 JSONObject jo = ServicesComments.getForFriends("f5ae1ce511b44e7b82e55491a59036ad", "TestUser");
		System.out.println(jo);
		 // ServicesFriends.get("769221ad24cc4ba6b493b0b4a977e6ad", "TestUser");
		// ServicesFriends.add("769221ad24cc4ba6b493b0b4a977e6ad", "Zeus");
		// ServicesFriends.add("769221ad24cc4ba6b493b0b4a977e6ad", "Hero");

		 DataBaseUtils.printMySQLTable("users");
		 DataBaseUtils.printMySQLTable("friends");
		 //DataBaseUtils.printMySQLTable("session");		 

		// DataBaseUtils.printMongoCollection("comments");

		// System.out.println("\n" + jo.toString() + "\n");
		/*
		 * try { System.out.println("All comments\n" +
		 * CommentsUtils.getComments(6, false)); List<JSONObject> list =
		 * CommentsUtils.getFriendsComments(6);
		 * 
		 * for (JSONObject comment : list){ System.out.println(comment); } }
		 * catch (InstantiationException | IllegalAccessException |
		 * ClassNotFoundException | SQLException | JSONException e) {
		 * 
		 * e.printStackTrace(); }
		 */

		
		//CommentsUtils.addComment(7, "I_robot", "I am a robot with no friends.");

		

		//String c1 = "Hey check out this #image https://s-media-cache-ak0.pinimg.com/736x/82/2f/c2/822fc271f3457af71e88d80b51346769.jpg cool right?";
		//String c2 = "Hey check this #video https://www.youtube.com/embed/U5zZ1l4scgM coll right?";
		//String c3 = "Hey this is pretty cool right? #yolo #swag #SWA0";
		
		//CommentsUtils.addComment(5, "Zeus", c1);
		//CommentsUtils.addComment(5, "Zeus", c2);
		//CommentsUtils.addComment(5, "Zeus", c3);
		 
		 //ServicesUser.login("TestUser", "T3stUser@");
		//JSONObject jo = ServicesComments.getForFriends("769221ad24cc4ba6b493b0b4a977e6ad", "TestUser");
		
		// JSONObject jo = ServicesComments.search("", "", false);
		
		//ServicesComments.add(key, comment)
		 
		/*
		List<JSONObject> jo = CommentsUtils.search(6, "", true, true);
		System.out.println(jo);
		jo = CommentsUtils.search(6, "llo", true, true);
		System.out.println(jo);
		jo = CommentsUtils.search(6, "", true, true);
		System.out.println(jo);
		
		List<JSONObject> jall = CommentsUtils.search(6, "", false, false);
		System.out.println(jall);
		jall = CommentsUtils.search(6, "llo", false, false);
		System.out.println(jall);
				
		
		//ServicesFriends.add("f5ae1ce511b44e7b82e55491a59036ad", "Hero");
		
		//CommentsUtils.removeComment("56f905672d0fed1422d0c673");
		 * */
		// ServicesFriends.add("f5ae1ce511b44e7b82e55491a59036ad", "Hero");
		
		
		 
		// JSONObject jo = ServicesFriends.remove("e84a49cfeeef4eecb9d0611a811e6233", "Giraffe");
		 
		 //System.out.println(jo);
		 
		
		//jo = ServicesComments.search("", "", false);
		//System.out.println(jo);

		// System.out.println(RegexUtils.parseImageUrl(c1));
		// System.out.println(RegexUtils.parseVideoUrl(c2));
		// System.out.println(RegexUtils.parseHashTags(c3));

		// jo = ServicesComments.getForUser(key, userLogin)

		 //CommentsUtils.printComments(CommentsUtils.getAllComments());
		// CommentsUtils.printComments(CommentsUtils.getComments(6, false));

		// CommentsUtils.printComments(CommentsUtils.getCommentsByUserId(24,
		// false));
		// CommentsUtils.printComments(CommentsUtils.getCommentsByUserId(24,
		// true));

	}
}
