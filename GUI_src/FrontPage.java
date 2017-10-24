import javax.swing.JFrame;
import javax.swing.JCheckBox;
import java.awt.BorderLayout;
import javax.swing.JScrollPane;
import java.awt.GridLayout;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import java.awt.Label;
import java.awt.Font;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import java.awt.TextField;
import java.awt.Button;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.awt.event.ActionEvent;
import java.awt.TextArea;
import javax.swing.JButton;
import java.awt.Frame;
import java.awt.Choice;
import javax.swing.JLabel;
import javax.swing.JPasswordField;


/**
 * GUI Application for the Archives of Babel Library Database
 * 
 * NAME: Ryan Reid
 * SECTION: COMP-2670-01
 * 
 * @author reidr
 */

/*
 * This class, along with the EmployeePage class actually creates the FrontPage, 
 * first adding all of the textFields,labels, comboBoxes, textAreas, buttons, etc. 
 * Connects to the database with JDBC API, queries the database, and shows the
 * results on screen.
 */
public class FrontPage extends JFrame {
	
	private static final String B_TITLE = "Book Title";
	private static final String DVD_TITLE = "DVD Title";
	private static final String AUTHOR = "Author (Last Name)";
	private static final String PRODUCER = "Producer";
	private static int counter = 0;
	
	
	private String prgArg;
	private JTextField textField_1;
	
