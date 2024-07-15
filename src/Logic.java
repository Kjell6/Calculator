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
        disp += number;
        if (operator == 0) {
            number1 = Double.parseDouble(disp);
        } else {
            number2 = Double.parseDouble(disp);
        }
        publishDisplayChange(disp);
    }

    public void decimalP() {
        disp += ".";
        publishDisplayChange(disp);
    }

    public void clear() {
        number1 = 0;
        number2 = 0;
        operator = 0;
        disp = "";
        publishDisplayChange(disp);
    }

    public void delete() {
        disp = disp.substring(0, disp.length() - 1);
        if (operator == 0) {
            number1 = Double.parseDouble(disp);
        } else {
            number2 = Double.parseDouble(disp);
        }
        publishDisplayChange(disp);
    }

    public void setOperator(int op) {
        operator = op;
        disp = "";
        publishDisplayNull();
    }

    public void result() {
        switch (operator) {
            case 1: disp = "" + (number1 + number2); break;
            case 2: disp = "" + (number1 - number2); break;
            case 3: disp = "" + (number1 * number2); break;
            case 4: disp = "" + (number1 / number2); break;
        }
        number1 = Double.parseDouble(disp);
        number2 = 0;
        publishDisplayChange(disp.replaceAll("0*$", "").replaceAll("\\.$", ""));
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
        System.out.println(this.disp);
    }

    private void publishDisplayNull() {
        for (ICalculatorInterface listener : subscribers) {
            listener.displayNull();
        }
        System.out.println(this.disp);
    }
}
