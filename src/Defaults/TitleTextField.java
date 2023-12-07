package Defaults;

import javax.swing.*;
import java.awt.*;

public class TitleTextField extends JTextField {
    public TitleTextField(String text){
        setFont(new Font("SansSerif", Font.BOLD, 18));
        setText(text);
        setForeground(Colors.textColor);
    }
}