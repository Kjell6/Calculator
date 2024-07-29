package config;

import java.awt.*;

public class Config {
    /*
    Old UI
    public static Color BUTTON_COLOR = new Color(91, 92, 92);
    public static Color EQUAL_COLOR = new Color(255, 160, 0);
    public static Color OPERATOR_COLOR = new Color(65, 65, 65);
    public static Color BACKGROUND =  new Color(37, 38, 39);
     */

    public static Color BUTTON_COLOR = new Color(51, 51, 51);
    public static Color EQUAL_COLOR = new Color(255, 159, 10);
    public static Color OPERATOR_COLOR = new Color(165, 165, 165);
    public static Color BACKGROUND =  new Color(61, 63, 60);


    public static final String NUMBER_SOUND = "src/assets/Number.wav";
    public static final String EQUAL_SOUND = "src/assets/Equal.wav";
    public static final String OPERATOR_SOUND = "src/assets/Operators.wav";
    public static final String DELETE_SOUND = "src/assets/Delete.wav";

    public static final void CHANGE_BUTTON_COLOR(Color c) {
        BUTTON_COLOR = c;
    }
    public static final void CHANGE_EQUAL_COLOR(Color c) {
        EQUAL_COLOR = c;
    }
    public static final void CHANGE_OPERATOR_COLOR(Color c) {
        OPERATOR_COLOR = c;
    }
    public static final void CHANGE_BACKGROUND(Color c) {
        BACKGROUND = c;
    }
}
