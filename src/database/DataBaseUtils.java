package database;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import com.mysql.jdbc.Connection;
import com.mysql.jdbc.ResultSetMetaData;
import com.mysql.jdbc.Statement;

public class DataBaseUtils {

	/** Database Auth infos */
	
	private static final String host = "132.227.201.129:33306";
	private static final String db = "gr3_postaru";
	private static final String username = "gr3_postaru";
	private static final String password = "WdSPtM";

	private static boolean pooling = false;
	private static DataBase database = null;
	
	
	/**
	 * Grants acces to the database.
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
			return (Connection) DriverManager.getConnection("jdbc:mysql://" + host + "/" + db, username, password);
		} else {

			if (database == null) {
				database = new DataBase("jdbc/db");
			}
			return database.getConnection();
		}
	}

	/**
 	 * Prints the database table of @param tableName
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
}

