package gui.UnitConverter;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JTextField;

//ActionListener for the reset button
public class ResetListener implements ActionListener
{
  JLabel toAmount;
  JComboBox<String> from;
  JComboBox<String> to;
  JComboBox<String> ingredient;
  JTextField amount;
 
  public ResetListener(JLabel toAmount, JComboBox<String> from, JComboBox<String> to, JComboBox<String> ingredient, JTextField amount) 
  {
    this.toAmount = toAmount;
    this.from = from;
    this.to = to;
    this.ingredient = ingredient;
    this.amount = amount;
  }
  
  //Resets the window
  public void actionPerformed(ActionEvent e)
  {
    toAmount.setText("To Amount: ______________");
    from.setSelectedItem("");
    to.setSelectedItem("");
    ingredient.setSelectedItem("");
    ingredient.setEnabled(false);
    amount.setText("");
  }
  
}
