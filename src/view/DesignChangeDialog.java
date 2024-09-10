package view;

import config.Config;
import model.Logic;
import model.DesignManager;
import view.customUIs.CustomColorChangingButtonUI;
import view.customUIs.CustomComboBoxUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.HashMap;
import java.util.Map;

/**
 * The ColorChangeDialog class represents a dialog that allows users to change
 * the colors of various components in the calculator application, save designs,
 * and load existing designs.
 */
public class DesignChangeDialog extends JDialog {
    private final View parentFrame;
    private final Logic logic;
    JPanel designButtons;
    JPanel colorButtons;
    private final JComboBox<String> designComboBox;
    private final String[] buttonTexts = {"Change Background Color", "Change Number Buttons Color",
            "Change Operator Buttons Color", "Change Equals Button Color", "Save Design", "Delete Design", "Update"};
    private Map<String, JButton> buttons = new HashMap<>();
    /**
     * Constructs a ColorChangeDialog with the specified parent frame and logic.
     *
     * @param frame the parent JFrame that the dialog is centered on
     * @param log   the Logic instance used for calculations
     */
    public DesignChangeDialog(View frame, Logic log) {
        this.parentFrame = frame;
        this.logic = log;
        setTitle("Change Colors");
        setLocationRelativeTo(frame);
        setSize(470, 300);
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        // Color Buttons
        colorButtons = new JPanel(new GridLayout(2, 2));
        colorButtons.setBackground(Config.BACKGROUND);
        // Design saving and deleting
        designButtons = new JPanel(new GridLayout(1, 2));
        designButtons.setBackground(Config.BACKGROUND);
        CustomColorChangingButtonUI ui = new CustomColorChangingButtonUI(logic);

        // Adds buttons to the dialog
        for (int i = 0; i < buttonTexts.length; i++) {
            JButton button = new JButton(buttonTexts[i]);
            buttons.put(buttonTexts[i], button);
            button.setUI(ui);
            addToPanel(button);
            button.addActionListener(e -> {
                buttonPressed(button.getText());
            });
        }

        // COnfigures the ComboBox
        designComboBox = new JComboBox<>(DesignManager.getAllDesignNames());
        designComboBox.setUI(new CustomComboBoxUI());
        designComboBox.addActionListener(e -> {
            String selectedDesign = (String) designComboBox.getSelectedItem();
            if (selectedDesign != null) {
                Map<String, Color> design = DesignManager.getDesignByName(selectedDesign);
                if (design != null) {
                    Config.CHANGE_BACKGROUND(design.get("background"));
                    Config.CHANGE_BUTTON_COLOR(design.get("number"));
                    Config.CHANGE_OPERATOR_COLOR(design.get("operator"));
                    Config.CHANGE_EQUAL_COLOR(design.get("equals"));
                }
            }
        });

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
        add(buttons.get("Update"), gbc);

        getContentPane().setBackground(Config.BACKGROUND);
        setVisible(true);
    }

    private void buttonPressed(String text) {
        switch (text) {
            case "Change Background Color":
                Color newColor = JColorChooser.showDialog(DesignChangeDialog.this, "Choose Background Color", Config.BACKGROUND);
                if (newColor != null) {
                    Config.CHANGE_BACKGROUND(newColor);
                }
                break;
            case "Change Number Buttons Color":
                Color newNumberColor = JColorChooser.showDialog(DesignChangeDialog.this, "Choose Button Color", Config.BUTTON_COLOR);
                if (newNumberColor != null) {
                    Config.CHANGE_BUTTON_COLOR(newNumberColor);
                }
                break;
            case "Change Operator Buttons Color":
                Color newOperatorColor = JColorChooser.showDialog(DesignChangeDialog.this, "Choose Operator Color", Config.OPERATOR_COLOR);
                if (newOperatorColor != null) {
                    Config.CHANGE_OPERATOR_COLOR(newOperatorColor);
                }
                break;
            case "Change Equals Button Color":
                Color newButtonColor = JColorChooser.showDialog(DesignChangeDialog.this, "Choose Equals Color", Config.EQUAL_COLOR);
                if (newButtonColor != null) {
                    Config.CHANGE_EQUAL_COLOR(newButtonColor);
                }
                break;
            case "Save Design":
                String name = JOptionPane.showInputDialog(this, "Enter design name:");
                if (name != null && !name.trim().isEmpty()) {
                    DesignManager.addCurrentDesign(name, Config.BACKGROUND, Config.BUTTON_COLOR, Config.OPERATOR_COLOR, Config.EQUAL_COLOR);
                    updateDesignComboBox();
                }
                break;
            case "Delete Design":
                String selectedDesign = (String) designComboBox.getSelectedItem();
                if (selectedDesign != null) {
                    DesignManager.deleteDesignByName(selectedDesign);
                    updateDesignComboBox();
                }
                break;
            case "Update":
                dispose();
                logic.unsubscribe(parentFrame);
                this.parentFrame.dispose();
                logic.reset();
                View view = new View(logic);
                view.changeAdvancedMode();
                break;
            default:
                System.out.println("Unknown button pressed");
        }
    }

    private void addToPanel(JButton button) {
        switch (button.getText()) {
            case "Change Background Color", "Change Number Buttons Color", "Change Operator Buttons Color", "Change Equals Button Color":
                colorButtons.add(button);
                break;
            case "Save Design", "Delete Design":
                designButtons.add(button);
                break;
            case "Update":
                break;
        }
    }

    /**
     * Updates the design ComboBox to reflect the current saved designs.
     */
    private void updateDesignComboBox() {
        designComboBox.setModel(new DefaultComboBoxModel<>(DesignManager.getAllDesignNames()));
    }
}

