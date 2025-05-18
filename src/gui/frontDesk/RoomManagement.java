package gui.frontDesk;

import com.formdev.flatlaf.FlatClientProperties;
import com.formdev.flatlaf.extras.FlatSVGIcon;
import gui.SignIn;
import static gui.frontDesk.FaontDeskOverview.roomsMap;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.util.Map;
import java.util.Vector;
import javax.swing.BorderFactory;
import javax.swing.DefaultListCellRenderer;
import javax.swing.DefaultListModel;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import model.ComponentStorage;
import model.ModifyTables;
import model.Rooms;
import java.sql.ResultSet;
import java.util.HashMap;
import model.MYsql;
import java.sql.SQLException;
import java.util.Calendar;
import javax.swing.JOptionPane;
import java.sql.Date;
import java.time.LocalDate;
import javax.swing.SwingUtilities;

public class RoomManagement extends javax.swing.JPanel {

    FrontDeskDashBoard perent;

    private HashMap<Integer, String> roomTypeMap;

    public RoomManagement(FrontDeskDashBoard perent) {
        this.perent = perent;
        initComponents();
        roundEges();
        jlistDisgn();
        disagnTable();
        loadRoomsTojList();
        loadRoomTypes();

    }

    //Load Room Types 
    private void loadRoomTypes() {

        if (roomTypeMap == null) {
            roomTypeMap = new HashMap();
        }

        String roomTypeQuary = "SELECT * FROM `roomtype`";

        try {
            ResultSet roomtypes = MYsql.execute(roomTypeQuary);

            while (roomtypes.next()) {
                roomTypeMap.put(roomtypes.getInt("id"), roomtypes.getString("type"));
            }
        } catch (SQLException e) {
            SignIn.logger.severe("Load Rooms > " + e.getMessage());
        }
    }

    //Load Rooms to list
    private void loadRoomsTojList() {

        Vector<String> vector = new Vector();
        DefaultListModel listModel = new DefaultListModel();

        for (Map.Entry<String, Rooms> room : roomsMap.entrySet()) {

            vector.add(room.getValue().getRoomNo());

            listModel.addElement(room.getValue().getRoomNo());

        }

        jList1.setModel(listModel);

    }
    //Load rooms to list

    //Load SVG 
    private void loadSvg() {
        FlatSVGIcon icon = new FlatSVGIcon("resource//save_icon.svg", 100, 100);
//        jLabel1.setIcon(icon)
    }
    //Load SVG 

    // table disagn
    private void disagnTable() {
        ModifyTables table = new ModifyTables();
        table.modifyTables(jTable1, jScrollPane2);
        table.modifyTables(jTable2, jScrollPane3);
    }
    // table disagn

    //jList Disagn
    private void jlistDisgn() {
        DefaultListCellRenderer renderer = new DefaultListCellRenderer();
        renderer.setHorizontalAlignment(SwingConstants.CENTER);
        jList1.setCellRenderer(renderer);
        jScrollPane1.setBorder(BorderFactory.createEmptyBorder());
    }
    //jList Disagn

    // Round Eges
    private void roundEges() {

        itemsPanel.putClientProperty(FlatClientProperties.STYLE, "arc:30");

        roomStatusBox.putClientProperty(FlatClientProperties.STYLE, "arc:999");

        roomInvoiceBtn.putClientProperty(FlatClientProperties.STYLE, "arc:999");

        jButton4.putClientProperty(FlatClientProperties.STYLE, "arc:20");
        jButton2.putClientProperty(FlatClientProperties.STYLE, "arc:20");
        jButton1.putClientProperty(FlatClientProperties.STYLE, "arc:20");
        jButton3.putClientProperty(FlatClientProperties.STYLE, "arc:20");
    }

