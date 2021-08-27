package gui;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.Serial;
import java.net.URL;

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

		Pomodoro timer = new Pomodoro(cardLayout, getUsername());
		timer.createTimer(mainPanel, frame);


		setLocation(location);
		setDimension(500, 500);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		pack();
		revalidate();
	}

	private void createActivityPage() {

		setTitle("Activity Page");

		toActivityLog();
		toTaskPage();
		toTimerPage();
		addBackground();

		pack();

	}

	private void addBackground() {
		URL url = ActivityPage.class.getResource("/resources/homebg.png");
		ImageIcon icon = new ImageIcon(url);

		JLabel background = new JLabel(icon);
		background.setBounds(0, 0, 500, 500);
		activityPage.add(background);
	}


	private String getUsername() {
		return username;
	}

	private void toActivityLog() {


		URL url = ActivityPage.class.getResource("/resources/act.png");
		ImageIcon icon = new ImageIcon(url);

		JLabel activityLog = new JLabel(icon);

		activityLog.setBounds(100, 75, 300, 70);

		activityLog.addMouseListener(new MouseListener() {

			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub

				setTitle("Activity Logs");
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

		URL url = ActivityPage.class.getResource("/resources/toDo.png");
		ImageIcon icon = new ImageIcon(url);

		JLabel taskLog = new JLabel(icon);
		taskLog.setBounds(100, 175, 300, 50);
		taskLog.addMouseListener(new MouseListener() {

			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub

				setTitle("Task Logger");
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

	private void toTimerPage() {

		URL url = ActivityPage.class.getResource("/resources/pomo.png");
		ImageIcon icon = new ImageIcon(url);

		JLabel pomoTimer = new JLabel(icon);
		pomoTimer.setBounds(100, 275, 300, 50);

		pomoTimer.addMouseListener(new MouseListener() {

			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub

				setTitle("Pomodoro Timer");
				setDimension(500, 500);
				pack();
				cardLayout.show(mainPanel, "timer");

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
				pomoTimer.setCursor((Cursor.getPredefinedCursor(Cursor.HAND_CURSOR)));
			}

			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub

				pomoTimer.setCursor(Cursor.getDefaultCursor());
			}
		});

		activityPage.add(pomoTimer);
	}

	public static void main(String[] args) {

		ActivityPage activity = new ActivityPage(new Point(500,500), "darkSlayer25");
		activity.setVisible(true);
	}
}
