package jmc284_spotifyknockoff;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;
import java.sql.Statement;
import java.util.Map;


public class Artist {

	private String artistID;
	private String firstName;
	private String lastName;
	private String bandName;
	private String bio;
	
	
	public Artist(String firstName, String lastName, String bandName) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.bandName = bandName;
		this.artistID = UUID.randomUUID().toString();
		this.bio = "";
		// add lines of code to add this to the database
		//will need to import stuff
		//watch the videos to see how to connect to the database
		//System.out.println(artistID);
		String sql = "INSERT INTO artist(artist_id,first_name,last_name,band_name,bio) ";
		//sql += "VALUES ('" + this.artistID + "', '" + this.firstName + "', '" + this.lastName + "', '" + this.bandName + "', '" + this.bio + "');";
		sql += "VALUES(?, ?, ?, ?, ?);";
		//System.out.println(sql);
		
		try {
			DbUtilities db = new DbUtilities();
			Connection conn = db.getConn();
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, this.artistID);
			ps.setString(2,  this.firstName);
			ps.setString(3, this.lastName);
			ps.setString(4, this.bandName);
			ps.setString(5, this.bio);
			ps.executeUpdate();
			db.closeDbConnection();
			db = null;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
	}//first constructor 
	
	
	public Artist(String artistID) {
		String sql = "SELECT * FROM artist WHERE artist_id = '" + artistID + "';";
		///System.out.println(sql);
		
		try {
			DbUtilities db = new DbUtilities();
			ResultSet rs = db.getResultSet(sql);
			while(rs.next()){
				
				
				this.artistID = rs.getString("artist_id");
				this.firstName = rs.getString("first_name");
				this.lastName = rs.getString("last_name");
				this.bandName = rs.getString("band_name");
				this.bio = rs.getString("bio");
				//System.out.println("Thsi is " + this.firstName + " " + this.lastName);
				
			
			}
			
			db.closeDbConnection();
			db = null;
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
				
		
		
		
		
		
	}//second constructor

	
	
	public String getArtistID() {
		// TODO Auto-generated method stub
		return artistID;
	}


	public String getBio() {
		return bio;
	}


	public void setBio(String bio) {
		this.bio = bio;
		//sql statements to update the data base
		String sql = "UPDATE artist SET bio = ? WHERE artist_id = ?;";
		try {
			DbUtilities db = new DbUtilities();
			Connection conn = db.getConn();
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, bio);
			ps.setString(2,  this.artistID);
			ps.executeUpdate();
			db.closeDbConnection();
			db = null;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}


	public String getFirstName() {
		return firstName;
	}


	public String getLastName() {
		return lastName;
	}


	public String getBandName() {
		return bandName;
	}
	
	public void deleteArtist(String artistID){
		//delete them all from the data base and set the object variables to null
		
		String sql = "DELETE FROM artist WHERE artist_id = '" + artistID + "';";
		String sqlJunction = "DELETE FROM song_artist WHERE fk_artist_id = '" + artistID + "';";
		
		try {
			DbUtilities db = new DbUtilities();
			Connection conn = db.getConn();
			Statement s = conn.createStatement();
			s.executeUpdate(sqlJunction);
			s.executeUpdate(sql);
			//PreparedStatement ps = conn.prepareStatement(sql);
			//ps.executeUpdate();
			db.closeDbConnection();
			db = null;
			//set object variables to null		
			this.firstName = null;
			this.lastName = null;
			this.bandName = null;
			this.artistID = null;
			this.bio = null;
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	

	
	
}
















