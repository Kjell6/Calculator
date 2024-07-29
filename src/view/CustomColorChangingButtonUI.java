package view;

import config.Config;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.plaf.basic.BasicButtonUI;
import java.awt.*;

class CustomColorChangingButtonUI extends BasicButtonUI {
    private Color buttonColor;

    public CustomColorChangingButtonUI() {
        this.buttonColor = Config.OPERATOR_COLOR;
    }

    @Override
    public void installUI (JComponent c) {
        super.installUI(c);
        AbstractButton button = (AbstractButton) c;
        button.setOpaque(false);
        button.setBorder(new EmptyBorder(5, 15, 5, 15));
        //Changes Text color depending on the Brightness of the Button
        float[] hsbWerte = Color.RGBtoHSB(buttonColor.getRed(), buttonColor.getGreen(), buttonColor.getBlue(), null);
        float brightness = hsbWerte[2];
        if (brightness < 0.5f) {
            button.setForeground(Color.WHITE);
        } else {
            button.setForeground(Color.BLACK);
        }

    }

    @Override
    public void paint (Graphics g, JComponent c) {
        AbstractButton b = (AbstractButton) c;
        paintBackground(g, b, b.getModel().isPressed());
        super.paint(g, c);
    }

    private void paintBackground (Graphics g, JComponent c, boolean isPressed) {
        Dimension size = c.getSize();
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        Color color = this.buttonColor;
        if (isPressed) color = color.darker();
        g.setColor(color);
        g.fillRoundRect(3, 3, size.width - 6, size.height - 6, 20, 20);

    }
}