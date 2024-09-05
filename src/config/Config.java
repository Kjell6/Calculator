package config;

import java.awt.*;

/**
 * The Config class contains the configuration settings for the calculator application,
 * including color settings for buttons, background, and filenames for design persistence.
 */
public class Config {

    public static Color BUTTON_COLOR = new Color(51, 51, 51);
    public static Color EQUAL_COLOR = new Color(255, 159, 10);
    public static Color OPERATOR_COLOR = new Color(165, 165, 165);
    public static Color BACKGROUND =  new Color(61, 63, 60);

    public static String DESIGNGALLERY = "designs.xml";
    public static String ACTIVEDESIGN = "CurrentDesigns.xml";

    /**
     * Changes the default button color to the specified color.
     *
     * @param c the new color for buttons
     */
    public static final void CHANGE_BUTTON_COLOR(Color c) {
        BUTTON_COLOR = c;
    }

    /**
     * Changes the default equals button color to the specified color.
     *
     * @param c the new color for the equals button
     */
    public static final void CHANGE_EQUAL_COLOR(Color c) {
        EQUAL_COLOR = c;
    }

    /**
     * Changes the default operator button color to the specified color.
     *
     * @param c the new color for operator buttons
     */
    public static final void CHANGE_OPERATOR_COLOR(Color c) {
        OPERATOR_COLOR = c;
    }

    /**
     * Changes the default background color to the specified color.
     *
     * @param c the new background color
     */
    public static final void CHANGE_BACKGROUND(Color c) {
        BACKGROUND = c;
    }
}
