package view;

import config.Config;
import model.ICalculatorInterface;
import model.Logic;

import javax.sound.sampled.*;
import javax.swing.*;
import java.awt.*;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;

public class View extends JFrame implements ICalculatorInterface {
    private JPanel mainPanel;
    private JButton a1Button;
    private JButton a2Button;
    private JButton a3Button;
    private JButton a4Button;
    private JButton a5Button;
    private JButton a6Button;
    private JButton a7Button;
    private JButton a8Button;
    private JButton a9Button;
    private JButton a0Button;
    private JButton buttonComma;
    private JButton divide;
    private JButton multi;
    private JButton minus;
    private JButton plus;
    private JButton equals;
    private JTextPane display;
    private JButton clear;
    private JButton delete;
    private JButton negPos;
    private final Logic logic;

    public View(Logic l) {
        this.logic = l;
        logic.subscribe(this);

        initializeComponents();

        // Main panel with BorderLayout
        mainPanel = new JPanel(new BorderLayout());

        // Display at the top
        display = new JTextPane();
        display.setEditable(false);
        display.setBackground(Config.BACKGROUND);
        display.setForeground(Color.WHITE);
        display.setFont(new Font("Arial", Font.PLAIN, 52)); // Increase font size
        display.setPreferredSize(new Dimension(300, 60)); // Set preferred size
        display.setText("0"); // Set initial text
        mainPanel.add(display,BorderLayout.NORTH);
        // Text-Alignment
        StyledDocument doc = display.getStyledDocument();
        SimpleAttributeSet rightAlign = new SimpleAttributeSet();
        StyleConstants.setAlignment(rightAlign, StyleConstants.ALIGN_RIGHT);
        doc.setParagraphAttributes(0, doc.getLength(), rightAlign, false);
        // Button panel with GridBagLayout
        JPanel buttonPanel = new JPanel(new GridBagLayout());
        buttonPanel.setBackground(Config.BACKGROUND);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1;
        gbc.weighty = 1;

        // Add buttons to the grid
        addButton(buttonPanel, clear, 0, 0, gbc);
        addButton(buttonPanel, negPos, 1, 0, gbc);
        addButton(buttonPanel, delete, 2, 0, gbc);
        addButton(buttonPanel, divide, 3, 0, gbc);

        addButton(buttonPanel, a7Button, 0, 1, gbc);
        addButton(buttonPanel, a8Button, 1, 1, gbc);
        addButton(buttonPanel, a9Button, 2, 1, gbc);
        addButton(buttonPanel, multi, 3, 1, gbc);

        addButton(buttonPanel, a4Button, 0, 2, gbc);
        addButton(buttonPanel, a5Button, 1, 2, gbc);
        addButton(buttonPanel, a6Button, 2, 2, gbc);
        addButton(buttonPanel, minus, 3, 2, gbc);

        addButton(buttonPanel, a1Button, 0, 3, gbc);
        addButton(buttonPanel, a2Button, 1, 3, gbc);
        addButton(buttonPanel, a3Button, 2, 3, gbc);
        addButton(buttonPanel, plus, 3, 3, gbc);

        // Make "0" button span two columns
        gbc.gridwidth = 2;
        addButton(buttonPanel, a0Button, 0, 4, gbc);
        gbc.gridwidth = 1;
        addButton(buttonPanel, buttonComma, 2, 4, gbc);
        addButton(buttonPanel, equals, 3, 4, gbc);

        //Frame Properties
        mainPanel.add(buttonPanel, BorderLayout.CENTER);
        add(mainPanel);
        setContentPane(mainPanel);
        setTitle("Calculator");
        setSize(300, 400);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(true);
        getContentPane().setBackground(Config.BACKGROUND);

        //adding Button Lisener and UI
        CustomButtonUI buttonUI = new CustomButtonUI();
        a1Button.setUI(buttonUI);
        a1Button.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);
                logic.numberInput(1);
                playSound(Config.NUMBER_SOUND);
            }
        });
        a2Button.setUI(buttonUI);
        a2Button.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);
                logic.numberInput(2);
                playSound(Config.NUMBER_SOUND);
            }
        });
        a3Button.setUI(buttonUI);
        a3Button.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);
                logic.numberInput(3);
                playSound(Config.NUMBER_SOUND);
            }
        });
        a4Button.setUI(buttonUI);
        a4Button.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);
                logic.numberInput(4);
                playSound(Config.NUMBER_SOUND);
            }
        });
        a5Button.setUI(buttonUI);
        a5Button.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);
                logic.numberInput(5);
                playSound(Config.NUMBER_SOUND);
            }
        });
        a6Button.setUI(buttonUI);
        a6Button.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);
                logic.numberInput(6);
                playSound(Config.NUMBER_SOUND);
            }
        });
        a7Button.setUI(buttonUI);
        a7Button.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);
                logic.numberInput(7);
                playSound(Config.NUMBER_SOUND);
            }
        });
        a8Button.setUI(buttonUI);
        a8Button.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);
                logic.numberInput(8);
                playSound(Config.NUMBER_SOUND);
            }
        });
        a9Button.setUI(buttonUI);
        a9Button.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);
                logic.numberInput(9);
                playSound(Config.NUMBER_SOUND);
            }
        });
        a0Button.setUI(buttonUI);
        a0Button.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);
                logic.numberInput(0);
                playSound(Config.NUMBER_SOUND);
            }
        });
        buttonComma.setUI(buttonUI);
        buttonComma.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);
                logic.decimalP();
                playSound(Config.NUMBER_SOUND);
            }
        });
        equals.setUI(new CustomButtonUI(Config.EQUAL_COLOR));
        equals.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);
                logic.result();
                playSound(Config.EQUAL_SOUND);
            }
        });
        CustomButtonUI operatorButtons = new CustomButtonUI(Config.OPERATOR_COLOR);
        plus.setUI(operatorButtons);
        plus.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);
                logic.setOperator(1);
                playSound(Config.OPERATOR_SOUND);
            }
        });
        minus.setUI(operatorButtons);
        minus.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);
                logic.setOperator(2);
                playSound(Config.OPERATOR_SOUND);
            }
        });
        multi.setUI(operatorButtons);
        multi.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);
                logic.setOperator(3);
                playSound(Config.OPERATOR_SOUND);
            }
        });
        divide.setUI(operatorButtons);
        divide.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);
                logic.setOperator(4);
                playSound(Config.OPERATOR_SOUND);
            }
        });
        clear.setUI(operatorButtons);
        clear.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);
                logic.clear();
                playSound(Config.DELETE_SOUND);
            }
        });
        delete.setUI(operatorButtons);
        delete.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);
                logic.delete();
                playSound(Config.DELETE_SOUND);
            }
        });
        negPos.setUI(operatorButtons);
        negPos.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);
                logic.plusMinus();
                playSound(Config.OPERATOR_SOUND);
            }
        });
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

    private static void playSound(String path) {
        try {
            File audioFile = new File(path);
            if (!audioFile.exists()) {
                System.err.println("Audio file not found: " + path);
                return;
            }

            AudioInputStream audioStream = AudioSystem.getAudioInputStream(audioFile);
            Clip clip = AudioSystem.getClip();
            clip.open(audioStream);
            clip.start();
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException ex) {
            ex.printStackTrace();
        }
    }
    private void initializeComponents() {
        clear = new JButton("AC");
        negPos = new JButton("±");
        delete = new JButton("⌫");
        divide = new JButton("÷");
        a7Button = new JButton("7");
        a8Button = new JButton("8");
        a9Button = new JButton("9");
        multi = new JButton("×");
         a4Button = new JButton("4");
        a5Button = new JButton("5");
        a6Button = new JButton("6");
        minus = new JButton("-");
        a1Button = new JButton("1");
        a2Button = new JButton("2");
        a3Button = new JButton("3");
        plus = new JButton("+");
        a0Button = new JButton("0");
        buttonComma = new JButton(".");
        equals = new JButton("=");
    }

    private void addButton(JPanel panel, JButton button, int x, int y, GridBagConstraints gbc) {
        gbc.gridx = x;
        gbc.gridy = y;
        panel.add(button, gbc);
    }
}