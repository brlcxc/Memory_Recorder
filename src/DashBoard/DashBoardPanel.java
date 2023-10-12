package DashBoard;

import javax.swing.*;
import java.awt.*;

public class DashBoardPanel extends JPanel {
    JPanel sidePanel;
    DashBoardPanel(){
        setBackground(Color.ORANGE);
        setSidePanel();
    }
    private void setSidePanel(){
        sidePanel = new JPanel();
        sidePanel.setBackground(Color.MAGENTA);
    }
    public JPanel getSidePanel(){
        return sidePanel;
    }
}