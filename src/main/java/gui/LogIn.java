package gui;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import org.bson.Document;

import com.mongodb.BasicDBObject;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;

public class LogIn extends JFrame{
	
	private static final long serialVersionUID = 1L;
	private CardLayout cardLayout; 
	private JPanel mainPanel;
	private Mongo mongo = new Mongo();

	public LogIn() {
		
		cardLayout = new CardLayout();
		mainPanel = new JPanel(cardLayout);
		loginPane();
		
		
		this.setContentPane(mainPanel);
		this.setTitle("Pomodoro.exe");
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setBounds(100, 100, 400, 200);
		//this.setPreferredSize(new Dimension(400, 300));
		this.pack();
		this.revalidate();
	}
	
	private void loginPane() {
	    
		JPanel login = new JPanel();
		login.setLayout(null);
		login.setPreferredSize(new Dimension(400, 200));
		
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
		passwordText.setBounds(110, 60, 100, 22);
		password.setBounds(20, 60, 90, 20);
		
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
		createAcc.setBounds(227, 145, 110, 22);		
		panel.add(createAcc);
		
		JButton back = new JButton("back");
		back.setBounds(50, 145, 50, 22);
		panel.add(back);
		
		JLabel username = new JLabel("username: ");
		JTextField userText = new JTextField();
		username.setBounds(50, 40, 90, 20);
		userText.setBounds(140, 40, 200, 22);
		
		
		JLabel password = new JLabel("password: ");
		JLabel confirm = new JLabel("confirm: ");
		JPasswordField confirmedPassword = new JPasswordField();
		JPasswordField passwordText = new JPasswordField();
		password.setBounds(50, 75, 90, 20);
		passwordText.setBounds(140, 75, 200, 22);
		confirm.setBounds(50, 110, 90, 20);
		confirmedPassword.setBounds(140, 110, 200, 22);	
		
		back.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				cardLayout.show(mainPanel, "login");
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
					if(createAccount(username, password)) {
						cardLayout.show(mainPanel, "login");
					}
					else {
						userText.setText("");
						passwordText.setText("");
						confirmedPassword.setText("");
					}
				}
				else {
					JOptionPane.showMessageDialog(null, "Passwords are not the same. Please try again.");
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

	@SuppressWarnings("deprecation")
	private void resetPassword() {
		JPanel passwordReset = new JPanel();
		
		passwordReset.setLayout(null);
		passwordReset.setBounds(100, 100, 400, 200);
		
		JLabel username = new JLabel("username: ");
		JTextField userText = new JTextField();
		username.setBounds(50, 40, 90, 20);
		userText.setBounds(140, 40, 200, 22);
		passwordReset.add(username);
		passwordReset.add(userText);
		
		JButton back = new JButton("back");
		back.setBounds(50, 145, 50, 20);
		back.addActionListener(new ActionListener() {
			
			@Override 
			public void actionPerformed(ActionEvent e) {
				cardLayout.show(mainPanel, "login");
			}
		});
		passwordReset.add(back);
		
		JButton reset = new JButton("reset");
		reset.setBounds(227, 145, 50, 20);
		passwordReset.add(reset);
		getRootPane().setDefaultButton(reset);
		JButton update = new JButton("update");
		update.setBounds(227, 145, 50, 20);
		
		passwordReset.add(update);
		
		JLabel passwordLabel = new JLabel("new password: "); 
		JLabel confirmPassword = new JLabel("confirm: ");
		JPasswordField passwordText = new JPasswordField();
		JPasswordField confirm = new JPasswordField();
		passwordLabel.setVisible(false);
		confirmPassword.setVisible(false);
		passwordText.setVisible(false);
		confirm.setVisible(false);
		
		passwordLabel.setBounds(50, 40, 150, 20);
		confirmPassword.setBounds(50, 75, 150, 20);
		passwordText.setBounds(160, 40, 150, 22);
		passwordText.setBounds(160, 40, 150, 22);
		confirm.setBounds(160, 75, 150, 20);
		
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
					getRootPane().setDefaultButton(update);
					
					passwordLabel.setVisible(true);
					confirmPassword.setVisible(true);
					passwordText.setVisible(true);
					confirm.setVisible(true);
						
					passwordReset.revalidate();
				}
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
					updatePassword(username, password);
				}
			}
		});
		passwordReset.add(confirmPassword);
		passwordReset.add(passwordLabel);
		passwordReset.add(passwordText);
		passwordReset.add(confirm);
			
		passwordReset.revalidate();
		
		mainPanel.add(passwordReset, "resetPassword");
	}
	
	private boolean createAccount(String username, String password) {
		final String logInCluster = "mongodb://user_1:Passw0rd1@pomodoro-shard-00-01.jnyc8.mongodb.net:27017/users?ssl=true&replicaSet=atlas-nqzfih-shard-0&authSource=admin&retryWrites=true";
		final String dbName = "users";
		
		MongoClientURI uri = new MongoClientURI(logInCluster);
		MongoClient mongoClient = new MongoClient(uri);
		MongoDatabase database = mongoClient.getDatabase(dbName);
		
		BasicDBObject query = new BasicDBObject("username", username);
		MongoCollection<Document> collection = database.getCollection("credentials");
		
		Document doesExist = new Document();
		doesExist = collection.find(query).first();
		
		//tests to see if the collection in our user name log on cluster exists
		if(doesExist == null) {
			
			Document insert = new Document("username", username).append("password", password);
			collection.insertOne(insert);
			JOptionPane.showMessageDialog(null, "Your account was successfully created");
			mongoClient.close();
			return true;
		}
		else {
			JOptionPane.showMessageDialog(null, "This username already exists try another one");
			mongoClient.close();
			return false;
		}
		//mongoClient.close();
	}
	
	private boolean doesUsernameExist(String username) {
		
		final String logInCluster = "mongodb://user_1:Passw0rd1@pomodoro-shard-00-01.jnyc8.mongodb.net:27017/users?ssl=true&replicaSet=atlas-nqzfih-shard-0&authSource=admin&retryWrites=true";
		final String dbName = "users";
		
		MongoClientURI uri = new MongoClientURI(logInCluster);
		MongoClient mongoClient = new MongoClient(uri);
		MongoDatabase database = mongoClient.getDatabase(dbName);
		
		BasicDBObject query = new BasicDBObject("username", username);
		MongoCollection<Document> collection = database.getCollection("credentials");
		
		Document doesExist = new Document();
		doesExist = collection.find(query).first();
		mongoClient.close();
		//tests to see if the collection in our user name log on cluster exists
		return doesExist != null;
	}
	
	private void updatePassword(String username, String password) {
		
		final String logInCluster = "mongodb://user_1:Passw0rd1@pomodoro-shard-00-01.jnyc8.mongodb.net:27017/users?ssl=true&replicaSet=atlas-nqzfih-shard-0&authSource=admin&retryWrites=true";
		final String dbName = "users";
		
		MongoClientURI uri = new MongoClientURI(logInCluster);
		MongoClient mongoClient = new MongoClient(uri);
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
