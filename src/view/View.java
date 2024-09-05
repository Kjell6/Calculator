package view;

import config.Config;
import model.DesignManager;
import model.ICalculatorInterface;
import model.Logic;
import model.Operator;
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
    //Number Buttons
    private JButton a1Button, a2Button, a3Button, a4Button, a5Button,
            a6Button, a7Button, a8Button, a9Button, a0Button;
    //Basic Operators
    private JButton buttonComma, divide, multi, minus, plus, negPos, equals;
    //Advanced Operators
    private JButton power, sqrt, sin, cos, tan, faculty, reciprocal, logarithm, modulo;

    private JButton clear, delete;
    private JButton colorSwitch, advancedModeSwitch;
    private JTextPane display;

    // UI-Elemente
    private final CustomButtonUI buttonUI;
    private final CustomButtonUI operatorButtonsUI;

    private final Logic logic;
    private boolean advancedModeEnabled = false;


    public View(Logic l) {
        this.logic = l;
        logic.subscribe(this);

        // Set the current to the last used
        DesignManager.changeActiveDesign(DesignManager.getActiveDesign());

        initializeComponents();

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

        //Adds all Buttons to the MainPanel, segmented by each row
        mainPanel.add(advancedModeSwitch, BorderLayout.SOUTH);
        addButton(buttonPanel, power, 0, 0, gbc);
        addButton(buttonPanel, colorSwitch, 1, 0, gbc);
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

        mainPanel.add(buttonPanel, BorderLayout.CENTER);
        mainPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
        add(mainPanel);

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

        //adding Button Liseners and UIs
        buttonUI = new CustomButtonUI();

        advancedModeSwitch.setUI(buttonUI);
        advancedModeSwitch.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);
                changeAdvancedMode();
            }
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
        operatorButtonsUI = new CustomButtonUI(Config.OPERATOR_COLOR);
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

        colorSwitch.setUI(operatorButtonsUI);
        colorSwitch.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);
                new DesignChangeDialog(View.this, logic);
            }
        });

        equals.setUI(new CustomButtonUI(Config.EQUAL_COLOR));
        equals.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);
                logic.result();
            }
        });
        buttonComma.setUI(buttonUI);
        buttonComma.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);
                logic.addDecimalPoint();
            }
        });
        clear.setUI(operatorButtonsUI);
        clear.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);
                logic.clear();
            }
        });
        delete.setUI(operatorButtonsUI);
        delete.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);
                logic.deleteChar();
            }
        });
        negPos.setUI(operatorButtonsUI);
        negPos.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);
                logic.switchSign();

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

    private void addNumberListener(JButton button, int number) {
        button.setUI(buttonUI);
        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);
                logic.numberInput(number);
            }
        });
    }

    private void addOperatorListener(JButton button, Operator op) {
        button.setUI(operatorButtonsUI);
        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);
                logic.setOperator(op);

            }
        });
    }

    private void updateAdvancedButtonsVisibility() {
        if (advancedModeEnabled) {
            setSize(Config.WIDTH, Config.HEIGHT);
        } else {
            setSize((Config.WIDTH * 2) / 3, Config.HEIGHT);
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
        colorSwitch.setVisible(advancedModeEnabled);
    }

    public void changeAdvancedMode() {
        advancedModeEnabled = !advancedModeEnabled;
        updateAdvancedButtonsVisibility();
    }

    private void initializeComponents() {
        colorSwitch = new JButton("\uD83C\uDFA8");
        advancedModeSwitch = new JButton("fx");
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
        power = new JButton("xʸ");
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
        int size = Config.WIDTH / 6;
        Dimension buttonSize = new Dimension(size, size); // Passen Sie die Größe nach Bedarf an
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