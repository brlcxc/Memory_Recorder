package DashBoard;

import Defaults.Colors;

import javax.swing.*;
import java.awt.*;

public class DashTextField extends JTextField {
    public DashTextField(String text){
        setText(text);
        setPreferredSize(new Dimension(200, 30));
        setBackground(Colors.cream);
        setBorder(BorderFactory.createEmptyBorder());
        setEditable(false);
    }
}