    // Round Eges
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        itemsPanel = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jList1 = new javax.swing.JList<>();
        jLabel4 = new javax.swing.JLabel();
        roomStatusBox = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        gestIdLable = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        gestNameLabel = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        gestMobileLabel = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        guestEmailLable = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        gestReservationNo = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        gestCheckInDate = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        gestCheckOutDate = new javax.swing.JLabel();
        guestBookingSource = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jLabel14 = new javax.swing.JLabel();
        gestIdLable1 = new javax.swing.JLabel();
        roomHouseKeeper = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        roomTypeLable = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        gestIdLable3 = new javax.swing.JLabel();
        roomRateLable = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        roomTypeLable1 = new javax.swing.JLabel();
        roomTotalCostLable = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        roomTypeLable2 = new javax.swing.JLabel();
        roomAdvancePymentLable = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        roomTypeLable3 = new javax.swing.JLabel();
        roomBalanceDueLable = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        paymentMethod = new javax.swing.JLabel();
        roomInvoiceBtn = new javax.swing.JButton();
        jLabel22 = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTable2 = new javax.swing.JTable();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();

        setBackground(new java.awt.Color(255, 255, 255));
        setPreferredSize(new java.awt.Dimension(1319, 1000));

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        jLabel1.setFont(new java.awt.Font("Poppins", 1, 36)); // NOI18N
        jLabel1.setText("Room Management");

        jLabel2.setFont(new java.awt.Font("Poppins", 0, 14)); // NOI18N
        jLabel2.setText("Search");

