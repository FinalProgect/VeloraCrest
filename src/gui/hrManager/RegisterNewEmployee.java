package gui.hrManager;

import com.formdev.flatlaf.FlatClientProperties;
import com.formdev.flatlaf.themes.FlatMacLightLaf;
import gui.SignIn;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import javax.swing.BorderFactory;
import model.ComponentStorage;
import model.Loggers;
import model.ModifyTables;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Vector;
import javax.swing.DefaultComboBoxModel;
import javax.swing.table.DefaultTableModel;
import model.MYsql;
import model.Validation;
import raven.toast.Notifications;

public class RegisterNewEmployee extends javax.swing.JFrame {

    HashMap<String, String> genderMap = new HashMap<>();
    HashMap<String, String> departmentMap = new HashMap<>();
    HashMap<String, String> positionMap = new HashMap<>();
    HashMap<String, String> cityMap = new HashMap<>();
    HashMap<String, String> districtMap = new HashMap<>();

    public RegisterNewEmployee() {
        initComponents();
        Notifications.getInstance().setJFrame(this);
        init();
    }

    private void init() {
        setExtendedState(RegisterNewEmployee.MAXIMIZED_BOTH);
        jScrollPane1.setBorder(BorderFactory.createEmptyBorder());
        ModifyTables modifyTables = new ModifyTables();
        modifyTables.modifyTables(jPanel6, jTable2, jScrollPane3);

        jPanel3.putClientProperty(FlatClientProperties.STYLE, "arc:90");
        jPanel4.putClientProperty(FlatClientProperties.STYLE, "arc:90");
        jPanel5.putClientProperty(FlatClientProperties.STYLE, "arc:90");

        jButton1.putClientProperty(FlatClientProperties.STYLE, "arc:90");
        jButton2.putClientProperty(FlatClientProperties.STYLE, "arc:90");
        jButton3.putClientProperty(FlatClientProperties.STYLE, "arc:90");

        loadGenderDepartmentPositionBoxes();
        loadEmployeeTable();
        loadDistricts();
        loadCity();
        jLabel2.setVisible(false);
        jLabel1.setVisible(false);

        jTable2.grabFocus();
    }

//    load cities
    private void loadCity() {
        String district = String.valueOf(jComboBox7.getSelectedItem());
        try {
            ResultSet citySet = MYsql.execute("SELECT * FROM `city` WHERE `distict_id` = '" + districtMap.get(district) + "' ");
            Vector<String> districtVector = new Vector<>();
            while (citySet.next()) {
                districtVector.add(citySet.getString("name"));
                cityMap.put(citySet.getString("name"), citySet.getString("id"));

            }
            DefaultComboBoxModel model = new DefaultComboBoxModel(districtVector);
            jComboBox8.setModel(model);
        } catch (Exception e) {
            e.printStackTrace();
            Loggers.logWarning(e.getMessage());
        }
    }

//    load districts
    private void loadDistricts() {
        try {
            ResultSet districtSet = MYsql.execute("SELECT * FROM `distict` ");
            Vector<String> districtVector = new Vector<>();
            while (districtSet.next()) {
                districtVector.add(districtSet.getString("name"));
                districtMap.put(districtSet.getString("name"), districtSet.getString("id"));

            }
            DefaultComboBoxModel model = new DefaultComboBoxModel(districtVector);
            jComboBox7.setModel(model);
        } catch (Exception e) {
            e.printStackTrace();
            Loggers.logWarning(e.getMessage());
        }
    }

//    load genders departments and positions
    private void loadGenderDepartmentPositionBoxes() { // loads gender, department, and position comboboxes
        try {
//            load genders
            ResultSet genderSet = MYsql.execute("SELECT * FROM `gender` ");

            Vector<String> genderVector = new Vector<>();
            while (genderSet.next()) {
                genderVector.add(genderSet.getString("gender"));
                genderMap.put(genderSet.getString("gender"), genderSet.getString("id"));
            }
            DefaultComboBoxModel genderModel = new DefaultComboBoxModel(genderVector);
            jComboBox3.setModel(genderModel);

//            load Departments
            ResultSet departmentSet = MYsql.execute("SELECT * FROM `department` ");

            Vector<String> DepartmentVector = new Vector<>();
            while (departmentSet.next()) {
                DepartmentVector.add(departmentSet.getString("name"));
                departmentMap.put(departmentSet.getString("name"), departmentSet.getString("id"));
            }
            DefaultComboBoxModel departmentModel = new DefaultComboBoxModel(DepartmentVector);
            jComboBox4.setModel(departmentModel);

//            load positions
            ResultSet positionSet = MYsql.execute("SELECT * FROM `employee_type` ");

            Vector<String> positionVector = new Vector<>();
            while (positionSet.next()) {
                positionVector.add(positionSet.getString("type"));
                positionMap.put(positionSet.getString("type"), positionSet.getString("id"));
            }
            DefaultComboBoxModel positionModel = new DefaultComboBoxModel(positionVector);
            jComboBox5.setModel(positionModel);

            System.out.println(genderMap + " " + " " + departmentMap + " " + positionMap);
        } catch (Exception e) {
            e.printStackTrace();
            Loggers.logWarning(e.getMessage());
        }
    }
//    load employee table

