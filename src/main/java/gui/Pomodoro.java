package gui;

import mongoDB.Mongo;
import org.bson.Document;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;
import java.util.ArrayList;


public class Pomodoro {

    private Mongo mongo = new Mongo();
    private String item;
    public String getUsername() {
        return username;
    }

    private String username;
    private CardLayout cardLayout;
    private int time = 0;
    private int session = 0;


    public Pomodoro(CardLayout cardLayout, String username) {

        this.username = username;
        this.cardLayout = cardLayout;

    }

    public void createTimer(JPanel mainPanel, JFrame frame) {
        JPanel timerPanel = new JPanel(null);

        JLabel timeLabel = new JLabel("00:00");
        timeLabel.setFont(new Font("Serif", Font.PLAIN, 64));
        timeLabel.setBounds(175, 130, 200, 150);

        JButton minutes25 = new JButton("25 minutes");
        minutes25.setBounds(300, 375, 115, 40);
        minutes25.setFont(new Font("Serif", Font.PLAIN, 20));


        JButton minutes15 = new JButton("15 minutes");
        minutes15.setBounds(30, 375, 115, 40);
        minutes15.setFont(new Font("Serif", Font.PLAIN, 20));

        ArrayList<String[]> task = new ArrayList<>();
        mongo.getTasks(getUsername(), task);

        String[] taskList = new String[task.size() + 1];
        taskList[0] = "select";
        for(int i = 0; i < task.size(); i++) {
            taskList[i + 1] = task.get(i)[1];
        }

        JLabel currentTask = new JLabel();
        currentTask.setBounds(340, 30, 260, 30);

        JLabel current = new JLabel("current task: ");
        current.setBounds(250, 30, 85, 30);
        current.setVisible(false);

        JComboBox tasks = new JComboBox(taskList);
        tasks.setSelectedItem(0);
        tasks.setBounds(30, 30, 100, 30);

        JButton select = new JButton("selected");
        select.setBounds(140, 30, 80, 30);
        select.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                item = (String) tasks.getSelectedItem();
                if(item.equals("select")) {

                }
                else {
                    current.setVisible(true);
                    currentTask.setText(item);
                }
            }
        });


        final Timer t = new Timer(1000, new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                time -= 600;
                timeLabel.setText(format(time / 60) + ":" + format(time % 60));
                if (time <= 0) {
                    final Timer timer = (Timer) e.getSource();
                    timer.stop();
                    timeLabel.setText("00:00");
                    timerPanel.revalidate();
                    mongo.logSession(getUsername(), session);
                    minutes15.setVisible(true);
                    minutes25.setVisible(true);
                }
            }
        });



        minutes15.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                time = 900;
                session = (time / 60);

                t.start();
                minutes25.setVisible(false);
                minutes15.setVisible(false);
            }
        });

        minutes25.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                time = 1500;
                session = (time / 60);
                t.start();
                minutes25.setVisible(false);
                minutes15.setVisible(false);
            }
        });

        JButton home = new JButton("home");
        home.setBounds(58, 415, 60, 30);
        home.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.setTitle("Activity Page");
                frame.setPreferredSize(new Dimension(500, 500));
                frame.pack();
                cardLayout.show(mainPanel, "home");
            }
        });

        JButton completed = new JButton("completed task");
        completed.setBounds(300, 415, 110, 30);
        completed.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                mongo.completedTask(getUsername(), item);
            }
        });


        URL url = ActivityPage.class.getResource("/resources/timerbg.png");
        ImageIcon icon = new ImageIcon(url);

        JLabel background = new JLabel(icon);
        background.setBounds(0, 0, 500, 500);

        timerPanel.add(completed);
        timerPanel.add(select);
        timerPanel.add(current);
        timerPanel.add(currentTask);
        timerPanel.add(tasks);
        timerPanel.add(home);
        timerPanel.add(minutes25);
        timerPanel.add(minutes15);
        timerPanel.add(timeLabel);
        timerPanel.add(background);
        mainPanel.add(timerPanel, "timer");
    }

    private String format(int i) {
        String result = String.valueOf(i);
        if (result.length() == 1) {
            result = "0" + result;
        }
        return result;
    }

}
