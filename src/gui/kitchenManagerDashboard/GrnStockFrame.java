package gui.kitchenManagerDashboard;

import com.formdev.flatlaf.FlatClientProperties;
import com.formdev.flatlaf.themes.FlatMacLightLaf;
import gui.SignIn;
import model.ModifyTables;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.Vector;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import model.MYsql;
import model.GrnItem;
import model.UnitConverter;
import raven.toast.Notifications;
import java.sql.ResultSet;
import java.sql.SQLException;

public class GrnStockFrame extends javax.swing.JFrame {

    private HashMap<String, Integer> UnitsMap;

    private HashMap<Integer, GrnItem> grnItemsMap;

    int productID;

    int supplierID;

    public GrnStockFrame() {
        initComponents();
        init();
        loadUnits();
        Notifications.getInstance().setJFrame(this);
        this.jTextField1.setText(String.valueOf(genarateUniqNumber()));
        grnItemsMap = new HashMap();
    }
    
    
    //reset All
    private void resetAll(){
        resetPraductInfo();
        jTextField2.setText("");
        this.supplierID = 0;
        this.grnItemsMap.clear();
    }
    
    
    
    //reset product info
    private void resetPraductInfo(){
        jTextField3.setText("");
        this.productID = 0;
        jFormattedTextField1.setText("");
        jComboBox1.setSelectedItem("Select");
        jFormattedTextField2.setText("0.00");
        jDateChooser1.setDate(null);
        jDateChooser2.setDate(null);
    }

    //Load Grn Intens To Table
    private void loadGrnItems() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        DefaultTableModel model = (DefaultTableModel) jTable2.getModel();
        model.setRowCount(0);

