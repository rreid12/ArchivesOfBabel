import javax.swing.JFrame;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.TextField;
import java.awt.Label;
import java.awt.Button;
import java.awt.TextArea;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.awt.event.ActionEvent;
import java.awt.Scrollbar;
import javax.swing.JComboBox;
import java.awt.Checkbox;
import java.awt.CheckboxGroup;

/**
 * GUI Application for the Archives of Babel Library Database
 * 
 * NAME: Ryan Reid
 * SECTION: COMP-2670-01
 * 
 * @author reidr
 */

/*
 * Creates the page for the employees to use to query the database to assist in
 * carrying out their jobs. The class adds in all the buttons, textFields, 
 * textAreas, etc, connects to the database through the JDBC API, queries the
 * database, and returns the results to the screen.
 */
public class EmployeePage extends JFrame {
	
	private String prgArg;
	private final static String RETURN_INFO = "Return Information";
	private final static String LOAN_INFO = "Loan Information";
	private TextField textField_1;
	
	
	public EmployeePage(/*String arg,*/ Connection connection) throws SQLException {
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//prgArg = arg;
		
		setTitle("Archives of Babel");
		getContentPane().setLayout(null);
		
		JLabel lblInventory = new JLabel("Employee Toolkit");
		lblInventory.setFont(new Font("Dialog", Font.BOLD, 30));
		lblInventory.setBounds(428, 63, 255, 39);
		getContentPane().add(lblInventory);
		
		JLabel lblArchivesOfBabel = new JLabel("Archives of Babel - Library Database");
		lblArchivesOfBabel.setFont(new Font("Dialog", Font.PLAIN, 25));
		lblArchivesOfBabel.setBounds(352, 115, 417, 33);
		getContentPane().add(lblArchivesOfBabel);
		
		Button button_3 = new Button("Employee Tools");
		button_3.setBounds(991, 0, 101, 24);
		getContentPane().add(button_3);
		
		Button button_4 = new Button("Front Page");
		button_4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//FrontPage frntPage;
				try {
					FrontPage frntPage = new FrontPage( connection);
					frntPage.setSize(1120, 1000);
					frntPage.setVisible(true);
					dispose();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
			}
		});
		button_4.setBounds(906, 0, 79, 24);
		getContentPane().add(button_4);
		
		JLabel lblLateReturners = new JLabel("Members' Late Returns");
		lblLateReturners.setFont(new Font("Dialog", Font.PLAIN, 20));
		lblLateReturners.setBounds(784, 417, 212, 24);
		getContentPane().add(lblLateReturners);
		
		TextArea textArea = new TextArea();
		textArea.setEditable(false);
		textArea.setBounds(704, 469, 363, 433);
		getContentPane().add(textArea);
		
		Label label_2 = new Label("Total Books in AoB:");
		label_2.setFont(new Font("Dialog", Font.PLAIN, 15));
		label_2.setBounds(704, 274, 197, 24);
		getContentPane().add(label_2);
		
		Label label_3 = new Label("Total DVDs in AoB:");
		label_3.setFont(new Font("Dialog", Font.PLAIN, 15));
		label_3.setBounds(704, 304, 197, 24);
		getContentPane().add(label_3);
		
		Label label = new Label("New label");
		label.setFont(new Font("Dialog", Font.PLAIN, 15));
		label.setBounds(704, 334, 224, 24);
		getContentPane().add(label);
		
		Label label_4 = new Label("New label");
		label_4.setFont(new Font("Dialog", Font.PLAIN, 15));
		label_4.setBounds(704, 363, 224, 24);
		getContentPane().add(label_4);
		
		/*^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^
		 * ADD IN BUTTONS, TEXTFIELDS, TEXTAREAS, ETC.
		 * 
		 * The above code simply adds in MOST of the required buttons and textFields for the page.
		 */
		
		
		String lateMembers = new String("");
		
