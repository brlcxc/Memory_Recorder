package DashBoard;

import Defaults.Colors;

import javax.swing.*;
import java.awt.*;

public class DashTextField2 extends JTextField {
    public DashTextField2(){
        setFont(new Font("SansSerif", Font.PLAIN, 14));
        setPreferredSize(new Dimension(200, 30));

        setBackground(Colors.cream);
//        setBorder(BorderFactory.createEmptyBorder());
    }
}
