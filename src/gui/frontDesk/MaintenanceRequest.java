package gui.frontDesk;

import com.formdev.flatlaf.FlatClientProperties;
import com.formdev.flatlaf.extras.FlatSVGIcon;
import gui.SignIn;
import java.awt.Color;
import model.ModifyTables;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Vector;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import model.MYsql;
import raven.toast.Notifications;

public class MaintenanceRequest extends javax.swing.JPanel {

    HashMap<String, Integer> servisesMap;
    HashMap<String, Integer> maintenanceLevelMap;

    public MaintenanceRequest(String roomNumber) {
        initComponents();
        loadSvg();
        rountEdges();
        loadRequstServices();
        roomNumberLable.setText(roomNumber);
        prayorityLevels();
        if (SignIn.employeeMap != null) {
            jLabel4.setText(SignIn.employeeMap.get("employee").getEmployeeName());
        }
        setDate();
        loadMaintenanceRequestToTable();
    }

    public MaintenanceRequest() {
        initComponents();
        loadSvg();
        rountEdges();
        loadRequstServices();
        prayorityLevels();
        OpenRoomSelector();
        if (SignIn.employeeMap != null) {
            jLabel4.setText(SignIn.employeeMap.get("employee").getEmployeeName());
        }
        setDate();
        loadMaintenanceRequestToTable();
    }

    //Load Room Maintenance Request to Table
    private void loadMaintenanceRequestToTable() {
        String maintenanceRequstsQuary = "SELECT `maintenance`.`id`, "
                + "`maintenance`.`rooms_id`, `maintenance`.`issu`, `maintenanceservises`.`name`, "
                + "`maintenance`.`dateTime`, `maintenanceprioritylevel`.`id`, `maintenanceprioritylevel`.`leval` FROM `maintenance` "
                + "INNER JOIN `maintenanceprioritylevel` ON `maintenance`.`maintenancePriorityLevel_id` = `maintenanceprioritylevel`.`id` "
                + "INNER JOIN `maintenanceservises` ON `maintenance`.`maintenanceServises_id` = `maintenanceservises`.`id` "
                + "INNER JOIN `maintenancestatus` ON `maintenance`.`maintenanceStatus_id` = `maintenancestatus`.`id` "
                + "WHERE `maintenance`.`rooms_id` = '" + FaontDeskOverview.roomsMap.get(roomNumberLable.getText()).getRoomId() + "' ORDER BY `maintenance`.`dateTime` DESC";

        DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
        model.setRowCount(0);
        
        

        try {
            ResultSet maintenanceResult = MYsql.execute(maintenanceRequstsQuary);

            while (maintenanceResult.next()) {
                Vector<String> vector = new Vector();
                vector.add("maintenance.id");
                vector.add(roomNumberLable.getText());
                vector.add(maintenanceResult.getString("maintenance.issu"));
                vector.add(maintenanceResult.getString("maintenanceservises.name"));
                vector.add(maintenanceResult.getString("maintenance.dateTime"));
                vector.add(maintenanceResult.getString("maintenanceprioritylevel.leval"));
                model.addRow(vector);
            }
            jTable1.setModel(model);

        } catch (SQLException e) {
            SignIn.logger.severe("Maintenance Requst Loading Error " + e.getMessage());
        }

    }

    // Set Date
    private void setDate() {
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        dateLable.setText(sdf.format(date));
    }

    //Open Room Selector JDialog
    private void OpenRoomSelector() {
        new SelectRoom(FrontDeskDashBoard.getInstance(), true, this).setVisible(true);
    }

    //Reset All Filds
    private void resetFilds() {
        issuFild.setText("");
        destriptionMaintence.setText("");
        servisesComboBox.setSelectedItem("Select");
        RequestMaintenance.setText("");
        levelComboBox.setSelectedItem("Select");
    }