//		try (final Connection connection = DriverManager.getConnection("jdbc:sqlite:" + prgArg)) {
			String sqlLateMembers = "";
			String sqlTotalBooks = "";
			String sqlTotalDVDs = "";
			String sqlLoanFees = "";
			String sqlLateFees = "";

				sqlLateMembers = "SELECT Member.MemberID AS mem_id, Member.FirstName AS f_name, Member.LastName AS l_name, sum(Return.DaysLate) AS days_late, Member.Street AS street, Member.City AS city, Member.State AS state, Member.ZipCode AS zip" +
					  " FROM Member INNER JOIN Return ON Member.MemberID = Return.MemberID" +
					  " WHERE Return.LateFee > 0" +
					  " GROUP BY Member.MemberID" +
					  " ORDER BY days_late ASC, Member.LastName DESC, Member.MemberID DESC;";
				
				sqlTotalBooks = "SELECT SUM(Book.NumOfCopies) AS total_books" +
								" FROM Book";
				
				sqlTotalDVDs = "SELECT SUM(DVD.NumOfCopies) AS total_dvds" +
							   " FROM DVD";
				
				sqlLoanFees = "SELECT printf(\"%.2f\", SUM(Loan.LoanFee)) AS loan_fees" +
							  " FROM Loan" +
							  " WHERE Loan.LoanFee > 0.01";
				
				sqlLateFees = "SELECT printf(\"%.2f\", SUM(Return.LateFee)) AS late_fees" +
						  	  " FROM Return" +
						      " WHERE Return.LateFee > 0.01";
				
				PreparedStatement stmt = connection.prepareStatement(sqlLateMembers);
				PreparedStatement stmtBooks = connection.prepareStatement(sqlTotalBooks);
				PreparedStatement stmtDVDs = connection.prepareStatement(sqlTotalDVDs);
				PreparedStatement stmtLoanFees = connection.prepareStatement(sqlLoanFees);
				PreparedStatement stmtLateFees = connection.prepareStatement(sqlLateFees);
				
				final ResultSet res = stmt.executeQuery();
				final ResultSet resBooks = stmtBooks.executeQuery();
				final ResultSet resDVDs = stmtDVDs.executeQuery();
				final ResultSet resLoanFees = stmtLoanFees.executeQuery();
				final ResultSet resLateFees = stmtLateFees.executeQuery();
				
				while (res.next()) {
					lateMembers = //"MemberID:	" + res.getString("mem_id") + "\n" +
							  	  "Name:		" + res.getString("f_name") + " " + res.getString("l_name") + "\n" +
							  	  "Days Late:		" + res.getString("days_late") + "\n" +
							  	  "Address:		" + res.getString("street") + "\n\t\t" + res.getString("city") + ", " + res.getString("state") + " " + res.getString("zip") + "\n\n" + lateMembers;
				}
				
				textArea.setText(lateMembers);
				label_2.setText("Total Books in AoB: " + resBooks.getString("total_books"));
				label_3.setText("Total DVDs in AoB: " + resDVDs.getString("total_dvds"));
				label.setText("Loan Fees Paid To Date: $" + resLoanFees.getString("loan_fees"));
				label_4.setText("Late Fees Paid To Date: $" + resLateFees.getString("late_fees"));
				
				
