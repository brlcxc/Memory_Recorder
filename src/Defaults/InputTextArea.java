package Defaults;

import javax.swing.*;
import java.awt.*;

public class InputTextArea extends JTextArea {
    public InputTextArea(String text){
        setLineWrap(true);
        setText(text);
        setFont(new Font("SansSerif", Font.PLAIN, 14));
        setBackground(Colors.cream);
        setBorder(BorderFactory.createEmptyBorder());
        setEditable(false);
    }
}
