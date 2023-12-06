package DashBoard;

import Defaults.Colors;

import javax.swing.*;
import java.awt.*;

public class DashTextField extends JTextField {
    public DashTextField(String text){
        setFont(new Font("SansSerif", Font.PLAIN, 14));
        setText(text);
        setPreferredSize(new Dimension(280, 30));
        setBackground(Colors.cream);
        setBorder(BorderFactory.createEmptyBorder());
        setEditable(false);
    }
}