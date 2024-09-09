package view.customUIs;

import config.Config;
import model.Logic;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.plaf.basic.BasicButtonUI;
import java.awt.*;

public class CustomColorChangingButtonUI extends BasicButtonUI {
    private Color buttonColor;
    private Logic logic;

    public CustomColorChangingButtonUI(Logic l) {
        this.buttonColor = Config.OPERATOR_COLOR;
        logic = l;
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