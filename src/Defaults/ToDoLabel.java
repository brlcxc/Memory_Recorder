package Defaults;

import javax.swing.*;
import java.awt.*;

public class ToDoLabel extends JLabel {
    public ToDoLabel(String labelText){
        setText(labelText);
        setFont(new Font("SansSerif",Font.BOLD, 18));
        setForeground(Colors.textColor);
    }
}