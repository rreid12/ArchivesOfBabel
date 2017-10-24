import java.awt.BorderLayout;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import javax.swing.JFrame;
import javax.swing.JTextField;

/**
 * GUI Application for the Archives of Babel Library Database
 * 
 * NAME: Ryan Reid
 * SECTION: COMP-2670-01
 * 
 * @author reidr
 */

/*
 * This class simply launches the application by opening the Front Page.
 * The 3 lines of code instantiate a new FrontPage object, set the size
 * of the window, and set it to be visible to the user on the screen
 */
public class EntryPoint {

	public static void main(String[] args) {
		
		/*- API - JFileChooser
		* - will let user choose a path to the database (possible addition)
		* 
		* 
		* instantiate a 'FrontPage' to bring up the front page of the UI
		*/
		
		try {
			final Connection connection = DriverManager.getConnection("jdbc:sqlite:" + args[0]);
			
			FrontPage pg = new FrontPage(connection);
			pg.setSize(1120, 1000);
			pg.setVisible(true);
			
		} catch (SQLException e) {
			System.out.printf("Error connecting to db: %s%n", e.getMessage());
			System.exit(0);
		}
		

	}

}
