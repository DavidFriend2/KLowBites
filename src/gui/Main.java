package gui;

import javax.swing.*;
import java.awt.*;

/**
 * Creates the Basic Main Window for KILowBites
 * 
 * TODO Add KIlowBites png to the main screen
 * TODO Setup shortcuts
 * TODO Add functionality to each menu header
 * TODO DO NOT ADD THINGS THAT ARE NOT GOING TO BE
 * DONE IN THIS SPRINT, READ DOC FOR MORE DETAILS
 */
public class Main extends JFrame
{

  public Main()
  {
    // Set up the frame
    setTitle("KiLowBites Main Window");
    setSize(600, 400);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setLocationRelativeTo(null);

    // Create a menu bar
    JMenuBar menuBar = new JMenuBar();
    setJMenuBar(menuBar);

    // Create File menu
    JMenu fileMenu = new JMenu("File");
    menuBar.add(fileMenu);

    // Create Edit menu
    JMenu editMenu = new JMenu("Edit");
    menuBar.add(editMenu);

    // Create Search menu
    JMenu searchMenu = new JMenu("Search");
    menuBar.add(searchMenu);

    // Create View menu
    JMenu viewMenu = new JMenu("View");
    menuBar.add(viewMenu);

    // Create Tools menu
    JMenu toolsMenu = new JMenu("Tools");
    menuBar.add(toolsMenu);

    // Create Configure menu
    JMenu configureMenu = new JMenu("Configure");
    menuBar.add(configureMenu);

    // Create Help menu
    JMenu helpMenu = new JMenu("Help");
    menuBar.add(helpMenu);

    // Add menu items to File menu
    JMenuItem exitItem = new JMenuItem("Exit");
    exitItem.addActionListener(e -> System.exit(0));
    fileMenu.add(exitItem);

    // Create main content panel
    JPanel mainPanel = new JPanel();
    mainPanel.setLayout(new BorderLayout());
    add(mainPanel);
  }

  public static void main(String[] args)
  {
    SwingUtilities.invokeLater(() -> {
      Main window = new Main();
      window.setVisible(true);
    });
  }
}