    //Load Maintenence Lavels
    private void prayorityLevels() {
        String prayorityQuaty = "SELECT * FROM `maintenanceprioritylevel`";
        maintenanceLevelMap = new HashMap();
        Vector<String> vector = new Vector();
        vector.add("Select");
        try {
            ResultSet proriryResult = MYsql.execute(prayorityQuaty);
            while (proriryResult.next()) {
                vector.add(proriryResult.getString("leval"));
                maintenanceLevelMap.put(proriryResult.getString("leval"), proriryResult.getInt("id"));
            }
            levelComboBox.setModel(new DefaultComboBoxModel(vector));
        } catch (SQLException e) {
            SignIn.logger.severe("Meintenance Prority Loading Error " + e.getMessage());
        }

    }

    //Load requested Services to ComboBox
    private void loadRequstServices() {
        String servisesQuary = "SELECT * FROM `maintenanceservises`";

        servisesMap = new HashMap();

        Vector<String> vector = new Vector();
        vector.add("Select");
        try {
            ResultSet servisesRsult = MYsql.execute(servisesQuary);

            while (servisesRsult.next()) {
                vector.add(servisesRsult.getString("name"));
                servisesMap.put(servisesRsult.getString("name"), servisesRsult.getInt("id"));
            }

            servisesComboBox.setModel(new DefaultComboBoxModel<>(vector));

        } catch (SQLException e) {
            SignIn.logger.severe("Maintenance Servises Loading Error. :" + e.getMessage());
        }
    }

    // Round Edges
    private void rountEdges() {
        jScrollPane1.putClientProperty(FlatClientProperties.STYLE, "arc:30");

        ModifyTables modifyTable = new ModifyTables();
        modifyTable.modifyTables(jTable1, jScrollPane3);
    }
    // Round Edges

    //Load SVG 
    private void loadSvg() {
        FlatSVGIcon saveicon = new FlatSVGIcon("resource//save_icon.svg", saveIcon.getHeight(), saveIcon.getWidth());
        saveIcon.setIcon(saveicon);

        FlatSVGIcon reseticon = new FlatSVGIcon("resource//reset_icon.svg", 30, 30);
        resetIcon.setIcon(reseticon);

        FlatSVGIcon deleteicon = new FlatSVGIcon("resource//delete-icon.svg", 30, 30);
        deleteIcon.setIcon(deleteicon);

    }
    //Load SVG 

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        dateLable = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        roomNumberLable = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        issuFild = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        destriptionMaintence = new javax.swing.JTextArea();
        saveIcon = new javax.swing.JLabel();
        resetIcon = new javax.swing.JLabel();
        deleteIcon = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jLabel11 = new javax.swing.JLabel();
        servisesComboBox = new javax.swing.JComboBox<>();
        jLabel12 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        RequestMaintenance = new javax.swing.JTextArea();
        jLabel13 = new javax.swing.JLabel();
        levelComboBox = new javax.swing.JComboBox<>();
        jPanel4 = new javax.swing.JPanel();
        jLabel14 = new javax.swing.JLabel();
        jComboBox2 = new javax.swing.JComboBox<>();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        jLabel1.setFont(new java.awt.Font("Poppins", 1, 24)); // NOI18N
        jLabel1.setText("Maintenance Request");

        jLabel2.setFont(new java.awt.Font("Poppins", 0, 12)); // NOI18N
        jLabel2.setText("Tag line text hear");

        jLabel3.setFont(new java.awt.Font("Poppins", 0, 12)); // NOI18N
        jLabel3.setText("Requester ID");

        jLabel4.setText("______________________________________________");

        jLabel5.setFont(new java.awt.Font("Poppins", 0, 12)); // NOI18N
        jLabel5.setText("Date");

