package gui;

import org.bson.Document;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.Serial;

import javax.swing.*;

public class ActivityPage extends JFrame{
	
	
	@Serial
	private static final long serialVersionUID = 8050119630303986305L;
	
	private final JPanel activityPage = new JPanel(null);
	private final String username;
	
	private final CardLayout cardLayout;
	private final JPanel mainPanel;
	
	public void setDimension(int x, int y) {
		setPreferredSize(new Dimension(x, y));
	}
	
	public ActivityPage(Point location, String username) {
		
		this.username = username;
		JFrame frame = new JFrame();
		frame = this;

		cardLayout = new CardLayout();
		mainPanel = new JPanel(cardLayout);
		createActivityPage();
		
		this.setContentPane(mainPanel);
		mainPanel.add(activityPage, "home");
		
		ActivityLog log = new ActivityLog(cardLayout, getUsername());
		log.log(mainPanel, frame);

		Tasks task = new Tasks(cardLayout, getUsername());
		task.createTask(mainPanel, frame);
		

		setLocation(location);
		setDimension(500, 500);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		pack();
		revalidate();
	}

	private void createActivityPage() {
		
		
		activityPage.setBackground(Color.white);

		setTitle("Activity Page");
		
		toActivityLog();
		toTaskPage();
		
		pack();
		
	}
	

	private String getUsername() {
		return username;
	}
	
	private void toActivityLog() {
		
		JLabel activityLog = new JLabel(new ImageIcon("/Users/braydenwong/git/Pomodoro/images/act.png"));
		
		activityLog.setBounds(100, 25, 300, 50);
		
		activityLog.addMouseListener(new MouseListener() {

			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub

				setDimension(500, 400);
				pack();
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

	private void toTaskPage() {

		JLabel taskLog = new JLabel(new ImageIcon("/Users/braydenwong/git/Pomodoro/images/pomo.png"));
		taskLog.setBounds(100, 95, 300, 50);
		taskLog.addMouseListener(new MouseListener() {

			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub

				setDimension(500, 350);
				pack();
				cardLayout.show(mainPanel, "tasks");

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
				taskLog.setCursor((Cursor.getPredefinedCursor(Cursor.HAND_CURSOR)));
			}

			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub

				taskLog.setCursor(Cursor.getDefaultCursor());
			}
		});
		activityPage.add(taskLog);
	}


	public static void main(String[] args) {
	
		ActivityPage activity = new ActivityPage(new Point(500,500), "darkSlayer25");
		activity.setVisible(true);
	}
}
