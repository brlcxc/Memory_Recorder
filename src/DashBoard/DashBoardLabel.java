package DashBoard;

import Defaults.Colors;

import javax.swing.*;
import java.awt.*;

public class DashBoardLabel extends JLabel {
    DashBoardLabel(String text) {
        setFont(new Font("SansSerif", Font.BOLD, 20));
        setText(text);
        setForeground(Colors.textColor);
    }
}
