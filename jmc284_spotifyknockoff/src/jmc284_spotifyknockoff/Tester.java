package jmc284_spotifyknockoff;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Map;
import java.util.UUID;

public class Tester {
	public static void main(String[] args){
		//create artist
		//Create an album
		//create 5 songs
		
		Artist artist1 = new Artist("Brendon", "Urie", "Panic at the Disco");
		Artist artist2 = new Artist("Patrick", "Stump", "Fall Out Boy");
		
		Album album1 = new Album("Pretty. Odd.", "2008-03-25", "Fueled by Ramen", 15, "R", 48);
		Album album2 = new Album("Folie a Deux", "2008-12-10", "Fueled by Ramen", 13, "R", 50);
		
		Song song1 = new Song("Nine in the afternoon", 3.12, "2008-03-25", "2003-03-03");
		Song song2 = new Song("That Green Gemntleman", 3.15, "2008-03-25", "2003-03-03");
		Song song3 = new Song("Northern Downpour", 4.07, "2008-03-25", "2003-03-03");
		Song song4 = new Song("What a Catch, Donnie", 4.53, "2008-03-25", "2003-03-03");
		Song song5 = new Song("20 Dollar Nose Bleed", 3.12, "2008-03-25", "2003-03-03");
		
		//put songs in the appropriate albums
		//give songs correct artists
		song1.addArtist(artist1);
		song2.addArtist(artist1);
		song3.addArtist(artist1);
		song4.addArtist(artist2);
		song5.addArtist(artist2);
		song4.addArtist(artist1);
		song5.addArtist(artist1);
		
		album1.addSong(song1);
		album1.addSong(song2);
		album1.addSong(song3);
		album2.addSong(song4);
		album2.addSong(song5);
		
		//add some incorrect artists to song objects
		//delete the artists from song objects
		song1.addArtist(artist2);
		song1.deleteArtist(artist2);

		//delete some songs from the database: create song objects based on songid, delete the song
		Song song6 = new Song(song3.getSongID());
		song6.deleteSong(song6.getSongID());
		song3 = new Song("Northern Downpour", 4.07, "2008-03-25", "2003-03-03");
		
		//delete an artist object, create one and delete it
		Artist artist3 = new Artist("Julia", "Cope", "My awesome band");
		Artist artist4 = new Artist(artist3.getArtistID());
		artist3.deleteArtist(artist3.getArtistID());
		artist4 = null;
		
		//add some incorrect songs to an album
		//delete the songs from the album
		album2.addSong(song3);
		album2.deleteSong(song3);
		album2.addSong(song3);
		album2.deleteSong(song3.getSongID());
		
		//delete an album
		Album album3 = new Album("cool album", "2008-12-10", "Java Rocks", 13, "R", 50);
		album3.deleteAlbum(album3.getAlbumID());
		
		
				
		
		System.out.println("nice...\n\nDONE!");
	}
}
