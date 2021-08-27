package gui;

import org.bson.Document;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.net.URL;
import java.util.ArrayList;

import mongoDB.Mongo;

public class Tasks {

    private final CardLayout cardLayout;
    private final String username;
    private final ArrayList<String[]> taskLog = new ArrayList<>();



    public Tasks(CardLayout cardLayout, String username) {

        this.cardLayout = cardLayout;
        this.username = username;

    }

    private String getUsername() {

        return this.username;
    }

    public void createTask(JPanel mainPanel, JFrame frame) {
        JPanel task = new JPanel(null);
        task.setPreferredSize(new Dimension(500, 350));

        DefaultTableModel tableModel = getTasks(getUsername(), taskLog);
        JTable table = new JTable(tableModel);
        table.setBackground(Color.white);
        table.setSelectionBackground(Color.white);
        table.setSelectionForeground(Color.black);
        table.getTableHeader().setResizingAllowed(false);
        table.getTableHeader().setReorderingAllowed(false);
        table.getColumnModel().getColumn(0).setPreferredWidth(30);
        table.getColumnModel().getColumn(1).setPreferredWidth(250);
        table.getColumnModel().getColumn(2).setPreferredWidth(90);
        table.getColumnModel().getColumn(3).setPreferredWidth(110);



        JScrollPane scroller = new JScrollPane(table);
        scroller.setBounds(10, 20, 480, 200);
        scroller.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        scroller.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        JLabel taskName = new JLabel("task name: ");
        taskName.setBounds(15, 250, 90 , 20);

        JTextField taskField = new JTextField();
        taskField.setBounds(110, 250, 300, 20);

        taskField.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {

            }

            @Override
            public void keyPressed(KeyEvent e) {
                if(e.getKeyCode() == KeyEvent.VK_ENTER) {
                    String taskName = taskField.getText();
                    String date = String.valueOf(java.time.LocalDate.now());
                    Mongo mongo = new Mongo();
                    mongo.insertTask(taskLog, getUsername(), taskName, date);

                    taskLog.clear();
                    table.removeAll();
                    DefaultTableModel tableModel = getTasks(getUsername(), taskLog);
                    table.setModel(tableModel);

                    table.getTableHeader().setResizingAllowed(false);
                    table.getTableHeader().setReorderingAllowed(false);
                    table.getColumnModel().getColumn(0).setPreferredWidth(30);
                    table.getColumnModel().getColumn(1).setPreferredWidth(250);
                    table.getColumnModel().getColumn(2).setPreferredWidth(90);
                    table.getColumnModel().getColumn(3).setPreferredWidth(110);
                    taskField.setText("");

                    task.revalidate();
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {

            }
        });

        JButton insert = new JButton("insert");
        insert.setBounds(200, 280, 80, 30);
        insert.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                String taskName = taskField.getText();
                String date = String.valueOf(java.time.LocalDate.now());
                Mongo mongo = new Mongo();
                mongo.insertTask(taskLog, getUsername(), taskName, date);

                taskLog.clear();
                table.removeAll();
                DefaultTableModel tableModel = getTasks(getUsername(), taskLog);
                table.setModel(tableModel);

                table.getTableHeader().setResizingAllowed(false);
                table.getTableHeader().setReorderingAllowed(false);
                table.getColumnModel().getColumn(0).setPreferredWidth(30);
                table.getColumnModel().getColumn(1).setPreferredWidth(250);
                table.getColumnModel().getColumn(2).setPreferredWidth(90);
                table.getColumnModel().getColumn(3).setPreferredWidth(110);

                taskField.setText("");

                task.revalidate();
            }
        });

        JButton update = new JButton("update");
        update.setBounds(300, 280, 80, 30);
        update.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                taskLog.clear();
                table.removeAll();
                DefaultTableModel tableModel = getTasks(getUsername(), taskLog);
                table.setModel(tableModel);

                table.getTableHeader().setResizingAllowed(false);
                table.getTableHeader().setReorderingAllowed(false);
                table.getColumnModel().getColumn(0).setPreferredWidth(30);
                table.getColumnModel().getColumn(1).setPreferredWidth(250);
                table.getColumnModel().getColumn(2).setPreferredWidth(90);
                table.getColumnModel().getColumn(3).setPreferredWidth(110);

                taskField.setText("");

                task.revalidate();
            }
        });

        JButton home = new JButton("home");
        home.setBounds(10, 280, 80, 30);
        home.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                frame.setTitle("Activity Page");
                frame.setPreferredSize(new Dimension(500, 500));
                frame.pack();
                cardLayout.show(mainPanel, "home");

            }
        });
        URL url = ActivityPage.class.getResource("/resources/homebg.png");
        ImageIcon icon = new ImageIcon(url);

        JLabel background = new JLabel(icon);
        background.setBounds(0, 0, 500, 350);

        task.add(update);
        task.add(insert);
        task.add(taskField);
        task.add(taskName);
        task.add(home);
        task.add(scroller);
        task.add(background);
        mainPanel.add(task, "tasks");
    }

    private DefaultTableModel getTasks(String username, ArrayList<String[]> tasks) {
        Mongo mongo = new Mongo();
        mongo.getTasks(username, tasks);

        ArrayList<String> column = new ArrayList<>();
        column.add("ID");
        column.add("task name");
        column.add("date added");
        column.add("date completed");

        return new DefaultTableModel(tasks.toArray(new Object[][] {}), column.toArray());
    }


}
