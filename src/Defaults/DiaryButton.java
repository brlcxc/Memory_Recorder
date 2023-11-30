package Defaults;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class DiaryButton extends JButton {
    Color bgColor;
    Color bgColor2;
    public DiaryButton(String buttonText, Color DefaultColor, Color HoverColor){
        this.bgColor = DefaultColor;
        this.bgColor2 = HoverColor;
        setFont(new Font("SansSerif", Font.BOLD, 16));
        setText(buttonText);
        setBackground(DefaultColor);
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