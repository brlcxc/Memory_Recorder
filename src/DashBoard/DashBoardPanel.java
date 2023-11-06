package DashBoard;
import Defaults.*;
import javax.swing.*;
import java.awt.*;

public class DashBoardPanel extends JPanel {
    JPanel sidePanel;
    PhotoArea photoPanel = new PhotoArea();

    public DashBoardPanel(){
        setBackground(new Color(241,148,184));
        setSidePanel();
    }
    private void setSidePanel(){

        sidePanel = new JPanel();
        sidePanel.setPreferredSize(new Dimension(50, 300));
        sidePanel.add(photoPanel.getPhotoPanel());
        sidePanel.setBackground(new Color(248,184,208));
    }
    public JPanel getSidePanel(){
        return sidePanel;
    }

}