package gui;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import mongoDB.Mongo;

public class ActivityLog {
	
	private final String username;
	private final Mongo mongo = new Mongo();
	private final ArrayList<String[]> logs = new ArrayList<>();
	private final CardLayout cardLayout;

	private int m = 0;
	private int h = 0;
	private int d = 0;

	private String days, hours, minutes;
	
	public String getUsername() {
		return this.username;
	}
	
	public ActivityLog(CardLayout cardLayout, String username) {

		this.cardLayout = cardLayout;
		this.username = username;
	}
		
	@SuppressWarnings("rawtypes")
	public void log(JPanel mainPanel, JFrame frame) {
		JPanel activityLog = new JPanel(null);
		List<String> sessionTimes = new ArrayList<>();

		frame.setPreferredSize(new Dimension(500, 350));
		frame.pack();
		//JList list = getLogs(sessionTimes);
		DefaultTableModel model = getLogs(sessionTimes, logs);

		JTable table = new JTable(model);


		for (String sessionTime : sessionTimes) {
			m += (int) Double.parseDouble(sessionTime);
			if(m >= 60) {
				h += 1;
				m = Math.abs(m - 60);
			}
			if(h >= 24) {
				d += 1;
				h = Math.abs(24 - h);
			}
			//System.out.println("hours: " + h + " minutes: " + m);
		}
		hours = String.valueOf(h);
		minutes = String.valueOf(m);
		days = String.valueOf(d);


		JScrollPane scroller = new JScrollPane(table);
		scroller.setBounds(10, 10, 480, 300);
		scroller.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		scroller.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		activityLog.setPreferredSize(new Dimension(500, 500));


		JLabel session = new JLabel();
		session.setBounds(30, 310, 200, 30);
		session.setText("Days: " + days + " Hours: " + hours + " Minutes: " + minutes);

		JButton update = new JButton("update");
		update.setBounds(350, 310, 68, 30);
		update.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				sessionTimes.clear();
				logs.clear();
				table.removeAll();
				DefaultTableModel model = getLogs(sessionTimes, logs);
				table.setModel(model);

				m = 0;
				h = 0;
				d = 0;


				for (String sessionTime : sessionTimes) {
					m += (int) Double.parseDouble(sessionTime);
					if(m >= 60) {
						h += 1;
						m = Math.abs(m - 60);
					}
					if(h >= 24) {
						d += 1;
						h = Math.abs(h - 24);
					}
				}

				minutes = String.valueOf(m);
				hours = String.valueOf(h);
				days = String.valueOf(d);

				session.setText("days: " + d + " hours: " + h + " minutes: " + m);
				activityLog.revalidate();
			}
		});

		JButton logOut = new JButton("home");
		logOut.setBounds(10, 340, 45, 20);
		logOut.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				frame.setPreferredSize(new Dimension(500, 500));
				frame.pack();
				cardLayout.show(mainPanel, "home");
			}
		});

		activityLog.add(logOut);
		activityLog.add(update);
		activityLog.add(session);
		activityLog.add(scroller);

		mainPanel.add(activityLog, "log");
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	
	private DefaultTableModel getLogs(List<String> sessionTimes, ArrayList<String[]> logs) {


		mongo.getLogs(username, logs, sessionTimes);
		ArrayList<String> column = new ArrayList<>();
		column.add("sessionID");
		column.add("session Length");

		return new DefaultTableModel(logs.toArray(new Object[][] {}), column.toArray());
	}
}
