package Notes;

import javax.swing.*;
import java.awt.*;

public class NotesPanel extends JPanel {
    JPanel sidePanel;
    public NotesPanel(){
        setBackground(Color.RED);
        setSidePanel();
    }
    private void setSidePanel(){
        sidePanel = new JPanel();
        sidePanel.setBackground(Color.ORANGE);
    }
    public JPanel getSidePanel(){
        return sidePanel;
    }
}
//called side panel in here but notes side panel outside