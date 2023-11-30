package Defaults;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class ToDoButton extends JButton {
    Color bgColor;
    Color bgColor2;
    public ToDoButton(String buttonText, Color bgColor, Color bgColor2){
        this.bgColor = bgColor;
        this.bgColor2 = bgColor2;
        setFont(new Font("SansSerif", Font.BOLD, 16));
        setText(buttonText);
        setBackground(bgColor);
        setForeground(Colors.textColor);
        setFocusPainted(false);
        setBorder(BorderFactory.createEmptyBorder(10, 25, 10, 25));
        addMouseListener(new MouseListener());
    }

    private class MouseListener extends MouseAdapter {
        public void mouseEntered(MouseEvent e) {
            setBackground(bgColor2);
        }
        public void mouseExited(MouseEvent e) {
            setBackground(bgColor);
        }
    }
}