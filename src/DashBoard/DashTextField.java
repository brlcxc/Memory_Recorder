package DashBoard;

import Defaults.Colors;

import javax.swing.*;
import java.awt.*;

public class DashTextField extends JTextField {
    public DashTextField(String text){
        setFont(new Font("SansSerif", Font.PLAIN, 14));
        setText(text);
<<<<<<<<< Temporary merge branch 1
        setFont(new Font("SansSerif", Font.PLAIN, 20));
        setPreferredSize(new Dimension(200, 30));
=========
        setPreferredSize(new Dimension(280, 30));
>>>>>>>>> Temporary merge branch 2
        setBackground(Colors.cream);
        setBorder(BorderFactory.createEmptyBorder());
        setEditable(false);
    }
}