package Defaults;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class StandardButton extends JButton {
    Color defaultColor;
    Color hoverColor;
    public StandardButton(String buttonText, Color defaultColor, Color hoverColor){
        this.defaultColor = defaultColor;
        this.hoverColor = hoverColor;
        setFont(new Font("SansSerif", Font.BOLD, 16));
        setText(buttonText);
        setBackground(defaultColor);
        setForeground(Colors.textColor);
        setFocusPainted(false);
        setBorder(BorderFactory.createEmptyBorder(10, 25, 10, 25));
        addMouseListener(new MouseListener());
    }

    private class MouseListener extends MouseAdapter {
        public void mouseEntered(MouseEvent e) {
            setBackground(hoverColor);
        }
        public void mouseExited(MouseEvent e) {
            setBackground(defaultColor);
        }
    }
}