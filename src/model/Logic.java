package model;

import java.util.LinkedList;
import java.util.List;

/**
 * The Logic class handles the core calculations and business logic
 * for the calculator application. It manages number inputs, operators,
 * and updates to the display. It also allows subscribers to listen for
 * display changes.
 */
public class Logic {
    private String displayNum;
    private float number1;
    private float number2;
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
     * Handles number input by appending the number to the display number
     * and updating the corresponding operand based on the current operator.
     *
     * @param number the number input by the user
     */
    public void numberInput(int number) {
        displayNum += number;
        if (operator == Operator.NONE) {
            number1 = Float.parseFloat(displayNum);
        } else {
            number2 = Float.parseFloat(displayNum);
        }
         publishDisplayChange(displayNum);
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
            publishDisplayChange(displayNum);
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
        publishDisplayChange(displayNum);
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
        float numberToUpdate = displayNum.isEmpty() ? 0 : Float.parseFloat(displayNum);
        if (operator == Operator.NONE) {
            number1 = numberToUpdate;
        } else {
            number2 = numberToUpdate;
        }
        publishDisplayChange(displayNum);
    }

    /**
     * Toggles the sign of the current display number.
     */
    public void switchSign() {
        if (!displayNum.isEmpty()) {
            displayNum = (displayNum.charAt(0) == '-') ? displayNum.substring(1) : "-" + displayNum;
            if (operator == Operator.NONE) {
                number1 = Float.parseFloat(displayNum);
            } else {
                number2 = Float.parseFloat(displayNum);
            }
            publishDisplayChange(displayNum);
        }
    }

    /**
     * Updates the display with the current value of displayNum.
     */
    public void updateDisplay() {
        publishDisplayChange(displayNum);
    }

    /**
     * Calculates the result based on the current operator and updates
     * the display. If the operation results in an invalid state (e.g.
     * division by zero), it displays an error message.
     */
    public void result() {
        float result = switch (operator) {
            case Operator.PLUS -> number1 + number2;
            case Operator.MINUS -> number1 - number2;
            case Operator.MULTI -> number1 * number2;
            case Operator.DIVIDE -> number1 / number2;
            case Operator.NONE -> number1;
            case Operator.POWER -> (float) Math.pow(number1, number2);
            case Operator.SQRT -> (float) Math.sqrt(number1);
            case Operator.SIN -> (float) Math.sin(Math.toRadians(number1));
            case Operator.COS -> (float) Math.cos(Math.toRadians(number1));
            case Operator.TAN -> (float) Math.tan(Math.toRadians(number1));
            case Operator.FACULTY -> faculty((int) number1);
            case Operator.RECOPROCAL -> 1 / number1;
            case Operator.LOGARITHM -> (float) Math.log10(number1);
            case Operator.MODULO -> number1 % number2;
        };
        displayNum = (result + "").replaceAll("0*$", "").replaceAll("\\.$", "");
        if (displayNum.contains("Infinity")) {
            displayNum = "";
            number1 = 0;
            publishDisplayChange("Nicht möglich");
        } else {
            publishDisplayChange(displayNum);
            number1 = Float.parseFloat(displayNum);
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
     * Calculates the factorial of a given number.
     *
     * @param n the number to calculate the factorial of
     * @return the factorial of the number
     */
    private float faculty(int n) {
        float result = 1;
        for (int i = 1; i <= n; i++) {
            result *= i;
        }
        return result;
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
     *
     * @param disp the new display number
     */
    private void publishDisplayChange(String disp) {
        for (ICalculatorInterface listener : subscribers) {
            listener.displayNumberChange(disp);
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
