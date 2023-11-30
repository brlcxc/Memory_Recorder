package DashBoard;
import Defaults.*;
import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.ResultSet;

public class DashBoardPanel extends JPanel {
    JPanel dashBoardPanel = new JPanel();
    JPanel sidePanel = new JPanel();
    PhotoPanel photoPanel = new PhotoPanel();
    SideButtonPanel sideButtonPanel;
    Connection connection;
    private ResultSet resultSet;
    private JFrame  mainFrame;

    public DashBoardPanel(Connection con, ResultSet result ,JFrame frame){
        connection = con;
        resultSet = result;
        mainFrame = frame;
        setDashBoardPanel();
        setSidePanel();

    }
    private void setSidePanel(){
        sideButtonPanel= new SideButtonPanel(dashBoardPanel, connection, resultSet, mainFrame);
        sidePanel.setBackground(Colors.pastelPurple);
        sidePanel.setLayout(new BorderLayout());
        sidePanel.setPreferredSize(new Dimension(100, 400));
        sidePanel.add(photoPanel.getPhotoPanel(), BorderLayout.NORTH);
        sidePanel.add(Box.createVerticalStrut(5),BorderLayout.CENTER);
        sidePanel.add(sideButtonPanel.getSideButtonPanel(), BorderLayout.SOUTH);

    }
    public JPanel getSidePanel(){
        return sidePanel;
    }

    private void setDashBoardPanel(){
        dashBoardPanel.setPreferredSize(new Dimension(400, 200));
        dashBoardPanel.setBackground(Colors.cream);
    }
    public JPanel getDashBoardPanel(){
        return dashBoardPanel;
    }
}