package gui;

import com.formdev.flatlaf.extras.FlatSVGIcon;
import gui.frontDesk.FrontDeskDashBoard;
import gui.housekeepingManager.HouseKeepingManagerDashboard;
import gui.housekeepingStaff.housekeepingStaffDashboard;
import gui.hrManager.HRManagerDashboard;
import gui.kitchenManagerDashboard.KitchenManagerDashboard;
import gui.managerOrDirectorDashboard.ManagerDashboard;
import javax.swing.JDialog;
import model.Loggers;
import java.util.logging.Logger;
import model.MYsql;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import javax.swing.JOptionPane;
import model.EmployeeDetails;

public class SignIn extends javax.swing.JDialog {

    public static final Logger logger = Loggers.getLogger();

    public static HashMap<String, EmployeeDetails> employeeMap = new HashMap();
    

    public SignIn(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();

        // Ensure the dialog stays on top
        setAlwaysOnTop(true);

        setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);

        setSvg();
    }

    private void setSvg() {

        FlatSVGIcon vclogo = new FlatSVGIcon("resource//veloraCrestSVG.svg", logo.getWidth(), logo.getHeight());
        logo.setIcon(vclogo);

    }
    
    
    //Direct Employee to His Window
    private void openEmployeeWindow() {

        if (employeeMap.get("employee").getEmployeeDepartment() == 1) { //Front Desk Department

            if (employeeMap.get("employee").getEmployeeType() == 1) { // employee

//                FrontDeskDashBoard frontDesk = new FrontDeskDashBoard();
//                frontDesk.setVisible(true);
            } else if (employeeMap.get("employee").getEmployeeType() == 2) {

                this.dispose();

                FrontDeskDashBoard frontDesk = new FrontDeskDashBoard();
                frontDesk.setVisible(true);

                Loggers.logInfo("Front Desk Employee " + employeeMap.get("employee").getEmployeeName() + " Log In To System");

            }

        } else if (employeeMap.get("employee").getEmployeeDepartment() == 2) {  //Kitchen Department

            if (employeeMap.get("employee").getEmployeeType() == 1) {  //Kitchen Manager

                KitchenManagerDashboard kitchenManager = new KitchenManagerDashboard();
                kitchenManager.setVisible(true);

                Loggers.logInfo("Kitchen Manager " + employeeMap.get("employee").getEmployeeName() + " Log In To System");

            } else if (employeeMap.get("employee").getEmployeeType() == 2) {  //Kitchen Staff

                DashboardKitchenStaff kishenStaffDashbord = new DashboardKitchenStaff();
                kishenStaffDashbord.setVisible(true);

                Loggers.logInfo("Ktchen Staff Member " + employeeMap.get("employee").getEmployeeName() + " Log In To System");

            }

        } else if (employeeMap.get("employee").getEmployeeDepartment() == 3) {  // Housekeeping

            if (employeeMap.get("employee").getEmployeeType() == 1) {

                HouseKeepingManagerDashboard housekeepingManager = new HouseKeepingManagerDashboard();
                housekeepingManager.setVisible(true);

                Loggers.logInfo("Housekeeping Manager " + employeeMap.get("employee").getEmployeeName() + " Log In To System");

            } else if (employeeMap.get("employee").getEmployeeType() == 2) {

                housekeepingStaffDashboard houseKeepingStaff = new housekeepingStaffDashboard();
                houseKeepingStaff.setVisible(true);

                Loggers.logInfo("Housekeeping Staff member " + employeeMap.get("employee").getEmployeeName() + " Log In To System");

            }

        } else if (employeeMap.get("employee").getEmployeeDepartment() == 4) {

            if (employeeMap.get("employee").getEmployeeType() == 1) {

                HRManagerDashboard hrManager = new HRManagerDashboard();
                hrManager.setVisible(true);

                Loggers.logInfo("Human resorses Manager " + employeeMap.get("employee").getEmployeeName() + " Log In To System");

            } else if (employeeMap.get("employee").getEmployeeType() == 2) {

            }

        } else if (employeeMap.get("employee").getEmployeeDepartment() == 5) {

            if (employeeMap.get("employee").getEmployeeType() == 1) {

                ManagerDashboard genaralManager = new ManagerDashboard();
                genaralManager.setVisible(true);

                Loggers.logInfo("Genaral Manager " + employeeMap.get("employee").getEmployeeName() + " Log In To System");

            } else if (employeeMap.get("employee").getEmployeeType() == 2) {

            }

        }

    }
    //Direct Employee to His Window
    

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        emailFild = new javax.swing.JTextField();
        passwordFild = new javax.swing.JPasswordField();
        jLabel3 = new javax.swing.JLabel();
        loginButton = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        logo = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(15, 140, 130));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Email");

        jLabel2.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("Password");

        jLabel3.setBackground(new java.awt.Color(255, 51, 51));
        jLabel3.setText("jLabel3");
        jLabel3.setOpaque(true);

        loginButton.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        loginButton.setForeground(new java.awt.Color(15, 140, 130));
        loginButton.setText("Login");
        loginButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                loginButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(82, 82, 82)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(43, 43, 43)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(passwordFild, javax.swing.GroupLayout.DEFAULT_SIZE, 276, Short.MAX_VALUE)
                    .addComponent(emailFild))
                .addGap(35, 35, 35)
                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(36, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(loginButton, javax.swing.GroupLayout.PREFERRED_SIZE, 163, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(193, 193, 193))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(112, 112, 112)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(emailFild, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(28, 28, 28)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, 37, Short.MAX_VALUE)
                    .addComponent(passwordFild))
                .addGap(45, 45, 45)
                .addComponent(loginButton, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(127, Short.MAX_VALUE))
        );

        jPanel2.setBackground(new java.awt.Color(15, 140, 130));

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(27, 27, 27)
                .addComponent(logo, javax.swing.GroupLayout.PREFERRED_SIZE, 197, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(46, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(73, 73, 73)
                .addComponent(logo, javax.swing.GroupLayout.PREFERRED_SIZE, 184, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void loginButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_loginButtonActionPerformed

        try {

            String email = emailFild.getText();
            String password = String.valueOf(passwordFild.getPassword());

            if (email.isEmpty()) {

                JOptionPane.showMessageDialog(this, "Please Enter Your Email", "Warning", JOptionPane.WARNING_MESSAGE);
                logger.warning("User try to login to system wothout enter email address.");
                emailFild.grabFocus();

            } else if (password.isEmpty()) {

                JOptionPane.showMessageDialog(this, "Please Enter Your Password", "Warning", JOptionPane.WARNING_MESSAGE);
                logger.warning("User try to login to system wothout enter Password.");
                passwordFild.grabFocus();

            } else {

                String quary = "SELECT * FROM `employee` WHERE `email` = '" + email + "' AND `password` = '" + password + "'";

                ResultSet result = MYsql.execute(quary);

                if (result.next()) {
                    
                    EmployeeDetails employeeDetails = new EmployeeDetails();
                    
                    employeeDetails.setEmployeeName(result.getString("fname") + " "+ result.getString("lname"));
                    employeeDetails.setEmployeeId(result.getInt("id"));
                    employeeDetails.setEmployeeEmail(result.getString("email"));
                    employeeDetails.setEmployeeDepartment(result.getInt("department_id"));
                    employeeDetails.setEmployeeType(result.getInt("Employee_type_id"));
                    
                    employeeMap.put("employee", employeeDetails);
                    
                    

                } else {
                    JOptionPane.showMessageDialog(this, "Invalid Email or Password", "Warning", JOptionPane.WARNING_MESSAGE);
                    logger.warning("User Enterd Invalid email or password.");
                }

            }
        } catch (SQLException e) {

            Loggers.logError(e.getMessage());
        }

    }//GEN-LAST:event_loginButtonActionPerformed

    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(SignIn.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(SignIn.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(SignIn.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(SignIn.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                SignIn dialog = new SignIn(new javax.swing.JFrame(), true);
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField emailFild;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JButton loginButton;
    private javax.swing.JLabel logo;
    private javax.swing.JPasswordField passwordFild;
    // End of variables declaration//GEN-END:variables
}
