package gui.housekeepingManager;

import com.formdev.flatlaf.FlatClientProperties;
import gui.SignIn;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.sql.ResultSet;
import java.util.Date;
import java.util.HashMap;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import model.FormatDate;
import model.MYsql;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PiePlot;
import org.jfree.data.general.DefaultPieDataset;

public class HouseKeepingManagerOverview extends javax.swing.JPanel {

    private static HouseKeepingManagerOverview overview;
    private boolean firstClick;

    public static synchronized HouseKeepingManagerOverview getInstance() {
        if (overview == null) {
            overview = new HouseKeepingManagerOverview();
        }
        return overview;
    }
    private Date date;

    private HouseKeepingManagerOverview() {
        initComponents();
        init();
    }

    private void init() {
        firstClick = true;
        date = new Date();
        jPanel1.putClientProperty(FlatClientProperties.STYLE, "arc:80");
        jPanel4.putClientProperty(FlatClientProperties.STYLE, "arc:80");
        jPanel13.putClientProperty(FlatClientProperties.STYLE, "arc:80");

        jPanel19.putClientProperty(FlatClientProperties.STYLE, "arc:50");
        jPanel20.putClientProperty(FlatClientProperties.STYLE, "arc:50");
        jPanel21.putClientProperty(FlatClientProperties.STYLE, "arc:50");

        jPanel22.putClientProperty(FlatClientProperties.STYLE, "arc:100");
        jPanel23.putClientProperty(FlatClientProperties.STYLE, "arc:100");
        jPanel24.putClientProperty(FlatClientProperties.STYLE, "arc:100");
        jPanel25.putClientProperty(FlatClientProperties.STYLE, "arc:100");
        jPanel27.putClientProperty(FlatClientProperties.STYLE, "arc:100");

        jScrollPane5.setBorder(BorderFactory.createEmptyBorder());
        jScrollPane1.setBorder(BorderFactory.createEmptyBorder());

//        increase scroll speed
        jScrollPane1.getVerticalScrollBar().setUnitIncrement(10);
        loadWidgets();
        loadRoomsDynamically();
    }

    private void loadOperationsChart(double completed, double incomplete) {

        // Dataset
        DefaultPieDataset dataset = new DefaultPieDataset();
        dataset.setValue("Completed", completed);
        dataset.setValue("Incomplete", incomplete);

        // Create Pie Chart
        JFreeChart chart = ChartFactory.createPieChart(
                "Operations", // Chart title
                dataset,
                false, // Legend
                false,
                false
        );
        PiePlot plot = (PiePlot) chart.getPlot();

        // Set colors
        plot.setSectionPaint("Completed", new Color(66, 165, 245));  // Blue
        plot.setSectionPaint("Incomplete", new Color(255, 87, 34));  // Red/Orange
        // Chart and background styling
        plot.setBackgroundPaint(new Color(245, 245, 245));           // Light gray background
        plot.setOutlineVisible(false);
        plot.setLabelGenerator(null);                                // Hide labels
        plot.setSimpleLabels(true);
        plot.setInteriorGap(0.02);                                   // Donut thickness

        // Title styling
        chart.setBackgroundPaint(new Color(252, 252, 252));
        chart.setTitle(new org.jfree.chart.title.TextTitle("Operations",
                new Font("Poppins", Font.PLAIN, 14)));
        chart.setTitle("");

        // Setup ChartPanel
        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setBorder(BorderFactory.createEmptyBorder());
        chartPanel.setPreferredSize(new Dimension(350, 227)); // Adjust to fit your layout

        Color bgColor = new Color(252, 252, 252);
        chart.setBackgroundPaint(bgColor);       // Background of the whole chart
        plot.setBackgroundPaint(bgColor);        // Background inside the plot
        chartPanel.setBackground(bgColor);       // Optional: ChartPanel background

        jPanel3.removeAll();  // Replace jPanel3 with your actual JPanel
        jPanel3.setLayout(new BorderLayout());
        jPanel3.add(chartPanel, BorderLayout.CENTER);
        jPanel3.validate();
    }

