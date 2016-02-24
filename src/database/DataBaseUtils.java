package database;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;

import org.bson.Document;

import com.mongodb.Block;
import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.ResultSetMetaData;
import com.mysql.jdbc.Statement;

public class DataBaseUtils {

	/** MySQL Database Auth infos */
	
	private static final String sqlHost = "132.227.201.129:33306";
	private static final String sqlDb = "gr3_postaru";
	private static final String sqlUsername = "gr3_postaru";
	private static final String sqlPassword = "WdSPtM";
		
	/** if using pooling */
	private static boolean pooling = false;
	
	/** current mySQL DataBase */
	private static DataBase mySQLDatabase = null;
	
	/** MongoDB Database Auth infos */
	
	private static final String mongoHost = "132.227.201.129";
	private static final int mongoPort = 27130;
	private static final String mongoDatabaseName = "gr3_postaru";
	
	/** current MongoDatabase */
	
	private static MongoDatabase mongoDatabse = null;
	
	
	/**
	 * Grants access to the database.
	 * @return a Connection the the database.
	 * @throws SQLException
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws ClassNotFoundException
	 */
	public static Connection getMySQLConnection()
			throws SQLException, InstantiationException, IllegalAccessException, ClassNotFoundException {

		if (pooling == false) {

			Class.forName("com.mysql.jdbc.Driver").newInstance();
			return (Connection) DriverManager.getConnection("jdbc:mysql://" + sqlHost + "/" + sqlDb, sqlUsername, sqlPassword);
		} else {

			if (mySQLDatabase == null) {
				mySQLDatabase = new DataBase("jdbc/db");
			}
			return mySQLDatabase.getConnection();
		}
	}
	
	/**
	 * Grants access to the mongo database. Opens a new connection if needed.
	 * @return a new mongo database connection
	 */
	
	public static MongoDatabase getMongoConnection(){
		
		/* Hide console logging output */
		java.util.logging.Logger.getLogger("org.mongodb.driver").setLevel(Level.SEVERE); 
						
		if (mongoDatabse == null){
			
			@SuppressWarnings("resource")
			MongoClient mongo = new MongoClient(mongoHost, mongoPort);		
			MongoDatabase db = mongo.getDatabase(mongoDatabaseName);			
			mongoDatabse = db;			
		}
		
		return mongoDatabse;						
	}

	/**
 	 * Prints the database table.
 	 * @param tableName the table name.
	 */

	public static void printMySQLTable(String tableName){
		
		String sql = "SELECT * FROM " + tableName;		
		
		try {
			  Connection connection = DataBaseUtils.getMySQLConnection();
			  Statement st = (Statement) connection.createStatement();
			  ResultSet rs = st.executeQuery(sql);
			  ResultSetMetaData rsmd = (ResultSetMetaData) rs.getMetaData();
			  			  
			  int columnsNumber = rsmd.getColumnCount();
			  
			  /* print column name */
			  
			  for (int i = 1; i <= columnsNumber; i++){
				  System.out.print(String.format("%-40s", rsmd.getColumnName(i)));
			  }
			  			  
			  /* print database info */
			  
			  while (rs.next()){	
				  System.out.println();
				  for (int i = 1; i <= columnsNumber; i++){
					  System.out.print(String.format("%-40s",rs.getString(i)));
				  
				  }					 
			  }	
			  
			  System.out.println();
			  st.close();
			  connection.close();			  			  			  
			
		} catch (Exception e) {
			e.printStackTrace();
		}				
	}
	
	public static void printMongoCollection (String collectionName){
		
		MongoDatabase db = getMongoConnection();
		MongoCollection<Document> collection = db.getCollection("comments");
		
		FindIterable<Document> iterable = collection.find();
		
		iterable.forEach(new Block<Document>() {
		    @Override
		    public void apply(final Document document) {
		        System.out.println(document.toJson());
		    }
		});				
	}
}

