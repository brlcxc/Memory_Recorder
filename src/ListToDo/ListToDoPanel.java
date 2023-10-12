package ListToDo;

import javax.swing.*;
import java.awt.*;

public class ListToDoPanel extends JPanel {
    JPanel sidePanel;
    ListToDoPanel(){
        setBackground(Color.YELLOW);
        setSidePanel();
    }
    private void setSidePanel(){
        sidePanel = new JPanel();
        sidePanel.setBackground(Color.GREEN);
    }
    public JPanel getSidePanel(){
        return sidePanel;
    }
}