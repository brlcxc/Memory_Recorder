package Defaults;

import javax.swing.*;
import java.awt.*;

public class DiaryLabel extends JLabel {
    public DiaryLabel(String text, int size){
        setFont(new Font("SansSerif", Font.BOLD, size));
        setText(text);
        setForeground(Colors.textColor);
    }
}
