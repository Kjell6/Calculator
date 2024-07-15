import java.util.LinkedList;
import java.util.List;

public class Logic {
    private double displayNum;
    private String disp;
    private double number1;
    private double number2;
    private int operator; // plus = 1, minus = 2, mulit = 3, divide = 4
    private final List<ICalculatorInterface> subscribers;

    public Logic() {
        this.subscribers = new LinkedList<>();
        this.displayNum = 0;
        this.operator = 0;
        number1 = 0;
        number2 = 0;
        disp = "";
    }

    public void numberInput(int number) {
        displayNum = (displayNum * 10) + number;
        disp += number;
        if (operator == 0) {
            number1 = displayNum;
        } else {
            number2 = displayNum;
        }
        publishDisplayChange(displayNum);
    }

    public void clear() {
        displayNum = 0;
        number1 = 0;
        number2 = 0;
        operator = 0;
        publishDisplayChange(displayNum);
    }

    public void delete() {
        /*displayNum = displayNum / 10;
        if (operator == 0) {
            number1 = displayNum;
        } else {
            number2 = displayNum;
        }*/
        disp = disp.substring(0, disp.length() - 1);
    }

    public void setOperator(int op) {
        operator = op;
        displayNum = 0;
        publishDisplayNull();

    }

    public void result() {
        switch (operator) {
            case 1: displayNum = number1 + number2; break;
            case 2: displayNum = number1 - number2; break;
            case 3: displayNum = number1 * number2; break;
            case 4: displayNum = number1 / number2; break;
        }
        number1 = displayNum;
        number2 = 0;
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

    private void publishDisplayChange(double disp) {
        for (ICalculatorInterface listener : subscribers) {
            listener.displayNumberChange(disp);
        }
        System.out.println(this.disp);
    }

    private void publishDisplayNull() {
        for (ICalculatorInterface listener : subscribers) {
            listener.displayNull();
        }
    }
}
