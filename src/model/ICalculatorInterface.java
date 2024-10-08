package model;

public interface ICalculatorInterface {

    /**
     * Displays the given number on the display.
     *
     * @param num the number to be displayed
     */
    void displayNumberChange(String num);

    /**
     * Displays the given calculation on the display.
     *
     * @param num the calculation to be displayed
     */
    void calculationDisplayChange(String num);

    /**
     * Clears the display, setting it to the default state 0.
     */
    void displayNull();

    /**
     * Opens the design change dialog.
     */
    void designChangePress();

    /**
     * Toggles the advanced mode.
     */
    void advancedModePress();
}
