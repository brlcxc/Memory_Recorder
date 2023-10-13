package DashBoard;

import javax.swing.*;
import java.awt.*;

public class DashBoardPanel extends JPanel {
    JPanel sidePanel;
    public DashBoardPanel(){
        setBackground(Color.ORANGE);
        setSidePanel();
    }
    private void setSidePanel(){
        sidePanel = new JPanel();
        sidePanel.setBackground(Color.MAGENTA);
        sidePanel.add(new JLabel("test"));
    }
    public JPanel getSidePanel(){
        return sidePanel;
    }
}