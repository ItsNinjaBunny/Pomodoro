package gui;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.*;

import javax.swing.*;

import org.bson.Document;

import com.mongodb.BasicDBObject;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;

import mongoDB.Mongo;

import static javax.swing.UIManager.*;

public class LogIn extends JFrame{
	
	private static final long serialVersionUID = 1L;
	private CardLayout cardLayout; 
	private JPanel mainPanel;
	private Mongo mongo = new Mongo();
	private JButton go = new JButton("go");
	private JButton reset = new JButton("reset");
	private Point location = new Point();

	public LogIn() {
		
		cardLayout = new CardLayout();
		mainPanel = new JPanel(cardLayout);
		loginPane();
		
		setResizable(false);
		this.setContentPane(mainPanel);
		this.setTitle("Pomodoro.exe");
		this.setIconImage(Toolkit.getDefaultToolkit().getImage("/Users/braydenwong/git/Pomodoro-App-with-Java-CSE215/src/Main/logo.png"));
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setPreferredSize(new Dimension(400, 175));
		this.setLocationRelativeTo(null);
		this.pack();
		this.revalidate();
		
	}
	
	  
	
	private void loginPane() {
	    
		JPanel login = new JPanel();
		login.setLayout(null);
		
		login.setPreferredSize(new Dimension(400, 175));
		
		JLabel createAcc = new JLabel("create an account");
		createAcc.setBounds(250, 30, 130, 20);
		createAcc.setForeground(Color.decode("#0645AD"));
		createAcc.addMouseListener(new MouseListener() {

			@Override
			public void mouseClicked(MouseEvent e) {
				createAcc();
				cardLayout.show(mainPanel, "createAccount");
			}

			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub
				createAcc.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
				
			}

			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub
				createAcc.setCursor(Cursor.getDefaultCursor());
			}
		});
		login.add(createAcc);
		
		JLabel resetPassword = new JLabel("reset password");
		resetPassword.setBounds(250, 60, 110, 20);
		resetPassword.setForeground(Color.decode("#0645AD"));
		resetPassword.addMouseListener(new MouseListener() {

			@Override
			public void mouseClicked(MouseEvent e) {
				resetPassword();
				cardLayout.show(mainPanel, "resetPassword");
			}

			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub
				resetPassword.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
				
			}

			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub
				resetPassword.setCursor(Cursor.getDefaultCursor());
			}
		});
		login.add(resetPassword);
		
		//user fields
		JLabel username = new JLabel("username: ");
		JTextField userText = new JTextField();
		username.setBounds(20, 30, 90, 20);
		userText.setBounds(110, 30, 100, 22);
		
		
		//password fields
		JLabel password = new JLabel("password: ");
		JPasswordField passwordText = new JPasswordField();
		passwordText.setBounds(110, 65, 100, 22);
		password.setBounds(20, 65, 90, 20);
		passwordText.addKeyListener(new KeyListener() {
			@Override
			public void keyTyped(KeyEvent e) {

			}

			@Override
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode() == KeyEvent.VK_ENTER) {
					String username = userText.getText();
					String password = passwordText.getText();

					if(accountExist(username, password)) {
						JOptionPane.showMessageDialog(login, "login successful");
						location = getLocation();
						dispose();

						ActivityPage activityPage = new ActivityPage(location, username);
						activityPage.setVisible(true);
					}
					else {
						JOptionPane.showMessageDialog(mainPanel, "username or password is incorrect");
						userText.setText("");
						passwordText.setText("");
					}
				}
			}

			@Override
			public void keyReleased(KeyEvent e) {

			}
		});

		
		JButton go = new JButton("Login");
		go.setBounds(135, 105, 70, 30);
		go.addActionListener(new ActionListener() {
			
			@SuppressWarnings("deprecation")
			@Override
			public void actionPerformed(ActionEvent e) {
				
				String username = userText.getText();
				String password = passwordText.getText();
				
				if(accountExist(username, password)) {
					JOptionPane.showMessageDialog(login, "login successful");
					location = getLocation();
					dispose();
					
					ActivityPage activityPage = new ActivityPage(location, username);
					activityPage.setVisible(true);
				}
				else {
					JOptionPane.showMessageDialog(mainPanel, "username or password is incorrect");
					userText.setText("");
					passwordText.setText("");
				}
			}
		});
		
		login.add(go);
		login.add(username);
		login.add(password);
		login.add(passwordText);
		login.add(userText);
		

		mainPanel.add(login, "login");
		cardLayout.show(mainPanel, "login");
		this.revalidate();
	}
	
	private void createAcc() {
		JPanel panel = new JPanel();
		panel.setLayout(null);
		
		JButton createAcc = new JButton("Create Account");
		createAcc.setBounds(200, 110, 130, 30);
		panel.add(createAcc);
		
		JButton back = new JButton("back");
		back.setBounds(50, 110, 70, 30);
		panel.add(back);
		
		JLabel username = new JLabel("username: ");
		JTextField userText = new JTextField();
		username.setBounds(50, 10, 90, 20);
		userText.setBounds(140, 10, 200, 22);
		
		/*
		 * JLabel passwordComp = new
		 * JLabel("minimum 8 characters, 1 uppercase, lowercase, numeric value, and symbol"
		 * ); String font = String.valueOf(passwordComp.getFont());
		 * passwordComp.setFont(new Font(font, Font.BOLD, 10));;
		 * passwordComp.setBounds(20, 120, 400, 30); panel.add(passwordComp);
		 */
		
		
		JLabel password = new JLabel("password: ");
		JLabel confirm = new JLabel("confirm: ");
		JPasswordField confirmedPassword = new JPasswordField();
		JPasswordField passwordText = new JPasswordField();
		password.setBounds(50, 45, 90, 22);
		passwordText.setBounds(140, 45, 200, 22);
		confirm.setBounds(50, 80, 90, 22);
		confirmedPassword.setBounds(140, 80, 200, 22);
		confirmedPassword.addKeyListener(new KeyListener() {

			@Override
			public void keyTyped(KeyEvent e) {

			}

			@Override
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode() == (KeyEvent.VK_ENTER)) {
					String password, username, confirmed;
					password = passwordText.getText();
					username = userText.getText();
					confirmed = confirmedPassword.getText();
					if(password.equals(confirmed)) {
						//if(validatePasswordComplexity(password)) {
						if(createAccount(username, password)) {
							cardLayout.show(mainPanel, "login");
							getRootPane().setDefaultButton(go);
							revalidate();
						}
/*					}
					else {
						JOptionPane.showMessageDialog(null, "password doesn't meet password complexity");
						userText.setText("");
						passwordText.setText("");
						confirmedPassword.setText("");
					}*/
					}
					else {
						JOptionPane.showMessageDialog(createAcc, "Passwords are not the same. Please try again.");
						userText.setText("");
						passwordText.setText("");
						confirmedPassword.setText("");
					}

				}

			}

			@Override
			public void keyReleased(KeyEvent e) {

			}
		});
		
		back.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				cardLayout.show(mainPanel, "login");
				getRootPane().setDefaultButton(go);
				revalidate();
			}
			
		});
		createAcc.addActionListener(new ActionListener() {
			
			@SuppressWarnings("deprecation")
			@Override
			public void actionPerformed(ActionEvent e) {
				String password, username, confirmed;
				password = passwordText.getText();
				username = userText.getText();
				confirmed = confirmedPassword.getText();
				if(password.equals(confirmed)) {
					//if(validatePasswordComplexity(password)) {
						if(createAccount(username, password)) {
							cardLayout.show(mainPanel, "login");
							getRootPane().setDefaultButton(go);
							revalidate();
						}
/*					}
					else {
						JOptionPane.showMessageDialog(null, "password doesn't meet password complexity");
						userText.setText("");
						passwordText.setText("");
						confirmedPassword.setText("");
					}*/
				}
				else {
					JOptionPane.showMessageDialog(createAcc, "Passwords are not the same. Please try again.");
					userText.setText("");
					passwordText.setText("");
					confirmedPassword.setText("");
				}
			}
		});

		panel.add(username);
		panel.add(userText);
		panel.add(passwordText);
		panel.add(confirm);
		panel.add(confirmedPassword);
		panel.add(password);
		mainPanel.add(panel, "createAccount");
	}

	private void resetPassword() {
		JPanel passwordReset = new JPanel();
		passwordReset.setLayout(null);
		
		JLabel username = new JLabel("username: ");
		JTextField userText = new JTextField();
		username.setBounds(50, 40, 90, 20);
		userText.setBounds(140, 40, 200, 22);
		passwordReset.add(username);
		passwordReset.add(userText);
		
		JButton back = new JButton("back");
		back.setBounds(50, 100, 80, 30);
		back.addActionListener(new ActionListener() {
			
			@Override 
			public void actionPerformed(ActionEvent e) {
				cardLayout.show(mainPanel, "login");
				getRootPane().setDefaultButton(go);
				revalidate();
			}
		});
		passwordReset.add(back);
		
		reset.setBounds(227, 100, 80, 30);
		passwordReset.add(reset);
		JButton update = new JButton("update");
		update.setBounds(227, 100, 80, 30);
		
		passwordReset.add(update);
		
		JLabel passwordLabel = new JLabel("new password: "); 
		JLabel confirmPassword = new JLabel("confirm: ");
		JPasswordField passwordText = new JPasswordField();
		JPasswordField confirm = new JPasswordField();
		passwordLabel.setVisible(false);
		confirmPassword.setVisible(false);
		passwordText.setVisible(false);
		confirm.setVisible(false);
		
		passwordLabel.setBounds(50, 25, 150, 20);
		confirmPassword.setBounds(50, 60, 150, 20);
		passwordText.setBounds(160, 25, 150, 20);
		confirm.setBounds(160, 60, 150, 20);

		confirm.addKeyListener(new KeyListener() {
			@Override
			public void keyTyped(KeyEvent e) {

			}

			@Override
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode() == KeyEvent.VK_ENTER) {
					String password, username, confirmed;
					password = passwordText.getText();
					username = userText.getText();
					confirmed = confirm.getText();

					if(password.equals(confirmed)) {
						//if(validatePasswordComplexity(password)) {
						updatePassword(username, password);
						JOptionPane.showMessageDialog(passwordReset, "your password was successfully updated");
						cardLayout.show(mainPanel, "login");
						getRootPane().setDefaultButton(go);
						revalidate();
					}
					else {
						JOptionPane.showMessageDialog(passwordReset, "password does not meet password requirements");
						passwordText.setText("");
						confirm.setText("");
					}
				}

			}

			@Override
			public void keyReleased(KeyEvent e) {

			}
		});

		userText.addKeyListener(new KeyListener() {
			@Override
			public void keyTyped(KeyEvent e) {

			}

			@Override
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode() == KeyEvent.VK_ENTER) {
					String name = userText.getText();
					if(doesUsernameExist(name)) {
						//System.out.println("hello");
						username.setVisible(false);
						userText.setVisible(false);

						reset.setVisible(false);;
						update.setVisible(true);

						passwordLabel.setVisible(true);
						confirmPassword.setVisible(true);
						passwordText.setVisible(true);
						confirm.setVisible(true);
						passwordReset.revalidate();
					}
					else {
						userText.setText("");
						passwordText.setText("");
						confirm.setText("");
					}
					passwordReset.revalidate();
				}
			}

			@Override
			public void keyReleased(KeyEvent e) {

			}
		});

		update.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				String password, username, confirmed;
				password = passwordText.getText();
				username = userText.getText();
				confirmed = confirm.getText(); 
				
				if(password.equals(confirmed)) {
					//if(validatePasswordComplexity(password)) {
						updatePassword(username, password);
						JOptionPane.showMessageDialog(passwordReset, "your password was successfully updated");
						cardLayout.show(mainPanel, "login");
						revalidate();
					}
					else {
						JOptionPane.showMessageDialog(passwordReset, "password does not meet password requirements");
						passwordText.setText("");
						confirm.setText("");
					}
				}
			//}
		});

		reset.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				String name = userText.getText();
				if(doesUsernameExist(name)) {
					//System.out.println("hello");
					username.setVisible(false);
					userText.setVisible(false);

					reset.setVisible(false);;
					update.setVisible(true);

					passwordLabel.setVisible(true);
					confirmPassword.setVisible(true);
					passwordText.setVisible(true);
					confirm.setVisible(true);

					/*
					 * JLabel passwordComp = new
					 * JLabel("minimum 8 characters, 1 uppercase, lowercase, numeric value, and symbol"
					 * ); String font = String.valueOf(passwordComp.getFont());
					 * passwordComp.setFont(new Font(font, Font.BOLD, 12));;
					 * passwordComp.setBounds(20, 90, 425, 30); passwordComp.setVisible(true);
					 * passwordReset.add(passwordComp);
					 */

					passwordReset.revalidate();
				}
				else {
					userText.setText("");
					passwordText.setText("");
					confirm.setText("");
				}
				passwordReset.revalidate();
			}
		});
		passwordReset.add(confirmPassword);
		passwordReset.add(passwordLabel);
		passwordReset.add(passwordText);
		passwordReset.add(confirm);
			
		passwordReset.revalidate();
		
		mainPanel.add(passwordReset, "resetPassword");
	}
	
	@SuppressWarnings("deprecation")
	private boolean createAccount(String username, String password) {
		final String dbName = "users";
		
		MongoClient mongoClient = mongo.connectDatabase();
		MongoDatabase database = mongoClient.getDatabase(dbName);
		
		BasicDBObject query = new BasicDBObject("username", username);
		MongoCollection<Document> collection = database.getCollection("credentials");
		
		Document doesExist = new Document();
		doesExist = collection.find(query).first();
		
		//tests to see if the collection in our user name log on cluster exists
		if(doesExist == null) {
			
			int docID = 1;
			int id = 0;
			
			BasicDBObject getID = new BasicDBObject("_id", docID);
			boolean isValid = true;
			Document nextID;
			
			nextID = collection.find(getID).first();
			do {
				
				if(nextID == null) {
					id = docID;
					isValid = false;
					break;
				}
				else {
					nextID.clear();
					getID.clear();
					docID++;
					getID.append("_id", docID);
					nextID = collection.find(getID).first();
					
				}
			}while(isValid);
			
			int index = mongoClient.getDatabaseNames().indexOf(username);
			if(index == -1) {
				MongoDatabase databaseTemp = mongoClient.getDatabase(username);
				databaseTemp.createCollection("logs");
				databaseTemp.createCollection("tasks");
			}
			
			Document insert = new Document("_id", id).append("username", username).append("password", password);
			collection.insertOne(insert);
			JOptionPane.showMessageDialog(mainPanel, "Your account was successfully created");
			mongoClient.close();
			return true;
		}
		else {
			mongoClient.close();
			return false;
		}
		//mongoClient.close();
	}
	
	private boolean doesUsernameExist(String username) {
		
		final String dbName = "users";
		
		MongoClient mongoClient = mongo.connectDatabase();
		MongoDatabase database = mongoClient.getDatabase(dbName);
		
		BasicDBObject query = new BasicDBObject("username", username);
		MongoCollection<Document> collection = database.getCollection("credentials");
		
		Document doesExist = new Document();
		doesExist = collection.find(query).first();
		mongoClient.close();
		//tests to see if the collection in our user name log on cluster exists
		return doesExist != null;
	}
	
	/*
	 * private boolean validatePasswordComplexity(String password) {
	 * 
	 * String regex =
	 * "^(?=.*\\d{1,})(?=.*[a-z]{1,})(?=.*[A-Z]{1,})(?=.*[!@#$%]{1,}).{8,20}$";
	 * return password.matches(regex); }
	 */
	
	private boolean accountExist(String username, String password) {
		
		final String dbName = "users";
		
		MongoClient mongoClient = mongo.connectDatabase();
		MongoDatabase database = mongoClient.getDatabase(dbName);
		MongoCollection<Document> collection = database.getCollection("credentials");
		
		BasicDBObject query = new BasicDBObject("username", username).append("password", password);
		Document result = collection.find(query).first();
		mongoClient.close();
		
		if(result == null) {
			return false;
		}
		else {
			return true;
		}
		
	}
	
	private void updatePassword(String username, String password) {
		
		final String dbName = "users";
		
		MongoClient mongoClient = mongo.connectDatabase();
		MongoDatabase database = mongoClient.getDatabase(dbName);
		MongoCollection<Document> collection = database.getCollection("credentials");

		collection.updateOne(Filters.eq("username", username), Updates.set("password", password));

		mongoClient.close();
	}
	
	public static void main(String[] args) {


		LogIn menu = new LogIn();
		menu.setVisible(true);
	}
}