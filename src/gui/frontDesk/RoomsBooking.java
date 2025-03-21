package gui.frontDesk;

import com.formdev.flatlaf.FlatClientProperties;
import gui.SignIn;
import static gui.frontDesk.FaontDeskOverview.roomsMap;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JToggleButton;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import model.RoomStatusGUIUpdatorRoomsBooking;
import model.Rooms;
import raven.toast.Notifications;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import model.MYsql;

public class RoomsBooking extends javax.swing.JPanel {

    public FrontDeskDashBoard perent;

    String CustomerID;

    private double roomsPrice;

    public RoomsBooking(FrontDeskDashBoard perent) {
        initComponents();
        init();
        this.perent = perent;
        loadRoomToPanel();
        loadRoomToPanel();
        loadRoomToPanel();
        loadRoomToPanel();
        loadRoomToPanel();
        jTextField3.setText("0");
        setDate();
        startGUIUpdator();

    }

    //Start GUI Updator
    private void startGUIUpdator() {
        if (!RoomStatusGUIUpdatorRoomsBooking.roomsStatusUpdatingWorkerStarted == true) {
            RoomStatusGUIUpdatorRoomsBooking roomGUIUpdator = new RoomStatusGUIUpdatorRoomsBooking(this);
            roomGUIUpdator.start();
        }
    }
    //Start GUI Updator

    //Check gestCount Equals or greater than Selected Rooms gestCount
    private boolean checkRoomsPersonceCount() {

        short enterdCount = (short) Integer.parseInt(jTextField3.getText());

        short maxPersonCount = (short) Integer.parseInt(jLabel15.getText());

        return enterdCount <= maxPersonCount;

    }
    //Check gestCount Equals or greater than Selected Rooms gestCount

    //Update Room Status
    public void updateRoomStatus() {
        Component[] rooms = jPanel5.getComponents();

        for (int i = 0; i < rooms.length; i++) {

            JToggleButton roomButton = (JToggleButton) rooms[i];

            JLabel roomNumberLable = (JLabel) roomButton.getComponent(1);

            String roomNumber = roomNumberLable.getText();

            roomNumberLable = null;

            Rooms room = FaontDeskOverview.roomsMap.get(roomNumber);

            byte roomReservationStatus = (byte) room.getOccupideStatus();

            byte roomCleanStatus = (byte) room.getRoomCleanStatus();

            if (roomReservationStatus == 1) {
                roomButton.setBackground(new Color(255, 0, 0)); // Set background color RED
                roomButton.setEnabled(false);
            } else {
                roomButton.setEnabled(true);
                switch (roomCleanStatus) {
                    case 1:
                        roomButton.setBackground(new Color(60, 179, 113)); // Set background color GREEN
                        break;
                    case 3:
                        roomButton.setBackground(new Color(62, 161, 217)); // Set background color BLUE
                        break;
                    default:
                        roomButton.setBackground(new Color(116, 1, 113)); // Set background color PURPAL
                        break;
                }

            }
            SwingUtilities.updateComponentTreeUI(roomButton);
            System.out.println("RRRRRRRROOOOOOOOOOMMMMMMMMM 222222" + roomNumber);

        }
    }
    //Update Room Status

    //Set Date
    private void setDate() {
        Date date = new Date();

        jDateChooser1.setDate(date);

    }
    //Set Date

