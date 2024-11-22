package gui.frontDesk;

import com.formdev.flatlaf.FlatClientProperties;
import com.formdev.flatlaf.extras.FlatSVGIcon;
import com.formdev.flatlaf.themes.FlatMacLightLaf;
import com.formdev.flatlaf.util.SwingUtils;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;

public class FrontDeskDashBoard extends javax.swing.JFrame {

    private static List<JButton> sideBarButtons = new ArrayList<>();

    public FrontDeskDashBoard() {
        setExtendedState(MAXIMIZED_BOTH);
        initComponents();
        init();
        loadSvg();
        loadOverview();
//        roundPannels(); /////////////Round veka eka hadanna one//////////////
    }

    // Load Maintenance Request
    private void loadMaintenanceRequst() {

        MaintenanceRequest maintenanceRequest = new MaintenanceRequest();
        landPanel.add(maintenanceRequest, BorderLayout.CENTER);
        SwingUtilities.updateComponentTreeUI(landPanel);

    }
    // Load Maintenance Request

    // Load Guest Inquaries
    private void loadGuestInquries() {
        GuestInquiries guestInquaris = new GuestInquiries();
        landPanel.add(guestInquaris, BorderLayout.CENTER);
        SwingUtilities.updateComponentTreeUI(landPanel);
    }
    // Load Guest Inquaries

    //Load Guest Registration
    private void loadGuestRegistration() {

        CustomerRegistration customerRegistration = new CustomerRegistration();
        landPanel.add(customerRegistration, BorderLayout.CENTER);
        SwingUtilities.updateComponentTreeUI(landPanel);

    }
    //Load Guest Registration

    //Load room food Orders Panel
    private void loadRoomFoodOrder() {
        RoomFoodOrder roomFoodOrder = new RoomFoodOrder();
        landPanel.add(roomFoodOrder, BorderLayout.CENTER);
        SwingUtilities.updateComponentTreeUI(landPanel);
    }
    //Load room food Orders Panel

    //Load room room management
    private void loadRoomManagement() {
        RoomManagement roomManagement = new RoomManagement();
        landPanel.add(roomManagement, BorderLayout.CENTER);
        SwingUtilities.updateComponentTreeUI(landPanel);
    }
    //Load room room management

    //Load Foog orders
    private void loadFoodOrders() {
        FoodOrders foodOrders = new FoodOrders();
        landPanel.add(foodOrders, BorderLayout.CENTER);
        SwingUtilities.updateComponentTreeUI(landPanel);
    }
    //Load Foog orders

    //Load Room Booking
    private void loadRoomBooking() {
        RoomsBooking roomsBooking = new RoomsBooking();
        landPanel.add(roomsBooking, BorderLayout.CENTER);
        SwingUtilities.updateComponentTreeUI(landPanel);
    }
    //Load Room Booking

    //Load Rooms Booking
    private void loadCheckInOut() {
        RoomReservation roomreservation = new RoomReservation();
        landPanel.add(roomreservation, BorderLayout.CENTER);
        SwingUtilities.updateComponentTreeUI(landPanel);
    }
    //Load Rooms Booking

    //private Load Booking calander
    private void loadBookingCalender() {
        BookingCalendar bookingCalender = new BookingCalendar();
        landPanel.add(bookingCalender, BorderLayout.CENTER);
        SwingUtilities.updateComponentTreeUI(landPanel);
    }
    //private Load Booking calander

    //Load over view Pannel
    private void loadOverview() {

        FaontDeskOverview productPannel = new FaontDeskOverview(this);
        landPanel.add(productPannel, BorderLayout.CENTER);
        SwingUtilities.updateComponentTreeUI(landPanel);

    }
    //Load over view Pannel

    private void init() {
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);
        FlatSVGIcon icon = new FlatSVGIcon("resource//veloraCrestSVG.svg", jLabel1.getWidth(), jLabel1.getHeight());
        jLabel1.setIcon(icon);
        jButton11.putClientProperty(FlatClientProperties.STYLE, "arc:40");
        jButton12.putClientProperty(FlatClientProperties.STYLE, "arc:40");
        jButton12.setBorderPainted(false);
        jButton13.putClientProperty(FlatClientProperties.STYLE, "arc:40");
        jButton13.setBorderPainted(false);

