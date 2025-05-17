package gui.hrManager;

import com.formdev.flatlaf.FlatClientProperties;
import gui.SignIn;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import javax.swing.BorderFactory;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PiePlot;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;
import model.MYsql;
import java.sql.ResultSet;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.AreaRenderer;

public class SalaryManagement extends javax.swing.JPanel {
    
    public SalaryManagement() {
        initComponents();
        init();
    }
    
    private void init() {
        jPanel2.putClientProperty(FlatClientProperties.STYLE, "arc:80");
        jPanel4.putClientProperty(FlatClientProperties.STYLE, "arc:80");
        jPanel6.putClientProperty(FlatClientProperties.STYLE, "arc:80");
        jButton2.putClientProperty(FlatClientProperties.STYLE, "arc:80");
        jScrollPane1.setBorder(BorderFactory.createEmptyBorder());
        loadEmployeesChart();
        loadScheduleChart();
        loadRevenueExpenseAreaChart();
    }
    
    private void loadScheduleChart() {
        DefaultPieDataset dataset = new DefaultPieDataset();
        dataset.setValue("Scheduled", 300);
        dataset.setValue("On Leave", 100);
        dataset.setValue("Training", 57);

        // Create chart
        JFreeChart chart = ChartFactory.createPieChart(
                "", // Chart title
                dataset, // Dataset
                true, // Include legend
                true,
                false);

        // Customize the plot
        PiePlot plot = (PiePlot) chart.getPlot();
        plot.setSectionPaint("Scheduled", new Color(60, 186, 84));  // Green color for Scheduled
        plot.setSectionPaint("On Leave", new Color(72, 133, 237));  // Blue color for On Leave
        plot.setSectionPaint("Training", new Color(244, 81, 30));   // Orange color for Training

        // Set the plot to be a donut (create a hole in the middle)
        plot.setInteriorGap(0.02);  // Adjust this value to make the hole bigger or smaller
        plot.setOutlineVisible(false);

        // Remove default label generation and legend formatting
        plot.setLabelGenerator(null);
        plot.setShadowPaint(null);
        // Center text (the total value)
        String centralValue = "457";
        plot.setSimpleLabels(true);
        plot.setCircular(true);

        // Customize chart appearance
        chart.setBackgroundPaint(Color.white);
        
        plot.setBackgroundPaint(new java.awt.Color(252, 252, 252));
        chart.setBackgroundPaint(new java.awt.Color(252, 252, 252));

        // Create ChartPanel
        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new Dimension(500, 300));
        
