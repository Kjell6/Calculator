package view;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.plaf.basic.BasicButtonUI;
import java.awt.*;

class CustomButtonUI extends BasicButtonUI {
    private Color buttonColor;

    public CustomButtonUI(Color buttonColor) {
        this.buttonColor = buttonColor;
    }

    public CustomButtonUI() {
        this.buttonColor = null;
    }

    @Override
    public void installUI (JComponent c) {
        super.installUI(c);
        AbstractButton button = (AbstractButton) c;
        button.setOpaque(false);
        button.setBorder(new EmptyBorder(5, 15, 5, 15));
        button.setForeground(Color.WHITE);
    }

    @Override
    public void paint (Graphics g, JComponent c) {
        AbstractButton b = (AbstractButton) c;
        paintBackground(g, b, b.getModel().isPressed() ? 2 : 0);
        super.paint(g, c);
    }

    private void paintBackground (Graphics g, JComponent c, int yOffset) {
        Dimension size = c.getSize();
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        Color color = this.buttonColor == null ? Config.BUTTON_COLOR : this.buttonColor;
        g.setColor(color.darker());
        g.fillRoundRect(0, yOffset, size.width - 3, size.height - yOffset - 3, 10, 10);
        g.setColor(color);
        g.fillRoundRect(0, yOffset, size.width - 3, size.height + yOffset - 5 - 3, 10, 10);
    }
}