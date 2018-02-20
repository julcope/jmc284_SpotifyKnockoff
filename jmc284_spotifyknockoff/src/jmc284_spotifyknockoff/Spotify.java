package jmc284_spotifyknockoff;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Spotify {

	public static void main(String[] args) {
		String hostName = "sis-teach-01.sis.pitt.edu";
		String dbName = "spotify_knockoff";
		String userName = "spotifyUser";
		String password = "spotifyUser123";
		
		String connString = "jdbc:mysql://" + hostName + "/" + dbName + "?user=" + userName
				+ "&password=" + password;
		
		System.out.println(connString);
		
		try {
			Class.forName("com.mysql.jbdc.Driver").newInstance();
			Connection conn = DriverManager.getConnection(connString);
			
			String sql = "SELECT * FROM users;";
			
			Statement statement = conn.createStatement();
			ResultSet result = statement.executeQuery(sql);
			
			
			
		} catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		
	}

}
