package Diary;

import javax.swing.*;
import java.awt.*;

public class DiaryPanel extends JPanel {
    JPanel sidePanel;
    DiaryPanel(){
        setBackground(Color.PINK);
        setSidePanel();
    }
    private void setSidePanel(){
        sidePanel = new JPanel();
        sidePanel.setBackground(Color.YELLOW);
    }
    public JPanel getSidePanel(){
        return sidePanel;
    }
}