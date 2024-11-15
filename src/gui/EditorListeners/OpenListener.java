package gui.EditorListeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JList;
import javax.swing.JTextField;
import javax.swing.filechooser.FileNameExtensionFilter;

import Information.Recipe;
import Information.RecipeIngredient;
import Information.Step;
import Information.Utensil;

public class OpenListener implements ActionListener 
{

  JTextField name;
  JTextField serves;
  JTextField ingNameInput;
  JTextField ingDetailsInput;
  JTextField ingAmountInput;
  JComboBox ingUnitCombo;
  JList ingList;
  JList stepList;
  JList utensilList;
  JComboBox stepUtensilCombo;
  JComboBox stepOnCombo;
  String currentFileName;
  List<RecipeIngredient> fullIngredientList;
  List<Step> fullStepList;
  List<Utensil> fullUtensilList;
  DefaultListModel<String> dlm;
  DefaultListModel<String> dlm2;
  DefaultListModel<String> dlm3;
  
  public OpenListener(final JTextField name, final JTextField serves, 
      final JTextField ingNameInput, final JTextField ingDetailsInput, 
      final JTextField ingAmountInput, final JComboBox ingUnitCombo, final JList ingList,
      final JList stepList, final JList utensilList, final JComboBox stepOnCombo, 
      final JComboBox stepUtensilCombo, final List<RecipeIngredient> fullIngredientList, 
      final List<Step> fullStepList, final List<Utensil> fullUtensilList, 
      final DefaultListModel<String> dlm, final DefaultListModel<String> dlm2, 
      final DefaultListModel<String> dlm3) 
  {
    this.name = name;
    this.serves = serves;
    this.ingAmountInput = ingAmountInput;
    this.ingDetailsInput = ingDetailsInput;
    this.ingUnitCombo = ingUnitCombo;
    this.ingNameInput = ingNameInput;
    this.ingList = ingList;
    this.stepList = stepList;
    this.utensilList = utensilList;
    this.stepOnCombo = stepOnCombo;
    this.stepUtensilCombo = stepUtensilCombo;
    this.fullIngredientList = fullIngredientList;
    this.fullStepList = fullStepList;
    this.fullUtensilList = fullUtensilList;
    this.dlm = dlm;
    this.dlm2 = dlm2;
    this.dlm3 = dlm3;
  }
  
  @SuppressWarnings("unchecked")
  @Override
  public void actionPerformed(final ActionEvent e) 
  {
    JFileChooser chooser = new JFileChooser();
    FileNameExtensionFilter filter = new FileNameExtensionFilter(
                "RCP files", "rcp");
    chooser.setFileFilter(filter);
    int returnVal = chooser.showOpenDialog(null);
    if(returnVal == JFileChooser.APPROVE_OPTION) 
    {
      System.out.println("You chose to open this file: " 
          + chooser.getSelectedFile().getName());
//            File file = new File(chooser.getSelectedFile(), chooser.getSelectedFile().getName());
      currentFileName = chooser.getSelectedFile().getAbsolutePath();
      try 
      {
        Recipe loaded = Recipe.loadRecipeFromFile(chooser.getSelectedFile().getAbsolutePath());
        name.setText(loaded.getName());
        serves.setText(String.valueOf(loaded.getServes()));
        for (RecipeIngredient ri : loaded.getIngredients()) 
        {
          fullIngredientList.add(ri);
        }
        //sort fullingredientslist somehow
        for (RecipeIngredient ri : fullIngredientList) 
        {
          if (ri.getDetails() != null && !ri.getDetails().equals("")) 
          {
            dlm.addElement(ri.getAmount() + " " + ri.getUnit().
                toLowerCase() + " (" + ri.getDetails() + ") " + ri.getName());
          } 
          else 
          {
            dlm.addElement(ri.getAmount() + " " + ri.getUnit().toLowerCase() + " " + ri.getName());
          }
          stepOnCombo.addItem(ri.getName());
        }
        
        for (Utensil ut : loaded.getUtenils()) 
        {
          if (ut.getDetails()!= null && !ut.getDetails().equals("")) 
          {
            dlm2.addElement(ut.getName()+ " (" + ut.getDetails() + ")");
            fullUtensilList.add(ut);
          } 
          else 
          {
            dlm2.addElement(ut.getName());
            fullUtensilList.add(ut);
          }
          stepUtensilCombo.addItem(ut.getName());
        }
        for (Step st : loaded.getSteps()) 
        {
          if (st.getDetails() == null || st.getDetails().equals("")) 
          {
            dlm3.addElement(st.getAction() + " the " 
                + st.getSourceUtensilOrIngredient() + " using a "
                + st.getDestinationUtensil() + ". Estimated Time: " + st.getTime() + " minutes");
          } 
          else 
          {
            dlm3.addElement(st.getAction() + " the "
                + st.getSourceUtensilOrIngredient() + " using a "
                + st.getDestinationUtensil() + " " + st.getDetails() 
                + ". Estimated Time: " + st.getTime() + " minutes");
          }
          
          fullStepList.add(st);
        }
        ingList.setModel(dlm);
        stepList.setModel(dlm3);
        utensilList.setModel(dlm2);
      } catch (ClassNotFoundException e1) 
      {
        // TODO Auto-generated catch block
        e1.printStackTrace();
      } catch (IOException e1) 
      {
        // TODO Auto-generated catch block
        e1.printStackTrace();
      }
    }
  }
  
  public void openMeal(final Recipe recipe) 
  {
    name.setText(recipe.getName());
    serves.setText(String.valueOf(recipe.getServes()));
    for (RecipeIngredient ri : recipe.getIngredients()) 
    {
      fullIngredientList.add(ri);
    }
    //sort fullingredientslist somehow
    for (RecipeIngredient ri : fullIngredientList) 
    {
      if (ri.getDetails() != null && !ri.getDetails().equals("")) 
      {
        dlm.addElement(ri.getAmount() + " " + ri.getUnit().
            toLowerCase() + " (" + ri.getDetails() + ") " + ri.getName());
      } 
      else 
      {
        dlm.addElement(ri.getAmount() + " " + ri.getUnit().toLowerCase() + " " + ri.getName());
      }
    }
    
    for (Utensil ut : recipe.getUtenils()) 
    {
      if (ut.getDetails() != null && !ut.getDetails().equals("")) 
      {
        dlm2.addElement(ut.getName()+ " (" + ut.getDetails() + ")");
        fullUtensilList.add(ut);
      } else 
      {
        dlm2.addElement(ut.getName());
        fullUtensilList.add(ut);
      }
      stepUtensilCombo.addItem(ut.getName());
    }
    for (Step st : recipe.getSteps()) 
    {
      if (st.getDetails() == null || st.getDetails().equals("")) 
      {
        dlm3.addElement(st.getAction() + " the " + st.getSourceUtensilOrIngredient() + " using a "
            + st.getDestinationUtensil() + ". Estimated Time: " + st.getTime() + " minutes");
      } 
      else 
      {
        dlm3.addElement(st.getAction() + " the " + st.getSourceUtensilOrIngredient() + " using a "
            + st.getDestinationUtensil() + " " 
            + st.getDetails() + ". Estimated Time: " + st.getTime() + " minutes");
      }
      
      fullStepList.add(st);
    }
    ingList.setModel(dlm);
    stepList.setModel(dlm3);
    utensilList.setModel(dlm2);
  }
  
  public String getCurrentFileName() 
  {
    return currentFileName;
  }
  
}
