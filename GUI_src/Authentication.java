import java.awt.BorderLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import java.awt.Button;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Arrays;
import java.awt.event.ActionEvent;

/**
 * GUI Application for the Archives of Babel Library Database
 * 
 * NAME: Ryan Reid
 * SECTION: COMP-2670-01
 * @author reidr
 */

public class Authentication extends JFrame{
	private JTextField txtUsername;
	private JPasswordField passwordField;
	private static int counter = 0;
	private final static String USER = "library";
	
	public Authentication (/*String arg*/ Connection connection) {
		
		setTitle("Archives of Babel - Login");
		getContentPane().setLayout(null);
		
		txtUsername = new JTextField();
		txtUsername.setBounds(107, 36, 219, 22);
		getContentPane().add(txtUsername);
		txtUsername.setColumns(10);
		
		passwordField = new JPasswordField();
		passwordField.setBounds(107, 66, 219, 22);
		getContentPane().add(passwordField);
		
		Button button = new Button("LOGIN");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (txtUsername.getText().equals(USER)) {
					if (Arrays.equals(passwordField.getPassword(), new char[] { 'b', 'a', 'b', 'e', 'l' })) {
						counter++;
						try {
							EmployeePage empPage = new EmployeePage(connection);
							empPage.setSize(1120, 1000);
							empPage.setVisible(true);
							dispose();
						} catch (SQLException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						
					}
				}
				else {
					txtUsername.setText("Invalid username/password");
					passwordField.setText("");				}

			}
		});
		button.setBounds(107, 98, 79, 24);
		getContentPane().add(button);
		
		JLabel lblUsername = new JLabel("Username:");
		lblUsername.setFont(new Font("Dialog", Font.PLAIN, 13));
		lblUsername.setBounds(12, 39, 93, 16);
		getContentPane().add(lblUsername);
		
		JLabel lblPassword = new JLabel("Password:");
		lblPassword.setFont(new Font("Dialog", Font.PLAIN, 13));
		lblPassword.setBounds(12, 69, 87, 16);
		getContentPane().add(lblPassword);
		
		JTextField user = new JTextField("Username");
		JPasswordField pass = new JPasswordField("Password");
		JButton login = new JButton("Login");
	}
}

/*^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^
 * ENTERING USERNAME AND PASSWORD
 * 
 * This class creates the authentication window, which is a small window with just a username textField, and a 
 * password textField for an employee to type in their credentials and gain access to the Employee Tools. As of 
 * right now, the only username and password combination that will work for testing purposes is 'library' and 
 * 'babel'. Upon successful authentication, a new EmployeePage is opened up.
 */