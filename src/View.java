import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.plaf.basic.BasicButtonUI;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

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
    private JPanel numberPanel;
    private JPanel zeroDeciPanel;
    private JPanel inputPanel;
    private JPanel operatorPanel;
    private JPanel buttonPanel;
    private JButton negPos;
    private final Logic logic;

    public View(Logic l) {
        this.logic = l;
        logic.subscribe(this);
        setContentPane(mainPanel);
        setTitle("Calculator");
        setSize(300, 400);
        // exit application on close
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        // show window in the center of the screen
        setLocationRelativeTo(null);
        // window is not resizable
        setResizable(true);
        getContentPane().setBackground(Config.BACKGROUND);
        inputPanel.setBackground(Config.BACKGROUND);
        buttonPanel.setBackground(Config.BACKGROUND);
        numberPanel.setBackground(Config.BACKGROUND);
        zeroDeciPanel.setBackground(Config.BACKGROUND);
        operatorPanel.setBackground(Config.BACKGROUND);
        display.setForeground(Color.WHITE);
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                pressButton(e.getKeyCode());
            }
        });
        CustomButtonUI buttonUI = new CustomButtonUI();
        a1Button.setUI(buttonUI);
        a1Button.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);
                logic.numberInput(1);
            }
        });
        a2Button.setUI(buttonUI);
        a2Button.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);
                logic.numberInput(2);
            }
        });
        a3Button.setUI(buttonUI);
        a3Button.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);
                logic.numberInput(3);
            }
        });
        a4Button.setUI(buttonUI);
        a4Button.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);
                logic.numberInput(4);
            }
        });
        a5Button.setUI(buttonUI);
        a5Button.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);
                logic.numberInput(5);
            }
        });
        a6Button.setUI(buttonUI);
        a6Button.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);
                logic.numberInput(6);
            }
        });
        a7Button.setUI(buttonUI);
        a7Button.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);
                logic.numberInput(7);
            }
        });
        a8Button.setUI(buttonUI);
        a8Button.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);
                logic.numberInput(8);
            }
        });
        a9Button.setUI(buttonUI);
        a9Button.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);
                logic.numberInput(9);
            }
        });
        a0Button.setUI(buttonUI);
        a0Button.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
            super.mousePressed(e);
                logic.numberInput(0);
            }
        });
        buttonComma.setUI(buttonUI);
        buttonComma.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);
                logic.decimalP();
            }
        });
        equals.setUI(buttonUI);
        equals.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);
                logic.result();
            }
        });
        plus.setUI(buttonUI);
        plus.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);
                logic.setOperator(1);
            }
        });
        minus.setUI(buttonUI);
        minus.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);
                logic.setOperator(2);
            }
        });
        multi.setUI(buttonUI);
        multi.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);
                logic.setOperator(3);
            }
        });
        divide.setUI(buttonUI);
        divide.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);
                logic.setOperator(4);
            }
        });
        clear.setUI(buttonUI);
        clear.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);
                logic.clear();
            }
        });
        delete.setUI(buttonUI);
        delete.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);
                logic.delete();
            }
        });
        negPos.setUI(buttonUI);
        negPos.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);
                logic.plusMinus();
            }
        });
        setVisible(true);
        setFocusable(true);
    }

    @Override
    public void displayNumberChange(String number) {
        display.setText(number);
    }

    @Override
    public void displayNull() {
        display.setText("");
    }

    class CustomButtonUI extends BasicButtonUI {
        @Override
        public void installUI (JComponent c) {
            super.installUI(c);
            AbstractButton button = (AbstractButton) c;
            button.setOpaque(false);
            button.setBorder(new EmptyBorder(5, 15, 5, 15));
            button.setForeground(Color.WHITE); // Setzt die Textfarbe auf Weiß
        }

        @Override
        public void paint (Graphics g, JComponent c) {
            AbstractButton b = (AbstractButton) c;
            paintBackground(g, b, b.getModel().isPressed() ? 2 : 0);
            super.paint(g, c);
        }

        private void paintBackground (Graphics g, JComponent c, int yOffset) {
            Dimension size = c.getSize();
            Graphics2D g2 = (Graphics2D) g;
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g.setColor(Config.BUTTON_COLOR.darker());
            g.fillRoundRect(0, yOffset, size.width - 3, size.height - yOffset - 3, 10, 10);
            g.setColor(Config.BUTTON_COLOR);
            g.fillRoundRect(0, yOffset, size.width - 3, size.height + yOffset - 5 - 3, 10, 10);
        }
    }


    private void pressButton(int keyCode) {
        switch (keyCode) {
            case 48:
                logic.numberInput(0);
                break;
            case 49:
                logic.numberInput(1);
                break;
            case 50:
                logic.numberInput(2);
                break;
            case 51:
                logic.numberInput(3);
                break;
            case 52:
                logic.numberInput(4);
                break;
            case 53:
                logic.numberInput(5);
                break;
            case 54:
                logic.numberInput(6);
                break;
            case 55:
                logic.numberInput(7);
                break;
            case 56:
                logic.numberInput(8);
                break;
            case 57:
                logic.numberInput(9);
                break;
            default:
                break;
        }
    }
}