    //Load rooms
    private void loadRoomToPanel() {

        for (Map.Entry<String, Rooms> room : roomsMap.entrySet()) {

//            String key = room.getKey();
            // Create the panel
            JToggleButton roomButton = new JToggleButton(room.getValue().getRoomNo());
            roomButton.setPreferredSize(new Dimension(150, 150)); // Set panel size

            System.out.println("**********************  - " + room.getValue().getOccupideStatus());

            if (room.getValue().getOccupideStatus() == 1) {
                roomButton.setBackground(new Color(255, 0, 0)); // Set background color
            } else {

                switch (room.getValue().getRoomCleanStatus()) {
                    case 1:
                        roomButton.setBackground(new Color(60, 179, 113)); // Set background color ROOM IS CLEANED
                        break;
                    case 3:
                        roomButton.setBackground(new Color(62, 161, 217)); // Set background color  ROOM IS NOT CLEANED
                        break;
                    default:
                        roomButton.setBackground(new Color(116, 1, 113)); // Set background color  ROOM IS CLEANNING
                        break;
                }
            }

            roomButton.setLayout(new BorderLayout()); // Set layout for alignment

            // Create the "Room No" label
            JLabel titleLabel = new JLabel("Room No", SwingConstants.CENTER);
            titleLabel.setFont(new Font("Poppins", Font.PLAIN, 16)); // Set font
            titleLabel.setForeground(Color.WHITE); // Set text color

            // Create the "101" label
            JLabel roomNoLabel = new JLabel(room.getValue().getRoomNo(), SwingConstants.CENTER);
            roomNoLabel.setFont(new Font("Poppins", Font.BOLD, 24)); // Set font
            roomNoLabel.setForeground(Color.WHITE); // Set text color

            // Add labels to the panel
            roomButton.add(titleLabel, BorderLayout.NORTH);
            roomButton.add(roomNoLabel, BorderLayout.CENTER);

            // Add action listener to handle button click events
            // Add ActionListener to each button
            roomButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    JToggleButton btn = (JToggleButton) e.getSource();
                    if (btn.isSelected()) {

//                        if (jTextField4.getText().equals("Room Number")) {
//                            JLabel roomNumberLable = (JLabel) btn.getComponent(1);
//                            jTextField4.setText(roomNumberLable.getText());
//                        } else {
//
//                            JLabel roomNumberLable = (JLabel) btn.getComponent(1);
//                            String roomNumbers = jTextField4.getText();
//
//                            jTextField4.setText(roomNumbers + "/" + roomNumberLable.getText());
                        loadRoomNumbersToTextFild();
                        loadPersonCountToLable();
                        calculateSelectedRoomPriceForDays();
//
//                        }
                    } else {
//                        System.out.println("Deselected: " + btn.getText());
//                       JLabel roomNumberLable = (JLabel) btn.getComponent(1);
//                        String roomNumbers = jTextField4.getText();
//                        String[] roomNumbersArray = roomNumbers.split("/");
//                        boolean firstTime = true;
//                        String newRoomListTxt = "";
//                        for (int x = 0; x < roomNumbersArray.length; x++) {
//                            if (roomNumbersArray[x].equals(roomNumberLable.getText())) {
//                            } else {
//                                if (firstTime) {
//                                    newRoomListTxt = roomNumbersArray[x];
//                                } else {
//                                    newRoomListTxt = newRoomListTxt + "/" + roomNumbersArray[x];
//                                }
//                            }
//                            System.out.println(roomNumbersArray[x]);
//                        }
//                        if (newRoomListTxt.equals("")) {
//                            jTextField4.setText("Room Number");
//                        } else {
//                            jTextField4.setText(newRoomListTxt);
//                        }

                        loadRoomNumbersToTextFild();
                        loadPersonCountToLable();
                        calculateSelectedRoomPriceForDays();

                    }
                }
            });
            jPanel5.add(roomButton);
        }

    }
    //Load rooms

    //Load Room Numbers to textFild
    private void loadRoomNumbersToTextFild() {
        String roomNumbers = "";
        Double roomsPriceTotal = 0.00;
        boolean firstTime = true;
        int selectedRoomsCount = Integer.parseInt(jLabel15.getText());
        for (Component comp : jPanel5.getComponents()) {
            if (comp instanceof JToggleButton) {
                JToggleButton button = (JToggleButton) comp;
                if (button.isSelected()) {
                    System.out.println("Currently Selected: " + button.getText());

                    JLabel lables = (JLabel) button.getComponent(1);
                    System.out.println(lables.getText());

                    FaontDeskOverview.roomsMap.get(lables.getText()).getPrice();
                    FaontDeskOverview.roomsMap.get(lables.getText()).getRoomPersonCount();

                    selectedRoomsCount += roomsMap.get(lables.getText()).getRoomPersonCount();

                    jLabel15.setText(String.valueOf(selectedRoomsCount));

                    if (firstTime) {
                        roomNumbers = lables.getText();

                        roomsPriceTotal = FaontDeskOverview.roomsMap.get(lables.getText()).getPrice();

                    } else {
                        roomNumbers = roomNumbers + "/" + lables.getText();

                        roomsPriceTotal += FaontDeskOverview.roomsMap.get(lables.getText()).getPrice();
                    }
                    firstTime = false;

                }
                if (roomNumbers.equals("")) {
                    jTextField4.setText("Room Number");
                } else {
                    jTextField4.setText(roomNumbers);
                }
            }
        }

//        jTextField8.setText(String.valueOf(roomsPriceTotal));
        this.roomsPrice = roomsPriceTotal;

    }
    //Load Room Numbers to textFild

    //Load Roos Persons Count To Lable
    private void loadPersonCountToLable() {

        int selectedRoomsPersonCount = 0;

        jLabel15.setText("");

        for (Component comp : jPanel5.getComponents()) {
            if (comp instanceof JToggleButton) {
                JToggleButton currentButton = (JToggleButton) comp;
                if (currentButton.isSelected()) {
                    JLabel roomNumberLable = (JLabel) currentButton.getComponent(1);
                    selectedRoomsPersonCount += roomsMap.get(roomNumberLable.getText()).getRoomPersonCount();
                }
            }
        }

        jLabel15.setText(String.valueOf(selectedRoomsPersonCount));
        if (checkRoomsPersonceCount()) {

            jLabel15.setForeground(Color.green);

        } else {

            jLabel15.setForeground(Color.red);

        }

    }
    //Load Roos Persons Count To Lable

    //Calculate Rooms Price For Selected days
    private void calculateSelectedRoomPriceForDays() {

        if (jTextField7.getText().equals("Days") || jTextField7.getText().equals("0") || jTextField7.getText().equals("")) {

//            JOptionPane.showMessageDialog(this, "Invalid Day Input. please select valid date.", "Warning", JOptionPane.WARNING_MESSAGE);
        } else if (roomsPrice <= 0) {

            JOptionPane.showMessageDialog(this, "Please Select Rooms First.", "Warning", JOptionPane.WARNING_MESSAGE);

        } else {

            short daysCount = Short.valueOf(jTextField7.getText());
            jTextField8.setText(String.valueOf(roomsPrice * daysCount));
        }

    }
    //Calculate Rooms Price For Selected days

    private void init() {
        jButton3.putClientProperty("JButton.buttonType", "roundRect");
        jButton4.putClientProperty("JButton.buttonType", "roundRect");

    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel11 = new javax.swing.JPanel();
        jLabel28 = new javax.swing.JLabel();
        jLabel29 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jPanel21 = new javax.swing.JPanel();
        jPanel22 = new javax.swing.JPanel();
        jPanel23 = new javax.swing.JPanel();
        jLabel46 = new javax.swing.JLabel();
        jLabel47 = new javax.swing.JLabel();
        jLabel48 = new javax.swing.JLabel();
        jLabel49 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jTextField2 = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jTextField3 = new javax.swing.JTextField();
        jButton3 = new javax.swing.JButton();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jTextField4 = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jTextField7 = new javax.swing.JTextField();
        jTextField8 = new javax.swing.JTextField();
        jButton4 = new javax.swing.JButton();
        jDateChooser1 = new com.toedter.calendar.JDateChooser();
        jDateChooser2 = new com.toedter.calendar.JDateChooser();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jPanel5 = new javax.swing.JPanel();

        jPanel11.setBackground(new java.awt.Color(60, 179, 113));

        jLabel28.setFont(new java.awt.Font("Poppins", 1, 24)); // NOI18N
        jLabel28.setForeground(new java.awt.Color(255, 255, 255));
        jLabel28.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel28.setText("101");

        jLabel29.setFont(new java.awt.Font("Poppins", 0, 16)); // NOI18N
        jLabel29.setForeground(new java.awt.Color(255, 255, 255));
        jLabel29.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel29.setText("Room No");

        javax.swing.GroupLayout jPanel11Layout = new javax.swing.GroupLayout(jPanel11);
        jPanel11.setLayout(jPanel11Layout);
        jPanel11Layout.setHorizontalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel11Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel29, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 92, Short.MAX_VALUE)
                    .addComponent(jLabel28, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel11Layout.setVerticalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel11Layout.createSequentialGroup()
                .addContainerGap(11, Short.MAX_VALUE)
                .addComponent(jLabel29)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel28, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(15, 15, 15))
        );

        setPreferredSize(new java.awt.Dimension(1401, 981));

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        jLabel1.setFont(new java.awt.Font("Poppins", 0, 14)); // NOI18N
        jLabel1.setText("Check Out Date");

        jLabel2.setFont(new java.awt.Font("Poppins", 1, 36)); // NOI18N
        jLabel2.setText("Rooms Booking");

        jLabel3.setFont(new java.awt.Font("Poppins", 0, 14)); // NOI18N
        jLabel3.setText("Check In Date");

        jPanel21.setBackground(new java.awt.Color(240, 240, 240));
        jPanel21.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        javax.swing.GroupLayout jPanel21Layout = new javax.swing.GroupLayout(jPanel21);
        jPanel21.setLayout(jPanel21Layout);
        jPanel21Layout.setHorizontalGroup(
            jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 20, Short.MAX_VALUE)
        );
        jPanel21Layout.setVerticalGroup(
            jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 20, Short.MAX_VALUE)
        );

        jPanel22.setBackground(new java.awt.Color(60, 179, 113));
        jPanel22.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        javax.swing.GroupLayout jPanel22Layout = new javax.swing.GroupLayout(jPanel22);
        jPanel22.setLayout(jPanel22Layout);
        jPanel22Layout.setHorizontalGroup(
            jPanel22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 20, Short.MAX_VALUE)
        );
        jPanel22Layout.setVerticalGroup(
            jPanel22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 20, Short.MAX_VALUE)
        );

        jPanel23.setBackground(new java.awt.Color(217, 83, 79));
        jPanel23.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        javax.swing.GroupLayout jPanel23Layout = new javax.swing.GroupLayout(jPanel23);
        jPanel23.setLayout(jPanel23Layout);
        jPanel23Layout.setHorizontalGroup(
            jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 20, Short.MAX_VALUE)
        );
        jPanel23Layout.setVerticalGroup(
            jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 20, Short.MAX_VALUE)
        );

        jLabel46.setFont(new java.awt.Font("Poppins", 0, 12)); // NOI18N
        jLabel46.setText("Confirmed Room");

        jLabel47.setFont(new java.awt.Font("Poppins", 0, 12)); // NOI18N
        jLabel47.setText("Available Room");

        jLabel48.setFont(new java.awt.Font("Poppins", 0, 12)); // NOI18N
        jLabel48.setText("Cleaning  Room");

        jLabel49.setFont(new java.awt.Font("Poppins", 0, 14)); // NOI18N
        jLabel49.setText("Date");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(46, 46, 46)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addGap(177, 177, 177)
                        .addComponent(jLabel1))
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(jLabel49, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jPanel21, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel46)
                        .addGap(44, 44, 44)
                        .addComponent(jPanel22, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel47)
                        .addGap(53, 53, 53)
                        .addComponent(jPanel23, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel48)))
                .addContainerGap(857, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel49)
                .addGap(30, 30, 30)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(jLabel1))
                .addGap(22, 22, 22)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel48)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jPanel21, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel46)
                            .addComponent(jPanel22, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel47))
                        .addGroup(jPanel1Layout.createSequentialGroup()
                            .addGap(4, 4, 4)
                            .addComponent(jPanel23, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(15, 15, 15))
        );

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));

        jLabel4.setFont(new java.awt.Font("Poppins", 1, 18)); // NOI18N
        jLabel4.setText("Customer Details");

        jLabel5.setFont(new java.awt.Font("Poppins", 0, 16)); // NOI18N
        jLabel5.setText("Name :");

        jLabel6.setFont(new java.awt.Font("Poppins", 0, 16)); // NOI18N
        jLabel6.setText("Mobile :");

        jLabel7.setFont(new java.awt.Font("Poppins", 0, 16)); // NOI18N
        jLabel7.setText("No. of Guests :");

        jTextField1.setEditable(false);
        jTextField1.setFont(new java.awt.Font("Poppins", 0, 14)); // NOI18N
        jTextField1.setText("Customer name here");

        jTextField2.setEditable(false);
        jTextField2.setFont(new java.awt.Font("Poppins", 0, 14)); // NOI18N
        jTextField2.setText("Mobile number here");
        jTextField2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField2ActionPerformed(evt);
            }
        });

        jButton1.setBackground(new java.awt.Color(240, 240, 240));
        jButton1.setFont(new java.awt.Font("Poppins", 0, 14)); // NOI18N
        jButton1.setText("-");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setBackground(new java.awt.Color(240, 240, 240));
        jButton2.setFont(new java.awt.Font("Poppins", 0, 14)); // NOI18N
        jButton2.setText("+");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jTextField3.setEditable(false);
        jTextField3.setFont(new java.awt.Font("Poppins", 0, 14)); // NOI18N

        jButton3.setBackground(new java.awt.Color(62, 161, 217));
        jButton3.setFont(new java.awt.Font("Poppins", 0, 16)); // NOI18N
        jButton3.setForeground(new java.awt.Color(255, 255, 255));
        jButton3.setText("Select");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jLabel8.setFont(new java.awt.Font("Poppins", 1, 18)); // NOI18N
        jLabel8.setText("Room Details");

        jLabel9.setFont(new java.awt.Font("Poppins", 0, 16)); // NOI18N
        jLabel9.setText("Room No :");

        jTextField4.setEditable(false);
        jTextField4.setFont(new java.awt.Font("Poppins", 0, 14)); // NOI18N
        jTextField4.setText("Room Number");
        jTextField4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField4ActionPerformed(evt);
            }
        });

        jLabel10.setFont(new java.awt.Font("Poppins", 0, 16)); // NOI18N
        jLabel10.setText("Check in Date :");

        jLabel11.setFont(new java.awt.Font("Poppins", 0, 16)); // NOI18N
        jLabel11.setText("Check out Date :");

        jLabel12.setFont(new java.awt.Font("Poppins", 0, 16)); // NOI18N
        jLabel12.setText("Days :");

        jLabel13.setFont(new java.awt.Font("Poppins", 0, 16)); // NOI18N
        jLabel13.setText("Amount :");

        jTextField7.setEditable(false);
        jTextField7.setFont(new java.awt.Font("Poppins", 0, 14)); // NOI18N
        jTextField7.setText("Days");
        jTextField7.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTextField7MouseClicked(evt);
            }
        });
        jTextField7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField7ActionPerformed(evt);
            }
        });

        jTextField8.setEditable(false);
        jTextField8.setFont(new java.awt.Font("Poppins", 0, 14)); // NOI18N
        jTextField8.setText("Amount");
        jTextField8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField8ActionPerformed(evt);
            }
        });

        jButton4.setBackground(new java.awt.Color(255, 69, 0));
        jButton4.setFont(new java.awt.Font("Poppins", 0, 16)); // NOI18N
        jButton4.setForeground(new java.awt.Color(255, 255, 255));
        jButton4.setText("Book Now");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        jDateChooser1.setFont(new java.awt.Font("Poppins", 0, 14)); // NOI18N

        jDateChooser2.setFont(new java.awt.Font("Poppins", 0, 14)); // NOI18N
        jDateChooser2.addInputMethodListener(new java.awt.event.InputMethodListener() {
            public void caretPositionChanged(java.awt.event.InputMethodEvent evt) {
            }
            public void inputMethodTextChanged(java.awt.event.InputMethodEvent evt) {
                jDateChooser2InputMethodTextChanged(evt);
            }
        });
        jDateChooser2.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                jDateChooser2PropertyChange(evt);
            }
        });

        jLabel14.setFont(new java.awt.Font("Poppins", 0, 16)); // NOI18N
        jLabel14.setText("Maximum gest count :");

        jLabel15.setFont(new java.awt.Font("Poppins", 0, 16)); // NOI18N
        jLabel15.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel15.setText("0");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(72, 72, 72)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel6)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jTextField2))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addComponent(jLabel5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jTextField1))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel4)
                        .addGap(47, 47, 47)
                        .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addGroup(jPanel2Layout.createSequentialGroup()
                            .addComponent(jLabel14)
                            .addGap(18, 18, 18)
                            .addComponent(jLabel15, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel2Layout.createSequentialGroup()
                            .addComponent(jLabel7)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addComponent(jButton1)
                            .addGap(8, 8, 8)
                            .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(8, 8, 8)
                            .addComponent(jButton2))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel8)
                        .addGap(63, 63, 63))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel13)
                            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addGroup(jPanel2Layout.createSequentialGroup()
                                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jLabel10)
                                        .addComponent(jLabel9))
                                    .addGap(12, 12, 12))
                                .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel11)
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                                        .addComponent(jLabel12)
                                        .addGap(83, 83, 83)))))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jTextField4, javax.swing.GroupLayout.PREFERRED_SIZE, 239, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextField7, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextField8, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jDateChooser1, javax.swing.GroupLayout.PREFERRED_SIZE, 208, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jDateChooser2, javax.swing.GroupLayout.PREFERRED_SIZE, 208, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 77, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 154, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(8, 8, 8)))
                        .addGap(151, 151, 151))))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel4)
                            .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(32, 32, 32)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel5)
                            .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(12, 12, 12)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel6)
                            .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(12, 12, 12)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel7)
                            .addComponent(jButton1)
                            .addComponent(jButton2)
                            .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel14)
                            .addComponent(jLabel15)))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel8)
                        .addGap(17, 17, 17)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jLabel9)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jLabel10)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jLabel11)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jLabel12)
                                .addGap(8, 8, 8)
                                .addComponent(jLabel13))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel2Layout.createSequentialGroup()
                                        .addGap(37, 37, 37)
                                        .addComponent(jDateChooser1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(jDateChooser2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(11, 11, 11)
                                        .addComponent(jTextField7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(jPanel2Layout.createSequentialGroup()
                                        .addComponent(jTextField4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(118, 118, 118)
                                        .addComponent(jTextField8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGap(48, 48, 48)
                                .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addContainerGap(49, Short.MAX_VALUE))
        );

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));
        jPanel3.setPreferredSize(new java.awt.Dimension(864, 200));
        jPanel3.setLayout(new java.awt.BorderLayout());

        jPanel5.setLayout(new java.awt.GridLayout(2, 0, 10, 10));
        jScrollPane2.setViewportView(jPanel5);

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1397, Short.MAX_VALUE)
            .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 1397, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 356, Short.MAX_VALUE)
            .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel4Layout.createSequentialGroup()
                    .addGap(3, 3, 3)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 350, Short.MAX_VALUE)
                    .addGap(3, 3, 3)))
        );

        jPanel3.add(jPanel4, java.awt.BorderLayout.CENTER);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, 1397, Short.MAX_VALUE)
                    .addContainerGap()))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 375, Short.MAX_VALUE)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                    .addContainerGap(234, Short.MAX_VALUE)
                    .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, 356, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(391, Short.MAX_VALUE)))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void jTextField2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField2ActionPerformed

    private void jTextField7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField7ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField7ActionPerformed

    private void jTextField8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField8ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField8ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed

        RegisterNewCustomerDialog registerNewCustomer = new RegisterNewCustomerDialog(perent, this, true);

        registerNewCustomer.setVisible(true);
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed

        int customersCount = Integer.parseInt(jTextField3.getText());

        if (customersCount > 0) {
            customersCount--;
            jTextField3.setText(String.valueOf(customersCount));

        } else {
            JOptionPane.showMessageDialog(this, "Customers count shoud be grater than 0", "Warrning", JOptionPane.WARNING_MESSAGE);
        }

    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed

        int customersCount = Integer.parseInt(jTextField3.getText());

        if (customersCount >= 0) {
            customersCount++;
            jTextField3.setText(String.valueOf(customersCount));
            if (checkRoomsPersonceCount()) {
                jLabel15.setForeground(Color.green);
            } else {
                jLabel15.setForeground(Color.red);
                //  Setup notifications with jfram
                Notifications.getInstance().setJFrame(perent);
                //  Show notifications
                Notifications.getInstance().show(Notifications.Type.WARNING, Notifications.Location.BOTTOM_RIGHT, 10000, "Need To Select Rooms");
            }

        } else {
            JOptionPane.showMessageDialog(this, "Customers count shoud be grater than 0", "Warrning", JOptionPane.WARNING_MESSAGE);
        }
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jTextField4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField4ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField4ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed

        if (jTextField1.getText().equals("Customer name here")) {
            JOptionPane.showMessageDialog(this, "Please Select Customer First", "Warning", JOptionPane.WARNING_MESSAGE);
        } else if (!jTextField2.getText().matches("^07[0,1,2,4,5,6,7,8]{1}[0-9]{7}$")) {
            JOptionPane.showMessageDialog(this, "Invalid Mobile number", "Warning", JOptionPane.WARNING_MESSAGE);
        } else if (jTextField3.getText().equals("0")) {
            JOptionPane.showMessageDialog(this, "Please Enter tha Gests count.", "Warning", JOptionPane.WARNING_MESSAGE);
        } else if (!checkRoomsPersonceCount()) {
            JOptionPane.showMessageDialog(this, "Gest Count Greater Than Rooms Capasity. Please Select more Rooms.", "Warning", JOptionPane.WARNING_MESSAGE);
        } else if (jTextField4.getText().equals("Room Number")) {
            JOptionPane.showMessageDialog(this, "Please Select Rooms to Continue", "Warning", JOptionPane.WARNING_MESSAGE);
        } else if (jTextField7.getText().equals("Days")) {
            JOptionPane.showMessageDialog(this, "Please Enter Chech-Out Date.", "Warning", JOptionPane.WARNING_MESSAGE);
        } else {

            String[] selectedRoomNumbers = jTextField4.getText().split("/");

            try {

                String searchQuary = "SELECT `rooms`.`id`, `rooms`.`no` FROM `reservation_has_room`"
                        + " INNER JOIN `rooms`ON `reservation_has_room`.`rooms_id` = `rooms`.`id` "
                        + " WHERE `rooms`.`no` IN ( ";

                for (int i = 0; i < selectedRoomNumbers.length; i++) {

                    searchQuary = searchQuary + "'" + selectedRoomNumbers[i] + "'";

                    if (i < selectedRoomNumbers.length - 1) {
                        searchQuary = searchQuary + ",";
                    }

                }
                searchQuary = searchQuary + " ) AND `reservation_has_room`.`roomReservationStatus_id` = 1";

                System.out.println(searchQuary);

                ResultSet searchResult = MYsql.execute(searchQuary);

                if (searchResult.next()) {
                    JOptionPane.showMessageDialog(this, "Room Alredy Receved. Please Check Again.", "Warning", JOptionPane.WARNING_MESSAGE);
                } else {

                    Date date = new Date();

                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

                    String insertQuary = "INSERT INTO `reservation` (`customer_id`,`employee_id`,`dateTime`,`days`) "
                            + " VALUES ('" + this.CustomerID + "', '" + SignIn.employeeMap.get("employee").getEmployeeId() + "', '" + sdf.format(date) + "', '" + jTextField7.getText() + "')";

                    MYsql.execute(insertQuary);

                    ResultSet lastInsertIndexResult = MYsql.execute("SELECT  LAST_INSERT_ID()");

                    if (lastInsertIndexResult.next()) {

                        String lastInsertId = lastInsertIndexResult.getString(1);

                        for (int i = 0; i < selectedRoomNumbers.length; i++) {

                            int roomId = FaontDeskOverview.roomsMap.get(selectedRoomNumbers[i]).getRoomId();

                            String roomsInsertQuary = "INSERT INTO `reservation_has_room` (`reservation_id`,`rooms_id`,`roomReservationStatus_id`) "
                                    + " VALUES ('" + lastInsertId + "', '" + roomId + "', '" + 1 + "' )";

                            MYsql.execute(roomsInsertQuary);

                            //  Setup notifications with jfram
                            Notifications.getInstance().setJFrame(perent);
                            //  Show notifications
                            Notifications.getInstance().show(Notifications.Type.SUCCESS, Notifications.Location.BOTTOM_RIGHT, 10000, "Room " + selectedRoomNumbers[i] + "Recerved For Customer : " + jTextField1.getText());

                        }

                    }

                }

            } catch (SQLException e) {
                e.printStackTrace();

            }

        }


    }//GEN-LAST:event_jButton4ActionPerformed

    private void jTextField7MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTextField7MouseClicked