	public FrontPage(Connection connection) throws SQLException {
		
		getContentPane().setFont(new Font("Dialog", Font.PLAIN, 13));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//prgArg = arg;
		
		setTitle("Archives of Babel");
		getContentPane().setLayout(null);
		
		Label titleLabel = new Label("Archives of Babel - Library Database");
		titleLabel.setAlignment(Label.CENTER);
		if (System.getProperty("os.name").contains("mac") || System.getProperty("os.name").contains("linux")) {
			titleLabel.setFont(new Font("Dialog", Font.BOLD, 26));
		} else {
			titleLabel.setFont(new Font("Dialog", Font.BOLD, 30));
		}
		titleLabel.setBounds(281, 57, 536, 47);
		getContentPane().add(titleLabel);
		
		Label searchLabel = new Label("Quick Search");
		searchLabel.setFont(new Font("Dialog", Font.PLAIN, 20));
		searchLabel.setBounds(40, 409, 125, 24);
		getContentPane().add(searchLabel);
		
		JComboBox comboBox = new JComboBox();
		comboBox.setModel(new DefaultComboBoxModel(new String[] {B_TITLE, DVD_TITLE, AUTHOR, PRODUCER}));
		comboBox.setBounds(40, 448, 118, 22);
		getContentPane().add(comboBox);
		
		TextField textField = new TextField();
		textField.setBounds(40, 476, 367, 24);
		getContentPane().add(textField);
		
		
		TextArea textArea = new TextArea();
		textArea.setEditable(false);
		textArea.setBounds(40, 506, 1010, 423);
		getContentPane().add(textArea);
		
		Button searchButton = new Button("SEARCH");
		searchButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (textField.getText() != null) {
					

					String result = new String("");
					
					//connection is made with the database
					try {
						String sql = "";
						
						//if the comboBox item selected, do this query
						if (comboBox.getSelectedItem() == B_TITLE) {

							sql = "SELECT Book.Title AS Title, Author.FirstName AS Author_First_Name, Author.LastName AS Author_Last_Name, Book.NumOfCopies AS Num_Copies, Book.BookCost AS Book_Cost"
									+ " FROM Book INNER JOIN Author ON Book.AuthorID = Author.AuthorID"
									+ " WHERE Book.Title LIKE ?"
									+ " ORDER BY Book.Title DESC, Author.LastName DESC";
							
							//prepare a statement to be sent to the database (SQL statement)
							PreparedStatement stmt = connection.prepareStatement(sql);
							
							//set the statement up to have the user's input, with the "%" around so that all similar results will show as well
							stmt.setString(1, "%" + textField.getText() + "%");
							
							//actually execute the query by sending it to the database
							final ResultSet res = stmt.executeQuery();
							
							//get all the results from the database and store them in a string
							while (res.next()) {
								result = "Book Title: 	" + res.getString("Title") + "\n" + 
										 "Author: 		" + res.getString("Author_First_Name") + " " + res.getString("Author_Last_Name") + "\n" + 
										 "# of Copies: 	" + res.getString("Num_Copies") + "\n" +
										 "Cost: 		" + "$" + res.getString("Book_Cost")+ "\n\n" + result;
							}
							
							//set the textArea (results box) to the string of results
							textArea.setText(result);

						} else if (comboBox.getSelectedItem() == AUTHOR) {

							sql = "SELECT Book.Title AS Title, Author.FirstName AS Author_First_Name, Author.LastName AS Author_Last_Name, Book.NumOfCopies AS Num_Copies, Book.BookCost AS Book_Cost"
									+ "	FROM Book INNER JOIN Author ON Book.AuthorID = Author.AuthorID"
									+ " WHERE Author.LastName LIKE ?"
									+ " ORDER BY Author.LastName DESC, Book.Title DESC";

							//use jdbc to prepare a statement to query the database
							PreparedStatement stmt = connection.prepareStatement(sql);
							
							//set the value entered by the user to the ? is the sql String
							stmt.setString(1, "%" + textField.getText() + "%");
							
							//get the resultSet
							final ResultSet res = stmt.executeQuery();
							
							while (res.next()) {
								result = "Book Title: 	" + res.getString("Title") + "\n" + 
										 "Author: 		" + res.getString("Author_First_Name") + " " + res.getString("Author_Last_Name") + "\n" + 
										 "# of Copies: 	" + res.getString("Num_Copies") + "\n" +
										 "Cost: 		" + "$" + res.getString("Book_Cost")+ "\n\n" + result;
							}
							
							//set the results to be displayed in the textArea below
							textArea.setText(result);
		
						} else if (comboBox.getSelectedItem() == DVD_TITLE) {
							//DVD Query goes here
							sql = "SELECT DVD.Title AS Title, DVD.Producer AS Producer_Name, DVD.NumOfCopies AS Num_Copies, DVD.DVDCost AS DVD_Cost" +
								  " FROM DVD" +
								  " WHERE DVD.Title LIKE ?" + 
								  " ORDER BY DVD.Title DESC, DVD.Producer DESC";
							
							PreparedStatement stmt = connection.prepareStatement(sql);
							
							stmt.setString(1, "%" + textField.getText() + "%");
							
							final ResultSet res = stmt.executeQuery();
							
							while (res.next()) {
								result = "DVD Title:		" + res.getString("Title") + "\n" +
										 "Producer:		" + res.getString("Producer_Name") + "\n" +
										 "# of Copies:	" + res.getString("Num_Copies") + "\n" +
										 "Cost: 		" + res.getString("DVD_Cost") + "\n\n" + result;
							}
							textArea.setText(result);
							
						} else if (comboBox.getSelectedItem() == PRODUCER) {
							//Producer Query goes here
							sql = "SELECT DVD.Title AS Title, DVD.Producer AS Producer_Name, DVD.NumOfCopies AS Num_Copies, DVD.DVDCost AS DVD_Cost" +
								  " FROM DVD" +
								  " WHERE DVD.Producer LIKE ?" + 
								  "	ORDER BY DVD.Producer DESC, DVD.Title DESC";
							
							PreparedStatement stmt = connection.prepareStatement(sql);
							
							stmt.setString(1, "%" + textField.getText() + "%");
							
							final ResultSet res = stmt.executeQuery();
							
							while (res.next()) {
								result = "DVD Title:	" + res.getString("Title") + "\n" +
										 "Producer:		" + res.getString("Producer_Name") + "\n" +
										 "# of Copies:	" + res.getString("Num_Copies") + "\n" +
										 "Cost: 		" + res.getString("DVD_Cost") + "\n\n" + result;
							}
							textArea.setText(result);
							
							
						}
					}catch (SQLException e) {
						e.printStackTrace();
					}
/*					} catch (SQLException e) {
						System.out.printf("Error connecting to db: %s%n", e.getMessage());
						System.exit(0);
					}*/
				}
			}
		});
		
		/*^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^
		 * QUICK SEARCH FUNCTION
		 * 
		 * After the first set of buttons, textFields, textAreas, etc. have been added, a connection is made to
		 * the DB by the path set in the Program Arguments of the program. After this, the comboBox which specifies
		 * which kind of search the user wishes to do. Based on their selection in this comboBox, one of the queries 
		 * above will be run in order to print the user's desired results to the screen in the textArea below the
		 * search bar.
		 */
		searchButton.setBounds(427, 476, 79, 24);
		getContentPane().add(searchButton);
		
		Button invtPage = new Button("Employee Tools");
		invtPage.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (counter == 0) {
					counter++;
					Authentication auth = new Authentication(/*arg*/ connection);
					auth.setSize(600, 400);
					auth.setVisible(true);
					dispose();
				}
				else {
					EmployeePage empPage;
					try {
						empPage = new EmployeePage(/*arg*/ connection);
						empPage.setSize(1120, 1000);
						empPage.setVisible(true);
						dispose();
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					
				}
			}
		});
		invtPage.setBounds(1004, 0, 97, 24);
		getContentPane().add(invtPage);
		
		Button frntPage = new Button("Front Page");
		frntPage.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				FrontPage frPg;
				try {
					frPg = new FrontPage(connection);
					frPg.setSize(1120, 1000);
					frPg.setVisible(true);
					dispose();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				
			}
		});
		
		/*^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^
		 * PAGE SWITCHING
		 * 
		 * The above section of code gives the user the ability to switch between pages of the database and 
		 * access its different functionality. If the user is an employee and wants to go to the Employee section
		 * of the program, they can click the "Employee Tools" button and an authentication page will appear.
		 * From here, they simply enter their username and password, and gain access to the Employee page for the
		 * rest of the time the program is running. The Employee user can freely switch between the Front Page and 
		 * the Employee page without having to authenticate each time.
		 * 
		 * The "Front Page" button, if clicked, simply opens a new FrontPage and clears the old one for the user
		 */
		frntPage.setBounds(912, 0, 79, 24);
		getContentPane().add(frntPage);
		
		JLabel lblAreTheArchives = new JLabel("Are the Archives of Babel right for you?");
		lblAreTheArchives.setFont(new Font("Dialog", Font.PLAIN, 20));
		lblAreTheArchives.setBounds(623, 169, 354, 24);
		getContentPane().add(lblAreTheArchives);
		
		textField_1 = new JTextField();
		textField_1.setBounds(623, 235, 354, 22);
		getContentPane().add(textField_1);
		textField_1.setColumns(10);
		
		TextArea textArea_2 = new TextArea();
		textArea_2.setEditable(false);
		textArea_2.setBounds(623, 280, 440, 106);
		getContentPane().add(textArea_2);
		
		Button button = new Button("SEARCH");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (textField.getText() != null) {
					
					String result1 = new String("");
					
					try {
						String sql = "";

							sql = "SELECT Location.Genre AS genre, COUNT(Book.BookID) AS number_of_books" +
								  " FROM Location INNER JOIN Book ON Location.LocationID = Book.LocationID" +
								  " WHERE genre LIKE ?"+
								  " GROUP BY Location.Genre" +
								  " ORDER BY number_of_books DESC, genre DESC;";
							
							PreparedStatement stmt = connection.prepareStatement(sql);
							
							stmt.setString(1, "%" + textField_1.getText() + "%");
							
							final ResultSet res = stmt.executeQuery();
							
							while (res.next()) {
								result1 = "Genre:		" + res.getString("genre") + "\n" +
										  "# of Books:	" + res.getString("number_of_books") + "\n\n" + result1;
							}
							
							textArea_2.setText(result1);
					} catch (SQLException ex) {
						System.out.printf("Error connecting to db: %s%n", ex.getMessage());
						System.exit(0);
					}
				}

			}
		});
		
		button.setBounds(983, 235, 79, 24);
		getContentPane().add(button);
		
		JLabel lblEnterYourFavorite = new JLabel("Enter your favorite genre of books to find out what we have for you:");
		lblEnterYourFavorite.setBounds(623, 206, 440, 16);
		getContentPane().add(lblEnterYourFavorite);
		
		TextArea textArea_1 = new TextArea();
		textArea_1.setEditable(false);
		textArea_1.setBounds(40, 206, 454, 180);
		getContentPane().add(textArea_1);
		
		/*^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^
		 * ARE THE ARCHIVE OF BABEL RIGHT FOR YOU?
		 * 
		 * The above section of code allows the user to search the library by their favorite genre of book. 
		 * The result will show any genres related to their search, along with the number of books the
		 * library has in those genres. This is useful because it allows newcomers to the library to see if 
		 * the Archives of Babel has the types of books they are looking for.
		 */
		
		String popularBooks = new String("");
		