/*		} catch (SQLException ex) {
			System.out.printf("Error connecting to db: %s%n", ex.getMessage());
			System.exit(0);
		}*/
		
		/*^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^
		 * 5 SHORT QUERIES
		 * 	
		 * Above, the program connects to the database and performs 5 short queries to display information about the library
		 * to the screen for the employee. The small queries are automatically run when the page is opened and require 
		 * no input from the employee user. The information is supposed to be useful to them for them to do their job, 
		 * particularly for librarians.
		 * 
		 * The queries show: total amount of books in the library, total amount of DVDs in the library, total loan fees paid,
		 * total late fees paid, and finally the members of the library who frequently return books late.
		 * 
		 */
		
		Label label_1 = new Label("Transaction Search");
		label_1.setFont(new Font("Dialog", Font.BOLD, 20));
		label_1.setBounds(35, 274, 319, 24);
		getContentPane().add(label_1);
		
		TextArea textArea_1 = new TextArea();
		textArea_1.setEditable(false);
		textArea_1.setBounds(40, 469, 558, 217);
		getContentPane().add(textArea_1);
		
		Checkbox checkbox = new Checkbox("Employee ID");
		checkbox.setVisible(true);
		checkbox.setBounds(155, 393, 108, 24);
		getContentPane().add(checkbox);
		
		Checkbox checkbox_1 = new Checkbox("Member Last Name");
		checkbox_1.setVisible(true);
		checkbox_1.setBounds(155, 417, 133, 24);
		getContentPane().add(checkbox_1);
		
		Label label_5 = new Label("Search By:");
		label_5.setVisible(true);
		label_5.setFont(new Font("Dialog", Font.PLAIN, 15));
		label_5.setBounds(35, 393, 79, 24);
		getContentPane().add(label_5);
		
		Label label_6 = new Label("(Choose ONE)");
		label_6.setVisible(true);
		label_6.setBounds(155, 439, 98, 24);
		getContentPane().add(label_6);
		
		Button button = new Button("SEARCH");
		
		TextField textField = new TextField();
		textField.setBounds(35, 363, 563, 24);
		getContentPane().add(textField);
		
		JComboBox comboBox = new JComboBox();
		comboBox.setToolTipText("");
		comboBox.setBounds(35, 320, 155, 22);
		getContentPane().add(comboBox);
		comboBox.setModel(new DefaultComboBoxModel(new String[] {LOAN_INFO, RETURN_INFO}));

		button.setBounds(519, 393, 79, 24);
		getContentPane().add(button);
		
		JLabel lblAddABookdvd = new JLabel("Add a Book to AoB");
		lblAddABookdvd.setFont(new Font("Dialog", Font.BOLD, 20));
		lblAddABookdvd.setBounds(35, 704, 237, 24);
		getContentPane().add(lblAddABookdvd);
		
		Label label_7 = new Label("Title:");
		label_7.setBounds(35, 745, 70, 24);
		getContentPane().add(label_7);
		
		Label label_8 = new Label("# of Copies:");
		label_8.setBounds(35, 775, 70, 24);
		getContentPane().add(label_8);
		
		Label label_9 = new Label("Cost:");
		label_9.setBounds(35, 805, 70, 24);
		getContentPane().add(label_9);
		
		Label label_10 = new Label("Author First Name:");
		label_10.setBounds(35, 865, 120, 24);
		getContentPane().add(label_10);
		
		Label label_11 = new Label("Author Last Name:");
		label_11.setBounds(35, 895, 120, 24);
		getContentPane().add(label_11);
		
		Label label_12 = new Label("Genre:");
		label_12.setBounds(35, 835, 70, 24);
		getContentPane().add(label_12);
		
		TextField textField_6 = new TextField();
		textField_6.setBounds(155, 895, 133, 24);
		getContentPane().add(textField_6);
		
		TextField textField_5 = new TextField();
		textField_5.setBounds(155, 865, 133, 24);
		getContentPane().add(textField_5);
		
		TextField textField_4 = new TextField();
		textField_4.setBounds(155, 835, 133, 24);
		getContentPane().add(textField_4);
		
		TextField textField_3 = new TextField();
		textField_3.setBounds(155, 805, 133, 24);
		getContentPane().add(textField_3);
		
		TextField textField_2 = new TextField();
		textField_2.setBounds(155, 775, 133, 24);
		getContentPane().add(textField_2);
		
		TextField textField_1 = new TextField();
		textField_1.setBounds(155, 745, 133, 24);
		getContentPane().add(textField_1);
		
		Button button_1 = new Button("ADD BOOK");
		button_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				String sqlAddBook = "";
				String sqlAddLocation = "";
				String sqlAddAuthor = "";

				if (textField_6 != null && textField_5 != null && textField_4 != null && textField_3 != null
						&& textField_2 != null && textField_1 != null) {

					try /*(final Connection connection = DriverManager.getConnection("jdbc:sqlite:" + prgArg))*/ {

						connection.setAutoCommit(true);

						sqlAddBook = "INSERT INTO Book"
								+ " (BookID, Title, NumOfCopies, LocationID, BookCost, AuthorID)"
								+ " VALUES ((select MAX(Book.bookid) from book)+1, ? , ? , (select MAX(Location.LocationId) from Location)+1 , ? , (SELECT MAX(Author.AuthorID) FROM Author) +1)";

						sqlAddLocation = "INSERT INTO Location" + " (LocationID, Genre, Section, RowNumber, Shelf)"
								+ " VALUES ((SELECT MAX(Location.LocationID)+1 FROM Location), ? , 1 , 1 , 1 )";

						sqlAddAuthor = "INSERT INTO Author" + " (AuthorID, FirstName, LastName)"
								+ " Values ((SELECT MAX(Author.AuthorID)+1 FROM Author), ? , ? )";

						// ADD NEW BOOK TO BOOK TABLE
						PreparedStatement stmtBook = connection.prepareStatement(sqlAddBook);

						stmtBook.setString(1, textField_1.getText());
						stmtBook.setString(2, textField_2.getText());
						stmtBook.setString(3, textField_3.getText());

						stmtBook.executeUpdate();

						// ADD CORRESPONDING INFO TO LOCATION TABLE (FOREIGN
						// KEY)
						PreparedStatement stmtLocation = connection.prepareStatement(sqlAddLocation);

						stmtLocation.setString(1, textField_4.getText());

						stmtLocation.executeUpdate();

						// ADD CORRESPONDING INFORMATION TO AUTHOR TABLE
						// (FOREIGN KEY)
						PreparedStatement stmtAuthor = connection.prepareStatement(sqlAddAuthor);

						stmtAuthor.setString(1, textField_5.getText());
						stmtAuthor.setString(2, textField_6.getText());

						stmtAuthor.executeUpdate();
					} catch (SQLException ex) {
						System.out.printf("Error connecting to db: %s%n", ex.getMessage());
						System.exit(0);
					}

				}

			}
		});
		
		/*^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^	
		 * The above event is triggered when the 'ADD BOOK' button is pushed and all fields 
		 * are completely filled out. Once this happens, the data entered in the text fields
		 * are added to the database in the Book, Author, and Location tables. This is very 
		 * useful for employees when they need to add new books to the library.
		 */
		button_1.setBounds(209, 919, 79, 24);
		getContentPane().add(button_1);
		
		Label label_13 = new Label("Add a DVD to AoB");
		label_13.setFont(new Font("Dialog", Font.BOLD, 20));
		label_13.setBounds(364, 704, 185, 24);
		getContentPane().add(label_13);
		
		Label label_14 = new Label("Title:");
		label_14.setBounds(352, 745, 54, 24);
		getContentPane().add(label_14);
		
		Label label_15 = new Label("Producer:");
		label_15.setBounds(352, 805, 62, 24);
		getContentPane().add(label_15);
		
		Label label_16 = new Label("Cost:");
		label_16.setBounds(352, 835, 39, 24);
		getContentPane().add(label_16);
		
		Label label_17 = new Label("Genre:");
		label_17.setBounds(352, 865, 54, 24);
		getContentPane().add(label_17);
		
		Label label_18 = new Label("# of Copies");
		label_18.setBounds(352, 775, 70, 24);
		getContentPane().add(label_18);
		
		TextField textField_7 = new TextField();
		textField_7.setBounds(428, 745, 133, 24);
		getContentPane().add(textField_7);
		
		TextField textField_9 = new TextField();
		textField_9.setBounds(428, 805, 133, 24);
		getContentPane().add(textField_9);
		
		TextField textField_10 = new TextField();
		textField_10.setBounds(428, 835, 133, 24);
		getContentPane().add(textField_10);
		
		TextField textField_8 = new TextField();
		textField_8.setBounds(428, 775, 133, 24);
		getContentPane().add(textField_8);
		
		TextField textField_11 = new TextField();
		textField_11.setBounds(428, 865, 133, 24);
		getContentPane().add(textField_11);
		
		Button button_2 = new Button("ADD DVD");
		button_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (textField_7 != null && textField_8 != null && textField_9 != null && textField_10 != null && textField_11 != null) {
					
					String sqlAddDVD = "";
					String sqlAddLocation = "";
					try {
						
						sqlAddDVD = "INSERT INTO DVD" +
									" (Title, NumOfCopies, DVDID, Producer, DVDCost, LocationID)" +
									" VALUES (? , ? , (SELECT MAX(DVDID)+1 FROM DVD), ? , ? , (SELECT MAX(LocationID)+1 FROM Location))";
						
						sqlAddLocation = "INSERT INTO Location" +
										 " (LocationID, Genre, Section, RowNumber, Shelf)" +
										 " VALUES ((SELECT MAX(Location.LocationID)+1 FROM Location), ? , 2 , 2 , 2	)";
						
						PreparedStatement stmtBook = connection.prepareStatement(sqlAddDVD);
						
						stmtBook.setString(1, textField_7.getText());
						stmtBook.setString(2, textField_8.getText());
						stmtBook.setString(3, textField_9.getText());
						stmtBook.setString(4, textField_10.getText());
						
						stmtBook.executeUpdate();
						
						
						PreparedStatement stmtLocation = connection.prepareStatement(sqlAddLocation);
						
						stmtLocation.setString(1, textField_11.getText());
						
						stmtLocation.executeUpdate();
						
						
					} catch (SQLException ex) {
						System.out.printf("Error connecting to db: %s%n", ex.getMessage());
						System.exit(0);
					} 
				}
			}
		});
		
		/*^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^	
		 * The above event is triggered when the 'ADD DVD' button is pushed and all fields 
		 * are completely filled out. Once this happens, the data entered in the text fields
		 * are added to the database in the DVD and Location tables. This is very 
		 * useful for employees when they need to add new DVDs to the library.
		 */
		button_2.setBounds(482, 919, 79, 24);
		getContentPane().add(button_2);

		
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String sql = "";
				String result = "";
			
				try{
					if (comboBox.getSelectedItem() == LOAN_INFO) {
						
						if (checkbox.getState()) {
							
							sql =  "SELECT Book.Title AS Title, Author.FirstName AS Author_First_Name, Author.LastName AS Author_Last_Name, Loan.LoanDate AS Loan_Date, Member.FirstName AS Member_First_Name, Member.LastName AS Member_Last_Name" +
								   " FROM Book INNER JOIN Author ON Book.AuthorID = Author.AuthorID" + 
								   " INNER JOIN BookLine ON Book.BookID = BookLine.BookID" +
								   " INNER JOIN Loan ON BookLine.LoanID = Loan.LoanID" +
								   " INNER JOIN Member ON Loan.MemberID = Member.MemberID" +
								   " INNER JOIN Employee ON Loan.LibrarianID = Employee.EmployeeID" +
								   " WHERE Employee.EmployeeID = ?" +
								   " ORDER BY Loan.LoanDate DESC, Author.LastName DESC, Book.Title DESC";
							
							PreparedStatement stmt = connection.prepareStatement(sql);
							
							stmt.setString(1, textField.getText());
							
							final ResultSet res = stmt.executeQuery();
							
							while (res.next()) {
								result = "Book Title: 	" + res.getString("Title") + "\n" + 
										 "Author: 		" + res.getString("Author_First_Name") + " " + res.getString("Author_Last_Name") + "\n" +
										 "Loan Date: 	" + res.getString("Loan_Date") + "\n" +
										 "Member:		" + res.getString("Member_First_Name") + " " + res.getString("Member_Last_Name") + "\n\n" + result;
							}
							
							textArea_1.setText(result);
							
						}
						else if (checkbox_1.getState()) {
							//search by member last name
							sql = "SELECT Book.Title AS Title, Author.FirstName AS Author_First_Name, Author.LastName AS Author_Last_Name, Loan.LoanDate AS Loan_Date, Member.FirstName AS Member_First_Name, Member.LastName AS Member_Last_Name" +
								  " FROM Book INNER JOIN Author ON Book.AuthorID = Author.AuthorID" + 
								  " INNER JOIN BookLine ON Book.BookID = BookLine.BookID" +
								  " INNER JOIN Loan ON BookLine.LoanID = Loan.LoanID" + 
								  " INNER JOIN Member ON Loan.MemberID = Member.MemberID" +
								  " WHERE Member.LastName LIKE ?" +
								  " ORDER BY Loan.LoanDate ASC";
							
							PreparedStatement stmt = connection.prepareStatement(sql);
							
							stmt.setString(1, "%" + textField.getText() + "%");
							
							final ResultSet res = stmt.executeQuery();
							
							while (res.next()) {
								result = "Book Title: 	" + res.getString("Title") + "\n" + 
										 "Author: 		" + res.getString("Author_First_Name") + " " + res.getString("Author_Last_Name") + "\n" +
										 "Loan Date: 	" + res.getString("Loan_Date") + "\n" +
										 "Member:		" + res.getString("Member_First_Name") + " " + res.getString("Member_Last_Name") + "\n\n" + result;
							}
							
							textArea_1.setText(result);
						}

					}
					else if (comboBox.getSelectedItem() == RETURN_INFO) {
						
						if (checkbox.getState()) {
							//search by employee ID
							sql = "SELECT Book.Title AS Title, Author.FirstName AS Author_First_Name, Author.LastName AS Author_Last_Name, Return.DaysLate AS Days_Late, Return.LateFee AS Late_Fee, Member.FirstName AS Member_First_Name, Member.LastName AS Member_Last_Name" +
								  " FROM Book INNER JOIN Author ON Book.AuthorID = Author.AuthorID" + 
								  " INNER JOIN BookLine ON Book.BookID = BookLine.BookID" +
								  " INNER JOIN Return ON BookLine.ReturnID = Return.ReturnID" +
								  "	INNER JOIN Member ON Return.MemberID = Member.MemberID" +
								  " INNER JOIN Employee ON Return.LibrarianID = Employee.EmployeeID" +
								  " WHERE Employee.EmployeeID = ?" +
								  " ORDER BY Book.Title ASC, Author.LastName DESC, Return.DaysLate DESC";
							
							PreparedStatement stmt = connection.prepareStatement(sql);
							
							stmt.setString(1, textField.getText());
							
							final ResultSet res = stmt.executeQuery();
							
							while (res.next()) {
								result = "Book Title: 	" + res.getString("Title") + "\n" + 
										 "Author: 		" + res.getString("Author_First_Name") + " " + res.getString("Author_Last_Name") + "\n" +
										 "Days Late: 	" + res.getString("Days_Late") + "\n" +
										 "Late Fee:		" + res.getString("Late_Fee") + "\n" +
										 "Member:		" + res.getString("Member_First_Name") + " " + res.getString("Member_Last_Name") + "\n\n" + result;
							}
							
							textArea_1.setText(result);
						}
						else if (checkbox_1.getState()) {
							
							sql = "SELECT Book.Title AS Title, Author.FirstName AS Author_First_Name, Author.LastName AS Author_Last_Name, Return.DaysLate AS Days_Late, Return.LateFee AS Late_Fee, Member.FirstName AS Member_First_Name, Member.LastName AS Member_Last_Name" +
								  "	FROM Book INNER JOIN Author ON Book.AuthorID = Author.AuthorID" +
								  " INNER JOIN BookLine ON Book.BookID = BookLine.BookID" +
								  "	INNER JOIN Return ON BookLine.ReturnID = Return.ReturnID" +
								  "	INNER JOIN Member ON Return.MemberID = Member.MemberID" +
								  "	WHERE Member.LastName LIKE ?" +
								  " ORDER BY Book.Title ASC, Author.LastName DESC, Return.DaysLate DESC";
							
							PreparedStatement stmt = connection.prepareStatement(sql);
							
							stmt.setString(1, "%" + textField.getText() + "%");
							
							final ResultSet res = stmt.executeQuery();
							
							while (res.next()) {
								result = "Book Title: 	" + res.getString("Title") + "\n" + 
										 "Author: 		" + res.getString("Author_First_Name") + " " + res.getString("Author_Last_Name") + "\n" +
										 "Days Late: 	" + res.getString("Days_Late") + "\n" +
										 "Late Fee:		" + res.getString("Late_Fee") + "\n" +
										 "Member:		" + res.getString("Member_First_Name") + " " + res.getString("Member_Last_Name") + "\n\n" + result;
							}
							
							textArea_1.setText(result);
							
						}
					}
					}  catch (SQLException E) {
						System.out.printf("Error connecting to db: %s%n", E.getMessage());
						System.exit(0);
					}
			}
		});
		/* ^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^
		 * This event is triggered from the employee page when either loan or return information is searched for 
		 * by EmployeeID or by Member Last Name. This query is useful because it gives employees the power to 
		 * search for transactional information, either for internal use, or for the benefit of the members of the
		 * library.  
		 */
	}
}


