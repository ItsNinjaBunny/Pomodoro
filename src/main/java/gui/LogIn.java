package gui;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class LogIn extends JFrame{
	
	private static final long serialVersionUID = 1L;
	private CardLayout cardLayout; 
	private JPanel mainPanel;

	public LogIn() {
		
		cardLayout = new CardLayout();
		mainPanel = new JPanel(cardLayout);
		loginPane();
		
		this.add(mainPanel);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setPreferredSize(new Dimension(500, 500));
		this.pack();
		this.revalidate();
	}
	
	private void loginPane() {
		JPanel login = new JPanel();
		login.setLayout(null);
		login.setPreferredSize(new Dimension(500, 500));
		
		JLabel createAcc = new JLabel("create an account");
		createAcc.setBounds(250, 50, 130, 20);
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
		
		//user fields
		JLabel username = new JLabel("username: ");
		JTextField userText = new JTextField();
		userText.setBounds(110, 50, 100, 22);
		username.setBounds(20, 50, 90, 20);
		
		//password fields
		JLabel password = new JLabel("password: ");
		JPasswordField passwordText = new JPasswordField();
		passwordText.setBounds(110, 90, 100, 22);
		password.setBounds(20, 90, 90, 20);
		
		login.add(username);
		login.add(password);
		login.add(passwordText);
		login.add(userText);
		

		mainPanel.add(login, "login");
		cardLayout.show(mainPanel, "login");
		this.revalidate();
	}
	
	private void createAcc() {
		JPanel createAcc = new JPanel();
		createAcc.setLayout(null);
		
		mainPanel.add(createAcc, "createAccount");
	}
	
	public static void main(String[] args) {
		LogIn menu = new LogIn();
		menu.setVisible(true);
		
	}
}
