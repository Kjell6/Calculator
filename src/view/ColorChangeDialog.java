package view;

import config.Config;
import model.Logic;
import model.DesignManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.XMLEncoder;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class ColorChangeDialog extends JDialog {
    private JFrame parentFrame;
    private Logic logic;
    private JComboBox<String> designComboBox;

    public ColorChangeDialog(JFrame frame, Logic log) {
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
        designComboBox = new JComboBox<>(DesignManager.getDesignNames());

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

        changeBackgroundColorButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Color newColor = JColorChooser.showDialog(ColorChangeDialog.this, "Choose Background Color", Config.BACKGROUND);
                if (newColor != null) {
                    Config.CHANGE_BACKGROUND(newColor);
                }
            }
        });

        changeForegroundColorButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Color newColor = JColorChooser.showDialog(ColorChangeDialog.this, "Choose Button Color", Config.BUTTON_COLOR);
                if (newColor != null) {
                    Config.CHANGE_BUTTON_COLOR(newColor);
                }
            }
        });

        changeButtonColorButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Color newColor = JColorChooser.showDialog(ColorChangeDialog.this, "Choose Operator Color", Config.OPERATOR_COLOR);
                if (newColor != null) {
                    Config.CHANGE_OPERATOR_COLOR(newColor);
                }
            }
        });

        changeEqualsColorButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Color newColor = JColorChooser.showDialog(ColorChangeDialog.this, "Choose Equals Color", Config.EQUAL_COLOR);
                if (newColor != null) {
                    Config.CHANGE_EQUAL_COLOR(newColor);
                }
            }
        });

        saveButton.addActionListener(e -> {
            String name = JOptionPane.showInputDialog(this, "Enter design name:");
            if (name != null && !name.trim().isEmpty()) {
                DesignManager.saveTempDesign(name, Config.BACKGROUND, Config.BUTTON_COLOR, Config.OPERATOR_COLOR, Config.EQUAL_COLOR);
                updateDesignComboBox();
            }
        });

        designComboBox.addActionListener(e -> {
            String selectedDesign = (String) designComboBox.getSelectedItem();
            if (selectedDesign != null) {
                Map<String, Color> design = DesignManager.getDesign(selectedDesign);
                if (design != null) {
                    Config.CHANGE_BACKGROUND(design.get("background"));
                    Config.CHANGE_BUTTON_COLOR(design.get("number"));
                    Config.CHANGE_OPERATOR_COLOR(design.get("operator"));
                    Config.CHANGE_EQUAL_COLOR(design.get("equals"));
                }
            }
        });

        deleteButton.addActionListener(e -> {
            String selectedDesign = (String) designComboBox.getSelectedItem();
            if (selectedDesign != null) {
                DesignManager.deleteDesign(selectedDesign);
                updateDesignComboBox();
            }
        });

        updateButton.addActionListener(e -> {
            dispose();
            parentFrame.dispose();
            View view = new View(logic);
            view.changeAdvancedMode();
        });

        colorButtons.add(changeBackgroundColorButton);
        colorButtons.add(changeForegroundColorButton);
        colorButtons.add(changeButtonColorButton);
        colorButtons.add(changeEqualsColorButton);
        colorButtons.setBackground(Config.BACKGROUND);
        add(colorButtons);
        add(designComboBox);
        designButtons.add(saveButton);
        designButtons.add(deleteButton);
        designButtons.setBackground(Config.BACKGROUND);
        add(designButtons);
        add(updateButton);

        // Add components using GridBagLayout
        gbc.fill = GridBagConstraints.BOTH;
        gbc.insets = new Insets(5, 5, 5, 5);

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

    private void updateDesignComboBox() {
        designComboBox.setModel(new DefaultComboBoxModel<>(DesignManager.getDesignNames()));
    }
}
