package gui;

import java.awt.Dimension;

import javax.swing.JFrame;

public class ActivityPage extends JFrame{


	private static final long serialVersionUID = 8050119630303986305L;
	
	private int x;
	private int y;
	private String username;
	
	public ActivityPage(int x, int y, String username) {
		
		this.username = username;
		
		this.x = x;
		this.y = y;
		setBounds(this.x, this.y, 500, 500);
		setPreferredSize(new Dimension(500, 500));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		pack();
		revalidate();
		setVisible(true);
	}

	public String getUsername() {
		return username;
	}
}
