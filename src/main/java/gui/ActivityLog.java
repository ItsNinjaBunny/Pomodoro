package gui;

import java.awt.Dimension;

import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;

import mongoDB.Mongo;

public class ActivityLog {
	
	private String username;
	private Mongo mongo = new Mongo();
	
	public String getUsername() {
		return this.username;
	}
	
	public ActivityLog(String username) {
		
		this.username = username;
	}
		
	@SuppressWarnings("rawtypes")
	public void log(JPanel mainPanel) {
		JPanel activityLog = new JPanel(null);
		
		activityLog.setPreferredSize(new Dimension(500, 500));
		JList list = getLogs();
		list.setVisibleRowCount(5);
		list.setLayoutOrientation(JList.VERTICAL);
		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);	
		JScrollPane scroller = new JScrollPane(list);
		scroller.setBounds(10, 10, 480, 300);
		scroller.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		scroller.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		activityLog.setPreferredSize(new Dimension(500, 500));
		
		activityLog.add(scroller);	
		
		
		mainPanel.add(activityLog, "log");
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	
	private JList getLogs() {
		DefaultListModel actList = new DefaultListModel();
		mongo.getLogs(username, actList);
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
