package gui.EditorListeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

public class ChangeTracker
{
  private List<JComponent> components;
  private JButton[] saveButtons;
  private JButton closeButton;
  
  public ChangeTracker(JButton closeButton, List<JComponent> components, JButton... saveButtons) {
    this.components = components;
    this.saveButtons = saveButtons;
    this.closeButton = closeButton;
    checkChanged();
  }

  //Checks if any component has changed so the save button will enable
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
        if (saveButtons.length == 1) {
          ((JComboBox) component).addActionListener(e -> saveButtons[0].setEnabled(true));
        }
        else 
        {
          ((JComboBox) component).addActionListener(e -> saveButtons[0].setEnabled(true));
          ((JComboBox) component).addActionListener(e -> saveButtons[1].setEnabled(true));
        }
      }
    }
  }
  private class TextFieldChanger implements DocumentListener
  {

    @Override
    public void insertUpdate(DocumentEvent e)
    {
      if (saveButtons.length == 1) {
        saveButtons[0].setEnabled(true);
        closeButton.setEnabled(false);
      }
      else 
      {
        saveButtons[0].setEnabled(true);
        saveButtons[1].setEnabled(true);
        closeButton.setEnabled(false);
      }
    }

    @Override
    public void removeUpdate(DocumentEvent e)
    {
      if (saveButtons.length == 1) {
        saveButtons[0].setEnabled(true);
        closeButton.setEnabled(false);
      }
      else 
      {
        saveButtons[0].setEnabled(true);
        saveButtons[1].setEnabled(true);
        closeButton.setEnabled(false);
      }
    }

    @Override
    public void changedUpdate(DocumentEvent e)
    {
      if (saveButtons.length == 1) {
        saveButtons[0].setEnabled(true);
        closeButton.setEnabled(false);
      }
      else 
      {
        saveButtons[0].setEnabled(true);
        saveButtons[1].setEnabled(true);
        closeButton.setEnabled(false);
      }
    }
    
  }
}

