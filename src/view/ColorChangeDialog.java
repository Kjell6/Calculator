package view;

import config.Config;
import model.Logic;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ColorChangeDialog extends JDialog {

    public  ColorChangeDialog(JFrame frame, Logic log) {
        setTitle("Change Colors");
        setLocationRelativeTo(frame);
        setSize(400, 220);
        setLayout(new GridLayout(3, 2));

        JButton changeBackgroundColorButton = new JButton("Change Background Color");
        JButton changeForegroundColorButton = new JButton("Change Button Color");
        JButton changeButtonColorButton = new JButton("Change Operator Color");
        JButton changeEqualsColorButton = new JButton("Change Equals Color");
        JButton updateButton = new JButton("Update");
        CustomColorChangingButtonUI ui = new CustomColorChangingButtonUI();
        changeBackgroundColorButton.setUI(ui);
        changeForegroundColorButton.setUI(ui);
        changeButtonColorButton.setUI(ui);
        changeEqualsColorButton.setUI(ui);
        updateButton.setUI(ui);

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



        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                frame.dispose();
                View view = new View(log);
                view.changeAdvancedMode();
            }
        });

        add(changeBackgroundColorButton);
        add(changeForegroundColorButton);
        add(changeButtonColorButton);
        add(changeEqualsColorButton);
        add(updateButton);
        getContentPane().setBackground(Config.BACKGROUND);
        setVisible(true);
    }
}