        // add the sidebar buttons
        sideBarButtons.add(jButton1);
        sideBarButtons.add(jButton2);
        sideBarButtons.add(jButton3);
        sideBarButtons.add(jButton4);
        sideBarButtons.add(jButton5);
        sideBarButtons.add(jButton6);
        sideBarButtons.add(jButton7);
        sideBarButtons.add(jButton8);
        sideBarButtons.add(jButton9);
        sideBarButtons.add(jButton10);
        sideBarButtonAnimate(jButton1);

        for (JButton sideBarButton : sideBarButtons) {
            sideBarButton.putClientProperty(FlatClientProperties.STYLE, "arc:50");
        }

    }

    private void sideBarButtonAnimate(JButton button) {
        List<JButton> newButtons = new ArrayList<>();
        newButtons.addAll(sideBarButtons);
        newButtons.remove(button);
        int buttonWidth = button.getWidth();
        int buttonHeight = button.getHeight();
        Thread t = new Thread(
                () -> {
                    for (int i = buttonWidth; i <= 305; i += 1) {
                        int finall = i;
                        SwingUtilities.invokeLater(() -> {
                            button.setSize(finall, buttonHeight);

                        });

                        try {

                            Thread.sleep(2);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
        );

        int restHeight = newButtons.get(1).getHeight();

        for (JButton newButton : newButtons) {
            int restWidth = newButton.getWidth();
            if (restWidth > 234) {
                Thread t2 = new Thread(
                        () -> {
                            for (int i = restWidth; i >= 234; i -= 1) {
                                int finall = i;
                                SwingUtilities.invokeLater(() -> {

                                    newButton.setSize(finall, buttonHeight);
                                });

                                try {

                                    Thread.sleep(2);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                );
                t2.start();
            }
            newButton.setFont(new java.awt.Font("Poppins", 0, 12));
            button.setFont(new java.awt.Font("Poppins SemiBold", 0, 16));
        }

        t.start();

    }

    private static void scalePanelWidth(JPanel panelToScale, JPanel referencePanel) {
        int referenceWidth = referencePanel.getWidth();
        // Set the preferred size based on the reference panel's width
        panelToScale.setPreferredSize(new Dimension(referenceWidth, panelToScale.getHeight()));
        // Update the UI to reflect changes
        SwingUtilities.updateComponentTreeUI(referencePanel.getParent());
    }

    private void loadSvg() {
//        FlatSVGIcon icon = new FlatSVGIcon("resource//veloraCrestSVG.svg", 100, 100);
//        jLabel1.setIcon(icon);

//        FlatSVGIcon micon = new FlatSVGIcon("resource//message.svg", massageIcon.getHeight(), massageIcon.getWidth());
//        massageIcon.setIcon(micon);
//
//        FlatSVGIcon nicon = new FlatSVGIcon("resource//notification.svg", notificationIcon.getHeight(), notificationIcon.getWidth());
//        notificationIcon.setIcon(nicon);
//
//        FlatSVGIcon logouticon = new FlatSVGIcon("resource//loguot.svg", 30, 30);
//        logoutIcon.setIcon(logouticon);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        jButton6 = new javax.swing.JButton();
        jButton7 = new javax.swing.JButton();
        jButton8 = new javax.swing.JButton();
        jButton9 = new javax.swing.JButton();
        jButton10 = new javax.swing.JButton();
        jButton11 = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jButton12 = new javax.swing.JButton();
        jButton13 = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        landPanel = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Dashboard | Front Desk");
        setUndecorated(true);

        jPanel1.setBackground(new java.awt.Color(0, 140, 130));

        jButton1.setFont(new java.awt.Font("Poppins SemiBold", 0, 16)); // NOI18N
        jButton1.setText("Overview");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setFont(new java.awt.Font("Poppins", 0, 12)); // NOI18N
        jButton2.setText("Booking Calendar");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton3.setFont(new java.awt.Font("Poppins", 0, 12)); // NOI18N
        jButton3.setText("Rooms Booking");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jButton4.setFont(new java.awt.Font("Poppins", 0, 12)); // NOI18N
        jButton4.setText("Check In/Out");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        jButton5.setFont(new java.awt.Font("Poppins", 0, 12)); // NOI18N
        jButton5.setText("Room Management");
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        jButton6.setFont(new java.awt.Font("Poppins", 0, 12)); // NOI18N
        jButton6.setText("Room Food Orders");
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });

        jButton7.setFont(new java.awt.Font("Poppins", 0, 12)); // NOI18N
        jButton7.setText("Food Orders");
        jButton7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton7ActionPerformed(evt);
            }
        });

        jButton8.setFont(new java.awt.Font("Poppins", 0, 12)); // NOI18N
        jButton8.setText("Guest Registration");
        jButton8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton8ActionPerformed(evt);
            }
        });

        jButton9.setFont(new java.awt.Font("Poppins", 0, 12)); // NOI18N
        jButton9.setText("Guest Inquiries");
        jButton9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton9ActionPerformed(evt);
            }
        });

        jButton10.setFont(new java.awt.Font("Poppins", 0, 12)); // NOI18N
        jButton10.setText("Maintenance Requests");
        jButton10.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton10ActionPerformed(evt);
            }
        });

        jButton11.setFont(new java.awt.Font("Poppins", 0, 14)); // NOI18N
        jButton11.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resource/LogoutPng.png"))); // NOI18N

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jButton11, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(86, 86, 86)
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(24, 24, 24)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jButton1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jButton10, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jButton9, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jButton8, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jButton7, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jButton6, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jButton5, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jButton4, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jButton3, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jButton2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 234, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(46, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(50, 50, 50)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(47, 47, 47)
                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButton5, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButton6, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButton7, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButton8, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButton9, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButton10, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(50, 50, 50)
                .addComponent(jButton11, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18))
        );

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));

        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resource/userIcon.png"))); // NOI18N

        jButton12.setBackground(new java.awt.Color(250, 250, 250));
        jButton12.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resource/message-25.png"))); // NOI18N

        jButton13.setBackground(new java.awt.Color(250, 250, 250));
        jButton13.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resource/notification-25.png"))); // NOI18N

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap(816, Short.MAX_VALUE)
                .addComponent(jButton13, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton12, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(20, 20, 20))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton13, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(10, Short.MAX_VALUE))
        );

        jPanel3.setBackground(new java.awt.Color(250, 250, 250));
        jPanel3.setLayout(new java.awt.BorderLayout());

        jScrollPane1.setBorder(null);
        jScrollPane1.setHorizontalScrollBar(null);

        landPanel.setBackground(new java.awt.Color(255, 255, 255));
        landPanel.setLayout(new java.awt.BorderLayout());
        jScrollPane1.setViewportView(landPanel);

        jPanel3.add(jScrollPane1, java.awt.BorderLayout.CENTER);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        sideBarButtonAnimate(jButton1);
        landPanel.removeAll();
        loadOverview();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        sideBarButtonAnimate(jButton2);
        landPanel.removeAll();
        loadBookingCalender();
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        sideBarButtonAnimate(jButton3);
        landPanel.removeAll();
        loadRoomBooking();
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        sideBarButtonAnimate(jButton4);

        landPanel.removeAll();
        loadCheckInOut();

    }//GEN-LAST:event_jButton4ActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        sideBarButtonAnimate(jButton5);
        landPanel.removeAll();
        loadRoomManagement();

    }//GEN-LAST:event_jButton5ActionPerformed

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
        sideBarButtonAnimate(jButton6);
        landPanel.removeAll();
        loadRoomFoodOrder();
    }//GEN-LAST:event_jButton6ActionPerformed

    private void jButton7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton7ActionPerformed
        sideBarButtonAnimate(jButton7);
        landPanel.removeAll();
        loadFoodOrders();
    }//GEN-LAST:event_jButton7ActionPerformed

    private void jButton8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton8ActionPerformed
        sideBarButtonAnimate(jButton8);
        landPanel.removeAll();
        loadGuestRegistration();
    }//GEN-LAST:event_jButton8ActionPerformed

    private void jButton9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton9ActionPerformed
        sideBarButtonAnimate(jButton9);
        landPanel.removeAll();
        loadGuestInquries();
    }//GEN-LAST:event_jButton9ActionPerformed

    private void jButton10ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton10ActionPerformed
        sideBarButtonAnimate(jButton10);
        landPanel.removeAll();
        loadMaintenanceRequst();
    }//GEN-LAST:event_jButton10ActionPerformed

    public static void main(String args[]) {
        /* Set the Nimbus look and feel */

        FlatMacLightLaf.setup();

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new FrontDeskDashBoard().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton10;
    private javax.swing.JButton jButton11;
    private javax.swing.JButton jButton12;
    private javax.swing.JButton jButton13;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JButton jButton7;
    private javax.swing.JButton jButton8;
    private javax.swing.JButton jButton9;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JPanel landPanel;
    // End of variables declaration//GEN-END:variables

    public JButton pressBookNowButton() {
        return jButton3;
    }

    public JButton pressSelectCustomerButton() {
        return jButton8;
    }

}
