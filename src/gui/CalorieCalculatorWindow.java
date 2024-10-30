package gui;

import javax.swing.*;
import java.awt.*;

/**
 * Creates the Calorie Calculator Window for KILowBites
 * 
 * @author Jayden S
 * TODO needs functionality
 */
public class CalorieCalculatorWindow extends JFrame {
    private static final long serialVersionUID = 1293847255;

    private static final String ICON1_PATH = "/img/calculate.png";
    private static final String ICON2_PATH = "/img/reset.png";

    private JPanel mainPanel;
    private JPanel iconPanel;
    private JPanel inputPanel;
    private JPanel caloriesPanel;

    public CalorieCalculatorWindow() {
        initializeWindow();
        createPanels();
        addComponentsToPanels();
        assembleMainPanel();
    }

    private void initializeWindow() {
        setTitle("KiLowBites Calorie Calculator");
        setSize(500, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
    }

    private void createPanels() {
        mainPanel = new JPanel(new BorderLayout());
        iconPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        inputPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        caloriesPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
    }

    private void addComponentsToPanels() {
        addIconsToPanel();
        addInputComponents();
        addCaloriesComponents();
    }

    private void addIconsToPanel() {
        addIconToPanel(iconPanel, ICON1_PATH, 30, 30);
        addIconToPanel(iconPanel, ICON2_PATH, 30, 30);
    }

    private void addInputComponents() {
        inputPanel.add(new JLabel("Ingredient:"));
        inputPanel.add(new JComboBox<>(new String[]{"Ingredient 1", "Ingredient 2", "Ingredient 3"}));
        inputPanel.add(new JLabel("Amount:"));
        inputPanel.add(new JTextField(5));
        inputPanel.add(new JLabel("Units:"));
        inputPanel.add(new JComboBox<>(new String[]{"g", "ml", "oz"}));
    }

    private void addCaloriesComponents() {
        caloriesPanel.add(new JLabel("Calories:"));
        JTextField caloriesField = new JTextField(10);
        caloriesField.setEditable(false);
        caloriesPanel.add(caloriesField);
    }

    private void assembleMainPanel() {
        JPanel contentPanel = new JPanel(new BorderLayout());
        contentPanel.add(iconPanel, BorderLayout.NORTH);
        contentPanel.add(inputPanel, BorderLayout.CENTER);
        contentPanel.add(caloriesPanel, BorderLayout.SOUTH);

        mainPanel.add(contentPanel, BorderLayout.NORTH);
        add(mainPanel);
    }

    private void addIconToPanel(JPanel panel, String imagePath, int width, int height) {
        ImageIcon icon = createImageIcon(imagePath);
        if (icon != null) {
            Image img = icon.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
            JLabel label = new JLabel(new ImageIcon(img));
            panel.add(label);
        } else {
            System.err.println("Couldn't find file: " + imagePath);
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
            CalorieCalculatorWindow window = new CalorieCalculatorWindow();
            window.setVisible(true);
        });
    }
}