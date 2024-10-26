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
        JPanel mainPanel = new JPanel(new BorderLayout());
        
        // Create image panel with FlowLayout aligned to the left
        JPanel imagePanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        
        // Add image buttons to the image panel
        addImageButton(imagePanel, "/img/calculate.png", "Calculate");
        addImageButton(imagePanel, "/img/reset.png", "Rest");

        // Add image panel to the top (NORTH) of the main panel
        mainPanel.add(imagePanel, BorderLayout.NORTH);

        // Add a label to the center of the panel
        JLabel label = new JLabel("TODO", SwingConstants.CENTER);
        mainPanel.add(label, BorderLayout.CENTER);

        add(mainPanel);
    }

    private void addImageButton(JPanel panel, String imagePath, String buttonName) {
        ImageIcon icon = createImageIcon(imagePath);
        if (icon != null) {
            Image img = icon.getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH);
            JButton button = new JButton(new ImageIcon(img));
            button.setPreferredSize(new Dimension(50, 50));
            button.setToolTipText(buttonName);
            panel.add(button);
        }
    }

    private ImageIcon createImageIcon(String path) {
        java.net.URL imgURL = getClass().getResource(path);
        if (imgURL != null) {
            return new ImageIcon(imgURL);
        } else {
            System.err.println("Couldn't find file: " + path);
            return null;
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new UnitConverterWindow().setVisible(true);
        });
    }
}