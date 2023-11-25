package Defaults;

import javax.swing.*;
import java.awt.*;

public class DiaryLabel extends JLabel {
    public DiaryLabel(String text){
        setFont(new Font("SansSerif", Font.BOLD, 16));
        setText(text);
        setForeground(Colors.textColor);
    }
}
