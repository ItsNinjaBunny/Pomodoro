package gui;

import java.awt.Dimension;
import java.awt.Point;

import javax.swing.JFrame;

@SuppressWarnings("serial")
public class Timer extends JFrame {
	
	private Point location;
	private String username;

	
	public String getUsername() {
		return username;
	}

	public Timer(Point location, String username) {
		
		setTitle("Pomodoro Timer");
		this.location = location;
		this.username = username;
		
		setLocation(location);
		setPreferredSize(new Dimension(500, 500));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		pack();
		revalidate();
		
		
	}
}
