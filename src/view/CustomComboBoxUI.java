package view;

import config.Config;
import javax.swing.*;
import javax.swing.plaf.basic.BasicComboBoxUI;
import javax.swing.plaf.basic.BasicComboPopup;
import javax.swing.plaf.basic.ComboPopup;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;

public class CustomComboBoxUI extends BasicComboBoxUI {
    private static final int ARROW_WIDTH = 15;
    private static final int BOX_HEIGHT = 20;

    @Override
    protected JButton createArrowButton() {
        return new ArrowButton();
    }

    private class ArrowButton extends JButton {
        ArrowButton() {
            setContentAreaFilled(false);
            setBorder(BorderFactory.createEmptyBorder());
        }

        @Override
        public Dimension getPreferredSize() {
            return new Dimension(ARROW_WIDTH, BOX_HEIGHT);
        }

        @Override
        public void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(Config.OPERATOR_COLOR);
            g2.fillRect(0, 0, getWidth(), getHeight());
            g2.setColor(getForeground());
            int[] xPoints = {getWidth() / 2 - 3, getWidth() / 2, getWidth() / 2 + 3};
            int[] yPoints = {getHeight() / 2 - 2, getHeight() / 2 + 2, getHeight() / 2 - 2};
            g2.fillPolygon(xPoints, yPoints, 3);
            g2.dispose();
        }
    }

    @Override
    protected ListCellRenderer<Object> createRenderer() {
        return new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                setBackground(isSelected ? new Color(101, 136, 174) : Config.OPERATOR_COLOR);
                setForeground(isSelected ? Color.WHITE : Color.BLACK);
                setBorder(BorderFactory.createEmptyBorder(2, 5, 2, 5));
                return this;
            }
        };
    }

    @Override
    public void installUI(JComponent c) {
        super.installUI(c);
        comboBox.setBackground(Config.OPERATOR_COLOR);
        comboBox.setForeground(Color.BLACK);
        comboBox.setPreferredSize(new Dimension(comboBox.getPreferredSize().width, BOX_HEIGHT));
    }

}