        jPanel5.setLayout(new BorderLayout());
        jPanel5.add(chartPanel, BorderLayout.CENTER);
        jPanel5.validate();
    }
    
    private void loadEmployeesChart() {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        try {
            ResultSet rs = MYsql.execute("SELECT COUNT(employee.id) AS employees, "
                    + "department.name AS dep FROM employee "
                    + "INNER JOIN department ON department.id = employee.department_id "
                    + "GROUP BY department.name");
            
            while (rs.next()) {
                dataset.addValue(rs.getInt("employees"), "Employees", rs.getString("dep"));
            }
        } catch (Exception e) {
            SignIn.logger.severe(e.getMessage());
        }

        // Create a horizontal bar chart instead of a vertical one
        JFreeChart chart = ChartFactory.createBarChart(
                "Employees by Department", // Chart title
                "Department", // Range axis label (Y-axis in horizontal bar chart)
                "Employees", // Domain axis label (X-axis in horizontal bar chart)
                dataset,
                PlotOrientation.HORIZONTAL, // <<-- this makes it horizontal
                false, // Include legend
                true,
                false
        );
        
        CategoryPlot plot = chart.getCategoryPlot();
        BarRenderer renderer = (BarRenderer) plot.getRenderer();

        // Appearance tweaks
        chart.setBackgroundPaint(new java.awt.Color(252, 252, 252));
        plot.setBackgroundPaint(new java.awt.Color(252, 252, 252));
        plot.setOutlineVisible(false);
        
        chart.getTitle().setFont(new Font("Poppins", Font.PLAIN, 14));
        plot.getDomainAxis().setLabelFont(new Font("Poppins", Font.PLAIN, 14));
        plot.getDomainAxis().setTickLabelFont(new Font("Poppins", Font.PLAIN, 12));
        plot.getRangeAxis().setLabelFont(new Font("Poppins", Font.PLAIN, 14));
        plot.getRangeAxis().setTickLabelFont(new Font("Poppins", Font.PLAIN, 12));
        
        renderer.setItemMargin(0.1);
        renderer.setMaximumBarWidth(0.15);  // narrower bars
        plot.getDomainAxis().setCategoryMargin(0.2);
        
        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setBorder(BorderFactory.createEmptyBorder());
        chartPanel.setPreferredSize(new java.awt.Dimension(400, 350));
        
        jPanel3.setLayout(new BorderLayout());
        jPanel3.removeAll();  // Clear previous chart if any
        jPanel3.add(chartPanel, BorderLayout.CENTER);
        jPanel3.validate();
    }
    
    private void loadRevenueExpenseAreaChart() {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();

        // Use LinkedHashMaps to preserve insertion order (month order)
        Map<String, Double> revenueMap = new LinkedHashMap<>();
        Map<String, Double> expenseMap = new LinkedHashMap<>();
        
        try {
            // Get revenue grouped by month
            ResultSet rsRevenue = MYsql.execute("SELECT DATE_FORMAT(`dateTime`, '%Y-%m') AS `month`, SUM(`total`) AS `revenue` "
                    + "FROM `invoice` GROUP BY `month` ORDER BY `month` ASC");
            
            while (rsRevenue.next()) {
                String month = rsRevenue.getString("month");
                double revenue = rsRevenue.getDouble("revenue");
                revenueMap.put(month, revenue);
            }

            // Get expenses grouped by month
            ResultSet rsExpense = MYsql.execute("SELECT DATE_FORMAT(`date_time`, '%Y-%m') AS `month`, SUM(`paidPrice`) AS `expense` "
                    + "FROM `grn` GROUP BY `month` ORDER BY `month` ASC");
            
            while (rsExpense.next()) {
                String month = rsExpense.getString("month");
                double expense = rsExpense.getDouble("expense");
                expenseMap.put(month, expense);
            }

            // Merge both datasets into chart dataset
            Set<String> allMonths = new TreeSet<>();
            allMonths.addAll(revenueMap.keySet());
            allMonths.addAll(expenseMap.keySet());
            
            for (String month : allMonths) {
                dataset.addValue(revenueMap.getOrDefault(month, 0.0), "Revenue", month);
                dataset.addValue(expenseMap.getOrDefault(month, 0.0), "Expenses", month);
            }
            
        } catch (Exception e) {
            SignIn.logger.severe(e.getMessage());
        }

        // Create Area Chart
        JFreeChart chart = ChartFactory.createAreaChart(
                "Monthly Revenue vs Expenses", // Title
                "Month", // X-axis label
                "Amount (LKR)", // Y-axis label
                dataset
        );
        
        CategoryPlot plot = chart.getCategoryPlot();
        chart.setBackgroundPaint(new java.awt.Color(252, 252, 252));
        plot.setBackgroundPaint(new java.awt.Color(252, 252, 252));
        plot.setOutlineVisible(false);

// ➕ Transparent fill colors
        AreaRenderer renderer = (AreaRenderer) plot.getRenderer();
        renderer.setSeriesPaint(0, new Color(0, 123, 255, 100)); // Revenue - blue
        renderer.setSeriesPaint(1, new Color(220, 53, 69, 100)); // Expenses - red
        plot.setRenderer(renderer);
        // Fonts
        chart.getTitle().setFont(new Font("Poppins", Font.PLAIN, 14));
        plot.getDomainAxis().setLabelFont(new Font("Poppins", Font.PLAIN, 14));
        plot.getDomainAxis().setTickLabelFont(new Font("Poppins", Font.PLAIN, 12));
        plot.getRangeAxis().setLabelFont(new Font("Poppins", Font.PLAIN, 14));
        plot.getRangeAxis().setTickLabelFont(new Font("Poppins", Font.PLAIN, 12));

        // Setup chart panel
        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setBorder(BorderFactory.createEmptyBorder());
        chartPanel.setPreferredSize(new java.awt.Dimension(900, 350));
        
        jPanel7.removeAll();  // Clear previous chart
        jPanel7.setLayout(new BorderLayout());
        jPanel7.add(chartPanel, BorderLayout.CENTER);
        jPanel7.validate();
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        jPanel1 = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        jButton1 = new javax.swing.JButton();
        jPanel6 = new javax.swing.JPanel();
        jPanel7 = new javax.swing.JPanel();
        jButton2 = new javax.swing.JButton();

        setLayout(new java.awt.BorderLayout());

        jScrollPane1.setBackground(new java.awt.Color(255, 255, 255));

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        jLabel7.setFont(new java.awt.Font("Poppins", 1, 36)); // NOI18N
        jLabel7.setText("Payroll Management");

        jLabel8.setFont(new java.awt.Font("Poppins", 0, 18)); // NOI18N
        jLabel8.setText("Payroll Overview");

        jPanel2.setBackground(new java.awt.Color(252, 252, 252));

        jLabel3.setFont(new java.awt.Font("Poppins SemiBold", 0, 18)); // NOI18N
        jLabel3.setText("Employees by Department");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel3))
                .addGap(21, 21, 21))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(24, 24, 24))
        );

        jPanel4.setBackground(new java.awt.Color(252, 252, 252));

        jLabel4.setFont(new java.awt.Font("Poppins SemiBold", 0, 18)); // NOI18N
        jLabel4.setText("Schedule");

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 269, Short.MAX_VALUE)
        );

        jButton1.setFont(new java.awt.Font("Poppins", 0, 12)); // NOI18N
        jButton1.setText("View Schedule");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 330, Short.MAX_VALUE)
                        .addComponent(jButton1)))
                .addGap(21, 21, 21))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton1))
                .addGap(18, 18, 18)
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(22, Short.MAX_VALUE))
        );

        jPanel6.setBackground(new java.awt.Color(252, 252, 252));

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 645, Short.MAX_VALUE)
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(21, 21, 21))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(21, 21, 21))
        );

        jButton2.setBackground(new java.awt.Color(15, 140, 130));
        jButton2.setFont(new java.awt.Font("Poppins SemiBold", 0, 14)); // NOI18N
        jButton2.setForeground(new java.awt.Color(255, 255, 255));
        jButton2.setText("Manage Employee Salaries");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(46, 46, 46)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addContainerGap())
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel7, javax.swing.GroupLayout.DEFAULT_SIZE, 522, Short.MAX_VALUE)
                        .addGap(761, 761, 761))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jPanel6, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 237, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addContainerGap())))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addComponent(jLabel7)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel8)
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(9, 9, 9))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(234, Short.MAX_VALUE))))
        );

        jScrollPane1.setViewportView(jPanel1);

        add(jScrollPane1, java.awt.BorderLayout.CENTER);
    }// </editor-fold>//GEN-END:initComponents

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        FixedSalaryEmployeeFrame.getInstance().setVisible(true);
    }//GEN-LAST:event_jButton2ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JScrollPane jScrollPane1;
    // End of variables declaration//GEN-END:variables
}
