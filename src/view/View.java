package view;

import config.Config;
import model.ICalculatorInterface;
import model.Logic;
import model.Operator;
import javax.sound.sampled.*;
import javax.swing.*;
import java.awt.*;
import javax.swing.border.EmptyBorder;
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
    private JButton power;
    private JButton sqrt;
    private JButton sin;
    private JButton cos;
    private JButton tan;
    private JButton faculty;
    private JTextPane display;
    private JButton clear;
    private JButton delete;
    private JButton negPos;
    private JButton reciprocal;
    private JButton logarithm;
    private JButton modulo;
    private JToggleButton advancedModeSwitch;
    private JToggleButton soundSwitch;
    private  CustomButtonUI buttonUI;
    private CustomButtonUI operatorButtons;

    private final Logic logic;
    private boolean advancedModeEnabled = false;
    private boolean soundOn = false;

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
        display.setFont(new Font("Helvetica", Font.PLAIN, 60));
        display.setPreferredSize(new Dimension(300, 50));
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

        mainPanel.add(advancedModeSwitch, BorderLayout.SOUTH);
        addButton(buttonPanel, power, 0, 0, gbc);
        addButton(buttonPanel, soundSwitch, 1, 0, gbc);
        //addButton(buttonPanel, advancedModeSwitch, 1, 0, gbc);
        addButton(buttonPanel, clear, 2, 0, gbc);
        addButton(buttonPanel, negPos, 3, 0, gbc);
        addButton(buttonPanel, delete, 4, 0, gbc);
        addButton(buttonPanel, divide, 5, 0, gbc);

        addButton(buttonPanel, sqrt, 0, 1, gbc);
        addButton(buttonPanel, sin, 1, 1, gbc);
        addButton(buttonPanel, a7Button, 2, 1, gbc);
        addButton(buttonPanel, a8Button, 3, 1, gbc);
        addButton(buttonPanel, a9Button, 4, 1, gbc);
        addButton(buttonPanel, multi, 5, 1, gbc);

        addButton(buttonPanel, faculty, 0, 2, gbc);
        addButton(buttonPanel, cos, 1, 2, gbc);
        addButton(buttonPanel, a4Button, 2, 2, gbc);
        addButton(buttonPanel, a5Button, 3, 2, gbc);
        addButton(buttonPanel, a6Button, 4, 2, gbc);
        addButton(buttonPanel, minus, 5,2, gbc);

        addButton(buttonPanel, reciprocal, 0, 3, gbc);
        addButton(buttonPanel, tan, 1, 3, gbc);
        addButton(buttonPanel, a1Button, 2, 3, gbc);
        addButton(buttonPanel, a2Button, 3, 3, gbc);
        addButton(buttonPanel, a3Button, 4, 3, gbc);
        addButton(buttonPanel, plus, 5, 3, gbc);

        addButton(buttonPanel, logarithm, 0, 4, gbc);
        addButton(buttonPanel, modulo, 1, 4, gbc);
        addButton(buttonPanel, advancedModeSwitch, 2, 4, gbc);
        addButton(buttonPanel, a0Button, 3, 4, gbc);
        addButton(buttonPanel, buttonComma, 4, 4, gbc);
        addButton(buttonPanel, equals, 5, 4, gbc);

        //Frame Properties
        mainPanel.add(buttonPanel, BorderLayout.CENTER);
        mainPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
        add(mainPanel);

        setContentPane(mainPanel);
        setTitle("Calculator");
        setSize(360, 400); //height will be: (height / 6) * 4
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        getContentPane().setBackground(Config.BACKGROUND);
        ImageIcon icon = new ImageIcon("src/assets/Icon-512.png");
        setIconImage(icon.getImage());

        //adding Button Lisener and UI
        buttonUI = new CustomButtonUI();

        advancedModeSwitch.setUI(buttonUI);
        advancedModeSwitch.addActionListener(e -> {
            playSound(Config.OPERATOR_SOUND);
            advancedModeEnabled = advancedModeSwitch.isSelected();
            updateAdvancedButtonsVisibility();
        });
        soundSwitch.setUI(buttonUI);
        soundSwitch.addActionListener(e -> {
            playSound(Config.OPERATOR_SOUND);
            soundOn = soundSwitch.isSelected();
            String soundSwitchText = soundOn ? "\uD83D\uDD09" : "\uD83D\uDD07";
            soundSwitch.setText(soundSwitchText);
        });

        //Adds Listeners to the number Buttons
        addNumberListener(a1Button, 1);
        addNumberListener(a2Button, 2);
        addNumberListener(a3Button, 3);
        addNumberListener(a4Button, 4);
        addNumberListener(a5Button, 5);
        addNumberListener(a6Button, 6);
        addNumberListener(a7Button, 7);
        addNumberListener(a8Button, 8);
        addNumberListener(a9Button, 9);
        addNumberListener(a0Button, 0);

        //Adds Listeners to the Operator Buttons
        operatorButtons = new CustomButtonUI(Config.OPERATOR_COLOR);
        addOperatorListener(plus, Operator.PLUS);
        addOperatorListener(minus, Operator.MINUS);
        addOperatorListener(multi, Operator.MULTI);
        addOperatorListener(divide, Operator.DIVIDE);
        addOperatorListener(power, Operator.POWER);
        addOperatorListener(sqrt, Operator.SQRT);
        addOperatorListener(faculty, Operator.FACULTY);
        addOperatorListener(sin, Operator.SIN);
        addOperatorListener(cos, Operator.COS);
        addOperatorListener(tan, Operator.TAN);
        addOperatorListener(reciprocal, Operator.RECOPROCAL);
        addOperatorListener(logarithm, Operator.LOGARITHM);
        addOperatorListener(modulo, Operator.MODULO);

        equals.setUI(new CustomButtonUI(Config.EQUAL_COLOR));
        equals.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);
                logic.result();
                playSound(Config.EQUAL_SOUND);
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
        updateAdvancedButtonsVisibility();
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

    private void playSound(String path) {
        if (soundOn) {
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
    }

    private void addNumberListener(JButton button, int number) {
        button.setUI(buttonUI);
        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);
                logic.numberInput(number);
                playSound(Config.NUMBER_SOUND);
            }
        });
    }

    private void addOperatorListener(JButton button, Operator op) {
        button.setUI(operatorButtons);
        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);
                logic.setOperator(op);
                playSound(Config.OPERATOR_SOUND);
            }
        });
    }

    private void updateAdvancedButtonsVisibility() {
        int w = getWidth();
        int h = getHeight();
        if (advancedModeEnabled) {
            setSize((w / 4) * 6, h);
        } else {
            setSize((w / 6) * 4, h);
        }
        power.setVisible(advancedModeEnabled);
        sin.setVisible(advancedModeEnabled);
        sqrt.setVisible(advancedModeEnabled);
        cos.setVisible(advancedModeEnabled);
        faculty.setVisible(advancedModeEnabled);
        tan.setVisible(advancedModeEnabled);
        reciprocal.setVisible(advancedModeEnabled);
        logarithm.setVisible(advancedModeEnabled);
        modulo.setVisible(advancedModeEnabled);
        soundSwitch.setVisible(advancedModeEnabled);
    }

    private void initializeComponents() {
        soundSwitch = new JToggleButton("\uD83D\uDD07");
        advancedModeSwitch = new JToggleButton("fx");
        setUniformSize(advancedModeSwitch);
        clear = new JButton("AC");
        setUniformSize(clear);
        negPos = new JButton("±");
        setUniformSize(negPos);
        delete = new JButton("⌫");
        setUniformSize(delete);
        divide = new JButton("÷");
        setUniformSize(divide);
        a7Button = new JButton("7");
        setUniformSize(a7Button);
        a8Button = new JButton("8");
        setUniformSize(a8Button);
        a9Button = new JButton("9");
        setUniformSize(a9Button);
        multi = new JButton("×");
        setUniformSize(multi);
        a4Button = new JButton("4");
        setUniformSize(a4Button);
        a5Button = new JButton("5");
        setUniformSize(a5Button);
        a6Button = new JButton("6");
        setUniformSize(a6Button);
        minus = new JButton("-");
        setUniformSize(minus);
        a1Button = new JButton("1");
        setUniformSize(a1Button);
        a2Button = new JButton("2");
        setUniformSize(a2Button);
        a3Button = new JButton("3");
        setUniformSize(a3Button);
        plus = new JButton("+");
        setUniformSize(plus);
        a0Button = new JButton("0");
        setUniformSize(a0Button);
        buttonComma = new JButton(".");
        setUniformSize(buttonComma);
        equals = new JButton("=");
        setUniformSize(equals);
        sqrt = new JButton("√");
        setUniformSize(sqrt);
        power = new JButton("x\u02B8");
        setUniformSize(power);
        faculty = new JButton("x!");
        setUniformSize(faculty);
        sin = new JButton("sin");
        setUniformSize(sin);
        cos = new JButton("cos");
        setUniformSize(cos);
        tan = new JButton("tan");
        setUniformSize(tan);
        reciprocal = new JButton("¹⁄ₓ");
        setUniformSize(reciprocal);
        logarithm = new JButton("log₁₀");
        setUniformSize(logarithm);
        modulo= new JButton("%");
        setUniformSize(modulo);
    }

    private void setUniformSize(AbstractButton button) {
        Dimension buttonSize = new Dimension(60, 60); // Passen Sie die Größe nach Bedarf an
        button.setPreferredSize(buttonSize);
        button.setMinimumSize(buttonSize);
        button.setMaximumSize(buttonSize);
        button.setFont(new Font("Arial", Font.PLAIN, 14)); // Passen Sie die Schriftgröße nach Bedarf an
        button.setHorizontalAlignment(SwingConstants.CENTER);
        button.setVerticalAlignment(SwingConstants.CENTER);
    }

    private void addButton(JPanel panel, AbstractButton button, int x, int y, GridBagConstraints gbc) {
        gbc.gridx = x;
        gbc.gridy = y;
        panel.add(button, gbc);
    }
}