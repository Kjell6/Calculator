package model;

import java.awt.*;
import java.util.LinkedList;
import java.util.List;

/**
 * The Logic class handles the core calculations and business logic
 * for the calculator application. It manages number inputs, operators,
 * and updates to the display. It also allows subscribers to listen for
 * display changes.
 */
public class Logic {
    public String displayNum;
    private double number1;
    private double number2;
    Operator operator;
    private final List<ICalculatorInterface> subscribers;

    /**
     * Initializes a new instance of the Logic class, setting up
     * initial values for the display number, operands, and operator.
     */
    public Logic() {
        this.subscribers = new LinkedList<>();
        operator = Operator.NONE;
        number1 = 0;
        number2 = 0;
        displayNum = "";
    }

    /**
     * Handles button presses by determining the action based on the button text.
     *
     * @param buttonText the text of the button that was pressed
     */
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

    /**
     * Handles number input by appending the number to the display number
     * and updating the corresponding operand based on the current operator.
     *
     * @param number the number input by the user
     */
    public void numberInput(int number) {
        displayNum += number;
        changeActiveNumber(Double.parseDouble(displayNum));
        publishDisplayChange();
    }


    /**
     * Sets the operator for the calculation and prepares the display
     * for the next input. If the operator is unary, the result is computed immediately.
     *
     * @param op the operator to be set
     */
    public void setOperator(Operator op) {
        operator = op;
        displayNum = "";
        if (isMonoOperator(op)) {
            result();
        } else {
            publishDisplayNull();
        }
    }

    /**
     * Adds a decimal point to the display number if it doesn't already
     * contain one. If the display is empty, it initializes it to "0".
     */
    public void addDecimalPoint() {
        if (displayNum.isEmpty()) displayNum = "0";
        if (!displayNum.contains(".")) {
            displayNum += ".";
            publishDisplayChange();
        }
    }

    /**
     * Clears all stored values, resetting the operator and display number.
     */
    public void clear() {
        number1 = 0;
        number2 = 0;
        operator = Operator.NONE;
        displayNum = "";
        publishDisplayChange();
    }

    /**
     * Deletes the last character from the display number and updates the corresponding operand.
     */
    public void deleteChar() {
        if (displayNum.length() == 1 || displayNum.isEmpty()) {
            displayNum = "";
        } else {
            displayNum = displayNum.substring(0, displayNum.length() - 1);
        }
        changeActiveNumber(displayNum.isEmpty() ? 0 : Double.parseDouble(displayNum));
        publishDisplayChange();
    }


    /**
     * Switches the sign of the current display number.
     */
    public void switchSign() {
        if (!displayNum.isEmpty()) {
            displayNum = (displayNum.charAt(0) == '-') ? displayNum.substring(1) : "-" + displayNum;
            changeActiveNumber(Double.parseDouble(displayNum));
            publishDisplayChange();
        }
    }


    /**
     * Calculates the result based on the current operator and updates
     * the display. If the operation results in an invalid state (e.g.
     * division by zero), it displays an error message.
     */
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
        // Remove trailing zeros and decimal point if result is a whole number
        displayNum = (result + "").replaceAll("0*$", "").replaceAll("\\.$", "");

        if (displayNum.contains("Infinity") || displayNum.contains("NaN")) {
            displayNum = "";
            number1 = 0;
            publishDisplayChange("Error");
        } else {
            number1 = Double.parseDouble(displayNum);
        }
        if (displayNum.equals("0")) displayNum = "";
        number2 = 0;
        operator = Operator.NONE;
    }

    /**
     * Checks if the given operator is a unary operator.
     *
     * @param op the operator to check
     * @return true if the operator is unary, false otherwise
     */
    private boolean isMonoOperator(Operator op) {
        return op == Operator.SQRT || op == Operator.SIN || op == Operator.COS
                || op == Operator.TAN || op == Operator.FACULTY || op == Operator.RECOPROCAL
                || op == Operator.LOGARITHM;
    }


    /**
     * changes the active number to the given number
     *
     * @param number the number, that will be the active one
     */
    private void changeActiveNumber(double number) {
        if (operator == Operator.NONE) {
            number1 = number;
        } else {
            number2 = number;
        }
    }

    /**
     * Calculates the factorial of a given number.
     *
     * @param n the number to calculate the factorial of
     * @return the factorial of the number
     */
    private double faculty(int n) {
        double result = 1;
        for (int i = 1; i <= n; i++) {
            result *= i;
        }
        return result;
    }

    /**
     * Returns the contrasting color for a given background color.
     *
     * @param groundColor the background color
     * @return the contrasting color
     */
    public Color getContrastingColor(Color groundColor) {
        float[] hsbValues = Color.RGBtoHSB(groundColor.getRed(), groundColor.getGreen(), groundColor.getBlue(), null);
        float brightness = hsbValues[2];
        if (brightness < 0.5f) {
            return Color.WHITE;
        } else {
            return Color.BLACK;
        }
    }

    /**
     * Subscribe/ add a listener to the subscriber list
     *
     * @param listener  the listener
     */
    public void subscribe(ICalculatorInterface listener) {
        subscribers.add(listener);
    }

    /**
     * Unsubscribe/ remove a listener from the subscriber list
     */
    public void unsubscribe(ICalculatorInterface listener) {
        subscribers.remove(listener);
    }

    /**
     * Notifies all subscribed listeners of a display number change.
     */
    private void publishDisplayChange() {
        for (ICalculatorInterface listener : subscribers) {
            listener.displayNumberChange(displayNum);
        }
    }

    /**
     * Notifies all subscribed listeners of a display number change.
     *
     * @param disp the new display number
     */
    private void publishDisplayChange(String disp) {
        for (ICalculatorInterface listener : subscribers) {
            listener.displayNumberChange(disp);
        }
    }

    /**
     * Notifies all subscribed listeners that the design change button was pressed.
     */
    private void publishDesignChange() {
        for (ICalculatorInterface listener : subscribers) {
            listener.designChangePress();
        }
    }

    /**
     * Notifies all subscribed listeners that the advanced mode button was pressed.
     */
    private void publishAdvancedMode() {
        for (ICalculatorInterface listener : subscribers) {
            listener.advancedModePress();
        }
    }

    /**
     * Notifies all subscribed listeners that the display is cleared.
     */
    private void publishDisplayNull() {
        for (ICalculatorInterface listener : subscribers) {
            listener.displayNull();
        }
    }
}