package jmc284_spotifyknockoff;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Hashtable;
import java.util.UUID;

public class Album {
	 private String albumID;
	 private String title;
	 private String releaseDate;
	 private String coverImagePath;
	 private String recordingCompany;
	 private int numberOfTracks;
	 private String pmrcRating;
	 private int length;
	 Hashtable<String, Song> albumSongs;

	 //Album(title:String, releaseDate:String, recordingCompany:String, numberOfTracks:int, pmrcRating:String, length:int)

	 public Album(String title, String releaseDate, String recordingCompany, int numberOfTracks, String pmrcRating, int length){
		 	this.albumID = UUID.randomUUID().toString();
			this.title = title;
			this.releaseDate = releaseDate;
			this.recordingCompany = recordingCompany;
			this.numberOfTracks = numberOfTracks;
			this.pmrcRating = pmrcRating;
			this.length = length;
			albumSongs = new Hashtable<String, Song>();
			
			// System.out.println(this.songID);
			// String sql = "INSERT INTO song (song_id,title,length,file_path,release_date,record_date,fk_album_id) ";
			// sql += "VALUES ('" + this.songID + "', '" + this.title + "', " + this.length + ", '', '" + this.releaseDate + "', '" + this.recordDate + "', '" + this.albumID + "');";
			String sql = "INSERT INTO spotify_knockoff.album(album_id,title,release_date,cover_image_path,recording_company_name,number_of_tracks,PMRC_rating,length)";
			sql += "VALUES(?,?,?,?,?,?,?,?)";
			
			//System.out.println(sql);
			
			try {
				DbUtilities db = new DbUtilities();
				Connection conn = db.getConn();
				PreparedStatement ps = conn.prepareStatement(sql);
				ps.setString(1, this.albumID);
				ps.setString(2,  this.title);
				ps.setString(3, this.releaseDate);
				ps.setString(4, "");
				ps.setString(5, this.recordingCompany);
				ps.setInt(6, this.numberOfTracks);
				ps.setString(7, this.pmrcRating);
				ps.setInt(8, this.length);
				ps.executeUpdate();
				db.closeDbConnection();
				db = null;
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
		}
	 
	 
	 public Album(String albumID) {
		 albumSongs = new Hashtable<String, Song>();
		 //pull the information from the database to create an object with those characteristics
		 String sql = "SELECT * FROM ablum WHERE album_id = '" + albumID + "';";
		 String sqlHash = "SELECT * FROM album_song WHERE fk_album_id = '" + albumID + "';";
		 
		 DbUtilities db = new DbUtilities();
			try {
				ResultSet rs = db.getResultSet(sql);
				//first result set is set to be for sql statement
				while(rs.next()){
					this.albumID = rs.getString("album_id");
					this.title = rs.getString("title");
					this.releaseDate = rs.getDate("release_date").toString();
					this.coverImagePath = rs.getString("cover_image_path");
					this.recordingCompany = rs.getString("recording_company_name");
					this.numberOfTracks = rs.getInt("number_of_tracks");
					this.pmrcRating = rs.getString("PMRC_rating");
					this.length = (int) rs.getDouble("length");
					//System.out.println("Song title from database: " + this.title);
				}
				
				// another SQL statement
				
				//in same try block make a new result set statement
				ResultSet rsHash = db.getResultSet(sqlHash);
				//first result set is set to be for sql statement
				while(rsHash.next()){
					Song thisSong = new Song(rsHash.getString("fk_song_id"));
					albumSongs.put(thisSong.getSongID(), thisSong);
					//Artist thisArtist = new Artist(rsHash.getString("fk_artist_id"));
					//songArtists.put(thisArtist.getArtistID(), thisArtist);
					this.albumID = null;
					this.title = null;
					this.releaseDate = null;
					this.recordingCompany = null;
					this.numberOfTracks = 0;
					this.pmrcRating = null;
					this.length = 0;
					this.albumSongs = null;
				}
				db.closeDbConnection();
				db = null;
				
				
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		 
		 
		 
		 
	 }
	 
	 

	public String getCoverImagePath() {
		return coverImagePath;
	}

	public void setCoverImagePath(String coverImagePath) {
		this.coverImagePath = coverImagePath;
		//database
	}

	public String getAlbumID() {
		return albumID;
	}

	public String getTitle() {
		return title;
	}

	public String getReleaseDate() {
		return releaseDate;
	}

	public String getRecordingCompany() {
		return recordingCompany;
	}

	public int getNumberOfTracks() {
		return numberOfTracks;
	}

	public String getPmrcRating() {
		return pmrcRating;
	}

	public int getLength() {
		return length;
	}

	 
	public void deleteAlbum(String albumID) {
		//delete from album table and set values to null
		//delete from album song junction table
		String sql = "DELETE FROM album WHERE album_id = '" + albumID + "';";
		String sqlJunction = "DELETE FROM album_song WHERE fk_album_id = '" + albumID + "';";
		
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
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

		
		
		

	
	public void addSong(Song song) {
		//add song to hashtable and to database junction table
		albumSongs.put(song.getSongID(), song);
		//database
		String sql = "INSERT INTO album_song(fk_album_id, fk_song_id) VALUES (?, ?);";
		try {
			DbUtilities db = new DbUtilities();
			Connection conn = db.getConn();
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1,  this.albumID);
			ps.setString(2,  song.getSongID());
			
			ps.executeUpdate();
			db.closeDbConnection();
			db = null;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
	}
	
	public void deleteSong(String songID) {
		//deletes a song from an album/song junction table
		//have to remove it from the hash table as well
		String sql ="DELETE FROM album_song WHERE fk_album_id = ? AND fk_song_id = ?;";
		try {
			DbUtilities db = new DbUtilities();
			Connection conn = db.getConn();
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1,  this.albumID);
			ps.setString(2,  songID);
			
			ps.executeUpdate();
			db.closeDbConnection();
			db = null;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//delete artist from hashtable
		Song thisSong = new Song(songID);
		albumSongs.remove(songID, thisSong);
		
	}
	
	public void deleteSong(Song song) {
		//deletes a song from an album
		//remove the song from hashtable and juncttion table in database
		
		
		String sql ="DELETE FROM album_song WHERE fk_album_id = ? AND fk_song_id = ?;";
		try {
			DbUtilities db = new DbUtilities();
			Connection conn = db.getConn();
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1,  this.albumID);
			ps.setString(2,  song.getSongID());
			
			ps.executeUpdate();
			db.closeDbConnection();
			db = null;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//delete artist from hashtable
		albumSongs.remove(song.getSongID(), song);
		
		
		
	}
	
	
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
		
}
