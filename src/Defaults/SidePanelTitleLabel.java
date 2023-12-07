package Defaults;

import javax.swing.*;
import java.awt.*;

public class SidePanelTitleLabel extends JLabel {
    public SidePanelTitleLabel(String text, int size){
        setFont(new Font("SansSerif", Font.BOLD, size));
        setText(text);
        setForeground(Colors.textColor);
    }
}
