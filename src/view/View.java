package view;

import config.Config;
import model.DesignManager;
import model.ICalculatorInterface;
import model.Logic;
import view.customUIs.CustomButtonUI;
import view.customUIs.CustomScrollBarUI;
import javax.swing.*;
import java.awt.*;
import javax.swing.border.EmptyBorder;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class View extends JFrame implements ICalculatorInterface {
    private final String[][] buttonTexts = {
            {"xʸ", "🎨", "C", "±", "⌫", "÷"},
            {"√", "sin", "7", "8", "9", "×"},
            {"x!", "cos", "4", "5", "6", "-"},
            {"¹⁄ₓ", "tan", "1", "2", "3", "+"},
            {"log₁₀", "%", "fx", "0", ".", "="}
    };
    private final String[] advancedButtons = {
            "xʸ", "🎨", "√", "sin", "cos", "tan", "x!", "¹⁄ₓ", "log₁₀", "%"
    };
    private JButton[][] buttonsArray = new JButton[buttonTexts.length][buttonTexts[0].length];
    private JTextPane display;

    // UI-Elemente
    private final CustomButtonUI buttonUI;
    private final CustomButtonUI operatorButtonsUI;

    private final Logic logic;
    private boolean advancedModeEnabled = true; // Advanced mode will be disabled by constructor


    public View(Logic l) {
        this.logic = l;
        logic.subscribe(this);

        // Sets the Design to the last used one
        DesignManager.changeActiveDesign(DesignManager.getActiveDesign());

        // Main panel with BorderLayout
        JPanel mainPanel = new JPanel(new BorderLayout());

        // Display at the top
        display = new JTextPane();
        display.setEditable(false);
        display.setBackground(Config.BACKGROUND);
        //Changes Text color depending on the Brightness of the Background
        float[] hsbWerte = Color.RGBtoHSB(Config.BACKGROUND.getRed(), Config.BACKGROUND.getGreen(), Config.BACKGROUND.getBlue(), null);
        float brightness = hsbWerte[2];
        if (brightness < 0.5f) {
            display.setForeground(Color.WHITE);
        } else {
            display.setForeground(Color.BLACK);
        }
        display.setFont(new Font("Helvetica", Font.PLAIN, (Config.WIDTH / 6) - 1));
        display.setPreferredSize(new Dimension(new Dimension(Config.WIDTH, Config.WIDTH / 6)));
        display.setText("0"); // Set initial text

        // Text-Alignment
        StyledDocument doc = display.getStyledDocument();
        SimpleAttributeSet rightAlign = new SimpleAttributeSet();
        StyleConstants.setAlignment(rightAlign, StyleConstants.ALIGN_RIGHT);
        doc.setParagraphAttributes(0, doc.getLength(), rightAlign, false);

        //Add horizontal Scrollbar
        JScrollPane scrollPane = new JScrollPane(display);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
        scrollPane.setBackground(Config.BACKGROUND);
        scrollPane.setBorder(null);
        scrollPane.setViewportBorder(null);
        // Custom Scrollbar
        JScrollBar horizontalScrollBar = scrollPane.getHorizontalScrollBar();
        horizontalScrollBar.setUI(new CustomScrollBarUI());
        mainPanel.add(scrollPane, BorderLayout.NORTH);

        // Button panel with GridBagLayout
        JPanel buttonPanel = new JPanel(new GridBagLayout());
        buttonPanel.setBackground(Config.BACKGROUND);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1;
        gbc.weighty = 1;

        // Create UIs
        buttonUI = new CustomButtonUI(Config.BUTTON_COLOR);
        operatorButtonsUI = new CustomButtonUI(Config.OPERATOR_COLOR);

        // Add buttonTexts to the panel
        for (int i = 0; i < buttonTexts.length; i++) {
            for (int j = 0; j < buttonTexts[i].length; j++) {
                JButton button = new JButton(buttonTexts[i][j]);
                buttonsArray[i][j] = button;

                addButton(buttonPanel, button, j, i, gbc);
                addUI(button);
                setUniformSize(button);

                button.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mousePressed(MouseEvent e) {
                        super.mousePressed(e);
                        logic.buttonPressed(button.getText());
                    }
                });
            }
        }

        mainPanel.add(buttonPanel, BorderLayout.CENTER);
        mainPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
        add(mainPanel); //To have the Border

        //Frame properties
        setContentPane(mainPanel);
        setTitle("Calculator");
        setSize(Config.WIDTH, Config.HEIGHT);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        getContentPane().setBackground(Config.BACKGROUND);
        ImageIcon icon = new ImageIcon("src/assets/Icon-512.png");
        setIconImage(icon.getImage());

        changeAdvancedMode();
        setVisible(true);
        setFocusable(true);
    }

    @Override
    public void displayNumberChange(String number) {
        display.setText(number);
        if (number.isEmpty()) display.setText("0");
    }

    @Override
    public void displayNull() {
        display.setText("");
    }

    @Override
    public void designChangePress() {
        new DesignChangeDialog(View.this, logic);
    }

    @Override
    public void advancedModePress() {
        changeAdvancedMode();
    }

    public void changeAdvancedMode() {
        advancedModeEnabled = !advancedModeEnabled;
        if (advancedModeEnabled) {
            setSize(Config.WIDTH, Config.HEIGHT);
        } else {
            setSize((Config.WIDTH * 2) / 3, Config.HEIGHT);
        }

        for (String advancedButton : advancedButtons) {
            for (JButton[] buttonsRow : buttonsArray) {
                for (JButton button : buttonsRow) {
                    if (button.getText().equals(advancedButton)) {
                        button.setVisible(advancedModeEnabled);
                    }
                }
            }
        }
    }

    private void setUniformSize(AbstractButton button) {
        int size = Config.WIDTH / 6;
        Dimension buttonSize = new Dimension(size, size); // Passen Sie die Größe nach Bedarf an
        button.setPreferredSize(buttonSize);
        button.setMinimumSize(buttonSize);
        button.setMaximumSize(buttonSize);
        button.setFont(new Font("Arial", Font.PLAIN, 14)); // Passen Sie die Schriftgröße nach Bedarf an
        button.setHorizontalAlignment(SwingConstants.CENTER);
        button.setVerticalAlignment(SwingConstants.CENTER);
    }

    private void addUI(AbstractButton button) {
        switch (button.getText()) {
            case "C", "±", "⌫", "÷", "×", "-", "+" -> button.setUI(operatorButtonsUI);
            case "=" -> button.setUI(new CustomButtonUI(Config.EQUAL_COLOR));
            default -> button.setUI(buttonUI);
        }
    }

    private void addButton(JPanel panel, AbstractButton button, int x, int y, GridBagConstraints gbc) {
        gbc.gridx = x;
        gbc.gridy = y;
        panel.add(button, gbc);
    }
}