    private void loadEmployeeTable() {
        String search = "";
        if (jToggleButton1.isSelected()) {
            String nic = jTextField7.getText();
            String mobile = jTextField6.getText();

            if (!nic.isBlank() || !mobile.isBlank()) {
                search = " WHERE ";
                if (!nic.isBlank() && mobile.isBlank()) {
//                  nic is not blank
                    search += " `nic` LIKE '%" + nic + "%' ";
                } else if (nic.isBlank() && !mobile.isBlank()) {
//                  mobile is not blank
                    search += " `mobile` LIKE '%" + mobile + "%' ";
                } else if (!nic.isBlank() && !mobile.isBlank()) {
//                  nic and mobile not blank
                    search += " `nic` LIKE '%" + nic + "%' AND `mobile` LIKE '%" + mobile + "%' ";
                }
            }
        }
        try {
            ResultSet employee_set = MYsql.execute("SELECT `employee`.`id`,`fullName`,`name`,`type` FROM "
                    + "`employee` INNER JOIN `department` ON `department`.`id` = `employee`.`department_id` "
                    + "INNER JOIN `employee_type` ON `employee_type`.`id` = `employee`.`Employee_type_id` " + search);
            DefaultTableModel employeeModel = (DefaultTableModel) jTable2.getModel();
            employeeModel.setRowCount(0);
            while (employee_set.next()) {
                Vector<String> employeeVector = new Vector();
                employeeVector.add(employee_set.getString("employee.id"));
                employeeVector.add(employee_set.getString("fullName"));
                employeeVector.add(employee_set.getString("name"));
                employeeVector.add(employee_set.getString("type"));

                employeeModel.addRow(employeeVector);
            }
            jTable2.setModel(employeeModel);
        } catch (Exception e) {
            e.printStackTrace();
            SignIn.logger.severe(e.getMessage());
        }
    }

    // employee registration process
    private void registerEmployee() {
        String fname = jTextField2.getText();
        String lname = jTextField3.getText();
        String fullname = jTextField4.getText();
        String email = jTextField5.getText();
        String mobile = jTextField6.getText();
        String nic = jTextField7.getText();
        String basicSalary = jFormattedTextField1.getText();
        double salary = Double.parseDouble(basicSalary);
        String line1 = jTextField9.getText();
        String line2 = jTextField10.getText();
        String password = String.valueOf(jPasswordField1.getPassword());
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date dateChooser = jDateChooser1.getDate();
        String birthDate = null;
        if (dateChooser != null) {
            birthDate = sdf.format(jDateChooser1.getDate());
        }
        String city = String.valueOf(jComboBox8.getSelectedItem());
//        today
        SimpleDateFormat todayf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String today = todayf.format(new Date());

        String gender = genderMap.get(String.valueOf(jComboBox3.getSelectedItem()));
        String department = departmentMap.get(String.valueOf(jComboBox4.getSelectedItem()));
        String position = positionMap.get(String.valueOf(jComboBox5.getSelectedItem()));

        String postalCode = jTextField11.getText();

        boolean validateMail = validateEmail(email);
        boolean validateMobile = validateMobile(mobile);
        boolean validateNIC = validateNIC(nic);
        boolean validatePostal = validatePostal(postalCode);

        if (fname.isBlank()) {
            Notifications.getInstance().show(Notifications.Type.WARNING, Notifications.Location.TOP_LEFT, 3000l, "Please enter the first name");
        } else if (lname.isBlank()) {
            Notifications.getInstance().show(Notifications.Type.WARNING, Notifications.Location.TOP_LEFT, 3000l, "Please enter the last name");
        } else if (fullname.isBlank()) {
            Notifications.getInstance().show(Notifications.Type.WARNING, Notifications.Location.TOP_LEFT, 3000l, "Please enter the full name");
        } else if (!validateMail) {
            Notifications.getInstance().show(Notifications.Type.WARNING, Notifications.Location.TOP_LEFT, 3000l, "Please enter a valid email address");
        } else if (!validateMobile) {
            Notifications.getInstance().show(Notifications.Type.WARNING, Notifications.Location.TOP_LEFT, 3000l, "Please enter a valid mobile number");
        } else if (!validateNIC) {
            Notifications.getInstance().show(Notifications.Type.WARNING, Notifications.Location.TOP_LEFT, 3000l, "Please enter a valid NIC");
        } else if (birthDate == null) {
            Notifications.getInstance().show(Notifications.Type.WARNING, Notifications.Location.TOP_LEFT, 3000l, "Please choose the date of birth");
        } else if (salary == 0.00) {
            Notifications.getInstance().show(Notifications.Type.WARNING, Notifications.Location.TOP_LEFT, 3000l, "Employee salary must be included.");
        } else if (password.isBlank()) {
            Notifications.getInstance().show(Notifications.Type.WARNING, Notifications.Location.TOP_LEFT, 3000l, "Please enter employee password");
        } else if (line1.isBlank()) {
            Notifications.getInstance().show(Notifications.Type.WARNING, Notifications.Location.TOP_LEFT, 3000l, "Please enter address line 1");
        } else if (line2.isBlank()) {
            Notifications.getInstance().show(Notifications.Type.WARNING, Notifications.Location.TOP_LEFT, 3000l, "Please enter address line 2");
        } else if (!validatePostal) {
            Notifications.getInstance().show(Notifications.Type.WARNING, Notifications.Location.TOP_LEFT, 3000l, "Please enter a valid postal code");

        } else {
            try {
//                check for existing employees with the same details
                ResultSet existCheck = MYsql.execute("SELECT COUNT(id) FROM `employee` WHERE `mobile` = '" + mobile + "' OR `nic` = '" + nic + "' OR `email` = '" + email + "' ");
                if (existCheck.next()) {
                    if (existCheck.getInt("COUNT(id)") > 0) {
                        Notifications.getInstance().show(Notifications.Type.ERROR, Notifications.Location.TOP_LEFT, 3000l, "Employee with the above details already exists.");
                    } else {
                        MYsql.execute("INSERT INTO `employee` (`fname`,`lname`,`fullName`,`email`,`mobile`,`nic`,`birthDay`,`registeredDate`,"
                                + "`employee_status_id`,`department_id`,`Employee_type_id`,`gender_id`,`password`) VALUES "
                                + "('" + fname + "','" + lname + "','" + fullname + "','" + email + "','" + mobile + "','" + nic + "','" + birthDate + "'"
                                + ",'" + today + "','1','" + department + "','" + position + "','" + gender + "','" + password + "') ");

                        ResultSet lastId = MYsql.execute("SELECT LAST_INSERT_ID() AS employee_id");
                        if (lastId.next()) {
                            int employee_id = lastId.getInt("employee_id");
                            try {
                                ResultSet address_rs = MYsql.execute("SELECT * FROM `address` WHERE `employee_id` = '" + employee_id + "' ");
                                if (address_rs.next()) {
//                                    update
                                } else {
//                                    insert
                                    MYsql.execute("INSERT INTO `address` VALUES ('" + employee_id + "','" + postalCode + "',"
                                            + "'" + line1 + "','" + line2 + "','" + cityMap.get(city) + "')");
                                }

                                MYsql.execute("INSERT INTO `salary` VALUES ('" + employee_id + "','" + basicSalary + "') ");

                            } catch (Exception e) {
                            }
                        }
                        clearAll();
                        Notifications.getInstance().show(Notifications.Type.SUCCESS, Notifications.Location.TOP_CENTER, 3000l, "Employee registered Successfully!.");
                    }

                }
            } catch (Exception e) {
                e.printStackTrace();
                SignIn.logger.severe(e.getMessage());
            }
        }
    }

