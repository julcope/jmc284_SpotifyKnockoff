package jmc284_spotifyknockoff;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JRadioButton;
import javax.swing.ButtonGroup;

public class SpotifyGUI {

	private JFrame frame;
	private final ButtonGroup buttonGroup = new ButtonGroup();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					SpotifyGUI window = new SpotifyGUI();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public SpotifyGUI() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame("Spotify");
		frame.setBounds(100, 100, 1000, 700);
		frame.getContentPane().setLayout(null);
		
		JLabel lblViewSelector = new JLabel("Select View");
		lblViewSelector.setFont(new Font("Arial", Font.PLAIN, 25));
		lblViewSelector.setBounds(28, 16, 149, 48);
		frame.getContentPane().add(lblViewSelector);
		
		JRadioButton radioShowAlbums = new JRadioButton("Songs");
		radioShowAlbums.setFont(new Font("Arial", Font.PLAIN, 16));
		buttonGroup.add(radioShowAlbums);
		radioShowAlbums.setBounds(38, 63, 155, 29);
		frame.getContentPane().add(radioShowAlbums);
		
		JRadioButton rdbtnNewRadioButton = new JRadioButton("Artists");
		rdbtnNewRadioButton.setFont(new Font("Arial", Font.PLAIN, 16));
		buttonGroup.add(rdbtnNewRadioButton);
		rdbtnNewRadioButton.setBounds(38, 100, 155, 29);
		frame.getContentPane().add(rdbtnNewRadioButton);
		
		JRadioButton rdbtnSongs = new JRadioButton("Albums");
		rdbtnSongs.setFont(new Font("Arial", Font.PLAIN, 16));
		buttonGroup.add(rdbtnSongs);
		rdbtnSongs.setBounds(38, 137, 155, 29);
		frame.getContentPane().add(rdbtnSongs);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	private static class __Tmp {
		private static void __tmp() {
			  javax.swing.JPanel __wbp_panel = new javax.swing.JPanel();
		}
	}
}
