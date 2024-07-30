package model;

public interface ICalculatorInterface {

    /**
     * Displays the given number on the display.
     *
     * @param num the number to be displayed
     */
    void displayNumberChange(String num);

    /**
     * Clears the display, setting it to the default state 0.
     */
    void displayNull();
}
