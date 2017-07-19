package utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public final class ConnectionHelperCrate {
	
	public static Connection getDBConnection() {
		Connection conn = null;

		try {
			conn = DriverManager.getConnection("crate://localhost:5432/");
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return conn;
	}


}
