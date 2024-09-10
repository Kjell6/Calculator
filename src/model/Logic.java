package model;

import java.awt.*;
import java.util.LinkedList;
import java.util.List;

public class Logic {
    private String displayNum;
    private double number1;
    private double number2;
    Operator operator;
    private final List<ICalculatorInterface> subscribers;

    public Logic() {
        this.subscribers = new LinkedList<>();
        operator = Operator.NONE;
        number1 = 0;
        number2 = 0;
        displayNum = "";
    }

    public void buttonPressed(String buttonText) {
        switch (buttonText) {
            case "C" -> clear();
            case "⌫" -> deleteChar();
            case "=" -> result();
            case "±" -> switchSign();
            case "." -> addDecimalPoint();
            case "xʸ" -> setOperator(Operator.POWER);
            case "√" -> setOperator(Operator.SQRT);
            case "sin" -> setOperator(Operator.SIN);
            case "cos" -> setOperator(Operator.COS);
            case "tan" -> setOperator(Operator.TAN);
            case "x!" -> setOperator(Operator.FACULTY);
            case "¹⁄ₓ" -> setOperator(Operator.RECOPROCAL);
            case "log₁₀" -> setOperator(Operator.LOGARITHM);
            case "%" -> setOperator(Operator.MODULO);
            case "+" -> setOperator(Operator.PLUS);
            case "-" -> setOperator(Operator.MINUS);
            case "×" -> setOperator(Operator.MULTI);
            case "÷" -> setOperator(Operator.DIVIDE);
            case "🎨" -> publishDesignChange();
            case "fx" -> publishAdvancedMode();
            default -> numberInput(Integer.parseInt(buttonText));
        }
    }

    public void numberInput(int number) {
        displayNum += number;
        changeActiveNumber(Double.parseDouble(displayNum));
        publishDisplayChange();
    }

    public void setOperator(Operator op) {
        operator = op;
        displayNum = "";
        if (isMonoOperator(op)) {
            result();
        } else {
            publishDisplayNull();
        }
    }

    public void addDecimalPoint() {
        if (displayNum.isEmpty()) displayNum = "0";
        if (!displayNum.contains(".")) {
            displayNum += ".";
            publishDisplayChange();
        }
    }

    public void clear() {
        number1 = 0;
        number2 = 0;
        operator = Operator.NONE;
        displayNum = "";
        publishDisplayChange();
    }

    public void deleteChar() {
        if (displayNum.length() == 1 || displayNum.isEmpty()) {
            displayNum = "";
        } else {
            displayNum = displayNum.substring(0, displayNum.length() - 1);
        }
        changeActiveNumber(displayNum.isEmpty() ? 0 : Double.parseDouble(displayNum));
        publishDisplayChange();
    }

    public void switchSign() {
        if (!displayNum.isEmpty()) {
            displayNum = (displayNum.charAt(0) == '-') ? displayNum.substring(1) : "-" + displayNum;
            changeActiveNumber(Double.parseDouble(displayNum));
            publishDisplayChange();
        }
    }

    public void result() {
        double result = switch (operator) {
            case Operator.PLUS -> number1 + number2;
            case Operator.MINUS -> number1 - number2;
            case Operator.MULTI -> number1 * number2;
            case Operator.DIVIDE -> number1 / number2;
            case Operator.NONE -> number1;
            case Operator.POWER -> Math.pow(number1, number2);
            case Operator.SQRT -> Math.sqrt(number1);
            case Operator.SIN -> Math.sin(Math.toRadians(number1));
            case Operator.COS -> Math.cos(Math.toRadians(number1));
            case Operator.TAN -> Math.tan(Math.toRadians(number1));
            case Operator.FACULTY -> faculty((int) number1);
            case Operator.RECOPROCAL -> 1 / number1;
            case Operator.LOGARITHM -> Math.log10(number1);
            case Operator.MODULO -> number1 % number2;
        };
        displayNum = (result + "").replaceAll("0*$", "").replaceAll("\\.$", "");

        if (displayNum.contains("Infinity")|| displayNum.contains("NaN")) {
            displayNum = "";
            number1 = 0;
            publishDisplayChange("Error");
        } else {
            publishDisplayChange();
            number1 = Double.parseDouble(displayNum);
        }
        if (displayNum.equals("0")) displayNum = "";
        number2 = 0;
        operator = Operator.NONE;
    }

    private boolean isMonoOperator(Operator op) {
        return op == Operator.SQRT || op == Operator.SIN || op == Operator.COS
                || op == Operator.TAN || op == Operator.FACULTY || op == Operator.RECOPROCAL
                || op == Operator.LOGARITHM;
    }

    private void changeActiveNumber(double number) {
        if (operator == Operator.NONE) {
            number1 = number;
        } else {
            number2 = number;
        }
    }

    private double faculty(int n) {
        double result = 1;
        for (int i = 1; i <= n; i++) {
            result *= i;
        }
        return result;
    }

    public Color getContrastingColor(Color groundColor) {
        float[] hsbValues = Color.RGBtoHSB(groundColor.getRed(), groundColor.getGreen(), groundColor.getBlue(), null);
        float brightness = hsbValues[2];
        if (brightness < 0.5f) {
            return Color.WHITE;
        } else {
            return Color.BLACK;
        }
    }

    public void subscribe(ICalculatorInterface listener) {
        subscribers.add(listener);
    }

    public void unsubscribe(ICalculatorInterface listener) {
        subscribers.remove(listener);
    }

    private void publishDisplayChange() {
        for (ICalculatorInterface listener : subscribers) {
            listener.displayNumberChange(displayNum);
        }
    }

    private void publishDisplayChange(String disp) {
        for (ICalculatorInterface listener : subscribers) {
            listener.displayNumberChange(disp);
        }
    }

    private void publishDesignChange() {
        for (ICalculatorInterface listener : subscribers) {
            listener.designChangePress();
        }
    }

    private void publishAdvancedMode() {
        for (ICalculatorInterface listener : subscribers) {
            listener.advancedModePress();
        }
    }

    private void publishDisplayNull() {
        for (ICalculatorInterface listener : subscribers) {
            listener.displayNull();
        }
    }
}