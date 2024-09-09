package view.customUIs;

import config.Config;
import model.Logic;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.plaf.basic.BasicButtonUI;
import java.awt.*;

public class CustomButtonUI extends BasicButtonUI {
    private Color buttonColor;
    private Logic logic;

    public CustomButtonUI(Color buttonColor, Logic logic) {
        this.buttonColor = buttonColor;
        this.logic = logic;
    }

    @Override
    public void installUI (JComponent c) {
        super.installUI(c);
        AbstractButton button = (AbstractButton) c;
        button.setOpaque(false);
        button.setBorder(new EmptyBorder(5, 15, 5, 15));
        button.setForeground(logic.getContrastingColor(buttonColor));
    }

    @Override
    public void paint (Graphics g, JComponent c) {
        AbstractButton b = (AbstractButton) c;
        paintButton(g, b, b.getModel().isPressed());

        // Draw Text
        FontMetrics metrics = g.getFontMetrics(getCustomFont(b));
        String text = b.getText();
        int x = (b.getWidth() - metrics.stringWidth(text)) / 2;
        int y = ((b.getHeight() - metrics.getHeight()) / 2) + metrics.getAscent();

        g.setColor(b.getForeground());
        g.setFont(getCustomFont(b));
        g.drawString(text, x, y);
    }

    private Font getCustomFont(AbstractButton b) {
        return new Font("Helvetica", Font.PLAIN, 16);
    }

    private void paintButton(Graphics g, JComponent c, boolean isPressed) {
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        Color color = this.buttonColor;
        if (isPressed) color = color.darker();
        g.setColor(color);
        int diameter = Math.min(c.getWidth(), c.getHeight()) - 3;
        int x = (c.getWidth() - diameter) / 2;
        int y = (c.getHeight() - diameter) / 2;
        g2.fillOval(x, y, diameter, diameter);
    }
}