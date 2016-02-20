package services.test.comments;

import java.util.logging.Level;

import org.bson.Document;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

public class Test {
	
	public static void main (String [] args){

		/* Hide console output */
		java.util.logging.Logger.getLogger("org.mongodb.driver").setLevel(Level.SEVERE); 
		
		
		MongoClient mongo = new MongoClient("132.227.201.129", 27130);		
		MongoDatabase db = mongo.getDatabase("gr3_postaru");		
		
		MongoCollection <Document> collection = db.getCollection("testDB");
		
		Document doc = new Document();
		
		//doc.put("msg", "It Works!");
		
		collection.insertOne(doc);				
		
		Document myDoc = collection.find().first();
		System.out.println(myDoc.toJson()); 
		 
		mongo.close();
		
	}
}
