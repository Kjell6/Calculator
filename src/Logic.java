import java.util.LinkedList;
import java.util.List;

public class Logic {
    private float displayNum;
    private String disp;
    private float number1;
    private float number2;
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
            number1 = Float.parseFloat(disp);
        } else {
            number2 = Float.parseFloat(disp);
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
        if (disp.length() == 1) {
            disp = "";
        } else {
            disp = disp.substring(0, disp.length() - 1);
        }
        float numberToUpdate = disp.isEmpty() ? 0 : Float.parseFloat(disp);
        if (operator == 0) {
            number1 = numberToUpdate;
        } else {
            number2 = numberToUpdate;
        }
        publishDisplayChange(disp);
    }

    public void setOperator(int op) {
        operator = op;
        disp = "";
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
        number1 = Float.parseFloat(disp);
        number2 = 0;
        operator = 0;
        disp = (result + "").replaceAll("0*$", "").replaceAll("\\.$", "");
        publishDisplayChange(disp);
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
