package Defaults;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MenuButton extends JButton {
    boolean active;
    public MenuButton(String buttonText){
        setBackground(Colors.pastelPurple);
        setForeground(Colors.textColor);
        setFont((new Font("SansSerif",Font.BOLD, 18)));
        setText(buttonText);
        setPreferredSize(new Dimension(140,50));
        addMouseListener(new MouseListener());
        addActionListener(new ButtonColorListener());
        active = false;
    }

    private class MouseListener extends MouseAdapter {
        public void mouseEntered(MouseEvent e) {
            setBackground(Colors.mintGreen);
        }
        public void mouseExited(MouseEvent e) {
            if(!active) {
                setBackground(Colors.pastelPurple);
            }
        }
    }
    private class ButtonColorListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            if(active){
                setBackground(Colors.pastelPurple);
                active = false;
            }
            else{
                setBackground(Colors.mintGreen);
                active = true;
            }
        }
    }
    public void deactivateButton(){
        active = false;
        setBackground(Colors.pastelPurple);
    }
}