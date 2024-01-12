package Main;

import Defaults.Colors;

import javax.swing.*;
import java.awt.*;

public class RegisterLabel extends JLabel {
    RegisterLabel(String text) {
        setFont(new Font("SansSerif", Font.BOLD, 16));
        setText(text);
        setForeground(Colors.textColor);
    }
}