        jTextField1.setFont(new java.awt.Font("Poppins", 0, 14)); // NOI18N
        jTextField1.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(46, 46, 46)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addGap(27, 27, 27)
                        .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 227, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel1))
                .addContainerGap(908, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(22, Short.MAX_VALUE))
        );

        itemsPanel.setBackground(new java.awt.Color(240, 240, 240));

        jList1.setBackground(new java.awt.Color(240, 240, 240));
        jList1.setFont(new java.awt.Font("Poppins", 0, 14)); // NOI18N
        jList1.setModel(new javax.swing.AbstractListModel<String>() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
        });
        jList1.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jList1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jList1MouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(jList1);

        javax.swing.GroupLayout itemsPanelLayout = new javax.swing.GroupLayout(itemsPanel);
        itemsPanel.setLayout(itemsPanelLayout);
        itemsPanelLayout.setHorizontalGroup(
            itemsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(itemsPanelLayout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10))
        );
        itemsPanelLayout.setVerticalGroup(
            itemsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(itemsPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1)
                .addGap(14, 14, 14))
        );

        jLabel4.setFont(new java.awt.Font("Poppins", 1, 18)); // NOI18N
        jLabel4.setText("ROOM 1");

        roomStatusBox.setEditable(false);
        roomStatusBox.setBackground(new java.awt.Color(15, 140, 130));
        roomStatusBox.setFont(new java.awt.Font("Poppins", 2, 12)); // NOI18N
        roomStatusBox.setForeground(new java.awt.Color(255, 255, 255));
        roomStatusBox.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        roomStatusBox.setText("Occupied");
        roomStatusBox.setDisabledTextColor(new java.awt.Color(255, 255, 255));
        roomStatusBox.setEnabled(false);

        jLabel3.setFont(new java.awt.Font("Poppins", 0, 14)); // NOI18N
        jLabel3.setText("Guest ID :");

        gestIdLable.setFont(new java.awt.Font("Poppins", 0, 14)); // NOI18N
        gestIdLable.setText("gestidcomeshear");

        jLabel5.setFont(new java.awt.Font("Poppins", 0, 14)); // NOI18N
        jLabel5.setText("Guest :");

        gestNameLabel.setFont(new java.awt.Font("Poppins", 0, 14)); // NOI18N
        gestNameLabel.setText("gestnameconeshear");

        jLabel6.setFont(new java.awt.Font("Poppins", 0, 14)); // NOI18N
        jLabel6.setText("Mobile :");

        gestMobileLabel.setFont(new java.awt.Font("Poppins", 0, 14)); // NOI18N
        gestMobileLabel.setText("gestmobilecomshear");

        jLabel7.setFont(new java.awt.Font("Poppins", 0, 14)); // NOI18N
        jLabel7.setText("Email :");

        guestEmailLable.setFont(new java.awt.Font("Poppins", 0, 14)); // NOI18N
        guestEmailLable.setText("gestemailcomshear");

        jLabel8.setFont(new java.awt.Font("Poppins", 0, 14)); // NOI18N
        jLabel8.setText("Reservation No :");

        gestReservationNo.setFont(new java.awt.Font("Poppins", 0, 14)); // NOI18N
        gestReservationNo.setText("gestReservationIdcomshear");

        jLabel9.setFont(new java.awt.Font("Poppins", 0, 14)); // NOI18N
        jLabel9.setText("Check-in Date :");

        gestCheckInDate.setFont(new java.awt.Font("Poppins", 0, 14)); // NOI18N
        gestCheckInDate.setText("checkInDateComsHear");

        jLabel10.setFont(new java.awt.Font("Poppins", 0, 14)); // NOI18N
        jLabel10.setText("Check-out Date :");

        gestCheckOutDate.setFont(new java.awt.Font("Poppins", 0, 14)); // NOI18N
        gestCheckOutDate.setText("checkOutDateComsHear");

        guestBookingSource.setFont(new java.awt.Font("Poppins", 0, 14)); // NOI18N
        guestBookingSource.setText("Online/");

        jLabel12.setFont(new java.awt.Font("Poppins", 0, 14)); // NOI18N
        jLabel12.setText("Booking Source :");

        jLabel13.setFont(new java.awt.Font("Poppins", 1, 18)); // NOI18N
        jLabel13.setText("Orders");

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "RequestI D", "Date", "Note", "Status"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane2.setViewportView(jTable1);
        if (jTable1.getColumnModel().getColumnCount() > 0) {
            jTable1.getColumnModel().getColumn(0).setResizable(false);
            jTable1.getColumnModel().getColumn(1).setResizable(false);
            jTable1.getColumnModel().getColumn(2).setResizable(false);
            jTable1.getColumnModel().getColumn(3).setResizable(false);
        }

        jLabel14.setFont(new java.awt.Font("Poppins", 0, 14)); // NOI18N
        jLabel14.setText("Room Rate:");

        gestIdLable1.setFont(new java.awt.Font("Poppins", 0, 14)); // NOI18N
        gestIdLable1.setText("LKR");

        roomHouseKeeper.setFont(new java.awt.Font("Poppins", 0, 14)); // NOI18N
        roomHouseKeeper.setText("HosekeeperIdComseHear");

        jLabel15.setFont(new java.awt.Font("Poppins", 0, 14)); // NOI18N
        jLabel15.setText("Room type :");

        roomTypeLable.setFont(new java.awt.Font("Poppins", 0, 14)); // NOI18N
        roomTypeLable.setText("Deluxe Room");

        jLabel16.setFont(new java.awt.Font("Poppins", 0, 14)); // NOI18N
        jLabel16.setText("Room type :");

        gestIdLable3.setFont(new java.awt.Font("Poppins", 0, 14)); // NOI18N
        gestIdLable3.setText("per night");

        roomRateLable.setFont(new java.awt.Font("Poppins", 0, 14)); // NOI18N
        roomRateLable.setText("1000000");

        jLabel17.setFont(new java.awt.Font("Poppins", 0, 14)); // NOI18N
        jLabel17.setText("Total Cost of Stay :");

        roomTypeLable1.setFont(new java.awt.Font("Poppins", 0, 14)); // NOI18N
        roomTypeLable1.setText("LKR");

        roomTotalCostLable.setFont(new java.awt.Font("Poppins", 0, 14)); // NOI18N
        roomTotalCostLable.setText("100000");

        jLabel18.setFont(new java.awt.Font("Poppins", 0, 14)); // NOI18N
        jLabel18.setText("Advance payments :");

        roomTypeLable2.setFont(new java.awt.Font("Poppins", 0, 14)); // NOI18N
        roomTypeLable2.setText("LKR");

        roomAdvancePymentLable.setFont(new java.awt.Font("Poppins", 0, 14)); // NOI18N
        roomAdvancePymentLable.setText("100000");

        jLabel19.setFont(new java.awt.Font("Poppins", 0, 14)); // NOI18N
        jLabel19.setText("Balance Due :");

        roomTypeLable3.setFont(new java.awt.Font("Poppins", 0, 14)); // NOI18N
        roomTypeLable3.setText("LKR");

        roomBalanceDueLable.setFont(new java.awt.Font("Poppins", 0, 14)); // NOI18N
        roomBalanceDueLable.setText("100000");

        jLabel20.setFont(new java.awt.Font("Poppins", 0, 14)); // NOI18N
        jLabel20.setText("Payment Method :");

        paymentMethod.setFont(new java.awt.Font("Poppins", 0, 14)); // NOI18N
        paymentMethod.setText("Cash");

        roomInvoiceBtn.setBackground(new java.awt.Color(62, 161, 217));
        roomInvoiceBtn.setFont(new java.awt.Font("Poppins", 0, 14)); // NOI18N
        roomInvoiceBtn.setForeground(new java.awt.Color(255, 255, 255));
        roomInvoiceBtn.setText("Invoice");
        roomInvoiceBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                roomInvoiceBtnActionPerformed(evt);
            }
        });

        jLabel22.setFont(new java.awt.Font("Poppins", 1, 18)); // NOI18N
        jLabel22.setText("Maintenance Requests");

        jTable2.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Order ID", "Date", "Time", "Cost", "Status"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTable2.getTableHeader().setReorderingAllowed(false);
        jScrollPane3.setViewportView(jTable2);
        if (jTable2.getColumnModel().getColumnCount() > 0) {
            jTable2.getColumnModel().getColumn(0).setResizable(false);
            jTable2.getColumnModel().getColumn(1).setResizable(false);
            jTable2.getColumnModel().getColumn(2).setResizable(false);
            jTable2.getColumnModel().getColumn(3).setResizable(false);
            jTable2.getColumnModel().getColumn(4).setResizable(false);
        }

        jButton1.setBackground(new java.awt.Color(62, 161, 217));
        jButton1.setFont(new java.awt.Font("Poppins", 0, 14)); // NOI18N
        jButton1.setForeground(new java.awt.Color(255, 255, 255));
        jButton1.setText("Maintenance Request");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setBackground(new java.awt.Color(62, 161, 217));
        jButton2.setFont(new java.awt.Font("Poppins", 0, 14)); // NOI18N
        jButton2.setForeground(new java.awt.Color(255, 255, 255));
        jButton2.setText("Guest History");

        jButton3.setBackground(new java.awt.Color(15, 140, 130));
        jButton3.setFont(new java.awt.Font("Poppins", 0, 14)); // NOI18N
        jButton3.setForeground(new java.awt.Color(255, 255, 255));
        jButton3.setText("Cleaner Request");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jButton4.setBackground(new java.awt.Color(217, 83, 79));
        jButton4.setFont(new java.awt.Font("Poppins", 0, 14)); // NOI18N
        jButton4.setForeground(new java.awt.Color(255, 255, 255));
        jButton4.setText("Food order");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addGap(32, 32, 32)
                .addComponent(itemsPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel4)
                                .addGap(66, 66, 66)
                                .addComponent(roomStatusBox, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 149, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(38, 38, 38))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel8)
                                            .addComponent(jLabel7)
                                            .addComponent(jLabel6)
                                            .addComponent(jLabel5)
                                            .addComponent(jLabel3)
                                            .addComponent(jLabel9)
                                            .addComponent(jLabel10)
                                            .addComponent(jLabel12))
                                        .addGap(46, 46, 46)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addComponent(guestBookingSource, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(gestReservationNo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(gestCheckInDate, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(gestCheckOutDate, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(guestEmailLable, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(gestMobileLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(gestNameLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(gestIdLable, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(19, 19, 19)
                                        .addComponent(jLabel13)))
                                .addGap(206, 206, 206)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLabel22)
                                        .addGap(0, 0, Short.MAX_VALUE))
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLabel15)
                                        .addGap(103, 103, 103)
                                        .addComponent(roomHouseKeeper, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel17)
                                            .addComponent(jLabel18)
                                            .addComponent(jLabel19)
                                            .addComponent(jLabel20)
                                            .addComponent(roomInvoiceBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(layout.createSequentialGroup()
                                                .addComponent(roomTypeLable3)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(roomBalanceDueLable, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE))
                                            .addGroup(layout.createSequentialGroup()
                                                .addComponent(roomTypeLable2)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(roomAdvancePymentLable, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE))
                                            .addGroup(layout.createSequentialGroup()
                                                .addComponent(roomTypeLable1)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(roomTotalCostLable, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE))
                                            .addComponent(paymentMethod, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGap(48, 48, 48))
                                    .addGroup(layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel16)
                                            .addComponent(jLabel14))
                                        .addGap(103, 103, 103)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(layout.createSequentialGroup()
                                                .addComponent(gestIdLable1)
                                                .addGap(10, 10, 10)
                                                .addComponent(roomRateLable)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(gestIdLable3))
                                            .addComponent(roomTypeLable, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))))
                        .addGap(118, 118, 118))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(25, 25, 25)
                                .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 181, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(41, 41, 41)
                                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 196, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 163, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(61, 61, 61))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(itemsPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addContainerGap())
                    .addGroup(layout.createSequentialGroup()
                        .addGap(38, 38, 38)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel4)
                                .addComponent(roomStatusBox, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(3, 3, 3)
                                .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel3)
                                    .addComponent(gestIdLable))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel5)
                                    .addComponent(gestNameLabel))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel6)
                                    .addComponent(gestMobileLabel)))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(28, 28, 28)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel14)
                                    .addComponent(gestIdLable1)
                                    .addComponent(gestIdLable3)
                                    .addComponent(roomRateLable))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel15)
                                    .addComponent(roomHouseKeeper)))
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel16)
                                .addComponent(roomTypeLable)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel7)
                            .addComponent(guestEmailLable))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel8)
                            .addComponent(gestReservationNo)
                            .addComponent(jLabel17)
                            .addComponent(roomTypeLable1)
                            .addComponent(roomTotalCostLable))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel9)
                            .addComponent(gestCheckInDate)
                            .addComponent(jLabel18)
                            .addComponent(roomTypeLable2)
                            .addComponent(roomAdvancePymentLable))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel10)
                            .addComponent(gestCheckOutDate)
                            .addComponent(jLabel19)
                            .addComponent(roomTypeLable3)
                            .addComponent(roomBalanceDueLable))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel20)
                            .addComponent(paymentMethod)
                            .addComponent(jLabel12)
                            .addComponent(guestBookingSource))
                        .addGap(10, 10, 10)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(93, 93, 93)
                                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 314, Short.MAX_VALUE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addComponent(roomInvoiceBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(28, 28, 28)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel13, javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel22))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(25, 25, 25))))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void roomInvoiceBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_roomInvoiceBtnActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_roomInvoiceBtnActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jList1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jList1MouseClicked

        String roomNo = jList1.getSelectedValue();
        String roomType;

        if (evt.getClickCount() == 2) {

            Rooms selectedRoom = roomsMap.get(roomNo);

            if (selectedRoom.getOccupideStatus() == 1) {
                roomStatusBox.setBackground(new Color(15, 140, 130));
                roomStatusBox.setText("Occupied");

                String reservationDetailsQuary = "SELECT * FROM `reservation` "
                        + "INNER JOIN `reservation_has_room` ON `reservation`.`id` = `reservation_has_room`.`reservation_id` "
                        + "INNER JOIN `rooms` ON `reservation_has_room`.`rooms_id`"
                        + "INNER JOIN `customer` ON `reservation`.`customer_id` = `customer`.`id` WHERE `reservation_has_room`.`roomReservationStatus_id` = '1' AND `rooms`.`no` = '" + roomNo + "'  ";
                try {

                    ResultSet reservationDetails = MYsql.execute(reservationDetailsQuary);

                    if (reservationDetails.next()) {

                        gestIdLable.setText(reservationDetails.getString("customer.id"));
                        gestNameLabel.setText(reservationDetails.getString("customer.fname") + " " + reservationDetails.getString("customer.lname"));
                        gestMobileLabel.setText(reservationDetails.getString("customer.mobile"));
                        guestEmailLable.setText(reservationDetails.getString("customer.email"));
                        gestReservationNo.setText(reservationDetails.getString("reservation.id"));
                        gestCheckInDate.setText(reservationDetails.getString("reservation.dateTime"));

                        Date date = reservationDetails.getDate("reservation.dateTime");

                        LocalDate localdate = date.toLocalDate();

                        LocalDate checkOutDate = localdate.plusDays(reservationDetails.getInt("reservation.days"));

                        gestCheckOutDate.setText(String.valueOf(checkOutDate));

                        guestBookingSource.setText("Local");

                        roomTypeLable.setText(roomTypeMap.get(reservationDetails.getInt("rooms.roomType_id")));
                        roomRateLable.setText(selectedRoom.getPrice().toString());

                        roomTotalCostLable.setText(String.valueOf(selectedRoom.getPrice() * reservationDetails.getInt("reservation.days")));
                        roomAdvancePymentLable.setText("0.00");
                        roomBalanceDueLable.setText(String.valueOf(selectedRoom.getPrice() * reservationDetails.getInt("reservation.days")));
                        paymentMethod.setText("Cash");

                    } else {
                        JOptionPane.showMessageDialog(perent, "Cannot Find Room Details, Please Try again.", "Error", JOptionPane.ERROR_MESSAGE);
                    }

                } catch (SQLException e) {
                    SignIn.logger.severe("Room Reservation details loading " + e.getMessage());
                }

            } else {
                roomStatusBox.setBackground(Color.red);
                roomStatusBox.setText("Not Occupied");

                gestIdLable.setText("Not Recived");
                gestNameLabel.setText("Not Recived");
                gestMobileLabel.setText("Not Recived");
                guestEmailLable.setText("Not Recived");
                gestReservationNo.setText("Not Recived");
                gestCheckInDate.setText("Not Recievd");
                gestCheckOutDate.setText("Not Recived");

                guestBookingSource.setText("Not Recived");
                roomTypeLable.setText("Not Recived");
                roomRateLable.setText(selectedRoom.getPrice().toString());

                roomTotalCostLable.setText("0.00");
                roomAdvancePymentLable.setText("0.00");
                roomBalanceDueLable.setText("0.00");
                paymentMethod.setText("Not Resived");

            }

        }


    }//GEN-LAST:event_jList1MouseClicked

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        if (jList1.getSelectedIndex() < 0) {
            JOptionPane.showMessageDialog(perent, "Please Select the Room to Add Maintenance Request.", "Warning", JOptionPane.WARNING_MESSAGE);
            SignIn.logger.severe("User " + SignIn.employeeMap.get("employee").getEmployeeName() + " try to go to Maintennce Requst. withoutselectiong room.");
        } else {
            perent.landPanel.removeAll();
            MaintenanceRequest maintenanceRequest = new MaintenanceRequest(jList1.getSelectedValue());
            perent.landPanel.add(maintenanceRequest, BorderLayout.CENTER);
            SwingUtilities.updateComponentTreeUI(perent.landPanel);
        }
    }//GEN-LAST:event_jButton1ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel gestCheckInDate;
    private javax.swing.JLabel gestCheckOutDate;
    private javax.swing.JLabel gestIdLable;
    private javax.swing.JLabel gestIdLable1;
    private javax.swing.JLabel gestIdLable3;
    private javax.swing.JLabel gestMobileLabel;
    private javax.swing.JLabel gestNameLabel;
    private javax.swing.JLabel gestReservationNo;
    private javax.swing.JLabel guestBookingSource;
    private javax.swing.JLabel guestEmailLable;
    private javax.swing.JPanel itemsPanel;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JList<String> jList1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTable jTable1;
    private javax.swing.JTable jTable2;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JLabel paymentMethod;
    private javax.swing.JLabel roomAdvancePymentLable;
    private javax.swing.JLabel roomBalanceDueLable;
    private javax.swing.JLabel roomHouseKeeper;
    private javax.swing.JButton roomInvoiceBtn;
    private javax.swing.JLabel roomRateLable;
    private javax.swing.JTextField roomStatusBox;
    private javax.swing.JLabel roomTotalCostLable;
    private javax.swing.JLabel roomTypeLable;
    private javax.swing.JLabel roomTypeLable1;
    private javax.swing.JLabel roomTypeLable2;
    private javax.swing.JLabel roomTypeLable3;
    // End of variables declaration//GEN-END:variables
}
