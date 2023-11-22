package ListToDo;
import Defaults.*;
import javax.swing.*;
import java.awt.*;

public class ListToDoPanel extends JPanel {
    JPanel sidePanel;
    public ListToDoPanel(){
        setBackground(Colors.mintGreen);
        setSidePanel();
    }
    private void setSidePanel(){
        sidePanel = new JPanel();
        sidePanel.setBackground(Color.GREEN);
        sidePanel.add(new JLabel("help"));
    }
    public JPanel getSidePanel(){
        return sidePanel;
    }
}