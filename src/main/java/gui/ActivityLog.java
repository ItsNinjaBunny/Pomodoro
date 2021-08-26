package gui;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.*;

import mongoDB.Mongo;

public class ActivityLog {
	
	private final String username;
	private final Mongo mongo = new Mongo();
	private final DefaultListModel actList = new DefaultListModel();

	private int m = 0;
	private int h = 0;
	private int d = 0;

	private String days, hours, minutes;
	
	public String getUsername() {
		return this.username;
	}
	
	public ActivityLog(String username) {
		
		this.username = username;
	}
		
	@SuppressWarnings("rawtypes")
	public void log(JPanel mainPanel) {
		JPanel activityLog = new JPanel(null);
		List<String> sessionTimes = new ArrayList<>();
		
		activityLog.setPreferredSize(new Dimension(500, 500));
		JList list = getLogs(sessionTimes);
		list.setVisibleRowCount(5);
		list.setLayoutOrientation(JList.VERTICAL);
		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);



		for (String sessionTime : sessionTimes) {
			m += (int) Double.parseDouble(sessionTime) % 60;
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


		JScrollPane scroller = new JScrollPane(list);
		scroller.setBounds(10, 10, 480, 300);
		scroller.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		scroller.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		activityLog.setPreferredSize(new Dimension(500, 500));


		JLabel session = new JLabel();
		session.setBounds(30, 310, 200, 30);
		session.setText("days: " + days + " hours: " + hours + " minutes: " + minutes);

		JButton update = new JButton("update");
		update.setBounds(350, 310, 68, 30);
		update.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				for(int i = 0; i < sessionTimes.size(); i++) {
					System.out.println(sessionTimes.get(i));
					sessionTimes.remove(i);
				}
				actList.clear();

				list.removeAll();
				mongo.getLogs(getUsername(), actList, sessionTimes);
				m = 0;
				h= 0;
				d = 0;

				for (String sessionTime : sessionTimes) {
					m += (int) Double.parseDouble(sessionTime) % 60;
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
				minutes = String.valueOf(m);
				hours = String.valueOf(h);
				days = String.valueOf(d);

				session.setText("days: " + days + " hours: " + hours + " minutes: " + minutes);
				activityLog.revalidate();
			}
		});

		activityLog.add(update);
		activityLog.add(session);
		activityLog.add(scroller);

		mainPanel.add(activityLog, "log");
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	
	private JList getLogs(List<String> sessionTimes) {

		mongo.getLogs(username, actList, sessionTimes);
		return new JList(actList);
	}
	
	public static void main(String[] args) {

		JFrame frame = new JFrame();
		frame.setBounds(100, 100, 500, 500);
		
		ActivityLog log = new ActivityLog("darkSlayer25");
		JPanel panel = new JPanel();
		log.log(panel);
		frame.setContentPane(panel);
		frame.pack();
		frame.setVisible(true);
	}
}
