package database;

import java.sql.DriverManager;
import java.sql.SQLException;
import com.mysql.jdbc.Connection;

public class DB {

	private static final String host = "132.227.201.129:33306";
	private static final String db = "gr3_postaru";
	private static final String username = "gr3_postaru";
	private static final String password = "WdSPtM";

	private static boolean pooling = false;
	private static DataBase database = null;

	public static Connection getMySQLConnection() throws SQLException,
			InstantiationException, IllegalAccessException,
			ClassNotFoundException {

		if (pooling == false) {
			
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			return (Connection) DriverManager.getConnection("jdbc:mysql://" + host + "/" + db,
					username, password);
		}
		else{
			
			if (database == null){
				database = new DataBase("jdbc/db");
			}
			return database.getConnection();
		}
	}

}
