package gui;

import javax.swing.*;
import java.awt.*;

/**
 * @author Jayden S
 * Creates the UnitConverterWindow
 * TODO implement the nec. data
 */
public class UnitConverterWindow extends JFrame {
    
    public UnitConverterWindow() {
        setTitle("KiLowBites Unit Converter");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        // Create a menu bar
        JMenuBar menuBar = new JMenuBar();
        setJMenuBar(menuBar);


        // Create main content panel
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        
        // Add a label to the center of the panel
        JLabel label = new JLabel("TODO", SwingConstants.CENTER);
        mainPanel.add(label, BorderLayout.CENTER);

        add(mainPanel);
    }
}