    private void loadRoomsDynamically() {
        jPanel5.removeAll(); // Clear existing content
        JLabel loadingLabel = new JLabel(new ImageIcon(getClass().getResource("/resource/loadingGif.gif")));
        jPanel5.add(loadingLabel);
        SwingUtilities.updateComponentTreeUI(jPanel5);
        JPanel jPanel6 = new JPanel();
        jPanel6.setBackground(new java.awt.Color(252, 252, 252));
        Thread loadRooms = new Thread(() -> {
            jPanel6.removeAll(); // Clear existing content
            jPanel6.setLayout(new GridLayout(0, 8, 10, 10)); // 8 columns, auto rows, 10px gap
            String today = FormatDate.getToday(date, "yyyy-MM-dd");
            try {
                ResultSet rs = MYsql.execute(
                        "SELECT rooms.id, rooms.`no`, cleanroom.Cleanstatus_id "
                        + "FROM rooms "
                        + "LEFT JOIN cleanroom ON cleanroom.rooms_id = rooms.id"
                );
                String cleanRsQuery = "SELECT fname FROM taskshedule INNER JOIN cleanroom ON"
                        + " taskshedule.rooms_id = cleanroom.rooms_id INNER JOIN employee ON "
                        + "employee.id = taskshedule.assignTo WHERE taskshedule.rooms_id = '%s' "
                        + "AND `date` = '%s' AND taskstatus_id != 3 ";
                while (rs.next()) {
                    String roomNo = rs.getString("no");
                    String statusId = rs.getString("Cleanstatus_id");
                    String status = "";
                    Color color;
                    switch (statusId) {
                        case "1": // Clean
                            color = new Color(60, 179, 113); // Green
                            status = "Ready";
                            break;
                        case "3": // Dirty
                            color = new Color(255, 69, 0); // Red
                            status = "Uncleaned";
                            break;
                        case "2": // Needs Attention
                            color = new Color(212, 175, 55); // Gold
                            status = "Cleaning";
                            break;
                        default: // Unknown status
                            color = Color.LIGHT_GRAY;
                    }

                    HashMap<String, String> roomDetailsMap = new HashMap<>();
                    roomDetailsMap.put("roomNo", roomNo);
                    roomDetailsMap.put("cleanStatusId", statusId);
                    roomDetailsMap.put("cleanStatus", status);
                    roomDetailsMap.put("roomID", rs.getString("rooms.id"));

                    JButton roomButton = createRoomButton(roomDetailsMap, color, today, cleanRsQuery);
                    roomButton.addActionListener(e -> {
                        new Thread(() -> {
                            AssignCleaning dialog = AssignCleaning.getInstance(roomDetailsMap, overview);
                            dialog.setVisible(true);
                        }).start();
                    });

                    jPanel6.add(roomButton);

                }
            } catch (Exception e) {
                e.printStackTrace();
                SignIn.logger.severe(e.getMessage());
            }

            jPanel6.revalidate();
            jPanel6.repaint();
            SwingUtilities.invokeLater(() -> {
                jPanel5.removeAll();
                jPanel5.add(jPanel6);
                jPanel5.revalidate();
                jPanel5.repaint();
            });
        });
        loadRooms.start();
    }

    public void loadrooms() {
        loadRoomsDynamically();
        loadWidgets();
    }

    private JLabel createCleanerLabel(String cleanerQuery, String date, String roomId, String status) throws NullPointerException {
        String text = "";

        try {
            ResultSet cleanRs = MYsql.execute(
                    String.format(cleanerQuery, roomId, date)
            );
            if (cleanRs.next()) {

                text = "Assigned :" + cleanRs.getString("fname");
                if (status.equals("Ready")) {
                    text = "";
                }

            }

        } catch (Exception e) {
            e.printStackTrace();
            SignIn.logger.severe(e.getMessage());
        }
        // Status - medium font
        JLabel statusLabel = new JLabel(text, SwingConstants.CENTER);

        statusLabel.setFont(new java.awt.Font("Poppins", 0, 14));
        statusLabel.setForeground(Color.WHITE);
        statusLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        return statusLabel;
    }

