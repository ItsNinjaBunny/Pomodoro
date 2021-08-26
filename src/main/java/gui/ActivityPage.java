package gui;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

public class ActivityPage extends JFrame{
	
	
	private static final long serialVersionUID = 8050119630303986305L;
	
	private JPanel activityPage = new JPanel(null);
	private Point location;
	private String username;
	
	private CardLayout cardLayout;
	private JPanel mainPanel;
	private JPanel logPage;
	
	
	
	public ActivityPage(Point location, String username) {
		
		this.username = username;
		this.location = location;
		
		cardLayout = new CardLayout();
		mainPanel = new JPanel(cardLayout);
		createActivityPage();
		
		this.setContentPane(mainPanel);
		mainPanel.add(activityPage, "home");
		
		ActivityLog log = new ActivityLog(username);
		log.log(mainPanel);
		

		setLocation(this.location);
		setPreferredSize(new Dimension(500, 500));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		pack();
		revalidate();
	}

	private void createActivityPage() {
		
		
		activityPage.setBackground(Color.white);
		setTitle("Activity Page");
		
		toActivityLog();
		
		pack();
		
	}
	

	public String getUsername() {
		return username;
	}
	
	public void toActivityLog() {
		
		JLabel activityLog = new JLabel(new ImageIcon("/Users/braydenwong/git/Pomodoro/images/act.png"));
		
		activityLog.setBounds(100, 25, 300, 50);
		
		activityLog.addMouseListener(new MouseListener() {

			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub
				
				cardLayout.show(mainPanel, "log");
				
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
				activityLog.setCursor((Cursor.getPredefinedCursor(Cursor.HAND_CURSOR)));
			}

			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub
				
				activityLog.setCursor(Cursor.getDefaultCursor());
			}
			
			
		});
		activityPage.add(activityLog);
		
	}
	
	public static void main(String[] args) {
	
		ActivityPage activity = new ActivityPage(new Point(500,500), "darkSlayer25");
		activity.setVisible(true);
	}
}