//        jTextField7.setText("");
    }//GEN-LAST:event_jTextField7MouseClicked

    private void jDateChooser2InputMethodTextChanged(java.awt.event.InputMethodEvent evt) {//GEN-FIRST:event_jDateChooser2InputMethodTextChanged

    }//GEN-LAST:event_jDateChooser2InputMethodTextChanged

    private void jDateChooser2PropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_jDateChooser2PropertyChange

        if (jDateChooser2.getDate() != null) {

            Date startDate = jDateChooser1.getDate();
            Date endDate = jDateChooser2.getDate();

            if (startDate != null && endDate != null) {
                int daysBetween = getDaysBetween(startDate, endDate);

                if (daysBetween > 0) {
                    jTextField7.setText(String.valueOf(daysBetween));
                    calculateSelectedRoomPriceForDays();
                } else {
                    JOptionPane.showMessageDialog(this, "Invalid dates Enterd.", "Warning", JOptionPane.WARNING_MESSAGE);
                    jDateChooser2.setCalendar(null);
                    jTextField7.setText("Days");
                }

                System.out.println("Number of days: " + daysBetween);
            } else {
                System.out.println("Please select both dates.");
            }

        }

    }//GEN-LAST:event_jDateChooser2PropertyChange

    private int getDaysBetween(Date startDate, Date endDate) {

        // Get the difference in milliseconds
        long diffInMillis = endDate.getTime() - startDate.getTime();
        // Convert milliseconds to days
        return (int) TimeUnit.DAYS.convert(diffInMillis, TimeUnit.MILLISECONDS);  //////////use primitive casting
    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private com.toedter.calendar.JDateChooser jDateChooser1;
    private com.toedter.calendar.JDateChooser jDateChooser2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel46;
    private javax.swing.JLabel jLabel47;
    private javax.swing.JLabel jLabel48;
    private javax.swing.JLabel jLabel49;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel21;
    private javax.swing.JPanel jPanel22;
    private javax.swing.JPanel jPanel23;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JScrollPane jScrollPane2;
    javax.swing.JTextField jTextField1;
    javax.swing.JTextField jTextField2;
    private javax.swing.JTextField jTextField3;
    private javax.swing.JTextField jTextField4;
    private javax.swing.JTextField jTextField7;
    private javax.swing.JTextField jTextField8;
    // End of variables declaration//GEN-END:variables
}
