package Defaults;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MenuButton extends JButton {
    public MenuButton(String buttonText){
        setBackground(Colors.pastelPurple);
        setForeground(Colors.textColor);
        setFont((new Font("SansSerif",Font.BOLD, 18)));
        setText(buttonText);
        setPreferredSize(new Dimension(140,50));
        addMouseListener(new MouseListener());
    }

    private class MouseListener extends MouseAdapter {
        public void mouseEntered(MouseEvent e) {
            setBackground(Colors.mintGreen);
        }
        public void mouseExited(MouseEvent e) {
            setBackground(Colors.pastelPurple);
        }
    }
}