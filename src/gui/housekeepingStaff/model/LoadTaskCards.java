package gui.housekeepingStaff.model;

import com.formdev.flatlaf.FlatClientProperties;
import gui.housekeepingStaff.HouseKeepingStaffOverview;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.sql.ResultSet;
import java.util.Date;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;
import model.FormatDate;
import model.MYsql;
import model.employeeDetails;

public class LoadTaskCards {

    public void loadTaskCards(JScrollPane pane, Date date, employeeDetails employee, int status) {
        loadTasksDynamically(pane, date, employee, status);
    }

    private JPanel createTaskCard(String taskName, String duration, int status, String taskID) {
        JPanel card = new JPanel();
//        card.setPreferredSize(new Dimension(300, 70));
        card.setBackground(Color.WHITE);
        card.setLayout(new BorderLayout());
        card.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        card.setPreferredSize(new Dimension(300, 70));
        card.setMaximumSize(new Dimension(300, 70));
        card.setMinimumSize(new Dimension(300, 70));

        // Text part
        JPanel textPanel = new JPanel(new GridLayout(2, 1));
        textPanel.setOpaque(false);
        JLabel title = new JLabel(taskName);
        title.setFont(new Font("Poppins", Font.BOLD, 14));
        JLabel durationLabel = new JLabel(duration + "s");
        durationLabel.setFont(new Font("Poppins", Font.PLAIN, 12));
        textPanel.add(title);
        textPanel.add(durationLabel);

        // Button part
        JButton startButton = new JButton();
        startButton.setFont(new Font("Poppins", Font.BOLD, 12));
        startButton.setFocusPainted(false);
        startButton.putClientProperty(FlatClientProperties.STYLE, "arc:80");

        switch (status) {
            case 1:
                // Pending
                startButton.setText("Start");
                startButton.setEnabled(true);
                startButton.setBackground(new Color(0, 150, 136));
                // Add action listener
                startButton.addActionListener(e -> {
                    System.out.println("Starting task: " + taskName);
                    TaskActionStrategyInterface taskAction = new StartTaskStrategy();
                    taskAction.doTask(taskID, HouseKeepingStaffOverview.getInstance());
                    // TODO: Call a method to mark task as started in database
                    // Optionally, update the UI to reflect the new state
                });
                break;
            case 2:
                // In progress or just completed
                startButton.setText("Completed");
                startButton.setEnabled(true);
                startButton.setBackground(new Color(62, 161, 217)); // Green
                startButton.addActionListener(e -> {
                    System.out.println("Completing task: " + taskName);
                    TaskActionStrategyInterface taskAction = new CompleteTaskStrategy();
                    taskAction.doTask(taskID, HouseKeepingStaffOverview.getInstance());
                    // TODO: Call a method to mark task as completed in database
                    // Optionally, update the UI to reflect the completed state
                });
                break;
            case 3:
                // Fully completed
                startButton.setText("Completed");
                startButton.setEnabled(false);
                startButton.setBackground(Color.LIGHT_GRAY);
                break;
            default:
                break;
        }

        startButton.setForeground(Color.WHITE);

        card.add(textPanel, BorderLayout.CENTER);
        card.add(startButton, BorderLayout.EAST);

        return card;
    }

    private void loadTasksDynamically(JScrollPane scrollpane, Date date, employeeDetails employee, int status) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(Color.WHITE);

        String today = FormatDate.getToday(date, "yyyy-MM-dd");
        int empID = employee.getEmployeeId();
        int taskCount = 0;

        String query = String.format(
                "SELECT `task` AS `task_name`, `date` AS `duration`, taskstatus.id AS `status_id`, taskshedule.id AS taskid "
                + "FROM taskshedule "
                + "INNER JOIN task ON task.id = taskshedule.task_id "
                + "INNER JOIN taskstatus ON taskstatus.id = taskshedule.taskStatus_id "
                + "WHERE assignTo = '%d' AND `date` = '%s' AND taskstatus.id = '%d'",
                empID, today, status
        );

        try {
            ResultSet rs = MYsql.execute(query);
            while (rs.next()) {
                String name = rs.getString("task_name");
                String duration = rs.getString("duration");
                String taskID = rs.getString("taskid");
                int statusId = rs.getInt("status_id");

                JPanel card = createTaskCard(name, duration, statusId, taskID);
                card.setAlignmentX(Component.LEFT_ALIGNMENT);
                panel.add(Box.createVerticalStrut(10));
                panel.add(card);

                taskCount++;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Add placeholders to ensure a consistent number of cards
        int placeholderCount = Math.max(0, 8 - taskCount);
        for (int i = 0; i < placeholderCount; i++) {
            JPanel placeholder = createInvisibleCard();
            placeholder.setAlignmentX(Component.LEFT_ALIGNMENT);
            panel.add(Box.createVerticalStrut(10));
            panel.add(placeholder);
        }

        // Wrap the vertical panel in a wrapper to control horizontal layout
        JPanel wrapper = new JPanel(new FlowLayout(FlowLayout.LEFT, 20, 20));
        wrapper.setBackground(Color.WHITE);
        wrapper.add(panel);

        scrollpane.getViewport().setView(null);
        scrollpane.setViewportView(wrapper);

        panel.revalidate();
        panel.repaint();
        scrollpane.revalidate();
        scrollpane.repaint();

        Component window = SwingUtilities.getWindowAncestor(scrollpane);
        if (window != null) {
            window.revalidate();
            window.repaint();
        }
    }

    private JPanel createInvisibleCard() {
        JPanel panel = new JPanel();
        panel.setPreferredSize(new Dimension(300, 90));
        panel.setMinimumSize(new Dimension(300, 90));
        panel.setMaximumSize(new Dimension(300, 90));
        panel.setOpaque(false);
        JLabel spacer = new JLabel(" "); // a space character
        spacer.setPreferredSize(new Dimension(300, 70));
        panel.add(spacer);
        return panel;
    }

}
