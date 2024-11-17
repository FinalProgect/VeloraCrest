/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import com.formdev.flatlaf.FlatClientProperties;
import javax.swing.JPanel;

/**
 *
 * @author KASUNEE
 */
public class RoundPanel extends JPanel{
    public RoundPanel(){
        init();
    }
    private void init(){
    this.putClientProperty(FlatClientProperties.STYLE, "arc:50");
    }
}
