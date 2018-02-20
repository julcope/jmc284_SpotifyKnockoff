package jmc284_spotifyknockoff;

import java.util.Map;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;

public class Song {
	private String songID;
	private String title;
	private double length;
	private String filePath;
	private String releaseDate;
	private String recordDate;
	Hashtable<String, Artist> songArtists;
	
	public Song(String title, double length, String releaseDate, String recordDate){
		this.title = title;
		this.length = length;
		this.releaseDate = releaseDate;
		this.recordDate = recordDate;
		this.songID = UUID.randomUUID().toString();
		songArtists = new Hashtable<String, Artist>();
		
		//this.addArtist();
		// System.out.println(this.songID);
		// String sql = "INSERT INTO song (song_id,title,length,file_path,release_date,record_date,fk_album_id) ";
		// sql += "VALUES ('" + this.songID + "', '" + this.title + "', " + this.length + ", '', '" + this.releaseDate + "', '" + this.recordDate + "', '" + this.albumID + "');";
		String sql = "INSERT INTO song (song_id,title,length,file_path,release_date,record_date) ";
		sql += "VALUES (?, ?, ?, ?, ?, ?);";
		
		//System.out.println(sql);
		
		try {
			DbUtilities db = new DbUtilities();
			Connection conn = db.getConn();
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, this.songID);
			ps.setString(2,  this.title);
			ps.setDouble(3, this.length);
			ps.setString(4, "");
			ps.setString(5, this.releaseDate);
			ps.setString(6, this.recordDate);
			ps.executeUpdate();
			db.closeDbConnection();
			db = null;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	
	public Song(String songID){
		songArtists = new Hashtable<String, Artist>();
		
		//to pull out a song that is already in the database
		String sql = "SELECT * FROM song WHERE song_id = '" + songID + "';";
		// System.out.println(sql);
		
		String sqlHash = "SELECT * FROM song_artist WHERE fk_song_id = '" + songID + "';";
		
		DbUtilities db = new DbUtilities();
		try {
			ResultSet rs = db.getResultSet(sql);
			//first result set is set to be for sql statement
			while(rs.next()){
				this.songID = rs.getString("song_id");
				// System.out.println("Song ID from database: " + this.songID);
				this.title = rs.getString("title");
				this.releaseDate = rs.getDate("release_date").toString();
				this.recordDate = rs.getDate("record_date").toString();
				this.length = rs.getDouble("length");
				//System.out.println("Song title from database: " + this.title);
			}
			
			// another SQL statement 
			//hashSQL = "SELECT * FROM album_song WHERE fk_album_id = " + albumID + ";"
			//change it to song artist
			//new result
			/*
			 * ResultSet rs2 = db.getResultSet(hashSQL);
		while(rs2.next()){
		
			Song thisSong = new Song(rs2.getString("fk_song_id"));
			albumSongs.put(thisSong.getSongID(), thisSong);
			//for song class make it song_artist the hashtable
			 * create artist with the fk artist ID from the database
			}
			 */
			
			//in same try block make a new result set statement
			ResultSet rsHash = db.getResultSet(sqlHash);
			//first result set is set to be for sql statement
			while(rsHash.next()){
				Artist thisArtist = new Artist(rsHash.getString("fk_artist_id"));
				songArtists.put(thisArtist.getArtistID(), thisArtist);
				
			}
			db.closeDbConnection();
			db = null;
			
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		
				
	}//second song constructor 

	public String getReleaseDate() {
		return releaseDate;
	}

	public String getSongID() {
		return songID;
	}

	public String getTitle() {
		return title;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
		String sql = "UPDATE song SET file_path = ? WHERE song_id ?;";
		
		try {
			DbUtilities db = new DbUtilities();
			Connection conn = db.getConn();
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, filePath);
			ps.setString(2,  this.songID);
			ps.executeUpdate();
			db.closeDbConnection();
			db = null;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	public double getLength() {
		return length;
	}

	public String getFilePath() {
		return filePath;
	}

	public String getRecordDate() {
		return recordDate;
	}


	/*public Map<String, Artist> getSongArtists() {
		return songArtists;
	}
	*/
	
	//Add artist
	/*first add the artist to the artist song hashtable 
	 * use the put command to add an artist id and artist object to the hash table
	 * make sql statmetment 
	 * sql = "INSERT INTO song_artist (fk_song_id, fk_artist_id) VALUES (?, ?)
	 * try catch to insert into the ?'s
	 
	 * }
	 */
	
	public void deleteSong(String songID) {
		//remove song from database and set object to null
		String sql = "DELETE FROM song WHERE song_id = '" + songID + "';";
		String sqlJunction = "DELETE FROM song_artist WHERE fk_song_id = '" + songID + "';";
		
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
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		//set object variables to null
		this.title = null;
		this.length = 0;
		this.releaseDate = null;
		this.recordDate = null;
		this.songID = null;
		this.songArtists = null;
	
	}
	
	public void addArtist(Artist artist) {
		//add an artist to the hastable for this artist
		songArtists.put(artist.getArtistID(), artist);
		// add both fks to the junction table in db
		String sql = "INSERT INTO song_artist(fk_song_id,fk_artist_id) VALUES (?, ?);";
		
		try {
			DbUtilities db = new DbUtilities();
			Connection conn = db.getConn();
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1,  this.songID);
			ps.setString(2,  artist.getArtistID());
			
			ps.executeUpdate();
			db.closeDbConnection();
			db = null;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public void deleteArtist(String artistID) {
		//delete artist from database table
		String sql ="DELETE FROM song_artist WHERE fk_song_id = ? AND fk_artist_id = ?;";
		
		try {
			DbUtilities db = new DbUtilities();
			Connection conn = db.getConn();
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1,  this.songID);
			ps.setString(2,  artistID);
			
			ps.executeUpdate();
			db.closeDbConnection();
			db = null;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		//delete artist from hashtable
		Artist thisArtist = new Artist(artistID);
		songArtists.remove(artistID, thisArtist);
		
		
	}
	
	public void deleteArtist(Artist artist) {
		// delete artist from database junction table
		String sql = "DELETE FROM song_artist WHERE fk_song_id = ? AND fk_artist_id = ?;";
		try {
			DbUtilities db = new DbUtilities();
			Connection conn = db.getConn();
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1,  this.songID);
			ps.setString(2,  artist.getArtistID());
			
			ps.executeUpdate();
			db.closeDbConnection();
			db = null;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		//delete artist from hashtable
		songArtists.remove(artist.getArtistID(), artist);
		
		
		
		
	}
	
	
	
	
	
	
	

}
