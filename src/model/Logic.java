package model;

import java.util.LinkedList;
import java.util.List;

public class Logic {
    private String displayNum;
    private float number1;
    private float number2;
    Operator operator;
    private final List<ICalculatorInterface> subscribers;

    public Logic() {
        this.subscribers = new LinkedList<>();
        //this.operator = 0;
        operator = Operator.NONE;
        number1 = 0;
        number2 = 0;
        displayNum = "";
    }

    public void numberInput(int number) {
        displayNum += number;
        if (operator == Operator.NONE) {
            number1 = Float.parseFloat(displayNum);
        } else {
            number2 = Float.parseFloat(displayNum);
        }
        publishDisplayChange(displayNum);
    }

    public void setOperator(Operator op) {
        operator = op;
        displayNum = "";
        if (isMonoOperato(op)) {
            result();
        } else {
            publishDisplayNull();
        }
    }

    public void decimalP() {
        if (displayNum.isEmpty()) displayNum = "0";
        if (!displayNum.contains(".")) {
            displayNum += ".";
            publishDisplayChange(displayNum);
        }
    }

    public void clear() {
        number1 = 0;
        number2 = 0;
        operator = Operator.NONE;
        displayNum = "";
        publishDisplayChange(displayNum);
    }

    public void delete() {
        if (displayNum.length() == 1 || displayNum.length() == 0) {
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

    public void plusMinus() {
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

    public void result() {
        float result = 0;
        switch (operator) {
            case Operator.PLUS: result = number1 + number2; break;
            case Operator.MINUS: result = number1 - number2; break;
            case Operator.MULTI: result = number1 * number2; break;
            case Operator.DIVIDE: result = number1 / number2; break;
            case Operator.NONE: result = number1; break;
            case Operator.POWER: result = (float) Math.pow(number1, number2); break;
            case Operator.SQRT: result = (float) Math.sqrt(number1); break;
            case Operator.SIN: result = (float) Math.sin(Math.toRadians(number1)); break;
            case Operator.COS: result = (float) Math.cos(Math.toRadians(number1)); break;
            case Operator.TAN: result = (float) Math.tan(Math.toRadians(number1)); break;
            case Operator.FACULTY: result = faculty((int) number1); break;
            case Operator.RECOPROCAL: result = 1 / number1; break;
            case Operator.LOGARITHM: result = (float) Math.log10(number1); break;
            case Operator.MODULO: result = number1 % number2; break;
        }
        displayNum = (result + "").replaceAll("0*$", "").replaceAll("\\.$", "");
        number1 = Float.parseFloat(displayNum);
        number2 = 0;
        operator = Operator.NONE;
        publishDisplayChange(displayNum);
    }

    private boolean isMonoOperato(Operator op) {
        return op == Operator.SQRT || op == Operator.SIN || op == Operator.COS
                || op == Operator.TAN || op == Operator.FACULTY || op == Operator.RECOPROCAL
                || op == Operator.LOGARITHM;
    }

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
     *
     * @param listener  the listener
     */
    public void unsubscribe(ICalculatorInterface listener) {
        subscribers.remove(listener);
    }

    private void publishDisplayChange(String disp) {
        for (ICalculatorInterface listener : subscribers) {
            listener.displayNumberChange(disp);
        }
    }

    private void publishDisplayNull() {
        for (ICalculatorInterface listener : subscribers) {
            listener.displayNull();
        }
    }
}