    // employee Update process
    private void updateEmployee() {
        String fname = jTextField2.getText();
        String lname = jTextField3.getText();
        String fullname = jTextField4.getText();
        String email = jTextField5.getText();
        String mobile = jTextField6.getText();
        String nic = jTextField7.getText();
        String basicSalary = jFormattedTextField1.getText();
        double salary = Double.parseDouble(basicSalary);
        String line1 = jTextField9.getText();
        String line2 = jTextField10.getText();
        String password = String.valueOf(jPasswordField1.getPassword());
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date dateChooser = jDateChooser1.getDate();
        String birthDate = null;
        if (dateChooser != null) {
            birthDate = sdf.format(jDateChooser1.getDate());
        }
        String city = String.valueOf(jComboBox8.getSelectedItem());
//        today
        SimpleDateFormat todayf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String today = todayf.format(new Date());

        String gender = genderMap.get(String.valueOf(jComboBox3.getSelectedItem()));
        String department = departmentMap.get(String.valueOf(jComboBox4.getSelectedItem()));
        String position = positionMap.get(String.valueOf(jComboBox5.getSelectedItem()));

        String postalCode = jTextField11.getText();

        boolean validateMail = validateEmail(email);
        boolean validateMobile = validateMobile(mobile);
        boolean validateNIC = validateNIC(nic);
        boolean validatePostal = validatePostal(postalCode);

        if (fname.isBlank()) {
            Notifications.getInstance().show(Notifications.Type.WARNING, Notifications.Location.TOP_LEFT, 3000l, "Please enter the first name");
        } else if (lname.isBlank()) {
            Notifications.getInstance().show(Notifications.Type.WARNING, Notifications.Location.TOP_LEFT, 3000l, "Please enter the last name");
        } else if (fullname.isBlank()) {
            Notifications.getInstance().show(Notifications.Type.WARNING, Notifications.Location.TOP_LEFT, 3000l, "Please enter the full name");
        } else if (!validateMail) {
            Notifications.getInstance().show(Notifications.Type.WARNING, Notifications.Location.TOP_LEFT, 3000l, "Please enter a valid email address");
        } else if (!validateMobile) {
            Notifications.getInstance().show(Notifications.Type.WARNING, Notifications.Location.TOP_LEFT, 3000l, "Please enter a valid mobile number");
        } else if (!validateNIC) {
            Notifications.getInstance().show(Notifications.Type.WARNING, Notifications.Location.TOP_LEFT, 3000l, "Please enter a valid NIC");
        } else if (birthDate == null) {
            Notifications.getInstance().show(Notifications.Type.WARNING, Notifications.Location.TOP_LEFT, 3000l, "Please choose the date of birth");
        } else if (salary == 0.00) {
            Notifications.getInstance().show(Notifications.Type.WARNING, Notifications.Location.TOP_LEFT, 3000l, "Employee salary must be included.");
        } else if (password.isBlank()) {
            Notifications.getInstance().show(Notifications.Type.WARNING, Notifications.Location.TOP_LEFT, 3000l, "Please enter employee password");
        } else if (line1.isBlank()) {
            Notifications.getInstance().show(Notifications.Type.WARNING, Notifications.Location.TOP_LEFT, 3000l, "Please enter address line 1");
        } else if (line2.isBlank()) {
            Notifications.getInstance().show(Notifications.Type.WARNING, Notifications.Location.TOP_LEFT, 3000l, "Please enter address line 2");
        } else if (!validatePostal) {
            Notifications.getInstance().show(Notifications.Type.WARNING, Notifications.Location.TOP_LEFT, 3000l, "Please enter a valid postal code");

        } else {
            try {
//                check for existing employees with the same details
                ResultSet existCheck = MYsql.execute("SELECT *, (SELECT COUNT(id) FROM `employee` WHERE"
                        + " `nic` = '" + nic + "' AND `email` = '" + email + "')  AS `total_count` FROM `employee` INNER"
                        + " JOIN `address` ON `address`.`employee_id` = `employee`.`id` INNER JOIN `salary` ON"
                        + " `salary`.`employee_id` = `employee`.`id` WHERE `nic` = '" + nic + "' AND `email` = '" + email + "' ");

                if (existCheck.next()) {
                    if (existCheck.getInt("total_count") > 0) {

                        if (existCheck.getString("fname").equals(fname)
                                && existCheck.getString("fullName").equals(fullname)
                                && existCheck.getString("lname").equals(lname)
                                && existCheck.getString("mobile").equals(mobile)
                                && existCheck.getString("birthDay").equals(birthDate)
                                && existCheck.getString("department_id").equals(department)
                                && existCheck.getString("Employee_type_id").equals(position)
                                && existCheck.getString("gender_id").equals(gender)
                                && existCheck.getString("password").equals(password)
                                && existCheck.getString("line1").equals(line1)
                                && existCheck.getString("line2").equals(line2)
                                && existCheck.getString("no").equals(postalCode)
                                && existCheck.getString("city_id").equals(cityMap.get(city))
                                && existCheck.getString("basicSalary").equals(basicSalary)) {
                            Notifications.getInstance().show(Notifications.Type.INFO, Notifications.Location.TOP_CENTER, 3000l, "Details have not been changed");
                        } else {
                            String employee_id = existCheck.getString("id");
                            String query = "UPDATE `employee` SET `fname` = '" + fname + "' , `lname` = '" + lname + "', `fullName` = '" + fullname + "',"
                                    + " `mobile` ='" + mobile + "', `birthDay` = '" + birthDate + "',`department_id` = '" + department + "',"
                                    + " `Employee_type_id` = '" + position + "', `gender_id` = '" + gender + "', `password` = '" + password + "' WHERE `id` = '" + employee_id + "'";

                            MYsql.execute(query);

                            ResultSet address_rs = MYsql.execute("SELECT * FROM `address` WHERE `employee_id` = '" + employee_id + "' ");
                            if (address_rs.next()) {
//                                    update
                            } else {
//                                    insert
                                MYsql.execute("UPDATE `address` SET `line1` = '" + line1 + "', `line2` = '" + line2 + "', `no` = '" + postalCode + "', "
                                        + " `city_id` = '" + cityMap.get(city) + "' WHERE `employee_id` = '" + employee_id + "' ");
                            }

                            MYsql.execute("UPDATE `salary` SET  `basicSalary` = '" + basicSalary + "' WHERE `employee_id` = '" + employee_id + "' ");

                            clearAll();
                            Notifications.getInstance().show(Notifications.Type.SUCCESS, Notifications.Location.TOP_LEFT, 3000l, "Employee details updated Successfully!.");
                        }

                    } else {
                        Notifications.getInstance().show(Notifications.Type.ERROR, Notifications.Location.TOP_LEFT, 3000l, "Employee with the above details does not exist.");
                    }

                }
            } catch (Exception e) {
                e.printStackTrace();
                SignIn.logger.severe(e.getMessage());
            }
        }
    }