        double total = 0;
        for (Map.Entry<Integer, GrnItem> grnItem : grnItemsMap.entrySet()) {

            Vector<String> vector = new Vector();

            vector.add(String.valueOf(grnItem.getValue().getProductID()));
            vector.add(grnItem.getValue().getProductName());
            vector.add(String.valueOf(grnItem.getValue().getQty()));

            total += grnItem.getValue().getBuyingPrice();

            for (Map.Entry<String, Integer> entry : UnitsMap.entrySet()) {
                if (entry.getValue() == grnItem.getValue().getUnitID()) {
                    vector.add(entry.getKey());

                    break;
                }
            }
            vector.add(String.valueOf(grnItem.getValue().getBuyingPrice()));
            vector.add(sdf.format(grnItem.getValue().getExp()));
            vector.add(sdf.format(grnItem.getValue().getMfd()));
            model.addRow(vector);
        }
        jTable2.setModel(model);
        jTextField4.setText(String.valueOf(total));
    }

    //GenarateQuniq 6digt number
    private int genarateUniqNumber() {
        int DIGIT_COUNT = 6;
        int MAX_NUMBER = 999999;
        int MIN_NUMBER = 100000;
        Set<Integer> generatedNumbers = new HashSet<>();
        Random random = new Random();

        int number;
        do {
            number = random.nextInt((MAX_NUMBER - MIN_NUMBER) + 1) + MIN_NUMBER;
        } while (generatedNumbers.contains(number));
        generatedNumbers.add(number);

        System.out.println(number);
        return number;
    }

    //Load Units To Combobox
    private void loadUnits() {
        String unitsQuary = "SELECT * FROM `mesherment`";
        this.UnitsMap = new HashMap<>();
        Vector<String> vector = new Vector();
        vector.add("Select");

        try {
            ResultSet unitsResult = MYsql.execute(unitsQuary);

            while (unitsResult.next()) {
                this.UnitsMap.put(unitsResult.getString("mesherment.unit"), unitsResult.getInt("mesherment.id"));
                vector.add(unitsResult.getString("mesherment.unit"));
            }
            jComboBox1.setModel(new DefaultComboBoxModel(vector));
        } catch (SQLException e) {
            SignIn.logger.severe(e.getMessage());
        }

    }

    //Decromant Quantity
    private void decrimentQty() {
        if (jFormattedTextField1.getText().equals("")) {
            jFormattedTextField1.setText("0.00");
        }
        double qty = Double.parseDouble(jFormattedTextField1.getText());
        if (qty <= 0) {
            jFormattedTextField1.setText("0");
        } else {
            qty--;
            jFormattedTextField1.setText(String.valueOf(qty));
        }
    }

    //Increment quantity
    private void incrementQty() {
        if (jFormattedTextField1.getText().equals("")) {
            jFormattedTextField1.setText("0.00");
        }
        double qty = Double.parseDouble(jFormattedTextField1.getText());
        qty += 1;
        jFormattedTextField1.setText(String.valueOf(qty));
    }

    private void init() {
        setExtendedState(GrnStockFrame.MAXIMIZED_BOTH);
//quantity + - buttons
        jButton4.putClientProperty(FlatClientProperties.STYLE, "arc:50");
        jButton4.setBorderPainted(false);
        jButton5.putClientProperty(FlatClientProperties.STYLE, "arc:50");
        jButton5.setBorderPainted(false);

        ModifyTables modifyTables = new ModifyTables();
        modifyTables.modifyTables(jPanel1, jTable2, jScrollPane2);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel5 = new javax.swing.JPanel();
        jPanel6 = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jTextField2 = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jTextField1 = new javax.swing.JTextField();
        jButton3 = new javax.swing.JButton();
        jTextField3 = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        jButton4 = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        jFormattedTextField1 = new javax.swing.JFormattedTextField();
        jComboBox1 = new javax.swing.JComboBox<>();
        jFormattedTextField2 = new javax.swing.JFormattedTextField();
        jLabel6 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jDateChooser1 = new com.toedter.calendar.JDateChooser();
        jDateChooser2 = new com.toedter.calendar.JDateChooser();
        jButton7 = new javax.swing.JButton();
        jPanel7 = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable2 = new javax.swing.JTable();
        jLabel12 = new javax.swing.JLabel();
        jFormattedTextField3 = new javax.swing.JFormattedTextField();
        jTextField4 = new javax.swing.JTextField();
        jLabel14 = new javax.swing.JLabel();
        jButton2 = new javax.swing.JButton();
        jButton6 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Add New Stock | Velora Crest");

        jPanel5.setBackground(new java.awt.Color(15, 140, 130));

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 52, Short.MAX_VALUE)
        );

        jLabel7.setFont(new java.awt.Font("Poppins", 1, 36)); // NOI18N
        jLabel7.setText("Add New Stock");

        jLabel8.setFont(new java.awt.Font("Poppins", 0, 18)); // NOI18N
        jLabel8.setText("Tagline here");

        jLabel9.setFont(new java.awt.Font("Poppins", 0, 14)); // NOI18N
        jLabel9.setText("Supplier: ");

        jTextField2.setEditable(false);
        jTextField2.setFont(new java.awt.Font("Poppins", 0, 14)); // NOI18N

        jLabel10.setFont(new java.awt.Font("Poppins", 0, 14)); // NOI18N
        jLabel10.setText("grnID");

        jLabel11.setFont(new java.awt.Font("Poppins", 0, 14)); // NOI18N
        jLabel11.setText("Product");

        jButton1.setBackground(new java.awt.Color(15, 140, 130));
        jButton1.setFont(new java.awt.Font("Poppins", 0, 14)); // NOI18N
        jButton1.setForeground(new java.awt.Color(255, 255, 255));
        jButton1.setText("Select Supplier");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jTextField1.setEditable(false);
        jTextField1.setFont(new java.awt.Font("Poppins", 0, 14)); // NOI18N

        jButton3.setBackground(new java.awt.Color(15, 140, 130));
        jButton3.setFont(new java.awt.Font("Poppins", 0, 14)); // NOI18N
        jButton3.setForeground(new java.awt.Color(255, 255, 255));
        jButton3.setText("Select Product");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jTextField3.setEditable(false);
        jTextField3.setFont(new java.awt.Font("Poppins", 0, 14)); // NOI18N

        jLabel13.setFont(new java.awt.Font("Poppins", 0, 14)); // NOI18N
        jLabel13.setText("Quantity");

        jButton4.setFont(new java.awt.Font("Poppins", 1, 14)); // NOI18N
        jButton4.setForeground(new java.awt.Color(15, 140, 130));
        jButton4.setText("-");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        jButton5.setFont(new java.awt.Font("Poppins", 1, 14)); // NOI18N
        jButton5.setForeground(new java.awt.Color(15, 140, 130));
        jButton5.setText("+");
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        jFormattedTextField1.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#0.00"))));
        jFormattedTextField1.setFont(new java.awt.Font("Poppins", 0, 14)); // NOI18N

        jComboBox1.setFont(new java.awt.Font("Poppins", 0, 14)); // NOI18N
        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "grams", "kilograms", "millilitres", "litres" }));

        jFormattedTextField2.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#0.00"))));
        jFormattedTextField2.setHorizontalAlignment(javax.swing.JTextField.TRAILING);
        jFormattedTextField2.setText("0.00");
        jFormattedTextField2.setFont(new java.awt.Font("Poppins", 0, 14)); // NOI18N

        jLabel6.setFont(new java.awt.Font("Poppins", 0, 14)); // NOI18N
        jLabel6.setText("Buying Price");

        jLabel15.setFont(new java.awt.Font("Poppins", 0, 14)); // NOI18N
        jLabel15.setText("MFD");

        jLabel16.setFont(new java.awt.Font("Poppins", 0, 14)); // NOI18N
        jLabel16.setText("EXP");

        jDateChooser1.setFont(new java.awt.Font("Poppins", 0, 14)); // NOI18N

        jDateChooser2.setFont(new java.awt.Font("Poppins", 0, 14)); // NOI18N

        jButton7.setBackground(new java.awt.Color(15, 140, 130));
        jButton7.setFont(new java.awt.Font("Poppins SemiBold", 0, 14)); // NOI18N
        jButton7.setForeground(new java.awt.Color(255, 255, 255));
        jButton7.setText("Add Stock");
        jButton7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton7ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGap(46, 46, 46)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addComponent(jLabel10)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jButton3, javax.swing.GroupLayout.DEFAULT_SIZE, 212, Short.MAX_VALUE)
                            .addComponent(jTextField1))
                        .addGap(27, 27, 27)
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel6Layout.createSequentialGroup()
                                .addComponent(jLabel13)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jButton4)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jFormattedTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 50, Short.MAX_VALUE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jButton5)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jComboBox1, 0, 149, Short.MAX_VALUE))
                            .addGroup(jPanel6Layout.createSequentialGroup()
                                .addComponent(jLabel11)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jTextField3)))
                        .addGap(57, 57, 57)
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel6Layout.createSequentialGroup()
                                .addComponent(jLabel6)
                                .addGap(21, 21, 21)
                                .addComponent(jFormattedTextField2, javax.swing.GroupLayout.DEFAULT_SIZE, 235, Short.MAX_VALUE))
                            .addComponent(jButton7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(12, 12, 12)
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel15)
                            .addComponent(jLabel16))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jDateChooser2, javax.swing.GroupLayout.DEFAULT_SIZE, 179, Short.MAX_VALUE)
                            .addComponent(jDateChooser1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addComponent(jLabel7)
                    .addComponent(jLabel8))
                .addGap(46, 46, 46))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel9)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, 193, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 144, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addComponent(jLabel7)
                .addGap(0, 0, 0)
                .addComponent(jLabel8)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 12, Short.MAX_VALUE)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel10)
                            .addComponent(jLabel11)
                            .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel6)
                            .addComponent(jFormattedTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jButton3)
                            .addComponent(jLabel13)
                            .addComponent(jButton4)
                            .addComponent(jButton5)
                            .addComponent(jFormattedTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton7)))
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel15, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jDateChooser1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel16)
                            .addComponent(jDateChooser2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(18, 18, 18))
        );

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        jTable2.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Item", "Quantity", "Unit", "Buying Price", "EXP", "MFD"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTable2.getTableHeader().setReorderingAllowed(false);
        jScrollPane2.setViewportView(jTable2);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addComponent(jScrollPane2)
                .addGap(18, 18, 18))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 365, Short.MAX_VALUE)
                .addGap(18, 18, 18))
        );

        jLabel12.setFont(new java.awt.Font("Poppins SemiBold", 0, 14)); // NOI18N
        jLabel12.setText("Total :");

        jFormattedTextField3.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#0.00"))));
        jFormattedTextField3.setHorizontalAlignment(javax.swing.JTextField.TRAILING);
        jFormattedTextField3.setText("0.00");
        jFormattedTextField3.setFont(new java.awt.Font("Poppins", 0, 14)); // NOI18N

        jTextField4.setEditable(false);
        jTextField4.setFont(new java.awt.Font("Poppins", 0, 14)); // NOI18N
        jTextField4.setHorizontalAlignment(javax.swing.JTextField.TRAILING);
        jTextField4.setText("0.00");

        jLabel14.setFont(new java.awt.Font("Poppins SemiBold", 0, 14)); // NOI18N
        jLabel14.setText("Payment :");

        jButton2.setBackground(new java.awt.Color(15, 140, 130));
        jButton2.setFont(new java.awt.Font("Poppins SemiBold", 0, 14)); // NOI18N
        jButton2.setForeground(new java.awt.Color(255, 255, 255));
        jButton2.setText("Add Stock");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton6.setFont(new java.awt.Font("Poppins SemiBold", 0, 14)); // NOI18N
        jButton6.setForeground(new java.awt.Color(15, 140, 130));
        jButton6.setText("Cancel");

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGap(46, 46, 46)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addComponent(jLabel12)
                        .addGap(18, 18, 18)
                        .addComponent(jTextField4, javax.swing.GroupLayout.PREFERRED_SIZE, 191, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel14)
                        .addGap(18, 18, 18)
                        .addComponent(jFormattedTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, 188, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton6, javax.swing.GroupLayout.PREFERRED_SIZE, 144, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 144, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(46, 46, 46))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel7Layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel12)
                    .addComponent(jFormattedTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel14)
                    .addComponent(jButton2)
                    .addComponent(jButton6))
                .addGap(46, 46, 46))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel5, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel6, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        SelectSupplier selectSupplier = new SelectSupplier(this, true);
        selectSupplier.setVisible(true);
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed

        SelectProduct selectProduct = new SelectProduct(this, true, this);
        selectProduct.setVisible(true);

    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton7ActionPerformed

        Date currentDate = new Date();

        if (jTextField1.getText().equals("")) {
            JOptionPane.showMessageDialog(this, "Cant find Grn ID, Please try again.", "Error", JOptionPane.ERROR_MESSAGE);
        } else if (jTextField2.getText().equals("")) {
            JOptionPane.showMessageDialog(this, "Please Select the Supplier.", "WARNING_MESSAGE", JOptionPane.WARNING_MESSAGE);
        } else if (jTextField3.getText().equals("")) {
            JOptionPane.showMessageDialog(this, "Please Select the Prodcut.", "WARNING_MESSAGE", JOptionPane.WARNING_MESSAGE);
        } else if (jFormattedTextField2.getText().equals("")) {
            JOptionPane.showMessageDialog(this, "Please Enter the Price.", "WARNING_MESSAGE", JOptionPane.WARNING_MESSAGE);
        } else if (jComboBox1.getSelectedItem().equals("Select")) {
            JOptionPane.showMessageDialog(this, "Please Select the Unit.", "WARNING_MESSAGE", JOptionPane.WARNING_MESSAGE);
        } else if (jDateChooser1.getDate() == null) {
            JOptionPane.showMessageDialog(this, "Please Enter the Manufacher Date.", "WARNING_MESSAGE", JOptionPane.WARNING_MESSAGE);
        } else if (jDateChooser2.getDate() == null) {
            JOptionPane.showMessageDialog(this, "Please Enter the Expier Date.", "WARNING_MESSAGE", JOptionPane.WARNING_MESSAGE);
        } else if (jFormattedTextField1.getText().equals("")) {
            JOptionPane.showMessageDialog(this, "Please Enter the Quantity.", "WARNING_MESSAGE", JOptionPane.WARNING_MESSAGE);
        } else if (currentDate.after(jDateChooser2.getDate())) {
            JOptionPane.showMessageDialog(this, "This Product was Expierd. Pleace Check That Again", "WARNING_MESSAGE", JOptionPane.WARNING_MESSAGE);
        } else if (currentDate.before(jDateChooser1.getDate())) {
            JOptionPane.showMessageDialog(this, "Invalid Product Manufactur Date. Please Check Again", "WARNING_MESSAGE", JOptionPane.WARNING_MESSAGE);
        } else if (jDateChooser1.getDate().after(jDateChooser2.getDate())) {
            JOptionPane.showMessageDialog(this, "Invalid Exp date and Mfd. Please Check Again", "WARNING_MESSAGE", JOptionPane.WARNING_MESSAGE);
        } else {

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

            GrnItem grnItem = new GrnItem();
            grnItem.setProductID(this.productID);
            grnItem.setProductName(this.jTextField3.getText());
            grnItem.setQty(Double.parseDouble(this.jFormattedTextField1.getText()));
            grnItem.setUnitID(UnitsMap.get(jComboBox1.getSelectedItem().toString()));
            grnItem.setBuyingPrice(Double.parseDouble(jFormattedTextField2.getText()));
            grnItem.setExp(jDateChooser2.getDate());
            grnItem.setMfd(jDateChooser1.getDate());

            if (grnItemsMap.get(this.productID) == null) {
                grnItemsMap.put(this.productID, grnItem);
            } else {

                GrnItem found = grnItemsMap.get(this.productID);

                if (sdf.format(found.getExp()).equals(sdf.format(grnItem.getExp()))
                        && sdf.format(found.getMfd()).equals(sdf.format(grnItem.getMfd()))) {

                    try {
                        Object[] result = UnitConverter.convertAndSum(found.getQty(), found.getUnitID(), grnItem.getQty(), grnItem.getUnitID());

                        found.setQty((double) result[0]);
                        found.setUnitID((int) result[1]);

                        double newBuyngPrice = found.getBuyingPrice() + grnItem.getBuyingPrice();

                        found.setBuyingPrice(newBuyngPrice);

                    } catch (IllegalArgumentException e) {
                        JOptionPane.showMessageDialog(this, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                        SignIn.logger.severe("User :" + SignIn.employeeMap.get("employee").getEmployeeName() + " " + e.getMessage());
                    }

                } else {
                    JOptionPane.showMessageDialog(this, "GRN item already exist with different dates and prices", "Error", JOptionPane.ERROR_MESSAGE);
                }

            }

        }
        this.resetPraductInfo();
        this.loadGrnItems();
    }//GEN-LAST:event_jButton7ActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        incrementQty();
    }//GEN-LAST:event_jButton5ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        decrimentQty();
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:MM");
        SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();

        String grnQuary = "INSERT INTO `grn` (`id`,`date_time`,`suppliers_id`,`paidPrice`) VALUES ('" + jTextField1.getText() + "', '" + sdf.format(date) + "','" + this.supplierID + "','" + jTextField4.getText() + "')";

        if (grnItemsMap.size() > 0) {

            try {

                MYsql.execute(grnQuary);

                for (Map.Entry<Integer, GrnItem> entry : grnItemsMap.entrySet()) {

                    String grnItemQuary = "INSERT INTO `grn_item` (`products_id`,`qty`,`mesherment_id`,`grn_id`,`buingPrice`,`exp`,`mfd`) "
                            + "VALUES ('" + entry.getValue().getProductID() + "','" + entry.getValue().getQty() + "','" + entry.getValue().getUnitID() + "','" + jTextField1.getText() + "','" + entry.getValue().getBuyingPrice() + "','" + sdf.format(entry.getValue().getExp()) + "','" + sdf.format(entry.getValue().getMfd()) + "')";

                    String searchMainStockQuary = "SELECT `mainstock`.`stock_id`, `mainstock`.`qty`, `mainstock`.`mesherment_id` FROM `mainstock` "
                            + "INNER JOIN `grn_item` ON `mainstock`.`grn_item_id` = `grn_item`.`id` WHERE `grn_item`.`exp` = '" + sdf2.format(entry.getValue().getExp()) + "' AND `grn_item`.`mfd` = '" + sdf2.format(entry.getValue().getMfd()) + "' "
                            + "AND `mainstock`.`products_id` = '" + entry.getValue().getProductID() + "'";

                    ResultSet foundProduct = MYsql.execute(searchMainStockQuary);

                    if (foundProduct.next()) {

                        Object[] result = UnitConverter.convertAndSum(foundProduct.getDouble("mainstock.qty"), foundProduct.getInt("mainstock.mesherment_id"), entry.getValue().getQty(), entry.getValue().getUnitID());

                        Double newQty = (Double) result[0];
                        int newUnit = (int) result[1];

                        String UpdateProductQuary = "UPDATE `mainstock` SET `mainstock`.`qty` = '" + newQty + "', `mainstock`.`mesherment_id` = '" + newUnit + "' WHERE `mainstock`.`stock_id` = '" + foundProduct.getString("mainstock.stock_id") + "'";

                        MYsql.execute(grnItemQuary);
                        MYsql.execute(UpdateProductQuary);

                        Notifications.getInstance().show(Notifications.Type.WARNING, Notifications.Location.BOTTOM_RIGHT, 10000, "New GRN Item registerd Successfully. This Product Alredy In Tha Stock, Current Stock Updated. Product Name :" + entry.getValue().getProductName() + " Stock ID :" + foundProduct.getString("mainstock.stock_id"));

                        SignIn.logger.info("New Grn Item Registerd. productID :" + entry.getValue().getProductID() + " Product Name: " + entry.getValue().getProductName() + "GRNID +" + jTextField1.getText() + "Stock Updated!. Stock ID : " + foundProduct.getString("mainstock.stock_id"));

                    } else {

                        ResultSet key = MYsql.execute(grnItemQuary);
                        key.next();
                        String addNewStockQuary = "INSERT INTO `mainstock` (`grn_item_id`,`date_time`,`products_id`,`qty`,`mesherment_id`) "
                                + "VALUES ('" + key.getInt(1) + "','" + sdf.format(date) + "','" + entry.getValue().getProductID() + "','" + entry.getValue().getQty() + "','" + entry.getValue().getUnitID() + "')";

                        ResultSet resultKey = MYsql.execute(addNewStockQuary);
                        resultKey.next();
                        Notifications.getInstance().show(Notifications.Type.SUCCESS, Notifications.Location.BOTTOM_RIGHT, 10000, "New GRN Item registerd Successfully. New Stock Added!, Product Name :" + entry.getValue().getProductName() + " Stock ID :" + resultKey.getString(1));

                        SignIn.logger.info("New Grn Item Registerd. productID :" + entry.getValue().getProductID() + " Product Name: " + entry.getValue().getProductName() + "GRNID +" + jTextField1.getText() + "Stock Added!. Stock ID : " + resultKey.getString(1));

                    }

                }
                
                this.resetAll();

            } catch (SQLException e) {
                SignIn.logger.severe(e.getMessage());
                e.printStackTrace();
            }

        } else {
            JOptionPane.showMessageDialog(this, "Please Select product to register grn", "Warning", JOptionPane.WARNING_MESSAGE);
        }


    }//GEN-LAST:event_jButton2ActionPerformed

    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        FlatMacLightLaf.setup();

        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new GrnStockFrame().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JButton jButton7;
    private javax.swing.JComboBox<String> jComboBox1;
    private com.toedter.calendar.JDateChooser jDateChooser1;
    private com.toedter.calendar.JDateChooser jDateChooser2;
    private javax.swing.JFormattedTextField jFormattedTextField1;
    private javax.swing.JFormattedTextField jFormattedTextField2;
    private javax.swing.JFormattedTextField jFormattedTextField3;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable jTable2;
    private javax.swing.JTextField jTextField1;
    javax.swing.JTextField jTextField2;
    javax.swing.JTextField jTextField3;
    private javax.swing.JTextField jTextField4;
    // End of variables declaration//GEN-END:variables
}
