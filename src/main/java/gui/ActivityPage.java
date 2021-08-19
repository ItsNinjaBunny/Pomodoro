package gui;

import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class ActivityPage extends JFrame{
	
	
	private static final long serialVersionUID = 8050119630303986305L;
	
	private Point location;
	private String username;
	private CardLayout cardLayout;
	private JPanel mainPanel;
	
	
	public ActivityPage(Point location, String username) {
		
		cardLayout = new CardLayout();
		mainPanel = new JPanel(cardLayout);
		getContentPane().add(mainPanel);
		createActivityPage();
		
		this.username = username;
		
		this.location = location;
		setLocation(this.location);
		setPreferredSize(new Dimension(500, 500));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		pack();
		revalidate();
	}

	private void createActivityPage() {
		
		JPanel activityPage = new JPanel(null);
		
		setTitle("Activity Page");
		JButton button = new JButton("pomodoro");
		button.setBounds(100, 100, 75, 20);
		button.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				location = getLocation();
				Timer timer = new Timer(location, username);
				timer.setVisible(true);
				
				dispose();
				
			}
		});
		activityPage.add(button);
		
		pack();
		mainPanel.add(activityPage, "activity");
		cardLayout.show(mainPanel, "activity");
	}
	

	public String getUsername() {
		return username;
	}
}