    private boolean validateEmail(String email) {
        if (email.isBlank()) {
            return false;
        } else if (email.matches(Validation.EMAIL.validation())) {
            return true;
        }
        return false;
    }

    private boolean validateMobile(String mobile) {
        if (mobile.isBlank()) {
            return false;
        } else if (mobile.matches(Validation.MOBILE.validation())) {
            return true;
        }
        return false;
    }

    private boolean validateNIC(String nic) {
        if (nic.isBlank()) {
            return false;
        } else if (nic.matches(Validation.NIC.validation())) {
            return true;
        }
        return false;
    }

    public boolean validatePostal(String postal) {
        if (postal.isBlank()) {
            return false;
        } else if (postal.matches(Validation.POSTAL.validation())) {
            return true;
        }
        return false;
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        jPanel1 = new javax.swing.JPanel();
        jPanel13 = new javax.swing.JPanel();
        jButton5 = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jLabel20 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jLabel25 = new javax.swing.JLabel();
        jLabel26 = new javax.swing.JLabel();
        jLabel27 = new javax.swing.JLabel();
        jLabel28 = new javax.swing.JLabel();
        jLabel29 = new javax.swing.JLabel();
        jLabel30 = new javax.swing.JLabel();
        jLabel31 = new javax.swing.JLabel();
        jLabel32 = new javax.swing.JLabel();
        jLabel33 = new javax.swing.JLabel();
        jLabel34 = new javax.swing.JLabel();
        jLabel35 = new javax.swing.JLabel();
        jTextField2 = new javax.swing.JTextField();
        jTextField3 = new javax.swing.JTextField();
        jTextField4 = new javax.swing.JTextField();
        jTextField5 = new javax.swing.JTextField();
        jTextField6 = new javax.swing.JTextField();
        jTextField7 = new javax.swing.JTextField();
        jDateChooser1 = new com.toedter.calendar.JDateChooser();
        jComboBox3 = new javax.swing.JComboBox<>();
        jComboBox4 = new javax.swing.JComboBox<>();
        jComboBox5 = new javax.swing.JComboBox<>();
        jFormattedTextField1 = new javax.swing.JFormattedTextField();
        jLabel40 = new javax.swing.JLabel();
        jPasswordField1 = new javax.swing.JPasswordField();
        jToggleButton2 = new javax.swing.JToggleButton();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        jLabel37 = new javax.swing.JLabel();
        jLabel38 = new javax.swing.JLabel();
        jLabel39 = new javax.swing.JLabel();
        jTextField9 = new javax.swing.JTextField();
        jTextField10 = new javax.swing.JTextField();
        jComboBox7 = new javax.swing.JComboBox<>();
        jLabel41 = new javax.swing.JLabel();
        jTextField11 = new javax.swing.JTextField();
        jLabel42 = new javax.swing.JLabel();
        jComboBox8 = new javax.swing.JComboBox<>();
        jPanel5 = new javax.swing.JPanel();
        jPanel6 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTable2 = new javax.swing.JTable();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jToggleButton1 = new javax.swing.JToggleButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);

