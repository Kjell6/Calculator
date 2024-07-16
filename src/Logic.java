import java.util.LinkedList;
import java.util.List;

public class Logic {
    private String displayNum;
    private float number1;
    private float number2;
    private int operator; // plus = 1, minus = 2, mulit = 3, divide = 4
    private final List<ICalculatorInterface> subscribers;

    public Logic() {
        this.subscribers = new LinkedList<>();
        this.operator = 0;
        number1 = 0;
        number2 = 0;
        displayNum = "0";
    }

    public void numberInput(int number) {
        displayNum += number;
        if (operator == 0) {
            number1 = Float.parseFloat(displayNum);
        } else {
            number2 = Float.parseFloat(displayNum);
        }
        publishDisplayChange(displayNum);
    }

    public void decimalP() {
        if (!displayNum.contains(".")) {
            displayNum += ".";
            publishDisplayChange(displayNum);
        }
    }

    public void clear() {
        number1 = 0;
        number2 = 0;
        operator = 0;
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
        if (operator == 0) {
            number1 = numberToUpdate;
        } else {
            number2 = numberToUpdate;
        }
        publishDisplayChange(displayNum);
    }

    public void plusMinus() {
        if (!displayNum.isEmpty()) {
            displayNum = (displayNum.charAt(0) == '-') ? displayNum.substring(1) : "-" + displayNum;
            if (operator == 0) {
                number1 = Float.parseFloat(displayNum);
            } else {
                number2 = Float.parseFloat(displayNum);
            }
            publishDisplayChange(displayNum);
        }
    }

    public void setOperator(int op) {
        operator = op;
        displayNum = "";
        publishDisplayNull();
    }

    public void result() {
        float result = 0;
        switch (operator) {
            case 1: result = number1 + number2; break;
            case 2: result = number1 - number2; break;
            case 3: result = number1 * number2; break;
            case 4: result = number1 / number2; break;
        }
        displayNum = (result + "").replaceAll("0*$", "").replaceAll("\\.$", "");
        number1 = Float.parseFloat(displayNum);
        number2 = 0;
        operator = 0;
        publishDisplayChange(displayNum);
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
