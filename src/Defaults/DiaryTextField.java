package Defaults;

import javax.swing.*;
import java.awt.*;

public class DiaryTextField extends JTextField {
    public DiaryTextField(String text){
        setFont(new Font("SansSerif", Font.BOLD, 18));
        setText(text);
        setForeground(Colors.textColor);
    }
}