package gui.EditorListeners;

import java.util.List;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

/**
 * ChangeTracker class used to check if the user has made a change.
 * 
 * @author David Friend
 */
public class ChangeTracker
{
  private List<JComponent> components;
  private JButton[] saveButtons;
  private JButton closeButton;
  private JButton newButton;
  
  /**
   * Default constructor.
   * 
   * @param closeButton
   * @param newButton
   * @param components
   * @param saveButtons
   */
  public ChangeTracker(final JButton closeButton, final JButton newButton, 
      final List<JComponent> components, final JButton... saveButtons) 
  {
    this.components = components;
    this.saveButtons = saveButtons;
    this.closeButton = closeButton;
    this.newButton = newButton;
    checkChanged();
  }

  /**
   * Checks if any component has changed so the buttons state could change.
   */
  public void checkChanged() 
  {
    for (JComponent component : components) 
    {
      if (component instanceof JTextField) 
      {
        ((JTextField) component).getDocument().addDocumentListener(new TextFieldChanger());
      }
      if (component instanceof JComboBox) 
      {
        if (saveButtons.length == 1) 
        {
          ((JComboBox) component).addActionListener(e -> saveButtons[0].setEnabled(true));
          ((JComboBox) component).addActionListener(e -> closeButton.setEnabled(false));
          ((JComboBox) component).addActionListener(e -> newButton.setEnabled(false));
        }
        else 
        {
          ((JComboBox) component).addActionListener(e -> saveButtons[0].setEnabled(true));
          ((JComboBox) component).addActionListener(e -> saveButtons[1].setEnabled(true));
          ((JComboBox) component).addActionListener(e -> closeButton.setEnabled(false));
          ((JComboBox) component).addActionListener(e -> newButton.setEnabled(false));
        }
      }
    }
  }
  private class TextFieldChanger implements DocumentListener
  {

    @Override
    public void insertUpdate(final DocumentEvent e)
    {
      if (saveButtons.length == 1) 
      {
        saveButtons[0].setEnabled(true);
        closeButton.setEnabled(false);
        newButton.setEnabled(false);
      }
      else 
      {
        saveButtons[0].setEnabled(true);
        saveButtons[1].setEnabled(true);
        closeButton.setEnabled(false);
        newButton.setEnabled(false);
      }
    }

    @Override
    public void removeUpdate(final DocumentEvent e)
    {
      if (saveButtons.length == 1) 
      {
        saveButtons[0].setEnabled(true);
        closeButton.setEnabled(false);
        newButton.setEnabled(false);
      }
      else 
      {
        saveButtons[0].setEnabled(true);
        saveButtons[1].setEnabled(true);
        closeButton.setEnabled(false);
        newButton.setEnabled(false);
      }
    }

    @Override
    public void changedUpdate(final DocumentEvent e)
    {
      if (saveButtons.length == 1) 
      {
        saveButtons[0].setEnabled(true);
        closeButton.setEnabled(false);
        newButton.setEnabled(false);
      }
      else 
      {
        saveButtons[0].setEnabled(true);
        saveButtons[1].setEnabled(true);
        closeButton.setEnabled(false);
        newButton.setEnabled(false);
      }
    }
    
  }
}

