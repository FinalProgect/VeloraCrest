package gui;

import com.formdev.flatlaf.extras.FlatSVGIcon;
import com.formdev.flatlaf.themes.FlatMacLightLaf;
import java.awt.Color;
import java.awt.SplashScreen;

public class SplshWindow extends javax.swing.JFrame {

    public SplshWindow() {
        initComponents();
        setBackground(new Color(0, 0, 0, 0));
        lodingProgressAnimation();
    }

    private void lodingProgressAnimation() {
        Thread lodingThread = new Thread(new Runnable() {
            public void run() {
                for (int x = 0; x <= 100; x++) {

                    jProgressBar1.setValue(x);

                    switch (x) {

                        case 10:
                            lodingText.setText("Connecting to Inernet . . .");

                            FlatSVGIcon star = new FlatSVGIcon("resource//star.svg", star1.getWidth(), star1.getHeight());
                            star1.setIcon(star);

                            break;

                        case 15:
                            lodingText.setText("Connected to the Inernet");
                            break;

                        case 18:
                            lodingText.setText("Loding Files . . .");
                            break;

                        case 23:
                            lodingText.setText("File Loaded");
                            break;

                        case 25:
                            lodingText.setText("Finding Libraries . . . .");

                            FlatSVGIcon stara = new FlatSVGIcon("resource//star.svg", star2.getWidth(), star2.getHeight());
                            star2.setIcon(stara);

                            break;

                        case 30:
                            lodingText.setText("Loading Libraries . . . .");
                            break;

                        case 40:
                            lodingText.setText("Libraries Completely Loaded.");
                            break;

                        case 45:
                            lodingText.setText("Loding Resourses . . .");
                            break;

                        case 55:
                            lodingText.setText("Resorses Loaded");

                            FlatSVGIcon starb = new FlatSVGIcon("resource//star.svg", star3.getWidth(), star3.getHeight());
                            star3.setIcon(starb);

                            break;

                        case 60:
                            lodingText.setText("Connectiong to Database . ");
                            break;

                        case 61:
                            lodingText.setText("Connectiong to Database . . ");
                            break;

                        case 62:
                            lodingText.setText("Connectiong to Database . . . ");
                            break;

                        case 63:
                            lodingText.setText("Connectiong to Database . . . . ");
                            break;

                        case 64:
                            lodingText.setText("Connectiong to Database . ");
                            break;

                        case 65:
                            lodingText.setText("Connectiong to Database . . ");
                            break;

                        case 66:
                            lodingText.setText("Connectiong to Database . . . ");
                            break;

                        case 67:
                            lodingText.setText("Connectiong to Database . . . . ");
                            break;

                        case 68:
                            lodingText.setText("Connectiong to Database . ");
                            break;

                        case 69:
                            lodingText.setText("Connectiong to Database . . ");
                            break;

                        case 70:
                            lodingText.setText("Connectiong to Database . . . ");

                            FlatSVGIcon starc = new FlatSVGIcon("resource//star.svg", star4.getWidth(), star4.getHeight());
                            star4.setIcon(starc);

                            break;

                        case 71:
                            lodingText.setText("Connectiong to Database . . . . ");
                            break;

                        case 72:
                            lodingText.setText("Connectiong to Database . ");
                            break;

                        case 73:
                            lodingText.setText("Connectiong to Database . . ");
                            break;

                        case 74:
                            lodingText.setText("Connectiong to Database . . . ");
                            break;

                        case 75:
                            lodingText.setText("Connectiong to Database . . . . ");
                            break;

                        case 76:
                            lodingText.setText("Connectiong to Database . ");
                            break;

                        case 77:
                            lodingText.setText("Connectiong to Database . . ");
                            break;

                        case 78:
                            lodingText.setText("Connectiong to Database . . . ");
                            break;

                        case 79:
                            lodingText.setText("Connectiong to Database . . . . ");
                            break;

                        case 80:
                            lodingText.setText("Connectiong to Database . ");
                            break;

                        case 81:
                            lodingText.setText("Connectiong to Database . . ");
                            break;

                        case 82:
                            lodingText.setText("Connectiong to Database . . . ");
                            break;

                        case 83:
                            lodingText.setText("Connectiong to Database . . . . ");
                            break;

                        case 84:
                            lodingText.setText("Connectiong to Database . ");
                            break;

                        case 85:
                            lodingText.setText("Connectiong to Database . . ");
                            break;

                        case 86:
                            lodingText.setText("Connectiong to Database . . . ");
                            break;

                        case 87:
                            lodingText.setText("Connectiong to Database . . . . ");
                            break;

                        case 88:
                            lodingText.setText("Connectiong to Database . ");
                            break;

                        case 89:
                            lodingText.setText("Connectiong to Database . . ");
                            break;

                        case 90:
                            lodingText.setText("Connectiong to Database . . . ");
                            break;

                        case 91:
                            lodingText.setText("Connectiong to Database . . . . ");
                            break;
                        case 92:
                            lodingText.setText("Connectiong to Database . ");
                            break;

                        case 93:
                            lodingText.setText("Connectiong to Database . . ");

                            FlatSVGIcon stard = new FlatSVGIcon("resource//star.svg", star5.getWidth(), star5.getHeight());
                            star5.setIcon(stard);

                            break;

                        case 94:
                            lodingText.setText("Connectiong to Database . . . ");
                            break;

                        case 95:
                            lodingText.setText("Connectiong to Database . . . . ");
                            break;
                        case 96:
                            lodingText.setText("Connectiong to Database . ");
                            break;

                        case 97:
                            lodingText.setText("Connectiong to Database . . ");
                            break;

                        case 98:
                            lodingText.setText("Successfully Connected to Database.");
                            break;

                        case 99:
                            lodingText.setText("Done  ");
                            break;

                    }

                    try {
                        Thread.sleep(50);  ///// loading time
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                dispose();
               signInWindow();
            }
        });
        lodingThread.start();

    }

    private void signInWindow() {

        SignIn signIn = new SignIn(this, true);
        signIn.setVisible(true);

    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jProgressBar1 = new javax.swing.JProgressBar();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        lodingText = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        star3 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        star1 = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        star2 = new javax.swing.JLabel();
        jPanel6 = new javax.swing.JPanel();
        star4 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        star5 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);

        jProgressBar1.setStringPainted(true);

        jPanel1.setBackground(new java.awt.Color(255, 0, 51));
        jPanel1.setOpaque(false);

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resource/hotel (1).png"))); // NOI18N

        lodingText.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(0, 27, Short.MAX_VALUE)
                .addComponent(jLabel1))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(78, 78, 78)
                .addComponent(lodingText, javax.swing.GroupLayout.PREFERRED_SIZE, 270, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(5, 5, 5)
                .addComponent(lodingText, javax.swing.GroupLayout.PREFERRED_SIZE, 15, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jPanel2.setBackground(new java.awt.Color(204, 204, 255));
        jPanel2.setOpaque(false);
        jPanel2.setPreferredSize(new java.awt.Dimension(140, 140));

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(star3, javax.swing.GroupLayout.DEFAULT_SIZE, 140, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(star3, javax.swing.GroupLayout.DEFAULT_SIZE, 140, Short.MAX_VALUE)
        );

        jPanel4.setBackground(new java.awt.Color(204, 204, 255));
        jPanel4.setOpaque(false);
        jPanel4.setPreferredSize(new java.awt.Dimension(140, 140));

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(star1, javax.swing.GroupLayout.DEFAULT_SIZE, 140, Short.MAX_VALUE)
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(star1, javax.swing.GroupLayout.DEFAULT_SIZE, 140, Short.MAX_VALUE)
        );

        jPanel5.setBackground(new java.awt.Color(255, 204, 255));
        jPanel5.setOpaque(false);
        jPanel5.setPreferredSize(new java.awt.Dimension(140, 140));

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(star2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(star2, javax.swing.GroupLayout.DEFAULT_SIZE, 140, Short.MAX_VALUE)
        );

        jPanel6.setBackground(new java.awt.Color(255, 204, 204));
        jPanel6.setOpaque(false);
        jPanel6.setPreferredSize(new java.awt.Dimension(140, 140));

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(star4, javax.swing.GroupLayout.DEFAULT_SIZE, 150, Short.MAX_VALUE)
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(star4, javax.swing.GroupLayout.DEFAULT_SIZE, 140, Short.MAX_VALUE)
        );

        jPanel3.setBackground(new java.awt.Color(204, 255, 51));
        jPanel3.setOpaque(false);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(star5, javax.swing.GroupLayout.DEFAULT_SIZE, 136, Short.MAX_VALUE)
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(star5, javax.swing.GroupLayout.DEFAULT_SIZE, 128, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jProgressBar1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, 150, Short.MAX_VALUE)
                            .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, 150, Short.MAX_VALUE))
                        .addContainerGap())
                    .addGroup(layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(301, 301, 301))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(29, 29, 29))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(34, 34, 34)))))
                .addComponent(jProgressBar1, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        FlatMacLightLaf.setup();

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {

                SplshWindow splashScreen = new SplshWindow();
                splashScreen.setVisible(true);

            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JProgressBar jProgressBar1;
    private javax.swing.JLabel lodingText;
    private javax.swing.JLabel star1;
    private javax.swing.JLabel star2;
    private javax.swing.JLabel star3;
    private javax.swing.JLabel star4;
    private javax.swing.JLabel star5;
    // End of variables declaration//GEN-END:variables
}
