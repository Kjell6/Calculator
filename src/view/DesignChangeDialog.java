package view;

import config.Config;
import model.Logic;
import model.DesignManager;
import view.customUIs.CustomColorChangingButtonUI;
import view.customUIs.CustomComboBoxUI;

import javax.swing.*;
import java.awt.*;
import java.util.Map;

/**
 * The ColorChangeDialog class represents a dialog that allows users to change
 * the colors of various components in the calculator application, save designs,
 * and load existing designs.
 */
public class DesignChangeDialog extends JDialog {
    private final JFrame parentFrame;
    private final Logic logic;
    private final JComboBox<String> designComboBox;

    /**
     * Constructs a ColorChangeDialog with the specified parent frame and logic.
     *
     * @param frame the parent JFrame that the dialog is centered on
     * @param log   the Logic instance used for calculations
     */
    public DesignChangeDialog(JFrame frame, Logic log) {
        this.parentFrame = frame;
        this.logic = log;
        setTitle("Change Colors");
        setLocationRelativeTo(frame);
        setSize(470, 300);
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        // Color Buttons
        JPanel colorButtons = new JPanel(new GridLayout(2, 2));
        JButton changeBackgroundColorButton = new JButton("Change Background Color");
        JButton changeForegroundColorButton = new JButton("Change Number Buttons Color");
        JButton changeButtonColorButton = new JButton("Change Operator Buttons Color");
        JButton changeEqualsColorButton = new JButton("Change Equals Button Color");

        // Design saving and deleting
        JPanel designButtons = new JPanel(new GridLayout(1, 2));
        JButton saveButton = new JButton("Save Design");
        JButton deleteButton = new JButton("Delete Design");

        designComboBox = new JComboBox<>(DesignManager.getAllDesignNames());
        designComboBox.setUI(new CustomComboBoxUI());

        //Update Button
        JButton updateButton = new JButton("Update");

        CustomColorChangingButtonUI ui = new CustomColorChangingButtonUI();
        changeBackgroundColorButton.setUI(ui);
        changeForegroundColorButton.setUI(ui);
        changeButtonColorButton.setUI(ui);
        changeEqualsColorButton.setUI(ui);
        updateButton.setUI(ui);
        saveButton.setUI(ui);
        deleteButton.setUI(ui);

        //Add Listeners
        changeBackgroundColorButton.addActionListener(e -> {
            Color newColor = JColorChooser.showDialog(DesignChangeDialog.this, "Choose Background Color", Config.BACKGROUND);
            if (newColor != null) {
                Config.CHANGE_BACKGROUND(newColor);
            }
        });

        changeForegroundColorButton.addActionListener(e -> {
            Color newColor = JColorChooser.showDialog(DesignChangeDialog.this, "Choose Button Color", Config.BUTTON_COLOR);
            if (newColor != null) {
                Config.CHANGE_BUTTON_COLOR(newColor);
            }
        });

        changeButtonColorButton.addActionListener(e -> {
            Color newColor = JColorChooser.showDialog(DesignChangeDialog.this, "Choose Operator Color", Config.OPERATOR_COLOR);
            if (newColor != null) {
                Config.CHANGE_OPERATOR_COLOR(newColor);
            }
        });

        changeEqualsColorButton.addActionListener(e -> {
            Color newColor = JColorChooser.showDialog(DesignChangeDialog.this, "Choose Equals Color", Config.EQUAL_COLOR);
            if (newColor != null) {
                Config.CHANGE_EQUAL_COLOR(newColor);
            }
        });

        saveButton.addActionListener(e -> {
            String name = JOptionPane.showInputDialog(this, "Enter design name:");
            if (name != null && !name.trim().isEmpty()) {
                DesignManager.addCurrentDesign(name, Config.BACKGROUND, Config.BUTTON_COLOR, Config.OPERATOR_COLOR, Config.EQUAL_COLOR);
                updateDesignComboBox();
            }
        });

        designComboBox.addActionListener(e -> {
            String selectedDesign = (String) designComboBox.getSelectedItem();
            if (selectedDesign != null) {
                DesignManager.changeActiveDesign(selectedDesign);
            }
        });

        deleteButton.addActionListener(e -> {
            String selectedDesign = (String) designComboBox.getSelectedItem();
            if (selectedDesign != null) {
                DesignManager.deleteDesignByName(selectedDesign);
                updateDesignComboBox();
            }
        });

        // Updates the current Design
        updateButton.addActionListener(e -> {
            dispose();
            parentFrame.dispose();
            View view = new View(logic);
            view.changeAdvancedMode();
        });

        //Adds Color Buttons to Button Panel
        colorButtons.add(changeBackgroundColorButton);
        colorButtons.add(changeForegroundColorButton);
        colorButtons.add(changeButtonColorButton);
        colorButtons.add(changeEqualsColorButton);
        colorButtons.setBackground(Config.BACKGROUND);

        //Adds Design Buttons to Design Panel
        designButtons.add(saveButton);
        designButtons.add(deleteButton);
        designButtons.setBackground(Config.BACKGROUND);

        // Add components using GridBagLayout
        gbc.fill = GridBagConstraints.BOTH;
        gbc.insets = new Insets(5, 5, 5, 5);

        //Adds Panels to Dialog
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 1;
        gbc.gridheight = 2;
        gbc.weightx = 1.0;
        gbc.weighty = 2.0;
        add(colorButtons, gbc);
        gbc.gridy = 2;
        gbc.gridheight = 1;
        gbc.weighty = 1.0;
        add(designComboBox, gbc);
        gbc.gridy = 3;
        add(designButtons, gbc);
        gbc.gridy = 4;
        add(updateButton, gbc);

        getContentPane().setBackground(Config.BACKGROUND);
        setVisible(true);
    }

    /**
     * Updates the design ComboBox to reflect the current saved designs.
     */
    private void updateDesignComboBox() {
        designComboBox.setModel(new DefaultComboBoxModel<>(DesignManager.getAllDesignNames()));
    }
}