//		try (final Connection connection = DriverManager.getConnection("jdbc:sqlite:" + prgArg)) {
			String sql = "";

				sql = "SELECT Book.Title as book_title, Location.Genre AS book_genre, COUNT(Loan.LoanID) AS number_of_loans" +
					  " FROM Location INNER JOIN Book ON Location.LocationID = Book.BookID" +
					  " INNER JOIN BookLine ON Book.BookID = BookLine.BookID" +
					  " INNER JOIN Loan ON BookLine.LoanID = Loan.LoanID" +
					  " GROUP BY Book.BookID" +
					  "	HAVING COUNT(Loan.LoanID) > 2" +
					  "	ORDER BY COUNT(Loan.LoanID) ASC, Book.Title DESC;";
				
				PreparedStatement stmt = connection.prepareStatement(sql);
				
				final ResultSet res = stmt.executeQuery();
				
				while (res.next()) {
					popularBooks = "Title:		" + res.getString("book_title") + "\n" +
							  	   "Genre:		" + res.getString("book_genre") + "\n" +
							  	   "Recent Loans:	" + res.getString("number_of_loans") + "\n\n" + popularBooks;
				}
				
				textArea_1.setText(popularBooks);
/*		} catch (SQLException ex) {
			System.out.printf("Error connecting to db: %s%n", ex.getMessage());
			System.exit(0);
		}*/
		
		Label label = new Label("Most Borrowed Books");
		label.setFont(new Font("Dialog", Font.PLAIN, 20));
		label.setBounds(40, 169, 205, 24);
		getContentPane().add(label);

		
		/*^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^
		 * SHOW POPULAR BOOKS
		 * 
		 * The above section of code produces the results of a query to the database AS the page is being created.
		 * It requires NO input from the user. However, the query represents a useful function of the database as 
		 * the query returns which books have been loaned out the most recently. This would allow members of the 
		 * library to see which books are most popular and then maybe they could loan them out for themselves.
		 */
	}
}
