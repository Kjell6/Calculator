import javax.swing.*;
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
    private JPanel deletePanel;
    private JPanel inputPanel;
    private JPanel operatorPanel;
    private JPanel buttonPanel;

    private final Logic logic;

    public View(Logic l) {
        this.logic = l;
        logic.subscribe(this);
        setContentPane(mainPanel);
        setTitle("Calculator");
        setSize(330, 350);
        // exit application on close
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        // show window in the center of the screen
        setLocationRelativeTo(null);
        // window is not resizable
        setResizable(true);
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                pressButton(e.getKeyCode());
            }
        });

        a1Button.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);
                logic.numberInput(1);
            }
        });
        a2Button.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);
                logic.numberInput(2);
            }
        });
        a3Button.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);
                logic.numberInput(3);
            }
        });
        a4Button.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);
                logic.numberInput(4);
            }
        });
        a5Button.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);
                logic.numberInput(5);
            }
        });
        a6Button.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);
                logic.numberInput(6);
            }
        });
        a7Button.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);
                logic.numberInput(7);
            }
        });
        a8Button.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);
                logic.numberInput(8);
            }
        });
        a9Button.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);
                logic.numberInput(9);
            }
        });
        a0Button.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
            super.mousePressed(e);
                logic.numberInput(0);
            }
        });
        buttonComma.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);
                logic.decimalP();
            }
        });
        equals.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);
                logic.result();
            }
        });
        plus.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);
                logic.setOperator(1);
            }
        });
        minus.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);
                logic.setOperator(2);
            }
        });
        multi.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);
                logic.setOperator(3);
            }
        });
        divide.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);
                logic.setOperator(4);
            }
        });
        setVisible(true);
        clear.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);
                logic.clear();
            }
        });
        delete.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);
                logic.delete();
            }
        });
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