        jPanel13.setBackground(new java.awt.Color(15, 140, 130));

        jButton5.setBackground(new java.awt.Color(15, 140, 130));
        jButton5.setForeground(new java.awt.Color(15, 140, 130));
        jButton5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resource/Vector.png"))); // NOI18N
        jButton5.setBorderPainted(false);
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel13Layout = new javax.swing.GroupLayout(jPanel13);
        jPanel13.setLayout(jPanel13Layout);
        jPanel13Layout.setHorizontalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel13Layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addComponent(jButton5, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel13Layout.setVerticalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel13Layout.createSequentialGroup()
                .addContainerGap(16, Short.MAX_VALUE)
                .addComponent(jButton5, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(15, 15, 15))
        );

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));

        jLabel20.setFont(new java.awt.Font("Poppins", 1, 36)); // NOI18N
        jLabel20.setText("Register New Employee");

        jPanel3.setBackground(new java.awt.Color(252, 252, 252));

        jLabel25.setFont(new java.awt.Font("Poppins", 0, 14)); // NOI18N
        jLabel25.setText("First Name :");

        jLabel26.setFont(new java.awt.Font("Poppins", 0, 14)); // NOI18N
        jLabel26.setText("Last Name :");

        jLabel27.setFont(new java.awt.Font("Poppins", 0, 14)); // NOI18N
        jLabel27.setText("Full Name :");

        jLabel28.setFont(new java.awt.Font("Poppins", 0, 14)); // NOI18N
        jLabel28.setText("Email :");

        jLabel29.setFont(new java.awt.Font("Poppins", 0, 14)); // NOI18N
        jLabel29.setText("Mobile :");

        jLabel30.setFont(new java.awt.Font("Poppins", 0, 14)); // NOI18N
        jLabel30.setText("NIC :");

        jLabel31.setFont(new java.awt.Font("Poppins", 0, 14)); // NOI18N
        jLabel31.setText("Date of Birth :");

        jLabel32.setFont(new java.awt.Font("Poppins", 0, 14)); // NOI18N
        jLabel32.setText("Gender :");

        jLabel33.setFont(new java.awt.Font("Poppins", 0, 14)); // NOI18N
        jLabel33.setText("Department :");

        jLabel34.setFont(new java.awt.Font("Poppins", 0, 14)); // NOI18N
        jLabel34.setText("Position :");

        jLabel35.setFont(new java.awt.Font("Poppins", 0, 14)); // NOI18N
        jLabel35.setText("Basic Salary :");

        jTextField2.setFont(new java.awt.Font("Poppins", 0, 14)); // NOI18N
        jTextField2.setHorizontalAlignment(javax.swing.JTextField.TRAILING);

        jTextField3.setFont(new java.awt.Font("Poppins", 0, 14)); // NOI18N
        jTextField3.setHorizontalAlignment(javax.swing.JTextField.TRAILING);

        jTextField4.setFont(new java.awt.Font("Poppins", 0, 14)); // NOI18N
        jTextField4.setHorizontalAlignment(javax.swing.JTextField.TRAILING);

        jTextField5.setFont(new java.awt.Font("Poppins", 0, 14)); // NOI18N
        jTextField5.setHorizontalAlignment(javax.swing.JTextField.TRAILING);

        jTextField6.setFont(new java.awt.Font("Poppins", 0, 14)); // NOI18N
        jTextField6.setHorizontalAlignment(javax.swing.JTextField.TRAILING);
        jTextField6.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTextField6KeyReleased(evt);
            }
        });

        jTextField7.setFont(new java.awt.Font("Poppins", 0, 14)); // NOI18N
        jTextField7.setHorizontalAlignment(javax.swing.JTextField.TRAILING);
        jTextField7.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTextField7KeyPressed(evt);
            }
        });

        jDateChooser1.setFont(new java.awt.Font("Poppins", 0, 14)); // NOI18N

        jComboBox3.setFont(new java.awt.Font("Poppins", 0, 14)); // NOI18N
        jComboBox3.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jComboBox4.setFont(new java.awt.Font("Poppins", 0, 14)); // NOI18N
        jComboBox4.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jComboBox5.setFont(new java.awt.Font("Poppins", 0, 14)); // NOI18N
        jComboBox5.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jFormattedTextField1.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#0.00"))));
        jFormattedTextField1.setHorizontalAlignment(javax.swing.JTextField.TRAILING);
        jFormattedTextField1.setText("0.00");
        jFormattedTextField1.setFont(new java.awt.Font("Poppins", 0, 14)); // NOI18N

        jLabel40.setFont(new java.awt.Font("Poppins", 0, 14)); // NOI18N
        jLabel40.setText("Password :");

        jPasswordField1.setFont(new java.awt.Font("Poppins", 0, 14)); // NOI18N
        jPasswordField1.setHorizontalAlignment(javax.swing.JTextField.TRAILING);

        jToggleButton2.setFont(new java.awt.Font("Poppins", 0, 14)); // NOI18N
        jToggleButton2.setText("Show Password");
        jToggleButton2.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jToggleButton2ItemStateChanged(evt);
            }
        });
        jToggleButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jToggleButton2ActionPerformed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Poppins", 0, 12)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        jLabel1.setText("Search Here");

        jLabel2.setFont(new java.awt.Font("Poppins", 0, 12)); // NOI18N
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        jLabel2.setText("Search Here");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(41, 41, 41)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel3Layout.createSequentialGroup()
                                .addComponent(jLabel25)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 54, Short.MAX_VALUE)
                                .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, 507, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel3Layout.createSequentialGroup()
                                .addComponent(jLabel26)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, 507, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel3Layout.createSequentialGroup()
                                .addComponent(jLabel27)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jTextField4, javax.swing.GroupLayout.PREFERRED_SIZE, 507, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel3Layout.createSequentialGroup()
                                .addComponent(jLabel28)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jTextField5, javax.swing.GroupLayout.PREFERRED_SIZE, 507, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel3Layout.createSequentialGroup()
                                .addComponent(jLabel29)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jTextField6, javax.swing.GroupLayout.PREFERRED_SIZE, 507, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel30)
                                    .addComponent(jLabel31)
                                    .addComponent(jLabel32)
                                    .addComponent(jLabel33)
                                    .addComponent(jLabel34)
                                    .addComponent(jLabel35)
                                    .addComponent(jLabel40))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jTextField7)
                                    .addComponent(jComboBox3, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jDateChooser1, javax.swing.GroupLayout.DEFAULT_SIZE, 165, Short.MAX_VALUE)
                                    .addComponent(jComboBox4, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jComboBox5, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jFormattedTextField1, javax.swing.GroupLayout.DEFAULT_SIZE, 507, Short.MAX_VALUE)
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                                        .addComponent(jPasswordField1)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(jToggleButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 149, javax.swing.GroupLayout.PREFERRED_SIZE))))))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 279, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 276, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(30, 30, 30))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel25)
                    .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel26)
                    .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel27)
                    .addComponent(jTextField4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel28)
                    .addComponent(jTextField5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel2)
                .addGap(0, 0, 0)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel29)
                    .addComponent(jTextField6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel1)
                .addGap(0, 0, 0)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jTextField7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel30))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jDateChooser1, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel31, javax.swing.GroupLayout.Alignment.TRAILING))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel32)
                    .addComponent(jComboBox3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel33)
                    .addComponent(jComboBox4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel34)
                    .addComponent(jComboBox5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel35)
                    .addComponent(jFormattedTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel40)
                    .addComponent(jPasswordField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jToggleButton2))
                .addGap(40, 40, 40))
        );

        jPanel4.setBackground(new java.awt.Color(252, 252, 252));

        jLabel37.setFont(new java.awt.Font("Poppins", 0, 14)); // NOI18N
        jLabel37.setText("District :");

        jLabel38.setFont(new java.awt.Font("Poppins", 0, 14)); // NOI18N
        jLabel38.setText("Line 1 :");

        jLabel39.setFont(new java.awt.Font("Poppins", 0, 14)); // NOI18N
        jLabel39.setText("Line 2 :");

        jTextField9.setFont(new java.awt.Font("Poppins", 0, 14)); // NOI18N

        jTextField10.setFont(new java.awt.Font("Poppins", 0, 14)); // NOI18N

        jComboBox7.setFont(new java.awt.Font("Poppins", 0, 14)); // NOI18N
        jComboBox7.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        jComboBox7.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jComboBox7ItemStateChanged(evt);
            }
        });

        jLabel41.setFont(new java.awt.Font("Poppins", 0, 14)); // NOI18N
        jLabel41.setText("Postal Code: ");

        jTextField11.setFont(new java.awt.Font("Poppins", 0, 14)); // NOI18N

        jLabel42.setFont(new java.awt.Font("Poppins", 0, 14)); // NOI18N
        jLabel42.setText("City : ");

        jComboBox8.setFont(new java.awt.Font("Poppins", 0, 14)); // NOI18N
        jComboBox8.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        jComboBox8.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jComboBox8ItemStateChanged(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addGap(41, 41, 41)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel38)
                    .addComponent(jLabel39)
                    .addComponent(jLabel37)
                    .addComponent(jLabel41)
                    .addComponent(jLabel42))
                .addGap(21, 21, 21)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jComboBox8, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jTextField11)
                    .addComponent(jComboBox7, javax.swing.GroupLayout.Alignment.LEADING, 0, 407, Short.MAX_VALUE)
                    .addComponent(jTextField9, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jTextField10, javax.swing.GroupLayout.Alignment.LEADING))
                .addGap(35, 35, 35))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel37)
                    .addComponent(jComboBox7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel42)
                    .addComponent(jComboBox8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(4, 4, 4)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel38)
                    .addComponent(jTextField9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel39)
                    .addComponent(jTextField10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel41)
                    .addComponent(jTextField11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(7, Short.MAX_VALUE))
        );

        jPanel5.setBackground(new java.awt.Color(252, 252, 252));

        jPanel6.setBackground(new java.awt.Color(255, 255, 255));

        jTable2.setFont(new java.awt.Font("Poppins", 0, 14)); // NOI18N
        jTable2.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Employee ID", "Employee Name", "Department", "Designation"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTable2.getTableHeader().setReorderingAllowed(false);
        jTable2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable2MouseClicked(evt);
            }
        });
        jTable2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTable2KeyPressed(evt);
            }
        });
        jScrollPane3.setViewportView(jTable2);

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 522, Short.MAX_VALUE)
                .addGap(18, 18, 18))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                .addGap(21, 21, 21))
        );

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(18, 18, 18))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(18, 18, 18))
        );

        jButton1.setFont(new java.awt.Font("Poppins SemiBold", 0, 14)); // NOI18N
        jButton1.setText("Clear All");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setBackground(new java.awt.Color(21, 160, 214));
        jButton2.setFont(new java.awt.Font("Poppins SemiBold", 0, 14)); // NOI18N
        jButton2.setForeground(new java.awt.Color(255, 255, 255));
        jButton2.setText("Update Employee");
        jButton2.setEnabled(false);
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton3.setBackground(new java.awt.Color(15, 140, 130));
        jButton3.setFont(new java.awt.Font("Poppins SemiBold", 0, 14)); // NOI18N
        jButton3.setForeground(new java.awt.Color(255, 255, 255));
        jButton3.setText("Register Employee");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jToggleButton1.setFont(new java.awt.Font("Poppins SemiBold", 0, 14)); // NOI18N
        jToggleButton1.setForeground(new java.awt.Color(15, 140, 130));
        jToggleButton1.setText("Search Disabled");
        jToggleButton1.setToolTipText("Enable this to search employees on the table");
        jToggleButton1.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jToggleButton1ItemStateChanged(evt);
            }
        });
        jToggleButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jToggleButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(46, 46, 46)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 177, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 177, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 177, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jLabel20)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jToggleButton1))
                            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(38, 38, 38)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanel4, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jPanel5, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addGap(46, 46, 46))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel20)
                    .addComponent(jToggleButton1))
                .addGap(27, 27, 27)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jButton2, javax.swing.GroupLayout.DEFAULT_SIZE, 42, Short.MAX_VALUE)
                    .addComponent(jButton3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(30, 30, 30))
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel13, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jPanel13, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jScrollPane1.setViewportView(jPanel1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jScrollPane1)
                .addGap(0, 0, 0))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 778, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        this.dispose();
        ComponentStorage.registerNewEmployee = null;
    }//GEN-LAST:event_jButton5ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        registerEmployee();
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jToggleButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jToggleButton1ActionPerformed
        if (jToggleButton1.isSelected()) {
            jToggleButton1.setText("Search Enabled");
        } else {
            jToggleButton1.setText("Search Disabled");
        }
    }//GEN-LAST:event_jToggleButton1ActionPerformed

    private void jToggleButton2ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jToggleButton2ItemStateChanged
        if (jToggleButton2.isSelected()) {
            jPasswordField1.setEchoChar('\0');
        } else {
            jPasswordField1.setEchoChar('\u2022');
        }
        jPasswordField1.grabFocus();
    }//GEN-LAST:event_jToggleButton2ItemStateChanged

    private void jToggleButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jToggleButton2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jToggleButton2ActionPerformed

    private void jComboBox8ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jComboBox8ItemStateChanged
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBox8ItemStateChanged

    private void jComboBox7ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jComboBox7ItemStateChanged
        loadCity();
    }//GEN-LAST:event_jComboBox7ItemStateChanged

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        updateEmployee();
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jTable2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable2MouseClicked
        if (evt.getButton() == MouseEvent.BUTTON1 && evt.getClickCount() == 2) {
            clickTableRow();
        }
    }//GEN-LAST:event_jTable2MouseClicked

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        clearAll();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jTable2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTable2KeyPressed
        if (evt.getKeyChar() == KeyEvent.VK_ENTER) {
            clickTableRow();
            evt.consume();
        }
    }//GEN-LAST:event_jTable2KeyPressed

    private void jTextField6KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField6KeyReleased
        loadEmployeeTable();
    }//GEN-LAST:event_jTextField6KeyReleased

    private void jTextField7KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField7KeyPressed
        loadEmployeeTable();
    }//GEN-LAST:event_jTextField7KeyPressed

    private void jToggleButton1ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jToggleButton1ItemStateChanged
        if (jToggleButton1.isSelected()) {
            jLabel2.setVisible(true);
            jLabel1.setVisible(true);
        } else {
            jLabel2.setVisible(false);
            jLabel1.setVisible(false);
        }
    }//GEN-LAST:event_jToggleButton1ItemStateChanged

    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        FlatMacLightLaf.setup();

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new RegisterNewEmployee().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton5;
    private javax.swing.JComboBox<String> jComboBox3;
    private javax.swing.JComboBox<String> jComboBox4;
    private javax.swing.JComboBox<String> jComboBox5;
    private javax.swing.JComboBox<String> jComboBox7;
    private javax.swing.JComboBox<String> jComboBox8;
    private com.toedter.calendar.JDateChooser jDateChooser1;
    private javax.swing.JFormattedTextField jFormattedTextField1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel33;
    private javax.swing.JLabel jLabel34;
    private javax.swing.JLabel jLabel35;
    private javax.swing.JLabel jLabel37;
    private javax.swing.JLabel jLabel38;
    private javax.swing.JLabel jLabel39;
    private javax.swing.JLabel jLabel40;
    private javax.swing.JLabel jLabel41;
    private javax.swing.JLabel jLabel42;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPasswordField jPasswordField1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTable jTable2;
    private javax.swing.JTextField jTextField10;
    private javax.swing.JTextField jTextField11;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JTextField jTextField3;
    private javax.swing.JTextField jTextField4;
    private javax.swing.JTextField jTextField5;
    private javax.swing.JTextField jTextField6;
    private javax.swing.JTextField jTextField7;
    private javax.swing.JTextField jTextField9;
    private javax.swing.JToggleButton jToggleButton1;
    private javax.swing.JToggleButton jToggleButton2;
    // End of variables declaration//GEN-END:variables

    private void clearAll() {
        jTextField2.setText("");
        jTextField3.setText("");
        jTextField4.setText("");
        jTextField5.setText("");
        jTextField6.setText("");
        jTextField7.setText("");
        jFormattedTextField1.setText("0.00");
        jTextField9.setText("");
        jTextField10.setText("");
        jDateChooser1.setDate(null);
        jComboBox8.setSelectedIndex(0);
        jComboBox3.setSelectedIndex(0);
        jComboBox4.setSelectedIndex(0);
        jComboBox5.setSelectedIndex(0);
        jTextField11.setText("");

        loadEmployeeTable();

        jButton3.setEnabled(true);
        jButton2.setEnabled(false);
        jTextField5.setEditable(true);
        jTextField7.setEditable(true);
        jPasswordField1.setText("");
    }

    private void clickTableRow() {
        if (jTable2.getSelectedRowCount() == 1) {

            jButton3.setEnabled(false);
            jButton2.setEnabled(true);
            jTextField5.setEditable(false);
            jTextField7.setEditable(false);

            int row = jTable2.getSelectedRow();
            String employeeId = String.valueOf(jTable2.getValueAt(row, 0));
            try {
                ResultSet employeeRs = MYsql.execute("SELECT * FROM `employee` INNER JOIN `address` ON"
                        + " `address`.`employee_id` = `employee`.`id` INNER JOIN `salary` ON"
                        + " `salary`.`employee_id` = `employee`.`id` INNER JOIN city ON city.id = address.city_id "
                        + "INNER JOIN gender ON gender.id = employee.gender_id INNER JOIN employee_type "
                        + "ON employee_type.id = employee.Employee_type_id INNER JOIN department ON"
                        + " department.id = employee.department_id "
                        + "WHERE `employee`.`id` =  '" + employeeId + "' ");
                if (employeeRs.next()) {
                    jTextField2.setText(employeeRs.getString("fname"));
                    jTextField3.setText(employeeRs.getString("lname"));
                    jTextField4.setText(employeeRs.getString("fullName"));
                    jTextField5.setText(employeeRs.getString("email"));
                    jTextField6.setText(employeeRs.getString("mobile"));
                    jTextField7.setText(employeeRs.getString("nic"));
                    jFormattedTextField1.setText(employeeRs.getString("basicSalary"));
                    jTextField9.setText(employeeRs.getString("line1"));
                    jTextField10.setText(employeeRs.getString("line2"));
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                    Date birthDate = sdf.parse(employeeRs.getString("birthDay"));
                    jDateChooser1.setDate(birthDate);
                    jComboBox8.setSelectedItem(employeeRs.getString("city.name"));
                    jComboBox3.setSelectedItem(employeeRs.getString("gender.gender"));
                    jComboBox4.setSelectedItem(employeeRs.getString("department.name"));
                    jComboBox5.setSelectedItem(employeeRs.getString("employee_type.type"));
                    jTextField11.setText(employeeRs.getString("no"));
                    jPasswordField1.setText(employeeRs.getString("password"));
                }

            } catch (Exception e) {
                SignIn.logger.severe(e.getMessage());
            }
        }
    }
}