    private JButton createRoomButton(HashMap<String, String> roomDetails, Color color, String date, String cleanerQuery) {
        String roomNo = roomDetails.get("roomNo");
        String status = roomDetails.get("cleanStatus");
        String roomId = roomDetails.get("roomID");
        String statusID = roomDetails.get("cleanStatusId");

        JButton roomPanel = new JButton();
        roomPanel.setPreferredSize(new Dimension(150, 150));
        roomPanel.setBackground(color);
        roomPanel.setLayout(new BoxLayout(roomPanel, BoxLayout.Y_AXIS));
        roomPanel.putClientProperty(FlatClientProperties.STYLE, "arc:50");

        // Add more space at the top
        roomPanel.add(Box.createVerticalStrut(15)); // Increased padding

        // "Room No" label - small font
        JLabel roomTitleLabel = new JLabel("Room No", SwingConstants.CENTER);
        roomTitleLabel.setFont(new java.awt.Font("Poppins", 0, 12));
        roomTitleLabel.setForeground(Color.WHITE);
        roomTitleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        roomPanel.add(roomTitleLabel);

        // Room number - larger font
        JLabel roomNoLabel = new JLabel(roomNo, SwingConstants.CENTER);
        roomNoLabel.setFont(new java.awt.Font("Poppins", 1, 22));
        roomNoLabel.setForeground(Color.WHITE);
        roomNoLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        roomPanel.add(Box.createVerticalStrut(5));
        roomPanel.add(roomNoLabel);

        // Status - medium font
        JLabel statusLabel = new JLabel(status, SwingConstants.CENTER);
        statusLabel.setFont(new java.awt.Font("Poppins", 0, 16));
        statusLabel.setForeground(Color.WHITE);
        statusLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        roomPanel.add(Box.createVerticalStrut(10));
        roomPanel.add(statusLabel);

        JLabel worker = createCleanerLabel(cleanerQuery, date, roomId, statusID);
        roomPanel.add(Box.createVerticalStrut(5));
        roomPanel.add(worker);

        return roomPanel;
    }

    private void loadCleaningProcess(double total, double pres, double abs, double late) {
        System.out.println(jPanel18.getPreferredSize().width);
        double length = (double) (jPanel18.getPreferredSize().width - 12);
        System.out.println(length);
        double totalEmp = total;
        double present = pres;
        double absent = abs;
        double lateEmp = late;
        double presentProgress = (double) present / totalEmp * length; // This will give you 330.0
        double absentProgress = (double) absent / totalEmp * length; // This will give you 330.0
        double lateProgress = (double) lateEmp / totalEmp * length; // This will give you 330.0

        System.out.println(presentProgress);
        System.out.println(absentProgress);
        System.out.println(lateProgress);

        jPanel19.setPreferredSize(new Dimension((int) presentProgress, 50));
        jPanel21.setPreferredSize(new Dimension((int) absentProgress, 50));
        jPanel20.setPreferredSize(new Dimension((int) lateProgress, 50));

        if (present != 0) {
            jLabel19.setText(String.valueOf((int) present));
        }
        if (absent != 0) {
            jLabel20.setText(String.valueOf((int) absent));
        }
        if (lateEmp != 0) {
            jLabel21.setText(String.valueOf((int) lateEmp));
        }
//        
        SwingUtilities.updateComponentTreeUI(jPanel18);
    }