        dateLable.setFont(new java.awt.Font("Poppins", 0, 12)); // NOI18N
        dateLable.setText("2024-10-31");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(37, 37, 37)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 283, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addComponent(jLabel5))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 262, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 248, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(15, 15, 15)
                                .addComponent(dateLable, javax.swing.GroupLayout.PREFERRED_SIZE, 225, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(55, 55, 55))))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(31, Short.MAX_VALUE)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4))
                .addGap(9, 9, 9)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(dateLable)))
        );

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));

        jLabel7.setFont(new java.awt.Font("Poppins", 0, 12)); // NOI18N
        jLabel7.setText("Room No");

        roomNumberLable.setFont(new java.awt.Font("Poppins", 0, 12)); // NOI18N
        roomNumberLable.setText("No room Selected");

        jLabel10.setFont(new java.awt.Font("Poppins", 0, 12)); // NOI18N
        jLabel10.setText("Issue");

        issuFild.setFont(new java.awt.Font("Poppins", 0, 12)); // NOI18N

        jLabel9.setFont(new java.awt.Font("Poppins", 0, 12)); // NOI18N
        jLabel9.setText("Description");

        destriptionMaintence.setColumns(20);
        destriptionMaintence.setFont(new java.awt.Font("Poppins", 0, 12)); // NOI18N
        destriptionMaintence.setRows(5);
        jScrollPane1.setViewportView(destriptionMaintence);

        saveIcon.setBackground(new java.awt.Color(255, 255, 255));
        saveIcon.setOpaque(true);
        saveIcon.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                saveIconMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                saveIconMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                saveIconMouseExited(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(37, 37, 37)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel9)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel7)
                                    .addComponent(jLabel10))
                                .addGap(51, 51, 51)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(issuFild)
                                    .addComponent(roomNumberLable, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 558, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(232, 232, 232)
                        .addComponent(saveIcon, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(resetIcon, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(deleteIcon, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(26, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(32, 32, 32)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(roomNumberLable, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(20, 20, 20)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10)
                    .addComponent(issuFild, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(20, 20, 20)
                .addComponent(jLabel9)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 143, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(saveIcon, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(resetIcon, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(deleteIcon, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(11, Short.MAX_VALUE))
        );

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));

        jLabel11.setFont(new java.awt.Font("Poppins", 0, 12)); // NOI18N
        jLabel11.setText("Requested Service");

        servisesComboBox.setFont(new java.awt.Font("Poppins", 0, 12)); // NOI18N
        servisesComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Electrical", "Plumber" }));

        jLabel12.setFont(new java.awt.Font("Poppins", 0, 12)); // NOI18N
        jLabel12.setText("Maintenance Required");

        RequestMaintenance.setColumns(20);
        RequestMaintenance.setFont(new java.awt.Font("Poppins", 0, 12)); // NOI18N
        RequestMaintenance.setRows(5);
        jScrollPane2.setViewportView(RequestMaintenance);

        jLabel13.setFont(new java.awt.Font("Poppins", 0, 12)); // NOI18N
        jLabel13.setText("Severity Level");

        levelComboBox.setFont(new java.awt.Font("Poppins", 0, 12)); // NOI18N
        levelComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Date" }));

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(34, 34, 34)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel13)
                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(jLabel12)
                        .addGroup(jPanel3Layout.createSequentialGroup()
                            .addComponent(jLabel11)
                            .addGap(77, 77, 77)
                            .addComponent(servisesComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 374, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addComponent(jScrollPane2))
                    .addComponent(levelComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 216, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(33, 33, 33)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel11)
                    .addComponent(servisesComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel12)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 137, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel13)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(levelComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));

        jLabel14.setFont(new java.awt.Font("Poppins", 1, 12)); // NOI18N
        jLabel14.setText("Filter By");

        jComboBox2.setFont(new java.awt.Font("Poppins", 0, 12)); // NOI18N
        jComboBox2.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Date" }));

        jTable1.setFont(new java.awt.Font("Poppins", 0, 12)); // NOI18N
        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Req.ID", "Room", "Issue", "Requested Service", "Date", "Level"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, true, true
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane3.setViewportView(jTable1);
        if (jTable1.getColumnModel().getColumnCount() > 0) {
            jTable1.getColumnModel().getColumn(0).setResizable(false);
            jTable1.getColumnModel().getColumn(1).setResizable(false);
            jTable1.getColumnModel().getColumn(2).setResizable(false);
            jTable1.getColumnModel().getColumn(3).setResizable(false);
            jTable1.getColumnModel().getColumn(4).setResizable(false);
            jTable1.getColumnModel().getColumn(5).setResizable(false);
        }

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(42, 42, 42)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 1226, Short.MAX_VALUE)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabel14)
                        .addGap(18, 18, 18)
                        .addComponent(jComboBox2, javax.swing.GroupLayout.PREFERRED_SIZE, 194, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel14)
                    .addComponent(jComboBox2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(20, 20, 20)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 222, Short.MAX_VALUE)
                .addGap(25, 25, 25))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(0, 0, 0)
                .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void saveIconMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_saveIconMouseClicked

        if (roomNumberLable.equals("No room Selected")) {
            JOptionPane.showMessageDialog(this, "Can't Find Room Number. Please Try Agein.", "Error", JOptionPane.ERROR_MESSAGE);
        } else if (jLabel4.getText().startsWith("__")) {
            JOptionPane.showMessageDialog(this, "Can't Find Requester. Please Try Agen.", "Error", JOptionPane.ERROR_MESSAGE);
        } else if (issuFild.getText().isBlank()) {
            JOptionPane.showMessageDialog(this, "Plase Enter The Issu to contine", "Warning", JOptionPane.WARNING_MESSAGE);
        } else if (destriptionMaintence.getText().isBlank()) {
            JOptionPane.showMessageDialog(this, "Please Enter The Description.", "Warning", JOptionPane.WARNING_MESSAGE);
        } else if (servisesComboBox.getSelectedItem().toString().equals("Select")) {
            JOptionPane.showMessageDialog(this, "Plase Please Select The Service Do You Need.", "Warning", JOptionPane.WARNING_MESSAGE);
        } else if (RequestMaintenance.getText().isBlank()) {
            JOptionPane.showMessageDialog(this, "Plase Enter The Gust Request. To Cotinue", "Warning", JOptionPane.WARNING_MESSAGE);
        } else if (levelComboBox.getSelectedItem().toString().equals("Select")) {
            JOptionPane.showMessageDialog(this, "Plase Select What kind of service needed.", "Warning", JOptionPane.WARNING_MESSAGE);
        } else {

            String roomNumber = roomNumberLable.getText();
            String issu = issuFild.getText();
            String description = destriptionMaintence.getText();
            String service = String.valueOf(servisesComboBox.getSelectedItem());
            String meintenanceRequest = RequestMaintenance.getText();
            String level = String.valueOf(levelComboBox.getSelectedItem());

            try {
                MYsql.execute("INSERT INTO `maintenance` (`rooms_id`,`maintenanceServises_id`,`maintenancePriorityLevel_id`,`issu`,`description`,`request`,`requestedEmployee`,`dateTime`, `maintenanceStatus_id`) "
                        + "VALUES ('" + FaontDeskOverview.roomsMap.get(roomNumber).getRoomId() + "', '" + this.servisesMap.get(service) + "','" + this.maintenanceLevelMap.get(level) + "','" + issu + "','" + description + "','" + meintenanceRequest + "','" + SignIn.employeeMap.get("employee").getEmployeeId() + "','" + dateLable.getText() + "','1')");

                SignIn.logger.severe("Maintenance Requst Added. Employee :" + SignIn.employeeMap.get("employee").getEmployeeName());
                resetFilds();
                Notifications.getInstance().show(Notifications.Type.SUCCESS, Notifications.Location.BOTTOM_RIGHT, 10000, "Maintenance Request Added To The System Successfully.");
            } catch (SQLException e) {
                SignIn.logger.severe("Maintenance Request Adding Error : " + e.getMessage());
            }

        }

    }//GEN-LAST:event_saveIconMouseClicked

    private void saveIconMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_saveIconMouseEntered
        saveIcon.setBackground(new Color(173, 216, 230));
    }//GEN-LAST:event_saveIconMouseEntered

    private void saveIconMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_saveIconMouseExited
        saveIcon.setBackground(new Color(255, 255, 255));
    }//GEN-LAST:event_saveIconMouseExited


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextArea RequestMaintenance;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JLabel dateLable;
    private javax.swing.JLabel deleteIcon;
    private javax.swing.JTextArea destriptionMaintence;
    private javax.swing.JTextField issuFild;
    private javax.swing.JComboBox<String> jComboBox2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTable jTable1;
    private javax.swing.JComboBox<String> levelComboBox;
    private javax.swing.JLabel resetIcon;
    javax.swing.JLabel roomNumberLable;
    private javax.swing.JLabel saveIcon;
    private javax.swing.JComboBox<String> servisesComboBox;
    // End of variables declaration//GEN-END:variables
}