    private void loadWidgets() {
        String today = FormatDate.getToday(date, "yyyy-MM-dd");
        try {
            ResultSet cleanProgress = MYsql.execute("SELECT \n"
                    + "	COUNT(*) AS total,\n"
                    + "    COUNT(CASE WHEN Cleanstatus_id = 1 THEN 1 END) AS clean,\n"
                    + "    COUNT(CASE WHEN Cleanstatus_id = 2 THEN 1 END) AS cleaning,\n"
                    + "    COUNT(CASE WHEN Cleanstatus_id = 3 THEN 1 END) AS dirty\n"
                    + "FROM cleanroom;");

            if (cleanProgress.next()) {
                double total = cleanProgress.getDouble("total");
                double present = cleanProgress.getDouble("clean");
                double abs = cleanProgress.getDouble("cleaning");
                double late = cleanProgress.getDouble("dirty");

                loadCleaningProcess(total, present, abs, late);
            }

            ResultSet taskCount = MYsql.execute("SELECT \n"
                    + "	COUNT(*) AS total,\n"
                    + "    COUNT(CASE WHEN taskStatus_id != 3 THEN 1 END) AS ongoing,\n"
                    + "    COUNT(CASE WHEN taskStatus_id = 3 THEN 1 END) AS completed\n"
                    + "FROM taskshedule WHERE `date` = ' " + today + "' ");
            if (taskCount.next()) {
                int total = taskCount.getInt("total");
                total = Math.round(total);
                jLabel5.setText(String.valueOf(total));
                double complete = taskCount.getDouble("completed");
                double ongoing = taskCount.getDouble("ongoing");
                loadOperationsChart(complete, ongoing);

            }
        } catch (Exception e) {
            e.printStackTrace();
            SignIn.logger.severe(e.getMessage());
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jScrollPane5 = new javax.swing.JScrollPane();
        jPanel26 = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jPanel25 = new javax.swing.JPanel();
        jLabel25 = new javax.swing.JLabel();
        jLabel26 = new javax.swing.JLabel();
        jPanel27 = new javax.swing.JPanel();
        jPanel13 = new javax.swing.JPanel();
        jLabel15 = new javax.swing.JLabel();
        jPanel18 = new javax.swing.JPanel();
        jPanel19 = new javax.swing.JPanel();
        jLabel19 = new javax.swing.JLabel();
        jPanel20 = new javax.swing.JPanel();
        jLabel21 = new javax.swing.JLabel();
        jPanel21 = new javax.swing.JPanel();
        jLabel20 = new javax.swing.JLabel();
        jPanel22 = new javax.swing.JPanel();
        jPanel23 = new javax.swing.JPanel();
        jPanel24 = new javax.swing.JPanel();
        jLabel22 = new javax.swing.JLabel();
        jLabel23 = new javax.swing.JLabel();
        jLabel24 = new javax.swing.JLabel();
        jButton3 = new javax.swing.JButton();
        jPanel4 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jPanel5 = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        jButton4 = new javax.swing.JButton();

        setBackground(new java.awt.Color(255, 255, 255));

        jLabel1.setFont(new java.awt.Font("Poppins", 1, 36)); // NOI18N
        jLabel1.setText("Overview");

        jScrollPane5.setBackground(new java.awt.Color(255, 255, 255));

        jPanel26.setBackground(new java.awt.Color(255, 255, 255));

        jPanel1.setBackground(new java.awt.Color(252, 252, 252));

        jLabel3.setFont(new java.awt.Font("Poppins", 0, 24)); // NOI18N
        jLabel3.setText("Overview");

        jPanel2.setBackground(new java.awt.Color(252, 252, 252));

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 350, Short.MAX_VALUE)
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 236, Short.MAX_VALUE)
        );

        jLabel4.setFont(new java.awt.Font("Poppins Medium", 0, 24)); // NOI18N
        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel4.setText("Total Tasks");

        jLabel5.setFont(new java.awt.Font("Poppins SemiBold", 0, 70)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(153, 153, 153));
        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel5.setText("26");

        jPanel25.setBackground(new java.awt.Color(62, 161, 217));

        javax.swing.GroupLayout jPanel25Layout = new javax.swing.GroupLayout(jPanel25);
        jPanel25.setLayout(jPanel25Layout);
        jPanel25Layout.setHorizontalGroup(
            jPanel25Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 22, Short.MAX_VALUE)
        );
        jPanel25Layout.setVerticalGroup(
            jPanel25Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 22, Short.MAX_VALUE)
        );

        jLabel25.setFont(new java.awt.Font("Poppins", 0, 14)); // NOI18N
        jLabel25.setText("Completed");

        jLabel26.setFont(new java.awt.Font("Poppins", 0, 14)); // NOI18N
        jLabel26.setText("Incomplete");

        jPanel27.setBackground(new java.awt.Color(255, 69, 0));

        javax.swing.GroupLayout jPanel27Layout = new javax.swing.GroupLayout(jPanel27);
        jPanel27.setLayout(jPanel27Layout);
        jPanel27Layout.setHorizontalGroup(
            jPanel27Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 22, Short.MAX_VALUE)
        );
        jPanel27Layout.setVerticalGroup(
            jPanel27Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 22, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addContainerGap())
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jPanel27, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel26))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jPanel25, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel25)))
                        .addContainerGap())
                    .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(8, 8, 8)
                        .addComponent(jLabel4)
                        .addGap(0, 0, 0)
                        .addComponent(jLabel5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jPanel25, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel25))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jPanel27, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel26))
                        .addGap(0, 23, Short.MAX_VALUE))
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel3))
                .addGap(21, 21, 21))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(19, 19, 19))
        );

        jPanel13.setBackground(new java.awt.Color(252, 252, 252));

        jLabel15.setFont(new java.awt.Font("Poppins", 0, 24)); // NOI18N
        jLabel15.setText("Room Cleaning Progess");

        jPanel18.setBackground(new java.awt.Color(252, 252, 252));
        jPanel18.setMinimumSize(new java.awt.Dimension(483, 100));

        jPanel19.setBackground(new java.awt.Color(60, 179, 113));
        jPanel19.setMinimumSize(new java.awt.Dimension(0, 50));

        jLabel19.setFont(new java.awt.Font("Poppins SemiBold", 0, 18)); // NOI18N
        jLabel19.setForeground(new java.awt.Color(241, 249, 249));
        jLabel19.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        javax.swing.GroupLayout jPanel19Layout = new javax.swing.GroupLayout(jPanel19);
        jPanel19.setLayout(jPanel19Layout);
        jPanel19Layout.setHorizontalGroup(
            jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel19, javax.swing.GroupLayout.DEFAULT_SIZE, 198, Short.MAX_VALUE)
        );
        jPanel19Layout.setVerticalGroup(
            jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel19, javax.swing.GroupLayout.DEFAULT_SIZE, 50, Short.MAX_VALUE)
        );

        jPanel20.setBackground(new java.awt.Color(62, 161, 217));
        jPanel20.setMinimumSize(new java.awt.Dimension(0, 50));
        jPanel20.setName(""); // NOI18N

        jLabel21.setFont(new java.awt.Font("Poppins SemiBold", 0, 18)); // NOI18N
        jLabel21.setForeground(new java.awt.Color(241, 249, 249));
        jLabel21.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        javax.swing.GroupLayout jPanel20Layout = new javax.swing.GroupLayout(jPanel20);
        jPanel20.setLayout(jPanel20Layout);
        jPanel20Layout.setHorizontalGroup(
            jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel21, javax.swing.GroupLayout.DEFAULT_SIZE, 139, Short.MAX_VALUE)
        );
        jPanel20Layout.setVerticalGroup(
            jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel21, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jPanel21.setBackground(new java.awt.Color(255, 69, 0));
        jPanel21.setMinimumSize(new java.awt.Dimension(0, 50));

        jLabel20.setFont(new java.awt.Font("Poppins SemiBold", 0, 18)); // NOI18N
        jLabel20.setForeground(new java.awt.Color(241, 249, 249));
        jLabel20.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        javax.swing.GroupLayout jPanel21Layout = new javax.swing.GroupLayout(jPanel21);
        jPanel21.setLayout(jPanel21Layout);
        jPanel21Layout.setHorizontalGroup(
            jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel20, javax.swing.GroupLayout.DEFAULT_SIZE, 170, Short.MAX_VALUE)
        );
        jPanel21Layout.setVerticalGroup(
            jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel20, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jPanel22.setBackground(new java.awt.Color(60, 179, 113));

        javax.swing.GroupLayout jPanel22Layout = new javax.swing.GroupLayout(jPanel22);
        jPanel22.setLayout(jPanel22Layout);
        jPanel22Layout.setHorizontalGroup(
            jPanel22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 22, Short.MAX_VALUE)
        );
        jPanel22Layout.setVerticalGroup(
            jPanel22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 22, Short.MAX_VALUE)
        );

        jPanel23.setBackground(new java.awt.Color(255, 69, 0));

        javax.swing.GroupLayout jPanel23Layout = new javax.swing.GroupLayout(jPanel23);
        jPanel23.setLayout(jPanel23Layout);
        jPanel23Layout.setHorizontalGroup(
            jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 22, Short.MAX_VALUE)
        );
        jPanel23Layout.setVerticalGroup(
            jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 22, Short.MAX_VALUE)
        );

        jPanel24.setBackground(new java.awt.Color(62, 161, 217));

        javax.swing.GroupLayout jPanel24Layout = new javax.swing.GroupLayout(jPanel24);
        jPanel24.setLayout(jPanel24Layout);
        jPanel24Layout.setHorizontalGroup(
            jPanel24Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 22, Short.MAX_VALUE)
        );
        jPanel24Layout.setVerticalGroup(
            jPanel24Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 22, Short.MAX_VALUE)
        );

        jLabel22.setFont(new java.awt.Font("Poppins", 0, 14)); // NOI18N
        jLabel22.setText("Ready");

        jLabel23.setFont(new java.awt.Font("Poppins", 0, 14)); // NOI18N
        jLabel23.setText("Dirty");

        jLabel24.setFont(new java.awt.Font("Poppins", 0, 14)); // NOI18N
        jLabel24.setText("Cleaning");

        javax.swing.GroupLayout jPanel18Layout = new javax.swing.GroupLayout(jPanel18);
        jPanel18.setLayout(jPanel18Layout);
        jPanel18Layout.setHorizontalGroup(
            jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel18Layout.createSequentialGroup()
                .addComponent(jPanel19, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel21, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel20, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(jPanel18Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel22, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel22, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel23, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(16, 16, 16)
                .addComponent(jLabel23, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel24, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel24)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel18Layout.setVerticalGroup(
            jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel18Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel19, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel21, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel20, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(39, 39, 39)
                .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel22)
                    .addComponent(jPanel23, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel23)
                    .addComponent(jLabel24)
                    .addComponent(jPanel22, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel24, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(28, Short.MAX_VALUE))
        );

        jButton3.setFont(new java.awt.Font("Poppins", 0, 12)); // NOI18N
        jButton3.setText("Refresh");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel13Layout = new javax.swing.GroupLayout(jPanel13);
        jPanel13.setLayout(jPanel13Layout);
        jPanel13Layout.setHorizontalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel13Layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel13Layout.createSequentialGroup()
                        .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 405, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton3))
                    .addComponent(jPanel18, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(24, 24, 24))
        );
        jPanel13Layout.setVerticalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel13Layout.createSequentialGroup()
                .addGap(76, 76, 76)
                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton3))
                .addGap(18, 18, 18)
                .addComponent(jPanel18, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(51, Short.MAX_VALUE))
        );

        jPanel4.setBackground(new java.awt.Color(252, 252, 252));

        jPanel5.setBackground(new java.awt.Color(252, 252, 252));
        jPanel5.setLayout(new java.awt.GridLayout(1, 9));
        jScrollPane1.setViewportView(jPanel5);

        jLabel6.setFont(new java.awt.Font("Poppins", 0, 24)); // NOI18N
        jLabel6.setText("Clearning Status");

        jButton4.setFont(new java.awt.Font("Poppins", 0, 12)); // NOI18N
        jButton4.setText("Refresh");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(28, 28, 28)
                .addComponent(jLabel6)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButton4)
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(jLabel6)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton4)
                        .addGap(18, 18, 18)))
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 371, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel26Layout = new javax.swing.GroupLayout(jPanel26);
        jPanel26.setLayout(jPanel26Layout);
        jPanel26Layout.setHorizontalGroup(
            jPanel26Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel26Layout.createSequentialGroup()
                .addGap(28, 28, 28)
                .addGroup(jPanel26Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel26Layout.createSequentialGroup()
                        .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel13, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(50, 50, 50))
        );
        jPanel26Layout.setVerticalGroup(
            jPanel26Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel26Layout.createSequentialGroup()
                .addGroup(jPanel26Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel26Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jPanel13, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(39, 39, 39))
        );

        jScrollPane5.setViewportView(jPanel26);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(46, 46, 46)
                .addComponent(jLabel1)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addComponent(jScrollPane5)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(36, 36, 36)
                .addComponent(jScrollPane5))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        loadRoomsDynamically();
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        loadWidgets();
    }//GEN-LAST:event_jButton3ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel18;
    private javax.swing.JPanel jPanel19;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel20;
    private javax.swing.JPanel jPanel21;
    private javax.swing.JPanel jPanel22;
    private javax.swing.JPanel jPanel23;
    private javax.swing.JPanel jPanel24;
    private javax.swing.JPanel jPanel25;
    private javax.swing.JPanel jPanel26;
    private javax.swing.JPanel jPanel27;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane5;
    // End of variables declaration//GEN-END:variables

}
